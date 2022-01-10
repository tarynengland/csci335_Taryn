package robosim.old_gui;
/*
import javafx.animation.AnimationTimer;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.*;
import javafx.stage.FileChooser;
import robosim.ai.Controller;
import robosim.core.AIReflector;
import robosim.core.SimObjMaker;
import robosim.core.SimObject;
import robosim.core.Simulator;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.util.stream.Stream;
*/

public class SimController {
    /*
    public static final double RADIUS = 10.0;
    public static final long PERIOD = 60;
    public static final long INTERVAL = 1000000000 / PERIOD;

    @FXML
    ChoiceBox<SimObjMaker> objectToPlace;

    @FXML
    ChoiceBox<String> ai;

    @FXML
    Canvas canvas;

    @FXML
    Button start;

    @FXML
    Button stop;

    @FXML
    Button singleStep;

    @FXML
    Button resume;

    @FXML
    Button reset;

    @FXML
    TextField total;

    @FXML
    TextField forward;

    @FXML
    TextField collisions;

    @FXML
    TextArea messages;

    Simulator map;

    AIReflector<Controller> ais;

    AnimationTimer timer;

    @FXML
    MenuItem open;

    @FXML
    MenuItem save;

    @FXML
    MenuItem close;

    Controller controller;

    @FXML
    void initialize() {
        map = new Simulator(canvas.getWidth(), canvas.getHeight());

        for (SimObjMaker maker: SimObjMaker.values()) {objectToPlace.getItems().add(maker);}
        objectToPlace.getSelectionModel().select(0);

        map.drawOn(canvas);
        canvas.setOnMouseClicked(event -> {
            SimObject obj = objectToPlace.getSelectionModel().getSelectedItem().makeAt(event.getX(), event.getY());
            map.add(obj);
            map.drawOn(canvas);
        });

        start.setOnAction(event -> {
            try {
                setupController();
                halt();
                map.reset();
                startAnimation();
            } catch (Exception e) {
                oops(e);
            }
        });

        stop.setOnAction(event -> halt());

        singleStep.setOnAction(event -> {
            try {
                if (controller == null) {
                    setupController();
                }
                advanceSimulation();
            } catch (Exception exc) {
                oops(exc);
            }
        });

        resume.setOnAction(event -> startAnimation());

        reset.setOnAction(event -> {
            try {
                setupController();
                halt();
                map.reset();
            } catch (Exception e) {
                oops(e);
            }
        });

        ais = new AIReflector<>(Controller.class, "robosim.ai");
        for (String typeName: ais.getTypeNames()) {
            ai.getItems().add(typeName);
        }
        if (ai.getItems().size() > 0) {
            ai.getSelectionModel().select(0);
        }

        total.setEditable(false);
        collisions.setEditable(false);
        forward.setEditable(false);

        open.setOnAction(event -> {
            FileChooser chooser = new FileChooser();
            chooser.setTitle("Select map file");
            File openee = chooser.showOpenDialog(null);
            if (openee != null) {
                try {
                    Stream<String> stream = Files.lines(openee.toPath());
                    StringBuilder sb = new StringBuilder();
                    stream.forEach(s -> sb.append(s).append("\n"));
                    map.resetObjectsFrom(sb.toString());
                    map.drawOn(canvas);
                } catch (IOException e) {
                    oops(e);
                }
            }
        });

        save.setOnAction(event -> {
            FileChooser chooser = new FileChooser();
            chooser.setTitle("Select save location");
            File savee = chooser.showSaveDialog(null);
            if (savee != null) {
                try {
                    PrintWriter out = new PrintWriter(savee);
                    out.println(map.getMapString());
                    out.close();
                } catch (FileNotFoundException e) {
                    oops(e);
                }
            }
        });
    }

    void setupController() throws IllegalAccessException, InstantiationException {
        controller = ais.newInstanceOf(ai.getSelectionModel().getSelectedItem());
    }

    void startAnimation() {
        timer = new AnimationTimer() {
            long next = 0;
            @Override
            public void handle(long now) {
                if (now > next) {
                    next = now + INTERVAL;
                    advanceSimulation();
                }
            }
        };
        timer.start();
    }

    void advanceSimulation() {
        controller.control(map);
        map.move();
        map.drawOn(canvas);
        Platform.runLater(() -> {
            total.setText(Integer.toString(map.getTotalMoves()));
            forward.setText(Integer.toString(map.getForwardMoves()));
            collisions.setText(Integer.toString(map.getCollisions()));
            messages.setText(controller.getStatus());
        });
    }

    void halt() {
        if (timer != null) timer.stop();
    }

    void oops(Exception exc) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setContentText(String.format("Exception: %s\nMessage: %s\n", exc.getClass().toString(), exc.getMessage()));
        alert.show();
        exc.printStackTrace();
    }

     */
}
