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
// 🔥 Convert backend song data → chart format
// ========================
function convertSongsToChart(songs) {
    return songs.map(s => ({
        name: s.track_name,
        value: s.popularity
    }));
}

// ========================
// Fetch data from Java server
// ========================
async function loadData() {
    const barDiv = document.getElementById('barChart');
    barDiv.innerHTML = '<p class="status">Fetching from Java server...</p>';

    document.getElementById('lineCard').style.display = 'none';
    document.getElementById('statsRow').style.display = 'none';

    let countries;

    try {
        const res = await fetch('/data'); // 
        if (!res.ok) throw new Error('HTTP ' + res.status);

        const songs = await res.json();

        console.log("RAW SONG DATA:", songs); // debug
        countries = convertSongsToChart(songs);

        console.log("CHART DATA:", countries); // debug

    } catch (e) {
        console.error("Fetch error:", e);
        barDiv.innerHTML = `<p class="status error">⚠ Could not reach Java server</p>`;
        return;
    }

    renderBarChart(countries);
    renderLineChart(countries);
    renderStats(countries);
}

// ========================
// Bar Chart
// ========================
function renderBarChart(countries) {
    if (!countries || countries.length === 0) {
        document.getElementById('barChart').innerHTML =
            '<p class="status error">No data available</p>';
        return;
    }

    const max        = Math.max(...countries.map(c => c.value));
    const GRID_STEPS = 5;
    const stepVal    = Math.ceil(max / GRID_STEPS);
    const chartMax   = stepVal * GRID_STEPS;

    let gridHTML = '<div class="gridlines">';
    for (let i = GRID_STEPS; i >= 0; i--) {
        gridHTML += `<div class="gridline"><span>${i * stepVal}</span></div>`;
    }
    gridHTML += '</div>';

    let barsHTML = '';
    countries.forEach((c, i) => {
        const pct   = (c.value / chartMax) * 100;
        const delay = (i * 0.07).toFixed(2);

        barsHTML += `
        <div class="bar-group">
            <div class="bar"
                data-value="${c.value}"
                style="height:${pct}%;background:${COLORS[i % COLORS.length]};animation-delay:${delay}s">
            </div>
            <div class="bar-label">${c.name}</div>
        </div>`;
    });

    document.getElementById('barChart').innerHTML = `
        <div class="chart-area">${gridHTML}${barsHTML}</div>
        <div class="x-axis"></div>
    `;
}

// ========================
// Line Chart
// ========================
function renderLineChart(countries) {
    document.getElementById('lineCard').style.display = 'block';

    const svg   = document.getElementById('lineChart');
    const W     = 760, H = 200, PAD = 20;

    const max   = Math.max(...countries.map(c => c.value)) * 1.1;
    const count = countries.length;

    const xs = countries.map((_, i) =>
        PAD + (i / (count - 1)) * (W - PAD * 2)
    );

    const ys = countries.map(c =>
        H - PAD - (c.value / max) * (H - PAD * 2)
    );

    const pathD = xs.map((x, i) =>
        `${i === 0 ? 'M' : 'L'} ${x} ${ys[i]}`
    ).join(' ');

    const areaD = pathD + ` L ${xs[count - 1]} ${H} L ${xs[0]} ${H} Z`;

    const dots = countries.map((c, i) =>
        `<circle class="dot" cx="${xs[i]}" cy="${ys[i]}" r="5">
            <title>${c.name}: ${c.value}</title>
        </circle>`
    ).join('');

    svg.innerHTML = `
        <defs>
            <linearGradient id="areaGrad" x1="0" y1="0" x2="0" y2="1">
                <stop offset="0%" stop-color="#ff3f6c"/>
                <stop offset="100%" stop-color="transparent"/>
            </linearGradient>
        </defs>
        <path class="area-path" d="${areaD}"/>
        <path class="line-path" d="${pathD}"/>
        ${dots}
    `;
}

// ========================
// Stats Row
// ========================
function renderStats(countries) {
    const values = countries.map(c => c.value);
    const sum    = values.reduce((a, b) => a + b, 0);
    const avg    = (sum / values.length).toFixed(1);
    const max    = Math.max(...values);
    const min    = Math.min(...values);

    const maxItem = countries.find(c => c.value === max);
    const minItem = countries.find(c => c.value === min);

    const items = [
        { label: 'Count', value: countries.length, color: '#00ffe0' },
        { label: 'Lowest', value: `${min} (${minItem.name})`, color: '#60a5fa' },
        { label: 'Highest', value: `${max} (${maxItem.name})`, color: '#ff3f6c' },
        { label: 'Average', value: avg, color: '#ffe040' },
        { label: 'Range', value: (max - min).toFixed(1), color: '#a78bfa' },
        { label: 'Sum', value: sum.toFixed(1), color: '#34d399' },
    ];

    document.getElementById('stats').innerHTML = items.map(s => `
        <div class="stat">
            <div class="stat-label">${s.label}</div>
            <div class="stat-value" style="color:${s.color}">${s.value}</div>
        </div>
    `).join('');

    document.getElementById('statsRow').style.display = 'block';
}

// ========================
// Auto-run
// ========================
loadData();