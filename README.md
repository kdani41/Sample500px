#Summary
* Sample app using Retrofit + Dagger + RxJava using 500px api.
* Supporting api>=18

<p align="left">
<img src="/demo/portrait.gif" />
</p>

<p align="right">
<img src="/demo/landscape.gif" />
</p>

## Requirements
* Add your consumer key by creating a file _secret.properties_ under app directory. File should be like _API_KEY=YOUR_API_KEY_
* You can get an API consumer key here: [500px Consumer Key](https://500px.com/settings/applications)
* You’ll first need to log in with your 500px account. Please create a 500px account if you don’t yet have one.

# Architecture Pattern
<p align="center">
<img src="/demo/MVP Flow.png" />
</p>

#Coding Style
* [Android - java-code-styles] (https://source.android.com/source/code-style.html)

#Key Concept
There are 2 screens in the app
* [Grid Screen](/app/src/main/java/com/d500px/fivehundredpx/views/grid_screen) 
* [Full Screen](/app/src/main/java/com/d500px/fivehundredpx/views/full_screen)

Each Screen has
* A contract defining the view and the presenter
* An Activity which is responsible for the creation of fragments and presenters
* A Fragment which implements the view interface.
* A presenter which implements the presenter interface

In general, the business logic lives in the presenter and relies on the view to do the Android UI work.
The view contains almost no logic: it converts the presenter's commands to UI actions and listens to user actions, which are passed to the presenter.

# Special Thanks To
* [GoogleSample](https://github.com/googlesamples/android-architecture) - For providing detailed understanding of different architecture.
* [500px](https://github.com/500px/api-documentation) - Using 500px api to display photos.
* [Retrofit2](http://square.github.io/retrofit/) - For sending api get request.
* [Picasso](http://square.github.io/picasso/) - For image loading and Caching
* [OkHttp](http://square.github.io/okhttp/) - Used by picasso for fast downloading of images
* [AppCompat](http://developer.android.com/intl/vi/tools/support-library/features.html) - Used for providing backward compatibility support
* [RxAndroid/ RxJava](https://github.com/ReactiveX/RxAndroid) - Used to caching remote calls which uses Observer pattern to subscribe and un-subscribe ui thread when not in view.
* [RxProguard Rules](https://github.com/artem-zinnatullin/RxJavaProGuardRules) - ProGuard rules for RxJava shipped as AAR!
* [ButterKnife](http://jakewharton.github.io/butterknife/) - Removing boiler plate code by using @Bind annotation.
* [Dagger 2](http://google.github.io/dagger/android.html) - For using dependency injection in android without reflection.
* [Android Support Libraries](http://developer.android.com/intl/vi/tools/support-library/features.html#v7-recyclerview) - For using support widgets like recycler view.
* [Espresso](https://google.github.io/android-testing-support-library/docs/espresso/) - Used for UI Testing
* [Mockito](http://http://mockito.org/) - Used for mocking classes in unit testing
* [JUnit](http://developer.android.com/intl/ko/tools/testing/testing_android.html) - Testing Library

