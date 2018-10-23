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
import javafx.util.Duration;

import java.net.URL;
import java.util.Arrays;
import java.util.Observable;
import java.util.Observer;
import java.util.ResourceBundle;

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

        dBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                new UpDownThread("Shared/" + List1.getSelectionModel().getSelectedItems().get(0), "Local/" + List1.getSelectionModel().getSelectedItems().get(0));
            }
        });

        // TODO: 23/10/2018 Handle uploads here
    }

}
