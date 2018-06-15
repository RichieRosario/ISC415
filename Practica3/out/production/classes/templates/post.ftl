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
<div class="container">

        <!-- Latest Posts -->
        <main class="card">
            <div class="container">

                        <h1>${articulo.getTitulo()}</h1>
                            <hr/>
                        <div class="post-footer d-flex align-items-center flex-column flex-sm-row"><a href="#" class="author d-flex align-items-center flex-wrap">
                            <div class="row">
                            <div class="title" style="padding-right: 640px;padding-left: 100px"><span>${articulo.getNombreAutor()}</span></div></a>

                            <div class="d-flex align-items-center flex-wrap flex-column flex-sm-row">
                                <div class="date "><i class="icon-clock"></i>${articulo.getFecha()}</div>

                            </div>
                        </div>
                        </div>
                <br>
            <br>
                        <div class="post-body">
                            ${articulo.getCuerpo()}
                        </div>
                <br>
                <br>
                        <div class="post-tags">
                            <p>Etiquetas:</p>
                       <#list etiquetas as etiqueta>
                           ${etiqueta.getEtiqueta()}
                       <#else>
                                No hay etiquetas
                       </#list>
                        </div>
                            <br>

                            <#if autenticado==true && admin == true>
                            <header>

                                <h3 class="h6">Comentarios<span class="no-of-comments">(${cantidadcomentarios})</span></h3>
                            </header>

                            <div class="card">
                                 <#list listaComentarios as comentario>
                                     <div class="card-header">${comentario.getNombreAutor(comentario.getAutorid())}
                                         <a href="/comentarios/borrar/${comentario.getId()}"><button type="button" class="close" data-dismiss="modal"> &times;</button></a>
                                     </div>
                                     <div class="card-body">${comentario.getComentario()}</div>
                                 <#else>
                                No hay comentarios
                                 </#list>

                            </div>

                            <br>
                            <hr/>
                            <header>
                                <h3>Deja tu opinión</h3>
                            </header>
                            <form action="/articulo/${articulo.getId()}" method="post" class="commenting-form">
                                <div class="row">

                                    <div class="form-group col-md-12">
                                        <textarea name="commentbody" id="commentbody" placeholder="Escriba su comentario..." class="form-control"></textarea>
                                    </div>
                                    <div class="form-group col-md-12">
                                        <button type="submit" class="btn btn-secondary">Enviar</button>
                                    </div>
                                </div>
                            </form>
                                <#elseif autenticado==true && autor == true>
                            <header>

                                <h3 class="h6">Comentarios<span class="no-of-comments">(${cantidadcomentarios})</span></h3>
                            </header>

                            <div class="card">
                                 <#list listaComentarios as comentario>
                                     <div class="card-header">${comentario.getNombreAutor(comentario.getAutorid())}
                                         <a href="comentarios/borrar/${comentario.getId()}"><button type="button" class="close" data-dismiss="modal"> &times;</button></a>
                                     </div>
                                     <div class="card-body">${comentario.getComentario()}</div>
                                 <#else>
                                No hay comentarios
                                 </#list>

                            </div>

                            <br>
                            <hr/>
                            <header>
                                <h3>Deja tu opinión</h3>
                            </header>
                            <form action="/articulo/${articulo.getId()}" method="post" class="commenting-form">
                                <div class="row">

                                    <div class="form-group col-md-12">
                                        <textarea name="commentbody" id="commentbody" placeholder="Escriba su comentario..." class="form-control"></textarea>
                                    </div>
                                    <div class="form-group col-md-12">
                                        <button type="submit" class="btn btn-secondary">Enviar</button>
                                    </div>
                                </div>
                            </form>

                                    <#elseif autenticado==true && admin == false && autor==false>
                            <header>

                                <h3 class="h6">Comentarios<span class="no-of-comments">(${cantidadcomentarios})</span></h3>
                            </header>

                            <div class="card">
                                 <#list listaComentarios as comentario>
                                     <div class="card-header">${comentario.getNombreAutor(comentario.getAutorid())}
                                     </div>
                                     <div class="card-body">${comentario.getComentario()}</div>
                                 <#else>
                                No hay comentarios
                                 </#list>

                            </div>

                            <br>
                            <hr/>
                            <header>
                                <h3>Deja tu opinión</h3>
                            </header>
                            <form action="/articulo/${articulo.getId()}" method="post" class="commenting-form">
                                <div class="row">

                                    <div class="form-group col-md-12">
                                        <textarea name="commentbody" id="commentbody" placeholder="Escriba su comentario..." class="form-control"></textarea>
                                    </div>
                                    <div class="form-group col-md-12">
                                        <button type="submit" class="btn btn-secondary">Enviar</button>
                                    </div>
                                </div>
                            </form>
                            <#else>
                            <header>

                                <h3 class="h6">Comentarios<span class="no-of-comments">(${cantidadcomentarios})</span></h3>
                            </header>

                            <div class="card">
                                 <#list listaComentarios as comentario>
                                     <div class="card-header">${comentario.getNombreAutor(comentario.getAutorid())}
                                     </div>
                                     <div class="card-body">${comentario.getComentario()}</div>
                                 <#else>
                                No hay comentarios
                                 </#list>
                            </div>

                            <br>
                            <hr/>

                                                    </#if>






        </main>
    </div>


<br>











<!-- JavaScript files-->
<script src="/vendor/jquery/jquery.min.js"></script>
<script src="/vendor/popper.js/umd/popper.min.js"> </script>
<script src="/vendor/bootstrap/js/bootstrap.min.js"></script>
<script src="/vendor/jquery.cookie/jquery.cookie.js"> </script>
<script src="/vendor/@fancyapps/fancybox/jquery.fancybox.min.js"></script>
<script src="/js/front.js"></script>
</body>

<footer class="main-footer bg-dark text-white" style="margin-top:50px;position:fixed;height:32px;width:100%;bottom:0;">
    <div class="container">

        <p>&copy; 2018. Blog de Ricardo y Emilio.</p>
    </div>
</footer>
</html>