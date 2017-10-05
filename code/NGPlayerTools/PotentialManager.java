package NGPlayerTools;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Nils on 13.05.2017.
 */
public class PotentialManager {
    private static PotentialManager ourInstance = new PotentialManager();
    private static List<VirtualPotential> virtualPotentialList = new ArrayList<>();

    private PotentialManager() {
    }

    public static PotentialManager getInstance() {
        return ourInstance;
    }

    public static void incorporatePotential(VirtualPotential virtualPotential)
    {
        getInstance().virtualPotentialList.add(virtualPotential);
        virtualPotential.getVirtualPosition().addPotentialScore(virtualPotential.getPotentialScore());
    }

    public static void incorporatePotentials(List<VirtualPotential> virtualPotentialList)
    {
        for(VirtualPotential virtualPotential:virtualPotentialList)
            incorporatePotential(virtualPotential);
    }

    public static List<VirtualPotential> getVirtualPotentialList() {
        return PotentialManager.getInstance().virtualPotentialList;
    }

    public static VirtualPosition getHighestPotential(VirtualGameBoard virtualGameBoard)
    {
        VirtualPosition highestPotentialPosition = null;
        boolean potentialOverZeroFound = false;

        for (List<VirtualPosition> columnList:virtualGameBoard.getListOfColumns())
        {
            for (VirtualPosition virtualPosition:columnList)
            {
                // just for first value: set initial value (upper left corner, (0|0))
                if(highestPotentialPosition == null)
                    highestPotentialPosition = virtualPosition;

                // compare values, make current highest if greater than existing
                if(highestPotentialPosition.getTotalPotentialScore() < virtualPosition.getTotalPotentialScore())
                    highestPotentialPosition = virtualPosition;
                /*// if values are equal, prefer RIVAL-one, because blocking is better than //TODO: good idea, but should be solved by acting according to chain-length regarding scoring
                else if(highestPotentialPosition.getTotalPotentialScore() == virtualPosition.getTotalPotentialScore())
                    highestPotentialPosition = virtualPosition;*/

                if(virtualPosition.getTotalPotentialScore() > 0)
                    potentialOverZeroFound = true;
            }
        }

        // for further processing it is necessary to get a null value, so it is clear that nothing was sorted, etc.
        if(potentialOverZeroFound == false)
            highestPotentialPosition = null;

        return highestPotentialPosition;
    }

    public static void resetAllPotentialScores(VirtualGameBoard virtualGameBoard){
        for(VirtualPotential virtualPotential:virtualPotentialList)
            virtualPotential.getVirtualPosition().resetPotentialScore();
    }

    public static void reset(VirtualGameBoard virtualGameBoard) {
        // before list of potentials is cleared, reset all potential scores for referenced positions
        resetAllPotentialScores(virtualGameBoard);
        virtualPotentialList.clear();
    }

    public static void filterOutPotentialsWithGapGreaterThan(int maxGapDepth) {
        for (VirtualPotential virtualPotential : getInstance().virtualPotentialList) {
            // look for potentials which do not match the requirements and reset them
            if(virtualPotential.getGapDepthUnderneathPosition() > maxGapDepth)
                virtualPotential.getVirtualPosition().resetPotentialScore();
        }
    }
/*
    private static void scorePotentialsBetweenTwoSameCoins(VirtualGameBoard virtualGameBoard){
        for (List<VirtualPosition> columnList:virtualGameBoard.getListOfColumns())
        {
            for (VirtualPosition virtualPosition:columnList)
            {
    }*/
}
