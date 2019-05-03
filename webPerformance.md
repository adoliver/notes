# Performant web pages
## high level
* [Page loading blockers](https://hacks.mozilla.org/2017/09/building-the-dom-faster-speculative-parsing-async-defer-and-preload/)
	* inline script tags block DOM and wait for CSSOM.
	* defer attribute on script tags load after DOM and in order with all deferred scripts.
	* priority: inline script > CCSOM > DOM
* [Async inline script](https://www.igvita.com/2014/05/20/script-injected-async-scripts-considered-harmful/) discussion
	* browsers have a "preload scanner" that tries to look ahead in the DOM to identify resources that need downloading. And start the downloading as early as possible.
## iframes
Iframes will load asynchronously, but block the onLoad page event until their contents have rendered. This needs to be worked around by loading the "src" attribute with javascript sometime after the onload event has ocurred.
### Embedded videos
A good pattern for embedded videos will be to have a static image place holder with a play button. Do not load the "src" attribute until the static image is clicked.
## Tools
* [Performance Timing](https://w3c.github.io/perf-timing-primer/)
	* window.performance
	* getDate() is unreliable and too coarse for page load timing in micro-seconds.
