FROM postgres:16-alpine

# Set environment variables
ENV POSTGRES_USER=app_user
ENV POSTGRES_PASSWORD=app_password
ENV POSTGRES_DB=app_db

# Copy initialization scripts
COPY init/*.sql /docker-entrypoint-initdb.d/

# Expose PostgreSQL port
EXPOSE 5432
