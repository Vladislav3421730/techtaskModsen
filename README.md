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
При входе в систему через ```http://localhost:8080/auth/login``` вам выдаётся token, который надо вставить в header Authorization в Bearer token.
Можно протестировать api благодаря Postman локально на компьютере или открыв по ссылке:<br>
```bash
https://solar-trinity-124167.postman.co/workspace/3b8ccce8-e323-47dd-a41b-98d3d65f7c8b/collection/29171033-f2a03f26-03ee-4881-9758-d37fa832767b?action=share&source=collection_link&creator=29171033
```
Swagger документацию можно посмотреть по ссылке ниже<br>
```bash
http://localhost:8080/swagger-ui/index.html
```
Дополнительное примечание: долгое время пытался написать на микросервисах, но к сожалению возникли проблемы с конфигурацией Spring Cloud,Spring Security и Docker. 
В ходе выполнения изучил такие темы как Микросервисная архитектура, Spring Cloud Eureka Server, Spring Cloud Eureka Client, Api GateWay.
