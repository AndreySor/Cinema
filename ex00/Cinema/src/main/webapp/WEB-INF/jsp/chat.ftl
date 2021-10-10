<html>
<head>
    <style>
        <#setting classic_compatible=true>
        <#include "css/style.css">
    </style>
    <!-- https://cdnjs.com/libraries/sockjs-client -->
    <script type="text/javascript" src="https://cdnjs.cloudflare.com/ajax/libs/sockjs-client/1.1.4/sockjs.min.js"></script>
    <!-- https://cdnjs.com/libraries/stomp.js/ -->
    <script type="text/javascript" src="https://cdnjs.cloudflare.com/ajax/libs/stomp.js/2.3.3/stomp.min.js"></script>
    <title>Chat</title>
</head>
<body>
<div id="chat-container">
    <div class="chat-header">
        <div class="user-container">
            <span id="username" utext="${userName}"></span>
            <span id="filmid" utext="${filmId}"></span>
        </div>
        <h3>${title}</h3>
    </div>

    <hr/>

    <div id="connecting">Connecting...</div>
    <ul id="messageArea">
    </ul>
    <form id="messageForm" name="messageForm">
        <div class="input-message">
            <input type="text" id="message" autocomplete="off"
                   placeholder="Type a message..."/>
            <button type="submit">Send</button>
        </div>
    </form>
</div>
<br>
<br>
<script type="text/javascript">
    'use strict';


    var messageForm = document.querySelector('#messageForm');
    var messageInput = document.querySelector('#message');
    var messageArea = document.querySelector('#messageArea');
    var connectingElement = document.querySelector('#connecting');

    var stompClient = null;
    var username = null;
    var filmId = null;


    function connect() {
        username = "${userName}";
        filmId = "${filmId}";

        var socket = new SockJS("http://localhost:8080/ws"); //не отрабатывает
        stompClient = Stomp.over(socket);

        stompClient.connect({}, onConnected, onError);
    }

    // Connect to WebSocket Server.
    connect();

    function onConnected() {
        // Subscribe to the Public Topic
        stompClient.subscribe("/films/" + currentUser.id + "/chat", onMessageReceived);

        // Tell your username to the server
        stompClient.send("/app/auth",
            {},
            JSON.stringify({authentication: username, filmId: filmId})
        )
        console.log(JSON.stringify({authentication: username, filmId: filmId}));
        connectingElement.classList.add('hidden');
    }


    function onError(error) {
        connectingElement.textContent = 'Could not connect to WebSocket server. Please refresh this page to try again!';
        connectingElement.style.color = 'red';
    }


    function sendMessage(event) {
        var messageContent = messageInput.value.trim();
        if(messageContent && stompClient) {
            var chatMessage = {
                authentication: username,
                message: messageInput.value,
            };
            stompClient.send("/app/chat", {}, JSON.stringify(chatMessage));
            messageInput.value = '';
        }
        event.preventDefault();
    }


    function onMessageReceived(payload) {
        var message = JSON.parse(payload.body);

        var messageElement = document.createElement('li');

        // if(message.type === 'JOIN') {
        //     messageElement.classList.add('event-message');
        //     message.content = message.sender + ' joined!';
        // } else if (message.type === 'LEAVE') {
        //     messageElement.classList.add('event-message');
        //     message.content = message.sender + ' left!';
        // } else {
        messageElement.classList.add('chat-message');
        var usernameElement = document.createElement('strong');
        usernameElement.classList.add('nickname');
        var usernameText = document.createTextNode(message.sender);
        usernameElement.appendChild(usernameText);
        messageElement.appendChild(usernameElement);
        // }

        var textElement = document.createElement('span');
        var messageText = document.createTextNode(message.content);
        textElement.appendChild(messageText);

        messageElement.appendChild(textElement);

        messageArea.appendChild(messageElement);
        messageArea.scrollTop = messageArea.scrollHeight;
    }


    messageForm.addEventListener('submit', sendMessage, true);
</script>
</body>
</html>