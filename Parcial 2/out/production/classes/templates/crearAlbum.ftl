<#include "layout.ftl">

<body class="bg-light">
<br>
<br>

<div class="card mx-auto"style="width:50%">
    <div class="card-header bg-dark" >
        <p class="text-white">Fotos</p></div>
    <br>
    <div class="card-body">

        <form method="post"  enctype='multipart/form-data' action="/crearAlbum">
            <div class="form-group">
            <input type="text" id="nombre" name="nombre" class="mx-auto form-control" placeholder="Nombre de album">
            </div>
            <div class="form-group">
                <label>Agrega una foto al album</label>
                <br>
            <input type='file' id="uf" name='uf' accept=".jpg, .jpeg, .png" >
            </div>


            <div class="form-group">
                <input type="text" id="descripcion" name="descripcion" class="mx-auto form-control" placeholder="Descripcion de album" required>
            </div>

            <select class="js-example-basic form-control" name="amigos[]" multiple="multiple">
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


