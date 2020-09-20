<!-- PROJECT LOGO -->
<br />
<p align="center">
  <a href="https://github.com/RSDosev/square-github-repos">
    <img src="https://cdn.worldvectorlogo.com/logos/square.svg" alt="Logo" width="80" height="80">
  </a>

  <h3 align="center">SquareRepos</h3>

  <p align="center">
    Simple browser of Square's repositories on GitHub
  </p>
</p>

## Demo
<img src="demo.gif" alt="alt text" width="350" height="700">

## About the implementation

- MVVM architecture
- Kotlin
- Android Jitpack (ViewModel, Navigation component, LiveData)
- Coroutines and Flow
- Koin dependency injection
- Unit and instrumented tests
- Modularization by layers 
  - App module - contains the UI logic (Activities, Fragments and ViewModels) + unit tests for the ViewModels and UI tests for the Activities and Fragments
  - Data module - c
  - Domain module - contains the business login (UseCases and Interactors) + unit tests
- Handling of loading, content and failure states
- In-memory caching
