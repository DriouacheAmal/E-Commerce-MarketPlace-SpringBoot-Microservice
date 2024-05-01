FROM adoptopenjdk/openjdk11:alpine-jre
VOLUME /tmp
ADD target/E-Commerce-MarketPlace-SpringBoot-Microservice.jar app.jar
ENTRYPOINT ["java", "-jar", "/app.jar"]
