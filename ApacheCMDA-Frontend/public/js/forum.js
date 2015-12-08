$(function(){
    $(".hide-hood").hide();
    $(".toggle").click(function(){
        var hood = $(this).parent().children(".hide-hood").toggle();
        hood.children("input").focus();
    })
});