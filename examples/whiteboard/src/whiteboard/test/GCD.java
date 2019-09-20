package test;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class GCD {
	public int generalizedGCD_recur(int num, int[] arr) {
		List<Integer> list = Arrays.stream(arr).boxed().collect(Collectors.toList());
		//Collections.sort(list);
		return runGCD(list);
	}
	
	public int runGCD(List<Integer> list) {
		if (list.size() == 2) {
			return normalGCD(list.get(0), list.get(1));
		} else if (list.size() == 1) {
			return list.get(0);
		} else if (list.size() < 1){
			throw new IllegalArgumentException("list can not be size zero");
		} else {
			Integer splitIndex = list.size()/2;
			if (splitIndex > 0 && splitIndex < list.size()-1) {
				return normalGCD(
						runGCD(list.subList(0, splitIndex)),
						runGCD(list.subList(splitIndex, list.size()-1)));	
			} else {
				throw new IllegalArgumentException("List should not have been smaller than 3");
			}
		}
	}
	
	public int normalGCD(Integer a, Integer b) {
		if (a == 0)
			return b;
		if (b == 0)
			return a;
		if ( a == b ) {
			return a;
		}
		
		return normalGCD(b, a %b);
	}
}
