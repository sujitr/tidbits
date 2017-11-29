Set Sapi = Wscript.CreateObject("SAPI.SpVoice")
 if hour(time) < 12 then
	Sapi.speak "Good Morning Sujit "
 else
	if hour(time) > 12 then
		if hour(time) > 16 then
			Sapi.speak "Good evening Sujit "
		else
			Sapi.speak "Good afternoon Sujit "
		end if
	end if
 end if
 
' set up to check internet connection
Set oShell = WScript.CreateObject("WScript.Shell")
strHost = "4.2.2.2" 'could also be google.com
strPingCommand = "ping -n 1 -w 3000 " & strHost
returnCode = oShell.Run(strPingCommand, 0, True)

If (returnCode = 0) Then
	' run all the web stuffs from weather info
	DIM weather
	weather= "http://api.openweathermap.org/data/2.5/weather?id=1277333&appid=<Please use your openweathermap appid>&mode=xml&units=metric"
	set objHttp = CreateObject("Msxml2.ServerXMLHTTP")
	objHttp.open "GET", weather, False
	objHttp.send
	If (objHttp.Status = 200) Then
		XMLData = objHttp.ResponseText
		DIM objXM, weatherCity, weatherCondition, currTemp, highTemp, lowTemp, humidity
		Set objXML = CreateObject("Microsoft.XMLDOM")
		objXML.async = "False"
		objXML.loadXML(XMLData)
		weatherCity = objXML.getElementsByTagName("city").item(0).attributes.getNamedItem("name").text
		weatherCondition = objXML.getElementsByTagName("weather").item(0).attributes.getNamedItem("value").text
		currTemp = objXML.getElementsByTagName("temperature").item(0).attributes.getNamedItem("value").value
		highTemp = objXML.getElementsByTagName("temperature").item(0).attributes.getNamedItem("max").value
		lowTemp = objXML.getElementsByTagName("temperature").item(0).attributes.getNamedItem("min").value
		humidity = objXML.getElementsByTagName("humidity").item(0).attributes.getNamedItem("value").value
		Set Sapi = Wscript.CreateObject("SAPI.SpVoice")
		Sapi.speak "The current weather in " & weatherCity & " is " & weatherCondition & " and " & currTemp & " degrees. The low as of now will be " & lowTemp & " with a high of " & highTemp & " degrees, with a humidity of "& humidity & " percent."
	else
		Set Sapi = Wscript.CreateObject("SAPI.SpVoice")
		Sapi.speak "Sorry, Unable to get weather report at the moment. Server is saying something went wrong back there."
	End if
Else
	' Announce no internet connectivity
	Set Sapi = Wscript.CreateObject("SAPI.SpVoice")
	Sapi.speak "I'm unable to fetch weather report. It seems internet connection is unavailable at the moment."
End If
