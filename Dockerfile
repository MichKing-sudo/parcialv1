FROM openjdk:17
COPY "./target/proyecto-0.0.1.jar" "app.jar"
EXPOSE 8099
ENTRYPOINT [ "java", "-jar", "app.jar" ]