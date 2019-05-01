# Git Rebasing
* title shared branches
* Do not rebase shared or chained branches
* use *push --force-with-lease* to update remote after rebase

## force push (--force-with-lease)
This is the output on failure
```
2019-02-14 12:27[latte] $git push --force-with-lease
To https://gitserver.fairisaac.com:8443/scm/myfico/latte.git
 ! [rejected]          eclipseRebaseConflict_force -> eclipseRebaseConflict_force (stale info)
error: failed to push some refs to 'https://AllenOliver@gitserver.fairisaac.com:8443/scm/myfico/latte.git'
```

## ReReRe
When turned on this tool will automatically replay conflict resolutiion by identifying previous conflicts on a line of code and re-use the stored resolution when encountered again. This is great for rebasing a branch from master that has many conflicts during the rebase resolution.
```
2019-02-14 13:01[latte] $git rebase --continue
Applying: update conflict
Recorded resolution for 'code/myFICO/src/main/resources/com/myfico/config/env/local.properties'.
```
