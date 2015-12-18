$(function(){
    $(".hide-hood").hide();
    $(".toggle").click(function(){
        var hood = $(this).parent().children(".hide-hood").toggle();
        hood.children("input").focus();
    })

    $(".vote-thumb").click(function(){
        var id = $(this).attr("data-commentId");
        var act = $(this).attr("data-act");
        var self = $(this)
        if (act === "voteup") {
            var url = "/workflow/thumbUp/"+id+"/"+$(self).attr("data-wfid");
            $.getJSON(url, {}, function(data){
                var vote_num = self.parent().children(".vote-num");
                var number = $(vote_num[0]).text();
                $(vote_num[0]).text(Number.parseInt(number)+1);
                self.parent().children(".vote-thumb").removeClass("voted");
                self.addClass("voted")
            });
        } else {
            var url = "/workflow/thumbDown/"+id+"/"+$(self).attr("data-wfid");
            $.getJSON(url, {}, function(data){
                var vote_num = self.parent().children(".vote-num");
                var number = $(vote_num[0]).text();
                $(vote_num[0]).text(Number.parseInt(number)-1);
                self.parent().children(".vote-thumb").removeClass("voted");
                self.addClass("voted")
            });
        }
    })

    $(".suggestion-like a").click(function(){
        var url = "/suggestion/voteToSuggestion/" + $(this).attr("data-sugId");
        var self = $(this);
        $.getJSON(url, {}, function(data){
            var numSpan = ($(self).parent().children("span"))[0];
            var number = Number.parseInt($(numSpan).text());
            $(self).addClass("like-voted");
            $(numSpan).text(number+1);
        })
    })
});