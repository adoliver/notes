package generate_combos;

import java.io.*;
import java.util.*;

/*
 Given a set of candidate numbers (C) (without duplicates) and a target number (T), find all unique combinations in C where the candidate numbers sums to T.
The same repeated number may be chosen from C unlimited number of times.
Note:
All numbers (including target) will be positive integers.
The solution set must not contain duplicate combinations.

For example, given candidate set [2, 3, 6, 7] and target 7, A solution set is: 
[
  [7],
  [2, 2, 3]
]

 */

public class GenerateCombos {
  public static void test() { // drive test cases
    System.out.println("\nRun Tests:");
    List<Test> allTests = loadTests();
    int correctTests = 0;
    for (Test test : allTests) {
        Map<String, List<Integer>> solution = new HashMap<String, List<Integer>>();
        
        System.out.println(String.format("Test: t%s p%s", Integer.toString(test.target), Arrays.toString(test.possible.toArray())));
        
        getUniqueRepeats(test.target, test.possible, 0, new ArrayList<Integer>(), solution, 0);

        int correctSubLists = 0;
        for(List<Integer> list : test.solutions) {
        	Collections.sort(list); // order the same as getUniqueRepeats
        	String key = Arrays.toString(list.toArray());
        	if (list.size() == 0 && solution.size() == 0) { // there should not be any solutions
        		// do nothing
        	} else if (solution.containsKey(key)) { // found key OR
        		String stored = Arrays.toString(
						solution.get(key).toArray());
        		if (key.equals(stored)) { // Compare strings of sorted arrays
        			correctSubLists++;
        		} else {
            		System.out.println(String.format("!!  expected %s found %s", key, stored));
        		}
        	} else {
        		System.out.println(String.format("!!  failed to find %s", key));
        	}
        	solution.remove(key); // remove successful test
        }
        
        if (correctSubLists == test.solutions.size() &&
        		solution.size() == 0) {
        	correctTests++;
        } else if (solution.size() > 0) {
        	System.out.println(String.format("    found %d extra solutions in answer", solution.size()));
        }
        
        System.out.println(String.format("    %d correct of %d", correctSubLists, test.solutions.size()));
        System.out.println("---");
    }
    System.out.println(String.format("%d correct Tests of %d", correctTests, allTests.size()));
    
    
  }
  
  public static void getUniqueRepeats(Integer target,
                                  List<Integer> possible,
                                  Integer total,
                                  List<Integer> attempt,
                                  Map<String, List<Integer>> solution,
                                  Integer level) {
    
    System.out.println(String.format("    at %d test %d == %d", level, total, target));
    if ( total == target ) {
//    	System.out.println(String.format("    at %d found %d", level, total));
    	addToSolution(solution, attempt);
    } else if (total < target) { 
        // Target was not found continue attempting more numbers
        for (int i=0; i < possible.size(); i++) {
              attempt.add(possible.get(i));
//              System.out.println(String.format("at %d call(%d + %d = %d): %s", 
//            		  level, 
//            		  total,
//            		  possible.get(i),
//            		  total + possible.get(i), 
//            		  Arrays.toString(attempt.toArray())));
              getUniqueRepeats(target,
                        possible,
                         total + possible.get(i), // do not modify 'total' value on this level, so base is the same for each call in loop.
                        attempt,
                        solution,
                        level + 1);
          }
    }
    else {
//    	System.out.println(String.format("    at %d failed with %d", level, total));
    }
    
    if (attempt.size() > 0) {
        attempt.remove(attempt.size()-1); // this level is complete. pop the integer as we go up a level.
    }
      
  }

  public static void addToSolution(Map<String, List<Integer>> solution, List<Integer> attemptOriginal) {
	  	if (attemptOriginal.size() < 1) {
	  		return; // invalid attempt. no values, so do not store as a solution
	  	}
  		List<Integer> attempt = new ArrayList<Integer>(attemptOriginal); // make a copy so that modifications to attempt don't alter the solution lists
	  	Collections.sort(attempt);
	  	String key = Arrays.toString(attempt.toArray());
	  	System.out.println(String.format("Store %s", key));
	  	solution.put(key, attempt);
  }
  
  public static List<Test> loadTests() {
	  List<Test> test = new ArrayList<Test>();
	  

	  test.add(new Test(5, Arrays.asList(8))); // no solutions
	  test.add(new Test(0, Arrays.asList(1))); // Edge case. target of zero matching starting value of total caused storage of empty list in solution.
	  test.add(new Test(2, Arrays.asList(1,2), // minimal paths makes manually visualizing space easier
			  Arrays.asList(1,1), 
			  Arrays.asList(2)
			  ));
	  test.add(new Test(4, Arrays.asList(1,2,3), 
			  Arrays.asList(1,1,1,1), 
			  Arrays.asList(1,1,2),
			  Arrays.asList(1,3),
			  Arrays.asList(2,2)
			  ));
	  test.add(new Test(7, Arrays.asList(2,3,6,7), 
			  Arrays.asList(7), 
			  Arrays.asList(2,2,3)
			  ));
	  test.add(new Test(4, Arrays.asList(3,2,1), // try reversed order
			  Arrays.asList(1,1,1,1), 
			  Arrays.asList(1,1,2),
			  Arrays.asList(1,3),
			  Arrays.asList(2,2)
			  ));
	  test.add(new Test(7, Arrays.asList(3,2,7,6), // messy order
			  Arrays.asList(7), 
			  Arrays.asList(2,2,3)
			  ));
	  
	  return test;
  }
  
  public static void getRepeats(Integer target,
                                  List<Integer> possible,
                                  Integer total,
                                  List<Integer> attempt,
                                  List<List<Integer>> solution,
                                  Integer level) {
    
    System.out.println(String.format("    at %d test %d == %d", level, total, target));
    if ( total == target ) {
    	System.out.println(String.format("    at %d found %d", level, total));
    	List<Integer> copy = new ArrayList<Integer>(attempt); // make a copy so that modifications to attempt don't alter the solution lists
    	solution.add(copy);
    } else if (total < target) {
        // Target was not found continue attempting more numbers
        for (int i=0; i < possible.size(); i++) {
              attempt.add(possible.get(i));
              System.out.println(String.format("at %d call(%d + %d = %d): %s", 
            		  level, 
            		  total,
            		  possible.get(i),
            		  total + possible.get(i), 
            		  Arrays.toString(attempt.toArray())));
              getRepeats(target,
                        possible,
                         total + possible.get(i), // do not modify 'total' value on this level, so base is the same for each call in loop.
                        attempt,
                        solution,
                        level + 1);
          }
    }
    else {
    	System.out.println(String.format("    at %d failed with %d", level, total));
    }
    
    if (attempt.size() > 0) {
        attempt.remove(attempt.size()-1); // this level is complete. pop the integer as we go up a level.
    }
      
  }

}
