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
7. 


##Vim Setup
For using vim as a golang IDE

plugin [project](https://github.com/fatih/vim-go#install)

plugin [tutorial](https://github.com/fatih/vim-go/wiki)
