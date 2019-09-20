package whiteboard;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import generate_combos.GenerateCombos;
import test.FileSizes;
import test.GCD;
import test.LifeGame;

public class Whiteboard {
	
	public static void main(String[] args) {
		System.out.println("Running...");
		
		// List to String
		// list.stream().map(Object::toString).collect(Collectors.joining(", "));
		
		// Array to String
		// Arrays.stream(arr).boxed().collect(Collectors.toList());
		
		// Array to List
		// Arrays.stream(arr).boxed().collect(Collectors.toList());
		
		// List to Array
		// 
		
		FileSizes.runFileSizes();
	}
	
	public static void runGCD() {
		GCD solution = new GCD();
		for(Map<String, Object> test : generateTests()) {
			List<Integer> list = (List<Integer>)test.get("list");
			int[] listInts = list.stream().mapToInt(i->i).toArray();
			System.out.println(String.format("try test %s\n\t%d", 
					list.stream().map(Object::toString).collect(Collectors.joining(", ")),
					solution.generalizedGCD_recur(list.size(), listInts)));
		}
	}
	
	public static List<Map<String,Object>> generateTests() {
		List<Map<String,Object>> testList = new ArrayList<Map<String,Object>>();
		testList.add(
		new HashMap<String,Object>() {
			{
			put ("count", Integer.valueOf(2) );
			put ("list", new ArrayList<Integer>() {{
				add(1);
				add(2);
				add(3);
				add(5);
			}});
			}
		});
		testList.add(
				new HashMap<String, Object>(){
					{
						put("count", Integer.valueOf(5));
						put("list", new ArrayList<Integer>() {{
							add(1);
							add(2);
							add(3);
						}});
					}
				});
		testList.add(
				new HashMap<String, Object>(){
					{
						put("count", Integer.valueOf(5));
						put("list", new ArrayList<Integer>() {{
							add(1);
							add(2);
						}});
					}
				});
		testList.add(
				new HashMap<String, Object>(){
					{
						put("count", Integer.valueOf(5));
						put("list", new ArrayList<Integer>() {{
							add(5);
						}});
					}
				});
		testList.add(
				new HashMap<String, Object>(){
					{
						put("count", Integer.valueOf(5));
						put("list", new ArrayList<Integer>() {{
							add(3);
							add(0);
						}});
					}
				});
		testList.add(
				new HashMap<String, Object>(){
					{
						put("count", Integer.valueOf(5));
						put("list", new ArrayList<Integer>() {{
							add(5);
							add(10);
							add(20);
							add(40);
						}});
					}
				});
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
