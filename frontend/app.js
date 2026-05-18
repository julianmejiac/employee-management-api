const API_URL = "http://localhost:8085/employees";

function getAuthHeader() {
    const username = prompt("Admin username");
    const password = prompt("Admin password");

    return "Basic " + btoa(username + ":" + password);
}

async function loadEmployees() {
    const response = await fetch(API_URL);

    if (!response.ok) {
        alert("Could not load employees. Status: " + response.status);
        return;
    }

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
        `;

        table.appendChild(row);
    });
}

// Adding an Employee
document.getElementById("employeeForm").addEventListener("submit", async function(event) {
    event.preventDefault();

    const employee = {
        name: document.getElementById("name").value,
        lastname: document.getElementById("lastname").value,
        dob: document.getElementById("dob").value,
        salary: parseFloat(document.getElementById("salary").value)
    };

    const response = await fetch(API_URL, {
        method: "POST",
        headers: {
            "Content-Type": "application/json",
            "Authorization": getAuthHeader()
        },
        body: JSON.stringify(employee)
    });

    if (!response.ok) {
        const errorMessage=await response.text();

        alert(errorMessage || "Create failed. Status: " + response.status);
        return;
    }

    const createdEmployee = await response.json();

    document.getElementById("employeeForm").reset();

    const table = document.getElementById("employeesTable");
    table.innerHTML = "";

    const row = document.createElement("tr");

    row.innerHTML = `
        <td>${createdEmployee.id}</td>
        <td>${createdEmployee.name}</td>
        <td>${createdEmployee.lastname}</td>
        <td>${createdEmployee.dob}</td>
        <td>${createdEmployee.salary}</td>
    `;

    table.appendChild(row);
});

// Updating salary
document.getElementById("salaryForm").addEventListener("submit", async function(event) {
    event.preventDefault();

    const id = document.getElementById("id").value;

    if (!id) {
        alert("Please enter an employee ID.");
        return;
    }

    const response = await fetch(`${API_URL}/${id}`);

    if (response.status === 404) {
        alert("Employee not found.");
        return;
    }

    if (!response.ok) {
        alert("Could not retrieve employee. Status: " + response.status);
        return;
    }

    const salaryUpdate = {
        salary: parseFloat(document.getElementById("newSalary").value)
    };

    const updateResponse = await fetch(`${API_URL}/${id}/salary`, {
        method: "PUT",
        headers: {
            "Content-Type": "application/json",
            "Authorization": getAuthHeader()
        },
        body: JSON.stringify(salaryUpdate)
    });

    if (!updateResponse.ok) {
        alert("Update failed. Status: " + updateResponse.status);
        return;
    }

    const updatedEmployeeResponse = await fetch(`${API_URL}/${id}`);
    const updatedEmployee = await updatedEmployeeResponse.json();

    document.getElementById("salaryForm").reset();

    const table = document.getElementById("employeesTable");
    table.innerHTML = "";

    const row = document.createElement("tr");

    row.innerHTML = `
        <td>${updatedEmployee.id}</td>
        <td>${updatedEmployee.name}</td>
        <td>${updatedEmployee.lastname}</td>
        <td>${updatedEmployee.dob}</td>
        <td>${updatedEmployee.salary}</td>
    `;

    table.appendChild(row);
});

// Deleting employee
document.getElementById("deleteForm").addEventListener("submit", async function(event) {
    event.preventDefault();

    const id = document.getElementById("deleteId").value;

    if (!id) {
        alert("Please enter an employee ID.");
        return;
    }

    const response = await fetch(`${API_URL}/${id}`);

    if (response.status === 404) {
        alert("Employee not found.");
        return;
    }

    if (!response.ok) {
        alert("Could not retrieve employee. Status: " + response.status);
        return;
    }

    const employee = await response.json();

    const confirmed = confirm(
        `Do you want to delete employee ${employee.name} ${employee.lastname}?`
    );

    if (!confirmed) {
        return;
    }

    const deleteResponse = await fetch(`${API_URL}/${id}`, {
        method: "DELETE",
        headers: {
            "Authorization": getAuthHeader()
        }
    });

    if (deleteResponse.status === 200) {
        alert("Employee deleted successfully.");
        clearTable();
    } else {
        alert("Error deleting employee. Status: " + deleteResponse.status);
    }

    document.getElementById("deleteForm").reset();
});

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

    if (!response.ok) {
        alert("Could not retrieve employee. Status: " + response.status);
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
    `;

    table.appendChild(row);
}

function clearTable() {
    const table = document.getElementById("employeesTable");
    table.innerHTML = "";
}