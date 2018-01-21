/*
 * Michael Pu
 * TetrisGameAI - SelectOrganismDialog
 * ICS3U1 - Mr.Radulovic
 * January 20, 2018
 */

package frontend.menu;

import ai.Organism;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.io.InvalidClassException;

/**
 * Dialog that opens to select an {@link Organism} saved to a file and loads it.
 */
public class SelectOrganismDialog extends Stage {

    private static final int DEFAULT_PADDING = 10;

    private static final Color GOOD_TEXT_COLOUR = Color.GREEN;
    private static final Color BAD_TEXT_COLOUR = Color.RED;

    private Organism mLoadedOrganism;
    private Text mErrorLabel;
    private CheckBox mFastModeBox;

    public SelectOrganismDialog(Stage parent) {

        // set up display properties
        VBox vBox = new VBox();
        vBox.setPrefWidth(400);
        vBox.setFillWidth(true);
        vBox.setSpacing(DEFAULT_PADDING);
        vBox.setPadding(new Insets(DEFAULT_PADDING));
        vBox.setAlignment(Pos.CENTER_LEFT);

        // label text
        Text label = new Text("Select an AI Organism file to use.");
        vBox.getChildren().add(label);

        // checkbox for fast mode
        mFastModeBox = new CheckBox("AI Fast Mode");
        vBox.getChildren().add(mFastModeBox);

        // error label
        mErrorLabel = new Text();
        mErrorLabel.setWrappingWidth(370);
        vBox.getChildren().add(mErrorLabel);

        // button to select file
        Button selectFileButton = new Button("Choose File");
        vBox.getChildren().add(selectFileButton);

        // set up file chooser
        File defaultDir = new File(System.getProperty("user.home"));
        FileChooser.ExtensionFilter organismFileFilter = new FileChooser.ExtensionFilter("Organism files (*.org.ser)", "*.org.ser");
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select an AI Organism");
        fileChooser.setInitialDirectory(defaultDir);
        fileChooser.getExtensionFilters().add(organismFileFilter);

        // set initial state
        setState(false, "No file selected.");

        final Stage fileChooseParentStage = this;

        selectFileButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                File organismFile = fileChooser.showOpenDialog(fileChooseParentStage);
                if (organismFile != null) {
                    // if an organism file has been selected
                    try {
                        mLoadedOrganism = Organism.loadOrganismFromFile(organismFile);
                    } catch (InvalidClassException e) {
                        setState(false, "The selected file is corrupted or outdated.");
                        return;
                    } catch (ClassNotFoundException | ClassCastException e) {
                        setState(false, "No AI organism can be found in the selected file.");
                        return;
                    } catch (IOException e) {
                        setState(false, "The selected file cannot be accessed.");
                        return;
                    }
                    // when the file has been loaded successfully
                    setState(true, "File loaded successfully.");
                    fileChooseParentStage.hide();
                } else {
                    setState(false, "No file selected.");
                }
            }
        });

        // set up window
        setTitle("Select an Organism");
        setScene(new Scene(vBox));
        setResizable(false);
        initOwner(parent);
        initModality(Modality.WINDOW_MODAL);
    }

    /**
     * Updates the state of the window, changing the text and colour of the error label
     *
     * @param ready <code>true</code> if the loaded file is ready, <code>false</code> otherwise.
     * @param text Text to display in the status label.
     */
    private void setState(boolean ready, String text) {
        mErrorLabel.setText(text);
        if (ready) {
            mErrorLabel.setFill(GOOD_TEXT_COLOUR);
        } else {
            mErrorLabel.setFill(BAD_TEXT_COLOUR);
        }
    }

    public Organism showDialog() {
        showAndWait();
        return mLoadedOrganism;
    }

    public boolean getIsFastMode() {
        return mFastModeBox.isSelected();
    }
}
