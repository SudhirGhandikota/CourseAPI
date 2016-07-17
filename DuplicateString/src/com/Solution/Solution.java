package com.Solution;

import java.util.HashMap;
import java.util.Scanner;

public class Solution {

	public static void main(String[] args) {
		
		Scanner sc = new Scanner(System.in);
		System.out.println("Enter String:");
		String input = sc.nextLine();
		char chars[] = input.toCharArray();
		String duplicate = getDuplicates(chars);
		System.out.println("Duplicate string is:"+duplicate);
	}

	/*
	 * This method iterates over all the characters in a string and adds it into a hashmap.
	 * Duplicate characters are appended to a string and it is returned after the iteration
	 * Performance: O(n) space complexity where n is the length or number of characters in a string
	 */
	private static String getDuplicates(char[] chars) {
		HashMap<Character,Integer> map = new HashMap();
		String temp = "";
		for(int i=0;i<chars.length;i++)
		{
			char c = chars[i];
			if(map.containsKey(c))
				temp = temp+c;
			else
				map.put(c, 1);
		}
		return temp;
	}

}
