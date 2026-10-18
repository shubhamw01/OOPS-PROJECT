/**
 * Standard set of slot machine symbols with their associated icons and payout multipliers.
 */
public enum StandardSymbol implements SlotSymbol {
    CHERRY("🍒", 3),
    WATERMELON("🍉", 4),
    BELL("🔔", 6),
    GUN("🔫", 10),
    MONEY("🤑", 20);

    private final String icon;
    private final int multiplier;

    StandardSymbol(String icon, int multiplier) {
        this.icon = icon;
        this.multiplier = multiplier;
    }

    @Override
    public String getIcon() {
        return icon;
    }

    @Override
    public int getMultiplier() {
        return multiplier;
    }
}