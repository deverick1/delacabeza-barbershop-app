/* ═══════════════════════════════════════
   auth.js – Login y logout
═══════════════════════════════════════ */

async function doLogin() {
  const email    = document.getElementById('loginEmail').value;
  const password = document.getElementById('loginPassword').value;
  try {
    const res = await fetch(`${API}/auth/login`, {
      method: 'POST',
      headers: { 'Content-Type': 'application/json' },
      body: JSON.stringify({ email, password })
    });
    const data = await res.json();
    if (!res.ok) throw new Error(data.error || 'Error al iniciar sesión');

    token = data.token;
    document.getElementById('sidebarUser').textContent = data.email || email;
    document.getElementById('loginPage').style.display = 'none';
    document.getElementById('appPage').style.display   = 'block';
    initApp();
  } catch (e) {
    const err = document.getElementById('loginError');
    err.textContent = '// ' + e.message + ' //';
    err.style.display = 'block';
  }
}

function doLogout() {
  token = null;
  document.getElementById('appPage').style.display  = 'none';
  document.getElementById('loginPage').style.display = 'flex';
  document.getElementById('loginEmail').value    = '';
  document.getElementById('loginPassword').value = '';
}

// Enter en el campo contraseña hace login
document.addEventListener('DOMContentLoaded', () => {
  document.getElementById('loginPassword').addEventListener('keydown', e => {
    if (e.key === 'Enter') doLogin();
  });
});