[TOP](README.md)
# Android Notes
Some notes for positerity when I run into issues or tips & tricks

## Helpful reminders

### Android Lifecycle
* when returning from asynchronous task make sure Fragment/Activity references still exist
	* check Fragment.isResumed() for Fragments or Actions to determine if it is in the foreground. Important before modifying any views.

### RoomStorage
* Always access the DB off the main thread. Android throws an error and potentially time consuming file system access makes it a bad idea.

## Problems

### Need older Android Studio version
1. Find download links for [older versions](https://developer.android.com/studio/archive).
1. Remove exsiting android studio

        # Deletes the Android Studio application
        # Note that this may be different depending on what you named the application as, or whether you downloaded the preview version
        rm -Rf /Applications/Android\ Studio.app
        # Delete All Android Studio related preferences
        # The asterisk here should target all folders/files beginning with the string before it
        rm -Rf ~/Library/Preferences/AndroidStudio*
        # Deletes the Android Studio's plist file
        rm -Rf ~/Library/Preferences/com.google.android.*
        # Deletes the Android Emulator's plist file
        rm -Rf ~/Library/Preferences/com.android.*
        # Deletes mainly plugins (or at least according to what mine (Edric) contains)
        rm -Rf ~/Library/Application\ Support/AndroidStudio*
        # Deletes all logs that Android Studio outputs
        rm -Rf ~/Library/Logs/AndroidStudio*
        # Deletes Android Studio's caches
        rm -Rf ~/Library/Caches/AndroidStudio*
        # Deletes older versions of Android Studio
        rm -Rf ~/.AndroidStudio*
1. Install older version from download link.

### Obfuscated code
There is a full description on the android developer's [website](https://developer.android.com/studio/build/shrink-code#decode-stack-trace) but below is a synopsis.

To convert an obfuscated stack trace to a readable one yourself, use the retrace script (retrace.bat on Windows; retrace.sh on Mac/Linux). It is located in the <sdk-root>/tools/proguard/ directory. The script takes the mapping.txt file and your stack trace, producing a new, readable stack trace. The syntax for using the retrace tool is:
```
retrace.bat|retrace.sh [-verbose] mapping.txt [<stacktrace_file>]
```
Example:
```
retrace.bat -verbose mapping.txt obfuscated_trace.txt
```

### Logcat

#### Too many errors displayed
Occasionally logcat will be spewing a continuous stream of warnings and errors. If these are not being triggered within your app check that you are still filtering correctly. There is an option to filter only the current application.
1. Find the logcat tab
1. Find the filter dropdown. It's on the top to the right of the regex filter. 
1. select "Sown only selected application"

If this fails to solve the issue *fix your app!*

### Images

#### Helpful Attributes
* adjustViewBounds
	* Set this to true if you want the ImageView to adjust its bounds to preserve the aspect ratio of its drawable. [Ref](https://developer.android.com/reference/android/widget/ImageView#attr_android:adjustViewBounds)

## Learning

* ViewModel
	* Helps aintain large datasets during UI restores and signalling between different Fragments.
* Using Uri for listener parameters on Fragments
	* What is the benefit?
	* What is the typical practice?
* "Quick Action UI Model"
	* Display a contextual menu of actions while the target of the action is still visible on screen.
