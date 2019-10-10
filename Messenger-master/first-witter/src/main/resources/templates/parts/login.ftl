<#macro login path register=true buttonName="Sign in">
    <form action=${path} method="post">
        <div class="form-group row">
            <label for="username" class="col-sm-2 col-form-label">Username</label>
            <div class="col-sm-5">
                <input type="text" name="username" class="form-control" id="username" placeholder="Username">
            </div>
        </div>
        <#if !register>
            <div class="form-group row">
                <label for="email" class="col-sm-2 col-form-label">Email</label>
                <div class="col-sm-5">
                    <input type="email" name="email" class="form-control" id="email" placeholder="email">
                </div>
            </div>
        </#if>
        <div class="form-group row">
            <label for="password" class="col-sm-2 col-form-label">Password</label>
            <div class="col-sm-5">
                <input type="password" name="password" class="form-control" id="password" placeholder="Password">
            </div>
        </div>
        <div><input type="hidden" name="_csrf" value="${_csrf.token}"/></div>
        <button type="submit" class="btn btn-primary">${buttonName}</button>
        <div>
            <#if register>
                <a href="/registration">Registration</a>
            </#if>
        </div>
    </form>
</#macro>

<#macro logout>
    <form action="/logout" method="post">
        <div><input type="hidden" name="_csrf" value="${_csrf.token}"/></div>
        <button type="submit" class="btn btn-danger">Log out</button>
    </form>
</#macro>