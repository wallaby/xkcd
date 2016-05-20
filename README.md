# xkcd
Xkcd viewer for Onfido interview.

Mobile Engineer Exercise:
For this exercise, candidates may use any existing open source libraries they may need. The exercise has two different steps as outlined below:

###Build a library
that can be embedded in another app and calls an external API and has a minimal UI to display its results. The library will call the XKCD JSON API [1] and present the data for the comic of the day. A user can then tap one of two buttons: thumbs up / thumbs down to return to the app.

This library should behave as follows:
When invoked by the host app, the library will call the API and show a view with the returned data. This view should have an image, the title and show the alt text when the image is tapped.
Both the thumbs up / thumbs down buttons would make our library UI go away, return to the previous app screen and notify the app about the user option (thumbs up or down) in some sort of callback.

###Create an app
that uses the library described above and brings it up when tapping a button in the app’s starting view.

The app should include the library and bring it up when a button is tapped. The app shouldn’t care about calling any API or gathering user feedback; all of that should be the library’s responsibility.

[1] http://xkcd.com/json.html
