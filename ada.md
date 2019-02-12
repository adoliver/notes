# visual compliance
* need enough contrast between colors for differentiation
* don't use color only to indicate information (text or iconography)

# screen readers
* repeteated text with different functionality needs to have aria-labels so that the speech reader can get more informative context.

# links & buttons
* everything needs to be <a> or <button>, do not use regular tags for these. Screen readers will not present them as links.
* buttons control on-screen behavior
* a tags control navigation

# images
* purely decorative images need aria-hidden attribute so that screen readers do not anonunce them

## svgs
* need role="img" so they are treated as images.

# Screen Events
* aria-relevant ?? how is this used compare to aria-live
* aria-live
	* used to announce UI updates to the user
	* assertive announces immediately on change
		* typically for popups, immediate user interaction or important content refreshes
		* user is expected to want to immediately interact with the change
	* polite announces once the user pauses their interaction
		* for passive information that updates automatically. e.g. a stock ticker or 
* role="alert"
	* intended for error messages
	* attach to top container of error message
* aria-haspopup="dialog"
	* needed for all popup triggers
* aria-expanded="true|false" used for accordian menus

## When Content is updated
* trigger focus point for tab order to be recalculated
* decide on assertive or polite announcement aria-live
* update any aria-hidden attributes with javascript as needed
