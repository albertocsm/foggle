FROM java:8-jre-alpine

ADD ./target/foggle-1.0-SNAPSHOT.jar /opt/foggle.jar

EXPOSE 8080
ENTRYPOINT ["/usr/bin/java","-jar","/opt/foggle.jar"]