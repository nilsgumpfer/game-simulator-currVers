package CCPlayerPackage;

import basic.*;

import java.util.List;

/**
 * Created by Carlo on 02.05.2017.
 */
public class Manager {

    CCPlayer ownPlayer;

    VirtualGameBoard virtualGameBoard = new VirtualGameBoard(this);

    MoveGenerator moveGenerator;
    WinSituationDetector winSituationDetector = new WinSituationDetector(this);
    public int lastOwnColumn = 8;

    //constructor
    public Manager(CCPlayer ownPlayer){
        this.ownPlayer = ownPlayer;
    }

    //general methods
    public void createMoveGenerator(Position p, List<Move> moves){
        moveGenerator = new MoveGenerator(p, moves, this, this.winSituationDetector, ownPlayer);
    }

    public MoveGenerator getMoveGenerator() {
        return moveGenerator;
    }

    public WinSituationDetector getWinSituationDetector() {
        return winSituationDetector;
    }

    public void setLastOwnColumn(int lastOwnColumn) {
        this.lastOwnColumn = lastOwnColumn;
    }

    //virtualGameBoard
    public void initializeVirtualGameBoard(){
        virtualGameBoard.initializeBoard();
    }
    public void printVirtualGameBoard(){
        virtualGameBoard.printBoard();
    }
    public PlayerEnum getPlayerEnumAtPosition(int row, int column){
        PlayerEnum playerEnum = virtualGameBoard.getPlayerEnumAtPosition(row, column);
        return playerEnum;
    }
    public void addCoinToVirtualGameBoard(PlayerEnum playerEnum, int col){
        virtualGameBoard.addCoinToBoard(playerEnum, col);
    }
    public List<Integer> getRemainingColumns(){
        return virtualGameBoard.getRemainingColumns();
    }
    public VirtualGameBoard getVirtualGameBoard() {
        return virtualGameBoard;
    }
    public void setVirtualGameBoard(VirtualGameBoard virtualGameBoard) {
        this.virtualGameBoard = virtualGameBoard;
    }
}
