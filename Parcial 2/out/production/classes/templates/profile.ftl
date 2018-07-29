
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
            <div class="container">
           <form method='post' enctype='multipart/form-data' action="/subirfoto">

    <input type='file' id="uploaded_file" name='uploaded_file' accept=".jpg, .jpeg, .png" required>
    <button id="submit" type="hidden" class="btn btn-default btn-xs">Cambiar Foto de Perfil</button>

            </form>

        </div>
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
                        <form method="post"  enctype='multipart/form-data' action="/addPost/${user.getUsername()}">
                        <textarea id="muro" name="muro" class="mx-auto" placeholder="Escribele algo a ${perfil.getNombre()}..." rows="5" cols="95" style="border-color:lightgray"></textarea>

                            <input type='file' id="uf" name='uf' accept=".jpg, .jpeg, .png">
                            <select class="js-example-basic form-control" id="amigos" name="amigos[]" multiple="">
                 <#list amigos as amigo>
                     <option value="${amigo.getUsername()}">${amigo.getProfile().getNombre()} ${amigo.getProfile().getApellido()}</option>
                 </#list>
                            </select>
                    </div>
                    <div class="modal-footer">

                        <button type="submit" class="btn btn-info btn-xs">Publicar</button>
                        </form>
                    </div>

                </div>


            <br>


                            <div class="card mx-auto" style="width:75%">
                                <div class="card-header bg-dark"><p class="text-white">Publicaciones</p></div>
                                   <div class="card-body">
            <#list muroentradas as entradas>
                                    <div class="card">
                                    <div class="card-header">${entradas.getUser().getProfile().getNombre()} ${entradas.getUser().getProfile().getApellido()}
                                    <#if owner==true>

                                         <a href="/post/borrar/${entradas.getId()}"><button type="button" class="close"> &times;</button></a>
                                    </#if>
                                    </div>
                                        <div class="card-body">
                                           <p> Personas en esta entrada:
                                            <#if entradas.getEtiqueta()??>
                                                <a href="/profile/${entradas.getEtiqueta().getUsers().getUsername()}">${entradas.getEtiqueta().getUsers().getProfile().getNombre()} ${entradas.getEtiqueta().getUsers().getProfile().getApellido()}</a>
                                            <#else>
                                            Nadie.
                                            </#if></p>
                                            <p>${entradas.getTexto()}</p>

                                        <#if entradas.getPhoto()??>
                                        <img src="data:image/jpeg;base64, ${entradas.getPhoto().getFoto()}" class="img-thumbnail" style="height:200px;width:auto; max-width:200px;">
                                    </#if>
                                    <form action="/like/post/${entradas.getId()}" method="post">
                                    <i class="fa fa-thumbs-up text-green" style="color:green"><button name="like" id="like" value="Me gusta" style="border:none">Me gusta</button></i>(${entradas.getcantlikes()})
                                    <i class="fa fa-thumbs-down text-red" style="color:red"><button name="like" id="like" value="No me gusta"style="border:none">No me gusta</button></i>(${entradas.getcantdislikes()})
                                </form>

                                    <hr>
                            <#list entradas.getComments() as comentario>
                            <div class="card-title">Comentarios</div>

                            <div class="card mx-auto" style="width:75%">
                                <div class="card-header">${comentario.getUser().getProfile().getNombre()} ${comentario.getUser().getProfile().getApellido()}
                                 <#if owner==true>

                                         <a href="/comentario/borrar/${comentario.getId()}"><button type="button" class="close"> &times;</button></a>
                                 </#if></div>
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
                                        <textarea id="muro"  name="muro" placeholder="Haz un comentario..." rows="5" cols="50" class="form-control"></textarea>

                                    <div class="modal-footer ">
                                        <button type="submit" class="btn btn-info btn-xs">Publicar</button>
                                    </div>

                                </form>

                                    </div>
                                   </div>
                <br>
            </#list>







                    <br>


                        <div class="card">
                            <div class="card-body">

                   <#list muroeventos as post>
                                <h1 class="card-title"> ${post.getEvento()} </h1>

                                <form action="/like/evento/${post.getId()}" method="post">
                            <i class="fa fa-thumbs-up text-green" style="color:green"><button name="like" id="like" value="Me gusta" style="border:none">Me gusta</button></i>(${post.getcantlikes()})
                            <i class="fa fa-thumbs-down text-red" style="color:red"><button name="like" id="like" value="No me gusta"style="border:none">No me gusta</button></i>(${post.getcantdislikes()})
                        </form>
                                <hr>
                            <#list post.getComments() as comentarioevento>
                            <div class="card-title">Comentarios</div>

                                <div class="card mx-auto" style="width:75%">
                                    <div class="card-header">${comentarioevento.getUser().getProfile().getNombre()} ${comentarioevento.getUser().getProfile().getApellido()}
                                      <#if owner==true>

                                         <a href="/comentario/borrar/${comentario.getId()}"><button type="button" class="close"> &times;</button></a>
                                      </#if></div>
                                    <div class="card-body">
                                        ${comentarioevento.getComentario()}
                                    </div>
                                    <div class="card-footer">
                                        <form action="/like/comentario/${comentarioevento.getId()}" method="post">
                                            <i class="fa fa-thumbs-up text-green" style="color:green"><button name="like" id="like" value="Me gusta" style="border:none">Me gusta</button></i>(${comentarioevento.getcantlikes()})
                                            <i class="fa fa-thumbs-down text-red" style="color:red"><button name="like" id="like" value="No me gusta"style="border:none">No me gusta</button></i>(${comentarioevento.getcantdislikes()})
                                        </form>
                                    </div>
                                </div>
                            <br>
                            <#else>
                            No hay comentarios de este evento.
                            <br>
                            </#list>

                        <form method="post" action="/comentario/evento/${post.getId()}">

                                <textarea id="muro" name="muro"  placeholder="Haz un comentario..." rows="5" cols="95" class="form-control"></textarea>

                    <div class="modal-footer">
                        <button type="submit" class="btn btn-info btn-xs">Publicar</button>
                    </div>
                        </form>
                            </div>

                        </div>
                   <br>

    </div>
    </div>
    </div>
                   </#list>

        <div id="menu1" class="container tab-pane fade"><br>
            <div class="card mx-auto" style="width:75%; ">
                <div class="card-header bg-dark">
                    <div class="form-inline">
                    <p class="text-white">Acerca de mi</p>
                    <#if owner == true>
                        <p style="margin-left:70%"><a  href="/usuarios/editar/${perfil.getUser().getId()}">Editar</a></p>
                    </#if>
                        </div>
                </div>
                <div class="card-body">
                    <p>Nombre: ${perfil.getNombre()}</p>
                        <p>Apellido: ${perfil.getApellido()}</p>
                    <p>Sexo: ${perfil.getSexo()}</p>
                    <p>Lugar de Nacimiento: ${perfil.getLugarnacimiento()}</p>
                    <p> Lugar de Residencia: ${perfil.getCiudadactual()}</p>
                    <p> Fecha de nacimiento: ${perfil.getFechanacimiento()}</p>
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
                                    <a href="/profile/${person.getUser().getUsername()}">${person.getNombre()} ${person.getApellido()}</a>
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
                                    <a href="/profile/${person.getUser().getUsername()}">${person.getNombre()} ${person.getApellido()}</a>
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
                  <#if owner == true>

<div class="card mx-auto"style="width:50%">
    <div class="card-header bg-dark" >
        <p class="text-white">Fotos</p></div>
    <br>
    <div class="card-body">
                        <#list albums as album>
                            <div class="card mx-auto" style="width:50%">
                                <div class="card-body">
                                    <a href="/album/${album.getId()}">${album.getNombre()}</a>
                                    <#if (album.getCover())??>
                                    <img src="data:image/jpeg;base64, ${album.getCover().getFoto()}" class="img-thumbnail" style="height:70px;width:auto; max-width:70px;">
                                    </#if>

                                </div>
                            </div>
                            <br>
                        <#else>
                            <p>No tienes fotos.</p>
                        </#list>
        <div class="modal-footer">
        <a href="/crearAlbum">     <button class="btn btn-info">Crea un album</button></a>
        </div>
    </div>
</div>
                  <#elseif isFriend == true>

<div class="card mx-auto"style="width:50%">
    <div class="card-header bg-dark" >
        <p class="text-white">Fotos</p></div>
    <br>
    <div class="card-body">
                        <#list albums as album>
                            <div class="card mx-auto" style="width:50%">
                                <div class="card-body">
                                    <a href="/album/${album.getId()}">${album.getNombre()}</a>
                                    <#if (album.getCover())??>
                                    <img src="data:image/jpeg;base64, ${album.getCover().getFoto()}" class="img-thumbnail" style="height:70px;width:auto; max-width:70px;">
                                    </#if>

                                </div>
                            </div>
                            <br>
                        <#else>
                            <p>Este usuario no tiene fotos.</p>
                        </#list>

    </div>
</div>

                  </#if>

            </#if>
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


<script>
    $(document).ready(function() {

         $('.js-example-basic').select2({
            placeholder: 'Personas en esta entrada',
             tokenSeparators: [','],
             maximumSelectionLength: 1,
             data: [],
             separator: ','
        });

    });


</script>