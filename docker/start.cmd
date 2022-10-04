call cd ..

call mvn clean install -DskipTests -DskipUTs

call cd docker

call docker-compose up --build --detach

