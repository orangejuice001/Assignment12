// Assignment 12: Arizona State University CSE205
//         Name: Rhea Mane
//    StudentID: 1222796497
//      Lecture: T/Th 1:30
//  Description: This class handles the animation


import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.util.Duration;

public class WaveDisplayPane extends Pane {

    // Task #1: implement instance variables, constructor, and methods
    // as outlned in the UML diagram and assignment description
    private Timeline timeline;
    private int time;
    private int waveLength;
    private int waveAmplitude;
    private int paneWidth;
    private Color color;

    //Initializing Constructor
    public WaveDisplayPane(int paneWidth, Color color) {
        time = 0;
        waveAmplitude = 100;
        waveLength = 50;
        this.paneWidth = paneWidth;
        this.color = color;

        setStyle("-fx-background-color:white; "+"-fx-border-color:black; ");
        KeyFrame kf = new KeyFrame(Duration.millis(150), (EventHandler<ActionEvent>) new WaveHandler());
        timeline = new Timeline(kf);
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
    }

    //implementing resume method
    public void resume(){
        timeline.play();
    }

    // implementing suspend method
    public void suspend(){
        timeline.pause();
        time = 0;
    }

    // implementing changeColor method
    public void changeColor(Color c){
        this.color = c;
    }

    // implementing clearPane method
    public void clearPane(){
        getChildren().clear();
        suspend();
    }

    // implementing setWaveLength method
    public void setWaveLength(int wl){
        waveLength = wl;
    }

    // implementing setWaveAmplitude method
    public void setWaveAmplitude(int wa){
        waveAmplitude = wa;
    }

    // implementing setRate method
    public void setRate(int rate){
        timeline.setRate(rate);
    }

    // defines an event listener to draw a new point
    private class WaveHandler implements EventHandler<ActionEvent> {
        public void handle(ActionEvent event) {
            time++;
            int x = (waveLength * time) / 314;
            int y = (int) (waveAmplitude * Math.sin((0.0174533) * time) + 115);

            if (x < paneWidth) {
                Circle dot = new Circle(x, y, 2);
                dot.setFill(color);
                getChildren().add(dot);
            } else suspend();
        }
    }
}

