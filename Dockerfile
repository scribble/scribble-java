FROM maven:latest
RUN apt-get update && apt-get install vim tree less -y

# RUN git clone https://github.com/scribble/scribble-java.git \
COPY . /scribble-java
RUN cd scribble-java \
    && mvn install -Dlicense.skip=true \
    && mv scribble-dist/target/scribble-dist* /
RUN unzip scribble-dist*
RUN cp scribble-java/scribble-core/src/test/scrib/tmp/Test.scr .
RUN chmod 755 scribblec.sh

ENTRYPOINT ["/bin/bash"]
