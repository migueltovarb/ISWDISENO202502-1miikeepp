# CRUD de Vehículos — Instrucciones rápidas

## Requisitos
- MongoDB (local o Docker)
- Java 17 y Maven

## Levantar MongoDB
- Docker: `docker run -d --name mongo -p 27017:27017 -e MONGO_INITDB_DATABASE=vehiculosdb mongo:6`
- Local: `mongod --dbpath C:\\data\\db`

## Ejecutar la API
- PowerShell: `$env:MONGODB_URI = 'mongodb://localhost:27017/vehiculosdb'; mvn -f api/pom.xml spring-boot:run`
- API en `http://localhost:8080` (endpoint `http://localhost:8080/api/vehicles`)

## Ejecutar el Front
- Abrir `frontend/index.html` directamente
- Opcional: `cd frontend && python -m http.server 5500` y abrir `http://localhost:5500`

## Nota
- Si ves “Error 500” en el front, verifica que MongoDB esté corriendo y accesible en el URI configurado.
