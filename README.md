# HabrDigest

### CouchDb
start couch db
```
cd ./docker 
docker-compose up
```

check is couch db start

```curl http://127.0.0.1:5984/```

also 

```sbt dockerComposeUp ```

### ScalaJS
for run front
```
sbt fastOptJS::webpack

sbt fastOptJS
```

and share static with ```index.html```

### CheckList
- Парсит сообщения с habr +
- Отдает по akka-http +
- Собирается bundle react +
- Обернуть sharding db в рест -
- Сделать репитбл PostActor -
- Сделать старт из одного dockerFile -

