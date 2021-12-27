$(document).ready(function() {
	$.ajax({
		type: "GET",
		url: "http://teraenergy.iptime.org:10070/getDeviceInfo?deviceId=eb5f163e05f612e8e7p9w4",
		success: function(res) {
			var va_temperature = parseInt(res.result.status[0].value) * 0.1;
			var va_humidity = res.result.status[1].value
			$("#va_temperature").text(va_temperature.toFixed(2) + "°C")
			$("#va_humidity").text(va_humidity + "%")
		},
		error: function(res) {
			var json = res
		}
	})
})