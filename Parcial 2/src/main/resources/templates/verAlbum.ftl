<#include "layout.ftl">

<body class="bg-light">
<br>
<br>

<p class="text-center">    <a href="/profile/${album.getUser().getUsername()}" >Volver al perfil del usuario</a></p>



<div class="card mx-auto"style="width:50%">
    <div class="card-header bg-dark" >
        <p class="text-white">${album.getNombre()}</p></div>
    <br>
    <div class="card-body">
                        <#list album.getPhotos() as photo>
                            <div class="card mx-auto" style="width:50%">
                                <a href="/photo/${photo.getId()}"> <img src="data:image/jpeg;base64, ${photo.getFoto()}" class="card-img-top">
                                </a>

                                <div class="card-body">
                            <#if owner == true>
                                    <a href="/editarFoto/${photo.getId()}">     <button class="btn btn-info">Editar esta  foto</button></a>

                                    <a href="/eliminarFoto/${photo.getId()}">     <button class="btn btn-danger">Eliminar esta foto</button></a>
                            </#if>
                                </div>
                            </div>
                            <br>
                        <#else>
                            <p>No tienes fotos.</p>
                        </#list>
        <p> Descripción: ${album.getNombredescripcion()}</p>
        <p> Personas en este albúm:
                                            <#if (album.getEtiqueta())??>
                                                <#if (album.getEtiqueta().getUsers())??>
                                                <a href="/profile/${album.getEtiqueta().getUsers().getUsername()}">${album.getEtiqueta().getUsers().getProfile().getNombre()} ${album.getEtiqueta().getUsers().getProfile().getApellido()}</a>
                                                <#else>
                                                Nadie.
                                                </#if>
                                                <#else>
                                            Nadie.
                                            </#if></p>
<#if owner == true>
<div class="modal-footer">
        <a href="/editarAlbum/${album.getId()}">     <button class="btn btn-info">Editar este  album</button></a>

        <a href="/eliminarAlbum/${album.getId()}">     <button class="btn btn-danger">Eliminar este  album</button></a>
    </div>
</#if>
    </div>
</div>

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

