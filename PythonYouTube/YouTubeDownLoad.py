from pytube import YouTube

filename = 'videodownloadlist.txt'
with open(filename) as f:
    lines = [line.strip() for line in f]

print('Total number of videos : ', len(lines))
tag = 22
count = 0;
for line in lines:
    yt = YouTube(line)
    print('Downloading - ', yt.title)
    stream = yt.streams\
                .filter(progressive=True, file_extension='mp4')\
                .order_by('resolution')\
                .desc()\
                .first()
    if stream is not None:
        stream.download('C:\\ScottVideos\\Morning2')
        count = count+1
        print('Downloaded...')
    else:
        print('Count not download - ', yt.title)
print('Total number of videos downloaded :',count)
