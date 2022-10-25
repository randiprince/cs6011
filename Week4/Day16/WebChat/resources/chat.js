let submitBtn = document.querySelector('#submit');
let chatUsers = document.querySelector(".users-in-chat");
let chatMessages = document.querySelector(".chat-messages");
let messageArea = document.querySelector("textarea");
let username, roomname;
let userNameInput = document.querySelector('#user-name');
let roomNameInput = document.querySelector('#room-name');
userNameInput.select();


let ws = new WebSocket("ws://localhost:8080");
ws.onmessage = handleWebSocketMessage;
ws.onopen = handleWebSocketConnection; 
ws.onclose = handleWebSocketClose;
ws.onerror = handleWebSocketError;

function handleWebSocketConnection(e){
    window.console.log("The web socket is connected!");
    submitBtn.addEventListener('click', handleSubmitClick);
    roomNameInput.addEventListener('keypress', handleSubmitClick);
    messageArea.addEventListener('keypress', handleSendMessage);
}

function handleWebSocketMessage(e) {
    window.console.log("The web socket sent a message!");
    let socketEvent = JSON.parse(e.data);
    window.console.log(socketEvent);

    if (socketEvent.type === "join") {
        let joinedUser = document.createElement('p');
        joinedUser.innerText = socketEvent.user;
        // chatUsers.appendChild(joinedUser);
        let message = document.createElement('p');
        message.innerText = `${socketEvent.user} has joined the room`;
        chatUsers.appendChild(joinedUser);
        chatMessages.appendChild(message);

    } else if (socketEvent.type === "message") {
        let messageText = document.createElement('p');
        messageText.innerText = `${socketEvent.user}: ${socketEvent.message}`;
        chatMessages.appendChild(messageText);
        messageArea.value = '';
        chatMessages.scrollTop = chatMessages.scrollHeight;

    } else if (socketEvent.type === "leave") {
        let userLeaving = document.createElement('p');
        userLeaving.innerText = `${socketEvent.user} has left the room`;
        chatMessages.appendChild(userLeaving);
        let allUsers = chatUsers.querySelectorAll('p');
        for (let i = 0; i < allUsers.length; i++) {
            if (allUsers[i].innerText === socketEvent.user) {
                allUsers[i].remove();
            }
        }
    }
}

function handleWebSocketClose() {
    window.console.log("The web socket has been closed!");
}

function handleWebSocketError() {
    window.console.log("An error has occured with the web socket");
}

function handleSubmitClick(e) {
    if (e.keyCode == 13 || e.type == 'click') {
        if (userNameInput.value) {
            username = userNameInput.value;
        } else {
            alert("Please enter your user name!");
            userNameInput.value = "Enter user here";
            userNameInput.select();
            return;
        }
    
        if (roomNameInput.value) {
            roomname = roomNameInput.value;
        } else {
            alert("Please enter chat room name!");
            roomNameInput.value = "Enter chat room here";
            roomNameInput.select();
            return;
        }
    
        if (username && roomname) {
            ws.send(`join ${username.toLowerCase()} ${roomname.toLowerCase()}`);
            messageArea.select();
        }
    } 
}

function handleSendMessage(e) {
    if (e.keyCode == 13) {
        let message = messageArea.value;
        ws.send(username + " " + message);
    } 
}