package robosim.core;

import java.awt.*;

public class SimObject {
	private double x, y, radius;
	
	public SimObject(double x, double y, double radius) {
		this.x = x;
		this.y = y;
		this.radius = radius;
	}

	public SimObject(String src) {
		src = src.substring(1, src.trim().length() - 1);
		String[] top = src.split(";");
		radius = Double.parseDouble(top[1]);
		String coords = top[0].substring(1, top[0].length() - 1);
		String[] xy = coords.split(",");
		x = Double.parseDouble(xy[0]);
		y = Double.parseDouble(xy[1]);
	}

	public String toString() {
		return String.format("{(%f,%f);%f}", x, y, radius);
	}
	
	public double getRadius() {return radius;}
	
	public double getX() {return x;}
	public double getY() {return y;}
	
	public Color getColor() {return Color.GREEN;}
	
	public void render(Graphics gc, Color override) {
		gc.setColor(override);
		gc.fillOval((int)(getX() - getRadius()), (int)(getY() - getRadius()), (int)getRadius() * 2, (int)getRadius() * 2);
	}
	
	public void render(Graphics gc) {
		render(gc, getColor());
	}
	
	public double distanceTo(SimObject other) {
		return Math.sqrt(Math.pow(this.x - other.x, 2) + Math.pow(this.y - other.y, 2));
	}
	
	public double angularDistance(SimObject other) {
		return Math.atan2(other.y - this.y, other.x - this.x);
	}
	
	public boolean isHitting(SimObject other) {
		return this.radius + other.radius >= this.distanceTo(other);
	}
	
	public void moveBy(double dx, double dy) {
		x += dx;
		y += dy;
	}

	@Override
	public boolean equals(Object other) {
		if (other instanceof SimObject that) {
			return this.x == that.x && this.y == that.y && this.radius == that.radius;
		}
		return false;
	}
}
