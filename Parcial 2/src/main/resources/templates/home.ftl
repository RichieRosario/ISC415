
    <#include "layout.ftl">
    <body class="bg-light">

<br>



<br>


<div class="card mx-auto" style="width:75%">
    <div class="card-header bg-dark"><p class="text-white">Publicaciones</p></div>
    <div class="card-body">
            <#list muroentradas as entradas>
                <div class="card">
                    <div class="card-header"> <a href="/profile/${entradas.getUser().getUsername()}">${entradas.getUser().getProfile().getNombre()} ${entradas.getUser().getProfile().getApellido()}</a>

                    </div>
                    <div class="card-body">
                        <p> Personas en esta entrada:
                                            <#if (entradas.getEtiqueta())??>
                                                <#if (entradas.getEtiqueta().getUsers())??>
                                                <a href="/profile/${entradas.getEtiqueta().getUsers().getUsername()}">${entradas.getEtiqueta().getUsers().getProfile().getNombre()} ${entradas.getEtiqueta().getUsers().getProfile().getApellido()}</a>
                                                <#else>
                                                Nadie.
                                                </#if>
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


                   <#list muroeventos as post>

                       <div class="card-header"><a href="/profile/${post.getUser().getUsername()}">${post.getUser().getProfile().getNombre()} ${post.getUser().getProfile().getApellido()}</a></div>
                   <div class="card-body">
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

                                    </div>
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
                   </#list>
</div>
    </div>
<br>
<br>
    </body>

