package whiteboard.apple_stock;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Stack;

public class AppleStock {
	public static void run(List<Integer> stockPrices) {
		 System.out.println(String.format("[10,9,8,7,6] : ",  Arrays.asList(new Integer[]{10,9,8,7,6})));
		 System.out.println(String.format("[1,3,5,7,9] : ",  Arrays.asList(new Integer[]{10,9,8,7,6})));
	}
	
	public Integer getStrategy(List<Integer> stockPrices) throws IllegalAccessException {
		if (stockPrices == null || stockPrices.size() < 2) {
			throw new IllegalAccessException("list of stock prices must be have at least two elements.");
		}
		Option bestValues = new Option(
				0,
				stockPrices.get(0),
				1,
				stockPrices.get(1),
				stockPrices.get(1) - stockPrices.get(0));
		
		// Loop through remaining prices to find a better one
		for (int n=2; n < stockPrices.size(); n++) {
			Integer currentVal = stockPrices.get(n);
			Integer profit = currentVal - bestValues.minVal;
			if (bestValues.profit < profit) {
				bestValues.profit = profit;
			}
			if (currentVal < bestValues.minVal) {
				bestValues.minVal = currentVal;
			}
		}
		
		return bestValues.profit;
	}
	

	public class Option {
		public Integer nMin;
		public Integer minVal;
		public Integer nMax;
		public Integer maxVal;
		public Integer profit;
		
		public Option(Integer nMin, Integer minVal, Integer nMax, Integer maxVal, Integer profit) {
			super();
			this.nMin = nMin;
			this.minVal = minVal;
			this.nMax = nMax;
			this.maxVal = maxVal;
			this.profit = profit;
		}
	}

	public class Results {
		public Integer nBuy;
		public Integer nSell;
		public Integer profit;
		
	}
}
