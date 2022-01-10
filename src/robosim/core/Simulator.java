package robosim.core;

import java.awt.*;

import javax.swing.*;

public class Simulator {
	private SimMap map;
	private Robot bot;
	private double width, height;
	private boolean wasHit;
	private Histogram<String> stats;
	
	public Simulator(double width, double height) {
		map = new SimMap();
		this.width = width;
		this.height = height;
		bot = new Robot(width/2, height/2, 0);
		reset();
	}
	
	public void reset() {
		wasHit = false;
		stats = new Histogram<>();
	}
	
	public void add(SimObject obj) {
		if (obj instanceof Robot) {
			bot = (Robot)obj;
			reset();
		} else {
			map.add(obj);
		}
	}

	public void resetObjectsFrom(String mapString) {
		map = new SimMap(mapString);
	}
	
	public void drawOn(JComponent canvas) {
		this.width = canvas.getWidth();
		this.height = canvas.getHeight();
		Graphics gc = canvas.getGraphics();
		gc.setColor(Color.WHITE);
		gc.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());
		
		bot.render(gc);
		map.drawOn(gc, bot);
	}
	
	public boolean inBounds(SimObject obj) {
		return obj.getX() - obj.getRadius() >= 0 && obj.getX() + obj.getRadius() <= width &&
				obj.getY() - obj.getRadius() >= 0 && obj.getY() + obj.getRadius() <= height;
	}
	
	public void translate(Direction direction) {
		bot.translate(direction);
	}
	
	public void turn(Direction direction) {
		bot.turn(direction);
	}
	
	public double getTranslationalVelocity() {return bot.getTranslationalVelocity();}
	public double getAngularVelocity() {return bot.getAngularVelocity();}
	
	public void move() {
		wasHit = false;
		bot.update();
		if (isColliding()) {
			wasHit = true;
			bot.update(Direction.REV);
		}
		stats.bump(wasHit ? "Collisions" : getAngularVelocity() == 0 && getTranslationalVelocity() > 0 ? "Forward" : "Other");
	}
	
	public int getTotalMoves() {return stats.getTotalCounts();}
	
	public int getForwardMoves() {return stats.getCountFor("Forward");}
	
	public int getCollisions() {return stats.getCountFor("Collisions");}
	
	public boolean wasHit() {return wasHit;}
	
	public boolean isColliding() {
		return !inBounds(bot) || map.isColliding(bot);
	}
	
	public double findClosest() {
		return Math.min(findClosestObject(), findClosestEdge());
	}
	
	public double findClosestObject() {
		return map.closestDistanceTo(bot).orElse(Double.MAX_VALUE) - bot.getRadius();
	}
	
	public double findClosestEdge() {
		double x = oneD(bot.getX(), Math.cos(bot.getHeading()), width);
		double y = oneD(bot.getY(), Math.sin(bot.getHeading()), height);
		double closest = x < y ? x : y;
		return closest - bot.getRadius();
	}
	
	private double oneD(double where, double part, double dimSize) {
		return Math.abs((part >= 0 ? dimSize - where : where) / part);
	}

	public String getMapString() {
		return map.toString();
	}
}
