//populate a select element with
function loadSelect(id, data) {
    var select = $("#"+id);
    var res = "";
    data.forEach(function(e) {
	console.log(e);
	res+='<option value="' + e + '">' + e + '</option>';
    });
    select.html(res);
}

$(document).ready(function(){
    $("#location").on('change', function (e) {
	console.log("selected location");
    });
    $("#route").on('change', function (e) {
	console.log("selected route");
    });
    $("#journey").on('change', function (e) {
	console.log("selected journey");
    });

    $("#reserve").on('click', function (e) {
	console.log("selected reserve");
    });
    var locations = $.get("api/locations")
	.done(function(data) {
	    console.log("Received")
	    console.log(data)
	})
	.fail(function() {
	    console.log("oh no, failed")
	    //delete me
	    loadSelect("location",
		       ["location A", "location B", "loc C"]);
	})

});
