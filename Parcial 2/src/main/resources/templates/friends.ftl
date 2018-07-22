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
        </div>

                    <#list perfiles as person>
                        <div>
                            <div>
                                <form:form method="POST" action="unFriend">
                                    <a href="profile/${person.getId()}"><h3>
                                    ${person.getNombre()} ${person.getApellido()}</a>
                                    <button type="submit">UnFriend</button>
                                    <input type="hidden" name="personId" value="${person.getId()}" />
                                </form:form>

                            </div>
                        </div>
                    </#list>
                </div>
                </div>

</body>
</html>