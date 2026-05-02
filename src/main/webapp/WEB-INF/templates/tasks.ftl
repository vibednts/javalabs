<#import "layout.ftl" as layout>

<@layout.page title="Мої Завдання">
    <div class="card p-3 mb-4 shadow-sm">
        <h4 class="mb-3">Додати нове завдання</h4>
        <form action="${contextPath}/tasks" method="post" class="row g-2">
            <div class="col-md-4">
                <input name="title" class="form-control" placeholder="Назва завдання" required>
            </div>
            <div class="col-md-6">
                <input name="description" class="form-control" placeholder="Детальний опис (необов'язково)">
            </div>
            <div class="col-md-2">
                <button class="btn btn-primary w-100">Додати</button>
            </div>
        </form>
    </div>

    <div class="card p-3 shadow-sm">
        <h4 class="mb-3">Список завдань</h4>

        <#if tasks?size == 0>
            <p class="text-muted text-center my-4">У вас ще немає завдань. Додайте перше!</p>
        <#else>
            <ul class="list-group list-group-flush">
                <#list tasks as task>
                    <li class="list-group-item d-flex justify-content-between align-items-center py-3">
                        <div>
                            <strong class="fs-5">${task.title}</strong>
                            <#if task.description?has_content>
                                <p class="mb-1 text-muted small">${task.description}</p>
                            </#if>

                            <#if task.status == 'NEW'>
                                <span class="badge bg-primary">Нове</span>
                            <#elseif task.status == 'IN_PROGRESS'>
                                <span class="badge bg-warning text-dark">В процесі</span>
                            <#elseif task.status == 'DONE'>
                                <span class="badge bg-success">Виконано</span>
                            </#if>
                        </div>

                        <div>
                            <#if task.status != 'DONE'>
                                <a href="${contextPath}/tasks?action=complete&id=${task.id}" class="btn btn-sm btn-outline-success me-1">
                                    ✓ Виконано
                                </a>
                            </#if>
                            <a href="${contextPath}/tasks?action=delete&id=${task.id}" class="btn btn-sm btn-outline-danger">
                                ✗ Видалити
                            </a>
                        </div>
                    </li>
                </#list>
            </ul>
        </#if>
    </div>
</@layout.page>