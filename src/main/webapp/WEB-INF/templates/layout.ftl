<#macro page title>
    <!DOCTYPE html>
    <html lang="uk">
    <head>
        <meta charset="UTF-8">
        <title>${title}</title>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet">

        <!-- Глобальна конфігурація для клієнтського JavaScript -->
        <script>
            window.appConfig = {
                contextPath: "${contextPath!''}",
                firebaseConfig: {
                    apiKey: "${apiKey!''}",
                    authDomain: "${authDomain!''}",
                    projectId: "${projectId!''}",
                    storageBucket: "${storageBucket!''}",
                    messagingSenderId: "${messagingSenderId!''}",
                    appId: "${appId!''}"
                }
            };
        </script>
    </head>
    <body class="bg-light">

    <!-- Навігація (якщо юзер авторизований) -->
    <#if isAuthenticated?? && isAuthenticated>
        <nav class="navbar navbar-expand-lg navbar-dark bg-dark mb-4">
            <div class="container">
                <a class="navbar-brand" href="${contextPath}/tasks">MyApp</a>
                <div class="d-flex align-items-center text-white">
                    <span class="me-3">${currentUserName!currentUserEmail!''}</span>
                    <!-- Кнопка виходу використовує JS для виходу з Firebase -->
                    <button id="logoutBtn" class="btn btn-outline-light btn-sm">Вийти</button>
                </div>
            </div>
        </nav>
    </#if>

    <!-- Основний контент сторінки -->
    <div class="container mt-5">
        <#nested>
    </div>

    <!-- Глобальний скрипт для логауту -->
    <#if isAuthenticated?? && isAuthenticated>
        <script type="module">
            import { initializeApp } from "https://www.gstatic.com/firebasejs/10.8.1/firebase-app.js";
            import { getAuth, signOut } from "https://www.gstatic.com/firebasejs/10.8.1/firebase-auth.js";

            const app = initializeApp(window.appConfig.firebaseConfig);
            const auth = getAuth(app);

            document.getElementById('logoutBtn').addEventListener('click', async () => {
                try {
                    await signOut(auth); // Виходимо з Firebase
                    // Після цього робимо POST-запит на бекенд для знищення сесії
                    await fetch(window.appConfig.contextPath + "/auth/logout", { method: 'POST' });
                    window.location.href = window.appConfig.contextPath + "/user/login";
                } catch (error) {
                    console.error("Помилка при виході:", error);
                }
            });
        </script>
    </#if>

    </body>
    </html>
</#macro>