# Habr дайджест (Рекомендательный сервис)
Основная идея проекта заключается в парсинге постов хабра и отбор постов с помощью рекомендательного сервиса который использует заранее введеные пользовательские данные.
Проект будет состоять из двух сервисов которые можно делать раздельно. 
# Сервис парссинга + ендпоинты для фронта
### Стек: Akka-Http, scala-scraper
Собственно сервис который будет парсить и класть посты в базу + отдавать эти данные. 
P.S. Возможно что хабр даст ключ и будет возможность использовать их клевое апи
# Рекомендательный сервис 
### Стек: python, pandas, word2vec?
Скорее всего для этого сервиса будет использоваться питон так как он содержит необходимые библиотеки для работы с мат. статом.
Сервис будет собирать количество комментариев апвоутов с помощью pandas и возвращать какой-то коэф. Либо использовать word2vec.
P.S. Опыта написания таких сервисов у меня нет. Так что в крайнем случае замокаю этот сервис
# Front и Android
### Стек Front: Scala.js, react, react-bootstrap, mobx, akka-stream
### Стек Android: Dagger, Android-Scala, Moxy, akka-stream
В идеале написать клиентские части который будет опрашивать пользователя о его предпочтениях(Выбор категорий, ключевые слова и т.д). Основная задача котую хотелось бы решить в этом разделе переиспользование клиент серверной прослойки между js и Android c с использованием akka-stream.

# Сборка проекта
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
# Ссылки
[пример android с akka stream](https://www.youtube.com/watch?v=kGH0mbjA2_U) 
[то как планирую писать фронт](https://www.youtube.com/watch?v=FuT1z3GbbC4 ) +
https://github.com/typesafehub/akka-js 
[рекомендательная система](https://www.youtube.com/watch?v=3MEe5IzBJk4)
[то чем будем парсить хабр](https://github.com/ruippeixotog/scala-scraper)

