$(function(){

loadlength = 0;
data = new Object();
// $("#load-search-btn").click(function(){
//     $("#search-table").empty();
//     $.getJSON("/api/searchInfo", function(result){
//         $("#search-table").append("<tr><th>Name</th><th>Status</th></tr>");
//         $.each(result.result, function(i, item){
//             var status = item.online ? "online" : "offline";
//             if (item.status) {
//                 $("#search-table").append("<tr><td><a href='/searchInfo/"+item.username+"'>" + item.username + "</a>(" + item.status + ", " + item.statusTime + ")" + "</td><td>" + status + "</td></tr>");
//             }
//             else {
//                 $("#search-table").append("<tr><td><a href='/searchInfo/"+item.username+"'>" + item.username + "</a></td><td>" + status + "</td></tr>");
//             }
//         });
//        $("#load-search-btn").text("Refresh")
//     });
// });

$(".select-item").click(function(){

  var type = $(this).attr("data-select");
  console.log("select", type)
  $("#searchtype").val(type);
})

$('#load-search-btn').click(function() {

   var $form = $("#searchForm");
   var $target = $($form.attr('data-target'));

   $.ajax({
      type: 'POST',
      url: "/search",
      data: $form.serialize()
    });
  
  return false;
});

function getTableValues() {
  var body = new Object();
  body.type = $("#searchtype").val();
  body.criteria = $("#criteria").val();
  body.offset = 0;
  return body;
}

function createTable(result, type) {
  loadlength = 0;
  $("#showMore").removeClass('hide');
  $("#search-table").empty();
  switch(type) {
        case "username":
        case "status":
console.log("result=" , result);
          $("#search-table").append("<tr><th>username</th><th>online</th><th>status</th></tr>");
          $.each(result.result, function(i, item){
            console.log(item);
              var online = item.online ? "online" : "offline";
              $("#search-table").append("<tr><td>" + item.username +  "</td><td>" + online + "</td><td>"  + item.status + "</td></tr>");
          });
          break;
      case "announcement":
          $("#search-table").append("<tr><th>username</th><th>content</th><th>timestamp</th><th>location</th></tr>");
          $.each(result.result, function(i, item){
              $("#search-table").append("<tr><td>" + item.username +  "</td><td>" + item.content + "</td><td>"  + item.timestamp + "</td><td>"  + item.location + "</td></tr>");
          });
          break;
      case "public chat":
      case "private chat":
          $("#search-table").append("<tr><th>username</th><th>content</th><th>timestamp</th></tr>");
          $.each(result.result, function(i, item){
              $("#search-table").append("<tr><td>" + item.username +  "</td><td>" + item.content + "</td><td>"  + item.timestamp + "</td></tr>");
          });
          break;
      default:
          break;
  }
}

function appendTable(result, type){
  switch(type) {
    case "announcement":
        $.each(result.result, function(i, item){
            $("#search-table").append("<tr><td>" + item.username +  "</td><td>" + item.content + "</td><td>"  + item.timestamp + "</td><td>"  + item.location + "</td></tr>");
        });
        break;
    case "public chat":
    case "private chat":
        $.each(result.result, function(i, item){
            $("#search-table").append("<tr><td>" + item.username +  "</td><td>" + item.content + "</td><td>"  + item.timestamp + "</td></tr>");
        });
        break;
    case "username":
    case "status":
    default:
        break;
  }
}

$("#searchForm").submit(function(){
  data = getTableValues();
  var type = data.type;
  console.log(data);
  $.post("/search", data, function(result) {
    console.log("type = ", type);
    createTable(result, type);
    // $("#showMore").show();
  })
  $("#criteria").val('');
  return false;
})

// function getStatusValues() {
//   var body = new Object();
//   body.type = "status";
//   body.criteria = $("input[name=status]").val();
//   body.offset = 0;
//   return body;
// }

$("#statusForm1").submit(function(){
    data.type = "status";
    data.criteria = "OK";
    data.offset = 0;
    console.log(data);
    $.post("/search", data, function(result) {
    createTable(result, data.type);
  })
  return false;
})

$("#statusForm2").submit(function(){
    data.type = "status";
    data.criteria = "Help";
    data.offset = 0;
    $.post("/search", data, function(result) {
    createTable(result, data.type);
  })
  return false;
})

$("#statusForm3").submit(function(){
    data.type = "status";
    data.criteria = "Emergency";
    data.offset = 0;
    $.post("/search", data, function(result) {
    createTable(result, data.type);
  })
  return false;
})

$("#loadButton").click(function(){
  loadlength = loadlength + 10;
  data.offset = loadlength;
  $.post("/search", data, function(result) {
    appendTable(result, data.type);
    console.log("result=",result,"length=",loadlength);
  })
  return false;
})


});