$(function(){

    $("#status-menu li").click(function(){
        var status = $(this).text();
        var username = $("#currentuser").text();
        $.post("/status", {"status": status, "username": username});
        $("#status-menu a").removeClass("active");
        $(this).children("a").addClass("active");
    });

});
