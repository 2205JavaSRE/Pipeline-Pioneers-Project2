FROM postgres
ENV POSTGRES_PASSWORD=password
ENV POSTGRES_DB=postgres
COPY ./bank_app_generate.sql /docker-entrypoint-initdb.d
