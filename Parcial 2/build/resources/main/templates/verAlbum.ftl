<#include "layout.ftl">

<body class="bg-light">
<br>
<br>

   <#if owner == true>

<div class="card mx-auto"style="width:50%">
    <div class="card-header bg-dark" >
        <p class="text-white">Fotos</p></div>
    <br>
    <div class="card-body">
                        <#list album.getPhotos() as photo>
                            <div class="card mx-auto" style="width:50%">
                                <div class="card-body">
                                    <a href="/photo/${photo.getId()}"> <img src="data:image/jpeg;base64, ${photo.getFoto()}" class="img-thumbnail" style="height:200px;width:auto; max-width:200px;">
                                   </a>

                                </div>
                            </div>
                            <br>
                        <#else>
                            <p>No tienes fotos.</p>
                        </#list>
        <a href="/crearAlbum">     <button class="btn btn-info">Agrega fotos a este  album</button></a>

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
                            <p>Este album no tiene fotos.</p>
                        </#list>
    </div>
</div>
   </#if>
</div>
</#if>

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

