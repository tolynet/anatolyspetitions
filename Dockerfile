FROM tomcat:latest
RUN mv /usr/local/tomcat/webapps /usr/local/tomcat/olderwebapps
RUN mv /usr/local/tomcat/webapps.dist /usr/local/tomcat/webapps
ADD target/*.war /usr/local/tomcat/webapps/anatolyspetitions.war
EXPOSE 9090
CMD ["catalina.sh", "run"]