let start = false;

function createTable() {
    const multTable = document.getElementById("mult-table");
    multTable.style.border = "blue 1px solid";
    for (let i = 0; i < 11; i++) {
        let tableRow = document.createElement('tr');
        multTable.appendChild(tableRow);
        for (let j = 0; j < 11; j++) {
            if (i === 0 || j === 0)  {
                let tableHead =  document.createElement('th');
                if (j === 0) {
                    tableHead.innerText = i;
                } else if (i === 0) {
                    tableHead.innerText = j;
                }
                tableHead.setAttribute("style", "width: 40px; height:40px;");
                tableRow.appendChild(tableHead);
            } else {
                let tableData = document.createElement('td');
                tableData.innerText = i*j;
                tableData.setAttribute("style", "width: 30px; height:30px; text-align:center; border: black 1px solid; padding: 5px;");
                tableData.addEventListener("mouseover", hoverState);
                tableData.addEventListener("mouseout", resetState);
                tableData.addEventListener("click", clickState);
                // tableData.removeEventListener("click", clickState);
                tableRow.appendChild(tableData);
            }
            
        }
    }
}

function buttonHandler() {
    const wrapper = document.querySelector(".wrapper");
    let button = document.createElement('button');
    button.innerText = "Click here!";
    button.addEventListener("click", handleClick)
    wrapper.appendChild(button);
}

createTable();
buttonHandler();

function hoverState(e) {
    e.target.classList.add("highlighted")
}

function resetState(e) {
    e.target.classList.remove("highlighted")
}

function clickState(e) {
    let cells = document.querySelectorAll('td');
    let firstRow = document.querySelector('tr').querySelectorAll('th');

    for (let i = 0; i < cells.length; i++) {
        if (cells[i].classList.contains('clicked')) {
            cells[i].classList.remove('clicked');
            cells[i].parentElement.children[0].classList.remove('clicked');
            for (let j = 0; j < firstRow.length; j++) { 
                if (firstRow[j].classList.contains('clicked') && firstRow[j].innerText != e.target.cellIndex) {
                    firstRow[j].classList.remove('clicked');
                }
            }
        }
    }

    e.target.classList.add('clicked');
    e.target.parentElement.children[0].classList.add('clicked');
    for (let j = 0; j < firstRow.length; j++) {
        if (firstRow[j].innerText == e.target.cellIndex) {
            firstRow[j].classList.add('clicked');
        }
    }
}

function handleClick() {
    const wrapper = document.querySelector("body");
    if (!start) {
        start = true;
    } else {
        start = false;
    }
    
    let animation = setInterval(function () {
        if (!start) {
            window.console.log("hi in conditional");
            clearInterval(animation);
            wrapper.style.backgroundColor = "#ffffff";
        } else {
            let color = '#' + ((Math.random()*16777215) | 0).toString(16);
            wrapper.style.backgroundColor = color;
        }
    }, 500);
}
