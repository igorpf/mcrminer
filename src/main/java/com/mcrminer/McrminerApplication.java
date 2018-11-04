package com.mcrminer;

import com.mcrminer.ui.AbstractJavaFxApplicationSupport;
import javafx.application.Preloader;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Lazy;

import java.util.ResourceBundle;
import java.util.Locale;

@Lazy
@SpringBootApplication
public class McrminerApplication extends AbstractJavaFxApplicationSupport {

	@Value("${app.ui.title:MCRMiner}")
	private String windowTitle;

	public static void main(String[] args) {
		launchApp(McrminerApplication.class, args);
	}

    @Override
	public void start(Stage stage) throws Exception {
		notifyPreloader(new Preloader.StateChangeNotification(Preloader.StateChangeNotification.Type.BEFORE_START));
		stage.setTitle(windowTitle);
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(getClass().getClassLoader().getResource("views/mainWindow.fxml"));
		loader.setControllerFactory(clazz -> getApplicationContext().getBean(clazz));
		loader.setResources(ResourceBundle.getBundle("bundles.messages",  Locale.ENGLISH));
		Scene scene = new Scene(loader.load());
		scene.getStylesheets().add(getClass().getClassLoader().getResource("css/mainWindow.css").toExternalForm());
		stage.setScene(scene);
		stage.setResizable(true);
		stage.centerOnScreen();
		stage.show();
	}
}
