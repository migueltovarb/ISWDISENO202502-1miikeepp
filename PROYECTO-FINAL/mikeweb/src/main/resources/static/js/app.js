const API_URL = '/api';

// Auth
async function login(email, password) {
    const response = await fetch(`${API_URL}/auth/login`, {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify({ email, password })
    });
    if (response.ok) {
        const user = await response.json();
        localStorage.setItem('user', JSON.stringify(user));
        window.location.href = user.role === 'ADMIN' ? 'admin.html' : 'dashboard.html';
    } else {
        alert('Login failed');
    }
}

async function register(name, email, password) {
    const response = await fetch(`${API_URL}/auth/register`, {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify({ name, email, password, role: 'STUDENT' })
    });
    if (response.ok) {
        alert('Registration successful! Please login.');
        toggleAuthMode();
    } else {
        alert('Registration failed');
    }
}

function logout() {
    localStorage.removeItem('user');
    window.location.href = 'index.html';
}

function checkAuth() {
    const user = JSON.parse(localStorage.getItem('user'));
    if (!user) {
        window.location.href = 'index.html';
    }
    return user;
}

// Rooms
async function getRooms() {
    const response = await fetch(`${API_URL}/salas`);
    return await response.json();
}

async function createRoom(room) {
    const response = await fetch(`${API_URL}/salas`, {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify(room)
    });
    return response.ok;
}

// Reservations
async function createReservation(roomId, start, end) {
    const user = JSON.parse(localStorage.getItem('user'));
    const response = await fetch(`${API_URL}/reservas`, {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify({
            userId: user.id,
            roomId: roomId,
            startTime: start,
            endTime: end
        })
    });
    if (response.ok) {
        alert('Reservation confirmed!');
        loadMyReservations();
    } else {
        const msg = await response.text();
        alert('Error: ' + msg);
    }
}

async function getMyReservations() {
    const user = JSON.parse(localStorage.getItem('user'));
    const response = await fetch(`${API_URL}/reservas/mias?userId=${user.id}`);
    return await response.json();
}

async function cancelReservation(id) {
    if (!confirm('Are you sure?')) return;
    const response = await fetch(`${API_URL}/reservas/${id}`, {
        method: 'DELETE'
    });
    if (response.ok) {
        loadMyReservations();
    }
}

async function deleteRoom(id) {
    if (!confirm('Are you sure?')) return;
    const response = await fetch(`${API_URL}/salas/${id}`, {
        method: 'DELETE'
    });
    if (response.ok) {
        alert('Room deleted');
        if (typeof loadAdminRooms === 'function') {
            loadAdminRooms();
        }
    } else {
        const msg = await response.text();
        alert('Error: ' + msg);
    }
}

async function getSports() {
    const response = await fetch(`${API_URL}/deportes`);
    return await response.json();
}

async function getTeamsBySport(sportId) {
    const response = await fetch(`${API_URL}/deportes/${sportId}/equipos`);
    return await response.json();
}

async function joinTeam(teamId) {
    const user = JSON.parse(localStorage.getItem('user'));
    const response = await fetch(`${API_URL}/equipos/${teamId}/miembros/${user.id}`, {
        method: 'POST'
    });
    return response.ok;
}

async function getSanctionsByStudent(studentId) {
    const response = await fetch(`${API_URL}/sanciones/por-estudiante/${studentId}`);
    return await response.json();
}

async function createSport(name) {
    const user = JSON.parse(localStorage.getItem('user'));
    const response = await fetch(`${API_URL}/deportes?adminUserId=${user.id}`, {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify({ name })
    });
    return response.ok ? await response.json() : null;
}

async function updateSport(sportId, name) {
    const user = JSON.parse(localStorage.getItem('user'));
    const response = await fetch(`${API_URL}/deportes/${sportId}?adminUserId=${user.id}`, {
        method: 'PUT',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify({ name })
    });
    return response.ok ? await response.json() : null;
}

async function createTeam(sportId, name) {
    const user = JSON.parse(localStorage.getItem('user'));
    const response = await fetch(`${API_URL}/deportes/${sportId}/equipos?adminUserId=${user.id}`, {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify({ name })
    });
    return response.ok ? await response.json() : null;
}

async function deleteTeam(sportId, teamId) {
    const user = JSON.parse(localStorage.getItem('user'));
    const response = await fetch(`${API_URL}/deportes/${sportId}/equipos/${teamId}?adminUserId=${user.id}`, {
        method: 'DELETE'
    });
    return response.ok;
}

async function addSanctionByEmail(studentEmail, reason, expiresAt) {
    const user = JSON.parse(localStorage.getItem('user'));
    const params = new URLSearchParams({ adminUserId: user.id, studentEmail, reason });
    if (expiresAt) params.set('expiresAt', expiresAt);
    const response = await fetch(`${API_URL}/sanciones/por-email?${params.toString()}`, { method: 'POST' });
    return response.ok ? await response.json() : null;
}

// expose globally for inline scripts
window.getSports = getSports;
window.getTeamsBySport = getTeamsBySport;
window.createSport = createSport;
window.updateSport = updateSport;
window.createTeam = createTeam;
window.deleteTeam = deleteTeam;
window.addSanctionByEmail = addSanctionByEmail;
window.joinTeam = joinTeam;
