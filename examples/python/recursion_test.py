def run():
	solution = []
	getCombo(4, 0, 0, [], [], solution)
	print "Solution list"
	print solution
	
def getCombo(target, total, level, possible, test, solution):
	if total == target:
		print "{0}    found total: {1}".format(level, test)
		solution.append(test[:]) # Store a copy so midifications to "test" are not applied to stored solution
		test.pop()
		return
	if total >= target:
		test.pop()
		print "{0}    past total".format(level)
		return

	print "{0} add {2}+{1}".format(level, 1,total);
	test.append(1)
	getCombo(target, total+1, level+1, possible, test, solution)
#	if len(test) > 0:
#		test.pop()

	print "{0} add {2}++{1}".format(level, 2, total);
	test.append(2)
	getCombo(target, total+2, level+1, possible, test, solution)
#	if len(test) > 0:
#		test.pop()

	print "{0} add {2}+++{1}".format(level, 3, total);
	test.append(3)
	getCombo(target, total+3, level+1, possible, test, solution)
#	if len(test) > 0:
#		test.pop()
	
#	charSet = [1,2,3]
#	for i in charSet:
#		print "{0} try: {1}, {2}".format(level, i, total) 
#		getCombo(target, total+i, level+1)

	
	

run()
