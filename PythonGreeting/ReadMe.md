Python greeting message (can be called while logging onto shell)
===============================================================

This is a simple python script which generates a greeting message depending upon the time of the day.
It uses the gTTS to create voice mp3 files from Googles API and then mpg123 to play them out. 
The content of the voice are two parts, one is a pre-recorded welcome to the user with his name depending upon time of the day, and then the other one is response from the OpenWeather API for weather details at that moment. 
The second part will be available only when the internet access from the system is available.

## Points of improvement - 
1. Make the announcements more random in the way of addressing
2. More definitive error handling scenarios when it comes to connecting the internet for weather api call

