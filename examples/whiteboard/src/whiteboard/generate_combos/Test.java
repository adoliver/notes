package generate_combos;

import java.util.ArrayList;
import java.util.List;

public class Test {
	  public Integer target;
	  public List<Integer> possible;
	  public List<List<Integer>> solutions;
	  
	  public Test(Integer target,
			  List<Integer> possible,
			  List<Integer> ...solutions) {
		  this.target = target;
		  this.possible = possible;
		  this.solutions = new ArrayList<List<Integer>>();
		  for (List<Integer> solution : solutions) {
			  this.solutions.add(solution);
		  }
		  
	  }
}