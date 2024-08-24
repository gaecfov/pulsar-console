# 使用官方的 OpenJDK 作为基础镜像
FROM openjdk:17-jdk-alpine

# 设定应用程序的工作目录
WORKDIR /pulsar-console

# 将 Spring Boot 应用的 JAR 文件复制到容器中
# 假设你的 JAR 文件在当前目录下，并命名为 app.jar
COPY target/pulsar-console-0.0.1-alpha.jar /pulsar-console/app.jar

# 暴露 Spring Boot 应用的端口（通常为 8080）
EXPOSE 8080

# 运行 Spring Boot 应用
CMD ["java", "-jar", "/pulsar-console/app.jar"]