/*
 * Michael Pu
 * TetrisGameAI - SelectOrganismDialog
 * ICS3U1 - Mr. Radulovic
 * December 28, 2017
 */

package frontend.common;

import ai.Organism;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.io.InvalidClassException;

public class SelectOrganismDialog extends Stage {

    private static final int DEFAULT_PADDING = 10;

    private static final Color GOOD_TEXT_COLOUR = Color.GREEN;
    private static final Color BAD_TEXT_COLOUR = Color.RED;

    private Organism mLoadedOrganism;
    private Text mErrorLabel;
    private CheckBox mFastModeBox;

    public SelectOrganismDialog(Stage parent) {

        VBox vBox = new VBox();
        vBox.setPrefWidth(400);
        vBox.setFillWidth(true);
        vBox.setSpacing(DEFAULT_PADDING);
        vBox.setPadding(new Insets(DEFAULT_PADDING));
        vBox.setAlignment(Pos.CENTER_LEFT);

        Text label = new Text("Select an AI Organism file to use.");
        vBox.getChildren().add(label);

        mFastModeBox = new CheckBox("AI Fast Mode");
        vBox.getChildren().add(mFastModeBox);

        mErrorLabel = new Text();
        mErrorLabel.setWrappingWidth(370);
        vBox.getChildren().add(mErrorLabel);

        HBox hBox = new HBox(10);
        vBox.getChildren().add(hBox);

        Button selectFileButton = new Button("Choose File");
        hBox.getChildren().add(selectFileButton);

        File defaultDir = new File(System.getProperty("user.home"));
        FileChooser.ExtensionFilter organismFileFilter = new FileChooser.ExtensionFilter("Organism files (*.org.ser)", "*.org.ser");
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select an AI Organism");
        fileChooser.setInitialDirectory(defaultDir);
        fileChooser.getExtensionFilters().add(organismFileFilter);

        setState(false, "No file selected.");

        final Stage fileChooseParentStage = this;

        selectFileButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                File organismFile = fileChooser.showOpenDialog(fileChooseParentStage);
                if (organismFile != null) {
                    try {
                        mLoadedOrganism = Organism.loadOrganismFromFile(organismFile);
                    } catch (InvalidClassException e) {
                        setState(false, "The selected file is corrupted or outdated.");
                        e.printStackTrace();
                        return;
                    } catch (ClassNotFoundException | ClassCastException e) {
                        setState(false, "No AI organism can be found in the selected file.");
                        e.printStackTrace();
                        return;
                    } catch (IOException e) {
                        setState(false, "The selected file cannot be accessed.");
                        e.printStackTrace();
                        return;
                    }

                    setState(true, "File loaded successfully.");
                    fileChooseParentStage.hide();
                } else {
                    setState(false, "No file selected.");
                }
            }
        });

        setScene(new Scene(vBox));
        setResizable(false);
        initOwner(parent);
        initModality(Modality.WINDOW_MODAL);
    }

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
