<#macro login path register=true buttonName="Sign in">
    <form action=${path} method="post">
        <div class="form-group row">
            <label for="username" class="col-sm-2 col-form-label">Username</label>
            <div class="col-sm-5">
                <input type="text"
                       name="username"
                       class="form-control ${(usernameError??)?string('is-invalid', '')}"
                       id="username"
                       value="<#if user??>${user.username}</#if>"
                       placeholder="Username">
                <#if usernameError??>
                    <div class="invalid-feedback">
                        ${usernameError}
                    </div>
                </#if>
            </div>
        </div>
        <#if register>
            <div class="form-group row">
                <label for="email" class="col-sm-2 col-form-label">Email</label>
                <div class="col-sm-5">
                    <input type="email"
                           name="email"
                           class="form-control ${(emailError??)?string('is-invalid', '')}"
                           id="email"
                           value="<#if user??>${user.email}</#if>"
                           placeholder="email"
                    />
                    <#if emailError??>
                        <div class="invalid-feedback">
                            ${emailError}
                        </div>
                    </#if>
                </div>
            </div>
        </#if>
        <div class="form-group row">
            <label for="password" class="col-sm-2 col-form-label">Password</label>
            <div class="col-sm-5 ">
                <input type="password"
                       name="password"
                       class="form-control ${(passwordError??)?string('is-invalid', '')}"
                       id="password"
                       placeholder="Password"
                />
                <#if passwordConfirmationError??>
                    <div class="invalid-feedback">
                        ${passwordError}
                    </div>
                </#if>
            </div>
        </div>
            <#if register>
            <div class="form-group row">
                <label for="password" class="col-sm-2 col-form-label">Confirm password</label>
                <div class="col-sm-5">
                    <input type="password"
                           name="passwordConfirmation"
                           class="form-control ${(passwordConfirmationError??)?string('is-invalid', '')}"
                           id="passwordConfirmation"
                           placeholder="Confirm your password"
                    />
                    <#if passwordConfirmationError??>
                        <div class="invalid-feedback">
                            ${passwordConfirmationError}
                        </div>
                    </#if>
                </div>
            </div>
            </#if>
        <div><input type="hidden" name="_csrf" value="${_csrf.token}"/></div>
        <button type="submit" class="btn btn-primary">${buttonName}</button>
        <div>
            <#if !register>
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