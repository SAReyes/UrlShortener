FROM java:8

RUN mkdir -p /usr/src/app

WORKDIR /usr/src/app

COPY urlshortener-configuration/build/libs/urlshortener-configuration.jar app.jar

EXPOSE 8080

CMD ["java", "-jar", "app.jar"]

