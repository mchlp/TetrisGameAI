/*
 * Michael Pu
 * TetrisGameAI - SaveOrganismDialog
 * ICS3U1 - Mr. Radulovic
 * December 28, 2017
 */

package frontend.base;

import ai.Organism;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;

public class SaveOrganismDialog extends Stage {

    private static final int DEFAULT_PADDING = 10;

    private static final Color GOOD_TEXT_COLOUR = Color.GREEN;
    private static final Color BAD_TEXT_COLOUR = Color.RED;

    private File mSaveFile;

    public SaveOrganismDialog(Stage parent, Organism saveOrganism) {

        VBox vBox = new VBox();
        vBox.setPrefWidth(400);
        vBox.setFillWidth(true);
        vBox.setSpacing(DEFAULT_PADDING);
        vBox.setPadding(new Insets(DEFAULT_PADDING));
        vBox.setAlignment(Pos.CENTER_LEFT);

        Text label = new Text("Select a file to store the organism.");
        vBox.getChildren().add(label);

        Text errorLabel = new Text("No save file has been selected.");
        errorLabel.setWrappingWidth(350);
        errorLabel.setFill(BAD_TEXT_COLOUR);
        vBox.getChildren().add(errorLabel);

        HBox hBox = new HBox(10);
        hBox.setAlignment(Pos.CENTER_LEFT);
        vBox.getChildren().add(hBox);

        Label nameLabel = new Label("Name: ");
        hBox.getChildren().add(nameLabel);

        TextField nameField = new TextField();
        nameField.setPrefWidth(300);
        nameField.setText(saveOrganism.getmName());
        hBox.getChildren().add(nameField);

        HBox buttonBox = new HBox(10);
        buttonBox.setAlignment(Pos.CENTER_LEFT);
        vBox.getChildren().add(buttonBox);

        Button selectFileButton = new Button("Choose File");
        buttonBox.getChildren().add(selectFileButton);

        Button saveButton = new Button("Save");
        saveButton.setDisable(true);
        buttonBox.getChildren().add(saveButton);

        File defaultDir = new File(System.getProperty("user.home"));
        FileChooser.ExtensionFilter organismFileFilter = new FileChooser.ExtensionFilter("Organism files (*.org.ser)", "*.org.ser");
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select an file to save organism");
        fileChooser.setInitialDirectory(defaultDir);
        fileChooser.getExtensionFilters().add(organismFileFilter);

        selectFileButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                String fileName = convertToFileName(nameField.getText());
                fileChooser.setInitialFileName(fileName);
                mSaveFile = fileChooser.showSaveDialog(parent);
                if (mSaveFile != null) {
                    try {
                        saveOrganism.setmName(nameField.getText());
                        if(!mSaveFile.getName().endsWith(".org.ser")) {
                            mSaveFile = new File(mSaveFile.getCanonicalPath() + ".org.ser");
                        }
                        errorLabel.setText(mSaveFile.getName());
                        errorLabel.setFill(GOOD_TEXT_COLOUR);
                        saveButton.setDisable(false);
                    } catch (IOException e) {
                        errorLabel.setText("Invalid file selected.");
                        e.printStackTrace();
                    }
                }
            }
        });

        saveButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                saveOrganism.saveToFile(mSaveFile);
                hide();
            }
        });

        setScene(new Scene(vBox));
        setResizable(false);
        initOwner(parent);
        initModality(Modality.WINDOW_MODAL);
        show();
    }

    private String convertToFileName(String name) {
        return name.replace(" ", "_");
    }
}
