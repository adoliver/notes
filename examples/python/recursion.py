def run():
	solution = []
	getCombo(4, 0, 0, [1,2,3], [], solution)
	print "Solution list"
	print solution
	
def getCombo(target, total, level, possible, test, solution):
	if total == target:
		solution.append(test[:]) # Store a copy so midifications to "test" are not applied to stored solution
		test.pop()
		return

	if total >= target:
		test.pop()
		return

	for i in possible:
		test.append(i)
		getCombo(target, total+i, level+1, possible, test, solution)

run()
