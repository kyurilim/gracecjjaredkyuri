// ========================
// Bar color palette
// ========================
const COLORS = [
    'linear-gradient(180deg, #00ffe0, #00b8a0)',
    'linear-gradient(180deg, #ff3f6c, #b02848)',
    'linear-gradient(180deg, #ffe040, #b89e00)',
    'linear-gradient(180deg, #a78bfa, #6d28d9)',
    'linear-gradient(180deg, #34d399, #059669)',
    'linear-gradient(180deg, #f97316, #c2410c)',
    'linear-gradient(180deg, #60a5fa, #2563eb)',
    'linear-gradient(180deg, #f472b6, #be185d)',
];

// ========================
// Fetch data from Java server
// Falls back to demo data if server not running
// ========================
async function loadData() {
    const barDiv = document.getElementById('barChart');
    barDiv.innerHTML = '<p class="status">Fetching from Java server...</p>';
    document.getElementById('lineCard').style.display = 'none';
    document.getElementById('statsRow').style.display = 'none';

    let songs = [];

    try {
        const res = await fetch('http://localhost:8080/data');
        if (!res.ok) throw new Error('HTTP ' + res.status);
        songs = await res.json();
    } catch (e) {

        barDiv.innerHTML = `<p class="status error">&#9888; Could not reach Java server &mdash; showing demo data</p>`;
        await new Promise(r => setTimeout(r, 700));
    }

    renderBarChart(songs);
    renderLineChart(songs);
    renderStats(songs);
}

// ========================
// Bar Chart
// ========================
function renderBarChart(songs) {
    const max        = Math.max(...songs.map(c => c.energy));
    const GRID_STEPS = 0.1;
    const stepVal    = Math.ceil(max / GRID_STEPS);
    const chartMax   = stepVal * GRID_STEPS;

    // Gridlines
    let gridHTML = '<div class="gridlines">';
    for (let i = GRID_STEPS; i >= 0; i--) {
        gridHTML += `<div class="gridline"><span>${i * stepVal}</span></div>`;
    }
    gridHTML += '</div>';

    // Bars with country name labels
    let barsHTML = '';
    songs.forEach((c, i) => {
        const pct   = (c.energy / chartMax) * 100;
        const delay = (i * 0.07).toFixed(2);
        barsHTML += `
      <div class="bar-group">
        <div class="bar"
          data-value="${c.energy}%"
          style="height:${pct}%;background:${COLORS[i % COLORS.length]};animation-delay:${delay}s">
        </div>
        <div class="bar-label">${c.track_name}</div>
      </div>`;
    });

    document.getElementById('barChart').innerHTML = `
    <div class="chart-area">${gridHTML}${barsHTML}</div>
    <div class="x-axis"></div>
  `;
}

// ========================
// Line Chart (SVG)
// ========================
function renderLineChart(songs) {
    document.getElementById('lineCard').style.display = 'block';
    const svg   = document.getElementById('lineChart');
    const W     = 760, H = 200, PAD = 20;
    const max   = Math.max(...songs.map(c => c.energy)) * 1.1;
    const count = songs.length;

    const xs = songs.map((_, i) => PAD + (i / (count - 1)) * (W - PAD * 2));
    const ys = songs.map(c => H - PAD - (c.energy / max) * (H - PAD * 2));
    const pathD = xs.map((x, i) => `${i === 0 ? 'M' : 'L'} ${x} ${ys[i]}`).join(' ');
    const areaD = pathD + ` L ${xs[count - 1]} ${H} L ${xs[0]} ${H} Z`;

    const dots = songs.map((c, i) =>
        `<circle class="dot" cx="${xs[i]}" cy="${ys[i]}" r="5"><title>${c.track_name}: ${c.energy}%</title></circle>`
    ).join('');

    svg.innerHTML = `
    <defs>
      <linearGradient id="areaGrad" x1="0" y1="0" x2="0" y2="1">
        <stop offset="0%"   stop-color="#ff3f6c"/>
        <stop offset="100%" stop-color="transparent"/>
      </linearGradient>
    </defs>
    <path class="area-path" d="${areaD}"/>
    <path class="line-path"  d="${pathD}"/>
    ${dots}
  `;
}

// ========================
// Stats Row
// ========================
function renderStats(songs) {
    const values = songs.map(c => c.energy);
    const sum    = values.reduce((a, b) => a + b, 0);
    const avg    = (sum / values.length).toFixed(1);
    const max    = Math.max(...values);
    const min    = Math.min(...values);

    // Find country names for min and max
    const maxSong = songs.find(c => c.energy === max).track_name;
    const minSong = songs.find(c => c.energy === min).track_name;

    const items = [
        { label: 'Count',    value: songs.length,          color: '#00ffe0' },
        { label: 'Lowest',   value: `${min}% (${minSong})`, color: '#60a5fa' },
        { label: 'Highest',  value: `${max}% (${maxSong})`, color: '#ff3f6c' },
        { label: 'Average',  value: avg + '%',                 color: '#ffe040' },
        { label: 'Range',    value: (max - min).toFixed(1)+'%',color: '#a78bfa' },
        { label: 'Sum',      value: sum.toFixed(1) + '%',      color: '#34d399' },
    ];

    document.getElementById('stats').innerHTML = items.map(s => `
    <div class="stat">
      <div class="stat-label">${s.label}</div>
      <div class="stat-value" style="color:${s.color}">${s.value}</div>
    </div>
  `).join('');

    document.getElementById('statsRow').style.display = 'block';
}

// Auto-run on page load
loadData();