<!DOCTYPE html>
<html lang="uk">
<head>
    <meta charset="UTF-8">
    <title>Завдання</title>

    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet">
</head>

<body class="bg-light">

<div class="container mt-5">

    <div class="d-flex justify-content-between">
        <h2>Привіт, ${userName}!</h2>
        <a href="logout" class="btn btn-danger">Вийти</a>
    </div>

    <hr>

    <div class="card p-3 mb-4">
        <h4>Додати завдання</h4>

        <form action="tasks" method="post">

            <input name="title" class="form-control mb-2" placeholder="Назва" required>

            <input name="description" class="form-control mb-2" placeholder="Опис">

            <button class="btn btn-primary">
                Додати
            </button>

        </form>
    </div>

    <div class="card p-3">

        <h4>Список завдань</h4>

        <ul class="list-group">

            <#list tasks as task>

                <li class="list-group-item d-flex justify-content-between">

                    <div>
                        <strong>${task.title}</strong>
                        <br>
                        ${task.description!''}
                        <br>
                        <span class="badge bg-secondary">${task.status}</span>
                    </div>

                    <div>

                        <#if task.status != 'DONE'>
                            <a href="tasks?action=complete&id=${task.id}" class="btn btn-success btn-sm">
                                Виконано
                            </a>
                        </#if>

                        <a href="tasks?action=delete&id=${task.id}" class="btn btn-danger btn-sm">
                            Видалити
                        </a>

                    </div>

                </li>

            </#list>

        </ul>

    </div>

</div>

</body>
</html>