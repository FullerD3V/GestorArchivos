/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gestorarchivos.files;

/*
 *
 */
import gestorarchivos.CreateFile.CreateFileAux;
import gestorarchivos.CreateFile.CreateFileController;
import gestorarchivos.GestorArchivos;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.ContextMenuEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.TilePane;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

/**
 *
 * @author alumno
 *
 *
 */
public class FilesController implements Initializable {

    @FXML
    private TilePane tpane;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        CreateFileAux.lista.clear();
        File f = new File("FILES");
        File[] files = f.listFiles();
        for (int i = 0; i < files.length; i++) {
            Text fileName = new Text();
            fileName.setText(files[i].getName());
            //darle un icono al fichero
            ImageView iconView = new ImageView();
            iconView.setFitHeight(90);
            iconView.setFitWidth(90);
            Image icon = new Image(GestorArchivos.class.getResource(files[i].isFile() == true ? "Resources/file.png" : "Resources/directory.png").toString());
            iconView.setImage(icon);
            String s = files[i].getName().substring(files[i].getName().length() - 3); //comprobar que el fichero es un documento de texto
            //introducir la imagen y el nombre en un borderpane
            BorderPane borderpane = new BorderPane();
            borderpane.setCenter(iconView);
            BorderPane.setAlignment(iconView, Pos.CENTER);
            borderpane.setBottom(fileName);
            BorderPane.setAlignment(fileName, Pos.CENTER);
            borderpane.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    if (event.getClickCount() == 2) {
                        CreateFileAux.ruta += "/" + fileName.getText();
                        //Abrir archivo de texto
                        if (s.equals("txt")) {
                            try {
                                Scanner input = new Scanner(new File(CreateFileAux.ruta));
                                while (input.hasNextLine()) {
                                    String line = input.nextLine();
                                    CreateFileAux.texto += line;
                                    CreateFileAux.texto += "\n";
                                }
                                input.close();
                            } catch (Exception ex) {
                                ex.printStackTrace();
                            }

                            Parent root = null;
                            try {
                                root = FXMLLoader.load(GestorArchivos.class.getResource("Main/MainFXML.fxml"));
                            } catch (IOException ex) {
                                Logger.getLogger(FilesController.class.getName()).log(Level.SEVERE, null, ex);
                            }
                            Stage stage = new Stage();
                            Scene scene = new Scene(root);
                            stage.setTitle(CreateFileAux.ruta);
                            stage.setScene(scene);
                            stage.show();

                            Stage currentStage = (Stage) tpane.getScene().getWindow();
                            currentStage.close();
                        } else {
                            // abrir directorio
                            Parent root = null;
                            try {
                                root = FXMLLoader.load(GestorArchivos.class.getResource("files/FilesFXML.fxml"));
                            } catch (IOException ex) {
                                Logger.getLogger(FilesController.class.getName()).log(Level.SEVERE, null, ex);
                            }
                            Stage stage = new Stage();
                            Scene scene = new Scene(root);
                            stage.setTitle(CreateFileAux.ruta);
                            stage.setScene(scene);
                            stage.show();

                            Stage currentStage = (Stage) tpane.getScene().getWindow();
                            currentStage.close();
                        }
                    }
                    if (event.getClickCount() == 1) {
                        ContextMenu menuDel = new ContextMenu();
                        MenuItem del = new MenuItem("Delete");
                        menuDel.getItems().addAll(del);
                        borderpane.setOnContextMenuRequested((ContextMenuEvent e) -> {
                            menuDel.show(borderpane, e.getScreenX(), e.getScreenY());
                        });
                    }
                }
            });
            CreateFileAux.lista.add(borderpane);
            CreateFileAux.texto = "";
        }

        tpane.getChildren().addAll(CreateFileAux.lista);
        ContextMenu contextmenu = new ContextMenu();
        MenuItem crearF = new MenuItem("Create file");
        MenuItem crearD = new MenuItem("Create directory");
        contextmenu.getItems().addAll(crearF, crearD);
        tpane.setOnContextMenuRequested((ContextMenuEvent event) -> {
            contextmenu.show(tpane, event.getScreenX(), event.getScreenY());
        });

        //Esconder contextMenu con un click
        tpane.setOnMousePressed((event) -> {
            if (contextmenu.isShowing()) {
                contextmenu.hide();
            }
        });

        /*
======================================================================================================================================================
        crear directorio
======================================================================================================================================================
         */
        crearD.setOnAction(event -> {
            CreateFileController.fileCheck = false;
            CreateFileAux.titulo.setText("Create directory");
            Stage stage = new Stage();
            Parent root = null;
            try {
                root = FXMLLoader.load(GestorArchivos.class.getResource("CreateFile/CreateFile.fxml"));
            } catch (IOException ex) {
                Logger.getLogger(FilesController.class.getName()).log(Level.SEVERE, null, ex);
            };

            Scene scene = new Scene(root);
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setTitle("New Directory");

            stage.setScene(scene);
            stage.setOnCloseRequest((WindowEvent e) -> {
                tpane.getChildren().clear();
                tpane.getChildren().addAll(CreateFileAux.lista);
                //System.exit(0);
            });
            stage.show();
        });
        /*
======================================================================================================================================================
        crear fichero
======================================================================================================================================================
         */
        crearF.setOnAction(event -> {
            CreateFileController.fileCheck = true;
            CreateFileAux.titulo.setText("Create file");
            Stage stage = new Stage();
            Parent root = null;
            try {
                root = FXMLLoader.load(GestorArchivos.class.getResource("CreateFile/CreateFile.fxml"));
            } catch (IOException ex) {
                Logger.getLogger(FilesController.class.getName()).log(Level.SEVERE, null, ex);
            };

            Scene scene = new Scene(root);
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setTitle("New File");

            stage.setScene(scene);
            stage.setOnCloseRequest((WindowEvent e) -> {
                tpane.getChildren().clear();
                tpane.getChildren().addAll(CreateFileAux.lista);
                //System.exit(0);
            });

            stage.show();
        });
    }
}
