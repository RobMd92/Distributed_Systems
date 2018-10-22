package sample;


import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;
import javafx.util.Duration;

import java.net.URL;
import java.sql.SQLOutput;
import java.util.*;
import java.util.Observer;


public class Controller implements Initializable, Observer {

    @FXML
    ListView<String> List1;
    @FXML
    ListView<String> List2;
    @FXML
    Button dBtn;

    @FXML
    Button uBtn;
    @FXML
    Button pBtn;

    private ObservableList<String> SharedList = FXCollections.observableArrayList();
    private ObservableList<String> LocalList = FXCollections.observableArrayList();
    private SharedImpl f;
    private LocalImpl L;
    private String choice;

    public void getObservableList() {


        String[] names = SharedImpl.getInstance().getNames();

        SharedList.clear();

        SharedList.addAll(Arrays.asList(names));

        List1.setItems(SharedList);

        names = LocalImpl.getInstance().getNames();
        LocalList.clear();
        LocalList.addAll(Arrays.asList(names));
        List2.setItems(LocalList);

    }

    public void getSelection() {
        choice = List1.getSelectionModel().getSelectedItems().get(0);
        SharedImpl.getInstance().openFile(choice);
    }

    @Override
    public void update(Observable o, Object arg) {
        getObservableList();
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        getObservableList();
        SharedImpl.getInstance().addObserver(this);
        LocalImpl.getInstance().addObserver(this);

        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(5), ev -> {
            // code to run every 5 secondus goes here
            System.out.println("5  seconds");

            SharedImpl.getInstance().isChange();
            LocalImpl.getInstance().isChange();


        }));
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();


    }

    public void Dbtn() {
        dBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) throws NullPointerException {
                System.out.println("Current Active threads" + Thread.activeCount());
                try {
                    Thread t1 = new Thread(Controller.this::getSelection);
                    t1.setDaemon(true);
                    t1.start();
                }
                catch (NullPointerException e){
                    e.printStackTrace();
                }




                System.out.println("\nCurrent Active threads" + Thread.activeCount());
                System.out.println(Thread.currentThread());
                Thread[] threads = new Thread[50];
                Thread.enumerate(threads);
                System.out.println(Arrays.toString(threads));

            }
        });
    }


}
