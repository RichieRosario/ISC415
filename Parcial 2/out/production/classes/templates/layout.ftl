<html>
    <head>
        <title>Una Red Social</title>
        <meta charset="utf-8">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.1.0/css/bootstrap.min.css">
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
        <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.0/umd/popper.min.js"></script>
        <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.1.0/js/bootstrap.min.js"></script></head>
    <body>


    <nav class="navbar navbar-expand-sm bg-dark navbar-dark">
        <div class="container-fluid">
            <div class="navbar-header">
                <a class="navbar-brand" style="color:white">2do Parcial</a>
            </div>
            <#if admin == false>
            <ul class="nav navbar-nav" style="text-decoration:none">
                <li class="navbar-item"><a href="/" class="nav-link"  >Inicio</a></li>
                <li class="navbar-item"><a href="/profile/${usuario.getUsername()}" class="nav-link"  >Perfil</a></li>
                <li class="navbar-item"><a href="/logout" class="nav-link">Cerrar Sesión</a></li>
            </ul>
            <#else>

                <ul class="nav navbar-nav" style="text-decoration:none">
                <li class="navbar-item"><a href="/" class="nav-link" >Inicio</a></li>
                <li class="navbar-item"><a href="/profile/${usuario.getUsername()}" class="nav-link" >Perfil</a></li>
                    <li class="navbar-item"><a href="/usuarios" class="nav-link" >Gestionar Usuarios</a></li>
                <li class="navbar-item"><a href="/logout" class="nav-link">Cerrar Sesión</a></li>
            </ul>

            </#if>

        </div>
    </nav>
    </body>


    <footer class="main-footer bg-dark text-white" style="position:fixed;height:32px;width:100%;bottom:0;z-index:9;">
        <div class="container">

            <p>Una Red Social - Ricardo y Emilio &copy; 2018 </p>
        </div>
    </footer>
</html>