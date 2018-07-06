<html>

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
    <div class="container-fluid">
        <div class="modal-body">
        <form class="form" method="POST" action="/modificar">
            <div class="form-group">
                <label for="matricula">Matricula</label>
                <input id="matricula" 
                type="text"
                class="form-control"
                       name="matricula"
                       placeholder="88888888" title="XXXXXXXX" required pattern="[0-9]{8}"
                       value="${matricula?string["0"]}"">
            </div>
            <div class="form-group">
                <label for="nombre">Nombre</label>
                <input type="text"
                       class="form-control"
                       id="nombre"
                       name="nombre"
                       placeholder="Jose"
                       value="${nombre}">
            </div>

            <div class="form-group">
                <label for="apellido">Apellido</label>
                <input type="text"
                       class="form-control"
                       id="apellido"
                       name="apellido"
                       placeholder="Perez"
                       value="${apellido}">
            </div>


            <div class="form-group">
                <input type="hidden"
                       class="form-control"
                       id="indice"
                       name="indice"
                       value="${indice}">
            </div>


            <div class="form-group">
                <label for="telefono">Telefono</label>
               <input id="telefono" type="text" class="form-control" name="telefono" placeholder="888-888-8888" title="XXX-XXX-XXXX" required pattern="[0-9]{3}-[0-9]{3}-[0-9]{4}"
               value="${telefono}">
            </div>
            <div class="modal-footer">
            <button type="submit" class="btn btn-default" style="border-color: lightgray"><a href="/">Volver</a></button>
            <button type="submit" class="btn btn-default" style="border-color: lightgray">Guardar cambios</button>
            </div>
        </form>
    </div></div>
</body>
</html>
