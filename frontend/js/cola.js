/* ═══════════════════════════════════════
   cola.js – Orden de llegada / Contadores
═══════════════════════════════════════ */

// contadores[nombre] = { cantidad, precio }
let contadores = {};

function inicializarContadores(servicios) {
  servicios.forEach(s => {
    if (!contadores[s.nombre]) {
      contadores[s.nombre] = { cantidad: 0, precio: Number(s.precio) };
    } else {
      contadores[s.nombre].precio = Number(s.precio);
    }
  });
  renderContadores();
}

function renderContadores() {
  const container = document.getElementById('contadoresContainer');
  if (!container) return;

  container.innerHTML = Object.entries(contadores).map(([nombre, data]) => `
    <div class="col-md-4 col-sm-6">
      <div class="neon-card contador-card">
        <div class="contador-servicio">${nombre}</div>
        <div class="contador-precio">$${data.precio.toLocaleString('es-AR')}</div>
        <div class="contador-numero ${data.cantidad > 0 ? 'activo' : ''}" id="cnt-${nombre}">${data.cantidad}</div>
        <div class="contador-label">realizados hoy</div>
        <div class="d-flex gap-2 mt-3 justify-content-center">
          <button class="btn-neon-pink" style="width:52px; height:52px; font-size:1.3rem;" onclick="restarServicio('${nombre}')" title="Restar uno">
            <i class="bi bi-dash-lg"></i>
          </button>
          <button class="btn-neon-cyan" style="width:52px; height:52px; font-size:1.3rem;" onclick="sumarServicio('${nombre}')" title="Sumar uno">
            <i class="bi bi-plus-lg"></i>
          </button>
        </div>
      </div>
    </div>
  `).join('');

  actualizarTotales();
}

function sumarServicio(nombre) {
  if (!contadores[nombre]) return;
  contadores[nombre].cantidad++;
  const el = document.getElementById('cnt-' + nombre);
  if (el) {
    el.textContent = contadores[nombre].cantidad;
    el.classList.add('activo');
  }
  actualizarTotales();
  toast('+1 ' + nombre, 'cyan');
}

function restarServicio(nombre) {
  if (!contadores[nombre] || contadores[nombre].cantidad <= 0) {
    toast('No hay nada que restar en ' + nombre, 'pink');
    return;
  }
  contadores[nombre].cantidad--;
  const el = document.getElementById('cnt-' + nombre);
  if (el) {
    el.textContent = contadores[nombre].cantidad;
    el.classList.toggle('activo', contadores[nombre].cantidad > 0);
  }
  actualizarTotales();
  toast('-1 ' + nombre, 'violet');
}

function actualizarTotales() {
  let totalDinero = 0;
  let totalCantidad = 0;
  Object.values(contadores).forEach(d => {
    totalDinero += d.cantidad * d.precio;
    totalCantidad += d.cantidad;
  });
  const elDinero = document.getElementById('totalRecaudado');
  const elCant   = document.getElementById('totalServicios');
  if (elDinero) elDinero.textContent = '$' + totalDinero.toLocaleString('es-AR');
  if (elCant)   elCant.textContent   = totalCantidad;
}

async function crearNuevoServicio() {
  const nombre = document.getElementById('nuevoServicioNombre').value.trim().toUpperCase();
  const precio = parseFloat(document.getElementById('nuevoServicioPrecio').value);
  if (!nombre) { toast('Ingresá un nombre', 'pink'); return; }
  if (!precio || precio <= 0) { toast('Ingresá un precio válido', 'pink'); return; }
  try {
    const nuevo = await api('/servicios', 'POST', { nombre, precio });
    contadores[nuevo.nombre] = { cantidad: 0, precio: Number(nuevo.precio) };
    serviciosCache.push(nuevo);
    renderContadores();
    closeModal('modalNuevoServicio');
    document.getElementById('nuevoServicioNombre').value = '';
    document.getElementById('nuevoServicioPrecio').value = '';
    toast('Servicio ' + nuevo.nombre + ' creado', 'cyan');
  } catch(e) {
    toast(e.message, 'pink');
  }
}

function loadCola() {
  inicializarContadores(serviciosCache);
}