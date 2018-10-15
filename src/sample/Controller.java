package sample;


import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;
import javafx.util.Duration;

import java.net.URL;
import java.util.*;
import java.util.Observer;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;


public class Controller implements Initializable, Observer {
    @FXML
    ListView<String> List1;
    private ObservableList<String> observableList = FXCollections.observableArrayList();
    private FolderMonImpl f = new FolderMonImpl();

    public void getObservableList() {

        String[] names = f.getNames();

        observableList.clear();

        observableList.addAll(Arrays.asList(names));

        List1.setItems(observableList);

    }

    public void getSelection() {

        List1.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                String choiceV = List1.getSelectionModel().getSelectedItems().get(0);
                f.openFile(choiceV);
            }
        });

    }

    @Override
    public void update(Observable o, Object arg) {
        getObservableList();
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        getObservableList();
        f.addObserver(this);
        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(5), ev -> {
            // code to run every 5 secondus goes here
            System.out.println("5  seconds");
            f.isChange();
        }));
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();

    }
}