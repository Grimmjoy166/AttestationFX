/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import beans.Etablissement;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TablePosition;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
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
    private TextField telephone;
    @FXML
    private TextField codeEtablissement;
    @FXML
    private TextField ville;
    @FXML
    private Button btnAdd;
    @FXML
    private TableView<Etablissement> mTable;
    @FXML
    private TableColumn<Etablissement, String> nomColumn;
    @FXML
    private TableColumn<Etablissement, String> typeColumn;
    @FXML
    private TableColumn<Etablissement, String> regionColumn;
    @FXML
    private TableColumn<Etablissement, String> telephoneColumn;
    @FXML
    private TableColumn<Etablissement, String> codeEtablissementColumn;
    @FXML
    private TableColumn<Etablissement, String> villeColumn;

    @FXML
    private void saveAction(ActionEvent e) {
        es.create(new Etablissement(nom.getText(), type.getText(), region.getText(), telephone.getText(), codeEtablissement.getText(), ville.getText()));
        init();
        clearFields();
        checkRowCount();
    }

    @FXML
    private void deleteAction(ActionEvent event) throws IOException {

        Stage window = new Stage();
        window.initModality(Modality.APPLICATION_MODAL);
        window.initStyle(StageStyle.UNDECORATED);
        window.getIcons().add(new Image(this.getClass().getResource("/images/loginLogo.png").toString()));

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/vue/ConfirmBoxVue.fxml"));
        Parent root = (Parent) fxmlLoader.load();
        ConfirmBoxController controller = fxmlLoader.<ConfirmBoxController>getController();
        controller.setmMessage("هل حقا تريد حدف هده المؤسسة ؟");
        controller.setmTitle("تأكيد الحدف");

        Scene scene = new Scene(root);

        window.setScene(scene);
        window.showAndWait();

        if (controller.getCurrentState()) {
            es.delete(es.findById(index));
            init();
            clearFields();
            checkRowCount();
        }
    }

    @FXML
    private void updateAction(ActionEvent event) throws IOException {

        Stage window = new Stage();
        window.initModality(Modality.APPLICATION_MODAL);
        window.initStyle(StageStyle.UNDECORATED);
        window.getIcons().add(new Image(this.getClass().getResource("/images/loginLogo.png").toString()));

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/vue/ConfirmBoxVue.fxml"));
        Parent root = (Parent) fxmlLoader.load();
        ConfirmBoxController controller = fxmlLoader.<ConfirmBoxController>getController();
        controller.setmMessage("هل تريد حقا تغيير معلومات هذه المؤسسة ؟");
        controller.setmTitle("تأكيد التغيير");

        Scene scene = new Scene(root);

        window.setScene(scene);
        window.showAndWait();

        if (controller.getCurrentState()) {
            Etablissement e = es.findById(index);
            e.setNom(nom.getText());
            e.setType(type.getText());
            e.setRegion(region.getText());
            e.setTelephone(telephone.getText());
            e.setCodeEtablissement(codeEtablissement.getText());
            e.setVille(ville.getText());
            es.update(e);
            init();
            clearFields();
        }
    }
    
    private void checkRowCount() {
        
       if(es.getEtablissementsCount() == 1) {
            btnAdd.setDisable(true);
        }else {
            btnAdd.setDisable(false);
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
        telephoneColumn.setCellValueFactory(new PropertyValueFactory<>("telephone"));
        codeEtablissementColumn.setCellValueFactory(new PropertyValueFactory<>("codeEtablissement"));
        villeColumn.setCellValueFactory(new PropertyValueFactory<>("ville"));
        
        if (es.findAll() != null) {
            etablissements.addAll(es.findAll());
        }

        mTable.setItems(etablissements);
    }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        init();
        mTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        mTable.setOnMousePressed(e -> {
            TablePosition pos = (TablePosition) mTable.getSelectionModel().getSelectedCells().get(0);
            int row = pos.getRow();
            Etablissement item = mTable.getItems().get(row);
            index = item.getId();

            nom.setText(item.getNom());
            type.setText(item.getType());
            region.setText(item.getRegion());
            telephone.setText(item.getTelephone());
            codeEtablissement.setText(item.getCodeEtablissement());
            ville.setText(item.getVille());
        });
        
        checkRowCount();
    }

}
