# TetrisGameAI
**Culminating Project for ICS3U1<br>**
_Michael Pu - January 2018<br>
Mr. Radulovic - Don Mills Collegiate Institute<br>_
Work log can be found in the commit history of this [GitHub repository](https://github.com/mchlp/TetrisGameAI)

## Description
- Clone of the classic Tetris game
- Includes a single player mode and AI mode

## Project Organization
Package                          | Description
---                              | ---
[algorithm](src/ai)              | files related to the AI
[backend](src/backend)           | files related to the processing and game logic
[frontend](src/frontend)         | files related to the JavaFX components displayed on screen
[tests](src/tests)               | test cases files and JUnit test files

## Features
- Various indicators on the side that show the score, level, lines cleared, etc.
- Two AI training modes: GUI training mode which shows the training game play and fast training mode which only shows the statistics 
- AI is trained using an evolutionary algorithm, genes can be found in the [Genes](/src/ai/Genes.java) class
- Window resizes automatically according to the size of the screen (80% of the height or width of the screen, depending on which is smaller)
- The size of the window changes automatically depending on the dimensions of the map and the size of the window
