from pytube import YouTube

# name of the file from which to read the video URL's
filename = 'videodownloadlist.txt'
# destination folder name for downloaded videos
dest_folder = 'C:\\YouTubeDownloads'

# read all the video URL's for downloading
with open(filename) as f:
    lines = [line.strip() for line in f]
print('Total number of videos : ', len(lines))

# download them one after another
count = 0;
for line in lines:
    yt = YouTube(line)
    print('Downloading - ', yt.title)
    # selecting the best resolution in mp4 format
    stream = yt.streams\
                .filter(progressive=True, file_extension='mp4')\
                .order_by('resolution')\
                .desc()\
                .first()
    if stream is not None:
        stream.download(dest_folder)
        count = count+1
        print('Downloaded...')
    else:
        print('Could not download - ', yt.title)
print('Total number of videos downloaded :',count)
