FROM openjdk:21-jdk-slim

LABEL maintainer="camimosquerab@gmail.com"

WORKDIR /app

COPY build/libs/demo-0.0.1-SNAPSHOT.jar app.jar

ADD https://raw.githubusercontent.com/vishnubob/wait-for-it/master/wait-for-it.sh wait-for-it.sh
RUN chmod +x wait-for-it.sh

EXPOSE 8080

ENTRYPOINT ["./wait-for-it.sh", "db:5432", "--", "java", "-jar", "app.jar"]