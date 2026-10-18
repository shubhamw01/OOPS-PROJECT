
public class PlayerData {
    private String name;
    private int balance;
    private int gamesPlayed;

    public PlayerData(String name, int balance, int gamesPlayed) {
        this.name = name;
        this.balance = balance;
        this.gamesPlayed = gamesPlayed;
    }

    public String getName() {
        return name;
    }

    public int getBalance() {
        return balance;
    }

    public void setBalance(int balance) {
        this.balance = balance;
    }

    public int getGamesPlayed() {
        return gamesPlayed;
    }

    public void incrementGames() {
        this.gamesPlayed++;
    }
}