/* ═══════════════════════════════════════
   servicios.js – Gestión de precios
═══════════════════════════════════════ */

let serviciosCache = [];

async function loadPrecios() {
  try {
    serviciosCache = await api('/servicios');
    renderPrecios();
    actualizarPreciosEnCola();
  } catch(e) {
    toast('Error cargando precios', 'pink');
  }
}

function actualizarPreciosEnCola() {
  serviciosCache.forEach(s => {
    const ids = { CORTE: 'precioCorte', BARBA: 'precioBarba', COMBO: 'precioCombo' };
    const el = document.getElementById(ids[s.nombre]);
    if (el) el.textContent = '$' + Number(s.precio).toLocaleString('es-AR');
  });
}

function renderPrecios() {
  const div = document.getElementById('preciosList');
  if (!div) return;
  div.innerHTML = serviciosCache.map(s => `
    <div class="col-md-4">
      <div class="precio-card">
        <div class="precio-servicio-nombre">${s.nombre}</div>
        <div class="precio-actual" id="precioDisplay${s.id}">$${Number(s.precio).toLocaleString('es-AR')}</div>
        <div class="form-group">
          <div class="input-label">Nuevo precio</div>
          <input type="number" id="precioInput${s.id}" class="neon-input" placeholder="Ingresá el nuevo precio" min="0"/>
        </div>
        <button class="btn-neon-cyan" style="margin-top:8px;" onclick="guardarPrecio(${s.id})">
          <i class="bi bi-floppy-fill me-2"></i> Guardar
        </button>
      </div>
    </div>
  `).join('');
}

async function guardarPrecio(id) {
  const input = document.getElementById('precioInput' + id);
  const valor = parseFloat(input.value);
  if (!valor || valor <= 0) { toast('Ingresá un precio válido', 'pink'); return; }
  try {
    const actualizado = await api('/servicios/' + id + '/precio', 'PATCH', { precio: valor });
    const idx = serviciosCache.findIndex(s => s.id === id);
    if (idx !== -1) serviciosCache[idx] = actualizado;
    input.value = '';
    document.getElementById('precioDisplay' + id).textContent = '$' + Number(actualizado.precio).toLocaleString('es-AR');
    actualizarPreciosEnCola();
    toast('Precio actualizado', 'cyan');
  } catch(e) {
    toast(e.message, 'pink');
  }
}