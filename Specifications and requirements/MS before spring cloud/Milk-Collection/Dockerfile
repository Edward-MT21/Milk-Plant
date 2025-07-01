#Start with a base image containing Java runtime
FROM openjdk:21-jdk-slim

# MAINTAINER instruction is deprecated in favor of using label
# MAINTAINER eazybytes.com
#Information around who maintains the image
LABEL "org.opencontainers.image.authors"="Edward Malte"

# Add the application's jar to the image
COPY target/Milk-Collection-0.0.1-SNAPSHOT.jar Milk-Collection-0.0.1-SNAPSHOT.jar

# execute the application
ENTRYPOINT ["java", "-jar", "Milk-Collection-0.0.1-SNAPSHOT.jar"]