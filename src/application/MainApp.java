package application;

import java.io.IOException;
import java.util.List;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

public class MainApp extends Application {
	private Stage primaryStage;
	private BorderPane rootLayout;

	@Override
	public void start(Stage primaryStage) {

		this.primaryStage = primaryStage;

		this.primaryStage.setTitle("TextProApp");

		try {
			FXMLLoader loader = new FXMLLoader(
					MainApp.class.getResource("view/RootLayout.fxml"));
			rootLayout = (BorderPane) loader.load();
			Scene scene = new Scene(rootLayout);
			primaryStage.setScene(scene);

			primaryStage.setMinHeight(430);
			primaryStage.setMinWidth(334);
			primaryStage.show();

		} catch (Exception e) {
			e.printStackTrace();
		}

		showTextProApp();
	}

	/**
	 * Shows the main TextApplication scene
	 */
	public void showTextProApp() {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource(
					"view/TextAppLayout.fxml"));

			HBox textProPage = (HBox) loader.load();
			rootLayout.setCenter(textProPage);

			TextProController controller = loader.getController();
			controller.setMainApp(this);

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	// SHOW NEW STAGE METHODS

	/**
	 * Shows dialog for user input error
	 * 
	 * @param inErr
	 *            - message to dispaly
	 */
	public void showInputErrorDialog(String inErr) {
		Alert alert = new Alert(AlertType.ERROR);
		alert.setTitle("Error");
		alert.setHeaderText("Input Error");
		alert.setContentText(inErr);

		alert.showAndWait();
	}

	/**
	 * Displays dialog that allows user to select local text file to display in
	 * TextArea
	 * 
	 * @param ta
	 *            - reference to TextArea to display loaded text file
	 * 
	 */
	public void showLoadFileDialog(AutoSpellingTextArea ta) {
		try {
			FXMLLoader loader = new FXMLLoader(
					MainApp.class.getResource("view/LoadFileLayout.fxml"));
			VBox page = (VBox) loader.load();
			Stage dialogStage = new Stage();
			dialogStage.setTitle("Load File");
			dialogStage.initModality(Modality.WINDOW_MODAL);
			dialogStage.initOwner(primaryStage);
			Scene scene = new Scene(page);
			dialogStage.setScene(scene);

			LoadFileDialogController controller = loader.getController();
			controller.setDialogStage(dialogStage);
			controller.setTextArea(ta);

			dialogStage.showAndWait();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void showEditDistanceDialog(String selectedText) {
		try {
			FXMLLoader loader = new FXMLLoader(
					MainApp.class.getResource("view/EditDistanceLayout.fxml"));
			VBox page = (VBox) loader.load();
			Stage dialogStage = new Stage();
			dialogStage.setTitle("Calculate Edit Distance");
			dialogStage.initModality(Modality.WINDOW_MODAL);
			dialogStage.initOwner(primaryStage);
			Scene scene = new Scene(page);
			dialogStage.setScene(scene);

			EditDistanceDialogController controller = loader.getController();
			controller.setDialogStage(dialogStage);
			controller.setMainApp(this);
			controller.setField(selectedText);
			dialogStage.showAndWait();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void showEDResult(List<String> path) {
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("Result");
		alert.setHeaderText("Word Path : ");
		alert.initModality(Modality.NONE);
		alert.setResizable(true);

		VBox box = new VBox();
		HBox midBox = new HBox();
		box.setPadding(new Insets(35, 0, 35, 0));
		box.setSpacing(35);
		midBox.setSpacing(15);

		Label pathLabel = new Label();
		Label numStepsLabel = new Label("Number of steps : ");
		Label numSteps = new Label();
		Font font = new Font(14);
		pathLabel.setFont(font);
		numStepsLabel.setFont(font);
		numSteps.setFont(Font.font(font.getFamily(), FontWeight.BOLD, 14));

		midBox.getChildren().add(numStepsLabel);
		midBox.getChildren().add(numSteps);
		midBox.setAlignment(Pos.CENTER);

		box.getChildren().add(pathLabel);
		box.getChildren().add(midBox);
		box.setAlignment(Pos.CENTER);
		alert.getDialogPane().setPrefWidth(300);

		if (path != null) {
			numSteps.setText(Integer.toString(path.size() - 1));
			pathLabel.setText(String.join(" -> ", path));

			Text text = new Text(pathLabel.getText());
			text.setFont(font);
			if (text.getLayoutBounds().getWidth() > 200) {
				alert.getDialogPane().setPrefWidth(
						text.getLayoutBounds().getWidth() + 100);
			}

		} else {
			pathLabel.setText("No Path Found.");
			numSteps.setText("N/A");
		}
		alert.getDialogPane().setContent(box);
		alert.getDialogPane()
				.getStylesheets()
				.add(getClass().getResource("application.css").toExternalForm());
		alert.getDialogPane().getStyleClass().add("myDialog");
		alert.showAndWait();
	}

	public void showMarkovDialog(textgen.MarkovTextGenerator mtg) {
		try {
			FXMLLoader loader = new FXMLLoader(
					MainApp.class.getResource("view/MarkovLayout.fxml"));
			BorderPane page = (BorderPane) loader.load();
			Stage dialogStage = new Stage();
			dialogStage.setTitle("Markov Text Generator");
			dialogStage.initOwner(primaryStage);
			Scene scene = new Scene(page);
			dialogStage.setScene(scene);

			MarkovController controller = loader.getController();
			controller.setMainApp(this);
			controller.setMTG(mtg);
			dialogStage.showAndWait();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void showLoadStage(Stage loadStage, String text) {
		loadStage.initModality(Modality.APPLICATION_MODAL);
		loadStage.initOwner(primaryStage);
		VBox loadVBox = new VBox(20);
		loadVBox.setAlignment(Pos.CENTER);
		Text tNode = new Text(text);
		tNode.setFont(new Font(16));
		loadVBox.getChildren().add(new HBox());
		loadVBox.getChildren().add(tNode);
		loadVBox.getChildren().add(new HBox());
		Scene loadScene = new Scene(loadVBox, 300, 200);
		loadStage.setScene(loadScene);
		loadStage.show();
	}

	public static void main(String[] args) {
		launch(args);
	}

	public Stage getStage() {
		return this.primaryStage;
	}
}
