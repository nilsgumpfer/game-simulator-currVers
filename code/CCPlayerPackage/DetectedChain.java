package CCPlayerPackage;

/**
 * Created by Carlo on 07.05.2017.
 */
public class DetectedChain {
    private int size = 0;
    private int startPositionRow;
    private int startPositionCol;
    private int endPositionRow;
    private int endPositionCol;

    public ChainType getChainType() {
        return chainType;
    }

    public void setChainType(ChainType chainType) {
        this.chainType = chainType;
    }

    private ChainType chainType;

    public void resetSize(){
        size = 0;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public int getStartPositionRow() {
        return startPositionRow;
    }

    public void setStartPositionRow(int startPositionRow) {
        this.startPositionRow = startPositionRow;
    }

    public int getStartPositionCol() {
        return startPositionCol;
    }

    public void setStartPositionCol(int startPositionCol) {
        this.startPositionCol = startPositionCol;
    }

    public int getEndPositionRow() {
        return endPositionRow;
    }

    public void setEndPositionRow(int endPositionRow) {
        this.endPositionRow = endPositionRow;
    }

    public int getEndPositionCol() {
        return endPositionCol;
    }

    public void setEndPositionCol(int endPositionCol) {
        this.endPositionCol = endPositionCol;
    }

}
