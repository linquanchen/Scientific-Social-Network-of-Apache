/**
 * Created by Xavier on 2015/10/6.
 */

var chat_public, chat_to, username;

$(function() {
    var FADE_TIME = 150; // ms
    var COLORS = [
        '#e21400', '#91580f', '#f8a700', '#f78b00',
        '#58dc00', '#287b00', '#a8f07a', '#4ae8c4',
        '#3b88eb', '#3824aa', '#a700ff', '#d300e7'
    ];

    // Initialize varibles
    var $window = $(window);
    var $messages = $('.messages'); // Messages area
    var $inputMessage = $('.inputMessage'); // Input message input box
    var $chatArea = $('.chatArea'); // Message show area
    var $currentInput = $inputMessage.focus();

    // Prompt for setting a username
    username =  $.cookie("username");

    var start = 0;

    // initial rutain
    function init() {
        chat_public = !($.cookie("private") === "true");
        chat_to = $.cookie("private") ? $("#data-private").attr("data-chatTo") : "";
        console.log("private?", $.cookie("private"));
        console.log("public?", chat_public)
        console.log("SendTo?", chat_to);
    }

    init();

    // Sends a chat message
    function sendMessage (c_p, c_t) {
        var message = $inputMessage.val();
        // Prevent markup from being injected into the message
        message = cleanInput(message);
        // if there is a non-empty message and a client connection
        if (message) {
            var msg = {
                username: username,
                content: message,
                time: (new Date()).toLocaleString(),//(new Date()).getHours() + ':' + (new Date()).getMinutes() + ':' + (new Date()).getSeconds() // (new Date()).toLocaleTimeString()
                isPublic: c_p,
                sendTo: c_t,
                type: "text"
            };
            console.log(msg);
            $.post("/api/chatmessage/send", {'message':msg});
            $inputMessage.val('');
            addChatMessage(msg);
        }
    }

    // Loads 5 messages
    function loadMessage (isPublic, chat_from, chat_to) {
        var api = "";
        if (isPublic)
            api = "/api/messages/" + start + "/5/";
        else
            api = "/api/pmessages/" + chat_from + "/" + chat_to + "/" + start + "/5"
        $.getJSON(api, function(result){
            var messages = eval(result);
            if (messages) {
                for (var i=0; i<messages.length; i++)
                {
                    var options = {
                        prepend: true
                    }

                    var $usernameDiv = $('<div class="username"/>')
                        .text(messages[i].username + '(' + messages[i].time + ')')
                        .css('color', getUsernameColor(messages[i].username));
                    var $messageBodyDiv = $('<div class="messageBody"/>')
                        .text(messages[i].content);

                    var $messageDiv = $('<li class="message">')
                        .data('username', messages[i].username)
                        .append($usernameDiv, $messageBodyDiv);

                    addMessageElement($messageDiv, options);
                    start ++;
                }
            }
        });
    }
    // Log a message
    function log (message, options) {
        var $el = $('<li>').addClass('log').text(message);
        addMessageElement($el, options);
    }

    // New message alert
    // message send to me, but I am not viewing
    function otherNewMessageAlert () {
        // TODO: show this alert
    }

    // Adds the visual chat message to the message list
    function addChatMessage (msg, options) {
        var $usernameDiv = $('<div class="username"/>')
            .text(msg.username + '(' + (new Date()).toLocaleString() + ')')
            .css('color', getUsernameColor(msg.username));
        var $messageBodyDiv = $('<div class="messageBody"/>')
            .text(msg.content);

        var $messageDiv = $('<li class="message">')
            .data('username', msg.username)
            .append($usernameDiv, $messageBodyDiv);

        addMessageElement($messageDiv, options);
        start++;
    }

    // Adds a message element to the messages and scrolls to the bottom
    // el - The element to add as a message
    // options.fade - If the element should fade-in (default = true)
    // options.prepend - If the element should prepend
    // all other messages (default = false)
    function addMessageElement (el, options) {
        var $el = $(el);

        // Setup default options
        if (!options) {
            options = {};
        }
        if (typeof options.fade === 'undefined') {
            options.fade = true;
        }
        if (typeof options.prepend === 'undefined') {
            options.prepend = false;
        }

        // Apply options
        if (options.fade) {
            $el.hide().fadeIn(FADE_TIME);
        }
        if (options.prepend) {
            $messages.prepend($el);
        } else {
            $messages.append($el);
        }
        $chatArea[0].scrollTop = $chatArea[0].scrollHeight;
    }

    // Prevents input from having injected markup
    function cleanInput (input) {
        return $('<div/>').text(input).text();
    }

    // Gets the color of a username through our hash function
    function getUsernameColor (username) {
        // Compute hash code
        var hash = 7;
        for (var i = 0; i < username.length; i++) {
            hash = username.charCodeAt(i) + (hash << 5) - hash;
        }
        // Calculate color
        var index = Math.abs(hash % COLORS.length);
        return COLORS[index];
    }

    // Keyboard events
    $window.keydown(function (event) {
        // Auto-focus the current input when a key is typed
        if (!(event.ctrlKey || event.metaKey || event.altKey)) {
            $currentInput.focus();
        }
        // When the client hits ENTER on their keyboard
        if (event.which === 13) {
            sendMessage(chat_public, chat_to);
        }
    });

    $("#btn").click(function(){
        sendMessage(chat_public, chat_to);
    });

    $("#history").click(function(){
        if (chat_public)
            loadMessage(true, null, null);
        else 
            loadMessage(false, username, chat_to)
    });

    // Click events

    // Focus input when clicking on the message input's border
    $inputMessage.click(function () {
        $inputMessage.focus();
    });

    // client events

    // Whenever the server emits 'new message', update the chat body
    client.on('chat message', function (msg) {
        console.log("new message +++++++++")
        console.log(msg.sendTo == username)
        console.log(!chat_public)
        console.log(msg)
        if (msg.isPublic=="true" && chat_public) {
            console.log("load public")
            addChatMessage(msg);
        } else if ((msg.sendTo == username) && (!chat_public)) {
            console.log("load private")
            addChatMessage(msg);
        }
    });

    // Whenever the server emits 'bye message', log it in the chat body
    client.on('bye message', function (byemsg) {

    });
});