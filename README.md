# Хранилище файлов и их атрибутов

## Описание проекта

Данный проект представляет собой HTTP API, который выполняет роль хранилища файлов и их атрибутов.
***

## Стек технологий

- Backend: **Java** + **Spring Boot** + **Spring Data JPA** + **Spring Web**
- База данных: **PostgreSQL**
- Модульные тесты: **JUnit**
- Сборка проекта: **Maven Wrapper**
- Контейнеризация: **Docker**

***

## Требования

- Java 17
- Docker и Docker compose

***

## Инструкция по запуску через командную строку (cmd)

1. **Клонируйте репозиторий и перейдите в директорию проекта:**

   ```shell
   git clone https://github.com/KatNag/file-storage-service
   cd file-storage-service
   ```

2. **Сборка проекта с помощью Maven**

   Для Windows используйте
    ```shell
    mvnw.cmd clean package
    ```

3. **Запустите приложение**

   Создайте Docker-образ и запустите Docker-контейнер:

    1. **Создайте Docker-образ**:
       ```shell
       docker build -t file-storage-service .
       ```

    2. **Запустите Docker-контейнер**:
       ```shell
       docker-compose up
       ```

***

## Реализованные API-методы

### 1. Создание файла

- **Адрес**: `http://localhost:8080/api/files`
- **Метод**: `POST`
- **Примеры запроса**:
    - Пример 1:
    ```json
    {
      "title": "Sample File",
      "creationDate": "2024-08-09T12:00:00Z",
      "description": "This is a sample file.",
      "fileBase64": "SGVsbG8gd29ybGQ="
    }
    ```
    - Пример 2:
    ```json
    {
      "title": "Another file",
      "creationDate": "2023-08-01T12:00:00Z",
      "description": "This is another file",
      "fileBase64": "U29tZSBmaWxlIGNvbnRlbnQy"
    }
     ```
    - Пример 3:
    ```json
    {
      "title": "Third file",
      "creationDate": "2024-08-03T17:00:00Z",
      "description": "This is third",
      "fileBase64": "dsdf1231Dsqwedv1="
    }
    ```

- **Возвращаемое значение**: JSON c `id` созданного файла или сообщение об ошибке в случае ввода некорректных данных.

### 2. Получение файла по ID

- **Адрес**: `http://localhost:8080/api/files/{id}`
- **Метод**: `GET`
- **Пример запроса**:
    - ```http://localhost:8080/api/files/1```
- **Возвращаемое значение**:
    - **Формат данных**: JSON
    - Пример возвращаемого JSON:
    ```json
    {
      "id": 1,
      "title": "Sample File",
      "creationDate": "2024-08-09T12:00:00.000+00:00",
      "description": "This is a sample file.",
      "fileBase64": "SGVsbG8gd29ybGQ="
    }
    ```

    - Пример ошибки, если файл не найден:
    ```json
    {
      "error": "Файл не найден",
      "message": "Файл с заданным id: 10 не найден",
      "status": "NOT_FOUND",
      "timestamp": "2024-08-09T14:54:42.226729803"
    }
    ```

### 3. Получение списка всех файлов

- **Адрес**: `http://localhost:8080/api/files`
- **Метод**: `GET`
- **Параметры запроса**:
    - `page` (необязательный, значение по умолчанию: 0) — номер страницы.
    - `size` (необязательный, значение по умолчанию: 10) — количество элементов на странице.
    - `sortBy` (необязательный, значение по умолчанию: "creationDate") — поле для сортировки (по убыванию).
- **Примеры запроса**:
    - Пример с параметрами по умолчанию: `http://localhost:8080/api/files`
    - Пример с пользовательскими параметрами:`http://localhost:8080/api/files?page=1&size=2&sortBy=id`
- **Возвращаемое значение**:
    - Пример ответа:

``` json
{
    "totalPages": 1,
    "totalElements": 3,
    "size": 10,
    "content": [
        {
            "id": 1,
            "title": "Sample File",
            "creationDate": "2024-08-09T12:00:00.000+00:00",
            "description": "This is a sample file.",
            "fileBase64": "SGVsbG8gd29ybGQ="
        },
        {
            "id": 3,
            "title": "Third file",
            "creationDate": "2024-08-03T17:00:00.000+00:00",
            "description": "This is third",
            "fileBase64": "dsdf1231Dsqwedv1="
        },
        {
            "id": 2,
            "title": "Another file",
            "creationDate": "2023-08-01T12:00:00.000+00:00",
            "description": "This is another file",
            "fileBase64": "U29tZSBmaWxlIGNvbnRlbnQy"
        }
    ],
    "number": 0,
    "sort": {
        "empty": false,
        "sorted": true,
        "unsorted": false
    },
    "pageable": {
        "pageNumber": 0,
        "pageSize": 10,
        "sort": {
            "empty": false,
            "sorted": true,
            "unsorted": false
        },
        "offset": 0,
        "paged": true,
        "unpaged": false
    },
    "numberOfElements": 3,
    "first": true,
    "last": true,
    "empty": false
}
```