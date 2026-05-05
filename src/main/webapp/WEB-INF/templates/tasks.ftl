<#import "layout.ftl" as layout>

<@layout.page title="Мої Завдання">
    <div class="card p-3 mb-4 shadow-sm">
        <h4 class="mb-3">Додати нове завдання</h4>
        <form action="${contextPath}/tasks" method="post" class="row g-2">
            <input type="hidden" name="action" value="add">

            <div class="col-md-4">
                <input name="title" class="form-control" placeholder="Назва завдання" required>
            </div>
            <div class="col-md-8">
                <input name="description" class="form-control" placeholder="Детальний опис (необов'язково)">
            </div>

            <!-- Новий рядок з випадаючими списками та датою -->
            <div class="col-md-3 mt-2">
                <select name="category" class="form-select text-secondary">
                    <option value="STUDY">🎓 Навчання</option>
                    <option value="WORK">💼 Робота</option>
                    <option value="PERSONAL" selected>🏠 Особисте</option>
                </select>
            </div>
            <div class="col-md-3 mt-2">
                <select name="priority" class="form-select text-secondary">
                    <option value="LOW">🟢 Низький</option>
                    <option value="MEDIUM" selected>🟡 Середній</option>
                    <option value="HIGH">🔴 Високий</option>
                </select>
            </div>
            <div class="col-md-4 mt-2">
                <input type="datetime-local" name="deadline" class="form-control text-secondary">
            </div>
            <div class="col-md-2 mt-2">
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
                    <li class="list-group-item d-flex justify-content-between align-items-center py-3 <#if task.overdue>border-start border-danger border-4</#if>">
                        <div>
                            <div class="d-flex align-items-center flex-wrap mb-1">
                                <!-- Назва таски (перекреслена, якщо виконана) -->
                                <strong class="fs-5 me-2 <#if task.status.name() == 'DONE'>text-decoration-line-through text-muted</#if>">
                                    ${task.title}
                                </strong>

                                <!-- Бейдж категорії -->
                                <span class="badge bg-secondary me-1">
                                    <#if task.category.name() == "STUDY">🎓 Навчання
                                    <#elseif task.category.name() == "WORK">💼 Робота
                                    <#else>🏠 Особисте</#if>
                                </span>

                                <!-- Бейдж пріоритету -->
                                <#if task.priority.name() == "HIGH">
                                    <span class="badge bg-danger me-2">🔴 Високий</span>
                                <#elseif task.priority.name() == "MEDIUM">
                                    <span class="badge bg-warning text-dark me-2">🟡 Середній</span>
                                <#else>
                                    <span class="badge bg-success me-2">🟢 Низький</span>
                                </#if>
                            </div>

                            <#if task.description?has_content>
                                <p class="mb-1 text-muted small">${task.description}</p>
                            </#if>

                            <!-- Дедлайн -->
                            <#if task.deadline??>
                                <div class="mb-1 small <#if task.overdue>text-danger fw-bold<#else>text-primary</#if>">
                                    ⏳ Дедлайн: ${task.deadline?string("dd.MM.yyyy HH:mm")}
                                    <span class="badge <#if task.overdue>bg-danger<#else>bg-light text-dark border</#if> ms-1">
                                        ${task.timeLeftStr}
                                    </span>
                                </div>
                            </#if>

                            <div class="mt-2">
                                <#if task.status.name() == 'NEW'>
                                    <span class="badge bg-primary">Нове</span>
                                <#elseif task.status.name() == 'IN_PROGRESS'>
                                    <span class="badge bg-info text-dark">В процесі</span>
                                <#elseif task.status.name() == 'DONE'>
                                    <span class="badge bg-success">Виконано</span>
                                </#if>
                            </div>
                        </div>

                        <div class="ms-3 text-end min-vw-10">
                            <#if task.status.name() != 'DONE'>
                                <a href="${contextPath}/tasks?action=complete&id=${task.id}" class="btn btn-sm btn-outline-success mb-1 w-100">
                                    ✓ Виконано
                                </a>
                                <br>
                            </#if>
                            <a href="${contextPath}/tasks?action=delete&id=${task.id}" class="btn btn-sm btn-outline-danger w-100">
                                ✗ Видалити
                            </a>
                        </div>
                    </li>
                </#list>
            </ul>
        </#if>
    </div>
</@layout.page>