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

                <hr class="my-4"> <!-- Розділювач -->
                <button type="button" class="btn btn-outline-danger w-100" id="googleBtn">
                    <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-google me-2" viewBox="0 0 16 16">
                        <path d="M15.545 6.558a9.42 9.42 0 0 1 .139 1.626c0 2.434-.87 4.492-2.384 5.885h.002C11.978 15.292 10.158 16 8 16A8 8 0 1 1 8 0a7.689 7.689 0 0 1 5.352 2.082l-2.284 2.284A4.347 4.347 0 0 0 8 3.166c-2.087 0-3.86 1.408-4.492 3.304a4.792 4.792 0 0 0 0 3.063h.003c.635 1.893 2.405 3.301 4.492 3.301 1.078 0 2.004-.276 2.722-.764h-.003a3.702 3.702 0 0 0 1.599-2.431H8v-3.08h7.545z"/>
                    </svg>
                    Увійти через Google
                </button>

                <a href="${contextPath}/user/register" class="mt-3 d-block text-center">Ще немає акаунта? Зареєструватися</a>
            </div>
        </div>
    </div>

    <script type="module">
        import { initializeApp } from "https://www.gstatic.com/firebasejs/10.8.1/firebase-app.js";
        import { getAuth, signInWithEmailAndPassword, GoogleAuthProvider, signInWithPopup} from "https://www.gstatic.com/firebasejs/10.8.1/firebase-auth.js";

        const app = initializeApp(window.appConfig.firebaseConfig);
        const auth = getAuth(app);

        const googleProvider = new GoogleAuthProvider();

        const loginForm = document.getElementById('loginForm');
        const errorAlert = document.getElementById('errorAlert');
        const loginBtn = document.getElementById('loginBtn');

        const googleBtn = document.getElementById('googleBtn');
        if(googleBtn) {
            googleBtn.addEventListener('click', async () => {
                googleBtn.disabled = true; // блокуємо кнопку, щоб не клікали двічі

                try {
                    // 1. Викликаємо вікно Google Авторизації
                    const result = await signInWithPopup(auth, googleProvider);

                    // 2. Отримуємо токен (так само, як і при email)
                    const idToken = await result.user.getIdToken();

                    // 3. Відправляємо токен на наш Java-бекенд
                    const response = await fetch(window.appConfig.contextPath + "/auth/session", {
                        method: "POST",
                        headers: { "Content-Type": "application/json" },
                        body: JSON.stringify({ idToken: idToken })
                    });

                    const serverResult = await response.json();

                    if (response.ok && serverResult.status === 'success') {
                        // Успіх! Переходимо на сторінку тасок
                        window.location.href = serverResult.redirectUrl;
                    } else {
                        throw new Error(serverResult.message);
                    }
                } catch (error) {
                    console.error(error);
                    alert("Помилка входу через Google: " + error.message);
                } finally {
                    googleBtn.disabled = false;
                }
            });
        }

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