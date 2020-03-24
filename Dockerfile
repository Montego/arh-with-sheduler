FROM mcr.microsoft.com/java/jdk:8u181-zulu-debian9
#ENV JAVA_APP_JAR arh-with-sheduler-0.0.1-SNAPSHOT.jar

ADD /target/arh-with-sheduler-0.0.1-SNAPSHOT.jar arh-with-sheduler-0.0.1-SNAPSHOT.jar
#russian utf-8
#ENV LANG C.UTF-8
#ENV LC_ALL C.UTF-8

ENTRYPOINT ["java","-jar","arh-with-sheduler-0.0.1-SNAPSHOT.jar"]

#EXPOSE 8085

