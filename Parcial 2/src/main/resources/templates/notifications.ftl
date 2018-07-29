<#include "layout.ftl">

<body>
<br>
<br>

<div class="card mx-auto"style="width:50%">
    <div class="card-header bg-dark" >
        <p class="text-white">Notificaciones</p></div>
    <br>
    <div class="card-body">
                        <#list notifications as notification>
                            <div class="card mx-auto" style="width:50%">
                                <div class="card-body">
                                    ${notification.getNotificacion()}
                                </div>
                            </div>
                            <br>
                        <#else>
                            <p>No tienes notificaciones.</p>
                        </#list>
    </div>
</div>

</body>