FROM os-java-maven-base
WORKDIR /tmp/app
COPY apache-activemq-5.14.5-bin.tar.gz .
RUN tar -xvzf apache-activemq-5.14.5-bin.tar.gz
RUN ln -s apache-activemq-5.14.5 apache-activemq
COPY log4j.properties apache-activemq/conf
EXPOSE 61616 5672 61613 1883 61614 8161
RUN mkdir apache-activemq-5.14.5/tmp
RUN chmod 777 apache-activemq-5.14.5/tmp
ENTRYPOINT sh -c "apache-activemq/bin/activemq console"
#CMD ["/bin/bash"]