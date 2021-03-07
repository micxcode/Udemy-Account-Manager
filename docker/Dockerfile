FROM docker.artifactory.squaretrade.com/developer/base:3.0.0
ENV SERVICE_NAME=udemy-account-manager
ENV SERVICE_PORT=8000
EXPOSE 8000
EXPOSE 8003
ADD build/libs/Udemy-Account-Manager*.jar udemy-account-manager.jar
RUN bash -c 'touch /udemy-account-manager.jar'
ENTRYPOINT ["dockerize", \
"-wait", "tcp://activemq:8161", \
"-timeout", "300s"]
CMD ["java", \
"-Xdebug", \
"-Xrunjdwp:transport=dt_socket,server=y,suspend=n,address=8003", \
"-Djava.security.egd=file:/dev/./urandom", \
"-jar", \
"/udemy-account-manager.jar"]