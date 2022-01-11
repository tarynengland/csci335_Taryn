package robosim.gui;

import robosim.core.Direction;
import robosim.core.SimObject;
import robosim.core.Simulator;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyAdapter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.util.Optional;
import java.util.stream.Stream;
import robosim.core.Action;

public class SimPanel extends JPanel {
    Simulator sim;

    public SimPanel() {
        super();
        sim = new Simulator(getWidth(), getHeight());
        addKeyListener(new KeyHandler());
    }

    private class KeyHandler extends KeyAdapter {
        @Override
        public void keyPressed(KeyEvent e) {
            key2action(e).ifPresent(action -> {
                action.applyTo(sim);
                sim.move();
            });
            repaint();
        }
    }

    Optional<Action> key2action(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_LEFT || e.getKeyCode() == KeyEvent.VK_A) {
            return Optional.of(Action.LEFT);
        } else if (e.getKeyCode() == KeyEvent.VK_RIGHT || e.getKeyCode() == KeyEvent.VK_D) {
            return Optional.of(Action.RIGHT);
        } else if (e.getKeyCode() == KeyEvent.VK_UP || e.getKeyCode() == KeyEvent.VK_W) {
            return Optional.of(Action.FORWARD);
        } else if (e.getKeyCode() == KeyEvent.VK_DOWN || e.getKeyCode() == KeyEvent.VK_S) {
            return Optional.of(Action.BACKWARD);
        } else {
            return Optional.empty();
        }
    }

    Simulator getSim() {return sim;}

    protected void paintComponent(Graphics gc) {
        super.paintComponent(gc);
        sim.resize(getWidth(), getHeight());
        gc.setColor(Color.WHITE);
        gc.fillRect(0, 0, getWidth(), getHeight());
        sim.drawOn(gc);
        requestFocus();
    }

    public void add(SimObject obj) {
        sim.add(obj);
        repaint();
    }

    public void reset() {
        sim.reset();
    }

    public void openFrom(File openee) throws IOException {
        Stream<String> stream = Files.lines(openee.toPath());
        StringBuilder sb = new StringBuilder();
        stream.forEach(s -> sb.append(s).append("\n"));
        sim.resetObjectsFrom(sb.toString());
        repaint();
    }

    public void saveTo(File savee) throws FileNotFoundException {
        PrintWriter out = new PrintWriter(savee);
        out.println(sim.getMapString());
        out.close();
    }
}
