FROM openjdk:22
COPY target/ItauSeguros-0.0.1.jar /ItauSeguros-0.0.1.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "/ItauSeguros-0.0.1.jar"]