
/*
 * Michael Pu
 * TetrisGameAI - TestDataGenerator
 * ICS3U1 - Mr.Radulovic
 * January 20, 2018
 */

import java.io.File;
import java.util.Timer;
import java.util.TimerTask;

import ai.FastTrainer;
import ai.Population;
import backend.GameMode;
import backend.GameProcessor;

public class TestDataGenerator {

	private static int taskCounter = 0;

	public static void main(String[] args) {

		// Test_loadPopulationFromFile_NoError.pop.ser

		File saveFile = new File("res/Test_loadPopulationFromFile_NoError.pop.ser");
		GameProcessor gameProcessor = new GameProcessor(GameMode.AI_FAST_TRAINER);
		Population population = new Population(saveFile);
		FastTrainer fastTrainer = new FastTrainer(gameProcessor, population);

		int taskDelay = 10;
		int taskTimes = 5 * 50 + 100;

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
}
