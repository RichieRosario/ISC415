<#include "layout.ftl">

<body class="bg-light">
<br>
<br>


   <p class="text-center">    <a href="/album/${foto.getAlbums().getId()}" >Volver al albúm</a></p>

                            <div class="card mx-auto" style="width:50%">
                                <img src="data:image/jpeg;base64, ${foto.getFoto()}" class="card-img-top">
                                <div class="card-body">
                                   <#if (foto.getCaption())??>
                                       ${foto.getCaption()}
                                   </#if>

                                    <p> Personas en esta foto:
                                            <#if (foto.getEtiqueta())??>
                                            <#if (foto.getEtiqueta().getUsers())??>
                                                <a href="/profile/${foto.getEtiqueta().getUsers().getUsername()}">${foto.getEtiqueta().getUsers().getProfile().getNombre()} ${foto.getEtiqueta().getUsers().getProfile().getApellido()}</a>
                                            <#else>
                                                Nadie.
                                            </#if>
                                            <#else>
                                            Nadie.
                                            </#if></p>

                                    <form action="/like/foto/${foto.getId()}" method="post">
                                        <i class="fa fa-thumbs-up text-green" style="color:green"><button name="like" id="like" value="Me gusta" style="border:none">Me gusta</button></i>(${foto.getcantlikes()})
                                        <i class="fa fa-thumbs-down text-red" style="color:red"><button name="like" id="like" value="No me gusta"style="border:none">No me gusta</button></i>(${foto.getcantdislikes()})
                                    </form>

                                    <hr>
                            <#list foto.getComments() as comentario>
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
                            No hay comentarios en esta foto.
                            <br>
                            </#list>
                                    <form method="post" action="/comentario/foto/${foto.getId()}">
                                        <textarea id="muro"  name="muro" placeholder="Haz un comentario..." rows="5" cols="50" class="form-control"></textarea>

                                        <div class="modal-footer ">
                                            <button type="submit" class="btn btn-info btn-xs">Publicar</button>
                                        </div>

                                    </form>
                                            <#if owner == true>
                                            <div class="modal-footer">
                                    <a href="/editarFoto/${foto.getId()}">     <button class="btn btn-info">Editar esta  foto</button></a>

                                    <a href="/eliminarFoto/${foto.getId()}">     <button class="btn btn-danger">Eliminar esta foto</button></a>
                                                <div class="modal-footer">
                                            </#if>
                                </div>
                            </div>
                            <br>


</div>

<br>

<br>

</body>

<script>
    $(document).ready(function() {
        $('.js-example-basic-multiple').select2({
            placeholder: 'Personas en este album'
        });
    });
</script>

