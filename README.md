# **Proyecto de API con Spring Boot, JWT y PostgreSQL**

Este proyecto es una API desarrollada en **Java 21** utilizando **Spring Boot** y **JWT** para autenticación, con **PostgreSQL** como base de datos. La API incluye endpoints para la gestión de usuarios, productos y categorías.

---

## **Requisitos**

Antes de ejecutar el proyecto, asegúrate de tener los siguientes requisitos instalados:

- **Java 21**
- **Gradel**
- **Docker** (opcional para crear contenedores)
- **PostgreSQL** (para la base de datos)

---

## **Configuración de la base de datos**

Configura una base de datos PostgreSQL y ajusta las siguientes propiedades en el archivo `application.properties` para conectarte a la base de datos:

```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/intcomex
spring.datasource.username=root
spring.datasource.password=root
```

---


## Configura el usuario y la contraseña:
	•	Usuario: root
	•	Contraseña: root
Puedes cambiar estas credenciales según tu configuración y actualizar el archivo application.properties en consecuencia.

---


## Colección de Postman

Para facilitar las pruebas de la API, se incluye un archivo de colección de Postman que contiene ejemplos de solicitudes POST y GET para los diferentes endpoints.

Ubicación del archivo Postman

El archivo de la colección de Postman está ubicado en la raíz del proyecto como postman_collection.json.

Importar el archivo en Postman

	1.	Abre Postman.
	2.	Haz clic en Importar en la esquina superior izquierda.
	3.	Selecciona el archivo postman_collection.json ubicado en la raíz del proyecto.
	4.	Una vez importado, podrás ejecutar las solicitudes disponibles, como el registro, login, creación de productos, categorías, etc.

Variables en Postman

	•	{{base_url}}: La URL base de la API (http://localhost:8080).
	•	{{token}}: El token JWT que se obtiene tras hacer login. Debes copiar este token y configurarlo en las variables de entorno de Postman para realizar solicitudes que requieran autenticación.

Endpoints de la API

Autenticación

	•	POST /auth/register - Registro de nuevos usuarios.
	•	POST /auth/login - Autenticación y generación de token JWT.

Categorías

	•	POST /Category/ - Crear una nueva categoría.
	•	GET /Category/ - Listar todas las categorías.

Productos

	•	POST /Product/ - Crear un nuevo producto.
	•	GET /Products/ - Listar todos los productos.
	•	GET /Products/{id}/ - Obtener un producto por su ID.
