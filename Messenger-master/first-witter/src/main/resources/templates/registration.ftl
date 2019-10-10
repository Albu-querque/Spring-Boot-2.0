<#import "parts/common.ftl" as c>
<#import "parts/login.ftl" as l>
<@c.page>
    <div><h1>Registration new user</h1></div>
    ${message?ifExists}
    <@l.login "/registration" false "To register"/>
    <div><a href="/login">Back</a></div>
</@c.page>