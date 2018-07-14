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
    <script src="/js/jquery-1.12.4.min.js"></script>
    <script src="/js/bootstrap.js"></script>
</head>



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
                      <div class="dropdown-menu" aria-labelledby="dropdownMenuButton">
                          <a class="dropdown-item" href="/logout">Cerrar Sesión</a>
                      </div>
                  </div>


                 </#if>

            </ul>
        </div>

    </nav>
</section>
<br>
    <input type="hidden" id="usuario" value="${usuario.getUsername()}">
    <input type="hidden" id="usuario_destino" value="${administrador.getUsername()}">
<div class="container mt-auto justify-content-center align-content-center">
    <div id="chatbox" class="row card " id="chat_window_1" style="margin-left: 30%;width: 40%"" >
                        <h3 class="card-header"><span class="glyphicon glyphicon-comment"></span> ${administrador.getNombre()}</h3>



                <div class="card-body" >
                    <div id="chat_panel">

                    </div>

                </div>
                    <div class="input-group">
                        <input id="btn-input" type="text" class="form-control input-sm chat_input" placeholder="Escriba su mensaje..." />
                        <span class="input-group-btn card-footer">
                        <button class="btn btn-primary btn-sm" id="btn-chat">Enviar</button>
                        </span>
                    </div>

    </div>
    </div>
</body>

<footer class="main-footer bg-dark text-white" style="position:fixed;height:32px;width:100%;bottom:0;">
    <div class="container">

        <p>&copy; 2018. Blog de Ricardo y Emilio.</p>
    </div>
</footer>

<script>
    $(document).ready(function () {
        var webSocket = new WebSocket("ws://" + location.hostname + ":" + location.port + "/mensajeServidor");
        webSocket.onopen  = function(e){
            var info = get_info(true);
            webSocket.send(info);
        };
        $("#btn-chat").click(function(){
            var info = get_info(false);
            webSocket.send(info);
            basechat(info,true);
            $("#btn-input").val("");
        });

        webSocket.onmessage  = function(msg){
            basechat(msg.data,false);
        };

        function basechat(mensaje,self) {
            var info = JSON.parse(mensaje);
            var html = '<div class="row">';
            html+=    '<div class="col-md-10 col-xs-10 ">';
            if(self){
                html+=    '<p><b>'+ info.usuario_origen +' dice:</b></p><p>' + info.mensaje + '</p>';
            }
            else if (info.usuario_origen === "server"){
                html+=    '<p><b><span style="color:red">'+ info.mensaje +' </b></span></p>';
            }
            else
                html+=    '<p><b>'+ info.nombre_origen +' dice:</b></p><p>' + info.mensaje + '</p>';


            html+=     '</div></div>';
            var div = document.createElement('div');
            div.innerHTML = html;
            document.getElementById("chat_panel").appendChild(div);

        }
    });



    function get_info (inicializando) {
        var info = {
            inicializando: inicializando,
            usuario_origen:$("#usuario").val(),
            usuario_destino: $("#usuario_destino").val(),
            mensaje: $('#btn-input').val(),
        };
        return  JSON.stringify(info);
    }
</script>

</html>

