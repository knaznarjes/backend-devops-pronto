FROM eclipse-temurin:17-jre-alpine
WORKDIR /app
COPY --from=builder /app/target/*.jar app.jar

# Add health check wait script
RUN apk add --no-cache curl
HEALTHCHECK --interval=10s --timeout=3s --start-period=30s \
  CMD curl -f http://localhost:9090/actuator/health/liveness || exit 1

ENTRYPOINT ["java", "-Xms512m", "-Xmx512m", "-jar", "app.jar"]