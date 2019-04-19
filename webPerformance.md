# Performant web pages
## iframes
Iframes will load asynchronously, but block the onLoad page event until their contents have rendered. This needs to be worked around by loading the "src" attribute with javascript sometime after the onload event has ocurred.
### Embedded videos
A good pattern for embedded videos will be to have a static image place holder with a play button. Do not load the "src" attribute until the static image is clicked.
