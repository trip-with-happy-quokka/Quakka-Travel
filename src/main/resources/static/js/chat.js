const token = "Bearer " + localStorage.getItem("jwtToken");

// chat.js
const socket = new SockJS(`/ws`);
const stompClient = Stomp.over(socket);

stompClient.connect(
    { Authorization: token },
    function (frame) {
        console.log('Connected: ' + frame);

        stompClient.subscribe('/user/queue/messages/' + chatRoomId, function (message) {
            const chatMessage = JSON.parse(message.body);
            displayMessage(chatMessage);
        });
    },
    function (error) {
        console.error('websocket connection error:', error);
    }
);

function sendMessage(sender, receiver, messageText) {
    const message = {
        sender: sender,
        receiver: receiver,
        text: messageText,
        chatRoomId: sender + '-' + receiver
    };

    stompClient.send("/app/chat", {}, JSON.stringify(message));
}

function displayMessage(chatMessage) {
    console.log("Received message: ", chatMessage);
}
