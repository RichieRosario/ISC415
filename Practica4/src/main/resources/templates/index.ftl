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

                <li>
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
               </div></li>

                 <#elseif autenticado == true && autor == true>
                 <li>
                  <div class="navbar-nav nav dropdown ">
                      <a class="nav-item text-white dropdown-toggle" id="dropdownMenuButton" href="#" data-toggle="dropdown">
                          Herramientas
                      </a>
                      <div class="dropdown-menu" aria-labelledby="dropdownMenuButton">
                          <a class="dropdown-item" href="/articulos">Gestión de Artículos</a>
                          <a class="dropdown-item" href="/logout">Cerrar Sesión</a>
                      </div>
                  </div></li>
                 <#elseif autenticado == true && autor == false && admin == false>
                 <li>
                  <div class="navbar-nav nav dropdown ">
                      <a class="nav-item text-white dropdown-toggle" id="dropdownMenuButton" href="#" data-toggle="dropdown">
                          Herramientas
                      </a>
                      <div class="dropdown-menu" aria-labelledby="dropdownMenuButton">
                          <a class="dropdown-item" href="/logout">Cerrar Sesión</a>
                      </div>
                  </div></li>


            </#if>
                <li style="margin-left:75rem"><div id="navbarcollapse" class="text-white nav navbar-nav">
                     Bienvenido,
                <br>
                ${usuariodentro}

                </div></li>
                </ul>
            </div>

        </nav>
    </section>
    <!-- Intro Section-->
    
    <!-- Latest Posts -->

                <br>
            <div class="container justify-content-center">
          <h2 >Últimas Entradas</h2></div>
            <hr/>

                <div class=" mt-auto justify-content-center align-content-center">
                <#list articulos as articulo>
                <center><div class="row">
                    <div class="container">
                <div class="card my-4" style="width:720px">
                 <div class="card-header"><h3 class="d-inline">${articulo.getTitulo()}</h3>
                 </div>
                  <div class="card-body text-muted d-inline-block">${articulo.getResumen()}
                      <right><a href="/articulo/${articulo.getId()}">Leer Más</a></right>
                  </div>
                      <div class="card-footer">
                      Fecha de creación: ${articulo.getFecha()}
                          <br>
                      Escrito por: ${articulo.getNombreAutor()}
                      </div>
                  </div>
                </div>
                    </div></center>

                    <#else>
                    <center><p>No hay entradas..</p></center>
                <br>
              </#list>
                </div>

<p>
    <div class="container modal-footer">
<#if 1 <= (paginaactual-1)>
<a href="/page/${paginaactual-1}" class="form-inline">Anterior</a>
    </#if>
        <a class="form-inline">${paginaactual}</a>
    <#if (paginaactual) < maxpage>
        <a href="/page/${paginaactual+1}" class="form-inline">Siguiente</a>
    </#if>
    </div>
    </p>

    
    <!-- Page Footer-->




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