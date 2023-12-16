The algorithm is selected using the -t option and key size using the -b option. The following commands illustrate:

ssh-keygen -t rsa -b 4096
ssh-keygen -t dsa
ssh-keygen -t ecdsa -b 521
ssh-keygen -t ed25519


http://localhost:8080/api/v1/swagger-ui/index.html


--- docker

crear la imagen de la app java
docker build -t my_client.app:v1 .


docker run --name mysql -p 3608:3606 -e MYSQL_ROOT_PASSWORD=password -e MYSQL_DATABASE=course mysql

docker run --name mysql -p 3610:3306 -e MYSQL_ROOT_PASSWORD=password -e MYSQL_DATABASE=course mysql

docker run --name security -p 8082:8081 spring-app:v1


para crear la red es
docker network connet <nombre_red> <contenedor>
docker network connet my-custom-red spring-mysql

en la url de nuestro spring
agregamos el nombre del contenedor de mysql y su puerto normal
jdbc:mysql://mysql:3306/course
cotenedor:puerto;


validar si tenemos docker compose instalado
docker-compose

mvn clean install package -DskipTests

para ejecutar nuestro compose 
docker-compose up --build -d

