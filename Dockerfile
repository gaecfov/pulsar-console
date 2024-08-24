# 使用官方的 OpenJDK 作为基础镜像
FROM openjdk:17-jdk-alpine

# 设定应用程序的工作目录
WORKDIR /pulsar-console

# 将本地文件添加到容器中
COPY target/pulsar-console.jar /pulsar-console/app.jar

# 暴露 Spring Boot 应用的端口（通常为 8080）
EXPOSE 8080

# 运行 Spring Boot 应用
CMD ["java", "-jar", "/pulsar-console/app.jar"]