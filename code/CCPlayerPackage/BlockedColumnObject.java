package CCPlayerPackage;

public class BlockedColumnObject {
    private int column;

    private PlayerEnum playerEnum;

    public BlockedColumnObject(int col, PlayerEnum playerEnum){
        this.column = col;
        this.playerEnum = playerEnum;
    }

    public int getColumn() {
        return column;
    }

    public PlayerEnum getPlayerEnum() {
        return playerEnum;
    }
}
