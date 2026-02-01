# Troubleshooting: Jaeger Tracing untuk Service Lokal

## Masalah: Traces tidak muncul di Jaeger UI

### Checklist

1. **Pastikan Jaeger berjalan di Docker:**
   ```bash
   docker ps | findstr jaeger
   ```
   Seharusnya ada container `app-jaeger` yang running.

2. **Test koneksi ke Jaeger OTLP endpoint:**
   ```powershell
   Invoke-WebRequest -Uri http://localhost:4318 -Method GET -UseBasicParsing
   ```
   Seharusnya return 404 (bukan connection refused).

3. **Pastikan authentication service menggunakan `application-default.yml`:**
   - Service harus berjalan dengan profile `default`
   - Cek logs startup untuk melihat profile yang aktif

4. **Generate request untuk membuat traces:**
   ```bash
   # Test OAuth token endpoint
   curl -X POST http://localhost:8080/api/oauth/token ^
     -u merchant-x:merchant-x-secret ^
     -H "Content-Type: application/x-www-form-urlencoded" ^
     -d "grant_type=client_credentials"
   ```

5. **Cek logs authentication service:**
   - Cari log dengan keyword: `Tracing`, `OTLP`, `brave`, `micrometer.tracing`
   - Seharusnya ada log dari `TracingConfig` saat startup
   - Jika ada error OTLP export, akan muncul di logs

### Konfigurasi yang Harus Ada

**`application-default.yml`:**
```yaml
spring:
  application:
    name: service-authentication

management:
  tracing:
    enabled: true
    sampling:
      probability: 1.0
    service-name: ${spring.application.name}
    export:
      otlp:
        enabled: true
  otlp:
    tracing:
      endpoint: http://localhost:4318/v1/traces
      protocol: http/protobuf

logging:
  level:
    io.micrometer.tracing: DEBUG
    brave: DEBUG
    io.opentelemetry: DEBUG
```

### Debugging Steps

1. **Cek apakah TracingConfig ter-load:**
   - Saat startup, seharusnya ada log:
     ```
     === Tracing Configuration Initialized ===
     Service Name: service-authentication
     OTLP Endpoint: http://localhost:4318/v1/traces
     ```

2. **Cek apakah traces ter-generate:**
   - Setelah membuat request, cek logs untuk `traceId` dan `spanId`
   - Seharusnya ada log dengan format JSON yang mengandung `traceId`

3. **Cek Jaeger UI:**
   - Buka `http://localhost:16686`
   - Pilih service: `service-authentication` (bukan "jaeger-all-in-one")
   - Klik "Find Traces"
   - Jika tidak ada, coba refresh atau tunggu beberapa detik

4. **Jika masih tidak muncul:**
   - Rebuild project: `./mvnw.cmd clean install`
   - Restart authentication service
   - Pastikan tidak ada error di logs
   - Cek apakah Jaeger benar-benar accessible dari localhost

### Test Endpoint

Setelah service berjalan, test dengan endpoint khusus:

```bash
curl http://localhost:8080/api/test/trace
```

Endpoint ini akan:
- Generate trace yang seharusnya muncul di Jaeger
- Return trace ID dan span ID dalam response
- Log trace information untuk debugging

**Expected Response:**
```json
{
  "status": "success",
  "message": "Trace test endpoint called",
  "traceId": "abc123...",
  "spanId": "def456...",
  "tracerAvailable": true,
  "currentSpanAvailable": true
}
```

Jika `tracerAvailable: false` atau `currentSpanAvailable: false`, berarti tracing tidak aktif.

### Common Issues

1. **Service name tidak muncul:**
   - Pastikan `spring.application.name` ter-set dengan benar
   - Pastikan `management.tracing.service-name` menggunakan `${spring.application.name}`

2. **Endpoint tidak accessible:**
   - Pastikan Jaeger container expose port `4318:4318`
   - Test dengan `curl http://localhost:4318`

3. **Traces tidak ter-export:**
   - Pastikan `management.tracing.export.otlp.enabled: true`
   - Pastikan `management.otlp.tracing.endpoint` benar
   - Cek logs untuk error OTLP export
   - Cek Jaeger logs: `docker logs app-jaeger | Select-String -Pattern "otlp|trace"`

4. **Tracing tidak aktif:**
   - Pastikan `management.tracing.enabled: true`
   - Pastikan dependencies ada di `authentication/pom.xml` (tidak hanya di core)
   - Rebuild project setelah menambahkan dependencies
   - Cek logs startup untuk `Tracing Configuration Initialized`

5. **Tracer bean tidak tersedia:**
   - Pastikan dependencies `micrometer-tracing-bridge-brave` dan `opentelemetry-exporter-otlp` ada di `authentication/pom.xml`
   - Test dengan endpoint `/api/test/trace` untuk verifikasi