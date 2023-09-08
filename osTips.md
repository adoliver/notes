[TOP](README.md)
* [Nix](osTips.md#nix)
* [OSX](osTips.md#osx)
* [Windows(osTips.md#windows)

# *Nix

## Commands

### searching for files

Find files while excluding certain directories `find . -name "*.rego" -type f -not \( -path "./dir_to_exclude/*" -prune \)`

*-prune* is used to exclude find from traversing that part of the tree when there is a match. Speeding up traversal.

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
* ssh-add from openssh-client tool ```apt-get install openssh-client```
	* add ```eval $(ssh-agent)``` to the terminal profile. This starts the ssh agent when the terminal loads.
* git ```apt-get install git```
* golang ```apt-get install golang```

## Update the System
[Kali update documentation](https://www.kali.org/docs/general-use/updating-kali/)

# Uninstall/Reinstall a WSL distro
1. open powershell
2. ```wsl -l``` lists all installed WSL
3. ```wsl --unregister <name_from_list``` uninstalls the WSL
## Reinstall
4. open windows store
5. Navigate to WSl distro
6. May show "open" button or "install" or "download". Click it. If showing "open" the system will discover it is not present and re-download.
