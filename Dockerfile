# Use uma imagem base do OpenJDK
FROM openjdk:17-alpine

 # Diretório de trabalho dentro do contêiner
WORKDIR /app

 # Copie o JAR da sua aplicação para o contêiner
COPY target/api-0.0.1-SNAPSHOT.jar /app/myapp.jar


 # Defina o comando para executar a aplicação Java
ENTRYPOINT ["java", "-jar", "/app/myapp.jar"]


EXPOSE 8080