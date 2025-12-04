FROM eclipse-temurin:21-jdk-alpine
ENV JAR_NAME=ordering.jar
ADD build/libs/${JAR_NAME} /app/${JAR_NAME}
CMD java $JAVA_OPTS -jar /app/${JAR_NAME}      