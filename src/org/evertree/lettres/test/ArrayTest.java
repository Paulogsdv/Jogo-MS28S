package org.evertree.lettres.test;

public class ArrayTest {
    
    public static void main(String[] args) {
	String [][] array = new String[2][2];
	fillArray(array);
	System.out.println(array[1][1]);
    }

    private static void fillArray(String[][] array) {
	array[0][0] = "0...0";
	array[0][1] = "0...1";
	array[1][0] = "1...0";
	array[1][1] = "1...1";
	
    }

}
