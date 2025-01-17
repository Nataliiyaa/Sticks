package game;

public class SquareState {
    private PlayerSymbol owner;

    public SquareState() {
        this.owner = PlayerSymbol.NONE;
    }

    public PlayerSymbol getOwner() {
        return owner;
    }

    public void setOwner(PlayerSymbol owner) {
        this.owner = owner;
    }
}
