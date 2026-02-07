<!DOCTYPE html>
<html>
<head>
    <title>User Dashboard</title>
    <style>
        body { font-family: sans-serif; padding: 20px; }
        .box { border: 1px solid #ccc; padding: 15px; margin-bottom: 10px; border-radius: 5px; }
        code { background: #f4f4f4; padding: 2px 5px; }
    </style>
</head>
<body>
<h1>Панель керування (GET запит)</h1>

<div class="box">
    <h3>Отримані дані:</h3>
    <ul>
        <li><strong>Path Variable (ID з URL):</strong> ${userId}</li>
        <li><strong>Request Param (type):</strong> ${type}</li>
    </ul>
    <p>Спробуйте змінити URL на: <a href="user/999?type=admin">/user/999?type=admin</a></p>
</div>

<div class="box">
    <h3>Сесії та Куки:</h3>
    <ul>
        <li><strong>Користувач у сесії:</strong> ${sessionUser}</li>
        <li><strong>Кука (lastVisit):</strong> ${cookieInfo}</li>
    </ul>
</div>

<div class="box">
    <h3>Тест POST запиту та JSON</h3>
    <form action="user" method="post">
        <label>Введіть ім'я для збереження в сесію:</label><br>
        <input type="text" name="username" placeholder="Ваше ім'я">
        <button type="submit">Відправити (POST)</button>
    </form>
</div>

<div class="box">
    <h3>Тест JSON (GET)</h3>
    <a href="user/777?type=tester&format=json" target="_blank">Відкрити JSON відповідь</a>
</div>
</body>
</html>