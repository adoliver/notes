#!/bin/bash

usage() {
	echo "jwtd.sh [-h|--help][--inc-signature][--header|--payload|--signature] [JWT_JSON_PATH]"
	echo "    retrieve and decode JWT to output the json objects in a human"
	echo "    readable format. For security the signature is omitted by default."
	echo "    Options:"
	echo "    -h | --help		display this message"
	echo "    --header		output only the JWT header"
	echo "    --payload		output only the JWT payload"
	echo "    --signature		output only the JWT signature"
	echo "    --inc-signature	include the signature [off by default]"
}

incSignature=0
headerOnly=0
payloadOnly=0
signatureOnly=0
jqCommand='split(".") | .[0],.[1] | @base64d | fromjson'

while [ "$1" != "" ]; do
	case $1 in
		-h | --help )		usage # call the help function
					exit 0 # exit with success
					;;
		--header )		headerOnly=1
					jqCommand='split(".") | .[0] | @base64d | fromjson'
					;;
		--payload )		payloadOnly=1
					jqCommand='split(".") | .[1] | @base64d'
					;;
		--signature )		signatureOnly=1
					jqCommand='split(".") | .[2]'
					echo "set --signature"
					;;
		--inc-signature )	incSignature=1
					jqSignature='split(".") | .[2]'
					echo "set --inc-signature"
					;;
		* )			break # after the first non-matching option assume the remainder are the arguments
					;;
	esac
	shift
done

if ! [[ -x $(command -v jq) ]]; then
	echo "Could not find dependency: jq command"
	exit 1 # exit with failure
fi

onlyPart=$headerOnly + $payloadOnly + $signatureOnly
if [ "$#" -gt 0 ]; then
	if [[ $signatureOnly -eq 1 ]]; then
		cat $1 | jq -Rr "$jqCommand"
	elif [[ $onlyPart -eq 0 ]] && [[ $incSignature -eq 1 ]]; then
		cat $1 | jq -R "$jqCommand"
		cat $1 | jq -Rr "$jqSignature"
	else
		cat $1 | jq -R "$jqCommand"
	fi
else
	if [[ $signatureOnly -eq 1 ]]; then
		jq -Rr "$jqCommand"
	elif [[ $onlyPart -eq 0 ]] && [[ $incSignature -eq 1 ]]; then
		tee >(jq -R "$jqCommand") >(jq -Rr "$jqSignature")
	else
		jq -R "$jqCommand"
	fi
fi
