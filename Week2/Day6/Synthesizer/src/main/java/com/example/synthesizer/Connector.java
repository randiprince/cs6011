package com.example.synthesizer;

import javafx.geometry.Bounds;
import javafx.geometry.Point2D;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;

import java.util.ArrayList;

class Cable extends Circle {

    AudioComponentWidgetBase parent_;
    ArrayList<Line> lines = new ArrayList<>();
    Line line_ = null;


    Cable(Pane pane, AudioComponentWidgetBase parent) {
        super(10);
        parent_ = parent;
        setFill(Color.BLUE);

        pane.getChildren().add(this);
        this.setOnMousePressed(e -> startConnection(e));
        this.setOnMouseDragged(e -> moveConnection(e));
        this.setOnMouseReleased(e -> stopConnecting(e));
    }

    private void startConnection(MouseEvent e) {
        AnchorPane mainWindow = SynthesizeApplication.MainCanvas_.getWindow();
        System.out.println("start connection");
        if (line_ != null) {
            mainWindow.getChildren().remove(line_);
        }
        Bounds mainBounds = mainWindow.localToScene(mainWindow.getBoundsInLocal());

        line_ = new Line();
        line_.setStrokeWidth(4);
        line_.setStartX(e.getSceneX() - mainBounds.getMinX());
        line_.setStartY(e.getSceneY() - mainBounds.getMinY());
        line_.setEndX(e.getSceneX() - mainBounds.getMinX());
        line_.setEndY(e.getSceneY() - mainBounds.getMinY());
        mainWindow.getChildren().add(line_);
    }

    private void moveConnection(MouseEvent e) {
        System.out.println("move connection");
        AnchorPane mainWindow = SynthesizeApplication.MainCanvas_.getWindow();
        if(line_ != null) {
            Bounds bounds = mainWindow.getBoundsInParent();
            line_.setEndX(e.getSceneX() - bounds.getMinX());
            line_.setEndY(e.getSceneY() - bounds.getMinY());
        }

        e.consume();
    }

    private void stopConnecting(MouseEvent e) {
        System.out.println("stop connection");
        AnchorPane mainWindow = SynthesizeApplication.MainCanvas_.getWindow();

        Speaker speaker = SynthesizeApplication.speaker_;
        Bounds speakerBounds = speaker.localToScene(speaker.getBoundsInLocal());
        double distanceToSpeaker = Math.sqrt(Math.pow(speakerBounds.getCenterX() - e.getSceneX(), 2.0) +
                Math.pow(speakerBounds.getCenterY() - e.getSceneY(), 2.0));
        double distanceToVolume = 50;
        AudioComponentWidgetBase acwbVolume_ =null;

        for (AudioComponentWidgetBase acwb : SynthesizeApplication.widgets_) {
            if (acwb.getAudioComponent().hasInput() && acwb.getComponentName() == "Volume") {
                Bounds inputBounds = acwb.connector_.localToScene(acwb.connector_.getBoundsInLocal());
                double tempDistanceToVolume = Math.sqrt(Math.pow(inputBounds.getCenterX() - e.getSceneX(), 2.0) +
                        Math.pow(inputBounds.getCenterY() - e.getSceneY(), 2.0));
                if (tempDistanceToVolume < distanceToVolume) {
                    distanceToVolume = tempDistanceToVolume;
                }
                acwbVolume_ = acwb;
            }

        }

        System.out.println(distanceToSpeaker);
        System.out.println(distanceToVolume);

        if (distanceToSpeaker < 10) {
            SynthesizeApplication.connectedWidgets_.add(parent_);
            lines.add(line_);
        } else if ( distanceToVolume < 10) {
            acwbVolume_.getAudioComponent().connectInput(parent_.getAudioComponent());
            lines.add(line_);
        } else {
            mainWindow.getChildren().remove(line_);
//            SynthesizeApplication.connectedWidgets_.remove(this);
            line_ = null;
        }
    }
    void removeLines() {
        AnchorPane mainWindow = SynthesizeApplication.MainCanvas_.getWindow();
        for( int i = lines.size() -1; i >= 0; i -- ) {
            mainWindow.getChildren().remove(lines.get(i));
        }
    }

    void moveLines() {
        AnchorPane mainWindow = SynthesizeApplication.MainCanvas_.getWindow();
        Bounds mainBounds = mainWindow.getBoundsInParent();
        Bounds bounds = this.localToScene(this.getBoundsInLocal());
        for (int i = 0; i < lines.size(); i++) {
            lines.get(i).setStartX(bounds.getCenterX() - mainBounds.getMinX());
            lines.get(i).setStartY(bounds.getCenterY() - mainBounds.getMinY());
        }
    }

}
