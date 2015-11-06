$(function(){
    client = io();

    client.on("notification", function(notification){
        console.log(notification);
    })

    // add current socket to socket pool
    client.emit("adduser", {username:$.cookie("username")})

    // To be override
    client.on("chat message", function(data){
        console.log("chat messages");
    })

    client.on("notify", function(data){
        console.log("Nitifications");
    })
});