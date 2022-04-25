# Generate SSH
## *Nix system
1. make sure openssh-client is installed
2. run ```ssh-keygen -t ed25519```
3. follow CLI instructions
4. confirm the generated <name> and <name>.pub files are in the $HOME/.ssh directory.

# Install SSH agent
## *Nix system
1. ```apt-get install openssh-client```
2. add ```eval $(ssh-agent)``` to the terminal profile. This starts the ssh agent and creates environment variables so the system keeps a single agent in-use by default.
