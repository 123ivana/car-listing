#Stage 1
# initialize build and set base image for first stage
FROM maven:3.8.2-eclipse-temurin-17 as stage1
# speed up Maven JVM a bit
ENV MAVEN_OPTS="-XX:+TieredCompilation -XX:TieredStopAtLevel=1"
# set working directory
WORKDIR /opt/car-listing
# copy just pom.xml
COPY pom.xml .
COPY /car-listing-commons/pom.xml  ./car-listing-commons/
COPY /car-listing-producer/pom.xml ./car-listing-producer/
COPY /car-listing-consumer/pom.xml ./car-listing-consumer/

RUN mvn dependency:go-offline -projects car-listing-commons,car-listing-producer

COPY ./car-listing-producer/src ./car-listing-producer/src
COPY ./car-listing-commons/src ./car-listing-commons/src

RUN mvn clean install -projects car-listing-commons,car-listing-producer -Dmaven.test.skip=true
#Stage 2
# set base image for second stage
FROM eclipse-temurin:17
# set deployment directory
WORKDIR /opt/car-listing
# copy over the built artifact from the maven image
COPY --from=stage1 /opt/car-listing/car-listing-producer/target/car-listing-producer.jar /opt/car-listing

ENTRYPOINT ["java","-jar","/opt/car-listing/car-listing-producer.jar"]

