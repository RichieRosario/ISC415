<#include "layout.ftl">

<body class="bg-light">
<br>
<br>


   <p class="text-center">    <a href="/album/${foto.getAlbums().getId()}" >Volver al albúm</a></p>

                            <div class="card mx-auto" style="width:50%">
                                <img src="data:image/jpeg;base64, ${foto.getFoto()}" class="card-img-top">
                                <div class="card-body">
                                    <form method="post" action="/editarFoto/${foto.getId()}">
                                   <#if (foto.getCaption())??>
                                       <input type="text" value="${foto.getCaption()}" id="caption" name="caption" placeholder="Agregue una breve descripción de la foto" class="form-control">
                                   <#else>
                                       <input type="text" id="caption" name="caption" placeholder="Agregue una breve descripción de la foto" class="form-control">
                                   </#if>

                                        <select class="js-example-basic form-control" name="amigos[]" multiple="multiple">
                <#list amigos as amigo>
                    <option value="${amigo.getUsername()}">${amigo.getProfile().getNombre()} ${amigo.getProfile().getApellido()}</option>
                </#list>
                                        </select>
                                        <div class="modal-footer">
                                            <button class="btn btn-info">Guardar cambios</button>
                                        <a href="/album/${foto.getAlbums().getId()}" > <button class="btn btn-danger">Cancelar</button></a>


                                        </div>
                                    </form>
                                    <hr>


                                </div>
                            </div>
                            <br>


</div>

<br>

<br>

</body>


<script>
    $(document).ready(function() {

        $('.js-example-basic').select2({
            placeholder: 'Personas en esta foto',
            tokenSeparators: [','],
            maximumSelectionLength: 1,
            data: [],
            separator: ','
        });

    });


</script>

