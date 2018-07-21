import unirest
import json;

response = unirest.get("http://localhost:4567/rest/estudiantes/", headers={ "Accept": "application/json" })
response2 = unirest.get("http://localhost:4567/rest/estudiantes/1", headers={"Accept": "application/json"})
response3 = unirest.post("http://localhost:4567/rest/estudiantes/", headers={"Accept": "application/json", "Content-Type" : "application/json"},params=json.dumps({ "nombre": "Emilio", "correo": "emilio05@gmail.com", "carrera": "ISC"}))

print "\n1- Listar todos los estudiantes \n"
print response.body
print "\n2- Consultar un estudiante \n"
print response2.body
print "\n3- Crear un estudiante \n"
print response3.body