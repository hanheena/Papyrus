var city = "Naju"
var appkey = "502364536faf678e6319a261e9148892"
var apiURI = "http://api.openweathermap.org/data/2.5/weather?q=" + city
	+ "&appid=" + appkey + "&lang=kr&units=metric";
	
$.ajax({
	url: apiURI,
	dataType: "json",
	type: "GET",
	async: "false",
	success: function(resp) {
		
		console.log("타나요");

		var imgURL = "http://openweathermap.org/img/w/"
			+ resp.weather[0].icon + ".png";
		$("#imgimg").attr("src", imgURL);
		$("#temperature").text(resp.main.temp + " °C");
		$("#cityname").text("나주시");
		$("#weather").text(resp.weather[0].description);

	}
})