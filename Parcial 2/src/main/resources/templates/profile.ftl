<html>
    <#include "layout.ftl">
    <body class="bg-light" style="position:relative" >
    <div class="container">

        <br>
        <div class="card">
            <div class="card-header bg-dark">
        <div class="container form-inline" class="bg-dark" style="padding-top:2%">
        <img src="data:image/jpeg;base64, ${perfil.getProfilepic()}" class="img-thumbnail" style="height:200px;width:auto; max-width:200px;">
    <h2 class="text-white" style="margin-left:2%">${perfil.getNombre()} ${perfil.getApellido()}</h2>
            <#if isFriend == true && owner == false>
            <button class="btn btn-default" style="margin-left:40%"  disabled>Amigos</button>
            </div>
             </div>
            <#elseif owner==false && isFriend == false && isPending == false>
            <form method="post" action="/sendRequest/${perfil.getUserId()}">
                <button class="btn btn-default" style="margin-left:200%">Agregar a mis amigos</button>
            </form>
            </div>
             </div>
            <#elseif isPending == true>
              <button class="btn btn-default" style="margin-left:40%" disabled>Solicitud de amistad enviada</button>
            </div>
             </div>
            <#else>
           <form method='post' enctype='multipart/form-data' action="/subirfoto">


    <input type='file' id="uploaded_file" name='uploaded_file' style="opacity:0;">

    <button id="submit" type="submit" class="btn btn-default btn-xs">Cambiar Foto de Perfil</button>

            </form>

        </div>
        </div>
            </#if>


    <#if owner == true || isFriend == true>
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
                        <textarea id="muro" name="muro" class="mx-auto" placeholder="Escribele algo a ${perfil.getNombre()}..." rows="5" cols="95" style="border-color:lightgray"></textarea>


                    </div>
                    <div class="modal-footer">

                        <button type="submit" class="btn btn-info btn-xs">Publicar</button>
                        </form>
                    </div>

                </div>


            <br>

                    <#list muroentradas as entradas>
                            <div class="card mx-auto" style="width:75%">
                                <div class="card-header bg-dark"><p class="text-white">Publicaciones</p></div>
                                <div class="card-body">
                                    <div class="card-title"><h1>${entradas.getUser().getProfile().getNombre()} ${entradas.getUser().getProfile().getApellido()} ha publicado: ${entradas.getTexto()} </h1></div>

                                    <form action="/like/post/${entradas.getId()}" method="post">
                                    <i class="fa fa-thumbs-up text-green" style="color:green"><button name="like" id="like" value="Me gusta" style="border:none">Me gusta</button></i>(${entradas.getcantlikes()})
                                    <i class="fa fa-thumbs-down text-red" style="color:red"><button name="like" id="like" value="No me gusta"style="border:none">No me gusta</button></i>(${entradas.getcantdislikes()})
                                </form>
                            <#list entradas.getComments() as comentarioevento>
                            <div class="card mx-auto" style="width:75%">
                                <div class="card-header">${comentario.getUser()}</div>
                                <div class="card-body">
                                ${comentario.getComentario()}
                                    </div>
                             <div class="card-footer">
                                <form action="/like/comentario/${comentario.getId()}" method="post">
                                 <i class="fa fa-thumbs-up text-green" style="color:green"><button name="like" id="like" value="Me gusta" style="border:none">Me gusta</button></i>(${comentario.getcantlikes()})
                                 <i class="fa fa-thumbs-down text-red" style="color:red"><button name="like" id="like" value="No me gusta"style="border:none">No me gusta</button></i>(${comentario.getcantdislikes()})
                             </form>
                                 </div>
                                </div>
                            <br>
                            <#else>
                            No hay comentarios en esta entrada.
                            <br>
                            </#list>

                                <form method="post" action="/comentario/post/${entradas.getId()}">
                                        <textarea id="muro"  name="muro" placeholder="Haz un comentario..." rows="5" cols="95" style="border-color:lightgray"></textarea>

                                    <div class="modal-footer ">
                                        <button type="submit" class="btn btn-info btn-xs">Publicar</button>
                                    </div>

                                </form>
                            </div>

                    </div>
                    <br>
                    </#list>

                   <#list muroeventos as post>
                        <div class="card mx-auto" style="width:75%">
                            <div class="card-header bg-dark"><p class="text-white">Eventos</p></div>
                            <div class="card-body">
                                <h1 class="card-title"> ${post.getEvento()} </h1>

                                <form action="/like/evento/${post.getId()}" method="post">
                            <i class="fa fa-thumbs-up text-green" style="color:green"><button name="like" id="like" value="Me gusta" style="border:none">Me gusta</button></i>(${post.getcantlikes()})
                            <i class="fa fa-thumbs-down text-red" style="color:red"><button name="like" id="like" value="No me gusta"style="border:none">No me gusta</button></i>(${post.getcantdislikes()})
                        </form>
                            <#list post.getComments() as comentarioevento>
                                <div class="card mx-auto" style="width:75%">
                                    <div class="card-header">${comentario.getUser()}</div>
                                    <div class="card-body">
                                        ${comentario.getComentario()}
                                    </div>
                                    <div class="card-footer">
                                        <form action="/like/comentario/${comentario.getId()}" method="post">
                                            <i class="fa fa-thumbs-up text-green" style="color:green"><button name="like" id="like" value="Me gusta" style="border:none">Me gusta</button></i>(${comentario.getcantlikes()})
                                            <i class="fa fa-thumbs-down text-red" style="color:red"><button name="like" id="like" value="No me gusta"style="border:none">No me gusta</button></i>(${comentario.getcantdislikes()})
                                        </form>
                                    </div>
                                </div>
                            <br>
                            <#else>
                            No hay comentarios de este evento.
                            <br>
                            </#list>

                        <form method="post" action="/comentario/evento/${post.getId()}">

                                <textarea id="muro" name="muro"  placeholder="Haz un comentario..." rows="5" cols="95" style="border-color:lightgray"></textarea>

                    <div class="modal-footer">
                        <button type="submit" class="btn btn-info btn-xs">Publicar</button>
                    </div>
                        </form>
                            </div>

                        </div>
                   <br>
                    </#list>
    </div>
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
            <#if owner == true>

<div class="card mx-auto"style="width:50%">
    <div class="card-header bg-dark" >
        <p class="text-white">Amigos</p></div>
    <br>
    <div class="card-body">
                        <#list perfiles as person>
                            <div class="card mx-auto" style="width:50%">
                                <div class="card-body">
                                    <img src="data:image/jpeg;base64, ${person.getProfilepic()}" class="img-thumbnail" style="height:70px;width:auto; max-width:70px;">
                                    <a href="profile/${person.getUser().getUsername()}">${person.getNombre()} ${person.getApellido()}</a>
                                    <div class="form-inline" style="margin-left:25%">

                                        <form method="POST" action="/unFriend">
                                            <button type="submit" class="btn btn-danger" name="submitDecline">Eliminar de amigos</button>
                                            <input type="hidden" name="decline" value="${person.getUser().getId()}" />
                                        </form>
                                    </div>
                                </div>
                            </div>
                            <br>
                        <#else>
                            <p>No tienes amigos.</p>
                        </#list>
    </div>
</div>
            <#elseif isFriend == true>

<div class="card mx-auto"style="width:50%">
    <div class="card-header bg-dark" >
        <p class="text-white">Amigos</p></div>
    <br>
    <div class="card-body">
                        <#list perfiles as person>
                            <div class="card mx-auto" style="width:50%">
                                <div class="card-body">
                                    <img src="data:image/jpeg;base64, ${person.getProfilepic()}" class="img-thumbnail" style="height:70px;width:auto; max-width:70px;">
                                    <a href="profile/${person.getUser().getUsername()}">${person.getNombre()} ${person.getApellido()}</a>
                                </div>
                            </div>
                            <br>
                        <#else>
                            <p>Este usuario no tiene amigos.</p>
                        </#list>
    </div>
</div>
            </#if>

              </div>
        <div id="menu3" class="container tab-pane fade"><br>
            <h3>Menu 2</h3>
            <p>Sed ut perspiciatis unde omnis iste natus error sit voluptatem accusantium doloremque laudantium, totam rem aperiam.</p>
        </div>
            </#if>

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