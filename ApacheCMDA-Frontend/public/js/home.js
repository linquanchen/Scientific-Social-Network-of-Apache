$(function(){
    function fit(){
        var wH = $(window).height();
        var hei = wH - $("#header").height() - 20;
        $(".sec-2").css("height", hei);
    }
    fit();
    $(window).resize(fit);
});
