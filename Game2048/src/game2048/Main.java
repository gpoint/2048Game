/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package game2048;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Scanner;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javax.swing.filechooser.FileSystemView;

/**
 *
 * @author harry
 */
public class Main extends Application {

    /**
     * k
     */
    private static Stage stage;
    private static Scene scene;
    protected static final Dimension D = Toolkit.getDefaultToolkit().getScreenSize().getSize();
    protected static int highScore = 0, userScore = 0, size[] = {(int) D.getHeight(), (int) D.getWidth()};

    /**
     * @return the stage
     */
    public static Stage getStage() {
        return stage;
    }

    /**
     * @param aStage the stage to set
     */
    public static void setStage(Stage aStage) {
        stage = aStage;
    }

    /**
     * @return the scene
     */
    public static Scene getScene() {
        return scene;
    }

    /**
     * @param aScene the scene to set
     */
    public static void setScene(Scene aScene) {
        scene = aScene;
    }


    @Override
    public void start(Stage stag) throws Exception {
        try {
            String scoreDir = FileSystemView.getFileSystemView().getHomeDirectory().getAbsoluteFile() + "/.game2048";
            String scoreFile = FileSystemView.getFileSystemView().getHomeDirectory().getAbsoluteFile() + "/.scores";
            try{
                new File(scoreDir).mkdirs();
                new File(scoreFile).createNewFile();
            }catch(Exception ex){
                ex.printStackTrace();
            }
            Scanner scn = new Scanner(new FileInputStream(scoreFile));

            {
                if (scn.hasNext()) {
                    userScore = Integer.parseInt(scn.nextLine().split(":")[1].trim());
                }
                if (scn.hasNext()) {
                    highScore = Integer.parseInt(scn.nextLine().split(":")[1].trim());
                }
            }
            setStage(stag);
            Parent root = FXMLLoader.load(getClass().getResource("game.fxml"));
            setScene(new Scene(root, 585, 600));
            getStage().getIcons().add(new Image(getClass().getResource("d.png").toExternalForm()));
            getStage().setScene(getScene());
            getStage().setMinHeight(585);
            getStage().setMinWidth(600);
            getStage().show();
            getStage().setHeight(585);
            getStage().setWidth(600);

            getStage().setOnCloseRequest((evt) -> {
                try {
                    new FileOutputStream(scoreFile).write((System.getProperty("user.name") + "'s Score : " + userScore + "\nHigh Score : " + highScore).getBytes());
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            });
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

}
