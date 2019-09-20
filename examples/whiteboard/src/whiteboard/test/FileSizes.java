package test;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Stack;
import java.util.stream.Collectors;

public class FileSizes {
	
	// METHOD SIGNATURE BEGINS, THIS METHOD IS REQUIRED
    int minimumTime(int numOfSubFiles, List<Integer> files)
    {
        // WRITE YOUR CODE HERE
        if (files.size() < 1) {
            return 0;
        } else if (files.size() == 1) {
            return files.get(0);
        } else if (files.size() == 2) {
            return files.get(0) + files.get(1);
        } else {
            return minimumTime(
                files.size()/2,
                files.subList(0,files.size()/2)) +
            minimumTime(
                files.size() - files.size() / 2,
                files.subList(files.size()/2, files.size()));
        }
    }
    
    int minumumTimeLoop(int numOfSubFiles, Stack<Integer> files) {
    	int total = files.pop() + files.pop();
    	files.push(total);
    	while (files.size() >= 2) {
    		Integer top = files.pop(); // running total
    		Integer next = files.pop();
    		total = top + top + next; // new running total
    		System.out.println(String.format("%d + %d + %d = %d", top, top, next, total));
    		files.push(total);
    	}
    	return total;
    }
    
    public static void runFileSizes() {
    	FileSizes solution = new FileSizes();
		for(Map<String, Object> test : generateTests()) {
			List<Integer> list = (List<Integer>)test.get("list");
			Stack<Integer> stack = new Stack<Integer>();
			stack.addAll(list);
			int[] listInts = list.stream().mapToInt(i->i).toArray();
			System.out.println(String.format("try test %s", 
					list.stream().map(Object::toString).collect(Collectors.joining(", "))));
			System.out.println(String.format("\t%d",
					solution.minumumTimeLoop(list.size(), stack)));
		}
	}
    

	
	public static List<Map<String,Object>> generateTests() {
		List<Map<String,Object>> testList = new ArrayList<Map<String,Object>>();
		testList.add(
		new HashMap<String,Object>() {
			{
			put ("list", new ArrayList<Integer>() {{
				add(20);
				add(8);
				add(4);
				add(2);
			}});
			}
		});
//		testList.add(
//				new HashMap<String, Object>(){
//					{
//						put("list", new ArrayList<Integer>() {{
//							add(1);
//							add(2);
//							add(3);
//						}});
//					}
//				});
//		testList.add(
//				new HashMap<String, Object>(){
//					{
//						put("list", new ArrayList<Integer>() {{
//							add(1);
//							add(2);
//						}});
//					}
//				});
//		testList.add(
//				new HashMap<String, Object>(){
//					{
//						put("list", new ArrayList<Integer>() {{
//							add(5);
//						}});
//					}
//				});
//		testList.add(
//				new HashMap<String, Object>(){
//					{
//						put("list", new ArrayList<Integer>() {{
//							add(3);
//							add(0);
//						}});
//					}
//				});
//		testList.add(
//				new HashMap<String, Object>(){
//					{
//						put("list", new ArrayList<Integer>() {{
//							add(5);
//							add(10);
//							add(20);
//							add(40);
//						}});
//					}
//				});
//		testList.add(
//				new HashMap<String, Object>(){
//					{
//						put("count", Integer.valueOf(5));
//						put("list", new ArrayList<Integer>() {{
//						}});
//					}
//				});
		return testList;
	}
}
