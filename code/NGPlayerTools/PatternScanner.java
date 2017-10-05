package NGPlayerTools;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Nils on 01.05.2017.
 */
public class PatternScanner {
    public static List<VirtualPattern> scanForAllPatterns(VirtualGameBoard virtualGameBoard, boolean onlyPotentials){
        List<VirtualPattern> listOfVirtualPatterns = new ArrayList<>();

        // iterate through Array of Positions and check if neighbors are same-colored,
        // if so, follow this "direction" and count length of uninterrupted chain, but also consider potentials
        // in same direction (1-coin-gaps), consider only looking in one direction / only recognizing patterns once,
        // so maybe mark positions checked in one direction, e.g. vertical, then go on horizontal, etc.)

        // concat sub-results
        listOfVirtualPatterns.addAll(scanForAllVerticalPatterns(virtualGameBoard, onlyPotentials));
        listOfVirtualPatterns.addAll(scanForAllHorizontalPatterns(virtualGameBoard, onlyPotentials));
        listOfVirtualPatterns.addAll(scanForAllDiagonalPatterns(virtualGameBoard, onlyPotentials));

        return listOfVirtualPatterns;
    }

    public static List<VirtualPattern> scanForAllPatternsForColor(VirtualGameBoard virtualGameBoard, PlayerColor playerColor, boolean onlyPotentials){
        List<VirtualPattern> listOfVirtualPatterns = new ArrayList<>();

        // concat sub-results
        listOfVirtualPatterns.addAll(scanForVerticalPatternsForColor(virtualGameBoard, playerColor, onlyPotentials));
        listOfVirtualPatterns.addAll(scanForHorizontalPatternsForColor(virtualGameBoard, playerColor, onlyPotentials));
        listOfVirtualPatterns.addAll(scanForDiagonalPatternsForColor(virtualGameBoard, playerColor, onlyPotentials));

        return listOfVirtualPatterns;
    }

    public static  List<VirtualPattern> scanForAllVerticalPatterns(VirtualGameBoard virtualGameBoard, boolean onlyPotentials)
    {
        List<VirtualPattern> listOfVirtualPatterns = new ArrayList<>();
        listOfVirtualPatterns.addAll(scanForVerticalPatternsForColor(virtualGameBoard,PlayerColor.Own, onlyPotentials));
        listOfVirtualPatterns.addAll(scanForVerticalPatternsForColor(virtualGameBoard,PlayerColor.Rival, onlyPotentials));

        return  listOfVirtualPatterns;
    }

    public static  List<VirtualPattern> scanForAllHorizontalPatterns(VirtualGameBoard virtualGameBoard, boolean onlyPotentials)
    {
        List<VirtualPattern> listOfVirtualPatterns = new ArrayList<>();

        listOfVirtualPatterns.addAll(scanForHorizontalPatternsForColor(virtualGameBoard,PlayerColor.Own, onlyPotentials));
        listOfVirtualPatterns.addAll(scanForHorizontalPatternsForColor(virtualGameBoard,PlayerColor.Rival, onlyPotentials));

        return  listOfVirtualPatterns;
    }

    public static  List<VirtualPattern> scanForAllDiagonalPatterns(VirtualGameBoard virtualGameBoard, boolean onlyPotentials)
    {
        List<VirtualPattern> listOfVirtualPatterns = new ArrayList<>();

        listOfVirtualPatterns.addAll(scanForDiagonalPatternsForColor(virtualGameBoard,PlayerColor.Own, onlyPotentials));
        listOfVirtualPatterns.addAll(scanForDiagonalPatternsForColor(virtualGameBoard,PlayerColor.Rival, onlyPotentials));

        return  listOfVirtualPatterns;
    }

    private static List<VirtualPattern> scanForDiagonalPatternsForColor(VirtualGameBoard virtualGameBoard, PlayerColor playerColor, boolean onlyPotentials) {
        List<VirtualPattern> listOfVirtualPatterns = new ArrayList<>();

        listOfVirtualPatterns.addAll(scanForDiagonalPatternsForColorForDirection(virtualGameBoard, playerColor, onlyPotentials, ScanDirection.UpperLeftToLowerRight));
        listOfVirtualPatterns.addAll(scanForDiagonalPatternsForColorForDirection(virtualGameBoard, playerColor, onlyPotentials, ScanDirection.UpperRightToLowerLeft));

        return  listOfVirtualPatterns;
    }

    public static  List<VirtualPattern> scanForVerticalPatternsForColor(VirtualGameBoard virtualGameBoard, PlayerColor playerColor, boolean onlyPotentials)
    {
        List<VirtualPattern> listOfVirtualPatterns = new ArrayList<>();

        for (List<VirtualPosition> currentColumn:virtualGameBoard.getListOfColumns())
        {
            int iMax = currentColumn.size() - 1;
            boolean hasPotential = true;

            for (int i=0; i<=iMax; i++)
            {
                VirtualPosition currentPosition = currentColumn.get(i);

                // potential is initially given, this means the space over the existing coins in this column is free,
                // so you can drop new ones and extend possible patterns, etc.
                // --> if a coin is found which makes it impossible to gain a winning-row, potential is lost
                if(i == 0 && currentPosition.getPlayerColor() != PlayerColor.Empty)
                    hasPotential = false;

                if(currentPosition.getPlayerColor() != playerColor && currentPosition.getPlayerColor() != PlayerColor.Empty)
                    hasPotential = false;

                // look for correct color, then start scanning further
                if(currentPosition.getPlayerColor() == playerColor)
                {
                    // go on only if this position is not already part of before recognized pattern
                    if(currentPosition.isTmpIsPartOfVerticalPattern() == false)
                    {
                        VirtualPosition patternStartPosition = currentPosition;
                        VirtualPosition patternEndPosition = null;
                        int gap = 1;
                        int chainLength = 1;
                        boolean goOn = true;
                        VirtualPosition tmpNeighborPosition = null;

                        // if potential is required for collection and not given, stop here
                        if(onlyPotentials && hasPotential == false)
                            goOn = false;

                        while(goOn)
                        {
                            // check if you´re already at the edge of your column
                            if (i + gap <= iMax) {
                                tmpNeighborPosition = currentColumn.get(i + gap);

                                // if same color, chain
                                if(tmpNeighborPosition.getPlayerColor() == playerColor)
                                {
                                    // mark start-position as used
                                    currentPosition.setTmpIsPartOfVerticalPattern(true);

                                    // mark found position as used
                                    tmpNeighborPosition.setTmpIsPartOfVerticalPattern(true);

                                    // temporary save this found position as end-position of pattern
                                    patternEndPosition = tmpNeighborPosition;

                                    // increase number of found occurences
                                    chainLength++;
                                }
                                else
                                {
                                    // if end of chain reached, break loop
                                    goOn = false;
                                }

                            }
                            else
                            {
                                // if end reached, break loop
                                goOn = false;
                            }

                            // increase gap-counter
                            gap++;
                        }

                        // go on only if pattern was found
                        if(chainLength >= MyHelper.chainLengthTreshold_Vertical)
                        {
                            // generate new pattern-object and save it
                            listOfVirtualPatterns.add(new VirtualPattern(PatternType.Vertical, patternStartPosition, patternEndPosition, virtualGameBoard, chainLength, playerColor, ScanDirection.TopToBottom));
                        }
                    }
                }
            }
        }

        // reset all flags, previously set for temporary pattern recognition
        MyHelper.flushPatternFlagsOfBoard(virtualGameBoard);

        return listOfVirtualPatterns;
    }

    public static  List<VirtualPattern> scanForHorizontalPatternsForColor(VirtualGameBoard virtualGameBoard, PlayerColor playerColor, boolean onlyPotentials)
    {
        List<VirtualPattern> listOfVirtualPatterns = new ArrayList<>();

        for (List<VirtualPosition> currentRow:virtualGameBoard.getListOfRows())
        {
            int iMax = currentRow.size() - 1;

            for (int i=0; i<=iMax; i++)
            {
                VirtualPosition currentPosition = currentRow.get(i);

                // look for correct color, then start scanning further
                if(currentPosition.getPlayerColor() == playerColor)
                {
                    // go on only if this position is not already part of before recognized pattern
                    if(currentPosition.isTmpIsPartOfVerticalPattern() == false)
                    {
                        VirtualPosition patternStartPosition = currentPosition;
                        VirtualPosition patternEndPosition = null;
                        int gap = 1;
                        int chainLength = 1;
                        boolean goOn = true;
                        VirtualPosition tmpNextPosition = null;

                        // go on only if position isn´t part of pattern already
                        if(currentPosition.isTmpIsPartOfHorizontalPattern())
                            goOn = false;

                        while(goOn)
                        {
                            // check if you´re already at the edge of your row
                            if (i + gap <= iMax) {
                                tmpNextPosition = currentRow.get(i + gap);

                                // if same color, chain
                                if(tmpNextPosition.getPlayerColor() == playerColor)
                                {
                                    // mark start-position as used
                                    currentPosition.setTmpIsPartOfHorizontalPattern(true);

                                    // mark found position as used
                                    tmpNextPosition.setTmpIsPartOfHorizontalPattern(true);

                                    // temporary save this found position as end-position of pattern
                                    patternEndPosition = tmpNextPosition;

                                    // increase number of found occurences
                                    chainLength++;
                                }
                                else
                                {
                                    // if end of chain reached, break loop
                                    goOn = false;
                                }

                            }
                            else
                            {
                                // if end reached, break loop
                                goOn = false;
                            }

                            // increase gap-counter
                            gap++;
                        }

                        // go on only if pattern was found
                        if(chainLength >= MyHelper.chainLengthTreshold_Horizontal)
                        {
                            // generate new pattern-object and save it
                            listOfVirtualPatterns.add(new VirtualPattern(PatternType.Horizontal, patternStartPosition, patternEndPosition, virtualGameBoard, chainLength, playerColor, ScanDirection.LeftToRight));
                        }
                    }
                }
            }
        }

        // reset all flags, previously set for temporary pattern recognition
        MyHelper.flushPatternFlagsOfBoard(virtualGameBoard);

        return listOfVirtualPatterns;
    }

    private static List<VirtualPattern> scanForDiagonalPatternsForColorForDirection(VirtualGameBoard virtualGameBoard, PlayerColor playerColor, boolean onlyPotentials, ScanDirection scanDirection)
    {
        List<VirtualPosition> listOfStartPositions = MyHelper.defineStartPositionsForScanDirection(virtualGameBoard, scanDirection);
        List<VirtualPattern> listOfVirtualPatterns = new ArrayList<>();
        VirtualPosition[][] arrayOfPositions = virtualGameBoard.getArrayOfPositions();

        int sizeVertical = virtualGameBoard.getListOfRows().size();
        int sizeHorizontal = virtualGameBoard.getListOfColumns().size();

        for (VirtualPosition currentStartPosition:listOfStartPositions) {
            // start at position
            int iV = currentStartPosition.getVerticalPosition();
            int iH = currentStartPosition.getHorizontalPosition();

            // as long as position is not out of bounds, look for chains of equal colors heading from corner to corner or parallel
            while(MyHelper.checkPositionStillInBounds(iH,iV,sizeHorizontal-1, sizeVertical-1))
            {
                // get position by using indexes
                VirtualPosition currentPosition = arrayOfPositions[iH][iV];
                currentPosition.setPositionScanned(true);

                // look for correct color, then start scanning further
                if(currentPosition.getPlayerColor() == playerColor)
                {
                    // go on only if this position is not already part of before recognized pattern
                    if(currentPosition.isTmpIsPartOfDiagonalPattern() == false)
                    {
                        VirtualPosition patternStartPosition = currentPosition;
                        VirtualPosition patternEndPosition = null;
                        int gap = 1;
                        int iHGap = 0;
                        int iVGap = 0;
                        int chainLength = 1;
                        boolean goOn = true;
                        VirtualPosition tmpNextPosition = null;

                        // go on only if position isn´t part of pattern already
                        if(currentPosition.isTmpIsPartOfHorizontalPattern())
                            goOn = false;

                        while(goOn)
                        {
                            // increase to go down
                            iVGap = iV + gap;

                            if(scanDirection == ScanDirection.UpperLeftToLowerRight)
                                // increase to go right
                                iHGap = iH + gap;
                            else
                                // instead of increasing (going right), decrease and go left
                                iHGap = iH - gap;

                            // check if you´re already at the edge of your row
                            if (MyHelper.checkPositionStillInBounds(iHGap,iVGap,sizeHorizontal-1, sizeVertical-1))
                            {
                                tmpNextPosition = arrayOfPositions[iHGap][iVGap];

                                // if same color, chain
                                if(tmpNextPosition.getPlayerColor() == playerColor)
                                {
                                    // mark start-position as used
                                    currentPosition.setTmpIsPartOfDiagonalPattern(true);

                                    // mark found position as used
                                    tmpNextPosition.setTmpIsPartOfDiagonalPattern(true);

                                    // temporary save this found position as end-position of pattern
                                    patternEndPosition = tmpNextPosition;

                                    // increase number of found occurences
                                    chainLength++;
                                }
                                else
                                {
                                    // if end of chain reached, break loop
                                    goOn = false;
                                }

                            }
                            else
                            {
                                // if end of bounds reached, break loop
                                goOn = false;
                            }

                            // increase gap-counter
                            gap++;

                            // print board for debugging
                            ////System.out.println(virtualGameBoard);
                        }

                        // go on only if pattern was found
                        if(chainLength >= MyHelper.chainLengthTreshold_Diagonal)
                        {
                            // generate new pattern-object and save it
                            listOfVirtualPatterns.add(new VirtualPattern(PatternType.Diagonal, patternStartPosition, patternEndPosition, virtualGameBoard, chainLength, playerColor, scanDirection));
                        }
                    }
                }

                if(scanDirection == ScanDirection.UpperRightToLowerLeft)
                    iH--;
                else
                    iH++;

                iV++;
            }

            // reset all flags, previously set for temporary pattern recognition
            MyHelper.flushPatternFlagsOfBoard(virtualGameBoard);
        }

        return listOfVirtualPatterns;
    }
}