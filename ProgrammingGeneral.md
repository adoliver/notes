[TOP](README.md)
# Tools
## Markdown
* [Grip](https://github.com/joeyespo/grip)
	* runs a small local server that renders the markdown file using GitHub's interpreter. Allowing any browser to preview the rendering.
	* uses GitHib's markdown API for rendering. May hit API limit, so Grip provides a way to [login](https://github.com/joeyespo/grip#access) to GitHub for a higher hourly rate.
### How to put a codeblock in an ordered list
```
1. First item explaining code block. Leave one blank line, then indent by 8 spaces before each line of the code block.

      cd ~/
      touch tryit.txt
1. The next item with proper numeral
```
1. First item explaining the code block

        cd ~/
        touch itworks.txt
1. The next item with proper numeral
## Git
### alias
```
hide = update-index --assume-unchanged
unhide = update-index --no-assume-unchanged
show-hidden = "!f(){ echo "Files  listed below have assume-unchanged applied"; git ls-files -v | grep '^[[:lower:]]';}; f"
graph = log --graph --pretty=oneline --abbrev-commit
```
### Rebase
* Never rebase a branch that is referenced by any other branch
* *push --force-with-lease* will not force-push if the remote branch HEAD has moved. Prevents accidentally force pushing after changes have been committed to the remote.

### Learning
* [learngitbranching](https://learngitbranching.js.org)
	* good visualization tool allowing real commands to be called. And nice visual tutorial of basic git behavior.
	* has a [sandbox](https://learngitbranching.js.org/?NODEMO) area
		* can chain commands by separating with semi-colon ;
		* can undo commands by typing *undo*
		* can generate a remote repository by typing *git clone*

## Vim

### vimdiff
the merge tool defaults to LOCAL file, BASE common ancestor, REMOTE file from the branch you are merging
'''
:diffu | :diffupdate (refresh diff highlighting after changes)
:only (shows only the merged file)
]c	(next difference)
[c	(previous difference)
do	(diff obtain)
dp	(diff put)
'''

## [Packer](https://www.packer.io/security.html)
A tool to deploy ami server images with customization for security hardening

## Ditch jQuery library
Most of the cross-browser support problems have gone away for the typical usages of jQuery functions. This blog written in 2014 demonstrates the pure javascript handling of typical use-cases.
* [You Don't need jQuery blog](https://blog.garstasio.com/you-dont-need-jquery/)
* &#91; jdLite alternative &#93;
	* have not found a well supported one yet. Preferably one that uses a simplified implementation now that browser support is more homogeneous.
