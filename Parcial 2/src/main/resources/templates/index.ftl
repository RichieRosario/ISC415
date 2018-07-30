<html>
 <head>
        <title>Una Red Social</title>
        <meta charset="utf-8">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.1.0/css/bootstrap.min.css">
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
        <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script> </head>
    <body>


    <nav class="navbar navbar-expand-sm bg-dark navbar-dark">
        <div class="container-fluid">
            <div class="navbar-header">
                <a class="navbar-brand" style="color:white">2do Parcial</a>
            </div>

            <ul class="nav navbar-nav " style="text-decoration:none">
                <form method="post"  action="/login"">
                <div class="form-inline">
                <li ><input type="text" name="userpost" id="userpost" class="form-control" style="width:92.5%" placeholder="Nombre de Usuario"/></li>
                <li><input type="password" name="passpost" id="passpost" class="form-control" style="width:92.5%" placeholder="Contraseña"/></li>
                    <button type="submit" class="btn btn-info btn-xs">Iniciar Sesión</button>

                </div>
                   </form>
            </ul>
        </div>
    </nav>
    </body>


    <footer class="main-footer bg-dark text-white" style="position:fixed;height:32px;width:100%;bottom:0;">
        <div class="container">

            <p>Una Red Social - Ricardo y Emilio &copy; 2018 </p>
        </div>
    </footer>
<br>
<body class="bg-light">
<div class="container" >

    <div class="row mr-auto">
        <div class="col-md-4">
    <h1>Crea tu cuenta</h1></div>

        <div class="col-md-6 offset-md-2 form-inline" style="margin-top:20%">
            <h2>Bienvenido a Una Red Social</h2>
                <h3>la red social que te ayuda a compartir con las personas que forman parte de tu vida. </h3></div>
    </div>

                <form method="post" class="container-fluid" action="/registrarse" style="margin-top:-25%;" >
                    <div class="form-group row">
                        <div class="col-md-6">
                            <input type="text" name="username" id="username" class="form-control" style="width:92.5%" placeholder="Nombre de Usuario"/>
                        </div>
                    </div>
                    <div class="form-group row">
                        <div class="col-md-6">
                            <input type="password" name="password" id="password" class="form-control" style="width:92.5%" placeholder="Nueva Contraseña"/>
                        </div>
                    </div>
                    <div class="form-group row form-inline">
                        <div class="col-auto">
                        <input type="text" name="nombre" id="nombre" class="form-control" style="width:110%" placeholder="Primer Nombre"/>
                            </div>

                        <div class="col-auto">
                        <input type="text" name="apellido" id="apellido" class="form-control" style="margin-left:27% !important;width:110%" placeholder="Apellido"/>
                        </div>
                    </div>
                    <div class="form-group row">
                        <div class="col-md-6">
                        <input type="text" name="email" id="email" class="form-control" style="width:92.5%" placeholder="Correo Electrónico"/>
                    </div>
                    </div>


                    <div class="form-group row">
                        <div class="col-md-6">
                            <input type="text" name="lugarresidencia" id="lugarresidencia" class="form-control" style="width:92.5%" placeholder="Lugar de Residencia"/>
                        </div>
                    </div>

                    <div class="form-group row">
                        <div class="col-md-6">
                            <input type="text" name="lugarnacimiento" id="lugarnacimiento" class="form-control" style="width:92.5%" placeholder="Lugar de Nacimiento"/>
                        </div>
                    </div>

                    <div class="form-group row">
                        <div class="col-md-6">
                        <input type="date" name="date" id="date" class="text-muted form-control" style="width:92.5%"/>
                    </div>
                    </div>

                    <div class="form-group row">
                        <div class="col-md-6">
                            <input type="text" name="lugartrabajo" id="lugartrabajo" class="form-control" style="width:92.5%" placeholder="Lugar de Trabajo"/>
                        </div>
                    </div>

                    <div class="form-group row">
                        <div class="col-md-6">
                            <input type="text" name="lugarestudio" id="lugarestudio" class="form-control" style="width:92.5%" placeholder="Lugar de Estudio"/>
                        </div>
                    </div>



                    <div class="form-group row">
                        <div class="col-md-6">
                            <label>Sexo:</label>
                    <input type="radio" name="sexo" value="M"> Masculino
                    <input type="radio" name="sexo" value="F" checked> Femenino
</div>
                        </div>

                    <button type="submit" class="btn btn-info btn-xs">Crear</button>


                </form>

</div>
<br>
</body>
</html>