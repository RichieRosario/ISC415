<!DOCTYPE html>
<html>
<head>
    <title>Friends</title>
</head>

<body>
        <div>
            <li>
                <ul>
                    <li>
                        <p>You have ${numeroNotificaciones} pending notifications</p>
                    </li>
                        <#list notificaciones as notification>
                            <li>
                                <div>
                                    <div>${notification.getNotificacion()}</div>
                                </div>
                            </li>
                        </#list>
                </ul>
            </li>
        </div>
        <div>
            <ul>
                <p><a href="profile/${user.getId()}">
                    <h5>${profile.getNombre()} ${profile.getApellido()}</h5></a></p>

                <li>
                    <a href="home">
                        <span>Home</span>
                    </a>
                </li>

                <li>
                    <a href="/friends" >
                        <span>Friends</span>
                    </a>
                    <ul>
                        <li><a href="/friends">Your Friends</a></li>
                        <li><a href="/friendRequests">Friend Requests</a></li>
                        <li><a href="/pendingRequests">Pending Requests</a></li>
                    </ul>
                </li>
            </ul>
             </div>
            <div>
                <div>
          	<#list personList as person>
              <div>
                  <div>
                      <form:form method="GET" action="profile">
                          <h3>${profile.getNombre()} ${profile.getApellido()}
                              <button type="submit" disabled class="pull-right btn btn-danger btn-sm">Friend Request Sent</button></h3>
                          <input type="hidden" name="personId" value="${user.getId()}" />
                      </form:form>
                  </div>
              </div>

                <#else>
                <p>No hay solicitudes pendientes.</p>
                </#list>
                </div>
</body>
</html>