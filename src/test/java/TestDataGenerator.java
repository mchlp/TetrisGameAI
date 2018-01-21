/*
 * Michael Pu
 * TetrisGameAI - TestDataGenerator
 * ICS3U1 - Mr.Radulovic
 * January 20, 2018
 */

import ai.FastTrainer;
import ai.Population;
import backend.GameMode;
import backend.GameProcessor;

import java.io.File;
import java.util.Timer;
import java.util.TimerTask;

public class TestDataGenerator {

    private static int taskCounter = 0;

    public static void main (String[] args) {

        // Test_loadPopulationFromFile_NoError.pop.ser

        File saveFile = new File("res/Test_loadPopulationFromFile_NoError.pop.ser");
        GameProcessor gameProcessor = new GameProcessor(GameMode.AI_FAST_TRAINER);
        Population population = new Population(saveFile);
        FastTrainer fastTrainer = new FastTrainer(gameProcessor, population);

        int taskDelay = 10;
        int taskTimes = 5*50+100;

        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                fastTrainer.update(taskDelay);
                taskCounter++;
                if (taskCounter >= taskTimes) {
                    timer.cancel();
                }

            }
        }, 0, taskDelay);
    }

    private static String getPopulationData(Population population) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("\nTrain Time: ").append(population.getmTrainTime());
        stringBuilder.append("\nGeneration: ").append(population.getmGeneration());
        stringBuilder.append("\nElite Fitness: ").append(population.getmEliteFitness());
        stringBuilder.append("\nTop Fitness: ").append(population.getmTopFitness());
        stringBuilder.append("\nTotal Fitness: ").append(population.getmTotalFitness());
        stringBuilder.append("\nMin Fitness: ").append(population.getmMinFitness());
        stringBuilder.append("\nAverage Fitness: ").append(population.getmAvgFitness());
        stringBuilder.append("\nTop 25 Fitness: ").append(population.getmTop25PerFitness());
        stringBuilder.append("\nBottom 25 Fitness: ").append(population.getmBottom25PerFitness());
        return stringBuilder.toString();
    }

}
