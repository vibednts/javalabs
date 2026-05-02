<!DOCTYPE html>
<html lang="uk">
<head>
    <meta charset="UTF-8">
    <title>Вхід</title>

    <!-- Bootstrap CSS -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body class="bg-light">

<div class="container mt-5">
    <div class="card p-4 shadow">

        <h2 class="mb-3">Вхід</h2>

        <#if error??>
            <div class="alert alert-danger">
                ${error}
            </div>
        </#if>

        <form action="login" method="post">

            <div class="mb-3">
                <label class="form-label">Email</label>
                <input type="email" name="email" class="form-control" required>
            </div>

            <div class="mb-3">
                <label class="form-label">Пароль</label>
                <input type="password" name="password" class="form-control" required>
            </div>

            <button type="submit" class="btn btn-primary">
                Увійти
            </button>

        </form>

        <a href="register" class="mt-3 d-block">
            Ще немає акаунта? Зареєструватися
        </a>

    </div>
</div>

</body>
</html>