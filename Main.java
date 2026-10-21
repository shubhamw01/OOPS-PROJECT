import java.io.IOException;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("=================================");
        System.out.println("   WELCOME TO THE SLOT MACHINE!  ");
        System.out.println("=================================");

        System.out.print("Enter your username: ");
        String username = scanner.nextLine().trim();

        PlayerData player = null;
        try {
            player = SaveManager.loadPlayer(username);
        } catch (Exception e) {
            System.out.println("An error occurred while loading player data: " + e.getMessage());
            return;
        }

        int currentLevel = 1;
        int levelOneWins = 0;
        boolean playing = true;

        while (playing) {
            try {

                SlotMachine slotMachine = getSlotMachineForLevel(currentLevel, player);
                int maxBetAllowed = getMaxBetForLevel(currentLevel, player);

                printHeader(player, currentLevel, levelOneWins);

                if (player.getBalance() <= 0) {
                    System.out.println("You are out of money! Game Over.");
                    break;
                }

                int effectiveMax = Math.min(player.getBalance(), maxBetAllowed);
                System.out.print("Enter bet amount (Max allowed for this level: $" + effectiveMax + "): $");

                int bet = getValidBet(scanner, effectiveMax);
                if (bet == -1) continue;


                player.setBalance(player.getBalance() - bet);
                System.out.println("\nSpinning the reels...");
                SlotSymbol[] row = slotMachine.spinRow();
                slotMachine.printRow(row);

                int payout = slotMachine.calculatePayout(row, bet);
                if (payout > 0) {
                    System.out.println("🎉 YOU WIN! You won $" + payout + "! 🎉");
                    player.setBalance(player.getBalance() + payout);


                    if (currentLevel == 1) {
                        levelOneWins++;
                        if (levelOneWins >= 3) {
                            currentLevel = 2;
                            System.out.println("\n🚀 CONGRATULATIONS! You unlocked LEVEL 2! Higher bets are now available.");
                        }
                    } else if (currentLevel == 2 && player.getBalance() >= 150) {
                        currentLevel = 3;
                        System.out.println("\n🌟 AMAZING! You unlocked LEVEL 3! Epic symbols (Diamonds & Crowns) are now in play!");
                    }
                } else {
                    System.out.println("❌ No match. Better luck next time!");
                }

                player.incrementGames();


                if (!shouldPlayAgain(scanner)) {
                    System.out.println("Thanks for playing!");
                    playing = false;
                }
            } catch (ArithmeticException e) {
                System.out.println("Mathematical error encountered during gameplay: " + e.getMessage());
            }
        }
        try {
            SaveManager.savePlayer(player);
        } catch (Exception e) {
            System.out.println("Error saving game data: " + e.getMessage());
        }
        scanner.close();
    }
    private static SlotMachine getSlotMachineForLevel(int level, PlayerData player) {
        if (level == 3) {
            return new ClassicSlotMachine(AdvancedSymbol.values());
        }
        return new ClassicSlotMachine(StandardSymbol.values());
    }
    private static int getMaxBetForLevel(int level, PlayerData player) {
        if (level == 1) return 10;
        if (level == 2) return 75;
        return player.getBalance();
    }

    private static void printHeader(PlayerData player, int level, int levelOneWins) {
        System.out.println("\n---------------------------------");
        System.out.println("Player: " + player.getName() + " | Level: " + level + " | Balance: $" + player.getBalance() + " | Games: " + player.getGamesPlayed());
        if (level == 1) {
            System.out.println("Level 1 Goal: Win " + (3 - levelOneWins) + " more time(s) to advance to Level 2! (Max bet: $10)");
        } else if (level == 2) {
            System.out.println("Level 2 Goal: Keep playing to reach Level 3 with new symbols! (Max bet: $75)");
        } else {
            System.out.println("Level 3 Unlocked: High Roller Mode with Diamond & Crown symbols!");
        }
        System.out.println("---------------------------------");
    }

    private static int getValidBet(Scanner scanner, int effectiveMax) {
        try {
            int bet = scanner.nextInt();
            if (bet <= 0 || bet > effectiveMax) {
                System.out.println("Invalid bet! You can only bet between $1 and $" + effectiveMax);
                return -1;
            }
            return bet;
        } catch (InputMismatchException e) {
            System.out.println("Invalid input! Please enter a valid number.");
            scanner.nextLine();
            return -1;
        } catch (ArithmeticException e) {
            System.out.println("Arithmetic overflow/error detected in bet input.");
            scanner.nextLine();
            return -1;
        }
    }

    private static boolean shouldPlayAgain(Scanner scanner) {
        scanner.nextLine();
        while (true) {
            System.out.print("\nDo you want to play again? (Y/N): ");
            String choice = scanner.nextLine().trim().toUpperCase();
            if (choice.equals("Y")) {
                return true;
            } else if (choice.equals("N")) {
                return false;
            } else {
                System.out.println("Invalid choice! Please type 'Y' or 'N'.");
            }
        }
    }
}
