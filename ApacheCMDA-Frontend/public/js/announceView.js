$(function(){

	
$("#load-ann-btn").click(function(){
    $("#annTableBody").empty();
    $.getJSON("/announcement/publish", function(result){
      console.log("in anncontroll")
      console.log(result);
        // $("#annTable").append("<tr><th>Name</th><th>Status</th></tr>");
        $.each(result.annlist, function(err, item){
            $("#annTableBody").append("<tr><td>" + item.username + "</td><td>" + item.content + "</td><td>" + item.timestamp + "</td><td>" + item.location + "</td></tr>");
        });
        $("#load-dir-btn").text("Refresh")
    });
});

$('#newAnnouncement').on('show.bs.modal', function (event) {
  var button = $(event.relatedTarget) // Button that triggered the modal
  var recipient = button.data('whatever') // Extract info from data-* attributes
  // If necessary, you could initiate an AJAX request here (and then do the updating in a callback).
  // Update the modal's content. We'll use jQuery here, but you could use a data binding library or other methods instead.
  var modal = $(this)
  modal.find('.modal-title').text('New announcement ')
  modal.find('.modal-body input').val(recipient)
  
});
/*
$("#closeForm").click(function(){
  console.log("Close Clicked")
});

$("#newAnnouncement").on('hide.bs.modal', function(){
    alert("Modal window has been completely closed.");
  });
*/

$('#formSubmit').click(function() {

   var $form = $("#newForm");
   var $target = $($form.attr('data-target'));

   $.ajax({
      type: 'POST',
      url: "/announcement/publish",
      data: $form.serialize(),

      success: function(msg) {
          $('#newAnnouncement').modal('hide');
          $("#load-ann-btn").click();
      }
    });
  
  return false;
});

// $('#newAnnouncement').on('hidden.bs.modal', function () {
//   $('#newAnnounce').removeData('bs.modal')
//     // do something…
// });

$('#newAnnouncement').on('hidden.bs.modal', function (e) {
  $(this)
    .find("input,textarea,select")
       .val('')
       .end()
    .find("input[type=checkbox], input[type=radio]")
       .prop("checked", "")
       .end();
})

});



//$('#formSubmit').click(function(){
//      e.preventDefault();
//      alert($('#myField').val());
//      $("#newAnnouncement").modal('hide');
//});
