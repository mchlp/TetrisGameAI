/*
 * Michael Pu
 * TetrisGameAI - TestDataGenerator
 * ICS3U1 - Mr.Radulovic
 * January 13, 2018
 */

import ai.FastTrainer;
import ai.Population;
import backend.GameMode;
import backend.GameProcessor;
import frontend.common.StatsBar;

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
                    System.out.println(getPopulationData(fastTrainer.getmPopulation()));
                }

            }
        }, 0, taskDelay);
    }

    private static String getPopulationData(Population population) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("\nTrain Time: " + population.getmTrainTime());
        stringBuilder.append("\nGeneration: " + population.getmGeneration());
        stringBuilder.append("\nElite Fitness: " + population.getmEliteFitness());
        stringBuilder.append("\nTop Fitness: " + population.getmTopFitness());
        stringBuilder.append("\nTotal Fitness: " + population.getmTotalFitness());
        stringBuilder.append("\nMin Fitness: " + population.getmMinFitness());
        stringBuilder.append("\nAverage Fitness: " + population.getmAvgFitness());
        stringBuilder.append("\nTop 25 Fitness: " + population.getmTop25PerFitness());
        stringBuilder.append("\nBottom 25 Fitness: " + population.getmBottom25PerFitness());
        return stringBuilder.toString();
    }

}
