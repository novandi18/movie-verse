<div align="center">
  <img src="https://i.ibb.co/0hkYTf0/movieverse.png" width="100px"/>
  <h1 align="center">Movie Verse</h1>
  <h3>The Movie Reference Based on TheMovieDB</h3>

  <p align="center">
    <a href="https://skillicons.dev">
      <img src="https://skillicons.dev/icons?i=kotlin,androidstudio" />
    </a>
  </p>

  Movie Verse is an innovative Android app that enriches your movie-watching experience by providing a comprehensive database of movie references sourced from TheMovieDB. Built with [Jetpack Compose](https://developer.android.com/jetpack/compose).

</div>

## Features
- **Authentication:** Connect with your TheMovieDB account.
- **Explore Movies:** Find thousands of films from various genres and countries easily through search and by popularity.
- **Favorite, Watchlist and Rated:** You can save movies to Favorites, Watchlist and rate them!

## Tech Stack
- [Kotlin](https://kotlinlang.org/) - Language
- [Jetpack Compose](https://developer.android.com/jetpack/compose) - UI Toolkit
- [Dagger Hilt](https://dagger.dev/hilt/) - Dependency Injection
- [Retrofit](https://square.github.io/retrofit/) - Network
- [OkHttp](https://square.github.io/okhttp/) - HTTP client
- [Room Database](https://developer.android.com/reference/kotlin/androidx/room/RoomDatabase) - Local Database

## Architecture
Multi-Module Architecture

## Design Pattern
Model-View-ViewModel (MVVM)

## Screenshot
![Movie Verse](https://github.com/user-attachments/assets/c0020ab4-7275-4895-8449-503c1547f8a2)


## Get Started
### 1. Clone the repository

```shell
git clone https://github.com/novandi18/movie-verse.git
cd movie-verse
```
### 2. Get your Authorization Token from TheMovieDB
Here's a complete guide to getting your api key: [TMDB - Get Started](https://developer.themoviedb.org/docs/getting-started)

### 3. Put your Authorization Token
Put your Authorization Token into local.properties like this
```shell
AUTHORIZATION = "your_authorization_token"
```
