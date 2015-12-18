$(function(){
    if($("#userFlag").length > 0) {
        $.getJSON("/notifications/count", function(data){
            if(data.friendRequest + data.mail > 0) {
                $(".reddot").removeClass("hide");
            }
        })
    }
});
