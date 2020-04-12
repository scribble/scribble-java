FROM maven:3.6.3-jdk-14
RUN yum install -y unzip
COPY . /scribble-java
RUN cd scribble-java \
    && mvn install -Dlicense.skip=true \
    && mv scribble-dist/target/scribble-dist* /

RUN unzip scribble-dist*
RUN chmod 755 scribblec.sh

ENTRYPOINT ["./scribblec.sh"]
