/*
╔═══════════════════════════════════════════════════════════════════════════════╗
║                           ROCK PAPER SCISSORS                                 ║
║                       "The UML That Became Code"                              ║
╚═══════════════════════════════════════════════════════════════════════════════╝
Sean Howard
Initial Version: 12-01-2025 
Last Edits: 12-12-2025 
COSC 111
Prof Tracy
DISCLAIMER:
I asked AI to help me find my ALT commands for the ASCI box | Alt + 201,187,200,188,186,205,204,185,203,202,206.
I asked AI to help me solve issue where I couldnt find Errors for why it wasnt compiling
I asked AI to help me solve the ASCI Art where it wasnt just one long System.out.println sequence
I used AI for refactoring suggestions and polishing. (Initial build was in 1 Main Helped me understand where to split into 2 classes)
*/

import java.util.Random;   // For computer's random choices
import java.util.Scanner;  // For reading user input

public class RockPaperScissors {

    // ──────────────────────────────────────────────────────────────────────
    // ASCII ART OF THE UML CLASS DIAGRAM (shown when program starts)
    // This is just for fun and to prove
    // ──────────────────────────────────────────────────────────────────────
    private static final String UML_ART = """
        
        ╔═════════════════════════════════════════════════════════════════╗
        ║              WELCOME TO THE SELF-AWARE UML GAME                 ║
        ║                (Yes, the class diagram is me)                   ║
        ╠═════════════════════════════════════════════════════════════════╣
        ║   +--------------------------+          +------------------+    ║
        ║   |   RockPaperScissors      |<-------> |     RPSGame      |    ║
        ║   +--------------------------+  users    +------------------+   ║
        ║   | + main(args: String[])   |          | - playerScore: int    ║
        ║   | + displayMenu()          |          | - computerScore: int  ║
        ║   | + getMenuChoice(): int   |          | - ties: int           ║
        ║   +--------------------------+          | - totalRounds: int    ║
        ║                                         | + getComputerChoice() ║
        ║                                         | + determineWinner()   ║
        ║                                         | + playRound()         ║
        ║                                         | + displayStats()      ║
        ║                                         | + resetGame()         ║
        ║                                         +------------------+    ║
        ╚═════════════════════════════════════════════════════════════════╝
        """;

    // One shared Scanner for the whole program
    private static final Scanner sc = new Scanner(System.in);

    // The actual game logic lives in this object
    // RockPaperScissors class = menu + program flow
    // RPSGame class = all the actual game data and rules
    private static final RPSGame game = new RPSGame();

    // ──────────────────────────────────────────────────────────────────────
    // MAIN METHOD – Program starts here
    // ──────────────────────────────────────────────────────────────────────
    public static void main(String[] args) {
        System.out.println(UML_ART);                                // Show the UML art
        System.out.println("Press Enter to start playing...");
        sc.nextLine();                                              // Wait for user

        System.out.println("=== Rock, Paper, Scissors ===\n");

        int choice;
        do {
            displayMenu();                     // Show menu options
            choice = getMenuChoice();          // Get valid user choice (0–3)

            // ──────────────────────────────────────────────────────────────────
            // MENU ACTIONS
            // ──────────────────────────────────────────────────────────────────
            switch (choice) {
                case 1 -> game.playRound();                    // Start a new round
                case 2 -> game.displayStats();                 // Show win/loss stats
                case 3 -> game.resetGame();                    // Clear all scores
                case 0 -> System.out.println("\nThanks for playing! Goodbye!\n");
                case 42 -> System.out.println("\nYou found the secret! The Answer to Everything is... a tie!\n");
                // (42 is an easter egg – try typing it!)
            }
        } while (choice != 0);  // Loop until user chooses 0 (Exit)

        sc.close();  // Always close Scanner when done
    }

    // ──────────────────────────────────────────────────────────────────────
    // Displays the main menu
    // ──────────────────────────────────────────────────────────────────────
    private static void displayMenu() {
        System.out.println("\n╔════════════════════════════════════╗");
        System.out.println("║               MENU                 ║");
        System.out.println("╚════════════════════════════════════╝");
        System.out.println("1. Play a Round");
        System.out.println("2. View Statistics");
        System.out.println("3. Reset Game");
        System.out.println("0. Exit");
        System.out.print("Enter choice (0–3): ");
    }

    // ──────────────────────────────────────────────────────────────────────
    // Safely gets a menu number from the user
    // Repeats until a valid integer 0–3 is entered
    // Also has easter egg for 42
    // ──────────────────────────────────────────────────────────────────────
    private static int getMenuChoice() {
        while (true) {
            String input = sc.nextLine().trim();

            // Secret easter egg
            if (input.equals("42")) {
                return 42;
            }

            try {
                int choice = Integer.parseInt(input);
                if (choice >= 0 && choice <= 3) {
                    return choice;
                } else {
                    System.out.print("Please enter 0, 1, 2, or 3: ");
                }
            } catch (NumberFormatException e) {
                System.out.print("Not a number! Try again: ");
            }
        }
    }
}

// ═══════════════════════════════════════════════════════════════════════════
// SECOND CLASS: RPSGame
// This class holds all the game data (scores) and logic (who wins?)
// ═══════════════════════════════════════════════════════════════════════════
class RPSGame {

    // ──────────────────────────────────────────────────────────────────────
    // Instance variables (fields) – store game state
    // ──────────────────────────────────────────────────────────────────────
    private int playerScore = 0;      // How many rounds player has won
    private int computerScore = 0;    // How many rounds computer has won
    private int ties = 0;             // How many ties
    private int totalRounds = 0;      // Total rounds played

    // Tools we'll use repeatedly
    private final Random rand = new Random();     // For computer's random choice
    private final Scanner sc = new Scanner(System.in);

    // ──────────────────────────────────────────────────────────────────────
    // Returns computer's random choice: "Rock", "Paper", or "Scissors"
    // ──────────────────────────────────────────────────────────────────────
    public String getComputerChoice() {
        return switch (rand.nextInt(3)) {   // 0, 1, or 2
            case 0 -> "Rock";
            case 1 -> "Paper";
            default -> "Scissors";
        };
    }

    // ──────────────────────────────────────────────────────────────────────
    // Determines who wins a round
    // Returns: 1 = player wins, -1 = computer wins, 0 = tie
    // ──────────────────────────────────────────────────────────────────────
    public int determineWinner(String player, String computer) {
        if (player.equals(computer)) {
            return 0;  // Same choice → tie
        }
        // Winning combinations for player: Rock > Scissors, Paper > Rock, Scissors > Paper
        if ((player.equals("Rock") && computer.equals("Scissors")) ||
            (player.equals("Paper") && computer.equals("Rock")) ||
            (player.equals("Scissors") && computer.equals("Paper"))) {
            return 1;  // Player wins
        }
        return -1; // Otherwise, computer wins
    }

    // ──────────────────────────────────────────────────────────────────────
    // Plays one full round: gets player input, compares, updates scores
    // ──────────────────────────────────────────────────────────────────────
    public void playRound() {
        totalRounds++;  // We played one more round
        System.out.println("\n--- Round " + totalRounds + " ---");

        String playerChoice = getValidPlayerChoice();     // Ask player
        String computerChoice = getComputerChoice();      // Computer picks

        // Show both choices
        System.out.println("You chose     : " + playerChoice);
        System.out.println("Computer chose: " + computerChoice);

        int result = determineWinner(playerChoice, computerChoice);

        // Update scores and show result message
        switch (result) {
            case 1 -> {
                playerScore++;
                System.out.println("YOU WIN THIS ROUND!");
            }
            case -1 -> {
                computerScore++;
                System.out.println("Computer wins this round!");
            }
            case 0 -> {
                ties++;
                System.out.println("It's a tie!");
            }
        }
        System.out.println();  // Blank line for readability
    }

    // ──────────────────────────────────────────────────────────────────────
    // Repeatedly asks player until they type a valid choice
    // Case-insensitive and forgiving
    // ──────────────────────────────────────────────────────────────────────
    private String getValidPlayerChoice() {
        while (true) {
            System.out.print("Your move (Rock/Paper/Scissors): ");
            String input = sc.nextLine().trim();

            if (input.equalsIgnoreCase("rock"))     return "Rock";
            if (input.equalsIgnoreCase("paper"))    return "Paper";
            if (input.equalsIgnoreCase("scissors")) return "Scissors";

            System.out.println("Invalid! Please type Rock, Paper, or Scissors.");
        }
    }

    // ──────────────────────────────────────────────────────────────────────
    // Shows all game statistics in a nice formatted box
    // ──────────────────────────────────────────────────────────────────────
    public void displayStats() {
        System.out.println("\n" + "═".repeat(50));
        System.out.println("            GAME STATISTICS");
        System.out.println("═".repeat(50));
        System.out.println("Total Rounds Played : " + totalRounds);
        System.out.println("You Won             : " + playerScore);
        System.out.println("Computer Won        : " + computerScore);
        System.out.println("Ties                : " + ties);

        if (totalRounds > 0) {
            double winRate = (playerScore * 100.0 / totalRounds);
            System.out.printf("Your Win Rate       : %.2f%%%n", winRate);
        }
        System.out.println("═".repeat(50));
    }

    // ──────────────────────────────────────────────────────────────────────
    // Resets everything to zero – like starting fresh
    // ──────────────────────────────────────────────────────────────────────
    public void resetGame() {
        playerScore = 0;
        computerScore = 0;
        ties = 0;
        totalRounds = 0;
        System.out.println("\nGame reset! All scores cleared.\n");
    }
}