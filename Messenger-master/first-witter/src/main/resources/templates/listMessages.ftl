<#import "parts/common.ftl" as c>

<@c.page>

    <a class="btn btn-primary" data-toggle="collapse" href="#collapse" role="button" aria-expanded="false" aria-controls="collapseExample">
        Message add form
    </a>
    <div class="form-row collapse <#if message??>show</#if>" id="collapse">
        <div class="form-group">
            <form method="post" action="/saveMessage" enctype="multipart/form-data" class="my-2">
                <div class="form-group">
                    <input type="text"
                           name="text"
                           class="form-control ${(textError??)?string('is-invalid', '')}"
                           value="<#if message??>${message.text}</#if>"
                           placeholder="Enter message"
                    />
                    <#if textError??>
                        <div class="invalid-feedback">
                            ${textError}
                        </div>
                    </#if>
                </div>

                <div class="form-group">
                    <input type="text"
                           name="tag"
                           class="form-control ${(textError??)?string('is-invalid', '')}"
                           value="<#if message??>${message.tag}</#if>"
                           placeholder="Enter tag"
                    />
                    <#if textError??>
                        <div class="invalid-feedback">
                            ${textError}
                        </div>
                    </#if>
                </div>
                <div class="form-group">
                    <div class="custom-file">
                        <input type="file"
                               name="file"
                               id="customFile">
                        <label class="custom-file-label" for="customFile">Choose file</label>
                    </div>
                </div>
                <input type="hidden" name="_csrf" value="${_csrf.token}"/>
                <div class="form-group">
                    <button type="submit" class="btn btn-primary">Add message</button>
                </div>
            </form>
        </div>
    </div>

    <div>
        <form method="get" action="/main">
            <button type="submit" class="btn btn-primary mt-3">Show all messages</button>
        </form>
    </div>

    <div class="form-row mt-3">
        <form method="get" action="/main" class="form-inline">
            <input type="text"
                   name="tag"
                   value="${tag?if_exists}"
                   class="form-control"
                   placeholder="Enter tag"/>
            <button type="submit" class="btn btn-primary ml-2">Search messages by tag</button>
        </form>
    </div>

    <br/>
    <br/>

    <div>
        <h1>List Messages</h1>
        <div class="card-columns">
            <#list messages as m>

                <div class="card my-2">
                    <div>
                        <#if m.filename??>
                            <img src="/img/${m.filename}"  class="card-img-top"/>
                        </#if>
                    </div>
                    <span>${m.text}</span>
                    <i>${m.tag}</i>
                    <div class="card-footer text-muted p-1">
                        <strong>${m.authorName}</strong>
                    </div>
                </div>

            <#else>
                <h3>List is empty!</h3>
            </#list>
        </div>
    </div>
</@c.page>