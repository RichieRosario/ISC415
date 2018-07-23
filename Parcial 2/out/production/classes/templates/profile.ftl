<html>
    <#include "layout.ftl">
    <body class="bg-light" style="position:relative" >
    <div class="container">

        <br>
        <div class="card">
            <div class="card-header bg-dark">
        <div class="container form-inline" class="bg-dark" style="padding-top:2%">
        <img src="data:image/jpeg;base64, ${perfil.getProfilepic()}" class="img-thumbnail" style="height:200px;width:auto;">
    <h2 class="text-white" style="margin-left:2%">${perfil.getNombre()} ${perfil.getApellido()}</h2>
            <#if isFriend == true && owner == false>
            <button class="btn btn-default" style="margin-left:40%" disabled>Amigos</button>
            <#elseif owner==false && isFriend == false && isPending == false>
            <form method="post" action="/sendRequest/${perfil.getUserId()}">
                <button class="btn btn-default" style="margin-left:40%">Agregar a mis amigos</button>
            </form>
            <#elseif isPending == true>
              <button class="btn btn-default" style="margin-left:40%" disabled>Solicitud de amistad enviada</button>
            <#else>
           <form method='post' enctype='multipart/form-data' action="/subirfoto">


    <input type='file' id="uploaded_file" name='uploaded_file' style="opacity:0;">

    <button id="submit" type="submit" class="btn btn-default btn-xs">Cambiar Foto de Perfil</button>

            </form>
            </#if>
        </div>
        </div>
            <ul class="nav nav-tabs nav-justified">
                <li class="nav-item">
                    <a class="nav-link active" href="#home">Muro</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href="#menu1">Información</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href="#menu2">Amigos</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href="#menu3">Fotos</a>
                </li>

            </ul>


        </div>


    </div>




    <br>

    <!-- Tab panes -->
    <div class="tab-content">
        <div id="home" class="container tab-pane active"><br>

                <div class="card mx-auto" style="width:75%">
                    <div class="card-header bg-dark">
                        <p class="text-white">Publicación</p>
                    </div>
                    <div class="card-body">
                        <form method="post" action="/addPost/${user.getUsername()}">
                        <div class="form-control">
                        <textarea id="muro" name="muro" placeholder="Escribele algo a ${perfil.getNombre()}..." rows="5" cols="97" style="border:none"></textarea>
                        </div>

                    </div>
                    <div class="card-footer">

                        <button type="submit" class="btn btn-info btn-xs ml-5">Publicar</button>
                        </form>
                    </div>

                </div>


            <br>

                    <h1>Publicaciones</h1>

                   <#list muroeventos as post>
                        <div class="card">
                           <h1 class="card-header"> ${post.getEvento()} </h1>
                        <form action="/like/evento/${post.getId()}" method="post">
                            <i class="fa fa-thumbs-up text-green" style="color:green"><button name="like" id="like" value="Me gusta" style="border:none">Me gusta</button></i>(0)
                            <i class="fa fa-thumbs-down text-red" style="color:red"><button name="like" id="like" value="No me gusta"style="border:none">No me gusta</button></i>(0)
                        </form>
                            <#list post.getComments() as comentarioevento>
                                ${comentario.getComentario()}
                            <#else>
                            No hay comentarios en esta entrada.
                            </#list>
                        <form method="post" action="/comentario/evento/${post.getId()}">
                            <div class="form-control">
                                <textarea id="muro" name="muro" placeholder="Haz un comentario..." rows="5" cols="93" style="border:none"></textarea>
                            </div>
                    <div class="card-footer">
                        <button type="submit" class="btn btn-info btn-xs ml-5">Publicar</button>
                        </form>
                        </div>
                        </div>
                    </#list>
        <div id="menu1" class="container tab-pane fade"><br>
            <div class="card mx-auto" style="width:75%; ">
                <div class="card-header bg-dark">
                    <p class="text-white">Acerca de mi</p>
                </div>
                <div class="card-body">
                    <p>Nombre: ${perfil.getNombre()}</p>
                        <p>Apellido: ${perfil.getApellido()}</p>
                    <p>Sexo: ${perfil.getSexo()}</p>
                    <p>Lugar de Nacimiento: ${perfil.getLugarnacimiento()}</p>
                    <p> Lugar de Residencia: ${perfil.getCiudadactual()}</p>
                    <p> Edad: ${perfil.getEdad()}</p>
                </div>

            </div>

        </div>
        <div id="menu2" class="container tab-pane fade"><br>
            <h3>Menu 2</h3>
            <p>Sed ut perspiciatis unde omnis iste natus error sit voluptatem accusantium doloremque laudantium, totam rem aperiam.</p>
        </div>
        <div id="menu3" class="container tab-pane fade"><br>
            <h3>Menu 2</h3>
            <p>Sed ut perspiciatis unde omnis iste natus error sit voluptatem accusantium doloremque laudantium, totam rem aperiam.</p>
        </div>

        <br>

        <br>
</body>

    <script>
        $(document).ready(function(){
            $(".nav-tabs a").click(function(){
                $(this).tab('show');
            });
        });
    </script>

</html>