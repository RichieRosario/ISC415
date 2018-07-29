<#include "layout.ftl">

<body>

<ul class="nav nav-tabs nav-justified">
    <li class="nav-item"><a  class="nav-link" href="/friends">Tus amigos</a></li>
    <li class="nav-item"><a  class="nav-link  active" href="/findFriends">Buscar amigos</a></li>
    <li class="nav-item"><a  class="nav-link" href="/friendRequests">Solicitudes de amistad</a></li>
    <li class="nav-item"><a  class="nav-link" href="/pendingRequests">Solicitudes Pendientes</a></li>
</ul>
<br>
<br>

<div class="card mx-auto"style="width:50%">
    <div class="card-header bg-dark" >
        <p class="text-white">Sugerencias de Amigos</p></div>
    <br>
    <div class="card-body">
                        <#list perfiles as person>
                            <div class="card mx-auto" style="width:50%">
                                <div class="card-body">
                                    <img src="data:image/jpeg;base64, ${person.getProfilepic()}" class="img-thumbnail" style="height:70px;width:auto; max-width:70px;">
                                    <a href="profile/${person.getUser().getUsername()}">${person.getNombre()} ${person.getApellido()}</a>
                                    <div class="form-inline" style="margin-left:25%">

                                        <form method="POST" action="/sendRequest/${person.getUser().getId()}">
                                            <button type="submit" class="btn btn-success" name="submitDecline">Agregar a mis amigos</button>
                                        </form>
                                    </div>
                                </div>
                            </div>
                            <br>
                        <#else>
                            <p>No hay sugerencias para tus amigos.</p>
                        </#list>
    </div>
</div>

</body>