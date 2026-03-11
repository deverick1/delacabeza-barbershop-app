/* ═══════════════════════════════════════
   dashboard.js – Estadísticas y resumen
═══════════════════════════════════════ */

async function loadDashboard() {
  try {
    const today  = new Date().toISOString().split('T')[0];
    const turnos = await api(`/turnos?fecha=${today}`);

    document.getElementById('statTurnos').textContent = turnos.length;

    // Turnos preview
    const turnosDiv  = document.getElementById('dashTurnos');
    const pendientes = turnos.filter(t => t.estado === 'PENDIENTE');
    if (pendientes.length === 0) {
      turnosDiv.innerHTML = `<div style="font-family:'Space Mono',monospace; color:var(--text-dim); font-size:0.8rem;">Sin turnos pendientes</div>`;
    } else {
      turnosDiv.innerHTML = pendientes.slice(0, 4).map(t => `
        <div style="display:flex; align-items:center; gap:12px; padding:8px 0; border-bottom:1px solid var(--border);">
          <span style="font-family:'Space Mono',monospace; color:var(--violet); font-size:0.9rem; min-width:52px;">${t.hora.substring(0,5)}</span>
          <span style="font-size:0.9rem;">${t.clienteNombre}</span>
          <span class="badge-neon badge-violet ms-auto">${t.servicio}</span>
        </div>
      `).join('');
    }

    // Cola preview - usa contadores locales
    const colaDiv = document.getElementById('dashCola');
    const totalServicios = Object.values(contadores).reduce((acc, d) => acc + d.cantidad, 0);
    if (totalServicios === 0) {
      colaDiv.innerHTML = `<div style="font-family:'Space Mono',monospace; color:var(--text-dim); font-size:0.8rem;">Sin servicios registrados hoy</div>`;
    } else {
      colaDiv.innerHTML = Object.entries(contadores)
        .filter(([_, d]) => d.cantidad > 0)
        .map(([nombre, d]) => `
          <div style="display:flex; align-items:center; gap:12px; padding:8px 0; border-bottom:1px solid var(--border);">
            <span style="font-family:'Space Mono',monospace; color:var(--cyan); font-size:0.9rem; min-width:24px;">${d.cantidad}x</span>
            <span style="font-size:0.9rem;">${nombre}</span>
            <span style="font-family:'Space Mono',monospace; color:var(--violet); font-size:0.8rem; margin-left:auto;">$${(d.cantidad * d.precio).toLocaleString('es-AR')}</span>
          </div>
        `).join('');
    }
  } catch(e) {
    toast('Error cargando dashboard', 'pink');
  }
}