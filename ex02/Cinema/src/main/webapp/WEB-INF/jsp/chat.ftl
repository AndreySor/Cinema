<html>
<head>
    <style>
        <#setting classic_compatible=true>
        <#include "css/chat.css">
    </style>
    <title>Chat</title>
</head>
<body>
<div id="username-page">
    <div class="username-page-container">
        <h1 class="title">Type your username</h1>
        <form id="usernameForm" name="usernameForm">
            <div class="form-group">
                <input type="text" id="name" placeholder="Username" autocomplete="off" class="form-control" />
            </div>
            <div class="form-group">
                <button type="submit" class="accent username-submit">Start Chatting</button>
            </div>
        </form>
    </div>
</div>

<div id="chat-page" class="hidden">
    <div class="chat-container">
        <div class="chat-header">
            <h2>${film.title}</h2>
        </div>
        <div class="connecting">
            Connecting...
        </div>
        <ul id="messageArea">
            <#list history as historyMessage>
                <li class="chat-message">
                    <span>${historyMessage.user.login}</span>
                    <p>${historyMessage.message}</p>
                </li>
            </#list>
        </ul>
        <form id="messageForm" name="messageForm">
            <div class="form-group">
                <div class="input-group clearfix">
                    <input type="text" id="message" placeholder="Type a message..." autocomplete="off" class="form-control"/>
                    <button type="submit" class="primary">Send</button>
                </div>
            </div>
        </form>
    </div>
    <div class="user-info">
        <div id="authInfo" class="auth">
            <h3>Users authentications</h3>
        </div>
        <div class="avatar">
            <div id="userAvatars">
                <h3>User's avatars</h3>
            </div>
            <form id="avatarForm" enctype="multipart/form-data" method="POST" action="/images">
                <p>
                    <input type="file" name="avatar" id="avatar">
                    <input type="hidden" name="filmId" id="formFilmId">
                    <input type="hidden" name="userId" id="formUserId">
                    <button id="uploadButton">Upload</button>
                </p>
            </form>
        </div>
    </div>
</div>

<script src="https://cdnjs.cloudflare.com/ajax/libs/sockjs-client/1.1.4/sockjs.min.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/stomp.js/2.3.3/stomp.min.js"></script>
<script src="http://ajax.googleapis.com/ajax/libs/jquery/1.3/jquery.min.js"></script>
<script>
    'use strict';
    let usernamePage = document.querySelector('#username-page');
    let chatPage = document.querySelector('#chat-page');
    let usernameForm = document.querySelector('#usernameForm');
    let messageForm = document.querySelector('#messageForm');
    let messageInput = document.querySelector('#message');
    let messageArea = document.querySelector('#messageArea');
    let connectingElement = document.querySelector('.connecting');
    let chatFilmId = '${film.filmId}';
    let stompClient = null;
    let username = null;

    let colors = [
        '#2196F3', '#32c787', '#00BCD4', '#ff5652',
        '#ffc107', '#ff85af', '#FF9800', '#39bbb0'
    ];

    $(document).ready(function () {
        console.log(document.cookie);
        document.getElementById("formFilmId").value=('${film.filmId}');
        document.getElementById("formUserId").value=(getCookie("userId"));
        let userCookie = getCookie("user");
        if (userCookie) {
            username = userCookie;
            usernamePage.classList.add('hidden');
            chatPage.classList.remove('hidden');
            let socket = new SockJS('/ws');
            stompClient = Stomp.over(socket);
            stompClient.connect({}, onConnected, onError);
        }
    })

    function connect(event) {
        username = document.querySelector('#name').value.trim();
        if(username) {
            document.cookie = "user=" + username;
            usernamePage.classList.add('hidden');
            chatPage.classList.remove('hidden');
            let socket = new SockJS('/ws');
            stompClient = Stomp.over(socket);
            stompClient.connect({}, onConnected, onError);
        }
        event.preventDefault();
    }

    function onConnected() {
        // Subscribe to the Public Topic
        stompClient.subscribe('/topic/public', onMessageReceived);
        // Tell your username to the server
        stompClient.send("/app/chat.addUser",
            {},
            JSON.stringify({type: 'JOIN', user: {
                login: username
                }})
        )
        connectingElement.classList.add('hidden');
    }
    function onError(error) {
        connectingElement.textContent = 'Could not connect to WebSocket server. Please refresh this page to try again!';
        connectingElement.style.color = 'red';
    }
    function sendMessage(event) {
        let messageContent = messageInput.value.trim();
        if(messageContent && stompClient) {
            let chatMessage = {
                message: messageInput.value,
                type: 'CHAT',
                film: {
                    filmId: chatFilmId
                },
                user: {
                    id: getCookie("userId"),
                    login: username
                }
            };
            stompClient.send("/app/chat.sendMessage", {}, JSON.stringify(chatMessage));
            messageInput.value = '';
        }
        event.preventDefault();
    }
    function onMessageReceived(payload) {
        let message = JSON.parse(payload.body);
        let messageElement = document.createElement('li');
        if (message.type === 'JOIN') {
            messageElement.classList.add('event-message');
            message.message = message.user.login + ' joined!';
            if (!getCookie("userId")) {
                document.cookie = "userId=" + message.user.id;
            }
            console.log(getCookie("userId"));
            console.log(message.user.id);
            if (getCookie("userId") == message.user.id) {
                getAuthList();
                getListOfAvatar();
            }
        } else if (message.type === 'LEAVE') {
            messageElement.classList.add('event-message');
            message.message = message.user.login + ' left!';
        } else {
            messageElement.classList.add('chat-message');
            let avatarElement = document.createElement('i');
            let avatarText = document.createTextNode(message.user.login[0]);
            avatarElement.appendChild(avatarText);
            avatarElement.style['background-color'] = getAvatarColor(message.user.login);
            messageElement.appendChild(avatarElement);
            let usernameElement = document.createElement('span');
            let usernameText = document.createTextNode(message.user.login);
            usernameElement.appendChild(usernameText);
            messageElement.appendChild(usernameElement);
        }
        let textElement = document.createElement('p');
        let messageText = document.createTextNode(message.message);
        textElement.appendChild(messageText);
        messageElement.appendChild(textElement);
        messageArea.appendChild(messageElement);
        messageArea.scrollTop = messageArea.scrollHeight;
    }
    function getAvatarColor(messageSender) {
        let hash = 0;
        for (let i = 0; i < messageSender.length; i++) {
            hash = 31 * hash + messageSender.charCodeAt(i);
        }
        let index = Math.abs(hash % colors.length);
        return colors[index];
    }

    function getCookie(name) {
        let matches = document.cookie.match(new RegExp(
            "(?:^|; )" + name.replace(/([\.$?*|{}\(\)\[\]\\\/\+^])/g, '\\$1') + "=([^;]*)"
        ));
        return matches ? decodeURIComponent(matches[1]) : undefined;
    }

    function setCookie(name, value) {
        document.cookie = encodeURIComponent(name) + "=" + encodeURIComponent(value);
    }

    function deleteCookie(name) {
        setCookie(name, "", {
            'max-age': -1
        })
    }

    function getAuthList() {
        let url = "/userInfo/auth";

        $.ajax({
            type: "GET",
            url: url,
            contentType: "application/json",
            dataType: "json",
            data: {id: getCookie("userId")},
            success: function (rs) {
                $.each(rs, function (key, value) {
                    $("#authInfo").append('<div><p>' + value.ipAddress + ' ' + value.date + '</p></d>')
                })
            },
            error: function (jqXhr, textStatus, errorMessage) {
                console.log("Error", errorMessage);
            }
        })
    }

    function getListOfAvatar() {
        let url = "/chat/usersAvatars/list";

        $.ajax({
            type: "GET",
            url: url,
            contentType: "application/json",
            dataType: "json",
            data: {id: getCookie("userId")},
            success: function (rs) {
                $.each(rs, function (key, value) {
                    $("#userAvatars").append('<p><a href="/chat/avatar/' + getCookie("userId") + '/' + value + '" target="_blank">' + value + '</a></p>')
                })
            },
            error: function (jqXhr, textStatus, errorMessage) {
                console.log("Error", errorMessage);
            }
        })
    }

    usernameForm.addEventListener('submit', connect, true)
    messageForm.addEventListener('submit', sendMessage, true)
</script>
</body>
</html>
