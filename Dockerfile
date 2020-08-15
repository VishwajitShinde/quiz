# Alpine Linux with OpenJDK JRE
FROM openjdk:8-jre-alpine

EXPOSE 9191

# copy WAR into image
COPY ./target/quiz-service.jar /quiz-service.jar 

# run application with this command line 
CMD ["/usr/bin/java", "-jar", "-Dspring.profiles.active=default", "server.ssl.key-store=G:/TECH_LEARNING/spring-boot/ssl-server.jks", "/quiz-service.jar"]



# -------MySql Setup Steps Added here for sample -----------
# docker pull mysql:latest
# docker images
# docker run -p 6033:3306 --name docker-mysql-db --env MYSQL_ROOT_PASSWORD=root --env MYSQL_PASSWORD=root --env MYSQL_DATABASE=test --env MYSQL_USER=sa -d mysql:latest  
# -- Now we can check by logging in to MySQL. 
# docker exec -it docker-mysql-db bash;
# docker exec -i docker-mysql mysql -uroot -proot test < G:/TECH_LEARNING/spring-boot/quiz/src/main/resources/RolesInsert.sql
# -- To Get Details Run Below Commands 
# Please Change application.properties file for : spring.datasource.url=jdbc:mysql://docker-mysql-db:3306/test
# -- in above line url docker-mysql-db is container name, 3306 is port and test is database name provided in docker run command
# # -------------------------------- 
#    For Application ( spring boot ) 
# # --------------------------------
#  docker build -t spring-boot-test/quiz-servie:0.0.5 .
# -- In Above command (Dot represents that the Dockerfile is present in the current directory) and 0.0.5 indicates pom version in tag 
# docker image ls
# docker run -d -p 9191:9191 --name spring-boot-test/quiz-servie:0.0.5 --link docker-mysql-db:mysql 
# ----------------------------------------------------------------
#               Some Useful Commands          
# ----------------------------------------------------------------
# docker container logs docker-mysql-db
# docker container ls => List the active containers
# docker container stop <container-name> => Stops the container : docker-mysql-db 
# docker container rm <container-name> => Remove the stopped container : docker-mysql-db 
# ------------------------
# Example References : For Mysql and Spring boot 
# https://dzone.com/articles/all-about-hibernate-manytomany-association 
# https://medium.com/thecodefountain/develop-a-spring-boot-and-mysql-application-and-run-in-docker-end-to-end-15b7cdf3a2ba 
# https://www.javainuse.com/devOps/docker/docker-mysql
# source code : https://github.com/sanjoy-sust/book-manager 
#
#
#