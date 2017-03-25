# android-artist-details
example Artist-album-songs 
Using the API documented here
http://restpack-serializer-sample.herokuapp.com/ write a mobile app that displays a list of artists, 
allows the user to click on an artist to get details about the artist, including a list of albums.
Selecting an album should display a list of songs. The code should be as lean as possible and third party libraries ar 
discouraged but not forbidden.
What We're Looking For.
We're not expecting brilliant looking apps, we're much more interested in functionality and process. 
We would love to see a git/svn repository with the history of how you built it (check-in early and often so 
we can see your thought processes). Document any major decisions you made or choices you took. Working code is 
preferred but non-functional with good testing and an explanation is also welcome.

1. Create activity
2. Create Network fragment that will encapsulate the network threading 
3. In Fragment initiate the download callback and download task class which will return Result in main thread in post execute and 
   and DownloadCallback will help to update Activity
4. JsonParser will parse the data from server. and returns the respective objects.

UI can be displayed in different fragment with RecycleView once we get callbacks from Result.
   
   