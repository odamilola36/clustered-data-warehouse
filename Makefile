all: build-jar start

start:
	docker-compose up

build-jar:
	./mvnw clean compile

stop:
	docker-compose down