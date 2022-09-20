all: build start

start:
	docker-compose up

build:
	chmod 700 ./mvnw
	./mvnw install -DskipTests

stop:
	docker-compose down