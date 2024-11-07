# techtaskModsen 
Для начала необходимо скопировать проект себе локально на компьютер<br>
```bash
git clone https://github.com/Vladislav3421730/techtaskModsen
```
Затем зайти в паку LibraryApi и в терминале написать команду<br>
```bash
docker compose up
```
Немного не понял как именно должна проходить авторизация через Bearer токен, поэтому сделал разделение по ролям, а именно USER и ADMIN
Пользователю с ролью ADMIN доступны все эндпоинты, а пользвоателю с ролью USER доступны только <br>
```bash
http://localhost:8080/auth/login
http://localhost:8080/auth/register
http://localhost:8080/book/get
http://localhost:8080/book/get/{id}
http://localhost:8080/book/get/{isbn}
http://localhost:8080/library/get/free
http://localhost:8080/library/get
http://localhost:8080/library/get/{book_status_id}
```
Неавторизованному пользователю доступны следующие эндпоинты<br>
```bash
http://localhost:8080/auth/login
http://localhost:8080/auth/register
```
При входе в систему через ```http://localhost:8080/auth/login``` вам выдаётся token, который надо вставить в header Authorization в Bearer token<br>
Дополнительное примечание: долгое время пытался написать на микросервисах, но к сожалению возникли проблемы с конфигурацией Spring Cloud,Spring Security и Docker. 
В ходе выполнения изучил такие темы как Микросервисная архитектура, Spring Cloud Eureka Server, Spring Cloud Eureka Client, Api GateWay.
