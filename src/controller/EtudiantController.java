/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import beans.Employe;
import beans.Etablissement;
import beans.Etudiant;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Locale;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.prefs.Preferences;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TablePosition;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.paint.Paint;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Duration;
import notification.Notification;
import notification.Notifications;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import service.EmployeService;
import service.EtablissementService;
import service.EtudiantService;
import tray.animations.AnimationType;
import tray.notification.NotificationType;
import tray.notification.TrayNotification;

/**
 * FXML Controller class
 *
 * @author Sinponzakra
 */
public class EtudiantController implements Initializable {

    EtudiantService es = new EtudiantService();
    EtablissementService ets = new EtablissementService();
    EmployeService eps = new EmployeService();

    ObservableList<Etudiant> etudiants = FXCollections.observableArrayList();
    ObservableList<Etablissement> etablissemnts = FXCollections.observableArrayList();

    //inner static varriable
    private static int index;
    private static int selectedEtablissementId;
    private static Etablissement currentEtab;

    Date dt = new Date();

    //Fields
    @FXML
    private TextField nom;
    @FXML
    private TextField prenom;
    @FXML
    private DatePicker date;
    @FXML
    private TextField lieu;
    @FXML
    private TextField cne;
    @FXML
    private TextField niveau;
    @FXML
    private ComboBox<Etablissement> etablissement;
    @FXML
    private TableView<Etudiant> mTable;
    @FXML
    private TableColumn<Etudiant, String> nomColumn;
    @FXML
    private TableColumn<Etudiant, String> prenomColumn;
    @FXML
    private TableColumn<Etudiant, LocalDate> dateColumn;
    @FXML
    private TableColumn<Etudiant, String> lieuColumn;
    @FXML
    private TableColumn<Etudiant, String> cneColumn;
    @FXML
    private TableColumn<Etudiant, String> niveauColumn;
    @FXML
    private TableColumn<Etudiant, Etablissement> etablissementColumn;

    //FXML Methods
    @FXML
    private void saveAction(ActionEvent e) {
        Instant instant = Instant.from(date.getValue().atStartOfDay(ZoneId.systemDefault()));
        dt = Date.from(instant);

        es.create(new Etudiant(nom.getText(), prenom.getText(), dt, lieu.getText(), cne.getText(), niveau.getText(), ets.findById(selectedEtablissementId)));
        init();
        clearFields();
    }

    @FXML
    private void deleteAction(ActionEvent e) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("تأكيد");
        alert.setHeaderText("تأكيد الحدف");
        alert.setContentText("هل تريد حدف هذا التلميذ ؟");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {
            es.delete(es.findById(index));
            init();
            clearFields();
        }
    }

    @FXML
    private void updateAction(ActionEvent e) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("تأكيد");
        alert.setHeaderText("تأكيد التغيير");
        alert.setContentText("هل تريد تغيير معلومات هذا الطالب ؟");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {

            Etudiant et = es.findById(index);
            et.setNom(nom.getText());
            et.setPrenom(prenom.getText());
            et.setDateNaissance(dt);
            et.setLieuNaissance(lieu.getText());
            et.setCne(cne.getText());
            et.setNiveauEtude(niveau.getText());
            et.setEtablissement(ets.findById(selectedEtablissementId));
            es.update(et);
            init();
            clearFields();
        }
    }

    @FXML
    private void importeFile(ActionEvent e) throws IOException, FileNotFoundException, ParseException {
        FileChooser fc = new FileChooser();

        fc.setTitle("قم باختيار الملف");
        fc.setInitialDirectory(new File("C:\\Users\\Sinponzakra\\Desktop"));
        fc.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Fichier Excel", "*.xlsx"),
                new FileChooser.ExtensionFilter("Fichier CSV", "*.csv")
        );

        Stage mStage = (Stage) ((Node) e.getSource()).getScene().getWindow();

        if (mStage != null) {
            File mFile = fc.showOpenDialog(mStage);
            if (mFile != null) {
                saveFromFile(mFile);
            }
        }

    }

    private void saveFromFile(File mFile) throws FileNotFoundException, IOException, ParseException {
        FileInputStream file = new FileInputStream(mFile);
        XSSFWorkbook workbook = new XSSFWorkbook(file);
        XSSFSheet sheet = workbook.getSheetAt(1);

        int EtudAdded = 0;
        Row row;
        for (int i = 1; i <= sheet.getLastRowNum(); i++) {
            if (sheet.getRow(i) != null) {
                row = (Row) sheet.getRow(i);

                String nom, prenom;
                if (row.getCell(2) == null) {
                    nom = null;
                    prenom = null;
                } else {
                    int firstOccurence = row.getCell(2).toString().indexOf(' ');
                    if (firstOccurence == -1) {
                        break;
                    }
                    //System.out.println(firstOccurence);
                    nom = row.getCell(2).toString().substring(firstOccurence);
                    prenom = row.getCell(2).toString().substring(0, firstOccurence);

                }
               // System.out.println("nom : " + nom + " | prenom :" + prenom);

                Date dt;
                if (row.getCell(3) == null) {
                    dt = null;
                } else {
                    SimpleDateFormat sdf = new SimpleDateFormat("dd-MMM-yyyy", Locale.FRENCH);
                    dt = sdf.parse(row.getCell(3).toString());

                }
               // System.out.println("Date Naissance :" + dt);

                String lieu;
                if (row.getCell(4) == null) {
                    lieu = null;
                } else {
                    lieu = row.getCell(4).toString();

                }
               // System.out.println("lieu :" + lieu);

                String cne;
                if (row.getCell(6) == null) {
                    cne = null;
                } else {
                    cne = row.getCell(6).toString().replaceAll("\\.", "");
                    
                 
                }
               // System.out.println("cne : " + cne);

                String niveau;
                if (row.getCell(5) == null) {
                    niveau = null;
                } else {
                    niveau = row.getCell(5).toString();
                }
               // System.out.println("niveau : " + niveau);
                
                if(es.isNotExist(nom, prenom, dt)) {
                    Etudiant e = new Etudiant(nom, prenom, dt, lieu, cne, niveau, currentEtab);
                    es.create(e);
                    init();
                    EtudAdded++;
                }
                
            } else {
                break;
            }

        }
        
            if(EtudAdded != 0){        
                    String title = "إعلام";
                    String message = "لقد تمت الإضافة بنجاح";
        
                    TrayNotification tray = new TrayNotification();
                    tray.setTitle(title);
                    tray.setMessage(message);
                    tray.setRectangleFill(Paint.valueOf("#2A9A84"));
                    tray.setAnimationType(AnimationType.POPUP);
                    tray.setNotificationType(NotificationType.SUCCESS);
                    tray.showAndDismiss(Duration.seconds(3));
            } else {
                    String title = "إعلام";
                    String message = "ليس هناك أي تحديث متاح";
        
                    TrayNotification tray = new TrayNotification();
                    tray.setTitle(title);
                    tray.setMessage(message);
                    tray.setRectangleFill(Paint.valueOf("#f44248"));
                    tray.setAnimationType(AnimationType.POPUP);
                    tray.setNotificationType(NotificationType.ERROR);
                    tray.showAndDismiss(Duration.seconds(3)); 
            }
    }

    //clear Fields function
    public void clearFields() {
        nom.clear();
        prenom.clear();
        date.setValue(null);
        lieu.clear();
        cne.clear();
        niveau.clear();
        etablissement.getSelectionModel().clearSelection();
    }

    //init function
    private void init() {
        etudiants.clear();
        etablissemnts.clear();

        nomColumn.setCellValueFactory(new PropertyValueFactory<>("nom"));
        prenomColumn.setCellValueFactory(new PropertyValueFactory<>("prenom"));
        dateColumn.setCellValueFactory(new PropertyValueFactory<>("dateNaissance"));
        lieuColumn.setCellValueFactory(new PropertyValueFactory<>("lieuNaissance"));
        cneColumn.setCellValueFactory(new PropertyValueFactory<>("cne"));
        niveauColumn.setCellValueFactory(new PropertyValueFactory<>("niveauEtude"));
        etablissementColumn.setCellValueFactory(new PropertyValueFactory<>("etablissement"));

        if (es.findAll() != null) {
            etudiants.addAll(es.findAll());
        }

        if (ets.findAll() != null) {
            etablissemnts.addAll(ets.findAll());
        }

        etablissement.setItems(etablissemnts);
        mTable.setItems(etudiants);

    }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        init();
        mTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        etablissement.setOnAction(e -> {
            Etablissement et = etablissement.getSelectionModel().getSelectedItem();
            selectedEtablissementId = et.getId();
        });

        mTable.setOnMousePressed(e -> {
            TablePosition pos = (TablePosition) mTable.getSelectionModel().getSelectedCells().get(0);
            int row = pos.getRow();
            Etudiant item = mTable.getItems().get(row);
            index = item.getId();

            nom.setText(item.getNom());
            prenom.setText(item.getPrenom());

            Date dts = item.getDateNaissance();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
            SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
            LocalDate localDate = LocalDate.parse(sdf.format(dts), formatter);

            date.setValue(localDate);

            lieu.setText(item.getLieuNaissance());
            cne.setText(item.getCne());
            niveau.setText(item.getNiveauEtude());
            etablissement.getSelectionModel().select(item.getEtablissement());
        });

        Preferences userPreferences = Preferences.userRoot();
        int currentUserId = userPreferences.getInt("currentUserId", 0);
        Employe e = eps.findById(currentUserId);
        currentEtab = e.getEtablissement();
        etablissement.getSelectionModel().select(currentEtab);
    }

}
