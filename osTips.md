[TOP](README.md)

# OSX

## Filesystem

* Show hidden folders in finder ```CMD + SHIFT + period(.)```

## Terminal

[TMux](OsTips/tmux.md) is a windowed terminal multiplexer, allowing multiple terminals in a single window.
* Installation
	* Use ```brew install tmux``` and add ```tmux new -A -s main``` to run on startup in the terminal profile

# Windows
Windows now has the WSL(Windows Subsystem for Linux) allowing an easy VM situation.
The Kali Linux minimal image can be quickly configured into a development environment.

# Kali Linux - minimal
For development I am trying the Kali minimal installation, which has vim, git, ssh and a few networking tools. The following are tools I've had to add to complete a dev environment:
* man pages
  * ```apt-get install man```
  * ```apt-get install manpages```
* tmux ```apt-get install tmux```
* vim ```apt-get install vim```

## Update the System
[Kali update documentation](https://www.kali.org/docs/general-use/updating-kali/)
