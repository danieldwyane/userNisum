# userNisum

1- Iniciar aplicación: 
```bash
mvn spring-boot:run
```

2- Importar en postman la colección "UserNisum.postman_collection" ubicada en la raíz del proyecto
    2.1- También se puede abrir la ruta swagger: http://localhost:8080/user/swagger-ui/

3- Enviar la petición llamada "create" para crear usuario y que te genere el token

4- Abrir la petición "findAll" y agregar o modificar el valor de la cabecera "Authorization" por el token nuevo y enviar petición; de lo contrario la petición será denegada

5- Si se quiere obtener token nuevo abrir la petición "login" y en el body enviar el key "user" con el valor del email registrado(ya que es único) y el key "password" (Nota: Quitar o limpiar Cookies en el postman al momento de enviar petición "login")

6- Ejecutar para lanzar pruebas unitarias y obtener reporte JaCoCo:

```bash
mvn test
```
o
```bash
mvn clean install
```
