<!DOCTYPE html>
<html lang="uk">
<head>
    <meta charset="UTF-8">
    <title>Реєстрація</title>

    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet">
</head>

<body class="bg-light">

<div class="container mt-5">

    <div class="card p-4 shadow">

        <h2>Реєстрація</h2>

        <form action="register" method="post">

            <div class="mb-3">
                <label class="form-label">Ім'я</label>
                <input type="text" name="name" class="form-control" required>
            </div>

            <div class="mb-3">
                <label class="form-label">Email</label>
                <input type="email" name="email" class="form-control" required>
            </div>

            <div class="mb-3">
                <label class="form-label">Пароль</label>
                <input type="password" name="password" class="form-control" required>
            </div>

            <button class="btn btn-success">
                Зареєструватися
            </button>

        </form>

        <a href="login" class="mt-3 d-block">
            Вже є акаунт? Увійти
        </a>

    </div>

</div>

</body>
</html>