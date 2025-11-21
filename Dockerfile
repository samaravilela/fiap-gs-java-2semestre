# Multi-stage build para Quarkus
FROM maven:3.9.6-eclipse-temurin-17 AS build
WORKDIR /app

# Copiar pom.xml primeiro (para cache de dependências)
COPY pom.xml .

# Baixar dependências (isso cria cache se pom.xml não mudar)
RUN mvn dependency:go-offline -B || true

# Copiar código fonte
COPY src ./src

# Build da aplicação Quarkus com variáveis de ambiente para Oracle
ENV QUARKUS_DATASOURCE_DB_KIND=oracle
ENV QUARKUS_DATASOURCE_JDBC_DRIVER=oracle.jdbc.OracleDriver
RUN mvn clean package -DskipTests -B

# Verificar se o build foi bem-sucedido
RUN test -d /app/target/quarkus-app || (echo "Build failed - quarkus-app directory not found" && exit 1)

# Stage final - Runtime
FROM eclipse-temurin:17-jre
WORKDIR /app

# Copiar artefatos do build Quarkus
COPY --from=build /app/target/quarkus-app/lib/ /app/lib/
COPY --from=build /app/target/quarkus-app/*.jar /app/
COPY --from=build /app/target/quarkus-app/app/ /app/app/
COPY --from=build /app/target/quarkus-app/quarkus/ /app/quarkus/

# Variáveis de ambiente
ENV JAVA_OPTS="-Dquarkus.http.host=0.0.0.0 -Djava.util.logging.manager=org.jboss.logmanager.LogManager"
ENV JAVA_APP_JAR="/app/quarkus-run.jar"

# Expor porta
EXPOSE 8080

# Comando para executar
CMD ["java", "-jar", "/app/quarkus-run.jar"]
