/**
 * Classic 3-reel implementation of the SlotMachine abstract class.
 */
public class ClassicSlotMachine extends SlotMachine {
    private final SlotSymbol[] availableSymbols;
    public ClassicSlotMachine(SlotSymbol[] availableSymbols) {
        this.availableSymbols = availableSymbols;
    }
    @Override
    public SlotSymbol[] spinRow() {
        SlotSymbol[] row = new SlotSymbol[3];
        for (int i = 0; i < 3; i++) {
            row[i] = availableSymbols[random.nextInt(availableSymbols.length)];
        }
        return row;
    }

    @Override
    public void printRow(SlotSymbol[] row) {
        System.out.println("*******************");
        System.out.println(" " + row[0].getIcon() + "|" + row[1].getIcon() + "|" + row[2].getIcon());
        System.out.println("*******************");
    }

    @Override
    public int calculatePayout(SlotSymbol[] row, int bet) {
        if (row[0] == row[1] && row[1] == row[2]) {
            return bet * row[0].getMultiplier();
        } else if (row[0] == row[1]) {
            return bet * row[0].getMultiplier();
        } else if (row[1] == row[2]) {
            return bet * row[1].getMultiplier();
        }
        return 0;
    }
}