#Payleven coding challenge

This is the test program for Payleven, written for Android. It's compiled against API 19 (4.4) but supports all Android versions from API 9 (2.3) onwards. It also uses v20 support libraries. It is made to compile against Android Studio Beta (0.8.6) with gradle plugin 0.12. 

The program uses reactive programming techniques that enable event-driven programming, and rx-java with rx-android library enables us to select particular threads we want it to run on (for example, subscribing to product results is on Schedulers.io() thread, while observing and UI updating is on AndroidSchedulers.mainThread()) so as to avoid UI blocking.

The layout was optimized for landscape use on both smartphones and tablets.

##TODO list

- add error handling
- add local data caching when rotating the app

## DEPENDENCIES

- AdvancedAndroidLogger - for advanced logging to logcat
- Lombok - for generating getters, setters, tostring etc.
- Rx-Java (core, Android) - for event-driven programming
- Retrofit with OkHttp - for REST calls with rx support
- Universal image loader - for cached image loading 
- Gson - for JSON <-> POJO conversions

## LICENSE

This app cannot be used for any purposes except by its creator and Payleven, Germany.