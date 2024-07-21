# restaurant-service

## Сборка

### Список аргументов

Файл `docker/Dockerfile` содержит в себе следующие аргументы:

| Имя аргумента      | Описание                                                    | Значение по умолчанию                |
|--------------------|-------------------------------------------------------------|--------------------------------------|
| REDHAT_REGISTRY    | Ссылка на репозиторий контейнеров Red Hat Container Catalog | registry.access.redhat.com           |
| MAVEN_REPOSITORY   | Ссылка на репозиторий Maven                                 | https://repo.maven.apache.org/maven2 |
| JAVA_BUILD_IMAGE   | Базовый образ Java JDK для сборки приложения                | ubi8/openjdk-17:1.17-1               |
| JAVA_RUNTIME_IMAGE | Базовый образ Java JRE для запуска приложения               | ubi8/openjdk-17-runtime:1.17-1       |

Значение аргумента можно переопределить с помощью параметра `--build-arg` в командах `docker build` и `buildah bud`.

### Сборка контейнера:

```commandline
buildah bud --no-cache -f docker/Dockerfile -t place2rest/stories-service:local .
```

ИЛИ

```commandline
docker build --no-cache -f docker/Dockerfile -t place2rest/stories-service:local .
```

## Запуск

### Список переменных окружения

Для запуска приложения необходимо задать переменные окружения указанные в README.md корня проекта

### Запуск контейнера

Запуск приложения по адресу http://localhost:5000:

```commandline
podman run -p 5000:5000 -e server_port=5000 place2rest/stories-service:local
```

ИЛИ

```commandline
docker run -p 5000:5000 -e server_port=5000 place2rest/stories-service:local
```
