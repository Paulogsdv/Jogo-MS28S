package org.evertree.lettres.test;

import java.awt.Font;

import javax.swing.UIManager;
import javax.swing.plaf.FontUIResource;

public class TestFonts {

	public static void main(String[] args) {
		FontUIResource f = new FontUIResource("Serif", Font.BOLD, 12);
		java.util.Enumeration<Object> keys = UIManager.getDefaults().keys();
		while (keys.hasMoreElements()) {
			Object key = keys.nextElement();
			Object value = UIManager.get(key);
			if (value instanceof javax.swing.plaf.FontUIResource){
				System.out.println(key);
				UIManager.put(key, f);
			}
		}
	}

}
