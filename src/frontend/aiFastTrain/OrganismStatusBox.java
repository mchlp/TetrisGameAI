/*
 * Michael Pu
 * TetrisGameAI - GenomeBox
 * ICS3U1 - Mr. Radulovic
 * December 26, 2017
 */

package frontend.aiFastTrain;

import ai.Organism;
import backend.Updatable;
import javafx.scene.control.TextArea;
import javafx.scene.text.Font;

public class OrganismStatusBox extends TextArea implements Updatable {

    private static final int FONT_SIZE = 12;

    private Organism mOrganism;

    public OrganismStatusBox(Organism organism) {
        super();
        setPrefRowCount(20);
        setFont(new Font(FONT_SIZE));
        mOrganism = organism;
    }

    public void setmOrganism(Organism mOrganism) {
        this.mOrganism = mOrganism;
    }

    @Override
    public void update(double deltaTime) {
        setText(mOrganism.printGenes() + "\n" + mOrganism.printFitness());
    }
}
