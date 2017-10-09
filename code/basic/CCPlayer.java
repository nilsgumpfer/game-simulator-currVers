package basic;

import CCPlayerTools.Manager;
import CCPlayerTools.PlayerEnum;

import java.util.List;

/**
 * Created by Carlo on 02.05.2017.
 */
public class CCPlayer extends Player {
    boolean firstRound = true;
    Manager manager = new Manager(this);

    int lastRivalCol = 0;

    Position p;
    List<Move> moves;

    public CCPlayer(){
        this.name = "CCPlayer";
    }

    @Override
    Move nextMove(Position p, List<Move> moves) {
        this.p = p;
        this.moves = moves;

        manager.createMoveGenerator(p, moves);

        boolean meFirst = false;

        //first move
        if(firstRound == true){
            manager.initializeVirtualGameBoard();
            if(p.getLastMove() == null){
                meFirst = true;
                setOwnMove(manager, getColumnOfMoveAsInt(manager.getMoveGenerator().getMove(meFirst)));
            } else {
                setRivalMove(manager, p);
                meFirst = false;
                setOwnMove(manager, getColumnOfMoveAsInt(manager.getMoveGenerator().getMove(meFirst)));
            }
        } else {
            setRivalMove(manager, p);
            lastRivalCol = safeRivalMove(p);
            manager.getWinSituationDetector().checkAllChains();
            setOwnMove(manager, getColumnOfMoveAsInt(manager.getMoveGenerator().getMove(meFirst)));
        }

        manager.printVirtualGameBoard();

        //end firstRound
        if(firstRound==true) {
            firstRound = false;
        }

        //safe lastOwnColumn + return move
        manager.setLastOwnColumn(getColumnOfMoveAsInt(manager.getMoveGenerator().getPlannedMove()));
        return manager.getMoveGenerator().getPlannedMove();
    }

    @Override
    public void reset(){
        manager = new Manager(this);
        manager.initializeVirtualGameBoard();
    }

    public void setOwnMove(Manager manager, int col){
        manager.addCoinToVirtualGameBoard(PlayerEnum.OWN, col);
    }

    private void setRivalMove(Manager manager, Position p){
        String lastMove = getColumnOfMoveAsString(p.getLastMove());
        manager.addCoinToVirtualGameBoard(PlayerEnum.RIVAL, Integer.parseInt(lastMove));
    }

    private int safeRivalMove(Position p){
        return getColumnOfMoveAsInt(p.getLastMove());
    }

    public String getColumnOfMoveAsString(Move move)
    {
        if(move != null){
            String moveString = move.toString();
            return Character.toString(moveString.charAt(moveString.length()-2));
        } else{
            return "1";
        }
    }

    public int getColumnOfMoveAsInt(Move move)
    {
        return Integer.parseInt(getColumnOfMoveAsString(move));
    }

    public int getLastRivalCol() {
        return lastRivalCol;
    }
}
