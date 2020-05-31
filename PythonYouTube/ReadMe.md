# Python YouTube Download Script

This script uses [pytube](https://python-pytube.readthedocs.io) library. Please make sure its installed in your system before using this script.

As of now this script reads YouTube video URL's from a txt file 'videodownloadlist.txt', one URL per line. It's by default has one video - Year in Search 2011: Year In Review from Google. The most eventful year for me, so far.

Destination folder needs
to be specified in the script for downloaded videos.

## Resolution of the downloads
Its a tricky part since YouTube has changed its streaming from its legacy Progressive to DASH (Dynamic Adaptive Streaming over HTTP). Although 'Progressive' streams (which contains both video and audio codecs in a single file) are still available, but they do not always provide for high quality resolution, but only for resolutions 720p and below.

DASH is a technique where the video codec (MP4) and the audio codec (MP3) is sent separately as per the best available bandwidth option and then combined at the end point. This makes switching resolutions easy and more adaptive.

In the context of pytube, the implications are for the highest quality streams; you now need to download both the audio and video tracks and then post-process them with software like FFmpeg to merge them.

For more details please check [here](https://python-pytube.readthedocs.io/en/latest/user/quickstart.html#working-with-streams).
