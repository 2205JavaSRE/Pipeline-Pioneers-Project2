FROM postgres
ENV POSTGRES_PASSWORD=password
ENV POSTGRES_DB=postgres
ENV POSTGRES_USER=postgres
COPY ./bank_app_generate.sql /docker-entrypoint-initdb.d
