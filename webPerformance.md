# Performant web pages
## high level
* [Page loading blockers](https://hacks.mozilla.org/2017/09/building-the-dom-faster-speculative-parsing-async-defer-and-preload/)
	* inline script tags block DOM and wait for CSSOM.
	* defer attribute on script tags load after DOM and in order with all deferred scripts.
	* DOM > CSSOM > inline script
## iframes
Iframes will load asynchronously, but block the onLoad page event until their contents have rendered. This needs to be worked around by loading the "src" attribute with javascript sometime after the onload event has ocurred.
### Embedded videos
A good pattern for embedded videos will be to have a static image place holder with a play button. Do not load the "src" attribute until the static image is clicked.
