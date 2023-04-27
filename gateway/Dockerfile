FROM openjdk:11-jre
WORKDIR /app
ENV MAVEN_OPTS="-Dmaven.repo.local=/Seckill"
ARG JAR_FILE
ADD /target/${JAR_FILE} /app/app.jar
EXPOSE 8090
ENTRYPOINT ["java", "-jar", "app.jar"]


