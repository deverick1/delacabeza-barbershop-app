/* ═══════════════════════════════════════
   app.js – Configuración global y helpers
═══════════════════════════════════════ */

const API = 'http://localhost:8080/api';
let token = null;
let clientesCache = [];

// ─── API HELPER ───
async function api(path, method = 'GET', body = null) {
  const opts = {
    method,
    headers: {
      'Authorization': `Bearer ${token}`,
      'Content-Type': 'application/json'
    }
  };
  if (body) opts.body = JSON.stringify(body);
  const res = await fetch(`${API}${path}`, opts);
  if (res.status === 204) return null;
  const data = await res.json();
  if (!res.ok) throw new Error(data.error || 'Error en la solicitud');
  return data;
}

// ─── TOAST ───
function toast(msg, type = 'cyan') {
  const el = document.getElementById('toastAlert');
  el.textContent = '// ' + msg + ' //';
  el.style.borderColor = type === 'pink'   ? 'var(--pink)'   :
                         type === 'violet' ? 'var(--violet)' : 'var(--cyan)';
  el.style.color       = type === 'pink'   ? 'var(--pink)'   :
                         type === 'violet' ? 'var(--violet)' : 'var(--cyan)';
  el.style.boxShadow   = type === 'pink'   ? 'var(--glow-p)' :
                         type === 'violet' ? 'var(--glow-v)' : 'var(--glow-c)';
  el.style.display = 'block';
  setTimeout(() => el.style.display = 'none', 3000);
}

// ─── MODALS ───
function openModal(id) {
  if (id === 'modalCliente') {
    if (!document.getElementById('clienteId').value) {
      document.getElementById('modalClienteTitulo').textContent = 'Nuevo Cliente';
      document.getElementById('clienteNombre').value   = '';
      document.getElementById('clienteTelefono').value = '';
      document.getElementById('clienteEmail').value    = '';
      document.getElementById('clienteId').value       = '';
    }
  }
  document.getElementById(id).classList.add('open');
}

function closeModal(id) {
  document.getElementById(id).classList.remove('open');
}

// ─── NAVIGATION ───
function showSection(name) {
  document.querySelectorAll('.page-section').forEach(s => s.classList.remove('active'));
  document.querySelectorAll('.nav-item-custom').forEach(n => n.classList.remove('active'));
  document.getElementById('section-' + name).classList.add('active');
  document.getElementById('nav-' + name).classList.add('active');
  if (name === 'cola')      loadCola();
  if (name === 'turnos')    loadTurnos();
  if (name === 'clientes')  loadClientes();
  if (name === 'dashboard') loadDashboard();
  if (name === 'precios')   loadPrecios();
}

// ─── CLIENTES CACHE ───
async function loadClientesCache() {
  try {
    clientesCache = await api('/clientes');
    fillClienteSelects();
  } catch(e) {}
}

function fillClienteSelects() {
  const selects = ['colaClienteId', 'turnoClienteId'];
  selects.forEach(id => {
    const sel = document.getElementById(id);
    if (!sel) return;
    sel.innerHTML = clientesCache.map(c =>
      `<option value="${c.id}">${c.nombre} – ${c.telefono}</option>`
    ).join('');
  });
}


function escapeStr(str) {
  return str.replace(/'/g, "\\'");
}

// ─── INIT APP ───
async function initApp() {
  await loadClientesCache();
  await loadPrecios();
  loadDashboard();
  const today = new Date().toISOString().split('T')[0];
  document.getElementById('filtroFecha').value = today;
}