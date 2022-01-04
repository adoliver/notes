# New Project Setup
The main idea is to set up your project setup so that the python and modules running in your project directory are isolated from the rest of the system. This way you never have to care about what the system default may be.

## OSX Install Python
1. install xcode-select ```xcode-select --install```
2. install [homebrew](https://brew.sh/)
3. install pyenv ```brew install pyenv```
4. create python project directory
5. install python with pyenv ```pyenv install <python_version>``` (3.10.1 as of writing)
6. set python version as global:

        pyenv global <python_version>
        # verify it worked
        pyenv version
        3.10.1 (set by /Users/Allen.Oliver/.pyenv/version)
5. Add the following to your shell profile

        export PYENV_ROOT="$HOME/.pyenv"
        export PATH="$PYENV_ROOT/bin:$PATH"

        # Check if pyenv command exists
        if command -v pyenv 1>/dev/null 2>&1; then
                # run pyenv init script
                eval "$(pyenv init --path)"
                eval "$(pyenv init -)"
                echo "pyenv initialized..."
        fi
6. Install virtuaenvwrapper ```$(pyenv which python3) -m pop install virtualenvwrapper```
7. Add the following to the shell profile. When the $WORKON_HOME doesn't exist this will reinitialize the virtual env directories and files.

        # home directory to store virtual environment files
        export WORKON_HOME=~/.virtualenvs
        mkdir -p $WORKON_HOME
        # retrieve the correct path to virtualenvwrapper.sh for the global python version
        virtual_env_path="$(pyenv prefix)/bin/virtualenvwrapper.sh"
        # activate the new virtual environment
        . $virtual_env_path

## Create Project w/pyenv
Create a new virtual environment with the ```mkvirtualenv <name>``` command. Best practice is to take advantage of a directory naming scheme so that your environments are named by the root directory name.

    $ mkdir -p ~/src/python_project_a && cd ~/src/python_project_a
    $ mkvirtualenv $(basename $(pwd))
    # do stuff
    (python_project_a)$ deactivate
From then on you will be able to navigate to the directory and use the command ```workon .```
