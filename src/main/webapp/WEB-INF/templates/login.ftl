<#import "layout.ftl" as layout>

<@layout.page title="Вхід">
    <div class="row justify-content-center">
        <div class="col-md-6 col-lg-4">
            <div class="card p-4 shadow">
                <h2 class="mb-3 text-center">Вхід</h2>

                <div id="errorAlert" class="alert alert-danger d-none"></div>

                <form id="loginForm">
                    <div class="mb-3">
                        <label class="form-label">Email</label>
                        <input type="email" id="email" class="form-control" required>
                    </div>
                    <div class="mb-3">
                        <label class="form-label">Пароль</label>
                        <input type="password" id="password" class="form-control" required>
                    </div>
                    <button type="submit" class="btn btn-primary w-100" id="loginBtn">Увійти</button>
                </form>

                <a href="${contextPath}/user/register" class="mt-3 d-block text-center">Ще немає акаунта? Зареєструватися</a>
            </div>
        </div>
    </div>

    <script type="module">
        import { initializeApp } from "https://www.gstatic.com/firebasejs/10.8.1/firebase-app.js";
        import { getAuth, signInWithEmailAndPassword } from "https://www.gstatic.com/firebasejs/10.8.1/firebase-auth.js";

        const app = initializeApp(window.appConfig.firebaseConfig);
        const auth = getAuth(app);

        const loginForm = document.getElementById('loginForm');
        const errorAlert = document.getElementById('errorAlert');
        const loginBtn = document.getElementById('loginBtn');

        loginForm.addEventListener('submit', async (e) => {
            e.preventDefault(); // Зупиняємо стандартну відправку форми

            loginBtn.disabled = true;
            errorAlert.classList.add('d-none');

            const email = document.getElementById('email').value;
            const password = document.getElementById('password').value;

            try {
                // 1. Авторизація у Firebase
                const userCredential = await signInWithEmailAndPassword(auth, email, password);
                const idToken = await userCredential.user.getIdToken();

                // 2. Відправка токена на наш бекенд
                const response = await fetch(window.appConfig.contextPath + "/auth/session", {
                    method: "POST",
                    headers: { "Content-Type": "application/json" },
                    body: JSON.stringify({ idToken: idToken })
                });

                const result = await response.json();

                if (response.ok && result.status === 'success') {
                    window.location.href = result.redirectUrl; // Йдемо на сторінку тасок
                } else {
                    throw new Error(result.message || "Помилка авторизації на сервері");
                }

            } catch (error) {
                console.error(error);
                errorAlert.textContent = "Помилка входу: перевірте email та пароль.";
                errorAlert.classList.remove('d-none');
            } finally {
                loginBtn.disabled = false;
            }
        });
    </script>
</@layout.page>