[TOP](../README.md)
[OS Tips](../osTips.md)

# TMux terminal

## config files
[tmux config](../examples/OsConfig/Nix/.tmux.conf)

```bind -n``` removes the C-b prefix from the command

```S-``` is the shift key

```C-``` is the control key

```M-``` is the Meta key
* To use the meta key on OSX you will need to go into the terminal preferences > keyboard and select "Use option as Meta"

To reload the config file run ```tmux source-file <path to config file>```

### Tips
* make sure to unbind the default key