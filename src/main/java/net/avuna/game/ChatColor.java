package net.avuna.game;

public enum ChatColor {

    RED("@red@", 0xFF0000),
    GREEN("@gre@", 0x00FF00),
    BLUE("@blu@", 0x0000FF),
    WHITE("", 0xFFFFFF),
    BLACK("", 0x000000);

    private final String color;
    private final String color2;

    ChatColor(String color, int col2) {
        this.color = color;
        this.color2 = String.format("<col=%d>", col2);
    }

    public String get317Color() {
        return color;
    }

    public String getAltColor() {
        return color2;
    }
}
