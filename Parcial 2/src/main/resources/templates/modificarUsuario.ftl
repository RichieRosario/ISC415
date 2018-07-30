<html>
 <#include "layout.ftl">
    <body class="bg-light">
<br>
        <div class="mx auto">
    <form method="post" class="container-fluid " action="/usuarios/editar/${user.getId()}" >
        <div class="form-group row">
            <div class="col-md-6">
                <input type="text" name="username" id="username" class="form-control disabled" style="width:92.5%" placeholder="Nombre de Usuario" value="${user.getUsername()}"/>
            </div>
        </div>
        <div class="form-group row">
            <div class="col-md-6">
                <input type="password" name="password" id="password" class="form-control" style="width:92.5%" placeholder="Nueva Contraseña" value="${user.getPassword()}"/>
            </div>
        </div>
        <div class="form-group row form-inline">
            <div class="col-auto">
                <input type="text" name="nombre" id="nombre" class="form-control" style="width:110%" placeholder="Primer Nombre" value="${user.getProfile().getNombre()}"/>
            </div>

            <div class="col-auto">
                <input type="text" name="apellido" id="apellido" class="form-control" style="margin-left:27% !important;width:110%" placeholder="Apellido" value="${user.getProfile().getApellido()}"/>
            </div>
        </div>
        <div class="form-group row">
            <div class="col-md-6">
                <input type="text" name="email" id="email" class="form-control" style="width:92.5%" placeholder="Correo Electrónico" value="${user.getEmail()}"/>
            </div>
        </div>


        <div class="form-group row">
            <div class="col-md-6">
                <input type="text" name="lugarresidencia" id="lugarresidencia" class="form-control" style="width:92.5%" placeholder="Lugar de Residencia" value="${user.getProfile().getCiudadactual()}"/>
            </div>
        </div>

        <div class="form-group row">
            <div class="col-md-6">
                <input type="text" name="lugarnacimiento" id="lugarnacimiento" class="form-control" style="width:92.5%" placeholder="Lugar de Nacimiento" value="${user.getProfile().getLugarnacimiento()}"/>
            </div>
        </div>

        <div class="form-group row">
            <div class="col-md-6">
                <input type="date" name="date" id="date" class="text-muted form-control" style="width:92.5%" value="${user.getProfile().getFechanacimiento()}"/>
            </div>
        </div>

        <div class="form-group row">
            <div class="col-md-6">
                <input type="text" name="lugartrabajo" id="lugartrabajo" class="form-control" style="width:92.5%" placeholder="Lugar de Trabajo" value="${user.getProfile().getLugartrabajo()}"/>
            </div>
        </div>

        <div class="form-group row">
            <div class="col-md-6">
                <input type="text" name="lugarestudio" id="lugarestudio" class="form-control" style="width:92.5%" placeholder="Lugar de Estudio" value="${user.getProfile().getLugarestudio()}"/>
            </div>
        </div>


        <div class="form-group row">
            <div class="col-md-6">
                <label>Sexo:</label>
                <#if user.getProfile().getSexo() == "Masculino">
                <input type="radio" name="sexo" value="M"checked> Masculino
                <input type="radio" name="sexo" value="F"> Femenino
                <#else>

                <input type="radio" name="sexo" value="M"> Masculino
                <input type="radio" name="sexo" value="F" checked> Femenino
                </#if>
            </div>
        </div>
        <div class="form-group">

            <label for="nombre">Permisos:</label>
            <#if user.isAdministrator() == true>
            <input type="radio" class="inline" name="rol" value="true" checked> Administrador
            <input type="radio" class="inline" name="rol" value="false"> Usuario Normal
            <#else>

            <input type="radio" class="inline" name="rol" value="true"> Administrador
            <input type="radio" class="inline" name="rol" value="false" checked> Usuario Normal
            </#if>
        </div>
        <button type="submit" class="btn btn-info btn-xs">Guardar</button>


    </form>
        </div>
<br>

</body>
</html>