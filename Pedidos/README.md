# FarmaPlus Pedidos

Microservicio de gestión de pedidos para FarmaPlus.

## Tecnologías
- Java 17
- Spring Boot 3.2.0
- Spring Data JPA
- MySQL / H2 (runtime)
- Lombok
- Maven

## Configuración
Ajuste `src/main/resources/application.properties` con sus credenciales de base de datos.

Puerto por defecto: `8883`.

## Endpoints principales
- `POST /api/ecommerce/pedido` - Crear pedido.
- `GET /api/ecommerce/pedido` - Listar pedidos (opcional `?q=` para búsqueda).
- `GET /api/ecommerce/pedido/{id}` - Obtener pedido por id.
- `GET /api/ecommerce/pedido/numero/{numero}` - Obtener pedido por número.
- `PUT /api/ecommerce/pedido/{id}/estado?estado=ENVIADO` - Actualizar estado.
- `DELETE /api/ecommerce/pedido/{id}` - Eliminar pedido.

## Uso local
1. Compilar: `mvn clean package`
2. Ejecutar: `mvn spring-boot:run`
3. Importar el proyecto en IntelliJ o Eclipse — la estructura está preparada para Maven.

## Observaciones
- Los archivos `.java` están en UTF-8.
- El proyecto incluye H2 para pruebas locales y MySQL para producción.
