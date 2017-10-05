package CCPlayerPackage;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Carlo on 05.05.2017.
 */
public class WinSituationDetector {

    Manager manager;

    public WinSituationDetector(Manager manager) {
        this.manager = manager;
    }

    //virtualGameBoard
    public List<DetectedChain> ownDetectedChains = new ArrayList<>();
    public List<DetectedChain> rivalDetectedChains = new ArrayList<>();

    public List<DetectedChain> getOwnDetectedChains() {
        return ownDetectedChains;
    }

    public List<DetectedChain> getRivalDetectedChains() {
        return rivalDetectedChains;
    }

    public void checkAllChains() {
        ownDetectedChains = new ArrayList<>();
        rivalDetectedChains = new ArrayList<>();

        checkHorizontalChains(true);
        checkVerticalChains(true);
        checkDiagonalChains(true);
        printFoundChains();
    }

    public void checkHorizontalChains(boolean virtualGameBoard) {

        boolean ownRowDetected = false;
        boolean rivalRowDetected = false;

        DetectedChain detectedChain = new DetectedChain();

        //OWN
        for (int i = 6; i >= 0; i--) {
            for (int j = 0; j < 7; j++) {
                if (virtualGameBoard) {
                    if (manager.getPlayerEnumAtPosition(i, j) == PlayerEnum.OWN) {
                        //check if new row
                        if (detectedChain.getStartPositionRow() != i) {
                            ownRowDetected = false;
                        }
                        //Bereits ein/mehrere Coins vorliegend
                        if (ownRowDetected == true) {
                            detectedChain.setSize(detectedChain.getSize() + 1);
                            //Ende der Row
                            if (j == 6) {
                                safeFoundChain(detectedChain, i, j, PlayerEnum.OWN, virtualGameBoard);
                                detectedChain = new DetectedChain();
                                ownRowDetected = false;
                            }
                        } else {
                            //Erster Coin gefunden
                            ownRowDetected = true;
                            //neue Chain mit Werten initialiseren
                            detectedChain.resetSize();
                            detectedChain.setStartPositionRow(i);
                            detectedChain.setStartPositionCol(j);
                            detectedChain.setSize(detectedChain.getSize() + 1);
                            detectedChain.setChainType(ChainType.HORIZONTAL);
                        }
                    } else
                    //Kein Coin (mehr) gefunden
                    {
                        ownRowDetected = false;
                        if (detectedChain.getSize() >= 2) {
                            safeFoundChain(detectedChain, i, j - 1, PlayerEnum.OWN, virtualGameBoard);
                            detectedChain = new DetectedChain();
                        }
                    }
                }
            }
        }

        //RIVAL
        for (int i = 6; i >= 0; i--) {
            for (int j = 0; j < 7; j++) {
                if (virtualGameBoard) {
                    if (manager.getPlayerEnumAtPosition(i, j) == PlayerEnum.RIVAL) {
                        //check if new row
                        if (detectedChain.getStartPositionRow() != i) {
                            rivalRowDetected = false;
                        }
                        //Bereits ein/mehrere Coins vorliegend
                        if (rivalRowDetected == true) {
                            detectedChain.setSize(detectedChain.getSize() + 1);
                            //Ende der Row
                            if (j == 6) {
                                safeFoundChain(detectedChain, i, j, PlayerEnum.RIVAL, virtualGameBoard);
                                detectedChain = new DetectedChain();
                                rivalRowDetected = false;
                            }
                        } else {
                            //Erster Coin gefunden
                            rivalRowDetected = true;
                            //neue Chain mit Werten initialiseren
                            detectedChain.resetSize();
                            detectedChain.setStartPositionRow(i);
                            detectedChain.setStartPositionCol(j);
                            detectedChain.setSize(detectedChain.getSize() + 1);
                            detectedChain.setChainType(ChainType.HORIZONTAL);
                        }
                    } else
                    //Kein Coin (mehr) gefunden
                    {
                        rivalRowDetected = false;
                        if (detectedChain.getSize() >= 2) {
                            safeFoundChain(detectedChain, i, j - 1, PlayerEnum.RIVAL, virtualGameBoard);
                            detectedChain = new DetectedChain();
                        }
                    }
                }
            }
        }
    }

    public void checkVerticalChains(boolean virtualGameBoard) {

        boolean ownRowDetected = false;
        boolean rivalRowDetected = false;

        DetectedChain detectedChain = new DetectedChain();

        //OWN
        for (int i = 0; i < 7; i++) {
            for (int j = 0; j < 7; j++) {
                if (virtualGameBoard) {
                    if (manager.getPlayerEnumAtPosition(j, i) == PlayerEnum.OWN) {
                        //check if new col
                        if (detectedChain.getStartPositionCol() != i) {
                            ownRowDetected = false;
                        }
                        //Bereits ein/mehrere Coins vorliegend
                        if (ownRowDetected == true) {
                            detectedChain.setSize(detectedChain.getSize() + 1);
                            //Ende der Col
                            if (j == 6) {
                                safeFoundChain(detectedChain, j, i, PlayerEnum.OWN, virtualGameBoard);
                                detectedChain = new DetectedChain();
                                ownRowDetected = false;
                            }
                        } else {
                            //Erster Coin gefunden
                            ownRowDetected = true;
                            //neue Chain mit Werten initialiseren
                            detectedChain.resetSize();
                            detectedChain.setStartPositionRow(j);
                            detectedChain.setStartPositionCol(i);
                            detectedChain.setSize(detectedChain.getSize() + 1);
                            detectedChain.setChainType(ChainType.VERTICAL);
                        }
                    } else
                    //Kein Coin (mehr) gefunden
                    {
                        ownRowDetected = false;
                        if (detectedChain.getSize() >= 2) {
                            safeFoundChain(detectedChain, j - 1, i, PlayerEnum.OWN, virtualGameBoard);
                            detectedChain = new DetectedChain();
                        }
                    }
                }
            }
        }

        //RIVAL
        for (int i = 0; i < 7; i++) {
            for (int j = 0; j < 7; j++) {
                if (virtualGameBoard) {
                    if (manager.getPlayerEnumAtPosition(j, i) == PlayerEnum.RIVAL) {
                        //check if new col
                        if (detectedChain.getStartPositionCol() != i) {
                            rivalRowDetected = false;
                        }
                        //Bereits ein/mehrere Coins vorliegend
                        if (rivalRowDetected == true) {
                            detectedChain.setSize(detectedChain.getSize() + 1);
                            //Ende der Col
                            if (j == 6) {
                                safeFoundChain(detectedChain, j, i, PlayerEnum.RIVAL, virtualGameBoard);
                                detectedChain = new DetectedChain();
                                rivalRowDetected = false;
                            }
                        } else {
                            //Erster Coin gefunden
                            rivalRowDetected = true;
                            //neue Chain mit Werten initialiseren
                            detectedChain.resetSize();
                            detectedChain.setStartPositionRow(j);
                            detectedChain.setStartPositionCol(i);
                            detectedChain.setSize(detectedChain.getSize() + 1);
                            detectedChain.setChainType(ChainType.VERTICAL);
                        }
                    } else
                    //Kein Coin (mehr) gefunden
                    {
                        rivalRowDetected = false;
                        if (detectedChain.getSize() >= 2) {
                            safeFoundChain(detectedChain, j - 1, i, PlayerEnum.RIVAL, virtualGameBoard);
                            detectedChain = new DetectedChain();
                        }
                    }
                }
            }
        }
    }

    private void checkDiagonalChains(boolean virtualGameBoard) {
        for (int i = 6; i >= 0; i--) {
            for (int j = 0; j < 7; j++) {
                if (virtualGameBoard) {
                    if (manager.getPlayerEnumAtPosition(i, j) == PlayerEnum.OWN) {
                        checkDiagonalTopRight(i, j, PlayerEnum.OWN, virtualGameBoard);
                        checkDiagonalTopLeft(i, j, PlayerEnum.OWN, virtualGameBoard);
                        checkDiagonalBottomRight(i, j, PlayerEnum.OWN, virtualGameBoard);
                        checkDiagonalBottomLeft(i, j, PlayerEnum.OWN, virtualGameBoard);
                    } else if (manager.getPlayerEnumAtPosition(i, j) == PlayerEnum.RIVAL) {
                        checkDiagonalTopRight(i, j, PlayerEnum.RIVAL, virtualGameBoard);
                        checkDiagonalTopLeft(i, j, PlayerEnum.RIVAL, virtualGameBoard);
                        checkDiagonalBottomRight(i, j, PlayerEnum.RIVAL, virtualGameBoard);
                        checkDiagonalBottomLeft(i, j, PlayerEnum.RIVAL, virtualGameBoard);
                    }
                }
            }
        }
    }

    private void checkDiagonalTopRight(int i, int j, PlayerEnum playerEnum, boolean virtualGameBoard) {
        int tempI = i;
        int tempJ = j;
        boolean chainDetected = false;
        DetectedChain detectedChain = new DetectedChain();

        if (virtualGameBoard) {
            while (tempI > 0 && tempJ < 6) {
                if (manager.getPlayerEnumAtPosition(tempI - 1, tempJ + 1) != null) {
                    if (manager.getPlayerEnumAtPosition(tempI - 1, tempJ + 1) == playerEnum) {
                        if (chainDetected == false) {
                            chainDetected = true;
                            detectedChain.setStartPositionRow(tempI);
                            detectedChain.setStartPositionCol(tempJ);
                            detectedChain.setSize(detectedChain.getSize() + 2);
                            detectedChain.setChainType(ChainType.DIAGONAL_TOP_RIGHT);
                        } else {
                            detectedChain.setSize(detectedChain.getSize() + 1);
                        }
                    } else {
                        if (detectedChain.getSize() >= 2) {
                            detectedChain.setEndPositionRow(tempI);
                            detectedChain.setEndPositionCol(tempJ);
                            if (playerEnum == PlayerEnum.OWN) {
                                ownDetectedChains.add(detectedChain);
                            } else {
                                rivalDetectedChains.add(detectedChain);
                            }
                        }
                        return;
                    }
                }
                tempI--;
                tempJ++;
            }

            if (detectedChain.getSize() >= 2) {
                detectedChain.setEndPositionRow(tempI);
                detectedChain.setEndPositionCol(tempJ);
                if (playerEnum == PlayerEnum.OWN) {
                    ownDetectedChains.add(detectedChain);
                } else {
                    rivalDetectedChains.add(detectedChain);
                }
            }
        }
    }

    private void checkDiagonalTopLeft(int i, int j, PlayerEnum playerEnum, boolean virtualGameBoard) {
        int tempI = i;
        int tempJ = j;
        boolean chainDetected = false;
        DetectedChain detectedChain = new DetectedChain();

        if (virtualGameBoard) {
            while (tempI > 0 && tempJ > 0) {
                if (manager.getPlayerEnumAtPosition(tempI - 1, tempJ - 1) != null) {
                    if (manager.getPlayerEnumAtPosition(tempI - 1, tempJ - 1) == playerEnum) {
                        if (chainDetected == false) {
                            chainDetected = true;
                            detectedChain.setStartPositionRow(tempI);
                            detectedChain.setStartPositionCol(tempJ);
                            detectedChain.setSize(detectedChain.getSize() + 2);
                            detectedChain.setChainType(ChainType.DIAGONAL_TOP_LEFT);
                        } else {
                            detectedChain.setSize(detectedChain.getSize() + 1);
                        }
                    } else {
                        if (detectedChain.getSize() >= 2) {
                            detectedChain.setEndPositionRow(tempI);
                            detectedChain.setEndPositionCol(tempJ);
                            if (playerEnum == PlayerEnum.OWN) {
                                ownDetectedChains.add(detectedChain);
                            } else {
                                rivalDetectedChains.add(detectedChain);
                            }
                        }
                        return;
                    }
                }
                tempI--;
                tempJ--;
            }

            if (detectedChain.getSize() >= 2) {
                detectedChain.setEndPositionRow(tempI);
                detectedChain.setEndPositionCol(tempJ);
                if (playerEnum == PlayerEnum.OWN) {
                    ownDetectedChains.add(detectedChain);
                } else {
                    rivalDetectedChains.add(detectedChain);
                }
            }
        }
    }

    private void checkDiagonalBottomRight(int i, int j, PlayerEnum playerEnum, boolean virtualGameBoard) {
        int tempI = i;
        int tempJ = j;
        boolean chainDetected = false;
        DetectedChain detectedChain = new DetectedChain();

        if (virtualGameBoard) {
            while (tempI < 6 && tempJ < 6) {
                if (manager.getPlayerEnumAtPosition(tempI + 1, tempJ + 1) != null) {
                    if (manager.getPlayerEnumAtPosition(tempI + 1, tempJ + 1) == playerEnum) {
                        if (chainDetected == false) {
                            chainDetected = true;
                            detectedChain.setStartPositionRow(tempI);
                            detectedChain.setStartPositionCol(tempJ);
                            detectedChain.setSize(detectedChain.getSize() + 2);
                            detectedChain.setChainType(ChainType.DIAGONAL_BOTTOM_RIGHT);
                        } else {
                            detectedChain.setSize(detectedChain.getSize() + 1);
                        }
                    } else {
                        if (detectedChain.getSize() >= 2) {
                            detectedChain.setEndPositionRow(tempI);
                            detectedChain.setEndPositionCol(tempJ);
                            if (playerEnum == PlayerEnum.OWN) {
                                ownDetectedChains.add(detectedChain);
                            } else {
                                rivalDetectedChains.add(detectedChain);
                            }
                        }
                        return;
                    }
                }
                tempI++;
                tempJ++;
            }

            if (detectedChain.getSize() >= 2) {
                detectedChain.setEndPositionRow(tempI);
                detectedChain.setEndPositionCol(tempJ);
                if (playerEnum == PlayerEnum.OWN) {
                    ownDetectedChains.add(detectedChain);
                } else {
                    rivalDetectedChains.add(detectedChain);
                }
            }
        }
    }

    private void checkDiagonalBottomLeft(int i, int j, PlayerEnum playerEnum, boolean virtualGameBoard) {
        int tempI = i;
        int tempJ = j;
        boolean chainDetected = false;
        DetectedChain detectedChain = new DetectedChain();

        if (virtualGameBoard) {
            while (tempI < 6 && tempJ > 0) {
                if (manager.getPlayerEnumAtPosition(tempI + 1, tempJ - 1) != null) {
                    if (manager.getPlayerEnumAtPosition(tempI + 1, tempJ - 1) == playerEnum) {
                        if (chainDetected == false) {
                            chainDetected = true;
                            detectedChain.setStartPositionRow(tempI);
                            detectedChain.setStartPositionCol(tempJ);
                            detectedChain.setSize(detectedChain.getSize() + 2);
                            detectedChain.setChainType(ChainType.DIAGONAL_BOTTOM_LEFT);
                        } else {
                            detectedChain.setSize(detectedChain.getSize() + 1);
                        }
                    } else {
                        if (detectedChain.getSize() >= 2) {
                            detectedChain.setEndPositionRow(tempI);
                            detectedChain.setEndPositionCol(tempJ);
                            if (playerEnum == PlayerEnum.OWN) {
                                ownDetectedChains.add(detectedChain);
                            } else {
                                rivalDetectedChains.add(detectedChain);
                            }
                        }
                        return;
                    }
                }
                tempI++;
                tempJ--;
            }

            if (detectedChain.getSize() >= 2) {
                detectedChain.setEndPositionRow(tempI);
                detectedChain.setEndPositionCol(tempJ);
                if (playerEnum == PlayerEnum.OWN) {
                    ownDetectedChains.add(detectedChain);
                } else {
                    rivalDetectedChains.add(detectedChain);
                }
            }
        }
    }

    private void safeFoundChain(DetectedChain detectedChain, int i, int j, PlayerEnum playerEnum, boolean virtualGameBoard) {
        if (playerEnum == PlayerEnum.OWN) {
            detectedChain.setEndPositionRow(i);
            detectedChain.setEndPositionCol(j);
            if (virtualGameBoard) {
                ownDetectedChains.add(detectedChain);
            }
        } else {
            detectedChain.setEndPositionRow(i);
            detectedChain.setEndPositionCol(j);
            if (virtualGameBoard) {
                rivalDetectedChains.add(detectedChain);
            }
        }
    }

    private void printFoundChains() {
        for (int k = 0; k < ownDetectedChains.size(); k++) {
            if (ownDetectedChains.get(k).getStartPositionRow() == ownDetectedChains.get(k).getEndPositionRow()) {
                ////System.out.println("OWN HORIZONTAL CHAIN: ");
            } else if (ownDetectedChains.get(k).getStartPositionCol() == ownDetectedChains.get(k).getEndPositionCol()) {
                ////System.out.println("OWN VERTICAL CHAIN: ");
            } else {
                if (ownDetectedChains.get(k).getStartPositionCol() < ownDetectedChains.get(k).getEndPositionCol()) {
                    ////System.out.println("OWN LEFT-TO-RIGHT DIAGONAL CHAIN: ");
                } else {
                    ////System.out.println("OWN RIGHT-TO-LEFT DIAGONAL CHAIN: ");
                }
            }
            ////System.out.println("StartpositionRow: " + ownDetectedChains.get(k).getStartPositionRow() + " / StartpositionCol: " + ownDetectedChains.get(k).getStartPositionCol());
            ////System.out.println("EndpositionRow: " + ownDetectedChains.get(k).getEndPositionRow() + " / EndpositionCol: " + ownDetectedChains.get(k).getEndPositionCol());
            ////System.out.println("Größe: " + ownDetectedChains.get(k).getSize());
        }
        ////System.out.println("-----------------------------------------------");
        for (int k = 0; k < rivalDetectedChains.size(); k++) {
            if (rivalDetectedChains.get(k).getStartPositionRow() == rivalDetectedChains.get(k).getEndPositionRow()) {
                ////System.out.println("RIVAL HORIZONTAL CHAIN: ");
            } else if (rivalDetectedChains.get(k).getStartPositionCol() == rivalDetectedChains.get(k).getEndPositionCol()) {
                ////System.out.println("RIVAL VERTICAL CHAIN: ");
            } else {
                if (rivalDetectedChains.get(k).getStartPositionCol() < rivalDetectedChains.get(k).getEndPositionCol()) {
                    ////System.out.println("RIVAL LEFT-TO-RIGHT DIAGONAL CHAIN: ");
                } else {
                    ////System.out.println("RIVAL RIGHT-TO-LEFT DIAGONAL CHAIN: ");
                }
            }
            ////System.out.println("StartpositionRow: " + rivalDetectedChains.get(k).getStartPositionRow() + " / StartpositionCol: " + rivalDetectedChains.get(k).getStartPositionCol());
            ////System.out.println("EndpositionRow: " + rivalDetectedChains.get(k).getEndPositionRow() + " / EndpositionCol: " + rivalDetectedChains.get(k).getEndPositionCol());
            ////System.out.println("Größe: " + rivalDetectedChains.get(k).getSize());
        }
        ////System.out.println("-----------------------------------------------");
    }

    private PlayerEnum getNextPlayerEnum(int i, int j) {
        return manager.getPlayerEnumAtPosition(i, j + 1);
    }

}


