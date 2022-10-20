'use strict';

let xValue = document.getElementById('xInput');
let yValue = document.getElementById('yInput');
let result = document.getElementById('result');
let wsResult = document.getElementById('wsResult');
let button = document.querySelector('button');


function handleKeyPress(e) {
    if (e.type == "click" || e.keyCode == 13) {
        let x, y;

        if (xValue.value) {
            x = Number(xValue.value);
        } else {
            alert("Please enter an X value!");
            xValue.value = "Enter x value here";
            xValue.select();
            return;
        }

        if (yValue.value) {
            y = Number(yValue.value);
        } else {
            alert("Please enter a Y value!");
            yValue.value = "Enter y value here";
            yValue.select();
            return;
        }

        e.preventDefault();


        if (isNaN(x)) {
            alert("Please make sure X is a number");
            xValue.value = "Enter number here";
            xValue.select();
            return;
        }

        if (isNaN(y)) {
            alert("Please make sure Y is a number");
            yValue.value = "Enter number here";
            yValue.select();
            return;
        }

        // result.value = x + y;

        // AJAX request
        let request = new XMLHttpRequest();
        const url = `http://localhost:8080/calculate?x=${x}&y=${y}`;
        request.addEventListener('load', requestSuccess);
        request.addEventListener('error', requestError);
        request.open("GET", url);
        request.send();

        // Web Socket request
        ws.send(x + " " + y);
        window.console.log(ws);
    }
}

xValue.addEventListener('keypress', handleKeyPress);
yValue.addEventListener('keypress', handleKeyPress);
button.addEventListener('click', handleKeyPress);

function requestSuccess() {
    window.console.log(this.responseText);
    result.value = this.responseText;
}

function requestError() {
    window.console.log("an error has occured during your request");
    result.value = "server has a problem!"
}

///// WEB SOCKET CODE ///

let ws = new WebSocket("ws://localhost:8080");
ws.onmessage = handleWebSocketMessage;
ws.onopen = handleWebSocketConnection; 
ws.onclose = handleWebSocketClose;
ws.onerror = handleWebSocketError;

function handleWebSocketConnection(){
    window.console.log("The web socket is connected!");
}

function handleWebSocketMessage(e) {
    window.console.log("The web socket sent a message!");
    wsResult.value = e.data; 
}

function handleWebSocketClose() {
    window.console.log("The web socket has been closed!");
}

function handleWebSocketError() {
    window.console.log("An error has occured with the web socket");
}
