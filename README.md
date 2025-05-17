# Trackademic

### Grupo
- **Juan David Acevedo**
- **Jose Manuel Cardona**
- **Andres Chamorro**
- **Oscar Muñoz**
- **Juan Camilo Muñoz**

Antes de ejecutar el proyecto, asegúrate de tener instalado PostgreSQL y haber creado la base de datos `trackademic_db` con el siguiente comando:
```sql
create user track_user with password '123456';
create database trackademic_db owner track_user;
grant connect on database trackademic_db to track_user;
```
Luego, cambia el archivo `application.properties` en `src/main/resources` para que cuando corras por primera vez la aplicación, se creen las tablas automáticamente. Cambia la propiedad `spring.sql.init.mode` a `always`:
```properties
spring.sql.init.mode=always
```
Para las proximas ejecuciones, puedes cambiarla a `never` para evitar que se creen las tablas nuevamente:
```properties
spring.sql.init.mode=never
```