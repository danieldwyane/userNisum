# userNisum

* Iniciar aplicación: 
```bash
mvn spring-boot:run
```

* Importar en postman la colección "UserNisum.postman_collection" ubicada en la raíz del proyecto
    * También se puede abrir la ruta swagger: http://localhost:8080/user/swagger-ui/

* Enviar la petición llamada "create" para crear usuario y que te genere el token

* Abrir la petición "findAll" y agregar o modificar el valor de la cabecera "Authorization" por el token nuevo y enviar petición; de lo contrario la petición será denegada

* Si se quiere obtener token nuevo abrir la petición "login" y en el body enviar el key "user" con el valor del email registrado(ya que es único) y el key "password" (Nota: Quitar o limpiar Cookies en el postman al momento de enviar petición "login")

* Ejecutar para lanzar pruebas unitarias y obtener reporte JaCoCo:

```bash
mvn test
```
o
```bash
mvn clean install
```
