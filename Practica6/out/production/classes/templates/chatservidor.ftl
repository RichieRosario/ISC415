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
    <script src="/js/chatUsuario.js"></script>
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
<div class="container mt-auto justify-content-center align-content-center" style="margin-left: 30%;width: 40%">
    <div class="panel panel-default" >
        <div class="panel-body" >
            <h2>Chats activos</h2>
            <hr>
            <div id="body"></div>
        </div>
    </div>
</div>
<input type="hidden" id="usuario" value="${usuario.getUsername()}">
<input type="hidden" id="nombre" value="${usuario.getNombre()}">

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
            console.log(info.usuario_origen);
            webSocket.send(JSON.stringify(info));
        };

        webSocket.onmessage  = function(msg){
            var data = JSON.parse(msg.data);
            if(document.getElementById('chat_window_'+data.usuario_origen)==null){
                basechat(data);
            }

            from_msg(data);
        };

        webSocket.onclose = function (msg) {

        }

        function from_msg(mensaje) {
            var html = '<div class="row">';
            html+=    '<div class="col-md-10 col-xs-10 ">';
            html+=    '<p><b>'+ mensaje.usuario_origen +' dice:</b></p><p>' + mensaje.mensaje + '</p>';
            html+=     '</div></div>';
            var div = document.createElement('div');
            div.innerHTML = html;
            document.getElementById("chat_panel_"+mensaje.usuario_origen).appendChild(div);

        }

        function to_msgelf(mensaje) {
            var html = '<div class="row msg_container base_sent">';
            html+=    '<div class="col-md-10 col-xs-10 ">';
            html+=    '<p><b>'+ mensaje.nombre_origen +' dice:</b></p><p>' + mensaje.mensaje + '</p>';
            html+=     '</div></div>';
            var div = document.createElement('div');
            div.innerHTML = html;
            document.getElementById("chat_panel_"+mensaje.usuario_destino).appendChild(div);

        }



        function basechat(mensaje){
            var html = '<div class="row card" id="chat_window_'+mensaje.usuario_origen+'" style="margin-top:20px;margin-left:5%" >'
                    +'<div class="card-header ">'
                    +'<h3 class="d-inline">'+mensaje.usuario_origen+'</h3> </div>'
                    +'<div id="chat_panel_'+mensaje.usuario_origen+'" class="card-body"></div>'
                    +'<div class="input-group">'
                    +'<input id="btn-input-'+mensaje.usuario_origen+'" type="text" class="form-control input-sm chat_input" placeholder="Escriba su mensaje..." />'
                    +'<span class="input-group-btn card-footer">'
                    +'<button class="btn btn-primary btn-sm" id="send-msg-'+mensaje.usuario_origen+'" value="'+mensaje.usuario_origen+'">Enviar</button>'
                    +'</span></div></div><br>';

            var div = document.createElement('div');
            div.innerHTML = html;
            document.getElementById("body").appendChild(div);
            $("#send-msg-"+mensaje.usuario_origen).click(function(){
                var usuario_destino = $(this).attr("value")
                var info = {
                    inicializando: false,
                    usuario_origen:$("#usuario").val(),
                    usuario_destino: usuario_destino,
                    mensaje: $("#btn-input-"+usuario_destino).val(),
                    nombre_origen: $("#nombre").val()

                };
                webSocket.send(JSON.stringify(info));
                to_msgelf(info);
                $("#btn-input-"+usuario_destino).val("");
            });
        }

    });

    function get_info (inicializando) {
        var info = {
            inicializando: inicializando,
            usuario_origen:$("#usuario").val(),
            usuario_destino: $("#usuario_destino").val(),
            mensaje: ""
        };
        return info
    }


</script>

</html>

