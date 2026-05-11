const API_URL = "http://localhost:8085/employees";

async function loadEmployees() {
    const response = await fetch(API_URL);
    const employees = await response.json();

    const table = document.getElementById("employeesTable");
    table.innerHTML = "";

    employees.forEach(employee => {
        const row = document.createElement("tr");

        row.innerHTML = `
            <td>${employee.id}</td>
            <td>${employee.name}</td>
            <td>${employee.lastname}</td>
            <td>${employee.dob}</td>
            <td>${employee.salary}</td>
            <td>
                <button onclick="deleteEmployee(${employee.id})">Delete</button>
            </td>
        `;

        table.appendChild(row);
    });
}

document.getElementById("employeeForm").addEventListener("submit", async function(event) {
    event.preventDefault();

    const employee = {
        name: document.getElementById("name").value,
        lastname: document.getElementById("lastname").value,
        dob: document.getElementById("dob").value,
        salary: parseFloat(document.getElementById("salary").value)
    };

    await fetch(API_URL, {
        method: "POST",
        headers: {
            "Content-Type": "application/json"
        },
        body: JSON.stringify(employee)
    });

    document.getElementById("employeeForm").reset();
    const table = document.getElementById("employeesTable");
    table.innerHTML = "";

    const row = document.createElement("tr");

    row.innerHTML = `
        <td>NEW</td>
        <td>${employee.name}</td>
        <td>${employee.lastname}</td>
        <td>${employee.dob}</td>
        <td>${employee.salary}</td>
        <td></td>
    `;

    table.appendChild(row);
    //loadEmployees();
});

async function deleteEmployee(id) {
    await fetch(`${API_URL}/${id}`, {
        method: "DELETE"
    });

    loadEmployees();
}
async function findEmployeeById() {
    const id = document.getElementById("searchId").value;

    if (!id) {
        alert("Please enter an employee ID.");
        return;
    }

    const response = await fetch(`${API_URL}/${id}`);

    if (response.status === 404) {
        alert("Employee not found.");
        return;
    }

    const employee = await response.json();

    const table = document.getElementById("employeesTable");
    table.innerHTML = "";

    const row = document.createElement("tr");

    row.innerHTML = `
        <td>${employee.id}</td>
        <td>${employee.name}</td>
        <td>${employee.lastname}</td>
        <td>${employee.dob}</td>
        <td>${employee.salary}</td>
        <td>
            <button onclick="deleteEmployee(${employee.id})">Delete</button>
        </td>
    `;

    table.appendChild(row);
}
function clearTable() {
    const table = document.getElementById("employeesTable");
    table.innerHTML = "";
}

// loadEmployees();