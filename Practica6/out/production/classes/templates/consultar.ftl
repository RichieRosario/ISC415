<html>
    <head
        <head>
            <title>Práctica #2: CRUD Estudiante</title>
            <meta charset="utf-8">
            <meta name="viewport" content="width=device-width, initial-scale=1">
            <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.1.0/css/bootstrap.min.css">
            <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
            <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script> </head>
    <body>


    <nav class="navbar navbar-expand-sm bg-dark navbar-dark">
        <div class="container-fluid">
            <div class="navbar-header">
                <a class="navbar-brand" style="color:white">Práctica #2</a>
            </div>
            <ul class="nav navbar-nav" style="text-decoration:none">
                <li ><a href="/">Inicio</a></li>
            </ul>
        </div>
    </nav>
        <div class="modal-body">
            Matricula: ${matricula?string["0"]}
            <br>
            Nombre: ${nombre}
        <br>
            Apellido: ${apellido}
        <br>
            Telefono: ${telefono}
        <div class="modal-footer">
        <div class="btn-group" role="group">
            <button type="button" class="btn btn-default" style="border-color: lightgray"><a
                    href="/modificar?indice=${indice}">Modificar</a></button>
            <button type="button" class="btn btn-default" style="border-color: lightgray"><a
                    href="/eliminar?indice=${indice}">Eliminar</a></button>
        </div>
        </div>
        </div>
    </body>
</html>
