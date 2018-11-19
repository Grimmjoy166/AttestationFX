/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import beans.Etablissement;
import beans.Profil;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TablePosition;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import service.EtablissementService;

/**
 * FXML Controller class
 *
 * @author Sinponzakra
 */
public class EtablissementController implements Initializable {

    EtablissementService es = new EtablissementService();
    ObservableList<Etablissement> etablissements = FXCollections.observableArrayList();
    private static int index;
    @FXML
    private TextField nom;
    @FXML
    private TextField type;
    @FXML
    private TextField region;
    @FXML
    private TableView<Etablissement> mTable;
    @FXML
    private TableColumn<Etablissement, String> nomColumn;
    @FXML
    private TableColumn<Etablissement, String> typeColumn;
    @FXML
    private TableColumn<Etablissement, String> regionColumn;

    @FXML
    private void saveAction(ActionEvent e) {
        es.create(new Etablissement(nom.getText(), type.getText(), region.getText()));
        init();
        clearFields();
    }

    @FXML
    private void deleteAction(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("تأكيد");
        alert.setHeaderText("تأكيد الحدف");
        alert.setContentText("هل حقا تريد حدف هده المؤسسة ؟");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {
            es.delete(es.findById(index));
            init();
            clearFields();
        }
    }
    
     @FXML
    private void updateAction(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("تأكيد");
        alert.setHeaderText("تأكيد التغيير");
        alert.setContentText("هل تريد حقا تغيير معلومات هذه المؤسسة ؟");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {
            Etablissement e = es.findById(index);
            e.setNom(nom.getText());
            e.setType(type.getText());
            e.setRegion(region.getText());
            es.update(e);
            init();
            clearFields();
        }
    }

    private void clearFields() {
        nom.clear();
        type.clear();
        region.clear();
    }

    private void init() {
        etablissements.clear();
        nomColumn.setCellValueFactory(new PropertyValueFactory<>("nom"));
        typeColumn.setCellValueFactory(new PropertyValueFactory<>("type"));
        regionColumn.setCellValueFactory(new PropertyValueFactory<>("region"));

        if (es.findAll() != null)
            etablissements.addAll(es.findAll());
        

        mTable.setItems(etablissements);
    }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
       init();
        mTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        mTable.setOnMousePressed(e-> {
            TablePosition pos = (TablePosition) mTable.getSelectionModel().getSelectedCells().get(0);
            int row = pos.getRow();
            Etablissement item = mTable.getItems().get(row);
            index = item.getId();
            
            nom.setText(item.getNom());
            type.setText(item.getType());
            region.setText(item.getRegion());
        });
    }

}