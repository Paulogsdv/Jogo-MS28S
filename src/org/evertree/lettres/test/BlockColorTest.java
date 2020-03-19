package org.evertree.lettres.test;

import org.evertree.lettres.resource.BlockColor;
import org.evertree.lettres.resource.Colors;

public class BlockColorTest {
    
    public static void main(String[] args) {
	BlockColor color = new BlockColor(Colors.DARK_BLUE);
	for (double i = 0; i <= 1; i += 0.05) {
	    color.setBrightness(i);
	    System.out.println(i);
	    System.out.println(color.getColor());
	}
    }

}
