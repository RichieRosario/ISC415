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


    <div class="container" style="margin-top:80px">
        <button type="button" class="btn btn-primary" data-toggle="modal" data-target="#agregar">
            Agregar Estudiante
        </button>

        <div class="modal" id="agregar">
            <div class="modal-dialog">
                <div class="modal-content">

                    <div class="modal-header">
                        <h4 class="modal-title">Agregar Estudiante</h4>
                        <button type="button" class="close" data-dismiss="modal">&times;</button>
                    </div>

                    <!-- Modal body -->
                    <div class="modal-body">
                        <form class="form" method="POST" action="/">
                            <div class="form-group">
                                <label for="matricula">Matricula</label>
                                <input id="matricula"
                                       type="text"
                                       class="form-control"
                                       name="matricula"
                                       placeholder="88888888" title="XXXXXXXX" required pattern="[0-9]{8}">
                            </div>
                            <div class="form-group">
                                <label for="nombre">Nombre</label>
                                <input type="text"
                                       class="form-control"
                                       id="nombre"
                                       name="nombre"
                                       placeholder="Jose">
                            </div>

                            <div class="form-group">
                                <label for="apellido">Apellido</label>
                                <input type="text"
                                       class="form-control"
                                       id="apellido"
                                       name="apellido"
                                       placeholder="Perez">
                            </div>

                            <div class="form-group">
                                <label for="telefono">Telefono</label>
                                <input id="telefono" type="text" class="form-control" name="telefono" placeholder="888-888-8888" title="XXX-XXX-XXXX" required pattern="[0-9]{3}-[0-9]{3}-[0-9]{4}">
                            </div>




                    </div>

                    <!-- Modal footer -->
                    <div class="modal-footer">
                        <button type="button" class="btn btn-danger" data-dismiss="modal">Cerrar</button>
                        <button type="submit" class="btn btn-primary active">Agregar</button>
                    </div>
                    </form>
                </div>
            </div>
        </div>

        <br>
        <br>

        <div class="table-responsive table-bordered table-striped" style="padding-right: -50px;">
        <table class="table">
            <thead>
            <tr>
                <th>Matricula</th>
                <th>Nombre</th>
                <th>Apellido</th>
                <th>Telefono</th>
                <th>Acciones</th>
            </tr>
            </thead>

            <#list lista as estudiante>
            <tr>
        <td>${estudiante.obtenerMatricula()?string["0"]}</td>
        <td>${estudiante.obtenerNombre()}</td>
        <td>${estudiante.obtenerApellido()}</td>
        <td>${estudiante.obtenerTelefono()}</td>
            <td>
                <div class="btn-group" role="group">
            <button type="button" class="btn btn-default btn-xs" style="border-color: lightgray"><a
                    href="/consultar?indice=${estudiante_index}">Consultar</a></button>
            <button type="button" class="btn btn-default btn-xs" style="border-color: lightgray"><a
                    href="/modificar?indice=${estudiante_index}" >Modificar</a></button>
            <button type="button" class="btn btn-default btn-xs" style="border-color: lightgray"><a
                    href="/eliminar?indice=${estudiante_index}">Eliminar</a></button>
                </div>
            </td>
    </tr>

            </#list>
        </table>
        </div>
        </div>
    </body>
</html>