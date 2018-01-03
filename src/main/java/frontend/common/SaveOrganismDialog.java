/*
 * Michael Pu
 * TetrisGameAI - SaveOrganismDialog
 * ICS3U1 - Mr. Radulovic
 * December 29, 2017
 */

package frontend.common;

import ai.Organism;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.DirectoryChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;

public class SaveOrganismDialog extends Stage {

    private static final int DEFAULT_PADDING = 10;

    private static final Color GOOD_TEXT_COLOUR = Color.GREEN;
    private static final Color BAD_TEXT_COLOUR = Color.RED;

    private File mSaveFile;
    private File mSaveFolder;

    private Organism mSaveOrganism;

    private TextField mNameField;
    private Text mErrorLabel;
    private Button mSaveButton;

    public SaveOrganismDialog(Stage parent, Organism saveOrganism) {

        mSaveOrganism = saveOrganism;

        VBox vBox = new VBox();
        vBox.setPrefWidth(400);
        vBox.setFillWidth(true);
        vBox.setSpacing(DEFAULT_PADDING);
        vBox.setPadding(new Insets(DEFAULT_PADDING));
        vBox.setAlignment(Pos.CENTER_LEFT);

        Text label = new Text("Select a folder to store the organism.");
        vBox.getChildren().add(label);

        mErrorLabel = new Text();
        vBox.getChildren().add(mErrorLabel);

        HBox hBox = new HBox(10);
        hBox.setAlignment(Pos.CENTER_LEFT);
        vBox.getChildren().add(hBox);

        Label nameLabel = new Label("Name: ");
        hBox.getChildren().add(nameLabel);

        mNameField = new TextField();
        mNameField.setPrefWidth(300);
        mNameField.setText(saveOrganism.getmName());
        hBox.getChildren().add(mNameField);

        HBox buttonBox = new HBox(10);
        buttonBox.setAlignment(Pos.CENTER_LEFT);
        vBox.getChildren().add(buttonBox);

        Button selectFileButton = new Button("Choose Folder");
        buttonBox.getChildren().add(selectFileButton);

        mSaveButton = new Button("Save");
        mSaveButton.setDisable(true);
        buttonBox.getChildren().add(mSaveButton);

        File defaultDir = new File(System.getProperty("user.home"));
        DirectoryChooser directoryChooser = new DirectoryChooser();
        directoryChooser.setTitle("Select a folder to save the organism");
        directoryChooser.setInitialDirectory(defaultDir);

        setState(false, "No folder selected.");

        final Stage fileChooseParentStage = this;

        selectFileButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                mSaveFolder = directoryChooser.showDialog(fileChooseParentStage);
                createSaveFile();
            }
        });

        mSaveButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                try {
                    saveOrganism.writeToFile(mSaveFile);
                    hide();
                } catch (IOException e) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error writing organism to file.");
                    alert.setHeaderText("Error writing organism to file.");
                    alert.setContentText("The file specified may be read-only or no longer available. " +
                            "Try selecting another file to save the organism.");
                    alert.getDialogPane().setMinHeight(Region.USE_PREF_SIZE);
                    alert.showAndWait();
                }
            }
        });

        mNameField.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                createSaveFile();
            }
        });

        setTitle("Save an Organism");
        setScene(new Scene(vBox));
        setResizable(false);
        initOwner(parent);
        initModality(Modality.WINDOW_MODAL);
        showAndWait();
    }

    private void createSaveFile() {
        String fileName = convertToFileName(mNameField.getText());
        if (mSaveFolder != null) {
            mSaveOrganism.setmName(mNameField.getText());
            mSaveFile = new File(mSaveFolder, fileName + ".org.ser");
            try {
                setState(true, mSaveFile.getCanonicalPath());
            } catch (IOException e) {
                setState(false, "Invalid folder selected.");
            }

        }
    }

    private void setState(boolean ready, String text) {
        mErrorLabel.setText(text);
        if (ready) {
            mErrorLabel.setFill(GOOD_TEXT_COLOUR);
            mSaveButton.setDisable(false);
        } else {
            mErrorLabel.setFill(BAD_TEXT_COLOUR);
            mSaveButton.setDisable(true);
        }
    }

    private String convertToFileName(String name) {
        return name.replace(" ", "_");
    }
}
