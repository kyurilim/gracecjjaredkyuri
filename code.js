const COLORS = [
    ['#00ffe0', '#00b8a0'],
    ['#ff3f6c', '#b02848'],
    ['#ffe040', '#b89e00'],
    ['#a78bfa', '#6d28d9'],
    ['#34d399', '#059669'],
    ['#f97316', '#c2410c'],
    ['#60a5fa', '#2563eb'],
    ['#f472b6', '#be185d'],
];

async function renderBarChart() {
    try {
        const res = await fetch("http://localhost:8080/data");
const raw = await res.text();

// Show raw around the broken position
console.log("Around position 29103:", raw.substring(29080, 29130));

const json = JSON.parse(raw);
    

        // Sort + top 10
        json.sort((a, b) => Number(b.value) - Number(a.value));
        const top = json.slice(0, 10);

        const labels = top.map(item => item.name);
        const values = top.map(item => Number(item.value));

        // Replace loading text with canvas
        document.getElementById("barChart").innerHTML =
            '<canvas id="chart" height="300"></canvas>';

        const canvas = document.getElementById("chart");
        const ctx = canvas.getContext("2d");

        await new Promise(requestAnimationFrame);

        const gradients = values.map((_, i) => {
            const gradient = ctx.createLinearGradient(0, 0, 0, canvas.height);
            const [start, end] = COLORS[i % COLORS.length];
            gradient.addColorStop(0, start);
            gradient.addColorStop(1, end);
            return gradient;
        });

        new Chart(ctx, {
            type: "bar",
            data: {
                labels,
                datasets: [{
                    data: values,
                    backgroundColor: gradients,
                    borderRadius: 10
                }]
            },
            options: {
                responsive: true,
                maintainAspectRatio: false,
                plugins: { legend: { display: false } },
                scales: {
                    x: { ticks: { color: "#aaa", maxRotation: 45 } },
                    y: { ticks: { color: "#aaa" }, beginAtZero: true }
                }
            }
        });

    } catch (err) {
        console.error("ERROR:", err);

        // Show error on page
        const errBox = document.getElementById("errors");
        errBox.style.display = "block";
        errBox.innerHTML = `<pre style="color:#ff3f6c; padding:16px; font-family:monospace;">
❌ ERROR: ${err.message}

fetch URL: http://localhost:8080/data
Check: Is your Java server running?
</pre>`;

        // Also update the chart area
        document.getElementById("barChart").innerText = "Failed to load data.";
    }
}

window.onload = renderBarChart;

function loadData() {
    // Clear previous error before retrying
    const errBox = document.getElementById("errors");
    errBox.style.display = "none";
    errBox.innerHTML = "";

    // Reset loading text
    document.getElementById("barChart").innerHTML =
        '<p id="loadingText">Fetching from Java server...</p>';

    renderBarChart();
}