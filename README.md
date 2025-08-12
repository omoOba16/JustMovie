JustMovie is an Android application built with Jetpack Compose that allows users to discover and explore movies and TV shows. It features a modern UI, endless scrolling for browsing extensive lists, and dynamic content loading.

## Features

*   **Modern UI with Jetpack Compose:** Entirely built using Kotlin and Jetpack Compose for a declarative and responsive user interface.
*   **Movie & TV Show Discovery:**
    *   Separate sections for Movies and TV Shows.
    *   Categorized lists (e.g., Now Playing, Popular, Top Rated, Airing Today, On The Air for TV Shows).
*   **Endless Scrolling:** Seamlessly load more movies or TV shows as the user scrolls through lists and grids.
*   **Dynamic "See All" Screens:** When users tap "See All" on a section, they navigate to a dedicated screen that loads all items for that category with endless scrolling.
*   **Translucent Status Bar:** Provides an immersive edge-to-edge viewing experience.
*   **Customizable Bottom Navigation:** Easy navigation between main sections of the app with support for both Material Icons and custom drawable icons.
*   **Shimmer Loading Effect:** Provides a smooth loading experience with shimmer placeholders for content being fetched.
*   **Error Handling:** Displays appropriate messages for network errors or when no data is found.

## Tech Stack & Libraries

*   **Kotlin:** Primary programming language.
*   **Jetpack Compose:** For building the native UI.
    *   **Compose Navigation:** For handling in-app navigation.
    *   **Material 3:** For UI components and theming.
*   **Coroutines & Flow:** For asynchronous operations and reactive data streams.
*   **ViewModel:** To manage UI-related data in a lifecycle-conscious way.
*   **Coil 3:** For image loading and caching.
*   **Retrofit & OkHttp:** (Assumed, if fetching data from a remote API like TMDB) For networking.
*   **Dagger Hilt:**  For dependency injection.
*   **Android Architecture Components:** Leveraging modern Android development patterns (MVVM).

*   ### API Key

This project uses The Movie Database (TMDB) API. To run the app, you need to obtain an API key and an Access Token from TMDB.

1.  Create an account at [TMDB](https://www.themoviedb.org/signup).
2.  Go to your account settings, then the "API" section.
3.  Request an API Key.
4.  You will also need your **API Read Access Token (v4 auth)**.

Once you have your Access Token:

1.  Create a `local.properties` file in the root of your project (if it doesn't already exist).
2.  Add your TMDB Access Token to the `local.properties` file:
