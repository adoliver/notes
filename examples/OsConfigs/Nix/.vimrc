colorscheme molokai
syntax on

""" Leader config """
" must happen before other mappings to take effect

" unset anything on <space>
noremap <space> <nop>

let mapleader=" "
""" End Leader config """

""" Set up relative numbering for active file """

" default to normal numbering turned on
set number
" set relativenumber

" when entering an editing window turn on relative numbering. Turn off relative numbering when leaving a window
:augroup numbertoggle
:  autocmd!
:  autocmd BufEnter,FocusGained,InsertLeave,WinEnter * if &nu && mode() != "i" | set rnu   | endif
:  autocmd BufLeave,FocusLost,InsertEnter,WinLeave   * if &nu                  | set nornu | endif
:augroup END

""" End Relative numbering """

function! SetTabs(spaces)
  execute "set noexpandtab"
  execute "set tabstop=" . a:spaces
  execute "set shiftwidth=" . a:spaces
endfunction

filetype on
filetype plugin on
filetype indent on

""" Vim-Go Setup """
" Set go files to 4 space tabs
autocmd BufNewFile,BufRead *.go exec SetTabs(8) 

" Run goimports when saving a file to auto-load any missing dependencies
let g:go_fmt_command = "goimports"

" auto save, if desired. One less step when using GoBuild. Currently too used
" to manually saving.
" set autowrite

" GoTest times out after 10s by default. Vim is not async, so long tests can
" be a problem. Update this timeout if necessary.
" let g:go_test_timeout = '10s'

" Vim-go auto formats the files when saving. This can optionally be turned
" off.
" let g:go_fmt_autosave = 0

" when saving any parsing errors show inside a quickfix list. This disables the
" default checking.
" let g:go_fmt_fail_silently = 1

" Linter uses snake_case by default, to use camelCase instead update this
" setting
" let g:go_addtags_transform = "camelcase"

" GoMetaLinter can run multiple linting checks on every save. Try using go vet, 
" golint, and errcheck all at once.
let g:go_metalinter_enabled = ['vet', 'golint', 'errcheck']
" run certain meta linter tools on every save.
let g:go_metalinter_autosave = 1
let g:go_metalinter_autosave_enabled = ['vet', 'golint']
" timeout because vim is single threaded, default is 5s
" let g:go_metalinter_deadline = "5s"

"" Quality of life mappings ""

" jump between build errors in quickfix list
" autocmd FileType go map <C-n> :cnext<CR>
" autocmd FileType go map <C-m> :cprevious<CR>
" autocmd FileType go nnoremap <leader>a :cclose<CR>

" run :GoBuild or :GoTestCompile based on the go file name
function! s:build_go_files()
	let l:file = expand('%')
	if l:file =~# '^\f\+_test\.go$'
		call go#test#Test(0,1)
	elseif l:file =~# '^\f\+\.go$'
		call go#cmd#Build(0)
	endif
endfunction
" Unset anything previously on the leader command
autocmd FileType go nnoremap <leader>b <nop>
autocmd FileType go nmap <leader>b :<C-u>call <SID>build_go_files()<CR>
autocmd FileType go nnoremap <leader>r <nop>
autocmd FileType go nmap <leader>r <Plug>(go-run)
autocmd FileType go nnoremap <leader>t <nop>
autocmd FileType go nmap <leader>t <Plug>(go-test)
autocmd FileType go nnoremap <leader>c <nop>
autocmd FileType go nmap <leader>c <Plug>(go-coverage-toggle)

"" END Quality of life mappings ""


""" End Vim-Go Setup """
