# TetrisGameAI
**Culminating Project for ICS3U1<br>**
_Michael Pu - January 2018<br>
Mr. Radulovic - Don Mills Collegiate Institute<br>_
Work log can be found in the commit history of this [GitHub repository](https://github.com/mchlp/TetrisGameAI)

- _**Currently Under Development**_

## Description
- Clone of the classic Tetris game
- Includes a single player mode and AI mode

## Project Organization
Package                     |Subpackage                                 | Description
---                         |---                                        | ---
[main.java.ai](src/main/java/ai)                |                                           | files related to the AI
[main.java.backend](src/main/java/backend)      |                                           | files related to the processing and game logic
[main.java.frontend](src/main/java/frontend)    |                                           | files related to the JavaFX components displayed on screen
[main.java.frontend](src/main/java/frontend)    |[aiFastTrain](src/main/java/frontend/aifasttrain)    | files related to the AI fast training mode
[main.java.frontend](src/main/java/frontend)    |[aiTrain](src/main/java/frontend/aitrain)            | files related to the AI GUI training mode
[main.java.frontend](src/main/java/frontend)    |[aiWatch](src/main/java/frontend/aiwatch)            | files related to the AI watch mode
[main.java.frontend](src/main/java/frontend)    |[base](src/main/java/frontend/base)                  | files related to abstract classes that are used in displaying 
[main.java.frontend](src/main/java/frontend)    |[common](src/main/java/frontend/common)              | files related to GUI elements that are shared between multiple modes
[main.java.frontend](src/main/java/frontend)    |[player](src/main/java/frontend/common)              | files related to the human player mode
[main.java.frontend](src/main/java/frontend)    |[menu](src/main/java/frontend/menu)                  | files related to the main menu
[main.java.frontend](src/main/java/frontend)    |[Game.java](src/main/java/frontend/Game.java)        | the class that is the entry point into the game
[tests](src/tests)          |                                           | test cases files and JUnit test files

## Features
- Various indicators on the side that show the score, level, lines cleared, etc.
- Two AI training modes: GUI training mode which shows the training game play and fast training mode which only shows the statistics 
- Includes an AI watch mode where one AI can be loaded to play multiple games
- AI is trained using a genetic algorithm, genes can be found in the [Genes](/main/ai/Genes.java) class
- Window resizes automatically according to the size of the screen (80% of the height or width of the screen, depending on which is smaller)
- The size of the window changes automatically depending on the dimensions of the map and the size of the window
