package controller;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import beans.Employe;
import com.jfoenix.controls.JFXButton;
import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.prefs.BackingStoreException;
import java.util.prefs.Preferences;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import service.EmployeService;
import service.EtablissementService;
import service.EtudiantService;
import service.ProfilService;
import util.EffectUtilities;

/**
 *
 * @author Sinponzakra
 */
public class MainController implements Initializable {

    EmployeService es = new EmployeService();
    ProfilService ps = new ProfilService();
    EtudiantService etuds = new EtudiantService();
    EtablissementService etabs = new EtablissementService();

    @FXML
    private Label headerText;
    @FXML
    private BorderPane mBorder;
    @FXML
    private Text profilCount;
    @FXML
    private Text employeCount;
    @FXML
    private Text etudiantCount;
    @FXML
    private Text etablissementCount;
    @FXML
    private VBox mainCenter;
    @FXML
    private VBox profilTicket;
    @FXML
    private VBox employeTicket;
    @FXML
    private VBox etudiantTicket;
    @FXML
    private VBox etablissementTicket;
    @FXML
    private JFXButton logOutBtn;
    @FXML
    private Label userName;
    @FXML
    private Label userEmail;
    @FXML
    private ImageView exitBarBtn;
    @FXML
    private ImageView maxBarBtn;
    @FXML
    private ImageView minBarBtn;
    @FXML
    private HBox mTopBar;

    @FXML
    private final CategoryAxis mChartEtab = new CategoryAxis();
    @FXML
    private final NumberAxis mChartEtud = new NumberAxis();
    @FXML
    private BarChart<Number, String> mChart = new BarChart<Number, String>(mChartEtud, mChartEtab);

    @FXML
    private void switchtoProfil(ActionEvent e) throws IOException {
        headerText.setText("الوظائف");
        VBox v = FXMLLoader.load(getClass().getResource("/vue/ProfilVue.fxml"));
        mBorder.setCenter(v);
    }

    @FXML
    private void switchtoHome(ActionEvent e) throws IOException {
        headerText.setText("الواجهة");
        mBorder.setCenter(mainCenter);
        setChart();
        setCountsHome();
    }

    @FXML
    private void switchtoEmploye(ActionEvent e) throws IOException {
        headerText.setText("الموظفون");
        VBox v = FXMLLoader.load(getClass().getResource("/vue/EmployeVue.fxml"));
        mBorder.setCenter(v);
    }

    @FXML
    private void switchEtudiant(ActionEvent e) throws IOException {
        headerText.setText("التلاميذ");
        VBox v = FXMLLoader.load(getClass().getResource("/vue/EtudiantVue.fxml"));
        mBorder.setCenter(v);
    }

    @FXML
    private void switchEtablissement(ActionEvent e) throws IOException {
        headerText.setText("المؤسسات");
        VBox v = FXMLLoader.load(getClass().getResource("/vue/EtablissementVue.fxml"));
        mBorder.setCenter(v);
    }

    @FXML
    private void switchSearch(ActionEvent e) throws IOException {
        headerText.setText("البحت");
        VBox v = FXMLLoader.load(getClass().getResource("/vue/SearchVue.fxml"));
        mBorder.setCenter(v);
    }

    private void setCountsHome() {
        profilCount.setText(ps.getProfilsCount() + "");
        employeCount.setText(es.getEmployesCount() + "");
        etudiantCount.setText(etuds.getEtudiantsCount() + "");
        etablissementCount.setText(etabs.getEtablissementsCount() + "");

    }

    private void setChart() {
        mChart.getData().clear();
        mChart.setBarGap(3);
        mChart.setCategoryGap(20);

        XYChart.Series chartSeries = new XYChart.Series();

        for (Object[] o : etuds.getChartData()) {
            chartSeries.setName(o[0].toString());
            chartSeries.getData().add(new XYChart.Data(o[0].toString(), Integer.parseInt(o[1].toString())));
        }

        mChart.getData().addAll(chartSeries);

    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        setChart();
        setCountsHome();

        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                Stage mStage = ((Stage) ((Node) mTopBar).getScene().getWindow());
                EffectUtilities.makeDraggable(mStage, mTopBar);
            }
        });

        exitBarBtn.setOnMousePressed(e -> {
            System.exit(0);
        });

        maxBarBtn.setOnMousePressed(e -> {
            if (((Stage) ((Node) e.getSource()).getScene().getWindow()).isMaximized()) {
                ((Stage) ((Node) e.getSource()).getScene().getWindow()).setMaximized(false);
            } else {
                ((Stage) ((Node) e.getSource()).getScene().getWindow()).setMaximized(true);

            }
        });

        minBarBtn.setOnMousePressed(e -> {
            ((Stage) ((Node) e.getSource()).getScene().getWindow()).setIconified(true);
        });

        profilTicket.setOnMousePressed(e -> {
            try {
                headerText.setText("الوظائف");
                VBox v = FXMLLoader.load(getClass().getResource("/vue/ProfilVue.fxml"));
                mBorder.setCenter(v);
            } catch (IOException ex) {
                Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, ex);
            }
        });

        employeTicket.setOnMousePressed(e -> {
            try {
                headerText.setText("الموظفون");
                VBox v = FXMLLoader.load(getClass().getResource("/vue/EmployeVue.fxml"));
                mBorder.setCenter(v);
            } catch (IOException ex) {
                Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, ex);
            }
        });

        etudiantTicket.setOnMousePressed(e -> {
            try {
                headerText.setText("التلاميذ");
                VBox v = FXMLLoader.load(getClass().getResource("/vue/EtudiantVue.fxml"));
                mBorder.setCenter(v);
            } catch (IOException ex) {
                Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, ex);
            }
        });

        etablissementTicket.setOnMousePressed(e -> {
            try {
                headerText.setText("المؤسسات");
                VBox v = FXMLLoader.load(getClass().getResource("/vue/EtablissementVue.fxml"));
                mBorder.setCenter(v);
            } catch (IOException ex) {
                Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, ex);
            }
        });

        Preferences userPreferences = Preferences.userRoot();
        int currentUserId = userPreferences.getInt("currentUserId", 0);

        Employe currentEmploye = es.findById(currentUserId);
        userName.setText(currentEmploye.getPrenom() + " " + currentEmploye.getNom());
        userEmail.setText(currentEmploye.getEmail());

        logOutBtn.setOnAction(e -> {
            try {
                
                Stage window = new Stage();
                window.initModality(Modality.APPLICATION_MODAL);
                window.initStyle(StageStyle.UNDECORATED);
                window.getIcons().add(new Image(this.getClass().getResource("/images/loginLogo.png").toString()));


                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/vue/ConfirmBoxVue.fxml"));
                Parent root = (Parent) fxmlLoader.load();
                ConfirmBoxController controller = fxmlLoader.<ConfirmBoxController>getController();
                controller.setmMessage("هل تريد تسجيل الخروج ؟");
                controller.setmTitle("تأكيد");
                

                Scene scene = new Scene(root);

                window.setScene(scene);
                window.showAndWait();
                
                if(controller.getCurrentState()){
                    userPreferences.clear();
                    
                    Stage stage = new Stage();
                    stage.initStyle(StageStyle.TRANSPARENT);
                    stage.getIcons().add(new Image(this.getClass().getResource("/images/loginLogo.png").toString())); 
                    Parent root1 = FXMLLoader.load(getClass().getResource("/vue/LoginVue.fxml"));

                    Scene scene1 = new Scene(root1);
                    scene1.setFill(Color.TRANSPARENT);
                    stage.setScene(scene1);
                    stage.show();
                    
                    ((Stage)logOutBtn.getScene().getWindow()).close();
                }

            } catch (IOException ex) {
                Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, ex);
            } catch (BackingStoreException ex) {
                Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
    }

}
