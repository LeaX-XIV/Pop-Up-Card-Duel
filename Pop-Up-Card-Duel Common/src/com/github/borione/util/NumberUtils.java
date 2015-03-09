package com.github.borione.util;

import java.util.List;

public class NumberUtils {
	
	public static int max(int[] numbers) {
		int max = numbers[0];
		
		for (int i : numbers) {
			if(i > max)
				max = i;
		}
		
		return max;
	}
	
	public static int max(List<Integer> numbers) {
		Integer[] nums = new Integer[numbers.size()];
		nums = numbers.toArray(new Integer[numbers.size()]);
		int[] ns = new int[nums.length];
		
		for (int i = 0; i < nums.length; i++) {
			ns[i] = nums[i];
		}
		
		return max(ns);
	}
	
	public static int min(int[] numbers) {
		int min = numbers[0];
		
		for (int i : numbers) {
			if(i < min)
				min = i;
		}
		
		return min;
	}
	
	public static int min(List<Integer> numbers) {
		Integer[] nums = new Integer[numbers.size()];
		nums = numbers.toArray(new Integer[numbers.size()]);
		int[] ns = new int[nums.length];
		
		for (int i = 0; i < nums.length; i++) {
			ns[i] = nums[i];
		}
		
		return min(ns);
	}

}
