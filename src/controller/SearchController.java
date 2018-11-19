/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import beans.Employe;
import beans.Etablissement;
import beans.Etudiant;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;
import java.util.prefs.Preferences;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TablePosition;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import service.EmployeService;
import service.EtablissementService;
import service.EtudiantService;

/**
 * FXML Controller class
 *
 * @author Sinponzakra
 */
public class SearchController implements Initializable {
    EtudiantService es = new EtudiantService();
    EtablissementService ets = new EtablissementService();
    EmployeService eps = new EmployeService();
    
    ObservableList<Etudiant> etudiants = FXCollections.observableArrayList();
    ObservableList<Etudiant> fetchedEtudiants = FXCollections.observableArrayList();
    ObservableList<Etudiant> etudiants2 = FXCollections.observableArrayList();

    
    
    //inner static varriable
    private static int index;
    private static Etablissement currentEtab;

    Date dt = new Date();
    
    
   
    @FXML
    private TextField nom;
    //Table 1
    @FXML
    private TableView<Etudiant> mTable;
    @FXML
    private TableColumn<Etudiant, String> nomColumn;
    @FXML
    private TableColumn<Etudiant, String> prenomColumn;
    @FXML
    private TableColumn<Etudiant, LocalDate> dateColumn;
    
    //Table 2
    @FXML
    private TableView<Etudiant> mTable1;
    @FXML
    private TableColumn<Etudiant, String> nomColumn1;
    @FXML
    private TableColumn<Etudiant, String> prenomColumn1;
    @FXML
    private TableColumn<Etudiant, LocalDate> dateColumn1;
    @FXML
    private TableColumn<Etudiant, String> lieuColumn1;
    @FXML
    private TableColumn<Etudiant, String> cneColumn1;
    @FXML
    private TableColumn<Etudiant, String> niveauColumn1;
    @FXML
    private TableColumn<Etudiant, Etablissement> etablissementColumn1;
    
    private void configTableColumn() {
        nomColumn.setCellValueFactory(new PropertyValueFactory<>("nom"));
        prenomColumn.setCellValueFactory(new PropertyValueFactory<>("prenom"));
        dateColumn.setCellValueFactory(new PropertyValueFactory<>("dateNaissance"));
        
        
        
        nomColumn1.setCellValueFactory(new PropertyValueFactory<>("nom"));
        prenomColumn1.setCellValueFactory(new PropertyValueFactory<>("prenom"));
        dateColumn1.setCellValueFactory(new PropertyValueFactory<>("dateNaissance"));
        lieuColumn1.setCellValueFactory(new PropertyValueFactory<>("lieuNaissance"));
        cneColumn1.setCellValueFactory(new PropertyValueFactory<>("cne"));
        niveauColumn1.setCellValueFactory(new PropertyValueFactory<>("niveauEtude"));
        etablissementColumn1.setCellValueFactory(new PropertyValueFactory<>("etablissement"));
    }
    
    //init function
    private void init() {
        etudiants.clear();
    
        configTableColumn();
        
         if(es.findAllbyEtab(currentEtab) != null)
            etudiants.addAll(es.findAllbyEtab(currentEtab));
        
        mTable.setItems(etudiants);
        
    }
    
    private void fillTab1(ObservableList<Etudiant> searchedEtud) {      
        mTable.setItems(searchedEtud);
        mTable1.setVisible(false);
    }
    
    private void fillTab2(Etudiant selectedEtud) {
        etudiants2.clear();
        
        if(es.findById(selectedEtud.getId()) != null)
             etudiants2.add(es.findById(selectedEtud.getId()));
        
        mTable1.setItems(etudiants2);
        mTable1.setVisible(true);
    }
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) { 
        mTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        
        
        mTable.setOnMousePressed(e -> {
            TablePosition pos = (TablePosition) mTable.getSelectionModel().getSelectedCells().get(0);
            int row = pos.getRow();
            Etudiant item = mTable.getItems().get(row);
            index = item.getId();
            
            fillTab2(item);
               
        });
      
        nom.textProperty().addListener(e -> {
                
                fetchedEtudiants.clear();
    
                for(Etudiant ee : es.findAllbyEtab(currentEtab)) {
                    System.out.println("looking for :"+ee.toString());
                    System.out.println("typed :"+nom.getText());
                    if(ee.toString().contains(nom.getText())) {
                        fetchedEtudiants.add(ee);
                    }
                }
                fillTab1(fetchedEtudiants);
          
        });
        
        Preferences userPreferences = Preferences.userRoot();
        int currentUserId = userPreferences.getInt("currentUserId", 0);
        Employe e = eps.findById(currentUserId);
        currentEtab = e.getEtablissement();
        
        init();
    }    
    
}
