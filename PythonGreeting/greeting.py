import requests
from xml.dom import minidom
from gtts import gTTS
import os
import math

url = "http://api.openweathermap.org/data/2.5/weather?id=1277333&appid=<OpenWeatherAPIKey>&mode=xml&units=metric"

print(url) 

response = requests.get(url)

'''print(response.content)'''

xmldoc = minidom.parseString(response.content)
weatherCity = xmldoc.getElementsByTagName('city')[0].attributes['name'].value
weatherCondition = xmldoc.getElementsByTagName('weather')[0].attributes['value'].value
currTemp = xmldoc.getElementsByTagName('temperature')[0].attributes['value'].value
highTemp = xmldoc.getElementsByTagName('temperature')[0].attributes['max'].value
lowTemp = xmldoc.getElementsByTagName('temperature')[0].attributes['min'].value
humidity = xmldoc.getElementsByTagName('humidity')[0].attributes['value'].value

modTemp = ""

if "." in str(currTemp):
    split_num = str(currTemp).split('.')
    int_part = int(split_num[0])
    decimal_part = int(split_num[1])
    modTemp = str(int_part)+"point"+str(decimal_part)
else:
    modTemp = str(currTemp)


forecast = "The current weather in " + weatherCity + " is " + weatherCondition + " and " + modTemp + " degrees. The temperature for the moment could range from a low of " + str(lowTemp) + " with a high of " + str(highTemp) + " degrees, with a humidity of " + str(humidity) + " percent."

print(forecast)

tts = gTTS(text=forecast, lang='en')
tts.save("weather.mp3")
#os.system("C:\mpg123-1.23.8-x86-64\mpg123 weather.mp3")




