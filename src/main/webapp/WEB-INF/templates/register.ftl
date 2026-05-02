<#import "layout.ftl" as layout>

<@layout.page title="Реєстрація">
    <div class="row justify-content-center">
        <div class="col-md-6 col-lg-4">
            <div class="card p-4 shadow">
                <h2 class="mb-3 text-center">Реєстрація</h2>

                <div id="errorAlert" class="alert alert-danger d-none"></div>

                <form id="registerForm">
                    <!-- Зауваж: Firebase Auth (Email/Pass) за замовчуванням не приймає ім'я при створенні,
                         але ми можемо оновити його профайл пізніше, або згенерувати ім'я на бекенді з email-у (як ми зробили в сервісі) -->
                    <div class="mb-3">
                        <label class="form-label">Email</label>
                        <input type="email" id="email" class="form-control" required>
                    </div>
                    <div class="mb-3">
                        <label class="form-label">Пароль (мінімум 6 символів)</label>
                        <input type="password" id="password" class="form-control" required minlength="6">
                    </div>
                    <button type="submit" class="btn btn-success w-100" id="regBtn">Зареєструватися</button>
                </form>

                <a href="${contextPath}/user/login" class="mt-3 d-block text-center">Вже є акаунт? Увійти</a>
            </div>
        </div>
    </div>

    <script type="module">
        import { initializeApp } from "https://www.gstatic.com/firebasejs/10.8.1/firebase-app.js";
        import { getAuth, createUserWithEmailAndPassword } from "https://www.gstatic.com/firebasejs/10.8.1/firebase-auth.js";

        const app = initializeApp(window.appConfig.firebaseConfig);
        const auth = getAuth(app);

        const registerForm = document.getElementById('registerForm');
        const errorAlert = document.getElementById('errorAlert');
        const regBtn = document.getElementById('regBtn');

        registerForm.addEventListener('submit', async (e) => {
            e.preventDefault();
            regBtn.disabled = true;
            errorAlert.classList.add('d-none');

            const email = document.getElementById('email').value;
            const password = document.getElementById('password').value;

            try {
                // 1. Реєстрація у Firebase
                const userCredential = await createUserWithEmailAndPassword(auth, email, password);
                const idToken = await userCredential.user.getIdToken();

                // 2. Відправка токена на наш бекенд для створення юзера в MySQL
                const response = await fetch(window.appConfig.contextPath + "/auth/session", {
                    method: "POST",
                    headers: { "Content-Type": "application/json" },
                    body: JSON.stringify({ idToken: idToken })
                });

                const result = await response.json();

                if (response.ok && result.status === 'success') {
                    window.location.href = result.redirectUrl;
                } else {
                    throw new Error(result.message);
                }

            } catch (error) {
                console.error(error);
                errorAlert.textContent = "Помилка реєстрації: " + error.message;
                errorAlert.classList.remove('d-none');
            } finally {
                regBtn.disabled = false;
            }
        });
    </script>
</@layout.page>