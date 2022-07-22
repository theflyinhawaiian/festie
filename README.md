# festie
Music recommendation app based on festival lineups, interfaces with the spotify API to suggest the user's ideal festival experience

## Welcome!
This app serves dual functions:

1. To demonstrate my programming/architecting abilities, as well as proof that I'm able to write Kotlin in a (somewhat) sane manner.
2. To be a proof-of-concept app for a fun idea I've had for a little while involving the Spotify API

What this app is not:
1. Production Quality! I don't intend this app to be consumed by the general public, so while I'm putting effort in to make the app clean and usable, most of my focus is on getting the happy path to work.
2. A reflection of what I'm able to do an any set period of time; this is a hobby project that I only work in my spare time, when I'm motivated to ;P

Current features:
- Uses the AppAuth Android library to facilitate OAuth functionality with the Spotify API
- Retrofit implementation for artist search
- Lineup selection (currently manual, artist-by-artist on the app. Ideal situation would be to have festival lineups provided by a web-backend that a user could choose between)

Planned features:
- Playlist creation (handled by the app. Ideally, playlist creation would be handled on a web-backend and the playlist id would be given to the app to display to the user)
- Dependency injection with Koin
- Rudimentary "smart" playlist modification via like/dislike buttons (web-backend replacment candidate)
- In-app music streaming via the Spotify Streaming API
