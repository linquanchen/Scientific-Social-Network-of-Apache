$(function(){
   $("#search-category>li>a").click(function(){
        var opt = $(this).attr("data-opt");
        var text = $(this).text();
        $("#searchLabel").text(text);
        $("#input-category").val(opt);
   })
});