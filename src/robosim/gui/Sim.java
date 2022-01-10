package robosim.gui;

import robosim.ai.Controller;
import robosim.core.SimObjMaker;
import robosim.core.Simulator;
import search.core.AIReflector;

import javax.swing.*;

public class Sim extends JFrame {
    public static final double RADIUS = 10.0;
    public static final long PERIOD = 60;
    public static final long INTERVAL = 1000000000 / PERIOD;

    JComboBox<SimObjMaker> objectToPlace;
    JComboBox<String> ai;
    JButton start, stop, singleStep, resume, reset;
    JTextField total, forward, collisions;
    JTextArea messages;

    Simulator map;

    AIReflector<Controller> ais;

    Timer timer;

    JMenuItem open, save, close;

    public Sim() {

    }
}
