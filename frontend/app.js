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

        `;

        table.appendChild(row);
    });
}

//Adding an Employee
document.getElementById("employeeForm").addEventListener("submit", async function(event) {
    event.preventDefault();

    const employee = {
        name: document.getElementById("name").value,
        lastname: document.getElementById("lastname").value,
        dob: document.getElementById("dob").value,
        salary: parseFloat(document.getElementById("salary").value)
    };

    const response=await fetch(API_URL, {
        method: "POST",
        headers: {
            "Content-Type": "application/json"
        },
        body: JSON.stringify(employee)
    });
    const createdEmployee = await response.json();


    document.getElementById("employeeForm").reset();
    const table = document.getElementById("employeesTable");
    table.innerHTML = "";

    const row = document.createElement("tr");

    row.innerHTML = `
        <td>${createdEmployee.id}</td>
        <td>${employee.name}</td>
        <td>${employee.lastname}</td>
        <td>${employee.dob}</td>
        <td>${employee.salary}</td>

    `;

    table.appendChild(row);
    //loadEmployees();
});

document.getElementById("salaryForm").addEventListener("submit", async function(event){
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

        const employee = await response.json();

    const salaryUpdate={
    salary: parseFloat(document.getElementById("newSalary").value)
    };
    await fetch(`${API_URL}/${id}/salary`,{
    method: "PUT",
    headers: {
                "Content-Type": "application/json"
            },
    body: JSON.stringify(salaryUpdate)
    });
     document.getElementById("salaryForm").reset();
        const table = document.getElementById("employeesTable");
        table.innerHTML = "";

        const row = document.createElement("tr");

        row.innerHTML = `
            <td>${id}</td>
            <td>${employee.name}</td>
            <td>${employee.lastname}</td>
            <td>${employee.dob}</td>
            <td>${salaryUpdate.salary}</td>

        `;

        table.appendChild(row);
        //loadEmployees();
});



document.getElementById("deleteForm").addEventListener("submit", async function(event) {
    event.preventDefault();

    const id = document.getElementById("deleteId").value;

    const response = await fetch(`${API_URL}/${id}`);

    if (response.status === 404) {
        alert("Employee not found.");
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
        method: "DELETE"
    });

    if (deleteResponse.status === 200) {
        alert("Employee deleted successfully.");
        clearTable();
    } else {
        alert("Error deleting employee.");
    }

    document.getElementById("deleteForm").reset();
});

async function deleteEmployee(id) {
    await fetch(`${API_URL}/${id}`, {
        method: "DELETE"
    });

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

    `;

    table.appendChild(row);
}
function clearTable() {
    const table = document.getElementById("employeesTable");
    table.innerHTML = "";
}

// loadEmployees();