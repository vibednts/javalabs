<!DOCTYPE html>
<html>
<head><title>Мої завдання</title></head>
<body>
<h2>Привіт, ${userName}! Твої завдання:</h2>
<a href="logout">Вийти</a>
<hr>

<h3>Додати завдання</h3>
<form action="tasks" method="post">
    Назва: <input type="text" name="title" required><br>
    Опис: <input type="text" name="description"><br>
    <button type="submit">Додати</button>
</form>

<hr>
<h3>Список:</h3>
<ul>
    <#list tasks as task>
        <li>
            <strong>${task.title}</strong> (${task.status}) - ${task.description!''}

            <#if task.status != 'DONE'>
                [<a href="tasks?action=complete&id=${task.id}">Виконано</a>]
            </#if>
            [<a href="tasks?action=delete&id=${task.id}" style="color:red;">Видалити</a>]
        </li>
    <#else>
        <p>У вас поки немає завдань.</p>
    </#list>
</ul>
</body>
</html>