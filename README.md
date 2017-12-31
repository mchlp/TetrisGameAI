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
[ai](src/ai)                |                                           | files related to the AI
[backend](src/backend)      |                                           | files related to the processing and game logic
[frontend](src/frontend)    |                                           | files related to the JavaFX components displayed on screen
[frontend](src/frontend)    |[aiFastTrain](src/frontend/aifasttrain)    | files related to the AI fast training mode
[frontend](src/frontend)    |[aiTrain](src/frontend/aitrain)            | files related to the AI GUI training mode
[frontend](src/frontend)    |[aiWatch](src/frontend/aiwatch)            | files related to the AI watch mode
[frontend](src/frontend)    |[base](src/frontend/base)                  | files related to abstract classes that are used in displaying 
[frontend](src/frontend)    |[common](src/frontend/common)              | files related to GUI elements that are shared between multiple modes
[frontend](src/frontend)    |[player](src/frontend/common)              | files related to the human player mode
[frontend](src/frontend)    |[Game.java](src/frontend/Game.java)                  | the class that is the entry point into the game
[tests](src/tests)          |                                           | test cases files and JUnit test files

## Features
- Various indicators on the side that show the score, level, lines cleared, etc.
- Two AI training modes: GUI training mode which shows the training game play and fast training mode which only shows the statistics 
- Includes an AI watch mode where one AI can be loaded to play multiple games
- AI is trained using an evolutionary algorithm, genes can be found in the [Genes](/src/ai/Genes.java) class
- Window resizes automatically according to the size of the screen (80% of the height or width of the screen, depending on which is smaller)
- The size of the window changes automatically depending on the dimensions of the map and the size of the window
