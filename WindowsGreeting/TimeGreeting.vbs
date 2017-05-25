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
 
' adding weather info module
DIM weather
weather= "http://api.openweathermap.org/data/2.5/weather?id=1277333&appid=<Please use your openweathermap appid>&mode=xml&units=metric"
set objHttp = CreateObject("Msxml2.ServerXMLHTTP")
objHttp.open "GET", weather, False
objHttp.send
If (objHttp.Status = 200) Then
XMLData = objHttp.ResponseText
DIM objXM, weatherCity, weatherCondition, currTemp, highTemp, lowTemp
Set objXML = CreateObject("Microsoft.XMLDOM")
objXML.async = "False"
objXML.loadXML(XMLData)
weatherCity = objXML.getElementsByTagName("city").item(0).attributes.getNamedItem("name").text
weatherCondition = objXML.getElementsByTagName("weather").item(0).attributes.getNamedItem("value").text
currTemp = objXML.getElementsByTagName("temperature").item(0).attributes.getNamedItem("value").value
highTemp = objXML.getElementsByTagName("temperature").item(0).attributes.getNamedItem("max").value
lowTemp = objXML.getElementsByTagName("temperature").item(0).attributes.getNamedItem("min").value
Set Sapi = Wscript.CreateObject("SAPI.SpVoice")
Sapi.speak "The current weather in " & weatherCity & " is " & weatherCondition & " and " & currTemp & " degrees. The low today will be " & lowTemp & " with a high of " & highTemp & " degrees."
else
Set Sapi = Wscript.CreateObject("SAPI.SpVoice")
Sapi.speak "Sorry, Unable to get weather report at the moment"
End if