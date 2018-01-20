/*
 * Michael Pu
 * TetrisGameAI - SaveOrganismDialog
 * ICS3U1 - Mr.Radulovic
 * January 13, 2018
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

/**
 * Dialog that opens to select a file to save an {@link Organism} to.
 */
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

        // set up display properties
        VBox vBox = new VBox();
        vBox.setPrefWidth(400);
        vBox.setFillWidth(true);
        vBox.setSpacing(DEFAULT_PADDING);
        vBox.setPadding(new Insets(DEFAULT_PADDING));
        vBox.setAlignment(Pos.CENTER_LEFT);

        // label text
        Text label = new Text("Select a folder to store the organism.");
        vBox.getChildren().add(label);

        // status text
        mErrorLabel = new Text();
        vBox.getChildren().add(mErrorLabel);

        // file name entry field
        HBox hBox = new HBox(10);
        hBox.setAlignment(Pos.CENTER_LEFT);
        vBox.getChildren().add(hBox);

        Label nameLabel = new Label("Name: ");
        hBox.getChildren().add(nameLabel);

        mNameField = new TextField();
        mNameField.setPrefWidth(300);
        mNameField.setText(saveOrganism.getmName());
        hBox.getChildren().add(mNameField);

        // buttons
        HBox buttonBox = new HBox(10);
        buttonBox.setAlignment(Pos.CENTER_LEFT);
        vBox.getChildren().add(buttonBox);

        Button selectFolderButton = new Button("Choose Folder");
        buttonBox.getChildren().add(selectFolderButton);

        mSaveButton = new Button("Save");
        mSaveButton.setDisable(true);
        buttonBox.getChildren().add(mSaveButton);

        // set up directory chooser
        File defaultDir = new File(System.getProperty("user.home"));
        DirectoryChooser directoryChooser = new DirectoryChooser();
        directoryChooser.setTitle("Select a folder to save the organism");
        directoryChooser.setInitialDirectory(defaultDir);

        // set default state
        setState(false, "No folder selected.");

        final Stage fileChooseParentStage = this;

        // open up directory chooser when the select button is pressed
        selectFolderButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                mSaveFolder = directoryChooser.showDialog(fileChooseParentStage);
                createSaveFile();
            }
        });

        // write the organism to the file when the save button is pressed
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

        // update the save file when the name of the file is changed
        mNameField.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                createSaveFile();
            }
        });

        // set up display properties of the window
        setTitle("Save an Organism");
        setScene(new Scene(vBox));
        setResizable(false);
        initOwner(parent);
        initModality(Modality.WINDOW_MODAL);

        // block input to the parent window until this window is closed
        showAndWait();
    }

    /**
     * Creates a file to save the organism in and updates the status of the window accordingly.
     */
    private void createSaveFile() {
        String fileName = convertToFileName(mNameField.getText());
        if (mSaveFolder != null) {

            // if a folder to save the file in has been selected
            mSaveOrganism.setmName(mNameField.getText());

            // create save file
            mSaveFile = new File(mSaveFolder, fileName + ".org.ser");

            // update the state of the window
            try {
                setState(true, mSaveFile.getCanonicalPath());
            } catch (IOException e) {
                setState(false, "Invalid folder selected.");
            }

        }
    }

    /**
     * Updates the state of the window, changing the text and colour of the status label as well as updating the button
     * states.
     *
     * @param ready <code>true</code> if the save file is ready, <code>false</code> otherwise.
     * @param text Text to display in the status label.
     */
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

    /**
     * Converts a string into a file name by replaces all spaces in the string with underscores.
     *
     * @param name Original name of the file.
     * @return Formatted name of the file.
     */
    private String convertToFileName(String name) {
        return name.replace(" ", "_");
    }
}
