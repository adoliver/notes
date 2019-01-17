# Selenium Automation
This is a collection of notes for developing selenium web-browser tests.

## Coding Help

### Interacting with Tabs
|code	|description	|
|---: |:--- |
| WebDriver.getWindowHandler() | String ID for the current "tab" |
| WebDriver.switchTo().window(WindowHandler) | Change contexts into another tab with the provided ID |

1. before triggering a tab record the WindowHandler ID. The context will change to the new tab when triggered.
1. If you want to return to the tab later
1. return to original tab with switchTo() command	

## Tips & Tricks
Not necessarily useful for coding or even permanent solutions.

### Prevent the browser from disappearing
When developing on a local machine you can prevent the browser from disappearing by turning off the WebDriver.quit() call
used when cleaning up after a test.
