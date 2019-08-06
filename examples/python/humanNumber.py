def run(tests):
	correct = 0;
	for k in tests.keys():
		correct += test(k, tests[k])

	print "{0} of {1} tests passed".format(correct,len(tests))

def test(number, humanText):
	result = humanNumber(number)  
	if result == humanText:
		print "    {0}".format(result)
		return 1;
	else:
		print "!!  expected {0} found {1}".format(humanText, result)
		return 0;

def humanNumber(number):
	if number in ONES:
		return ONES[number]
	if number in TEENS:
		return TEENS[number]
	
	# number is some 10^n power
	numberLength = len("{0}".format(number))
	tensPlace = pow(10, numberLength)
	if tensPlace

	return ""

SEPERATOR = {
	10 : "-",
	100 : "and",
	1000 : ","
}

ONES = {
	1 : "One",
	2 : "Two",
	3 : "Three",
	4 : "Four",
	5 : "Five",
	6 : "Six",
	7 : "Seven",
	8 : "Eight",
	9 : "Nine"
}

TEENS = {
	10 : "Ten",
	11 : "Eleven",
	12 : "Twelve",
	13 : "Thirteen",
	14 : "Fourteen",
	15 : "Fifteen",
	16 : "Sixteen",
	17 : "Seventeen",
	18 : "Eighteen",
	19 : "Nineteen"
}

TENS = {
	20 : "Twenty",
	30 : "Thirty",
	40 : "Fourty",
	50 : "Fifty",
	60 : "Sixty",
	70 : "Seventy",
	80 : "Eighty",
	90 : "Ninety"
}

DESCRPIPTOR = {
	100 : "hundred",
	1000 : "thousand",
	1000000 : "million"
}


tests = {
	1 : "One",
	2 : "Two",
	3 : "Three",
	4 : "Four",
	5 : "Five",
	6 : "Six",
	7 : "Seven",
	8 : "Eight",
	9 : "Nine",
	100 : "One hundred"
}

run(tests)
