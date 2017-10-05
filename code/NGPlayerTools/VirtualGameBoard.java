package NGPlayerTools;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Nils on 30.04.2017.
 */
public class VirtualGameBoard {
    private int verticalSize                            = 7;
    private int horizontalSize                          = 7;
    private int moveIndex                               = 0; //counts the total number of moves commited to the board
    private List<List<VirtualPosition>> listOfColumns   = new ArrayList<>();
    private List<List<VirtualPosition>> listOfRows      = new ArrayList<>();
    private VirtualPosition[][] arrayOfPositions        = {};

    public VirtualGameBoard(){
        initializeBoard();
    }

    public void initializeBoard(){
        arrayOfPositions = new VirtualPosition[horizontalSize][verticalSize];
        initColumnList();
        initRowList();

        moveIndex = 0;
    }

    private void initColumnList(){
        for(int h = 0; h < horizontalSize; h++)
        {
            List<VirtualPosition> tempColumn = new ArrayList<>();

            for(int v = 0; v < verticalSize; v++)
            {
                VirtualPosition virtualPosition = new VirtualPosition(h,v);

                tempColumn.add(virtualPosition);
                arrayOfPositions[h][v] = virtualPosition;
            }

            listOfColumns.add(tempColumn);
        }
    }

    private void initRowList(){
        for(int v = 0; v < verticalSize; v++)
        {
            List<VirtualPosition> tempRow = new ArrayList<>();

            for (List<VirtualPosition> currentColumn:listOfColumns) {
                tempRow.add(currentColumn.get(v));
            }

            listOfRows.add(tempRow);
        }
    }

    public void resetBoard(){
        initializeBoard();
    }

    public int getUsedRowCount(int columnIndex)
    {
        List<VirtualPosition> currentColumn = listOfColumns.get(columnIndex);
        int counter = 0;

        for(int i = currentColumn.size() - 1; i > 0; i--) {
            if(currentColumn.get(i).getPlayerColor() != PlayerColor.Empty) //count positions with valid coins
                counter++;
            else //if first position with no coin found, stop
                break;
        }

        return counter;
    }

    public boolean isColumnEmpty(int columnIndex)
    {
        if(getUsedRowCount(columnIndex) == 0)
            return true;
        else
            return false;
    }

    public void addCoinToColumn(int columnIndex, PlayerColor playerColor){
        List<VirtualPosition> currentColumn     = listOfColumns.get(columnIndex);
        int indexDestination                    = currentColumn.size() - getUsedRowCount(columnIndex) - 1;
        VirtualPosition positionDestination     = currentColumn.get(indexDestination);

        // set coin-color
        positionDestination.setPlayerColor(playerColor);
        // set index of move (to know their order)
        positionDestination.setMoveIndex(moveIndex);
        moveIndex++;
    }

    @Override
    public String toString(){
        String plainPortrayalOfBoard = "";
        String colorTemp = "";
        PlayerColor playerColorTemp = null;
        int v = 0;

        plainPortrayalOfBoard += '\n';
        plainPortrayalOfBoard += '\n';
        plainPortrayalOfBoard += "VIRTUAL GAMEBOARD";
        plainPortrayalOfBoard += '\n';

        plainPortrayalOfBoard += "\\";

        for(int h=0 ;h < horizontalSize; h++)
            plainPortrayalOfBoard += "|" + h;

        plainPortrayalOfBoard += "|";
        plainPortrayalOfBoard += '\n';

        for (List<VirtualPosition> currentRow:listOfRows) {

            plainPortrayalOfBoard += v;

            for (VirtualPosition currentPosition:currentRow) {
                playerColorTemp = currentPosition.getPlayerColor();

                switch(playerColorTemp)
                {
                    case Own:
                        colorTemp = "O";
                        break;
                    case Rival:
                        colorTemp = "R";
                        break;
                    case Empty:
                        colorTemp = " ";
                        break;
                }

                plainPortrayalOfBoard += "|" + colorTemp;
            }

            plainPortrayalOfBoard += "|";
            plainPortrayalOfBoard += '\n';

            v++;
        }

        return plainPortrayalOfBoard;
    }

    public void printScores(){
        String plainPortrayalOfBoard = "";
        int v = 0;
        int score = 0;

        plainPortrayalOfBoard += '\n';
        plainPortrayalOfBoard += '\n';
        plainPortrayalOfBoard += "VIRTUAL GAMEBOARD";
        plainPortrayalOfBoard += '\n';

        plainPortrayalOfBoard += "\\";

        for(int h=0 ;h < horizontalSize; h++)
            plainPortrayalOfBoard += "|" + h;

        plainPortrayalOfBoard += "|";
        plainPortrayalOfBoard += '\n';

        for (List<VirtualPosition> currentRow:listOfRows) {

            plainPortrayalOfBoard += v;

            for (VirtualPosition currentPosition:currentRow) {
                score = currentPosition.getTotalPotentialScore();

                if(score != 0)
                    plainPortrayalOfBoard += "|" + score;
                else if(currentPosition.getPlayerColor() == PlayerColor.Rival)
                    plainPortrayalOfBoard += "|" + "R";
                else if(currentPosition.getPlayerColor() == PlayerColor.Own)
                    plainPortrayalOfBoard += "|" + "O";
                else
                    plainPortrayalOfBoard += "|" + " ";
            }

            plainPortrayalOfBoard += "|";
            plainPortrayalOfBoard += '\n';

            v++;
        }

        //System.out.println(plainPortrayalOfBoard);
    }

    public List<List<VirtualPosition>> getListOfColumns() {
        return listOfColumns;
    }

    public List<List<VirtualPosition>> getListOfRows() {
        return listOfRows;
    }

    public VirtualPosition[][] getArrayOfPositions(){
        return arrayOfPositions;
    }
}
