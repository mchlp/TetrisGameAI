/*
 * Michael Pu
 * TetrisGameAI - Gene
 * ICS3U1 - Mr. Radulovic
 * December 25, 2017
 */

package ai;

import java.io.Serializable;

public class Gene implements Serializable {

    private static final double MUTATION_RATE = 0.1;

    private Genes mType;
    private double mValue;

    public Gene(Genes type, double value) {
        mType = type;
        mValue = value;
    }

    public double getmValue() {
        return mValue;
    }

    public Gene merge(Gene parentGene) {
        double newValue = Math.random() >= 0.5 ? mValue : parentGene.mValue;
        if (Math.random() < MUTATION_RATE) {
            newValue += Math.random() * Math.abs(mType.modifier) * (Math.random() >= 0.5 ? 1 : -1);
        }
        return new Gene(mType, newValue);
    }

    public Gene clone() {
        return new Gene(mType, mValue);
    }
}
