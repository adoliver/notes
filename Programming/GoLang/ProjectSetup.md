[TOP](../../README.md)

# New Project Setup
For a refresher on modules see [using-go-modules](https://go.dev/blog/using-go-modules) and [create-module](https://go.dev/doc/tutorial/create-module)

## OSX
1. install [homebrew](https://brew.sh/)
1. install xcode
  1. check if already installed ```xcode-select -p```
  2. path to CommandLineTools means it is installed
  3. to install run ```xcode-select install```
1. check that homebrew is up to date ```brew doctor```
4. install golang ```brew install golang```
5. create workspace ```mkdir -p $HOME/go/{bin,src}```
6. Add the following to your terminal profile:

        export GOPATH=$HOME/go 
        export PATH=$PATH:$GOPATH/bin
7. Install [Vim go](#vim-setup)


## Vim Setup
Vim-go plugin for using vim as a golang IDE

KNOWN ISSUES
* GoDef utilizing ```guru``` may [not work](https://golang.org/cmd/go/#hdr-Module_support) outside of GOPATH.\
    One of the most noticeable changes in the ecosystem since this tutorial was written is the 
    introduction of Go modules. The tutorial does not yet explain some of the differences in 
    behavior of the go tool and other community provided tools in [GOPATH mode vs module aware 
    mode](https://golang.org/cmd/go/#hdr-Module_support). The vast majority of vim-go features 
    work as described here with the exception of 
    commands that rely on ```guru```. ```guru``` only works as expected in 
    [GOPATH mode](https://golang.org/cmd/go/#hdr-Module_support). Other commands may 
    require the use of ```gopls```, and many options have been added to vim-go to control whether gopls 
    or the traditional tool is used under the hood. Please reference vim-go's help (```:help vim-go```)
    when in doubt.
    * ```let g:go_def_mode = 'gopls'``` may be up-to-date for modules (need to investigate)

## Installation
1. plugin [project](https://github.com/fatih/vim-go#install) into ```.vim/pack/plugins/start```
    1. may want to have the plugin and vim settings load dynamically later. 
    2. vim-go overrides ```[[``` and ```]]``` movement behavior to use golanc func AST instead of braces {}
    3. vim-go overrides ```K``` to use GoDoc instead of manpages
3. make sure "Vim-Go setup" from the [.vimrc](../../examples/OsConfigs/Nix/.vimrc) example is in your .vimrc
4. if not present, install the [Ctlp plugin](https://github.com/ctrlpvim/ctrlp.vim) into ```.vim/pack/plugins/start``` 

plugin [tutorial](https://github.com/fatih/vim-go/wiki)

consider adding [snippets](https://github.com/fatih/vim-go-tutorial#snippets) for code boiler-plate

consider adding [split/join](https://github.com/fatih/vim-go-tutorial#struct-split-and-join) plugin to edit structs.

consider adding additional [syntax highlighting](https://github.com/fatih/vim-go-tutorial#beautify-it)

consider adding :GoDecls to <leader>d

### vim-go Cheatsheet
#### Navigating definitions and references
```gd``` ```Ctr-]``` both go to the definition under the curor
```Ctr-t``` pops the Go navigation stack to return. GoDefPop under the hood. (Consider adding GoDefStack to a leader to see the breadcrumb trail)

#### vim targets in Normal Mode 
```if``` vim specifier means "inner" content of the function block

```af``` vim specifier means "all" content of the function block, including description comments

e.g. ```yif``` yank the "inner' content of the funtion block.
