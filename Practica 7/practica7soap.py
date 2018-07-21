import suds

client = suds.client.Client("http://localhost:7777/ws/AcademicoWebService?wsdl")
result = client.service.getAsignatura("1")
result2 = client.service.getProfesor(1)
result3= client.service.getAllEstudiante()

print "1- Listar Estudiantes\n"
print result3
print "\n2- Consultar profesor 1\n"
print result2
print "\n3- Consultar una asignatura\n"
print result