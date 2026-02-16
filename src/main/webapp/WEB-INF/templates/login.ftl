<!DOCTYPE html>
<html>
<head><title>Вхід</title></head>
<body>
<h2>Вхід</h2>
<#if error??>
    <p style="color:red;">${error}</p>
</#if>
<form action="login" method="post">
    Email: <input type="email" name="email" required><br>
    Пароль: <input type="password" name="password" required><br>
    <button type="submit">Увійти</button>
</form>
<a href="register">Ще немає акаунта? Зареєструватися</a>
</body>
</html>