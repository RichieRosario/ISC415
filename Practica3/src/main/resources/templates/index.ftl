<!DOCTYPE html>
<html>
  <head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <title>Bootstrap Blog - B4 Template by Bootstrap Temple</title>
    <meta name="description" content="">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="robots" content="all,follow">

    <!-- Bootstrap CSS-->
      <link rel="stylesheet" href="vendor/bootstrap/css/bootstrap.css">

    <!-- Font Awesome CSS-->
    <link rel="stylesheet" href="vendor/font-awesome/css/font-awesome.min.css">
    <!-- Custom icon font-->
    <link rel="stylesheet" href="css/fontastic.css">
    <!-- Google fonts - Open Sans-->
    <link rel="stylesheet" href="https://fonts.googleapis.com/css?family=Open+Sans:300,400,700">
    <!-- Fancybox-->
    <link rel="stylesheet" href="vendor/@fancyapps/fancybox/jquery.fancybox.min.css">



      <!-- Favicon-->
    <link rel="shortcut icon" href="favicon.png">
    <!-- Tweaks for older IEs--><!--[if lt IE 9]>
        <script src="https://oss.maxcdn.com/html5shiv/3.7.3/html5shiv.min.js"></script>
        <script src="https://oss.maxcdn.com/respond/1.4.2/respond.min.js"></script><![endif]-->
  </head>
  <body>

    <!-- Hero Section-->
    <section style="background: url(img/hero.jpg); background-size: cover; background-position: center center; height:240px">

        <nav class="navbar navbar-expand-lg bg-transparent position-fixed">

            <div class="container-fluid">
            <ul class="nav navbar-nav navbar-collapse collapse w-100 order-3 dual-collapse2">


                    <li><a class="text-white navbar-nav nav" href="/">
                        Inicio
                    </a></li>
                 <#if autenticado == false>
           <li><div id="navbarcollapse" class="text-white navbar-nav nav">
             <a href="" data-toggle="modal" data-target="#loginModal" class="text-white"> Iniciar Sesión</a>

          </div></li>

            <#elseif autenticado == true>


               <div class="navbar-nav nav dropdown ">
                   <a class="nav-item text-white dropdown-toggle" id="dropdownMenuButton" href="#" data-toggle="dropdown">
                       Herramientas
                   </a>
                   <div class="dropdown-menu" aria-labelledby="dropdownMenuButton">
                       <a class="dropdown-item" href="#">Gestión de Artículos</a>
                       <a class="dropdown-item" href="#">Gestión de Usuarios</a>
                       <a class="dropdown-item" href="#">Cerrar Sesión</a>
                   </div>
               </div>



            </#if>
                </ul>
            </div>

        </nav>
    </section>
    <!-- Intro Section-->
    
    <!-- Latest Posts -->
<<<<<<< HEAD
    <section class="latest-posts nopadding-top"> 
      <div class="container">
        <header> 
          <h2>Últimas Entradas</h2>
        </header>
        <div class="row">
                <#list articulos as articulo>
          <div class="post col-md-4">
            <div class="post-thumbnail"><a href="/articulos"><img src="img/blog-1.jpg" alt="..." class="img-fluid"></a></div>
            <div class="post-details">
              <div class="post-meta d-flex justify-content-between">
                <div class="date">${articulo.getFecha()}</div>
                <div class="author">${articulo.getNombreAutor()}</div>
              </div><a href="/articulos">
                <h3 class="h4">${articulo.getTitulo()}</h3></a>
              <p class="text-muted">${articulo.getCuerpo()}</p>
            </div>
                    <#else>
                    <p>No hay entradas</p>
              </#list>

          </div>
=======

                <br>
            <div class="container justify-content-center">
          <h2 >Últimas Entradas</h2></div>
            <hr/>

                <div class="card-deck mt-auto justify-content-center">
                <#list articulos as articulo>
                <div class="row">
                    <div class="container">
                <div class="card my-4" style="width:480px">
                 <div class="card-header"><h3 class="d-inline">${articulo.getTitulo()}</h3>
                 </div>
                  <div class="card-body text-muted d-inline-block">${articulo.getResumen()}
                      <right><a href="">Leer Más</a></right>
                  </div>
                      <div class="card-footer">
                      Fecha de creación: ${articulo.getFecha()}&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                      Escrito por: ${articulo.getAutor().getUsername()}
                      </div>
                  </div>
                </div>
                    </div>

                    <#else>
                    <p>No hay entradas..</p>
                <br>
              </#list>
                </div>



>>>>>>> aa8715ca7560c5f26dada7e2350ed145053ee647

    
    <!-- Page Footer-->
    <footer class="main-footer bg-dark text-white">
        <div class="container">
          <div class="row">
            <div class="col-md-6">
              <p>&copy; 2018. Blog de Ricardo y Emilio.</p>
            </div>

          </div>
        </div>

    </footer>


    <div id="loginModal" class="modal fade" role="dialog">
        <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
              <h4>Iniciar Sesión</h4>
                <button type="button" class="close" data-dismiss="modal"> &times;</button>
                
            </div>
            <div class="modal-body">
                   <form class="form">
                   <div class="form-group">
                       <label class="sr-only" for="username">Usuario</label><input type="text" class="form-control input-sm" placeholder="Usuario" id="username" name="username">
                       </div>
                        <div class="form-group">  
                          
                           <label class="sr-only" for="password">Contraseña</label>
                                     <input type="password" class="form-control input-sm" placeholder="Contraseña" id="password" name="password"></div>
                       <div class="checkbox">
                       <label>
                       <input type="checkbox" id="remember" name="remember"> Recordarme
                       </label>
                         </div>


                         <div class="modal-footer">
                       <button type="button" class="btn btn-default btn-xs" data-dismiss="modal">Cancel</button> 
                       <button type="submit" class="btn btn-info btn-xs">Sign in</button>
                    </div>
                   
                     
               
                    </form>
            </div>

        </div>
        </div>
    </div>



    <!-- JavaScript files-->
    <script src="vendor/jquery/jquery.min.js"></script>
    <script src="vendor/popper.js/umd/popper.min.js"> </script>
    <script src="vendor/bootstrap/js/bootstrap.min.js"></script>
    <script src="vendor/jquery.cookie/jquery.cookie.js"> </script>
    <script src="vendor/@fancyapps/fancybox/jquery.fancybox.min.js"></script>
    <script src="js/front.js"></script>
  </body>
</html>