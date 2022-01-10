package robosim.gui;

import handwriting.gui.DrawingEditor;
import robosim.ai.Controller;
import robosim.core.SimObjMaker;
import robosim.core.Simulator;
import search.core.AIReflector;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.util.stream.Stream;

public class Sim extends JFrame {
    public static final double RADIUS = 10.0;
    public static final long PERIOD = 60;
    public static final long INTERVAL = 1000000000 / PERIOD;

    JComboBox<SimObjMaker> objectToPlace;
    JComboBox<String> ai;
    JButton start, stop, singleStep, resume, reset;
    JTextField total, forward, collisions;
    JTextArea messages;
    SimPanel sim;

    Simulator map;

    AIReflector<Controller> ais;

    JMenuItem open, save, close;

    public Sim() {
        setSize(700, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        ais = new AIReflector<>(SimObjMaker.class, "robosim.ai");

        getContentPane().setLayout(new BorderLayout());

        sim = new SimPanel();
        getContentPane().add(sim, BorderLayout.CENTER);

        JMenuBar bar = new JMenuBar();
        setJMenuBar(bar);
        addFileMenu(bar);
    }

    private void addFileMenu(JMenuBar bar) {
        JMenu fileMenu = new JMenu("File");
        bar.add(fileMenu);

        open = new JMenuItem("Open");
        open.addActionListener(e -> {
            JFileChooser chooser = new JFileChooser();
            if (chooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
                try {
                    File openee = chooser.getSelectedFile();
                    Stream<String> stream = Files.lines(openee.toPath());
                    StringBuilder sb = new StringBuilder();
                    stream.forEach(s -> sb.append(s).append("\n"));
                    map.resetObjectsFrom(sb.toString());
                    map.drawOn(sim);
                } catch (IOException ex) {
                    oops(ex);
                }
            }});
        fileMenu.add(open);

        save = new JMenuItem("Save");
        save.addActionListener(e -> {
            JFileChooser chooser = new JFileChooser();
            if (chooser.showSaveDialog(null) == JFileChooser.APPROVE_OPTION) {
                try {
                    File savee = chooser.getSelectedFile();
                    PrintWriter out = new PrintWriter(savee);
                    out.println(map.getMapString());
                    out.close();
                } catch (FileNotFoundException ex) {
                    oops(ex);
                }
            }
        });
        fileMenu.add(save);
    }

    private void oops(IOException e) {
    }
}
