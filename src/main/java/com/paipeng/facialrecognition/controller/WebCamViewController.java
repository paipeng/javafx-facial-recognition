package com.paipeng.facialrecognition.controller;

import com.paipeng.facialrecognition.utils.CameraUtil;
import com.paipeng.facialrecognition.utils.CommonUtil;
import javafx.application.Platform;
import javafx.embed.swing.SwingFXUtils;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.awt.image.BufferedImage;
import java.net.URL;
import java.util.Locale;
import java.util.Objects;
import java.util.ResourceBundle;

public class WebCamViewController implements Initializable {
    public static Logger logger = LoggerFactory.getLogger(WebCamViewController.class);
    private static Stage stage;
    private static final String FXML_FILE = "/fxml/WebCamViewController.fxml";

    @FXML
    private ImageView previewImageView;

    @FXML
    private Button openButton;
    @FXML
    private Button captureButton;

    @FXML
    private TextField fpsTextField;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        previewImageView.setImage(new Image(Objects.requireNonNull(WebCamViewController.class.getResourceAsStream("/images/logo.png"))));
        openButton.setOnMouseClicked(event -> {
            logger.trace("openButton clicked");

            if (CameraUtil.getInstance().isWebcamOpened()) {
                stopCamera();
            } else {
                if (CameraUtil.getInstance().hasWebCam()) {
                    startCamera();
                } else {
                    logger.error("no camera found!");
                }
            }
        });

        CameraUtil.getInstance().setCameraUtilInterface(new CameraUtil.CameraUtilInterface() {
            @Override
            public void webcamOpened() {
                logger.trace("webcamOpened");
                Platform.runLater(() -> openButton.setText(CommonUtil.getString("close_webcam")));
            }

            @Override
            public void webcamClosed() {
                logger.trace("webcamClosed");
                Platform.runLater(() -> openButton.setText(CommonUtil.getString("open_webcam")));
                previewImageView.setImage(new Image(Objects.requireNonNull(WebCamViewController.class.getResourceAsStream("/images/logo.png"))));
            }

            @Override
            public void updateImage(BufferedImage bufferedImage, double fps) {
                if (bufferedImage != null) {
                    previewImageView.setImage(SwingFXUtils.toFXImage(bufferedImage, null));
                }
            }
        });
    }

    public static void start() {
        logger.trace("start");
        try {
            ResourceBundle resources = ResourceBundle.getBundle("bundles.languages", new Locale("zh", "Zh"));
            Parent root = FXMLLoader.load(Objects.requireNonNull(WebCamViewController.class.getResource(FXML_FILE)), resources);

            Scene scene = new Scene(root);
            stage = new Stage();
            //stage.initStyle(StageStyle.UNDECORATED);

            stage.setTitle(resources.getString("app_title"));

            stage.getIcons().add(new Image(Objects.requireNonNull(WebCamViewController.class.getResourceAsStream("/images/logo.png"))));
            stage.setScene(scene);
            stage.centerOnScreen();
            stage.setResizable(true);
            stage.show();

        } catch (Exception ex) {
            logger.error(ex.getMessage());
        }
    }

    public void startCamera() {
        CameraUtil.getInstance().start();
    }

    public void stopCamera() {
        CameraUtil.getInstance().stop();
    }
}
