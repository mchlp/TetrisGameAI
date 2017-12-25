/*
 * Michael Pu
 * TetrisGameAI - Gene
 * ICS3U1 - Mr. Radulovic
 * December 25, 2017
 */

package ai;

import java.io.Serializable;

public class Gene implements Serializable {

    private static final double MUTATION_RATE = 0.2;

    private Genes mType;
    private double mValue;

    public Gene(Genes type, double value) {
        mType = type;
        mValue = value;
    }

    public Genes getmType() {
        return mType;
    }

    public void setmValue(double mValue) {
        this.mValue = mValue;
    }

    public Gene merge(Gene parentGene) {
        double newValue = Math.random() >= 0.5 ? mValue : parentGene.mValue;
        if (Math.random() < MUTATION_RATE) {
            newValue += Math.random();
        }
        return new Gene(mType, newValue);
    }
}