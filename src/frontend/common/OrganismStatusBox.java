/*
 * Michael Pu
 * TetrisGameAI - OrganismStatusBox
 * ICS3U1 - Mr.Radulovic
 * December 28, 2017
 */

package frontend.common;

import ai.Organism;
import backend.Updatable;
import javafx.geometry.Insets;
import javafx.scene.control.TextArea;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

public class OrganismStatusBox extends VBox implements Updatable {

    private static final int TEXT_FONT_SIZE = 11;
    private static final int TITLE_FONT_SIZE = 15;
    private static final int MAX_NUMBER_OF_ROWS = 27;
    private static final int ELEMENT_SPACING = 10;
    private static final int PADDING_SIZE = 10;
    private static final String DEFAULT_STYLE = "" +
            "-fx-background-color: lightgrey; " +
            "-fx-opacity: 1;";

    private Organism mOrganism;
    private TextArea mTextArea;

    public OrganismStatusBox(String name, Organism organism) {
        super();
        setSpacing(ELEMENT_SPACING);
        setPadding(new Insets(PADDING_SIZE));
        setStyle(DEFAULT_STYLE);
        setBorder(new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));

        Text title = new Text(name);
        title.setFont(new Font(TITLE_FONT_SIZE));
        getChildren().add(title);

        mTextArea = new TextArea();
        mTextArea.setPrefRowCount(MAX_NUMBER_OF_ROWS);
        mTextArea.setFont(new Font(TEXT_FONT_SIZE));
        mTextArea.setWrapText(true);
        getChildren().add(mTextArea);

        mOrganism = organism;
    }

    public void setmOrganism(Organism mOrganism) {
        this.mOrganism = mOrganism;
    }

    @Override
    public void update(double deltaTime) {
        if (!mTextArea.getText().equals(mOrganism.getStatus())) {
            mTextArea.setText(mOrganism.getStatus());
        }
    }
}
