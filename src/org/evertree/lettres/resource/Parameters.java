package org.evertree.lettres.resource;

import java.util.HashMap;
import java.util.Map;

public class Parameters {

    public static Map<String, Object> all;
    public static String LETTRES_VERSION;
    public static Long VANISH_ANIMATION_DURATION;
    public static Long LIGHT_ANIMATION_DURATION;
    public static Long BOMB_ANIMATION_DURATION;
    public static Integer FPS;
    public static Integer LINES_PER_LEVEL;
    public static Long MOVE_VERTICAL_INTERVAL;
    public static Long MOVE_HORIZONTAL_INTERVAL;
    public static Long DROP_INTERVAL_DECREMENT;
    public static Long ONE_LINE_FILLED_SCORE;
    public static Long TWO_LINE_FILLED_SCORE;
    public static Long THREE_LINE_FILLED_SCORE;
    public static Long FOUR_LINE_FILLED_SCORE;
    public static Long INITIAL_DROP_INTERVAL;

    static {
	all = new HashMap<String, Object>();

	all.put("game.version", "0.9");
	all.put("vanish.animation.duration", 250L);
	all.put("light.animation.duration", 1250L);
	all.put("bomb.animation.duration", 500L);
	all.put("fps", 100);
	all.put("lines.per.level", 15);
	all.put("move.vertical.interval", 50L);
	all.put("move.horizontal.interval", 150L);
	all.put("drop.interval.decrement", 50L);
	all.put("1.line.filled.score", 150L);
	all.put("2.line.filled.score", 500L);
	all.put("3.line.filled.score", 1500L);
	all.put("4.line.filled.score", 3500L);
	all.put("initial.drop.interval", 600L);

	update();
    }

    public static void update() {
	LETTRES_VERSION = (String) all.get("game.version");
	VANISH_ANIMATION_DURATION = (Long) all.get("vanish.animation.duration");
	LIGHT_ANIMATION_DURATION = (Long) all.get("light.animation.duration");
	BOMB_ANIMATION_DURATION = (Long) all.get("bomb.animation.duration");
	FPS = (Integer) all.get("fps");
	LINES_PER_LEVEL = (Integer) all.get("lines.per.level");
	MOVE_VERTICAL_INTERVAL = (Long) all.get("move.vertical.interval");
	MOVE_HORIZONTAL_INTERVAL = (Long) all.get("move.horizontal.interval");
	DROP_INTERVAL_DECREMENT = (Long) all.get("drop.interval.decrement");
	ONE_LINE_FILLED_SCORE = (Long) all.get("1.line.filled.score");
	TWO_LINE_FILLED_SCORE = (Long) all.get("2.line.filled.score");
	THREE_LINE_FILLED_SCORE = (Long) all.get("3.line.filled.score");
	FOUR_LINE_FILLED_SCORE = (Long) all.get("4.line.filled.score");
	INITIAL_DROP_INTERVAL = (Long) all.get("initial.drop.interval");
    }

}
