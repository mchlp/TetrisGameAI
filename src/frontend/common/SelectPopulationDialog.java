/*
 * Michael Pu
 * TetrisGameAI - SelectOrganismDialog
 * ICS3U1 - Mr. Radulovic
 * December 28, 2017
 */

package frontend.common;

import ai.Population;
import backend.GameMode;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
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

public class SelectPopulationDialog extends Stage {

    private static final int DEFAULT_PADDING = 10;

    private static final Color GOOD_TEXT_COLOUR = Color.GREEN;
    private static final Color BAD_TEXT_COLOUR = Color.RED;

    private Population mLoadedPopulation;
    private GameMode mTrainMode;
    private boolean mGoClicked;

    public SelectPopulationDialog(Stage parent) {

        mGoClicked = false;

        VBox vBox = new VBox();
        vBox.setPrefWidth(400);
        vBox.setFillWidth(true);
        vBox.setSpacing(DEFAULT_PADDING);
        vBox.setPadding(new Insets(DEFAULT_PADDING));
        vBox.setAlignment(Pos.CENTER_LEFT);

        // training mode section

        Text modeLabel = new Text("Select an training mode");
        vBox.getChildren().add(modeLabel);

        ToggleGroup modeGroup = new ToggleGroup();

        RadioButton fastModeButton = new RadioButton("Fast Training Mode");
        fastModeButton.setSelected(true);
        fastModeButton.setToggleGroup(modeGroup);
        vBox.getChildren().add(fastModeButton);
        mTrainMode = GameMode.AI_FAST_TRAINER;

        RadioButton guiModeButton = new RadioButton("GUI Training Mode");
        guiModeButton.setSelected(false);
        guiModeButton.setToggleGroup(modeGroup);
        vBox.getChildren().add(guiModeButton);

        // source file section

        Text populationFileLabel = new Text("Select an AI population");
        vBox.getChildren().add(populationFileLabel);

        ToggleGroup fileSourceGroup = new ToggleGroup();

        RadioButton newFileButton = new RadioButton("Create new population file");
        newFileButton.setSelected(true);
        newFileButton.setToggleGroup(fileSourceGroup);
        vBox.getChildren().add(newFileButton);

        RadioButton existingFileButton = new RadioButton("Use existing population file");
        existingFileButton.setSelected(false);
        existingFileButton.setToggleGroup(fileSourceGroup);
        vBox.getChildren().add(existingFileButton);

        Text fileLabel = new Text();
        vBox.getChildren().add(fileLabel);

        // error label

        Text errorLabel = new Text();
        errorLabel.setText("No file selected.");
        errorLabel.setFill(BAD_TEXT_COLOUR);
        vBox.getChildren().add(errorLabel);

        // button bar

        HBox hBox = new HBox(10);
        vBox.getChildren().add(hBox);

        Button selectFileButton = new Button("Create File");
        hBox.getChildren().add(selectFileButton);

        Button goButton = new Button("Go");
        goButton.setDisable(true);
        hBox.getChildren().add(goButton);

        guiModeButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                mTrainMode = GameMode.AI_TRAINER;
            }
        });

        fastModeButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                mTrainMode = GameMode.AI_FAST_TRAINER;
            }
        });

        newFileButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                selectFileButton.setText("Create File");
            }
        });

        existingFileButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                selectFileButton.setText("Choose File");
            }
        });

        File defaultDir = new File(System.getProperty("user.home"));
        FileChooser.ExtensionFilter organismFileFilter = new FileChooser.ExtensionFilter("Population files (*.pop.ser)", "*.pop.ser");
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select an AI Population");
        fileChooser.setInitialDirectory(defaultDir);
        fileChooser.getExtensionFilters().add(organismFileFilter);

        final Stage fileChooseParentStage = this;

        selectFileButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                File populationFile;
                if (newFileButton.isSelected()) {

                    populationFile = fileChooser.showSaveDialog(fileChooseParentStage);

                    if (populationFile != null) {
                        try {
                            if (!populationFile.getName().endsWith(".pop.ser")) {
                                populationFile = new File(populationFile.getCanonicalPath() + ".pop.ser");
                            }
                            mLoadedPopulation = new Population(populationFile);
                            fileLabel.setText(populationFile.getCanonicalPath());
                        } catch (IOException e) {
                            fileLabel.setText("");
                            errorLabel.setText("Selected file cannot be accessed.");
                            errorLabel.setFill(BAD_TEXT_COLOUR);
                            fileLabel.setFill(BAD_TEXT_COLOUR);
                            goButton.setDisable(true);
                            e.printStackTrace();
                            return;
                        }
                    } else {
                        fileLabel.setText("");
                        errorLabel.setText("No file selected.");
                        errorLabel.setFill(BAD_TEXT_COLOUR);
                        fileLabel.setFill(BAD_TEXT_COLOUR);
                        goButton.setDisable(true);
                        return;
                    }
                } else {

                    populationFile = fileChooser.showOpenDialog(fileChooseParentStage);

                    if (populationFile != null) {

                        try {
                            mLoadedPopulation = Population.loadPopulationFromFile((populationFile));
                            fileLabel.setText(populationFile.getCanonicalPath());
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
                    } else {
                        fileLabel.setText("");
                        errorLabel.setText("No file selected.");
                        errorLabel.setFill(BAD_TEXT_COLOUR);
                        fileLabel.setFill(BAD_TEXT_COLOUR);
                        goButton.setDisable(true);
                        return;
                    }
                }

                errorLabel.setText("File loaded successfully.");
                errorLabel.setFill(GOOD_TEXT_COLOUR);
                fileLabel.setFill(GOOD_TEXT_COLOUR);
                goButton.setDisable(false);
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

    public Population showDialog() {
        showAndWait();
        if (mGoClicked) {
            return mLoadedPopulation;
        } else {
            return null;
        }
    }

    public Population getmLoadedPopulation() {
        return mLoadedPopulation;
    }

    public GameMode getmTrainMode() {
        return mTrainMode;
    }
}
