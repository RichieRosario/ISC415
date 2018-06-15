<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <title>Blog ISC415</title>
    <meta name="description" content="">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="robots" content="all,follow">

    <!-- Bootstrap CSS-->
    <link rel="stylesheet" href="/vendor/bootstrap/css/bootstrap.css">

    <!-- Font Awesome CSS-->
    <link rel="stylesheet" href="/vendor/font-awesome/css/font-awesome.min.css">
    <!-- Custom icon font-->
    <link rel="stylesheet" href="/css/fontastic.css">
    <!-- Google fonts - Open Sans-->
    <link rel="stylesheet" href="https://fonts.googleapis.com/css?family=Open+Sans:300,400,700">
    <!-- Fancybox-->
    <link rel="stylesheet" href="/vendor/@fancyapps/fancybox/jquery.fancybox.min.css">




    <!-- Tweaks for older IEs--><!--[if lt IE 9]>
    <script src="https://oss.maxcdn.com/html5shiv/3.7.3/html5shiv.min.js"></script>
    <script src="https://oss.maxcdn.com/respond/1.4.2/respond.min.js"></script><![endif]-->
</head>
<body>

<!-- Hero Section-->
<section style="background: url(/img/hero.jpg); background-size: cover; background-position: center center; height:240px">

<nav class="navbar navbar-expand-lg bg-transparent position-fixed">

<div class="container-fluid">
<ul class="nav navbar-nav navbar-collapse collapse w-100 order-3 dual-collapse2">


    <li><a class="text-white navbar-nav nav" href="/">
        Inicio
    </a></li>
                 <#if autenticado == false>
           <li><div id="navbarcollapse" class="text-white navbar-nav nav">
               <a href="/login"  class="text-white"> Iniciar Sesión</a>

           </div></li>

                 <#elseif autenticado == true && admin == true>


               <div class="navbar-nav nav dropdown ">
                   <a class="nav-item text-white dropdown-toggle" id="dropdownMenuButton" href="#" data-toggle="dropdown">
                       Herramientas
                   </a>
                   <div class="dropdown-menu" aria-labelledby="dropdownMenuButton">
                       <a class="dropdown-item" href="/articulos">Gestión de Artículos</a>
                       <a class="dropdown-item" href="/comentarios">Gestión de Comentarios</a>
                       <a class="dropdown-item" href="/usuarios">Gestión de Usuarios</a>
                       <a class="dropdown-item" href="/logout">Cerrar Sesión</a>
                   </div>
               </div>

                 <#elseif autenticado == true && autor == true>
                  <div class="navbar-nav nav dropdown ">
                      <a class="nav-item text-white dropdown-toggle" id="dropdownMenuButton" href="#" data-toggle="dropdown">
                          Herramientas
                      </a>
                      <div class="dropdown-menu" aria-labelledby="dropdownMenuButton">
                          <a class="dropdown-item" href="/articulos">Gestión de Artículos</a>
                          <a class="dropdown-item" href="/logout">Cerrar Sesión</a>
                      </div>
                  </div>
                 <#elseif autenticado == true && autor == false && admin == false>
                  <div class="navbar-nav nav dropdown ">
                      <a class="nav-item text-white dropdown-toggle" id="dropdownMenuButton" href="#" data-toggle="dropdown">
                          Herramientas
                      </a>
                      <div class="dropdown-menu" aria-labelledby="dropdownMenuButton"
                      <a class="dropdown-item" href="/logout">Cerrar Sesión</a>
                  </div>
                  </div>


                 </#if>

        </ul>
        </div>

    </nav>
</section>
        <br>
        <br>

<#if autenticado == true>
<a href="/usuarios/nuevo"><button type="button" class="btn btn-primary text-white" style="text-decoration: none">Nuevo Usuario</button></a>

<br>


<div class="table-responsive table-bordered table-striped" style="padding-right: -50px;">
        <table class="table">
            <thead>
            <tr>
                <th>ID</th>
                <th>Nombre</th>
                <th>Usuario</th>
                <th>Contraseña</th>
                <th>Rol</th>
                <th>Acciones</th>
            </tr>
            </thead>

            <#list usuarios as usuario>
            <tr>
        <td>${usuario.getId()}</td>
        <td>${usuario.getNombre()}</td>
        <td>${usuario.getUsername()}</td>
        <td>${usuario.getPassword()}</td>
                <td>
                <#if usuario.isAdministrator() == true >
                    Administrador
                    <#elseif usuario.isAutor() == true >
                    Autor
                    <#else>
                    Visitante
            </#if>

                </td>
            <td>
                <div class="btn-group" role="group">

            <#if usuario.getUsername() == "admin">
                <button type="button" class="btn btn-default disabled btn-xs" style="border-color: lightgray">Modificar</button>
            <button type="button" class="btn btn-default disabled btn-xs" style="border-color: lightgray">Eliminar</button>
            <#else>
            <button type="button" class="btn btn-default btn-xs" style="border-color: lightgray"><a
                    href="/usuarios/borrar/${usuario.getId()}">Eliminar</a></button>
                 <button type="button" class="btn btn-default btn-xs" style="border-color: lightgray"><a
                         href="/usuarios/editar/${usuario.getId()}" >Modificar</a></button>
            </#if>
                </div>
            </td>
    </tr>

            </#list>
        </table>
        </div>
        </div>


</#if>



<!-- JavaScript files-->
<script src="/vendor/jquery/jquery.min.js"></script>
<script src="/vendor/popper.js/umd/popper.min.js"> </script>
<script src="/vendor/bootstrap/js/bootstrap.min.js"></script>
<script src="/vendor/jquery.cookie/jquery.cookie.js"> </script>
<script src="/vendor/@fancyapps/fancybox/jquery.fancybox.min.js"></script>
<script src="/js/front.js"></script>
</body>

<footer class="main-footer bg-dark text-white" style="position:fixed;height:32px;width:100%;bottom:0;">
    <div class="container">

        <p>&copy; 2018. Blog de Ricardo y Emilio.</p>
    </div>
</footer>
</html>