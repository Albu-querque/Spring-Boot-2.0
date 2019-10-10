<#import "parts/common.ftl" as c>
<#import "parts/login.ftl" as l>

<@c.page>
    <div><h1>Login page</h1></div>
    ${message?ifExists}
    <@l.login "/login"/>
</@c.page>