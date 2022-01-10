package robosim.core;

import java.awt.*;
import java.util.ArrayList;
import java.util.OptionalDouble;

public class SimMap {
    private ArrayList<SimObject> objects = new ArrayList<>();

    public SimMap() {}

    public SimMap(String src) {
        String[] objs = src.split(":");
        for (int i = 1; i < objs.length; i++) {
            objects.add(new SimObject(objs[i]));
        }
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("SimMap");
        for (SimObject so: objects) {
            sb.append(':');
            sb.append(so.toString());
        }
        return sb.toString();
    }

    public void add(SimObject obj) {
        objects.add(obj);
    }

    public void drawOn(Graphics gc, Robot bot) {
        for (SimObject obj: objects) {
            obj.render(gc, bot.withinSonar(obj) ? Color.YELLOW : obj.getColor());
        }
    }

    public boolean isColliding(Robot bot) {
        return objects.stream().anyMatch(bot::isHitting);
    }

    public OptionalDouble closestDistanceTo(Robot bot) {
        return objects.stream()
                .filter(bot::withinSonar)
                .mapToDouble(bot::distanceTo)
                .min();
    }
}
