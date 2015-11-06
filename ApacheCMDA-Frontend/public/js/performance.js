$(function(){

    function lockServer() {
        $.get("/settest/start", function(result){ console.log(result)});
    }

    function unlockServer() {
        $.get("/settest/stop", function(result){ console.log(result)});
    }

    function test(second, interval) {
        post_count = 0;
        post_total = 0;
        get_count = 0;
        get_total = 0;
        test_username = $.cookie("username");
        test_content = "abcdefghijklmnopurst";
        var postLoop;
        var getLoop;
        // test post request
        // start from beginning, last for half time
     
        postLoop = setInterval(function(){
            post_total += 1;
            if (post_total > 4000) {
                clearInterval(postLoop);
                setTimeout(unlockServer, 10);
                alert("Test Too Long");
                location.reload();
            } 
            $.post("/api/chatmessage/send", {message:
                {
                    username:test_username, 
                    isPublic:"true", 
                    sendTo:"", 
                    type:"text", 
                    time:"2010/08/09", 
                    read:"true"
                }}, function(result){
                    post_count += 1;
            })
        }, interval);
        
        // test get request
        // start from half time, last to end
        setTimeout(function(){
            // stop post testing
            clearInterval(postLoop);
            post_result = post_count / second * 2;
            
            // start get testing
            getLoop = setInterval(function(){
                get_total += 1
                $.getJSON("/api/messages/0/5", function(resutl){
                    get_count += 1;
                })
            }, interval)
        }, second * 1000 / 2 + 10)


        setTimeout(function(){
            clearInterval(getLoop);
            get_result = get_count / second * 2;
            $("#result").text("Calculating...")
            setTimeout(function(){
                $("#result").addClass("hide")
                $("#testbtn").text("Test").attr("disabled", false).removeClass("btn-warning").addClass("btn-success");
                unlockServer();
                $("#test-post-send").text(post_total);
                $("#test-post-ok").text(post_count);
                $("#test-post-rps").text(post_count*1.0/second + " r/s") 
                $("#test-get-send").text(get_total);
                $("#test-get-ok").text(get_count);
                $("#test-get-rps").text(get_count*1.0/second + " r/s") 
                $("#result-table").removeClass("hide"); 

            }, 5000)

        }, second * 1000)
        

    }

    $("#testbtn").click(function(){
        $(this).text("Testing").attr("disabled", true).removeClass("btn-success").addClass("btn-warning");
        lockServer();
        $("#result").removeClass("hide");
        var duration = parseInt($("#test-duration").val())
        test(duration, 3);
    })
})