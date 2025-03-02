[TOP](../../README.md) [UP](general.md)

# Shell Scripting

## Neat tricks
The dash(-) character will act on stdin. So `cat -` will cat the current stdin.

## BASH
Bash has some special syntactic sugar to assist

### Append to stdin
`<(COMMAND)` is a special bash usage which can be used with stdin

`echo -n "base" | <(echo -n "prefix...") - <(echo "...postfix")` This will append a prefix and posfix to stdin -


## POSIX
[tutorial](https://www.grymoire.com/Unix/Sh.html#uh-42)

