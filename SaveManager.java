import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class SaveManager {
    private static final String FILE_PATH = "player_data.txt";

    public static PlayerData loadPlayer(String username) {
        File file = new File(FILE_PATH);

        if (file.exists()) {
            try (Scanner fileScanner = new Scanner(file)) {
                while (fileScanner.hasNextLine()) {
                    String line = fileScanner.nextLine();
                    String[] data = line.split(",");

                    // Exact case match so "HeM" and "hem" are treated as distinct accounts
                    if (data.length == 3 && data[0].equals(username)) {
                        String name = data[0];
                        int balance = Integer.parseInt(data[1]);
                        int gamesPlayed = Integer.parseInt(data[2]);
                        System.out.println("\nWelcome back, " + name + "! Save file loaded successfully.");
                        return new PlayerData(name, balance, gamesPlayed);
                    }
                }
            } catch (IOException | NumberFormatException e) {
                System.out.println("Warning: Read error or file structure issue. Starting fresh.");
            }
        }

        System.out.println("\nCreating new profile for " + username + " with starting balance of $100.");
        return new PlayerData(username, 100, 0);
    }

    public static void savePlayer(PlayerData currentPlayer) {
        File file = new File(FILE_PATH);
        List<PlayerData> allPlayers = new ArrayList<>();
        boolean updatedExisting = false;

        // 1. Read existing records if the file exists
        if (file.exists()) {
            try (Scanner fileScanner = new Scanner(file)) {
                while (fileScanner.hasNextLine()) {
                    String line = fileScanner.nextLine();
                    String[] data = line.split(",");

                    if (data.length == 3) {
                        String name = data[0];
                        int balance = Integer.parseInt(data[1]);
                        int gamesPlayed = Integer.parseInt(data[2]);

                        // Update current player's record, retain everyone else
                        if (name.equals(currentPlayer.getName())) {
                            allPlayers.add(currentPlayer);
                            updatedExisting = true;
                        } else {
                            allPlayers.add(new PlayerData(name, balance, gamesPlayed));
                        }
                    }
                }
            } catch (IOException | NumberFormatException e) {
                System.out.println("Warning: Failed to read existing player records during save.");
            }
        }

        // 2. Add as a new entry if the account didn't exist previously
        if (!updatedExisting) {
            allPlayers.add(currentPlayer);
        }

        // 3. Write all player profiles back to the file
        try (PrintWriter writer = new PrintWriter(new FileWriter(FILE_PATH, false))) {
            for (PlayerData player : allPlayers) {
                writer.println(player.getName() + "," + player.getBalance() + "," + player.getGamesPlayed());
            }
            System.out.println("Game saved successfully! See you next time.");
        } catch (IOException e) {
            System.out.println("Error: Unable to save game data.");
        }
    }
}