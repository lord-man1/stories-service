# Stories Serviced for Place2Rest

## STORY ENTITY

    "id": 3da400a9-bee5-4ff6-8a1b-926ca71e0b66,
    "name": "Скидки!",
    "type": "IMAGE",
    "publish_date": "2021-01-01",
    "expiration_date": "2021-01-01T00:00:00+00:00",
    "status": "ACTUAL"

## COMPILE AND RUN

    mvn clean package

mvn clean удаляет старый зависимости/джарники mvn package создает jar file, который будет лежать в папке target, которая
автоматически будет сгенерирована мавеном далее нужно спуститься в эту папку и положить файл .env

     cp *РАСПОЛОЖЕНИЕ .env ФАЙЛА*/.env *НЫНЕШНЯЯ ДИРЕКТОРИЯ*/target

Далее нужно запустить джарник(в него уже подгружены все зависимсти)

    cd target/

Спускаемся в папку для удобства использования

Дальше запускаем джарник

    java -jar StoriesService-*.jar 

Имя джар файла формируется из пом файла первая часть названия это - <artifactId>RestaurantService</artifactId>
и звездочка это версия - <version>0.0.1</version>

для версии 0.0.1 запуск кода выглядит так

    java -jar StoriesService-0.0.1.jar

Тут же должны задаваться параметры для jwm и тд

## .env

    server_port=9100
    conn_timeout=20000
    conn_url=jdbc:postgresql://localhost:5432/stories_service
    conn_login=localhost
    conn_password=localhost
    conn_min_idle=10
    conn_max=15
    user_service_valid=http://127.0.0.1:8091
    path_picture=/home/valery/Desktop/cat
    max_threads=10
    redis_host=
    redis_port=
    profile= DEV/PROD режим работы сервиса
