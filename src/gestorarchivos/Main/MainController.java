/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gestorarchivos.Main;

import gestorarchivos.CreateFile.CreateFileAux;
import gestorarchivos.CreateFile.CreateFileController;
import gestorarchivos.GestorArchivos;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextArea;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 *
 * @author alumno
 *
 * Ventana principal
 */
public class MainController implements Initializable {

    @FXML
    public TextArea txtArea;

    @FXML
    public void aboutOnAction(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(GestorArchivos.class.getResource("InfoAbout/Information.fxml"));

        Stage stage = new Stage();
        Scene scene = new Scene(root);
        stage.setTitle("About");
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    public void openOnAction(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(GestorArchivos.class.getResource("files/FilesFXML.fxml"));
        Stage stage = new Stage();
        Scene scene = new Scene(root);
        stage.initModality(Modality.APPLICATION_MODAL);
        File f = new File("FILES");
        CreateFileAux.ruta = f.getPath();
        stage.setTitle(CreateFileAux.ruta);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    public void saveOnAction(ActionEvent event) throws IOException {
        CreateFileAux.titulo.setText("Create file");
        CreateFileAux.texto = txtArea.getText();
        CreateFileController.fileCheck = true;
        Parent root = FXMLLoader.load(GestorArchivos.class.getResource("CreateFile/CreateFile.fxml"));
        Stage stage = new Stage();
        Scene scene = new Scene(root);
        stage.setTitle("Save file");
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    public void deleteOnAction(ActionEvent event) {
        Stage currentStage = (Stage) txtArea.getScene().getWindow();
        currentStage.setTitle("NEW FILE");
        txtArea.clear();
        File fichero = new File(CreateFileAux.ruta);
        fichero.delete();
    }

    @FXML
    public void newOnAction(ActionEvent event) throws IOException {
        Stage currentStage = (Stage) txtArea.getScene().getWindow();
        currentStage.setTitle("NEW FILE");
        txtArea.clear();
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        //TODO
        txtArea.setText(CreateFileAux.texto);
    }
}
