[TOP](README.md)
# Tools
## Regex
* Online regular expression testing [tool](https://regex101.com/).

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

Results:

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
* If you are repeating the same conflict resolution over and over again, [use git rerere.](https://git-scm.com/docs/git-rerere)

### Reverting a merge commit

TLDR Don't do this unless absolutely necessary, prefer instead to add a new commit with a fix. 

If done, recommiting the same code later can be problematic and other complications. Linus has a detailed explanation [here](https://github.com/git/git/blob/master/Documentation/howto/revert-a-faulty-merge.txt).

### Removing files from history

If files got added to the git history which must be removed, there are two options:
1. (preferred) would be to rebase affected local branches to remove the files BEFORE committing to any shared git repository.
1. is to use the [repo-filter](https://github.com/newren/git-filter-repo#why-filter-repo-instead-of-other-alternatives) tool [promoted](https://git.github.io/rev_news/2019/08/21/edition-54/#an-introduction-to-git-filter-repo--written-by-elijah-newren) by the git tool developers.

If the files included secrets, those secrets should preferably be revoked so they are unusable. At that point its probably not necessary to remove them from history, just for future commits to reduce clutter.

### Learning

* [learngitbranching](https://learngitbranching.js.org)
	* good visualization tool allowing real commands to be called. And nice visual tutorial of basic git behavior.
	* has a [sandbox](https://learngitbranching.js.org/?NODEMO) area
		* can chain commands by separating with semi-colon ;
		* can undo commands by typing *undo*
		* can generate a remote repository by typing *git clone*

## Vim

Really good [Cheatsheet](http://www.viemu.com/a_vi_vim_graphical_cheat_sheet_tutorial.html)

### Colorscheme

Search for schemes [here](https://vimcolorschemes.com/)
* [molokai](https://github.com/tomasr/molokai/blob/master/colors/molokai.vim)

Download color script into ```.vim/colors``` directory
e.g.
```
mkdir -p ~/.vim/colors && curl "https://raw.githubusercontent.com/tomasr/molokai/master/colors/molokai.vim" > ~/.vim/colors/molokai.vim
```

Update ```.vimrc``` to load your preferred colorscheme: ```colorscheme <name>```

### vim-go plugin

Good for using vim as a golang IDE

plugin [project](https://github.com/fatih/vim-go#install)

plugin [tutorial](https://github.com/fatih/vim-go/wiki)

### vimdiff

the merge tool defaults to LOCAL file, BASE common ancestor, REMOTE file from the branch you are merging
```
:diffu | :diffupdate (refresh diff highlighting after changes)
:only (shows only the merged file)
]c	(next difference)
[c	(previous difference)
do	(diff obtain)
dp	(diff put)
```

### Research

It would be nice to learn a bit more on how vim treats "object" commands. the vim-go selector ```if``` seems similar to what I wanted to do with having ```<leader>ta``` be "run all the tests in the file" and ```<leader>tf``` be "test just the current function around the cursor." [treating go types as objects in vim](https://medium.com/@farslan/treating-go-types-as-objects-in-vim-ed6b3fad9287#.45q2rtqgf)

## [Packer](https://www.packer.io/security.html)

A tool to deploy ami server images with customization for security hardening

## Ditch jQuery library

Most of the cross-browser support problems have gone away for the typical usages of jQuery functions. This blog written in 2014 demonstrates the pure javascript handling of typical use-cases.
* [You Don't need jQuery blog](https://blog.garstasio.com/you-dont-need-jquery/)
* &#91; jqLite alternative &#93;
	* have not found a well supported one yet. Preferably one that uses a simplified implementation now that browser support is more homogeneous.
