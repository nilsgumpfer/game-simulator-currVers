package CCPlayerPackage;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Carlo on 02.05.2017.
 */
public class VirtualGameBoard {

    Manager manager;
    public PlayerEnum[][] virtualGameBoard = new PlayerEnum[7][7];

    public VirtualGameBoard(Manager manager) {
        this.manager = manager;
    }

    //initialization
    public void initializeBoard() {
        for (int i = 0; i < 7; i++) {
            for (int j = 0; j < 7; j++) {
                virtualGameBoard[i][j] = PlayerEnum.EMPTY;
            }
        }
    }

    public void printBoard() {
        String line = "";
        ////System.out.println("         Col 1 | Col 2 | Col 3 | Col 4 | Col 5 | Col 6 | Col 7 |");
        for (int i = 0; i < 7; i++) {
            line = "Row " + i + ": | ";
            for (int j = 0; j < 7; j++) {
                if (virtualGameBoard[i][j] == PlayerEnum.OWN) {
                    line += virtualGameBoard[i][j] + "   | ";
                } else {
                    line += virtualGameBoard[i][j] + " | ";
                }

            }
            ////System.out.println(line);
        }
    }

    public void addCoinToBoard(PlayerEnum playerEnum, int col) {
        for (int i = 6; i >= 0; i--) {
            if (virtualGameBoard[i][col - 1] == PlayerEnum.EMPTY) {
                virtualGameBoard[i][col - 1] = playerEnum;
                break;
            }
        }
    }

    public void addCoinToSpecificPosition(PlayerEnum playerEnum, int row, int col) {
        virtualGameBoard[row][col] = playerEnum;
    }

    public PlayerEnum[][] getVirtualGameBoard() {
        return virtualGameBoard;
    }

    public PlayerEnum getPlayerEnumAtPosition(int row, int column) {
        return virtualGameBoard[row][column];
    }

    public List<Integer> getRemainingColumns() {
        List<Integer> columnList = new ArrayList<>();
        for (int i = 0; i < 7; i++) {
            if (virtualGameBoard[0][i] == PlayerEnum.EMPTY) {
                columnList.add(i);
            }
        }
        return columnList;
    }

    public int countEnemyCoinsOnBoard() {
        int count = 0;
        for (int i = 0; i < 7; i++) {
            for (int j = 0; j < 7; j++) {
                if (manager.getPlayerEnumAtPosition(i, j) == PlayerEnum.RIVAL) {
                    count++;
                }
            }
        }
        return count;
    }

    public void swapPlayerCoins() {
        for (int i = 0; i < 7; i++) {
            for (int j = 0; j < 7; j++) {
                if (getPlayerEnumAtPosition(i, j) == PlayerEnum.RIVAL) {
                    addCoinToSpecificPosition(PlayerEnum.OWN, i, j);
                } else {
                    if (getPlayerEnumAtPosition(i, j) == PlayerEnum.OWN) {
                        addCoinToSpecificPosition(PlayerEnum.RIVAL, i, j);
                    }
                }
            }
        }
    }
}
