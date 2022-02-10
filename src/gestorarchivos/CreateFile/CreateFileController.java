/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gestorarchivos.CreateFile;

import gestorarchivos.GestorArchivos;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.*;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

/**
 * FXML Controller class
 *
 * @author alumno
 */
public class CreateFileController implements Initializable {

    public static boolean fileCheck;
    /**
     * Initializes the controller class.
     */
    @FXML
    private Button cancel, create;

    @FXML
    private TextField txt;

    @FXML
    public Label lblTitle;

    @FXML
    public void create_OnAction() throws IOException {
        //darle nombre al archivo o directorio
        Text fileName = new Text();
        fileName.setText(fileCheck == true ? txt.getText() + ".txt" : txt.getText());
        //darle un icono al fichero
        ImageView iconView = new ImageView();
        iconView.setFitHeight(90);
        iconView.setFitWidth(90);
        Image icon = new Image(GestorArchivos.class.getResource(fileCheck == true ? "Resources/file.png" : "Resources/directory.png").toString());
        iconView.setImage(icon);
        //introducir la imagen y el nombre en un borderpane
        BorderPane borderpane = new BorderPane();
        borderpane.setCenter(iconView);
        BorderPane.setAlignment(iconView, Pos.CENTER);
        borderpane.setBottom(fileName);
        BorderPane.setAlignment(fileName, Pos.CENTER);
        //insertar el borderpane en una lista
        CreateFileAux.lista.add(borderpane);

        //crear el fichero
        if (fileCheck == true) {
            //crear fichero de texto
            PrintWriter writer = new PrintWriter("FILES/" + fileName.getText(), "UTF-8");
            writer.write(CreateFileAux.texto == null ? "" : CreateFileAux.texto);
            writer.close();
        } else {
            //crear directorio
            File file = new File("FILES/" + fileName.getText());
            file.mkdir();
        }
        Stage stage = (Stage) create.getScene().getWindow();
        stage.fireEvent(new WindowEvent(stage, WindowEvent.WINDOW_CLOSE_REQUEST));
    }

    //cerrar ventana
    @FXML
    public void cancel_OnAction() {
        Stage stage = (Stage) cancel.getScene().getWindow();
        stage.close();
    }

    //desbloquear botón create
    @FXML
    public void txt_OnKeyPressed() {
        create.setDisable(false);
    }

    //restringir la entrada de texto a solo letras
    @FXML
    public void txtOnKeyTyped(KeyEvent event) {

        if (!event.getCharacter().matches("[a-z, A-Z, ñ, Ñ]")) {
            event.consume();
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        lblTitle.setText(CreateFileAux.titulo.getText());
    }
}
