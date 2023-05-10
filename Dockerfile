FROM tomcat:10.1.8
COPY /target/rndgif.war /usr/local/tomcat/webapps/
CMD ["catalina.sh", "run"]
EXPOSE 8080