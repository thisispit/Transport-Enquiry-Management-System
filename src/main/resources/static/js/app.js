const routesGrid = document.getElementById('routesGrid');
const statusText = document.getElementById('statusText');
const messageBar = document.getElementById('messageBar');
const routeTemplate = document.getElementById('routeCardTemplate');
const searchForm = document.getElementById('searchForm');
const sourceInput = document.getElementById('sourceInput');
const destinationInput = document.getElementById('destinationInput');
const bookingRouteInput = document.getElementById('bookingRouteId');
const pageSizeSelect = document.getElementById('pageSizeSelect');
const loadDemoRoutesBtn = document.getElementById('loadDemoRoutesBtn');
const refreshRoutesBtn = document.getElementById('refreshRoutesBtn');
const sourceSuggestions = document.getElementById('sourceSuggestions');
const destinationSuggestions = document.getElementById('destinationSuggestions');

const demoRoutes = [
  {
    id: 101,
    routeName: 'City Connector',
    source: 'Pune',
    destination: 'Mumbai',
    fare: 250,
    travelDurationMinutes: 180,
    availableSeats: 24,
    active: true,
    stops: [
      { stopName: 'Hinjewadi', stopOrder: 1 },
      { stopName: 'Swargate', stopOrder: 2 },
      { stopName: 'Panvel', stopOrder: 3 }
    ]
  },
  {
    id: 102,
    routeName: 'Metro Link',
    source: 'Delhi',
    destination: 'Noida',
    fare: 80,
    travelDurationMinutes: 45,
    availableSeats: 18,
    active: true,
    stops: [
      { stopName: 'Connaught Place', stopOrder: 1 },
      { stopName: 'Akshardham', stopOrder: 2 },
      { stopName: 'Sector 62', stopOrder: 3 }
    ]
  },
  {
    id: 106,
    routeName: 'Capital Corridor',
    source: 'Delhi',
    destination: 'Jaipur',
    fare: 320,
    travelDurationMinutes: 300,
    availableSeats: 20,
    active: true,
    stops: [
      { stopName: 'Gurugram', stopOrder: 1 },
      { stopName: 'Rewari', stopOrder: 2 },
      { stopName: 'Alwar', stopOrder: 3 }
    ]
  },
  {
    id: 107,
    routeName: 'Delhi Metro Sprint',
    source: 'Delhi',
    destination: 'Gurugram',
    fare: 60,
    travelDurationMinutes: 35,
    availableSeats: 32,
    active: true,
    stops: [
      { stopName: 'Rajiv Chowk', stopOrder: 1 },
      { stopName: 'Sikandarpur', stopOrder: 2 },
      { stopName: 'Cyber Hub', stopOrder: 3 }
    ]
  },
  {
    id: 108,
    routeName: 'Delhi Ring Link',
    source: 'Delhi',
    destination: 'Faridabad',
    fare: 90,
    travelDurationMinutes: 55,
    availableSeats: 28,
    active: true,
    stops: [
      { stopName: 'Sarita Vihar', stopOrder: 1 },
      { stopName: 'Badarpur', stopOrder: 2 },
      { stopName: 'Sector 15', stopOrder: 3 }
    ]
  },
  {
    id: 103,
    routeName: 'Coastal Express',
    source: 'Bangalore',
    destination: 'Chennai',
    fare: 520,
    travelDurationMinutes: 420,
    availableSeats: 30,
    active: true,
    stops: [
      { stopName: 'Hosur', stopOrder: 1 },
      { stopName: 'Vellore', stopOrder: 2 },
      { stopName: 'Kanchipuram', stopOrder: 3 }
    ]
  },
  {
    id: 104,
    routeName: 'Deccan Runner',
    source: 'Hyderabad',
    destination: 'Bengaluru',
    fare: 410,
    travelDurationMinutes: 360,
    availableSeats: 26,
    active: true,
    stops: [
      { stopName: 'Kurnool', stopOrder: 1 },
      { stopName: 'Anantapur', stopOrder: 2 },
      { stopName: 'Chikkaballapur', stopOrder: 3 }
    ]
  },
  {
    id: 105,
    routeName: 'North Link',
    source: 'Jaipur',
    destination: 'Delhi',
    fare: 180,
    travelDurationMinutes: 240,
    availableSeats: 22,
    active: true,
    stops: [
      { stopName: 'Ajmer', stopOrder: 1 },
      { stopName: 'Gurugram', stopOrder: 2 },
      { stopName: 'Dwarka', stopOrder: 3 }
    ]
  }
];

let routeCatalog = [...demoRoutes];

function uniqueCities(routes) {
  const cities = new Set();

  (routes || []).forEach((route) => {
    if (route?.source) cities.add(route.source);
    if (route?.destination) cities.add(route.destination);
  });

  return [...cities].sort((left, right) => left.localeCompare(right));
}

function refreshSuggestions(routes = routeCatalog) {
  const cities = uniqueCities(routes);
  const optionsMarkup = cities.map((city) => `<option value="${city}"></option>`).join('');

  if (sourceSuggestions) {
    sourceSuggestions.innerHTML = optionsMarkup;
  }

  if (destinationSuggestions) {
    destinationSuggestions.innerHTML = optionsMarkup;
  }
}

function setMessage(message, kind = 'info') {
  if (!messageBar) return;
  messageBar.textContent = message;
  messageBar.style.color = kind === 'error' ? '#ffb3b3' : '#a9b2d0';
}

function setRouteSearch(source, destination) {
  if (sourceInput) {
    sourceInput.value = source || '';
  }
  if (destinationInput) {
    destinationInput.value = destination || '';
  }
}

function focusBookingRoute(routeId) {
  if (bookingRouteInput && routeId !== undefined && routeId !== null) {
    bookingRouteInput.value = routeId;
  }
  window.location.hash = '#bookings';
  document.getElementById('page-bookings')?.scrollIntoView({ behavior: 'smooth', block: 'start' });
  setMessage(`Route #${routeId} copied into the booking form.`);
}

async function copyRouteId(routeId) {
  const text = String(routeId);
  try {
    await navigator.clipboard.writeText(text);
    setMessage(`Route ID ${text} copied to clipboard.`);
  } catch (error) {
    console.error(error);
    setMessage(`Could not copy Route ID ${text}.`, 'error');
  }
}

function setStatus(message) {
  if (statusText) {
    statusText.textContent = message;
  }
}

function formatMoney(value) {
  if (value === null || value === undefined || Number.isNaN(Number(value))) return 'N/A';
  return `₹${Number(value).toLocaleString('en-IN')}`;
}

function formatDuration(minutes) {
  if (!minutes && minutes !== 0) return 'N/A';
  const hours = Math.floor(minutes / 60);
  const mins = minutes % 60;
  return hours > 0 ? `${hours}h ${mins}m` : `${mins}m`;
}

function buildRouteLabels(route) {
  const labels = [];
  const stops = Array.isArray(route?.stops) ? route.stops : [];

  if (route?.source === 'Delhi' || route?.destination === 'Delhi') labels.push('Delhi route');
  if ((route?.travelDurationMinutes ?? 0) <= 60) labels.push('Quick hop');
  if ((route?.fare ?? 0) <= 100) labels.push('Budget');
  if ((route?.fare ?? 0) >= 400) labels.push('Premium');
  if (stops.length >= 3) labels.push('Multi-stop');
  if ((route?.availableSeats ?? 0) >= 25) labels.push('High availability');

  return labels.slice(0, 3);
}

function renderStopTimeline(route) {
  const stops = Array.isArray(route?.stops) ? route.stops : [];

  if (stops.length === 0) {
    return '<div class="stop-timeline-empty">No stops available on this route.</div>';
  }

  return `
    <div class="stop-timeline">
      ${stops
        .slice(0, 5)
        .map((stop, index) => `
          <div class="stop-timeline-item">
            <div class="stop-timeline-marker">
              <span class="stop-timeline-dot"></span>
              ${index < stops.slice(0, 5).length - 1 ? '<span class="stop-timeline-line"></span>' : ''}
            </div>
            <div class="stop-timeline-content">
              <strong>${stop.stopName}</strong>
              <span>Stop ${stop.stopOrder}</span>
            </div>
          </div>
        `)
        .join('')}
    </div>
  `;
}

function filterLocalRoutes(query = {}) {
  const source = (query.source || '').trim().toLowerCase();
  const destination = (query.destination || '').trim().toLowerCase();

  return routeCatalog.filter((route) => {
    const routeSource = String(route.source || '').toLowerCase();
    const routeDestination = String(route.destination || '').toLowerCase();

    const sourceMatch = !source || routeSource.includes(source);
    const destinationMatch = !destination || routeDestination.includes(destination);
    return sourceMatch && destinationMatch;
  });
}

function clearRoutes() {
  routesGrid.innerHTML = '';
}

function renderEmptyState(title, description) {
  routesGrid.innerHTML = `
    <article class="empty-state">
      <h3>${title}</h3>
      <p>${description}</p>
    </article>
  `;
}

function renderRoutes(routes) {
  clearRoutes();

  if (!routes || routes.length === 0) {
    renderEmptyState('No routes found', 'Try different search values or load the demo routes.');
    return;
  }

  routes.forEach((route) => {
    const card = routeTemplate.content.cloneNode(true);
    card.querySelector('.route-id').textContent = `Route #${route.id ?? 'N/A'}`;
    card.querySelector('.route-name').textContent = route.routeName ?? 'Unnamed route';
    card.querySelector('.route-status').textContent = route.active ? 'Active' : 'Inactive';
    card.querySelector('.route-status').classList.add(route.active ? 'active' : 'inactive');
    card.querySelector('.route-path').textContent = `${route.source ?? 'Unknown'} → ${route.destination ?? 'Unknown'}`;
    card.querySelector('.route-fare').textContent = formatMoney(route.fare);
    card.querySelector('.route-seats').textContent = String(route.availableSeats ?? 0);
    card.querySelector('.route-duration').textContent = formatDuration(route.travelDurationMinutes ?? 0);

    const routeMeta = card.querySelector('.route-path');
    const distanceHint = route.travelDurationMinutes ? `Travel time ${formatDuration(route.travelDurationMinutes)}` : 'Travel time N/A';
    routeMeta.textContent = `${route.source ?? 'Unknown'} → ${route.destination ?? 'Unknown'} • ${distanceHint}`;

    const tagContainer = card.querySelector('.route-tags');
    const labels = buildRouteLabels(route);
    tagContainer.innerHTML = labels.length > 0
      ? labels.map((label) => `<span class="route-tag">${label}</span>`).join('')
      : '<span class="route-tag route-tag-muted">Route preview</span>';

    const stopTimeline = card.querySelector('.route-stop-timeline');
    stopTimeline.innerHTML = renderStopTimeline(route);

    const copyButton = card.querySelector('.copy-route-btn');
    const useRouteButton = card.querySelector('.use-route-btn');

    copyButton?.addEventListener('click', () => copyRouteId(route.id));
    useRouteButton?.addEventListener('click', () => focusBookingRoute(route.id));

    routesGrid.appendChild(card);
  });
}

function normalizePageResponse(payload) {
  if (payload && Array.isArray(payload.content)) {
    return payload.content;
  }
  if (Array.isArray(payload)) {
    return payload;
  }
  return [];
}

async function fetchJson(url, options = {}) {
  const response = await fetch(url, {
    headers: {
      'Content-Type': 'application/json',
      ...(options.headers || {})
    },
    ...options
  });

  if (!response.ok) {
    const text = await response.text();
    throw new Error(text || `Request failed with status ${response.status}`);
  }

  if (response.status === 204) return null;
  return response.json();
}

async function loadRoutes(query = '') {
  setStatus('Loading routes from the backend...');
  try {
    const pageSize = pageSizeSelect?.value || 10;
    const url = query
      ? `/routes/search?source=${encodeURIComponent(query.source || '')}&destination=${encodeURIComponent(query.destination || '')}&page=0&size=${pageSize}`
      : `/routes?page=0&size=${pageSize}`;

    const data = await fetchJson(url);
    const routes = normalizePageResponse(data);

    if (routes.length > 0) {
      routeCatalog = [...routes];
      refreshSuggestions(routeCatalog);
      renderRouteHighlights();
      renderRoutes(routes);
      setStatus(`Showing ${routes.length} route(s)`);
      setMessage('Routes loaded successfully.');
    } else {
      const localRoutes = filterLocalRoutes(query);
      if (localRoutes.length > 0) {
        renderRoutes(localRoutes);
        setStatus(`Showing ${localRoutes.length} local route(s)`);
        setMessage('Using local route data because the backend returned no results.');
      } else {
        renderEmptyState('No routes returned by the API', 'You can still use the demo button to preview the interface.');
        setStatus('No routes returned.');
      }
    }
  } catch (error) {
    console.error(error);
    const localRoutes = filterLocalRoutes(query);
    if (localRoutes.length > 0) {
      renderRoutes(localRoutes);
      setStatus(`Showing ${localRoutes.length} local route(s)`);
      setMessage('Backend unavailable. Showing local route data instead.');
      return;
    }

    renderEmptyState('Backend not available', 'The UI is working, but the API did not respond. Use the demo data or start the Spring Boot app.');
    setStatus('Backend not reachable. Showing UI only.');
    setMessage('Failed to load routes from the backend.', 'error');
  }
}

function loadDemoData() {
  routeCatalog = [...demoRoutes];
  refreshSuggestions(routeCatalog);
  renderRouteHighlights();
  renderRoutes(demoRoutes);
  setStatus('Showing demo route data.');
  setMessage('Demo data loaded. Start the backend to connect real API data.');
}

async function createBooking(event) {
  event.preventDefault();

  const payload = {
    userId: Number(document.getElementById('bookingUserId').value),
    routeId: Number(document.getElementById('bookingRouteId').value),
    travelDate: document.getElementById('bookingDate').value,
    seatCount: Number(document.getElementById('bookingSeats').value)
  };

  try {
    const result = await fetchJson('/bookings', {
      method: 'POST',
      body: JSON.stringify(payload)
    });
    setMessage(`Booking created with reference ${result.bookingReference}.`);
  } catch (error) {
    console.error(error);
    setMessage('Could not create booking. Check the backend data and try again.', 'error');
  }
}

async function submitFeedback(event) {
  event.preventDefault();

  const payload = {
    userId: Number(document.getElementById('feedbackUserId').value),
    routeId: document.getElementById('feedbackRouteId').value ? Number(document.getElementById('feedbackRouteId').value) : null,
    rating: Number(document.getElementById('feedbackRating').value),
    comments: document.getElementById('feedbackComments').value.trim()
  };

  try {
    await fetchJson('/feedbacks', {
      method: 'POST',
      body: JSON.stringify(payload)
    });
    setMessage('Feedback submitted successfully.');
  } catch (error) {
    console.error(error);
    setMessage('Could not submit feedback. Make sure the backend is running.', 'error');
  }
}

function bindQuickActions() {
  document.querySelectorAll('[data-action]').forEach((button) => {
    button.addEventListener('click', () => {
      const action = button.dataset.action;
      if (action === 'openRouteDocs') window.open('/swagger-ui.html', '_blank', 'noreferrer');
      if (action === 'openBookingDocs') window.open('/swagger-ui.html', '_blank', 'noreferrer');
      if (action === 'openAdminDocs') window.open('/swagger-ui.html', '_blank', 'noreferrer');
    });
  });
}

function setActivePage(pageId) {
  document.querySelectorAll('.page').forEach((page) => {
    page.classList.toggle('active', page.id === `page-${pageId}`);
  });

  document.querySelectorAll('.nav-link[data-page]').forEach((link) => {
    link.classList.toggle('active', link.dataset.page === pageId);
  });
}

function renderRouteHighlights() {
  const recentRoutesList = document.getElementById('recentRoutesList');
  if (!recentRoutesList) return;

  const highlights = routeCatalog.slice(0, 3);
  recentRoutesList.innerHTML = highlights
    .map((route) => `
      <article class="route-highlight-card">
        <div class="route-highlight-top">
          <div>
            <p class="route-id">Route #${route.id}</p>
            <h3>${route.source} → ${route.destination}</h3>
          </div>
          <span class="pill ${route.active ? 'active' : 'inactive'}">${route.active ? 'Active' : 'Inactive'}</span>
        </div>
        <p class="route-highlight-route">${route.routeName}</p>
        <div class="route-highlight-meta">
          <span>${formatMoney(route.fare)}</span>
          <span>${formatDuration(route.travelDurationMinutes)}</span>
          <span>${route.availableSeats} seats</span>
        </div>
        <button type="button" class="secondary-btn" data-route-preset data-source="${route.source}" data-destination="${route.destination}">Explore this route</button>
      </article>
    `)
    .join('');

  recentRoutesList.querySelectorAll('[data-route-preset]').forEach((button) => {
    button.addEventListener('click', () => {
      const source = button.dataset.source || '';
      const destination = button.dataset.destination || '';
      window.location.hash = '#routes';
      setActivePage('routes');
      setRouteSearch(source, destination);
      loadRoutes({ source, destination });
    });
  });
}

function init() {
  const initialPage = window.location.hash.replace('#', '') || 'routes';
  setActivePage(initialPage);

  document.querySelectorAll('.nav-link[data-page]').forEach((link) => {
    link.addEventListener('click', (event) => {
      event.preventDefault();
      const pageId = link.dataset.page || 'home';
      window.location.hash = `#${pageId}`;
      setActivePage(pageId);
    });
  });

  window.addEventListener('hashchange', () => {
    const pageId = window.location.hash.replace('#', '') || 'routes';
    setActivePage(pageId);
  });

  searchForm?.addEventListener('submit', (event) => {
    event.preventDefault();
    const source = sourceInput?.value.trim();
    const destination = destinationInput?.value.trim();
    loadRoutes({ source, destination });
  });

  document.getElementById('bookingForm').addEventListener('submit', createBooking);
  document.getElementById('feedbackForm').addEventListener('submit', submitFeedback);
  loadDemoRoutesBtn?.addEventListener('click', loadDemoData);
  refreshRoutesBtn?.addEventListener('click', () => loadRoutes());
  pageSizeSelect?.addEventListener('change', () => loadRoutes());
  sourceInput?.addEventListener('input', () => refreshSuggestions(routeCatalog));
  destinationInput?.addEventListener('input', () => refreshSuggestions(routeCatalog));
  document.querySelectorAll('[data-route-preset]').forEach((button) => {
    button.addEventListener('click', () => {
      const source = button.dataset.source || '';
      const destination = button.dataset.destination || '';
      setRouteSearch(source, destination);
      setStatus(`Example loaded: ${source} to ${destination}`);
      setMessage(`Preset applied: ${source} → ${destination}. Press Search to load matching routes.`);
      loadRoutes({ source, destination });
    });
  });
  bindQuickActions();
  refreshSuggestions(routeCatalog);
  renderRouteHighlights();
  loadDemoData();
}

init();
