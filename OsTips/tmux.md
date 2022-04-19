[TOP](../README.md) | [OS Tips](../osTips.md)

# TMux terminal
installation [instructions.](https://github.com/tmux/tmux/wiki/Installing)

## config files
[tmux config](../examples/OsConfigs/Nix/.tmux.conf)
Default prefix command is C-b

```bind -n``` removes the prefix from the command
```bind -r``` allows the command to be repeated without repeating the prefix, so long as the command is repeated within about 1s

```S-``` is the shift key

```C-``` is the control key

```M-``` is the Meta key
* To use the meta key on OSX you will need to go into the terminal preferences > keyboard and select "Use option as Meta"

To reload the config file run ```tmux source-file <path to config file>```

### Tips
* make sure to unbind the default key when creating a new mapping
