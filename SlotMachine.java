import java.util.Random;

/**
 * Abstract class defining the core behavior of a slot machine.
 */
public abstract class SlotMachine {
    protected Random random = new Random();

    public abstract SlotSymbol[] spinRow();
    public abstract void printRow(SlotSymbol[] row);
    public abstract int calculatePayout(SlotSymbol[] row, int bet);
}