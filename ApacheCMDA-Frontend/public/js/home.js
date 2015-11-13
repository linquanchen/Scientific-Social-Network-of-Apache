$(function(){

function refresh_directory(){
    
    $.getJSON("/api/users", function(result){
        $("#user-table").empty();
        $("#user-table").append("<tr><th>Name</th><th>Status</th></tr>");
        $.each(result.userList, function(i, item){
            var status = item.online ? "online" : "offline";
            if (item.status) {
                $("#user-table").append("<tr><td><a href='/chat/"+item.username+"'>" + item.username + "</a>(" + item.status + ", " + item.statusTime + ")" + "</td><td>" + status + "</td></tr>");
            }
            else {
                $("#user-table").append("<tr><td><a href='/chat/"+item.username+"'>" + item.username + "</a></td><td>" + status + "</td></tr>");
            }
        });
    });
}

refresh_directory();
setTimeout(function(){
    refresh_directory()
},1200)

client.on("notify", function(msg) {
    console.log("refresh");
    refresh_directory();
})

});
