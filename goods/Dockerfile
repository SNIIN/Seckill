FROM openjdk:11-jre
WORKDIR /app
ENV MAVEN_OPTS="-Dmaven.repo.local=/Seckill"
ARG JAR_FILE
ADD /target/${JAR_FILE} /app/app.jar
EXPOSE 8083
ENTRYPOINT ["java", "-jar", "app.jar"]
CMD ["--spring.datasource.url=jdbc:mysql://mysql:3306/seckill_goods?serverTimezone=Asia/Shanghai","--spring.datasource.username=seckill", "--spring.datasource.password=123456"]

