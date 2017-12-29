/*
 * Michael Pu
 * TetrisGameAI - MenuScreen
 * ICS3U1 - Mr.Radulovic
 * December 28, 2017
 */

package menu;

import backend.GameMode;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.CornerRadii;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class MenuScreen extends BorderPane {

    private static final int DEFAULT_PADDING = 20;

    private Menu mMenu;
    GameMode mGameMode;

    public MenuScreen(Stage stage) {

        setBackground(new Background(new BackgroundFill(Color.BLACK, CornerRadii.EMPTY, Insets.EMPTY)));
        setPrefHeight(400);
        setPrefWidth(500);
        setPadding(new Insets(DEFAULT_PADDING));
        mGameMode = GameMode.MAIN_MENU;

        Text titleText = new Text("Tetris Game");
        titleText.setFont(new Font(50));
        titleText.setFill(Color.GREEN);
        setTop(titleText);
        setAlignment(titleText, Pos.CENTER);

        mMenu = new Menu(stage);
        mMenu.setMaxWidth(300);
        setCenter(mMenu);

        Text descriptionText = new Text("Michael Pu - Culminating Project - ICS3U");
        descriptionText.setFill(Color.GREY);
        descriptionText.setFont(new Font(11));
        setBottom(descriptionText);
        setAlignment(descriptionText, Pos.BOTTOM_RIGHT);
    }

    public void toggleAdvanced() {
        mMenu.toggleAdvanced();
    }

    public GameMode getmGameMode() {
        return mGameMode;
    }

    public Menu getmMenu() {
        return mMenu;
    }
}
