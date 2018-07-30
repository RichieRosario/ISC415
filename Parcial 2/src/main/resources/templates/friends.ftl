<#include "layout.ftl">

<body>

<ul class="nav nav-tabs nav-justified">
    <li class="nav-item"><a  class="nav-link active" href="/friends">Tus amigos</a></li>
    <li class="nav-item"><a  class="nav-link" href="/findFriends">Buscar amigos</a></li>
    <li class="nav-item"><a  class="nav-link" href="/friendRequests">Solicitudes de amistad</a></li>
    <li class="nav-item"><a  class="nav-link" href="/pendingRequests">Solicitudes Pendientes</a></li>
</ul>
<br>
<br>

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
                                    <div class="form-inline" style="margin-left:25%">

                                        <form method="POST" action="/unFriend">
                                            <button type="submit" class="btn btn-danger" name="submitDecline">Eliminar de amigos</button>
                                            <input type="hidden" name="decline" value="${person.getUser().getId()}" />
                                        </form>
                                    </div>
                                </div>
                            </div>
                            <br>
                        <#else>
                            <p>No tienes amigos.</p>
                        </#list>
    </div>
</div>

</body>