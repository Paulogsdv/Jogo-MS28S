package org.evertree.lettres.resource;

import java.awt.Color;

public class MessageLine {

    public static final MessageLine[] MESSAGE_GAME_OVER = {
	    new MessageLine("08,04,#FF0000 Game over!"),
	    new MessageLine("09,00,#FFFFFF Press any key to restart . . .") };
    public static final MessageLine[] MESSAGE_PAUSED = {
	    new MessageLine("08,05,#0000FF Paused"),
	    new MessageLine("09,00,#FFFFFF Press any key to resume . . .") };
    public static final MessageLine[] MESSAGE_INTRO = {
	    new MessageLine("01,02,#0000FF Welcome to Lettres!"),
	    new MessageLine("02,03,#FFFFFF music by DJ DAS"),
	    new MessageLine("04,04,#FFFF00 Instructions"),
	    new MessageLine("05,01,#FFFF00 LEFT"),
	    new MessageLine("05,05,#FFFFFF move to left"),
	    new MessageLine("06,01,#FFFF00 RIGHT"),
	    new MessageLine("06,05,#FFFFFF move to right"),
	    new MessageLine("07,01,#FFFF00 DOWN"),
	    new MessageLine("07,05,#FFFFFF to speed up drop"),
	    new MessageLine("08,01,#FFFF00 UP"),
	    new MessageLine("08,05,#FFFFFF to rotate"),
	    new MessageLine("10,01,#FFFFFF Any other key to pause"),
	    new MessageLine("11,01,#FFFFFF and resume."),
	    new MessageLine("13,01,#FF4444 HINT:"),
	    new MessageLine("13,04,#FFFFFF  form the word LIST"),
	    new MessageLine("15,04,#FFFFFF Visit us at"),
	    new MessageLine("16,01,#00FF00 http://www.evertree.org"),
	    new MessageLine("18,01,#FFFFFF Press any key to start . . .") };

    private Color color;
    private String message;
    private Integer x;
    private Integer y;

    public MessageLine(String line) {
	super();
	decode(line);
    }

    private void decode(String line) {
	this.color = Color.decode(line.substring(6, 13));
	this.x = Integer.valueOf(line.substring(3, 5));
	this.y = Integer.valueOf(line.substring(0, 2));
	this.message = line.substring(14);
    }

    public String getMessage() {
	return message;
    }

    public Color getColor() {
	return color;
    }
    
    public Integer getX() {
	return x;
    }
    
    public Integer getY() {
	return y;
    }
}
