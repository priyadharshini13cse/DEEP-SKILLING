// API endpoints
const API_BASE = '/api';

// DOM elements
const empForm = document.getElementById('employeeForm');
const empName = document.getElementById('empName');
const empEmail = document.getElementById('empEmail');
const empDept = document.getElementById('empDept');
const empSalary = document.getElementById('empSalary');

const btnHibernate = document.getElementById('btnHibernate');
const btnSpringData = document.getElementById('btnSpringData');

const codeDisplay = document.getElementById('code-display');
const logOutput = document.getElementById('log-output');
const sqlOutput = document.getElementById('sql-output');
const activeTabIndicator = document.getElementById('active-tab-indicator');

// Pipeline DOM elements
const steps = {
    client: document.getElementById('step-client'),
    controller: document.getElementById('step-controller'),
    service: document.getElementById('step-service'),
    orm: document.getElementById('step-orm'),
    db: document.getElementById('step-db'),
    proxy: document.getElementById('proxy-wrapper')
};

const arrows = {
    1: document.getElementById('arrow-1'),
    2: document.getElementById('arrow-2'),
    3: document.getElementById('arrow-3'),
    4: document.getElementById('arrow-4')
};

const labelService = document.getElementById('service-name');
const detailService = document.getElementById('service-detail');
const labelOrm = document.getElementById('orm-name');
const detailOrm = document.getElementById('orm-detail');

// Load employees table on load
document.addEventListener('DOMContentLoaded', () => {
    fetchEmployees();
});

// Fetch all employees from backend
async function fetchEmployees() {
    try {
        const response = await fetch(`${API_BASE}/employees`);
        if (!response.ok) throw new Error('Failed to fetch employees');
        
        const employees = await response.ok ? await response.json() : [];
        renderEmployees(employees);
    } catch (err) {
        console.error(err);
        appendConsoleLog('system-msg', 'Failed to retrieve database records.');
    }
}

// Render database records in table
function renderEmployees(employees) {
    const tbody = document.getElementById('employeesBody');
    if (!employees || employees.length === 0) {
        tbody.innerHTML = `
            <tr>
                <td colspan="6" class="empty-state">No employee records in H2 database memory. Add employees using the buttons above.</td>
            </tr>
        `;
        return;
    }
    
    tbody.innerHTML = employees.map(emp => {
        const badgeClass = emp.createdVia === 'Hibernate' ? 'badge-hibernate' : 'badge-spring';
        const formattedSalary = new Intl.NumberFormat('en-US', { style: 'currency', currency: 'USD', maximumFractionDigits: 0 }).format(emp.salary);
        return `
            <tr>
                <td>${emp.id}</td>
                <td><strong>${escapeHtml(emp.name)}</strong></td>
                <td>${escapeHtml(emp.email)}</td>
                <td>${escapeHtml(emp.department)}</td>
                <td>${formattedSalary}</td>
                <td><span class="badge ${badgeClass}">${emp.createdVia}</span></td>
            </tr>
        `;
    }).join('');
}

// Clear database records
async function clearDatabase() {
    if (!confirm('Are you sure you want to delete all database records?')) return;
    
    try {
        const response = await fetch(`${API_BASE}/employees`, { method: 'DELETE' });
        if (response.ok) {
            appendConsoleLog('system-msg', 'Database cleared successfully.');
            resetPipelineColors();
            codeDisplay.innerHTML = '// Select a method above to run and inspect execution...';
            sqlOutput.textContent = '-- Awaiting persistence action...';
            activeTabIndicator.textContent = 'Ready';
            activeTabIndicator.className = 'terminal-tab';
            fetchEmployees();
        }
    } catch (err) {
        appendConsoleLog('error', 'Error clearing database: ' + err.message);
    }
}

// Main logic to save employee
async function saveEmployee(type) {
    // Basic Form validation
    if (!empName.value || !empEmail.value || !empDept.value || !empSalary.value) {
        alert('Please fill out all employee fields.');
        return;
    }

    const payload = {
        name: empName.value,
        email: empEmail.value,
        department: empDept.value,
        salary: parseFloat(empSalary.value)
    };

    // Toggle loading UI states
    setFormDisabled(true);
    resetPipelineColors();
    clearConsole();

    // Set UI Header values based on mode
    if (type === 'hibernate') {
        activeTabIndicator.textContent = 'Hibernate native';
        activeTabIndicator.className = 'terminal-tab tab-hibernate';
        labelService.textContent = 'HibernateEmployeeService';
        detailService.textContent = 'manual transaction management';
        labelOrm.textContent = 'Hibernate Session';
        detailOrm.textContent = 'translating entity state via session.persist()';
    } else {
        activeTabIndicator.textContent = 'Spring Data JPA';
        activeTabIndicator.className = 'terminal-tab tab-spring';
        labelService.textContent = 'SpringDataEmployeeService';
        detailService.textContent = '@Transactional method proxy';
        labelOrm.textContent = 'SimpleJpaRepository';
        detailOrm.textContent = 'delegates to JpaRepository proxy -> Hibernate';
    }

    try {
        const endpoint = `${API_BASE}/employees/${type}`;
        const response = await fetch(endpoint, {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify(payload)
        });

        if (!response.ok) {
            const errTrace = await response.json();
            throw new Error(errTrace.logs ? errTrace.logs[errTrace.logs.length - 1] : 'Error saving employee');
        }

        const trace = await response.json();
        
        // Execute the visual step-by-step animation
        await animatePipeline(type, trace);
        
        // Refresh H2 table
        fetchEmployees();
        
        // Reset form inputs except department for convenience
        empName.value = '';
        empEmail.value = '';
        
    } catch (err) {
        appendConsoleLog('error', 'Execution interrupted: ' + err.message);
        sqlOutput.textContent = '/* TRANSACTION ROLLBACK EXECUTED */';
    } finally {
        setFormDisabled(false);
    }
}

// Pipeline animator helper
function delay(ms) {
    return new Promise(resolve => setTimeout(resolve, ms));
}

async function animatePipeline(type, trace) {
    const isHibernate = type === 'hibernate';
    const activeClass = isHibernate ? 'active-hibernate' : 'active-spring';
    
    // Format and display the static code
    renderCodeSnippet(trace.codeSnippet);
    
    // Step 1: Client HTTP POST
    steps.client.classList.add('active', activeClass);
    appendConsoleLog('info', trace.logs[0]); // Start log
    await delay(900);

    // Arrow 1 activation
    arrows[1].classList.add(activeClass);
    await delay(400);

    // Step 2: REST Controller
    steps.controller.classList.add('active', activeClass);
    appendConsoleLog('info', '[HTTP API] EmployeeController.java received request mapping');
    await delay(900);

    // Arrow 2 activation
    arrows[2].classList.add(activeClass);
    await delay(400);

    // Step 3: Transaction Interceptor (Spring Data JPA only)
    if (!isHibernate) {
        steps.proxy.classList.add(activeClass);
        appendConsoleLog('info', trace.logs[1]); // Proxy interception
        highlightCodeLine(5); // Highlight @Transactional annotation
        await delay(900);
        appendConsoleLog('info', trace.logs[2]); // opening session/tx
        await delay(800);
    }

    // Step 4: Employee Service
    steps.service.classList.add('active', activeClass);
    if (isHibernate) {
        appendConsoleLog('info', trace.logs[1]); // Unwrap Factory
        highlightCodeLine(2);
        await delay(900);
        
        appendConsoleLog('info', trace.logs[2]); // Open session
        highlightCodeLine(3);
        await delay(900);
        
        appendConsoleLog('info', trace.logs[3]); // Begin TX
        highlightCodeLine(6);
        await delay(900);
    } else {
        appendConsoleLog('info', trace.logs[3]); // Call Repository save
        highlightCodeLine(7);
        await delay(900);
    }

    // Arrow 3 activation
    arrows[3].classList.add(activeClass);
    await delay(400);

    // Step 5: Hibernate ORM translation
    steps.orm.classList.add('active', activeClass);
    if (isHibernate) {
        appendConsoleLog('info', trace.logs[4]); // session.persist
        highlightCodeLine(7);
        await delay(900);
    } else {
        appendConsoleLog('info', trace.logs[4]); // SimpleJpaRepository delegates
        highlightCodeLine(7);
        await delay(900);
    }

    // Arrow 4 activation
    arrows[4].classList.add(activeClass);
    await delay(400);

    // Step 6: Database Execution (SQL update)
    steps.db.classList.add('active', activeClass);
    sqlOutput.textContent = trace.sqlQuery;
    appendConsoleLog('success', 'Database Executed: ' + trace.sqlQuery);
    await delay(1000);

    // Step 7: Commit and Cleanup
    if (isHibernate) {
        appendConsoleLog('info', trace.logs[5]); // Tx Commit
        highlightCodeLine(8);
        await delay(900);
        
        appendConsoleLog('info', trace.logs[trace.logs.length - 3]); // Manual closing session
        highlightCodeLine(12);
        await delay(900);
    } else {
        appendConsoleLog('info', trace.logs[5]); // Spring AOP Commit
        highlightCodeLine(8);
        await delay(900);
        
        appendConsoleLog('info', trace.logs[6]); // Session closed
        await delay(900);
    }

    // Success logs
    appendConsoleLog('success', trace.logs[trace.logs.length - 2]); // Success creation ID msg
    appendConsoleLog('system-msg', trace.logs[trace.logs.length - 1]); // End trace message
    
    // Clear code highlights
    clearCodeHighlights();
}

// Render formatted lines of code with indices
function renderCodeSnippet(code) {
    const lines = code.split('\n');
    codeDisplay.innerHTML = lines.map((line, idx) => {
        return `<div class="code-line-row" id="code-line-${idx + 1}">${escapeHtml(line) || ' '}</div>`;
    }).join('');
}

function highlightCodeLine(lineNum) {
    clearCodeHighlights();
    const line = document.getElementById(`code-line-${lineNum}`);
    if (line) {
        line.classList.add('code-highlight');
        line.scrollIntoView({ behavior: 'smooth', block: 'nearest' });
    }
}

function clearCodeHighlights() {
    const highlighted = document.querySelectorAll('.code-highlight');
    highlighted.forEach(el => el.classList.remove('code-highlight'));
}

// Form Helpers
function setFormDisabled(disabled) {
    empName.disabled = disabled;
    empEmail.disabled = disabled;
    empDept.disabled = disabled;
    empSalary.disabled = disabled;
    btnHibernate.disabled = disabled;
    btnSpringData.disabled = disabled;
}

function clearConsole() {
    logOutput.innerHTML = '';
    sqlOutput.textContent = '-- Running SQL query...';
}

function appendConsoleLog(type, message) {
    const line = document.createElement('div');
    line.className = `log-line ${type}`;
    line.textContent = message;
    logOutput.appendChild(line);
    logOutput.scrollTop = logOutput.scrollHeight;
}

function resetPipelineColors() {
    Object.values(steps).forEach(step => {
        step.className = 'pipeline-step';
    });
    Object.values(arrows).forEach(arrow => {
        arrow.className = 'pipeline-arrow';
    });
    steps.proxy.className = 'proxy-wrapper';
    
    labelService.textContent = 'Employee Service';
    detailService.textContent = 'Select method...';
    labelOrm.textContent = 'Hibernate Engine (JPA)';
    detailOrm.textContent = 'Translates Object to SQL';
}

function escapeHtml(str) {
    return str.replace(/&/g, "&amp;")
              .replace(/</g, "&lt;")
              .replace(/>/g, "&gt;")
              .replace(/"/g, "&quot;")
              .replace(/'/g, "&#039;");
}
