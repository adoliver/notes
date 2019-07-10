#!/bin/bash
isVerbose=false
numLines=-1
startDir=""
destinationDir=""


usage() {
	echo "bashSimple [-h|--help|-v|--verbose|-n N|--length N] <rootDirectory>"
	echo "	simple bash file to demonstrate multiple useful techniques"
	echo "	Options:"
	echo "	-h | --help		display this message"
	echo "	-v | --verbose		display additional output"
	echo "	-n N | --length N	display only the first N lines of output"
	
}

# loop through the option flags provided after the script name
# loop until an option flag doesn't match what is expected
while [ "$1" != "" ];do
	case $1 in
		-h | --help )	usage # call the usage() function
				exit 0 # exit the script with a success
				;;
		-v| --verbose )	isVerbose=true # set up a local variable
				;;
		-n| --length )	shift #get the next value which will be stored
				numLines=$1
				;;
		* )		break #after the first non-matching option assume the remainder are the arguments
				;;
	esac
	shift
done

# now that the options have been consumed the remaining numbered parameters are in the expected positions
if [ "$1" != "" ]; then
	startDir=$1
fi
if [ "$2" != "" ]; then
	destimationDir=$2
fi

# do something to the file path passed
# function definition needs to before the function is used
datePublishedLocator="##date_published##"
dateModifiedLocator="##date_modified##"
perFileProcessing() {
	# retrieve the paramters the same way the script parameters are retrieved
	if [ "$1" != "" ]; then
		srcFile="$startDir/$1"
		targetFile="$destinationDir/$1"

		# store the result of a command into a variable
		datePublished=$(git log --diff-filter=A --format=%cI --date=iso "$srcFile") # find the add commit(--diff-filter=A) and retrieve the commit date
		dateModified=$(git log -n 1 --format=%cI --date=iso "$srcFile") # find the most recent commit(-n 1) and retrieve the commit date

		# run text replacement with sed
		# copy the output to a new file
		# OR in command will call the second command if the first exits with non-zero
		isError=false
		sed -e "s/$datePublishedLocator/$datePublished/" -e "s/$dateModifiedLocator/$dateModified/" "$srcFile" > "$targetFile" || isError=true
		if ($isError); then
			return 1 #return with an error code
		fi

		if [ ! -r "$targetFile" ]; then
			# target file can not be read. Something has gone wrong
			return 2 #return with an error code
		fi

		if ($isVerbose); then
			echo "scanned $srcFile for date placeholders published=$datePublished modified=$dateModified"
		fi
	fi
	return 0
}

# loop over the results of a command
# 1. cd to initial directory
# 2. run find in the directory
# 3. process each file found
hasError=false
$(cd "$startDir";find . -type f \( -name "*.txt" ! -name "*.tmp.txt" \) > _temp.txt) # use a subshell so that the current shell's path doesn't change.
cat "$startDir/_tempt.txt" | while read filepath; do
	perFileProcessing "$filepath"
	result=$?
	if [ $result -gt 0 ]; then # check for a return code greater than 0, indicating an error
		hasError=true
		echo "error processing file: $filepath"
		#continue processing the remaining files
	fi
done

if [ -e "$startDir/_temp.txt" ]; then
	rm "$startDir/_temp.txt" # clean up the temporary file used to store find results
fi

if ($hasError); then
	exit 1;
else
	echo "finished processing files in $startDir"
	exit 0
fi
