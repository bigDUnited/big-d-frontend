$(document).ready(function() {
	var url = window.location.pathname
	console.log("shit");
	console.log(url);
	var reservationid = url.substring(url.lastIndexOf("/") + 1, url.length);
	console.log(reservationId);
	var serverUrl = "boyko's server" + reservationId;
	$.get(URL)
	    .done(function(data) {
	    	var tempText = $("#reservationId").text();
	    	$("#reservationId").text(tempText + last);
	 		tempText = $("#location").text();
	    	$("#location").text(tempText + data.location);
	    	var tempText = $("#route").text();
	    	$("#route").text(tempText + data.route);
	    	var tempText = $("#journey").text();
	    	$("#journey").text(tempText + data.journey);
	    	
	    	   	
	 
	    })
	    .fail(function() {
	    	$("#reservationId").text("Invalid reservation id.");
			$("#location").hide();
			$("#route").hide();
			$("#journey").hide();
	    })
	
})