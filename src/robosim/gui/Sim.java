package robosim.gui;

import handwriting.gui.DrawingEditor;
import robosim.ai.Controller;
import robosim.core.SimObjMaker;
import robosim.core.SimObject;
import robosim.core.Simulator;
import search.core.AIReflector;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
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

    AIReflector<Controller> ais;

    JMenuItem open, save, close;

    public Sim() {
        setSize(700, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        ais = new AIReflector<>(SimObjMaker.class, "robosim.ai");

        getContentPane().setLayout(new BorderLayout());

        sim = new SimPanel();
        getContentPane().add(sim, BorderLayout.CENTER);
        sim.addMouseListener(new SimClickListener());

        JMenuBar bar = new JMenuBar();
        setJMenuBar(bar);
        addFileMenu(bar);

        objectToPlace = new JComboBox<>();
        for (SimObjMaker maker: SimObjMaker.values()) {objectToPlace.addItem(maker);}

        JPanel topButtons = new JPanel();
        topButtons.add(objectToPlace);
        getContentPane().add(topButtons, BorderLayout.NORTH);
    }

    private class SimClickListener extends MouseAdapter {
        @Override
        public void mousePressed(MouseEvent e) {
            sim.add(objectToPlace.getItemAt(objectToPlace.getSelectedIndex()).makeAt(e.getX(), e.getY()));
        }
    }

    private void addFileMenu(JMenuBar bar) {
        JMenu fileMenu = new JMenu("File");
        bar.add(fileMenu);

        open = new JMenuItem("Open");
        open.addActionListener(e -> {
            JFileChooser chooser = new JFileChooser();
            if (chooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
                try {
                    sim.openFrom(chooser.getSelectedFile());
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
                    sim.saveTo(chooser.getSelectedFile());
                } catch (FileNotFoundException ex) {
                    oops(ex);
                }
            }
        });
        fileMenu.add(save);
    }

    private void oops(IOException e) {
        e.printStackTrace();
    }

    public static void main(String[] args) {
        Sim s = new Sim();
        s.setVisible(true);
    }
}
