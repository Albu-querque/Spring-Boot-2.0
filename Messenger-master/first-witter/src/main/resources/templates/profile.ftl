<#import "parts/common.ftl" as c>

<@c.page>
    <div><h1>Edit profile</h1></div>
    <div><h5>${username}</h5></div>
    ${message?ifExists}
    <form action="/users/profile" method="post">
            <div class="form-group row">
                <label for="email" class="col-sm-2 col-form-label">Email</label>
                <div class="col-sm-5">
                    <input type="email" name="email" class="form-control" id="email" value="${email}" placeholder="email">
                </div>
            </div>

        <div class="form-group row">
            <label for="password" class="col-sm-2 col-form-label">Password</label>
            <div class="col-sm-5">
                <input type="password" name="password" class="form-control" id="password" placeholder="Password">
            </div>
        </div>

        <div><input type="hidden" name="_csrf" value="${_csrf.token}"/></div>
        <button type="submit" class="btn btn-primary">Save</button>
    </form>
</@c.page>