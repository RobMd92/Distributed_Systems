package sample;

import com.sun.jndi.toolkit.url.Uri;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;


public class Controller {
    @FXML
    ListView<String> List1;
    private ObservableList<String> observableList = FXCollections.observableArrayList();
    private FolderMonImpl f = new FolderMonImpl();

    public void getObservableList() {

        String[] names = f.getNames();

        observableList.addAll(Arrays.asList(names));

        List1.setItems(observableList);

    }
    public void getSelection(){

        List1.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                String choiceV = List1.getSelectionModel().getSelectedItems().get(0);
                f.openFile(choiceV);
            }
        });

    }
}