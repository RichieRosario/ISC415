<html>
 <#include "layout.ftl">
    <body class="bg-light">
<br>
    <div class="table-responsive table-bordered table-striped" style="padding-right: -50px;">
        <table class="table">
            <thead>
            <tr>
                <th>ID</th>
                <th>Usuario</th>
                <th>Contraseña</th>
                <th>Email</th>
                <th>Privilegios</th>
                <th>Acciones</th>
            </tr>
            </thead>

            <#list usuarios as usuario>
            <tr>
                <td>${usuario.getId()}</td>
                <td>${usuario.getUsername()}</td>
                <td>${usuario.getPassword()}</td>
                <td>${usuario.getEmail()}</td>
                <td>
                <#if usuario.isAdministrator() == true >
                    Administrador
                <#else>
                    Regular
                </#if>

                </td>
                <td>
                    <div class="btn-group" role="group">

            <#if usuario.getUsername() == "admin">
                <button type="button" class="btn btn-default disabled btn-xs" style="border-color: lightgray">Eliminar</button>
                <button type="button" class="btn btn-default disabled btn-xs" style="border-color: lightgray">Modificar</button>
            <#else>
                <a href="/usuarios/borrar/${usuario.getId()}"><button type="button" class="btn btn-default btn-xs" style="border-color: lightgray">Eliminar</button></a>
                <a href="/usuarios/editar/${usuario.getId()}" ><button type="button" class="btn btn-default btn-xs" style="border-color: lightgray">Modificar</button></a>

            </#if>
                    </div>
                </td>
            </tr>

            </#list>
        </table>
        </div>


<br>

</body>
</html>