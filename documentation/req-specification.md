# Requirements specification

The application is a clone of the [Minesweeper](https://en.wikipedia.org/wiki/Minesweeper_(video_game)) puzzle game.

* The game presents the player with an interactive minefield
    * [x] The player can click a square to reveal it
        * [x] Clicking a square with no neighbouring mines reveals all neighbours
        * [x] Clicking an already revealed square will reveal all its neighbours iff it has the correct amount of flagged neighbours
    * [x] Right clicking a square flags it and right clicking again unflags it
    * [x] The game presents the player with a win screen when all the mines have been correctly identified
    * [x] The game presents the player with a lost screen when they hit a mine
* The player can choose the board width and height and how many mines will be placed
* The first guess never hits a mine — it will always hit a zero square instead
* The player can save and load games to keep their progress even when the program closes