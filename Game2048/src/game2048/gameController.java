/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package game2048;

import java.awt.Desktop;
import java.io.FileOutputStream;
import java.net.URI;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckMenuItem;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;

/**
 *
 * @author harry
 */
public class gameController implements Initializable {

    private FileOutputStream diff;

    @FXML
    private WebView webv;
    private WebEngine webe;

    @FXML
    private Button yscore, hscore, help;

    @FXML
    private CheckMenuItem easy, medi, hard;

    private void setDifficulty(int level) {
        try {
            webe.executeScript("level = " + level);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @FXML
    private void hscore() {
        try {

        } catch (Exception ex) {

        }
    }

    @FXML
    private void getScore() {
        try {
            String score = webe.getDocument().getElementById("cscore").getTextContent();
            if (score.contains("+")) {
                score = score.substring(0, score.indexOf("+"));
            }
            Main.userScore = Integer.parseInt(score);
            yscore.setText("Your Score : " + score);
            if (Main.userScore > Main.highScore) {
                hscore.setText("High Score : " + (Main.highScore = Main.userScore));
            }
        } catch (Exception ex) {
        }
    }

    @FXML
    private void help() {
        new Thread() {
            public void run() {
                try {
                    Desktop dm = Desktop.getDesktop();
                    dm.browse(new URI("https://games.chibuezeharry.xyz/2048game"));
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        }.start();

    }

    @FXML
    private void restart() {
        webe.executeScript("var evt = document.createEvent(\"MouseEvents\");\n"
                + "                evt.initMouseEvent(\"click\", true, true, window, 1, 0, 0, 0, 0,\n"
                + "                    false, false, false, false, 0, null);\n"
                + "\n"
                + "                var cb = document.getElementById(\"restart\");\n"
                + "                cb.dispatchEvent(evt);");
        yscore.setText("Your Score : 0");
        Main.userScore = 0;
    }

    @Override
    public void initialize(java.net.URL url, java.util.ResourceBundle rb) {
        try {
            webe = webv.getEngine();
            webv.setContextMenuEnabled(false);
            webe.load(getClass().getResource("game.html").toExternalForm());

            Main.getStage().setTitle("2048 Game _ ( " + System.getProperty("user.name").toUpperCase() + " )");
        } catch (Exception ex) {
        }
        hscore.setText("High Score : " + Main.highScore);
        yscore.setText("Your Score : " + Main.userScore);

        easy.setOnAction((evt) -> {
            easy.setSelected(true);
            medi.setSelected(false);
            hard.setSelected(false);
            setDifficulty(1024);
        });
        medi.setOnAction((evt) -> {
            easy.setSelected(false);
            medi.setSelected(true);
            hard.setSelected(false);
            setDifficulty(2048);
        });
        hard.setOnAction((evt) -> {
            easy.setSelected(false);
            medi.setSelected(false);
            hard.setSelected(true);
            setDifficulty(4096);
        });

    }

}
