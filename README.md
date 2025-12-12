# Rock Paper Scissors – "The UML That Became Code" (joke :P)

A fully menu-driven, object-oriented **Rock Paper Scissors** game in Java complete with ASCII, andw easter eggs.

This project was built to satisfy a university final project and yes, **the UML diagram is printed in the terminal when you run it.

## Features

- Menu-driven interface with clear boxed design
- Fully **error-tolerant** input handling (no crashes!)
- **Two custom classes**:
  - `RockPaperScissors` → handles menu & program flow
  - `RPSGame` → encapsulates all game logic and statistics (your required student-defined class)
- 5 methods (actually 9+ including helpers)
- Tracks and displays:
  - Wins, losses, ties, total rounds
  - Your win percentage
- Easter egg: type `42` in the menu
- Startup ASCII art that **is literally the UML class diagram**

## UML Class Diagram

When you run the program, this ASCII dappears:

╔═══════════════════════════════════════════════════════════════╗
║              WELCOME TO THE SELF-AWARE UML GAME               ║
║           (Yes, the class diagram is literally me)           ║
╠═══════════════════════════════════════════════════════════════╣
║   +--------------------------+          +------------------+  ║
║   |   RockPaperScissors      |<>-------> |     RPSGame      |  ║
║   +--------------------------+  uses    +------------------+  ║
║   | + main(args: String[])   |          | - playerScore: int    ║
║   | + displayMenu()          |          | - computerScore: int  ║
║   | + getMenuChoice(): int   |          | - ties: int           ║
║   +--------------------------+          | - totalRounds: int    ║
║                                         | + getComputerChoice() ║
║                                         | + determineWinner()   ║
║                                         | + playRound()         ║
║
║                                         | + displayStats()      ║
║                                         | + resetGame()         ║
║                                         +------------------+    ║
╚═══════════════════════════════════════════════════════════════╝


Clone Repository (optional)
Open RockPaperScissors.java and play the game :D
