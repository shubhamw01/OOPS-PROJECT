
public enum AdvancedSymbol implements SlotSymbol {
    CHERRY("🍒", 3),
    WATERMELON("🍉", 4),
    BELL("🔔", 6),
    GUN("🔫", 10),
    MONEY("🤑", 20),
    DIAMOND("💎", 50),
    CROWN("👑", 100);

    private final String icon;
    private final int multiplier;

    AdvancedSymbol(String icon, int multiplier) {
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
