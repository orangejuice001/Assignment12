// Assignment 12: Arizona State University CSE205
//         Name: Rhea Mane
//    StudentID: 1222796497
//      Lecture: T/Th 1:30
//  Description: This class creates all components, sets their handler/listener, and arranges them using layout panes.

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;


public class WaveControlPane extends Pane {

    // ******************************************************************
    // Task #2a: add instance variables for sliders, buttons, and labels
    // ******************************************************************
    Button startButton;
    Button stopButton;
    Button clearButton;
    Button surpriseButton;
    Label speedLabel;
    Label widthLabel;
    Label heightLabel;
    Slider speedSlider;
    Slider widthSlider;
    Slider heightSlider;
    private WaveDisplayPane wavePane;
    private int width, height;
    private Color color;
    private ColorPicker picker;

    // constructor to create all components, set their handler/listener,
    // and arrange them using layout panes.
    public WaveControlPane(int h, int w, Color initialColor) {
        this.color = initialColor;
        this.width = (int) (h * 0.68);
        this.height = w - 10;

        // creates a pane to display waves with the specified color
        wavePane = new WaveDisplayPane(width, color);
        wavePane.setMinSize(width, height);
        wavePane.setMaxSize(width, height);

        // create a color picker with the specified initial color
        picker = new ColorPicker(color);
        picker.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);

        // ************************************************************************
        // Task #2b: create 4 buttons and resize them to the width of the VBox pane,
        // then add them to the VBox buttonPane instantiated below.
        // *************************************************************************
        startButton = new Button("Start");
        stopButton = new Button("Stop");
        clearButton = new Button("Clear");
        surpriseButton = new Button("Surprise!");
        startButton.setMaxWidth(Double.MAX_VALUE);
        stopButton.setMaxWidth(Double.MAX_VALUE);
        clearButton.setMaxWidth(Double.MAX_VALUE);
        surpriseButton.setMaxWidth(Double.MAX_VALUE);
        VBox buttonPane = new VBox(startButton, stopButton, clearButton, surpriseButton, picker);

        buttonPane.setPrefSize(100, 100);
        buttonPane.setAlignment(Pos.CENTER);


        // ************************************************************************
        // Task #2c: create 3 sliders and 3 labels and add them to the VBox panes
        // instantiated below.
        // *************************************************************************
        speedLabel = new Label("Speed");
        speedSlider = new Slider();
        speedSlider.setOrientation(Orientation.VERTICAL);
        speedSlider.setMajorTickUnit(10);
        speedSlider.setMinorTickCount(5);
        speedSlider.setMin(5);
        speedSlider.setMax(100);
        speedSlider.setValue(20);

        widthLabel = new Label("Width");
        widthSlider = new Slider();
        widthSlider.setOrientation(Orientation.VERTICAL);
        widthSlider.setMajorTickUnit(10);
        widthSlider.setMinorTickCount(5);
        widthSlider.setMin(5);
        widthSlider.setMax(100);
        widthSlider.setValue(50);

        heightLabel = new Label("Height");
        heightSlider = new Slider();
        heightSlider.setOrientation(Orientation.VERTICAL);
        heightSlider.setMajorTickUnit(10);
        heightSlider.setMinorTickCount(5);
        heightSlider.setMin(5);
        heightSlider.setMax(100);
        heightSlider.setValue(100);

        VBox speedSliderPane = new VBox(speedLabel, speedSlider);
        VBox waveLengthSliderPane = new VBox(widthLabel, widthSlider);
        VBox waveAmplitudeSliderPane = new VBox(heightLabel, heightSlider);

        TilePane sliderPane = new TilePane();
        sliderPane.setPrefColumns(3);
        sliderPane.setPadding(new Insets(5, 5, 5, 5));
        sliderPane.setAlignment(Pos.CENTER);
        sliderPane.getChildren().addAll(speedSliderPane, waveLengthSliderPane, waveAmplitudeSliderPane);

        HBox controls = new HBox(buttonPane, sliderPane);
        controls.setAlignment(Pos.CENTER);

        BorderPane controlsAndWaves = new BorderPane();
        controlsAndWaves.setLeft(controls);
        controlsAndWaves.setCenter(wavePane);

        this.getChildren().add(controlsAndWaves);

        // ************************************************************************
        // Task #2d: Register the buttons, sliders, and color picker with the
        // appropriate handler object
        // *************************************************************************

        startButton.setOnAction(new ButtonHandler());
        stopButton.setOnAction(new ButtonHandler());
        clearButton.setOnAction(new ButtonHandler());
        surpriseButton.setOnAction(new ButtonHandler());
        speedSlider.valueProperty().addListener(new SpeedHandler());
        widthSlider.valueProperty().addListener(new WaveLengthHandler());
        heightSlider.valueProperty().addListener(new WaveAmplitudeHandler());
        picker.setOnAction(new ColorHandler());

    }

    // ************************************************************************
    // Task #3: Implement event handlers for the four buttons (task #3a),
    // the color picker (task #3b), the speed slider (task #3c), and the
    // sliders for wave amplitude and legth (tasks #3d, #3e)
    // *************************************************************************

    public class ButtonHandler implements EventHandler<ActionEvent> {
        public void handle(ActionEvent e) {
            if (e.getTarget() == startButton) {
                wavePane.resume();
            } else if (e.getTarget() == stopButton) {
                wavePane.suspend();
            } else if (e.getTarget() == clearButton) {
                wavePane.clearPane();
            } else if(e.getTarget() == surpriseButton){
                wavePane.suspend();

                // creating variables for the random values
                int randomAmplitude = (int) Math.floor(Math.random() * (96) + 5);
                int randomWavelength = (int) Math.floor(Math.random() * (96) + 5);
                int randomSpeed = (int) Math.floor(Math.random() * (96) + 5);
                float randomRed = (float) Math.random();
                float randomGreen = (float)Math.random();
                float randomBlue = (float)Math.random();

                wavePane.setWaveAmplitude(randomAmplitude);
                wavePane.setWaveLength(randomWavelength);
                wavePane.setRate(randomSpeed);

                Color newColor = new Color(randomRed, randomGreen, randomBlue, 1.0);
                wavePane.changeColor(newColor);

                heightSlider.setValue(randomAmplitude);
                widthSlider.setValue(randomWavelength);
                speedSlider.setValue(randomSpeed);
                picker.setValue(newColor);

                wavePane.resume();


            }
        }
    }

    public class ColorHandler implements EventHandler<ActionEvent> {
        public void handle(ActionEvent actionEvent) {
            wavePane.changeColor(picker.getValue());
        }
    }

    public class SpeedHandler implements ChangeListener<Number> {
        public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
            wavePane.setRate((int) speedSlider.getValue());
        }
    }

    public class WaveLengthHandler implements ChangeListener<Number> {
        public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
            wavePane.setWaveLength((int) widthSlider.getValue());
        }
    }

    public class WaveAmplitudeHandler implements ChangeListener<Number> {
        public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
            wavePane.setWaveAmplitude((int) heightSlider.getValue());
        }
    }


}

