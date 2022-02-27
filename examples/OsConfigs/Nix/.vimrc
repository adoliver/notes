set number
" set relativenumber

:augroup numbertoggle
:  autocmd!
:  autocmd BufEnter,FocusGained,InsertLeave,WinEnter * if &nu && mode() != "i" | set rnu   | endif
:  autocmd BufLeave,FocusLost,InsertEnter,WinLeave   * if &nu                  | set nornu | endif
:augroup END

function! SetTabs(spaces)
  execute "set tabstop=" . a:spaces
  execute "set shiftwidth=" . a:spaces
endfunction
