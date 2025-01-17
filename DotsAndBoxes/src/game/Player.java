package game;

public abstract class Player {
    protected final PlayerSymbol symbol;

    public Player(PlayerSymbol symbol) {
        this.symbol = symbol;
    }

    public PlayerSymbol getSymbol() {
        return symbol;
    }

    public abstract boolean makeMove(GameMediator mediator);
}


