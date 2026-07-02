let currentSearchMode = 'partial';
const API_URL = '/api/countries';

document.addEventListener('DOMContentLoaded', () => {
    loadCountries();
});

function addLog(message, type = 'info') {
    const consoleOutput = document.getElementById('consoleOutput');
    const timestamp = new Date().toLocaleTimeString();
    const logElement = document.createElement('span');
    logElement.className = `log-entry ${type}-log`;
    logElement.textContent = `[${timestamp}] ${message}`;
    consoleOutput.appendChild(logElement);
    consoleOutput.scrollTop = consoleOutput.scrollHeight;
}

function clearLogs() {
    const consoleOutput = document.getElementById('consoleOutput');
    consoleOutput.innerHTML = `<span class="log-entry system-log">[System] Logs cleared. Console ready.</span>`;
}

function showToast(message, type = 'success') {
    const toast = document.getElementById('toast');
    const toastMessage = document.getElementById('toastMessage');
    toast.className = `toast show ${type}`;
    toastMessage.textContent = message;
    
    setTimeout(() => {
        toast.classList.remove('show');
    }, 4000);
}

function switchSearchTab(mode) {
    currentSearchMode = mode;
    document.querySelectorAll('.tab-btn').forEach(btn => btn.classList.remove('active'));
    document.getElementById('partialSearchGroup').classList.remove('active');
    document.getElementById('exactSearchGroup').classList.remove('active');

    const activeBtn = event.currentTarget;
    activeBtn.classList.add('active');

    if (mode === 'partial') {
        document.getElementById('partialSearchGroup').classList.add('active');
        loadCountries();
    } else {
        document.getElementById('exactSearchGroup').classList.add('active');
    }
}

async function loadCountries() {
    const nameQuery = document.getElementById('searchName').value.trim();
    let url = API_URL;
    if (nameQuery) {
        url += `?name=${encodeURIComponent(nameQuery)}`;
    }
    
    try {
        const response = await fetch(url);
        if (!response.ok) throw new Error('Failed to load countries');
        const countries = await response.json();
        
        populateTable(countries);
        document.getElementById('recordCount').textContent = countries.length;
        
        if (nameQuery) {
            addLog(`Searched for partial name "${nameQuery}". Found ${countries.length} records.`, 'success');
        } else {
            addLog(`Loaded full registry. Found ${countries.length} records.`, 'system');
        }
    } catch (error) {
        addLog(`Fetch Error: ${error.message}`, 'error');
        showToast(error.message, 'danger');
    }
}

function handleSearch() {
    loadCountries();
}

async function lookupByCode() {
    const code = document.getElementById('searchCode').value.trim().toUpperCase();
    if (!code) {
        showToast('Please enter a country code to lookup', 'danger');
        return;
    }
    
    addLog(`Initiating lookup for Country Code: "${code}"`, 'info');
    try {
        const response = await fetch(`${API_URL}/${code}`);
        if (response.status === 404) {
            addLog(`No country found with code: "${code}"`, 'error');
            showToast(`Country with code "${code}" not found.`, 'danger');
            populateTable([]);
            document.getElementById('recordCount').textContent = 0;
            return;
        }
        if (!response.ok) throw new Error('API request failed');
        const country = await response.json();
        
        populateTable([country]);
        document.getElementById('recordCount').textContent = 1;
        addLog(`Successfully retrieved country "${country.coName}" (${country.coCode})`, 'success');
        showToast(`Retrieved ${country.coName}`, 'success');
    } catch (error) {
        addLog(`Error looking up code "${code}": ${error.message}`, 'error');
        showToast(error.message, 'danger');
    }
}

function populateTable(countries) {
    const tbody = document.getElementById('countryTableBody');
    tbody.innerHTML = '';
    
    if (countries.length === 0) {
        tbody.innerHTML = `<tr><td colspan="3" style="text-align: center; color: var(--text-secondary); padding: 2rem;">No countries found.</td></tr>`;
        return;
    }
    
    countries.forEach(country => {
        const tr = document.createElement('tr');
        tr.innerHTML = `
            <td><span class="code-badge">${escapeHTML(country.coCode)}</span></td>
            <td><strong>${escapeHTML(country.coName)}</strong></td>
            <td style="text-align: center;">
                <button class="btn btn-danger" onclick="deleteCountry('${country.coCode}')">Delete</button>
            </td>
        `;
        tbody.appendChild(tr);
    });
}

async function handleAddCountry(e) {
    e.preventDefault();
    const code = document.getElementById('newCode').value.trim().toUpperCase();
    const name = document.getElementById('newName').value.trim();
    
    if (!code || !name) {
        showToast('Code and Name are required!', 'danger');
        return;
    }
    
    addLog(`Adding country: "${name}" [${code}]`, 'info');
    try {
        const response = await fetch(API_URL, {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify({ coCode: code, coName: name })
        });
        
        const data = await response.json();
        
        if (!response.ok) {
            throw new Error(data.message || 'Failed to add country');
        }
        
        addLog(`Successfully added "${data.coName}" [${data.coCode}] via Spring Data JPA`, 'success');
        showToast(`Registered "${data.coName}" successfully!`, 'success');
        document.getElementById('addCountryForm').reset();
        loadCountries();
    } catch (error) {
        addLog(`Failed to add country: ${error.message}`, 'error');
        showToast(error.message, 'danger');
    }
}

async function fetchCountryForUpdate() {
    const code = document.getElementById('updateCode').value.trim().toUpperCase();
    if (!code) return;
    
    try {
        const response = await fetch(`${API_URL}/${code}`);
        if (response.ok) {
            const country = await response.json();
            document.getElementById('updateName').value = country.coName;
            addLog(`Prefetched data for update: "${country.coName}" [${code}]`, 'info');
        }
    } catch (error) {
        // ignore
    }
}

async function handleUpdateCountry(e) {
    e.preventDefault();
    const code = document.getElementById('updateCode').value.trim().toUpperCase();
    const name = document.getElementById('updateName').value.trim();
    
    if (!code || !name) {
        showToast('Code and New Name are required!', 'danger');
        return;
    }
    
    addLog(`Updating country code "${code}" with name "${name}"`, 'info');
    try {
        const response = await fetch(`${API_URL}/${code}`, {
            method: 'PUT',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify({ coCode: code, coName: name })
        });
        
        const data = await response.json();
        
        if (!response.ok) {
            throw new Error(data.message || 'Failed to update country');
        }
        
        addLog(`Successfully updated "${data.coCode}" to "${data.coName}"`, 'success');
        showToast(`Updated "${data.coName}" successfully!`, 'success');
        document.getElementById('updateCountryForm').reset();
        loadCountries();
    } catch (error) {
        addLog(`Failed to update country: ${error.message}`, 'error');
        showToast(error.message, 'danger');
    }
}

async function deleteCountry(code) {
    if (!confirm(`Are you sure you want to delete country with code "${code}"?`)) return;
    
    addLog(`Deleting country with code: "${code}"`, 'info');
    try {
        const response = await fetch(`${API_URL}/${code}`, {
            method: 'DELETE'
        });
        
        if (!response.ok) {
            const data = await response.json();
            throw new Error(data.message || 'Failed to delete country');
        }
        
        addLog(`Successfully deleted country "${code}"`, 'success');
        showToast(`Deleted country "${code}"`, 'success');
        loadCountries();
    } catch (error) {
        addLog(`Failed to delete country: ${error.message}`, 'error');
        showToast(error.message, 'danger');
    }
}

function escapeHTML(str) {
    return str.replace(/[&<>'"]/g, 
        tag => ({
            '&': '&amp;',
            '<': '&lt;',
            '>': '&gt;',
            "'": '&#39;',
            '"': '&quot;'
        }[tag] || tag)
    );
}
