# Comic-Reader

## Libraries 🛠
- [Kotlin](https://kotlinlang.org/) - First class and official programming language for Android development.
- [Coroutines](https://kotlinlang.org/docs/reference/coroutines-overview.html) - For asynchronous and more..
- [Android Architecture Components](https://developer.android.com/topic/libraries/architecture) - Collection of libraries that help you design robust, testable, and maintainable apps.
  - [LiveData](https://developer.android.com/topic/libraries/architecture/livedata) - Data objects that notify views when the underlying database changes.
  - [ViewModel](https://developer.android.com/topic/libraries/architecture/viewmodel) - Stores UI-related data that isn't destroyed on UI changes. 
  - [Room](https://developer.android.com/topic/libraries/architecture/room) - Database Library
- [Navigation Component](https://developer.android.com/guide/navigation/navigation-getting-started) - One Activity More Fragment.
- [Dagger - Hilt](https://dagger.dev/hilt/) - Dependency Injection Framework
- [Firebase](https://firebase.google.com/) - for backend.
- [Coil](https://github.com/coil-kt/coil) - Image loader library.
- [Lottie](https://github.com/airbnb/lottie-android) - Image loader library.
- [PRDownloader](https://github.com/MindorksOpenSource/PRDownloader) - A file downloader library for Android.
- [AndroidPdfViewer](https://github.com/barteksc/AndroidPdfViewer) - PDF Viewer Library with custom properties.


# Package Structure
    .
    .
    .
    ├── di                    # Dependency Injection 
    ├── model                 # Model classes
    ├── network               # For API Service.
    ├── ui                    # Activity
    |    ├── favorite           
    |    |               
    |    ├── main             # MainFragments, MainRepositorys, MainViewModels
    |    |
    |    └── splash
    |
    ├── utils                 # Useful classes
    |
    └── ComicApplication.kt    # @HiltAndroidApp
    
<table style="width:100%">
  <tr>
    <th>Bookshelf Screen</th>
    <th>Detail Screen</th>
    <th>Review Screen</th>
  </tr>
  <tr>
    <td><img src="screenshots/Screenshot_1602333815 (Phone).png"/></td>
    <td><img src="screenshots/Screenshot_1602333932 (Phone).png"/></td>
    <td><img src="screenshots/Screenshot_1602334283 (Phone).png"/></td>
  </tr>
</table>
