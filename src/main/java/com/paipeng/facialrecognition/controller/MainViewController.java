package com.paipeng.facialrecognition.controller;

import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URL;
import java.util.Locale;
import java.util.Objects;
import java.util.ResourceBundle;

public class MainViewController implements Initializable {
    public static Logger logger = LoggerFactory.getLogger(MainViewController.class);
    public static final String FXML_FILE = "/fxml/MainViewController.fxml";

    private static Stage stage;
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        logger.info("initialize");
    }


    public static void start() {
        logger.info("start");

        try {
            ResourceBundle resources = ResourceBundle.getBundle("bundles.languages", getCurrentLanguageLocale());
            Parent root = FXMLLoader.load(Objects.requireNonNull(MainViewController.class.getResource(FXML_FILE)), resources);

            Scene scene = new Scene(root);
            stage = new Stage();
            stage.setTitle(resources.getString("app_title"));
            stage.setScene(scene);
            stage.centerOnScreen();
            //stage.getIcons().add(new Image(Objects.requireNonNull(BatchImageResizeViewController.class.getResourceAsStream("/images/idcard-logo.png"))));
            stage.setResizable(true);
            stage.show();


        } catch (Exception ex) {
            logger.error(ex.getMessage());
        }
    }


    public static Locale getCurrentLanguageLocale() {
        Locale locale;
        if (true) {
            locale = new Locale("zh", "Zh");
        } else {
            locale = new Locale("en", "En");
        }
        return locale;
    }

    @SuppressWarnings("unused")
    public static ResourceBundle getStringResource() {
        return ResourceBundle.getBundle("bundles.languages", getCurrentLanguageLocale());
    }
}
