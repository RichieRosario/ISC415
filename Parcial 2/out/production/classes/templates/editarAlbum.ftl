<#include "layout.ftl">

<body class="bg-light">
<br>
<br>

<div class="card mx-auto"style="width:50%">
    <div class="card-header bg-dark" >
        <p class="text-white">Fotos</p></div>
    <br>
    <div class="card-body">
 <#list album.getPhotos() as photo>
                            <div class="card mx-auto" style="width:50%">
                                <a href="/photo/${photo.getId()}"> <img src="data:image/jpeg;base64, ${photo.getFoto()}" class="card-img-top">
                                </a>

                                <div class="card-body">
                                <a href="/editarFoto/${photo.getId()}">     <button class="btn btn-info">Editar esta  foto</button></a>

                                <a href="/eliminarFoto/${photo.getId()}">     <button class="btn btn-danger">Eliminar esta foto</button></a>

                                </div>
                            </div>
                            <br>
 <#else>
                            <p>No tienes fotos.</p>
 </#list>
        <form method="post"  enctype='multipart/form-data' action="/editarAlbum/${album.getId()}">
            <div class="form-group">
            <input type="text" id="nombre" name="nombre" class="mx-auto form-control" placeholder="Nombre de album" value="${album.getNombre()}" required>
            </div>
            <div class="form-group">
                <label>Agrega una foto al album</label>
                <br>
            <input type='file' id="uf" name='uf' accept=".jpg, .jpeg, .png" >
            </div>


            <div class="form-group">
                <input type="text" id="descripcion" name="descripcion" class="mx-auto form-control" placeholder="Descripcion de album" value="${album.getNombredescripcion()}" required>
            </div>

            <select class="js-example-basic form-control" name="amigos[]" multiple="multiple">
                <#list amigos as amigo>
                <option value="${amigo.getUsername()}">${amigo.getProfile().getNombre()} ${amigo.getProfile().getApellido()}</option>
                </#list>
            </select>


    </div>
    <div class="modal-footer">
        <button class="btn btn-info">Guardar cambios</button>
        <a href="/album/${album.getId()}" > <button class="btn btn-danger">Cancelar</button></a>


    </div>
    </form>
</div>
<br>
</body>

<script>
    $(document).ready(function() {

        $('.js-example-basic').select2({
            placeholder: 'Personas en este album',
            tokenSeparators: [','],
            maximumSelectionLength: 1,
            data: [],
            separator: ','
        });

    });


</script>
