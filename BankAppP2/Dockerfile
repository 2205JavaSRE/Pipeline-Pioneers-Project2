FROM openjdk:8-jre

ENV db_username=postgres
ENV db_password=password
ENV db_url=jdbc:postgresql://pipeline-db-service:5432/postgres

COPY target/BankAppP2-1.0-SNAPSHOT.jar demo.jar

CMD ["java","-jar","demo.jar"]