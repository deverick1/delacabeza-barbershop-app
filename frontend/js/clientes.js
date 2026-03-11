/* ═══════════════════════════════════════
   clientes.js – ABM de clientes
═══════════════════════════════════════ */

async function loadClientes() {
  try {
    const clientes = await api('/clientes');
    clientesCache  = clientes;
    fillClienteSelects();
    renderClientes(clientes);
  } catch(e) {
    toast('Error cargando clientes', 'pink');
  }
}


function renderClientes(clientes) {
  const tbody = document.getElementById('clientesBody');
  if (clientes.length === 0) {
    tbody.innerHTML = `<tr><td colspan="5" style="font-family:'Share Tech Mono',monospace; color:var(--text-dim); font-size:0.8rem;">Sin clientes registrados</td></tr>`;
    return;
  }
  tbody.innerHTML = clientes.map(c => `
    <tr>
      <td style="font-family:'Share Tech Mono',monospace; color:var(--text-dim);">${c.id}</td>
      <td style="font-weight:600;">${c.nombre}</td>
      <td style="font-family:'Share Tech Mono',monospace; color:var(--cyan);">${c.telefono}</td>
      <td style="color:var(--text-dim); font-size:0.85rem;">${c.email || '—'}</td>
      <td>
        <div class="d-flex gap-1">
          <button class="btn-neon-cyan btn-sm-neon" onclick="editarCliente(${c.id},'${escapeStr(c.nombre)}','${escapeStr(c.telefono)}','${escapeStr(c.email || '')}')">
            <i class="bi bi-pencil"></i>
          </button>
          <button class="btn-neon-pink btn-sm-neon" onclick="eliminarCliente(${c.id})">
            <i class="bi bi-trash"></i>
          </button>
        </div>
      </td>
    </tr>
  `).join('');
}

async function buscarClientes() {
  const q = document.getElementById('buscarCliente').value.trim();
  if (q.length === 0) { loadClientes(); return; }
  try {
    const clientes = await api(`/clientes?nombre=${encodeURIComponent(q)}`);
    renderClientes(clientes);
  } catch(e) {}
}

function editarCliente(id, nombre, telefono, email) {
  document.getElementById('modalClienteTitulo').textContent = 'Editar Cliente';
  document.getElementById('clienteId').value       = id;
  document.getElementById('clienteNombre').value   = nombre;
  document.getElementById('clienteTelefono').value = telefono;
  document.getElementById('clienteEmail').value    = email;
  document.getElementById('modalCliente').classList.add('open');
}

async function guardarCliente() {
  const id       = document.getElementById('clienteId').value;
  const nombre   = document.getElementById('clienteNombre').value.trim();
  const telefono = document.getElementById('clienteTelefono').value.trim();
  const email    = document.getElementById('clienteEmail').value.trim();
  if (!nombre || !telefono) { toast('Nombre y teléfono son obligatorios', 'pink'); return; }
  try {
    if (id) {
      await api(`/clientes/${id}`, 'PUT', { nombre, telefono, email: email || null });
      toast('Cliente actualizado');
    } else {
      await api('/clientes', 'POST', { nombre, telefono, email: email || null });
      toast('Cliente creado');
    }
    closeModal('modalCliente');
    loadClientes();
  } catch(e) {
    toast(e.message, 'pink');
  }
}

async function eliminarCliente(id) {
  try {
    await api(`/clientes/${id}`, 'DELETE');
    toast('Cliente eliminado', 'violet');
    loadClientes();
  } catch(e) {
    toast(e.message, 'pink');
  }
}