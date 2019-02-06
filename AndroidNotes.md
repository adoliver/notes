# Android Notes
Some notes for positerity when I run into issues or tips & tricks

## Helpful reminders

### Android Lifecycle
* when returning from asynchronous task make sure Fragment/Activity references still exist
** check Fragment.isResumed() for Fragments or Actions to determine if it is in the foreground. Important before modifying any views.

### RoomStorage
* Always access the DB off the main thread. Android throws an error and potentially time consuming file system access makes it a bad idea.

## Problems

### Logcat

#### Too many errors displayed
Occasionally logcat will be spewing a continuous stream of warnings and errors. If these are not being triggered within your app check that you are still filtering correctly. There is an option to filter only the current application.
1. Find the logcat tab
1. Find the filter dropdown. It's on the top to the right of the regex filter. 
1. select "Sown only selected application"

If this fails to solve the issue *fix your app!*

## Learning

* ViewModel
** Helps aintain large datasets during UI restores and signalling between different Fragments.
* Using Uri for listener parameters on Fragments
** What is the benefit?
** What is the typical practice?
