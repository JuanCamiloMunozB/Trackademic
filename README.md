# Trackademic - Sistema de Gestión de Notas Académicas

![Spring Boot](https://img.shields.io/badge/Spring_Boot-6DB33F?style=for-the-badge&logo=spring&logoColor=white)
![MongoDB](https://img.shields.io/badge/MongoDB-47A248?style=for-the-badge&logo=mongodb&logoColor=white)
![PostgreSQL](https://img.shields.io/badge/PostgreSQL-316192?style=for-the-badge&logo=postgresql&logoColor=white)

Aplicación Spring Boot para gestionar planes de evaluación, notas académicas y comentarios colaborativos, desarrollada como proyecto final para la materia *Sistemas Intensivos en Datos II*.

## 👥 Equipo de Desarrollo
- **Juan David Acevedo**
- **Jose Manuel Cardona**
- **Ricardo Andrés Chamorro**
- **Oscar Stiven Muñoz**
- **Juan Camilo Muñoz**

## 📌 Características Principales
- **Persistencia Poliglota**: 
  - **PostgreSQL**: Datos estructurados (empleados, facultades, grupos).
  - **MongoDB**: Datos semi-estructurados (planes de evaluación, comentarios, semestres).
- **Documentacion Detallada**: Lo puedes encontrar en [docs/Documentacion Proyecto final SIDII](docs/Documentación%20Proyecto%20final%20SIDII.pdf).

## 🛠 Tecnologías Utilizadas
- **Backend**: Spring Boot 3.4.5
- **Bases de Datos**: MongoDB y PostgreSQL
- **Herramientas**: Lombok, Spring Data JPA, Spring Data MongoDB
- **Despliegue**: Bases de datos alojadas en MongoDB Atlas y Railway (PostgreSQL).

## 🗄 Modelado de Datos

Aqui puedes ver anexado ambos modelos utilizados en el proyecto:  

### Modelo Relacional (PostgreSQL)
![Diagrama relacional](docs/models/RelationalModel.jpeg)

### Modelo de Documentos (MongoDB)
![Diagrama de documentos](docs/models/MongoModel.jpg)

## ⚙ Configuración y Acceso a Bases de Datos

### Credenciales de Conexión (application.properties)
```properties
# MongoDB
spring.data.mongodb.uri=mongodb://mongo:IwJvnHxCPXRMXumoDbkEAeRhBjyyUStF@mainline.proxy.rlwy.net:52476
spring.data.mongodb.database=trackademic

# PostgreSQL
spring.datasource.url=jdbc:postgresql://caboose.proxy.rlwy.net:45135/railway
spring.datasource.username=postgres
spring.datasource.password=cQNaFJywkdHPzUFmlfauUSQRSXSiAphl
```

### Conexión Externa a las Bases de Datos

#### 1. **MongoDB** (usando `mongosh`):
```bash
mongosh 'mongodb://mongo:IwJvnHxCPXRMXumoDbkEAeRhBjyyUStF@mainline.proxy.rlwy.net:52476/trackademic?authSource=admin'
use trackademic
```

#### 2. **PostgreSQL** (usando `psql`):
```bash
psql -h caboose.proxy.rlwy.net -p 45135 -U postgres -d railway
```
Cuando solicite la contraseña, ingrese:  
`cQNaFJywkdHPzUFmlfauUSQRSXSiAphl`

Tambien puedes conectarte con PgAdmin:

- Los pasos a seguir los puedes encontrar en [docs/Conexion pgAdmin](docs/Conexion%20pgAdmin.pdf).

## 🚀 Ejecución de la Aplicación

1. **Clonar el repositorio**:
   ```bash
   git clone https://github.com/tu-usuario/Trackademic.git
   cd Trackademic
   ```

2. **Instalar dependencias**:
   ```bash
    # Opción 1: Usando el wrapper de Maven
    ./mvnw clean install
    # Opción 2: Con Maven instalado globalmente
    mvn clean install
    ```
    Asegúrate de tener Java 17 o superior instalado.

2. **Ejecutar con Maven**:
   ```bash
   # Opción 1: Usando el wrapper de Maven
   ./mvnw spring-boot:run

   # Opción 2: Con Maven instalado globalmente
   mvn spring-boot:run
   ```

3. **Acceder a la aplicación**:
   Abre tu navegador y dirígete a la siguiente URL:
   ```bash
   http://localhost:8080/Trackademic/
   ```
