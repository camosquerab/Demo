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

## Descripción General
Esta API está diseñada para gestionar productos y categorías. Requiere autenticación de usuario utilizando tokens JWT.

## URL Base
Reemplaza `{{base_url}}` con la URL base real de tu API, por ejemplo, `http://localhost:8080`.

---

## Registro de Usuario

Primero, crea un usuario con el rol de **ROLE_ADMIN**.

### Registrar Usuario
**Método:** `POST`  
**Endpoint:** `{{base_url}}/auth/register`

### Cuerpo de la Solicitud
```json
{
    "username": "newadmin",
    "password": "newpassword",
    "role": "ROLE_ADMIN"
}
```
## Registro de Usuario

Primero, crea un usuario con el rol de **ROLE_ADMIN**.

### Registrar Usuario
**Método:** `POST`  
**Endpoint:** `{{base_url}}/auth/register`

### Cuerpo de la Solicitud
```json
{
    "username": "newadmin",
    "password": "newpassword",
    "role": "ROLE_ADMIN"
}
```
### Inicio de Sesión

**Método:** `POST`  
**Endpoint:** `{{base_url}}/auth/login`

### Cuerpo de la Solicitud
```json
{
    "username": "newadmin",
    "password": "newpassword"
}
```
### Crear Categoría

Una vez que tengas el token, puedes crear una categoría.

**Método:** `POST`  
**Endpoint:** `{{base_url}}/category/`

### Cuerpo de la Solicitud
```json
{
    "categoryName": "SERVERS1",
    "description": "Hardware servers for various applications",
    "picture": "FILE"
}
```
### Crear Producto

Luego, puedes crear un producto asociado a una categoría.

**Método:** `POST`  
**Endpoint:** `{{base_url}}/product/`

### Cuerpo de la Solicitud
```json
{
    "productName": "Dell PowerEdge R740",
    "supplierID": 1001,
    "categoryID": 1,
    "quantityPerUnit": "1 unit",
    "unitPrice": 2500.00,
    "unitsInStock": 10,
    "unitsOnOrder": 5,
    "reorderLevel": 3,
    "discontinued": false
}
```
### Buscar Producto por ID

Para buscar un producto específico, puedes usar el ID del producto.

- **Método:** `GET`  
- **Endpoint:** `{{base_url}}/product/products/1/`

---

### Lista de Productos

Si deseas obtener la lista de todos los productos, utiliza el siguiente endpoint.

- **Método:** `GET`  
- **Endpoint:** `{{base_url}}/product/products/`

---

### Notas

Para las últimas cuatro solicitudes (Crear Categoría, Crear Producto, Buscar Producto por ID y Lista de Productos), asegúrate de incluir el token Bearer en el encabezado de la solicitud para autenticarte.
