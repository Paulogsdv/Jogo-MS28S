package org.evertree.lettres.resource;

import java.awt.Color;

public class BlockColor {

    private static final long serialVersionUID = 1L;

    private Color color;
    private double brightness = 0.0;
    private int deltaWhite;
    private int deltaBlack;

    public BlockColor(Color color) {
	this.color = color;
	calculateDeltas();
    }

    public Color getColor() {
	Color realColor = null;
	if (brightness == 0.0) {
	    realColor = color;
	} else if (brightness > 0.0) {
	    int factor = (int) (deltaWhite * brightness);
	    int red = Math.min(255, color.getRed() + factor);
	    int green = Math.min(255, color.getGreen() + factor);
	    int blue = Math.min(255, color.getBlue() + factor);
	    realColor = new Color(red, green, blue);
	} else {
	    int factor = (int) (deltaBlack * brightness);
	    int red = Math.max(0, color.getRed() + factor);
	    int green = Math.max(0, color.getGreen() + factor);
	    int blue = Math.max(0, color.getBlue() + factor);
	    realColor = new Color(red, green, blue);
	}
	return realColor;
    }

    public void setBrightness(double brightness) {
	this.brightness = brightness;
    }

    private void calculateDeltas() {
	int lighterColor = Math.max(color.getBlue(), Math.max(color.getRed(),
		color.getGreen()));
	this.deltaBlack = lighterColor;
	int darkerColor = Math.min(color.getBlue(), Math.min(color.getRed(),
		color.getGreen()));
	this.deltaWhite = 255 - darkerColor;
    }

}
