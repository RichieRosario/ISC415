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
    <link rel="stylesheet" href="../../../../../../../../Downloads/distribution/vendor/bootstrap/css/bootstrap.css">


    <!-- Font Awesome CSS-->
    <link rel="stylesheet" href="../../../../../../../../Downloads/distribution/vendor/font-awesome/css/font-awesome.min.css">
    <!-- Custom icon font-->
    <link rel="stylesheet" href="../../../../../../../../Downloads/distribution/css/fontastic.css">
    <!-- Google fonts - Open Sans-->
    <link rel="stylesheet" href="https://fonts.googleapis.com/css?family=Open+Sans:300,400,700">
    <!-- Fancybox-->
    <link rel="stylesheet" href="../../../../../../../../Downloads/distribution/vendor/@fancyapps/fancybox/jquery.fancybox.min.css">

    <!-- Favicon-->
    <link rel="shortcut icon" href="favicon.png">
    <!-- Tweaks for older IEs--><!--[if lt IE 9]>
        <script src="https://oss.maxcdn.com/html5shiv/3.7.3/html5shiv.min.js"></script>
        <script src="https://oss.maxcdn.com/respond/1.4.2/respond.min.js"></script><![endif]-->
  </head>
  <body>
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
    <section class="latest-posts nopadding-top"> 
      <div class="container">
         <div class="modal-dialog modal-lg">
        <div class="modal-content">
            <div class="modal-header">
              <h4>Crear Artículo</h4>
                <button type="button" class="close" data-dismiss="modal"> &times;</button>
                
            </div>
            <div class="modal-body" >
                   <form class="form">
                   <div class="form-group">
                       <label for="titulo">Titulo</label><input type="text" class="form-control input-sm" placeholder="Titulo" id="titulo" name="titulo">
                       </div>
                        <div class="form-group">  
                          
                           <label for="cuerpo">Cuerpo</label>
                                     <textarea class="form-control input" placeholder="Cuerpo" id="cuerpo" name="cuerpo" cols="40" rows="50"></textarea>
                                </div>
                  <label for="etiqueta">Etiquetas</label><input type="text"  data-role="tagsinput" class="form-control input-sm" placeholder="Etiquetas" id="etiqueta" name="etiqueta">
                       </div>
                        <div class="form-group">  


                         <div class="modal-footer">
                       <button type="button" class="btn btn-default btn-xs" data-dismiss="modal">Volver</button> 
                       <button type="submit" class="btn btn-info btn-xs">Crear</button>
                    </div>
                   
                     
               
                    </form>
            </div>

        </div>
        </div>
		</div>

    </section>
    
    <!-- Page Footer-->
    < <!-- Page Footer-->
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
    <script src="../../../../../../../../Downloads/distribution/vendor/jquery/jquery.min.js"></script>
    <script src="../../../../../../../../Downloads/distribution/vendor/popper.js/umd/popper.min.js"> </script>
    <script src="../../../../../../../../Downloads/distribution/vendor/bootstrap/js/bootstrap.min.js"></script>
    <script src="../../../../../../../../Downloads/distribution/vendor/jquery.cookie/jquery.cookie.js"> </script>
    <script src="../../../../../../../../Downloads/distribution/vendor/@fancyapps/fancybox/jquery.fancybox.min.js"></script>
    <script src="../../../../../../../../Downloads/distribution/js/front.js"></script>
  </body>
</html>