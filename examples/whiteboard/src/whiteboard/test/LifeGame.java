package test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class LifeGame {
	
	public static void runGame() {
		
		int[] states = new int[3];
		states[0] = 1;
		states[1] = 1;
		states[2] = 1;
		
		LifeGame game = new LifeGame();
		List<Integer> answer = game.cellCompete(states, 2);
		Collections.sort(answer);
		System.out.println("Answer;");
		System.out.println(Arrays.toString(answer.toArray()));
	}
	
	// METHOD SIGNATURE BEGINS, THIS METHOD IS REQUIRED
    public List<Integer> cellCompete(int[] states, int days)
    {
        System.out.println("Run my code");
    // WRITE YOUR CODE HERE
        int[] newStates = new int[0];
        for (int i=0; i < days; i++) {
        	System.out.println(String.format("day %d %s", i, Arrays.toString(states)));
            newStates = getStates(states);
            System.out.println(String.format("\t%s", Arrays.toString(newStates)));
            states = newStates;
        }
        List<Integer> output = new ArrayList<Integer>();
        for (int j=0; j < newStates.length; j++) {
            output.add(newStates[j]);
        }
        return output;
    }
  // METHOD SIGNATURE ENDS
  
  private int[] getStates(int[] states) {
      int[] newStates = new int[states.length];
      for(int i=0; i < states.length; i++) {
          int i_prev = i-1; // previous neighbor
          int i_next = i+1; // next neighbor
          int prev_active = 0; // default to neighbor being inactive
          int next_active = 0;
          if (i_prev >= 0) {
              prev_active = states[i_prev];
          }
          if (i_next < states.length) {
              next_active = states[i_next];
          }
          if (prev_active == next_active) { // neighboring cells are the same. inactivate this cell.
              newStates[i] = 0;
              System.out.println(String.format("\ti=%d ip:%d in:%d\n\t\tp:%d n:%d new:%d",i, i_prev, i_next, prev_active, next_active, 0));
          } else { // neighboring cells are different. activate the cell
              newStates[i] = 1;
              System.out.println(String.format("\ti=%d ip:%d in:%d\n\t\tp:%d n:%d new:%d",i, i_prev, i_next, prev_active, next_active, 1));
          }
      }
      return newStates;
  }
}
