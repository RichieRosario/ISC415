<!DOCTYPE html>
<html lang="en">
<head>

    <title>Friend Requests</title>

</head>

<body>

        <div>
            <p>You have ${numeroNotificaciones} pending notifications</p>
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
            <div>
                        <#list profilesList as person>
                        <div>
                            <div>
                                <form:form method="POST" action="acceptRequest">
                                    <a href="profile/${person.getId()}">
                                    ${person.getNombre()} ${person.getApellido()}</a>
                                    <button type="submit" name="submitAccept" >Accept</button>
                                    <input type="hidden" name="personId" value="${person.getId()}" />
                                </form:form>
                                <form:form method="POST" action="unFriend">
                                    <button type="submit" name="submitDecline">Decline</button></h3>
                                    <input type="hidden" name="personId" value="${person.getId()}" />
                                </form:form>
                            </div>
                        </div>
                        </#list>
            </div>

</body>
</html>