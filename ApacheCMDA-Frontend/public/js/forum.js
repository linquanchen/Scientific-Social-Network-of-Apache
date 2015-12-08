$(function(){
    $(".hide-hood").hide();
    $(".toggle").click(function(){
        $(this).parent().children(".hide-hood").toggle();
    })
});