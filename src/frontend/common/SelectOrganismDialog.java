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
    private boolean mGoClicked;

    public SelectOrganismDialog(Stage parent) {

        mGoClicked = false;

        VBox vBox = new VBox();
        vBox.setPrefWidth(400);
        vBox.setFillWidth(true);
        vBox.setSpacing(DEFAULT_PADDING);
        vBox.setPadding(new Insets(DEFAULT_PADDING));
        vBox.setAlignment(Pos.CENTER_LEFT);

        Text label = new Text("Select an AI Organism file to use.");
        vBox.getChildren().add(label);

        Text errorLabel = new Text();
        errorLabel.setText("No file selected.");
        errorLabel.setFill(BAD_TEXT_COLOUR);
        vBox.getChildren().add(errorLabel);

        HBox hBox = new HBox(10);
        vBox.getChildren().add(hBox);

        Button selectFileButton = new Button("Choose File");
        hBox.getChildren().add(selectFileButton);

        Button goButton = new Button("Go");
        goButton.setDisable(true);
        hBox.getChildren().add(goButton);

        File defaultDir = new File(System.getProperty("user.home"));
        FileChooser.ExtensionFilter organismFileFilter = new FileChooser.ExtensionFilter("Organism files (*.org.ser)", "*.org.ser");
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select an AI Organism");
        fileChooser.setInitialDirectory(defaultDir);
        fileChooser.getExtensionFilters().add(organismFileFilter);

        Text fileLabel = new Text();
        vBox.getChildren().add(fileLabel);

        final Stage fileChooseParentStage = this;

        selectFileButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                File organismFile = fileChooser.showOpenDialog(fileChooseParentStage);
                if (organismFile != null) {
                    try {
                        fileLabel.setText(organismFile.getCanonicalPath());
                    } catch (IOException e) {
                        fileLabel.setText("");
                        errorLabel.setText("Selected file cannot be accessed.");
                        errorLabel.setFill(BAD_TEXT_COLOUR);
                        fileLabel.setFill(BAD_TEXT_COLOUR);
                        goButton.setDisable(true);
                        e.printStackTrace();
                        return;
                    }

                    try {
                        mLoadedOrganism = Organism.loadOrganismFromFile(organismFile);
                    } catch (InvalidClassException e) {
                        errorLabel.setText("The selected file is corrupted or outdated.");
                        errorLabel.setFill(BAD_TEXT_COLOUR);
                        fileLabel.setFill(BAD_TEXT_COLOUR);
                        goButton.setDisable(true);
                        e.printStackTrace();
                        return;
                    } catch (ClassNotFoundException e) {
                        errorLabel.setText("An AI organism cannot cannot be loaded from the selected file.");
                        errorLabel.setFill(BAD_TEXT_COLOUR);
                        fileLabel.setFill(BAD_TEXT_COLOUR);
                        goButton.setDisable(true);
                        e.printStackTrace();
                        return;
                    } catch (IOException e) {
                        errorLabel.setText("The selected file cannot be accessed.");
                        errorLabel.setFill(BAD_TEXT_COLOUR);
                        fileLabel.setFill(BAD_TEXT_COLOUR);
                        goButton.setDisable(true);
                        e.printStackTrace();
                        return;
                    }

                    errorLabel.setText("File loaded successfully.");
                    errorLabel.setFill(GOOD_TEXT_COLOUR);
                    fileLabel.setFill(GOOD_TEXT_COLOUR);
                    goButton.setDisable(false);
                } else {
                    fileLabel.setText("");
                    errorLabel.setText("No file selected.");
                    errorLabel.setFill(BAD_TEXT_COLOUR);
                    fileLabel.setFill(BAD_TEXT_COLOUR);
                    goButton.setDisable(true);
                }
            }
        });

        goButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                mGoClicked = true;
                fileChooseParentStage.hide();
            }
        });

        setScene(new Scene(vBox));
        setResizable(false);
        initOwner(parent);
        initModality(Modality.WINDOW_MODAL);
    }

    public Organism showDialog() {
        showAndWait();
        if (mGoClicked) {
            return mLoadedOrganism;
        } else {
            return null;
        }
    }

}
