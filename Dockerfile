FROM openjdk:19-jdk-alpine
ADD gwrteam.jar .
EXPOSE 443
CMD java -jar gwrteam.jar