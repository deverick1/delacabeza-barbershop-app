/* ═══════════════════════════════════════
   turnos.js – Gestión de turnos
═══════════════════════════════════════ */

function estadoTurnoClass(estado) {
  if (estado === 'PENDIENTE')  return 'badge-violet';
  if (estado === 'COMPLETADO') return 'badge-green';
  if (estado === 'CANCELADO')  return 'badge-pink';
  return 'badge-dim';
}

async function loadTurnos() {
  const fecha = document.getElementById('filtroFecha').value;
  try {
    const turnos = await api(`/turnos?fecha=${fecha}`);
    const tbody  = document.getElementById('turnosBody');
    if (turnos.length === 0) {
      tbody.innerHTML = `<tr><td colspan="5" style="font-family:'Share Tech Mono',monospace; color:var(--text-dim); font-size:0.8rem;">Sin turnos para esta fecha</td></tr>`;
      return;
    }
    tbody.innerHTML = turnos.map(t => `
      <tr>
        <td style="font-family:'Share Tech Mono',monospace; color:var(--violet);">${t.hora.substring(0,5)}</td>
        <td>${t.clienteNombre}</td>
        <td><span class="badge-neon badge-cyan">${t.servicio}</span></td>
        <td><span class="badge-neon ${estadoTurnoClass(t.estado)}">${t.estado}</span></td>
        <td>
          <div class="d-flex gap-1">
            ${t.estado === 'PENDIENTE' ? `
              <button class="btn-neon-cyan btn-sm-neon" onclick="cambiarEstadoTurno(${t.id},'COMPLETADO')">
                <i class="bi bi-check2"></i>
              </button>
              <button class="btn-neon-pink btn-sm-neon" onclick="cambiarEstadoTurno(${t.id},'CANCELADO')">
                <i class="bi bi-x"></i>
              </button>
            ` : ''}
            <button class="btn-neon-violet btn-sm-neon" onclick="eliminarTurno(${t.id})">
              <i class="bi bi-trash"></i>
            </button>
          </div>
        </td>
      </tr>
    `).join('');
  } catch(e) {
    toast('Error cargando turnos', 'pink');
  }
}

async function crearTurno() {
  const clienteId = document.getElementById('turnoClienteId').value;
  const fecha     = document.getElementById('turnoFecha').value;
  const hora      = document.getElementById('turnoHora').value;
  const servicio  = document.getElementById('turnoServicio').value;
  const notas     = document.getElementById('turnoNotas').value;
  if (!clienteId || !fecha || !hora) { toast('Completá todos los campos', 'pink'); return; }
  try {
    await api('/turnos', 'POST', {
      clienteId: parseInt(clienteId),
      fecha,
      hora: hora + ':00',
      servicio,
      notas: notas || null
    });
    closeModal('modalTurno');
    toast('Turno creado');
    loadTurnos();
    loadDashboard();
  } catch(e) {
    toast(e.message, 'pink');
  }
}

async function cambiarEstadoTurno(id, estado) {
  try {
    await api(`/turnos/${id}/estado?estado=${estado}`, 'PATCH');
    toast(`Turno marcado como ${estado.toLowerCase()}`);
    loadTurnos();
    loadDashboard();
  } catch(e) {
    toast(e.message, 'pink');
  }
}

async function eliminarTurno(id) {
  try {
    await api(`/turnos/${id}`, 'DELETE');
    toast('Turno eliminado', 'violet');
    loadTurnos();
    loadDashboard();
  } catch(e) {
    toast(e.message, 'pink');
  }
}