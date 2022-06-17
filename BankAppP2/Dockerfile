FROM openjdk:8-jre

ENV db_url=$db_url
ENV db_username=$db_username
ENV db_password=$db_password

COPY target/BankAppP2-1.0-SNAPSHOT.jar demo.jar

CMD ["java","-jar","demo.jar"]