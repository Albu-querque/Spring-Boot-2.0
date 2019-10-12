<#import "parts/common.ftl" as c>
<@c.page>
    <h1>List users</h1>
    <table>
        <thead>
            <tr>
                <th>Username</th>
                <th>Email</th>
                <th>Roles</th>
            </tr>
        </thead>
        <tbody>
        <#list users as u>
            <tr>
                <td>${u.username}</td>
                <td>${u.email}</td>
                <td>
                    <#list u.roles as role>
                        ${role}
                        <sep>,</sep>
                    </#list>
                </td>
                <td><a href="/users/${u.id}">Edit</a></td>
            </tr>
        </#list>
        </tbody>
    </table>
</@c.page>