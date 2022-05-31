FROM centos
RUN yum install -y java
ENV SERVICE_NAME=udemy-account-manager
ENV SERVICE_PORT=8000
EXPOSE 8000
EXPOSE 8003
VOLUME /tmp
ADD build/libs/Udemy-Account-Manager*.jar udemy-account-manager.jar
RUN sh -c 'touch /udemy-account-manager.jar'
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/udemy-account-manager.jar"]