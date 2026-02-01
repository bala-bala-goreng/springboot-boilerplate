# Troubleshooting: Traces Tidak Muncul di Jaeger

## Masalah
Traces dari authentication service (local) tidak muncul di Jaeger (Docker), meskipun:
- Tracing sudah ter-initialize
- Trace ID dan Span ID sudah ter-generate
- Request sudah diproses dengan sukses

## Analisis

Dari log, terlihat:
1. ✅ Tracing Configuration initialized
2. ✅ Trace ID ter-generate: `18f6e2d4-7bb7-476d-9a05-d8fad5a685c4`
3. ✅ Span ID ter-generate: `cd5c096c712ba732`
4. ❌ **TIDAK ADA log yang menunjukkan traces ter-export ke Jaeger**

## Kemungkinan Penyebab

### 1. Brave Bridge Tidak Mengirim ke OTLP
Spring Boot 3.5 dengan `micrometer-tracing-bridge-brave` **TIDAK otomatis** mengirim traces ke OTLP endpoint. Perlu konfigurasi tambahan.

### 2. Auto-Configuration Tidak Terpicu
Meskipun dependencies sudah ada, auto-configuration Spring Boot untuk OTLP export mungkin tidak terpicu.

### 3. Endpoint Tidak Terjangkau
Meskipun Jaeger container expose port `4318`, mungkin ada masalah konektivitas.

## Solusi yang Sudah Dicoba

1. ✅ Dependencies sudah ada di `authentication/pom.xml`:
   - `micrometer-tracing-bridge-brave`
   - `opentelemetry-exporter-otlp`

2. ✅ Konfigurasi sudah lengkap di `application-default.yml`:
   ```yaml
   management:
     tracing:
       enabled: true
       export:
         otlp:
           enabled: true
     otlp:
       tracing:
         endpoint: http://localhost:4318/v1/traces
         protocol: http/protobuf
   ```

3. ✅ Debug logging sudah aktif

## Solusi yang Direkomendasikan

### Opsi 1: Gunakan OTLP Bridge (Recommended)
Ganti `micrometer-tracing-bridge-brave` dengan `micrometer-tracing-otel`:

```xml
<!-- Remove -->
<dependency>
    <groupId>io.micrometer</groupId>
    <artifactId>micrometer-tracing-bridge-brave</artifactId>
</dependency>

<!-- Add -->
<dependency>
    <groupId>io.micrometer</groupId>
    <artifactId>micrometer-tracing-otel</artifactId>
</dependency>
```

### Opsi 2: Tambahkan Konfigurasi Eksplisit
Jika tetap menggunakan Brave bridge, tambahkan konfigurasi bean eksplisit untuk OTLP exporter (lihat file `OtlpTracingConfig.java` yang sudah dibuat sebelumnya).

### Opsi 3: Verifikasi Konektivitas
Test apakah endpoint Jaeger benar-benar accessible:
```bash
curl -v http://localhost:4318/v1/traces
```

Jika mendapat error connection refused, berarti ada masalah dengan Docker port mapping.

## Langkah Selanjutnya

1. Rebuild project: `./mvnw.cmd clean install`
2. Restart authentication service
3. Test dengan request: `POST /api/oauth/token`
4. Cek logs untuk error OTLP export
5. Cek Jaeger UI: `http://localhost:16686/search`

## Catatan Penting

**Spring Boot 3.5 dengan Brave bridge tidak otomatis mengirim traces ke OTLP**. Untuk OTLP export, lebih baik menggunakan `micrometer-tracing-otel` bridge atau menambahkan konfigurasi eksplisit.
