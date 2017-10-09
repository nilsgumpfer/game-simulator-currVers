package CCPlayerTools;

import basic.Move;
import basic.CCPlayer;
import basic.Position;

import java.util.*;

/**
 * Created by Carlo on 07.05.2017.
 */
public class MoveGenerator {

    Position position;
    List<Move> moves;
    List<BlockedColumnObject> blockedColumnObjects = new ArrayList<>();

    Move plannedMove;
    CCPlayer ownPlayer;
    Manager manager;
    WinSituationDetector winSituationDetector;

    public MoveGenerator(Position p, List<Move> moves, Manager manager, WinSituationDetector winSituationDetector, CCPlayer ownPlayer) {
        position = p;
        this.moves = moves;
        this.manager = manager;
        this.winSituationDetector = winSituationDetector;
        this.ownPlayer = ownPlayer;
    }

    //Eigene 2er/3er Reihe zum Gewinn führen
    public Move getOwnWinMove(List<DetectedChain> ownDetectedChains,PlayerEnum playerEnum) {
        List<DetectedChain> winnableChains = new ArrayList<>();

        //get >= 2-coin chains
        for (int i = 0; i < ownDetectedChains.size(); i++) {
            if (ownDetectedChains.get(i).getSize() >= 2) {
                winnableChains.add(ownDetectedChains.get(i));
            }
        }

        //VERTICAL
        for (int i = 0; i < winnableChains.size(); i++) {
            if (winnableChains.get(i).getChainType() == ChainType.VERTICAL) {
                if (winnableChains.get(i).getSize() == 3) {
                    if (winnableChains.get(i).getStartPositionRow() - 1 >= 0) {
                        if (manager.getPlayerEnumAtPosition(winnableChains.get(i).getStartPositionRow() - 1, winnableChains.get(i).getStartPositionCol()) != null) {
                            if (manager.getPlayerEnumAtPosition(winnableChains.get(i).getStartPositionRow() - 1, winnableChains.get(i).getStartPositionCol()) == PlayerEnum.EMPTY) {
                                return getMoveOfColumn(winnableChains.get(i).getStartPositionCol());
                            }
                        }
                    }
                }
            }
        }

        //HORIZONTAL --> win 3 coin chain
        for (int i = 0; i < winnableChains.size(); i++) {
            //SET ON LEFT
            if (winnableChains.get(i).getChainType() == ChainType.HORIZONTAL) {
                if (winnableChains.get(i).getSize() == 3) {
                    //place left
                    if (winnableChains.get(i).getStartPositionCol() - 1 >= 0) {
                        if (manager.getPlayerEnumAtPosition(winnableChains.get(i).getStartPositionRow(), winnableChains.get(i).getStartPositionCol() - 1) != null) {
                            if (manager.getPlayerEnumAtPosition(winnableChains.get(i).getStartPositionRow(), winnableChains.get(i).getStartPositionCol() - 1) == PlayerEnum.EMPTY) {
                                if (winnableChains.get(i).getStartPositionRow() == 6) {
                                    //WIN IT!
                                    return getMoveOfColumn(winnableChains.get(i).getStartPositionCol() - 1);
                                } else {
                                    //check falling
                                    if (manager.getPlayerEnumAtPosition(winnableChains.get(i).getStartPositionRow() + 1, winnableChains.get(i).getStartPositionCol() - 1) != null) {
                                        if (manager.getPlayerEnumAtPosition(winnableChains.get(i).getStartPositionRow() + 1, winnableChains.get(i).getStartPositionCol() - 1) != PlayerEnum.EMPTY) {
                                            return getMoveOfColumn(winnableChains.get(i).getStartPositionCol() - 1);
                                        }
                                    }
                                }
                            }
                        }
                    }

                    //SET ON RIGHT
                    if (winnableChains.get(i).getEndPositionCol() + 1 < 7) {
                        if (manager.getPlayerEnumAtPosition(winnableChains.get(i).getEndPositionRow(), winnableChains.get(i).getEndPositionCol() + 1) != null) {
                            if (manager.getPlayerEnumAtPosition(winnableChains.get(i).getEndPositionRow(), winnableChains.get(i).getEndPositionCol() + 1) == PlayerEnum.EMPTY) {
                                if (winnableChains.get(i).getEndPositionRow() == 6) {
                                    return getMoveOfColumn(winnableChains.get(i).getEndPositionCol() + 1);
                                } else {
                                    //check falling
                                    if (manager.getPlayerEnumAtPosition(winnableChains.get(i).getEndPositionRow() + 1, winnableChains.get(i).getEndPositionCol() + 1) != null) {
                                        if (manager.getPlayerEnumAtPosition(winnableChains.get(i).getEndPositionRow() + 1, winnableChains.get(i).getEndPositionCol() + 1) != PlayerEnum.EMPTY) {
                                            return getMoveOfColumn(winnableChains.get(i).getEndPositionCol() + 1);
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }

        //HORIZONTAL --> complete 2coin-chain with missing coin
        for (int i = 0; i < winnableChains.size(); i++) {
            if (winnableChains.get(i).getChainType() == ChainType.HORIZONTAL) {
                if (winnableChains.get(i).getSize() == 2) {
                    //left
                    if (winnableChains.get(i).getStartPositionCol() - 2 >= 0) {
                        if (manager.getPlayerEnumAtPosition(winnableChains.get(i).getStartPositionRow(), winnableChains.get(i).getStartPositionCol() - 2) != null) {
                            if (manager.getPlayerEnumAtPosition(winnableChains.get(i).getStartPositionRow(), winnableChains.get(i).getStartPositionCol() - 2) == playerEnum) {
                                if (manager.getPlayerEnumAtPosition(winnableChains.get(i).getStartPositionRow(), winnableChains.get(i).getStartPositionCol() - 1) == PlayerEnum.EMPTY) {
                                    //check falling
                                    if (winnableChains.get(i).getStartPositionRow() == 6) {
                                        return getMoveOfColumn(winnableChains.get(i).getStartPositionCol() - 1);
                                    }
                                    if (winnableChains.get(i).getStartPositionRow() + 1 < 7 && winnableChains.get(i).getStartPositionCol() - 1 >= 0) {
                                        if (manager.getPlayerEnumAtPosition(winnableChains.get(i).getStartPositionRow() + 1, winnableChains.get(i).getStartPositionCol() - 1) != PlayerEnum.EMPTY) {
                                            return getMoveOfColumn(winnableChains.get(i).getStartPositionCol() - 1);
                                        }
                                    }
                                }
                            }
                        }
                    }

                    //right
                    if (winnableChains.get(i).getEndPositionCol() + 2 < 7) {
                        if (manager.getPlayerEnumAtPosition(winnableChains.get(i).getEndPositionRow(), winnableChains.get(i).getEndPositionCol() + 2) != null) {
                            if (manager.getPlayerEnumAtPosition(winnableChains.get(i).getEndPositionRow(), winnableChains.get(i).getEndPositionCol() + 2) == playerEnum) {
                                if (manager.getPlayerEnumAtPosition(winnableChains.get(i).getEndPositionRow(), winnableChains.get(i).getEndPositionCol() + 1) == PlayerEnum.EMPTY) {
                                    //check falling
                                    if (winnableChains.get(i).getEndPositionRow() == 6) {
                                        return getMoveOfColumn(winnableChains.get(i).getStartPositionCol() + 1);
                                    }
                                    if (winnableChains.get(i).getEndPositionRow() + 1 < 7 && winnableChains.get(i).getEndPositionCol() + 1 < 7) {
                                        if (manager.getPlayerEnumAtPosition(winnableChains.get(i).getEndPositionRow() + 1, winnableChains.get(i).getEndPositionCol() + 1) != PlayerEnum.EMPTY) {
                                            return getMoveOfColumn(winnableChains.get(i).getEndPositionCol() + 1);
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }

        //DIAGONAL_TOP_LEFT - complete 3 coins
        for (int i = 0; i < winnableChains.size(); i++) {
            if (winnableChains.get(i).getChainType() == ChainType.DIAGONAL_TOP_LEFT) {
                if (winnableChains.get(i).getSize() == 3) {
                    if (winnableChains.get(i).getEndPositionCol() - 1 >= 0 && winnableChains.get(i).getEndPositionRow() - 1 >= 0) {
                        if (manager.getPlayerEnumAtPosition(winnableChains.get(i).getEndPositionRow() - 1, winnableChains.get(i).getEndPositionCol() - 1) != null) {
                            if (manager.getPlayerEnumAtPosition(winnableChains.get(i).getEndPositionRow() - 1, winnableChains.get(i).getEndPositionCol() - 1) == PlayerEnum.EMPTY) {
                                //check falling
                                if (manager.getPlayerEnumAtPosition(winnableChains.get(i).getEndPositionRow(), winnableChains.get(i).getEndPositionCol() - 1) != PlayerEnum.EMPTY) {
                                    return getMoveOfColumn(winnableChains.get(i).getEndPositionCol() - 1);
                                }
                            }
                        }
                    }
                }
            }
        }

        //DIAGONAL_TOP_RIGHT - complete 3 coins
        for (int i = 0; i < winnableChains.size(); i++) {
            if (winnableChains.get(i).getChainType() == ChainType.DIAGONAL_TOP_RIGHT) {
                if (winnableChains.get(i).getSize() == 3) {
                    if (winnableChains.get(i).getEndPositionCol() + 1 < 7 && winnableChains.get(i).getEndPositionRow() - 1 >= 0) {
                        if (manager.getPlayerEnumAtPosition(winnableChains.get(i).getEndPositionRow() - 1, winnableChains.get(i).getEndPositionCol() + 1) != null) {
                            if (manager.getPlayerEnumAtPosition(winnableChains.get(i).getEndPositionRow() - 1, winnableChains.get(i).getEndPositionCol() + 1) == PlayerEnum.EMPTY) {
                                //check falling
                                if (manager.getPlayerEnumAtPosition(winnableChains.get(i).getEndPositionRow(), winnableChains.get(i).getEndPositionCol() + 1) != PlayerEnum.EMPTY) {
                                    return getMoveOfColumn(winnableChains.get(i).getEndPositionCol() + 1);
                                }
                            }
                        }
                    }
                }
            }
        }

        //DIAGONAL_BOTTOM_RIGHT - complete 3 coins
        for (int i = 0; i < winnableChains.size(); i++) {
            if (winnableChains.get(i).getChainType() == ChainType.DIAGONAL_BOTTOM_RIGHT) {
                if (winnableChains.get(i).getSize() == 3) {
                    if (winnableChains.get(i).getEndPositionCol() + 1 < 7 && winnableChains.get(i).getEndPositionRow() + 1 < 7) {
                        if (manager.getPlayerEnumAtPosition(winnableChains.get(i).getEndPositionRow() + 1, winnableChains.get(i).getEndPositionCol() + 1) != null) {
                            if (manager.getPlayerEnumAtPosition(winnableChains.get(i).getEndPositionRow() + 1, winnableChains.get(i).getEndPositionCol() + 1) == PlayerEnum.EMPTY) {
                                //check falling
                                if (winnableChains.get(i).getEndPositionRow() + 1 == 6) {
                                    return getMoveOfColumn(winnableChains.get(i).getEndPositionCol() + 1);
                                } else {
                                    if (winnableChains.get(i).getEndPositionRow() + 2 < 7 && winnableChains.get(i).getEndPositionCol() + 1 < 7) {
                                        if (manager.getPlayerEnumAtPosition(winnableChains.get(i).getEndPositionRow() + 2, winnableChains.get(i).getEndPositionCol() + 1) != PlayerEnum.EMPTY) {
                                            return getMoveOfColumn(winnableChains.get(i).getEndPositionCol() + 1);
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }

        //DIAGONAL_BOTTOM_LEFT - complete 3 coins
        for (int i = 0; i < winnableChains.size(); i++) {
            if (winnableChains.get(i).getChainType() == ChainType.DIAGONAL_BOTTOM_LEFT) {
                if (winnableChains.get(i).getSize() == 3) {
                    if (winnableChains.get(i).getEndPositionCol() - 1 >= 0 && winnableChains.get(i).getEndPositionRow() + 1 < 7) {
                        if (manager.getPlayerEnumAtPosition(winnableChains.get(i).getEndPositionRow() + 1, winnableChains.get(i).getEndPositionCol() - 1) != null) {
                            if (manager.getPlayerEnumAtPosition(winnableChains.get(i).getEndPositionRow() + 1, winnableChains.get(i).getEndPositionCol() - 1) == PlayerEnum.EMPTY) {
                                //check falling
                                if (winnableChains.get(i).getEndPositionRow() + 1 == 6) {
                                    return getMoveOfColumn(winnableChains.get(i).getEndPositionCol() - 1);
                                } else {
                                    if (manager.getPlayerEnumAtPosition(winnableChains.get(i).getEndPositionRow() + 2, winnableChains.get(i).getEndPositionCol() - 1) != PlayerEnum.EMPTY) {
                                        return getMoveOfColumn(winnableChains.get(i).getEndPositionCol() - 1);
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }

        //DIAGONAL TOP RIGHT - 2 coins with empty fields on left & right
        for (int i = 0; i < winnableChains.size(); i++) {
            if (winnableChains.get(i).getChainType() == ChainType.DIAGONAL_TOP_RIGHT && winnableChains.get(i).getSize() == 2) {
                if (winnableChains.get(i).getEndPositionCol() + 1 < 7 && winnableChains.get(i).getEndPositionRow() - 1 >= 0) {
                    if (manager.getPlayerEnumAtPosition(winnableChains.get(i).getEndPositionRow() - 1, winnableChains.get(i).getEndPositionCol() + 1) != null) {
                        if (manager.getPlayerEnumAtPosition(winnableChains.get(i).getEndPositionRow() - 1, winnableChains.get(i).getEndPositionCol() + 1) == PlayerEnum.EMPTY) {
                            if (winnableChains.get(i).getEndPositionCol() + 2 < 7 && winnableChains.get(i).getEndPositionRow() - 2 >= 0) {
                                if (manager.getPlayerEnumAtPosition(winnableChains.get(i).getEndPositionRow() - 2, winnableChains.get(i).getEndPositionCol() + 2) != null) {
                                    if (manager.getPlayerEnumAtPosition(winnableChains.get(i).getEndPositionRow() - 2, winnableChains.get(i).getEndPositionCol() + 2) == playerEnum) {
                                        //check falling
                                        if (manager.getPlayerEnumAtPosition(winnableChains.get(i).getEndPositionRow(), winnableChains.get(i).getEndPositionCol() + 1) != PlayerEnum.EMPTY) {
                                            return getMoveOfColumn(winnableChains.get(i).getEndPositionCol() + 1);
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }

        //DIAGONAL TOP LEFT - 2 coins with empty fields on left & right
        for (int i = 0; i < winnableChains.size(); i++) {
            if (winnableChains.get(i).getChainType() == ChainType.DIAGONAL_TOP_RIGHT && winnableChains.get(i).getSize() == 2) {
                if (winnableChains.get(i).getEndPositionCol() - 1 >= 0 && winnableChains.get(i).getEndPositionRow() - 1 >= 0) {
                    if (manager.getPlayerEnumAtPosition(winnableChains.get(i).getEndPositionRow() - 1, winnableChains.get(i).getEndPositionCol() - 1) != null) {
                        if (manager.getPlayerEnumAtPosition(winnableChains.get(i).getEndPositionRow() - 1, winnableChains.get(i).getEndPositionCol() - 1) == PlayerEnum.EMPTY) {
                            if (winnableChains.get(i).getEndPositionCol() - 2 >= 0 && winnableChains.get(i).getEndPositionRow() - 2 >= 0) {
                                if (manager.getPlayerEnumAtPosition(winnableChains.get(i).getEndPositionRow() - 2, winnableChains.get(i).getEndPositionCol() - 2) != null) {
                                    if (manager.getPlayerEnumAtPosition(winnableChains.get(i).getEndPositionRow() - 2, winnableChains.get(i).getEndPositionCol() - 2) == playerEnum) {
                                        //check falling
                                        if (manager.getPlayerEnumAtPosition(winnableChains.get(i).getEndPositionRow(), winnableChains.get(i).getEndPositionCol() - 1) != PlayerEnum.EMPTY) {
                                            return getMoveOfColumn(winnableChains.get(i).getEndPositionCol() - 1);
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }

        //check special moves
        checkSpecialMoves(playerEnum, blockedColumnObjects);

        //BLOCK COLUMNS
        blockColumns(winnableChains, playerEnum);

        //NO WINMOVE FOUND
        return null;
    }

    public Move preventEnemyWin() {
        List<DetectedChain> harmfulRivalChains = new ArrayList<>();

        //get 2 & 3-coin rival chains
        for (int i = 0; i < winSituationDetector.getRivalDetectedChains().size(); i++) {
            if (winSituationDetector.getRivalDetectedChains().get(i).getSize() >= 2) {
                harmfulRivalChains.add(winSituationDetector.getRivalDetectedChains().get(i));
            }
        }

        //BLOCK COLUMNS
        blockColumns(harmfulRivalChains, PlayerEnum.RIVAL);


        //VERTICAL
        for (int i = 0; i < harmfulRivalChains.size(); i++) {
            if (harmfulRivalChains.get(i).getChainType() == ChainType.VERTICAL) {
                //WARNING: 3 coins!
                if (harmfulRivalChains.get(i).getSize() == 3) {
                    if (harmfulRivalChains.get(i).getStartPositionRow() - 1 >= 0) {
                        if (manager.getPlayerEnumAtPosition(harmfulRivalChains.get(i).getStartPositionRow() - 1, harmfulRivalChains.get(i).getStartPositionCol()) == PlayerEnum.EMPTY) {
                            return getMoveOfColumn(harmfulRivalChains.get(i).getStartPositionCol());
                        }
                    }
                }
            }
        }

        //HORIZONTAL 2 coins with empty fields on left & right
        for (int i = 0; i < harmfulRivalChains.size(); i++) {
            if (harmfulRivalChains.get(i).getChainType() == ChainType.HORIZONTAL && harmfulRivalChains.get(i).getSize() == 2) {
                if (harmfulRivalChains.get(i).getStartPositionCol() - 1 >= 0 && harmfulRivalChains.get(i).getEndPositionCol() + 1 < 7) {
                    //check if left and right are empty
                    if (manager.getPlayerEnumAtPosition(harmfulRivalChains.get(i).getStartPositionRow(), harmfulRivalChains.get(i).getStartPositionCol() - 1) != null) {
                        if (manager.getPlayerEnumAtPosition(harmfulRivalChains.get(i).getStartPositionRow(), harmfulRivalChains.get(i).getStartPositionCol() - 1) == PlayerEnum.EMPTY) {
                            if (manager.getPlayerEnumAtPosition(harmfulRivalChains.get(i).getEndPositionRow(), harmfulRivalChains.get(i).getEndPositionCol() + 1) != null) {
                                if (manager.getPlayerEnumAtPosition(harmfulRivalChains.get(i).getEndPositionRow(), harmfulRivalChains.get(i).getEndPositionCol() + 1) == PlayerEnum.EMPTY) {
                                    //check falling
                                    //no falling - row = 6
                                    if (harmfulRivalChains.get(i).getStartPositionRow() == 6) {
                                        Random random = new Random();
                                        boolean left = random.nextBoolean();
                                        if (left) {
                                            return getMoveOfColumn(harmfulRivalChains.get(i).getStartPositionCol() - 1);
                                        } else {
                                            return getMoveOfColumn(harmfulRivalChains.get(i).getEndPositionCol() + 1);
                                        }
                                    }
                                    //left
                                    if (harmfulRivalChains.get(i).getStartPositionCol() - 1 >= 0) {
                                        if (harmfulRivalChains.get(i).getStartPositionRow() + 1 < 7) {
                                            if (manager.getPlayerEnumAtPosition(harmfulRivalChains.get(i).getStartPositionRow() + 1, harmfulRivalChains.get(i).getStartPositionCol() - 1) != PlayerEnum.EMPTY) {
                                                if (manager.getPlayerEnumAtPosition(harmfulRivalChains.get(i).getEndPositionRow() + 1, harmfulRivalChains.get(i).getEndPositionCol() + 1) != PlayerEnum.EMPTY) {
                                                    //if rival doesn't win with next move
                                                        return getMoveOfColumn(harmfulRivalChains.get(i).getStartPositionCol() - 1);
                                                    }
                                                }
                                            }
                                        }
                                    }
                                    //right
                                    if (harmfulRivalChains.get(i).getEndPositionCol() + 1 < 7) {
                                        if (harmfulRivalChains.get(i).getEndPositionRow() + 1 < 7) {
                                            if (manager.getPlayerEnumAtPosition(harmfulRivalChains.get(i).getEndPositionRow() + 1, harmfulRivalChains.get(i).getEndPositionCol() + 1) != PlayerEnum.EMPTY) {
                                                if (manager.getPlayerEnumAtPosition(harmfulRivalChains.get(i).getStartPositionRow() + 1, harmfulRivalChains.get(i).getStartPositionCol() - 1) != PlayerEnum.EMPTY) {
                                                    return getMoveOfColumn(harmfulRivalChains.get(i).getEndPositionCol() + 1);
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }

        //HORIZONTAL prevent completion of 2 coins with empty fields on left & right + 1 rival coin
        for (int i = 0; i < harmfulRivalChains.size(); i++) {
            if (harmfulRivalChains.get(i).getChainType() == ChainType.HORIZONTAL) {
                if (harmfulRivalChains.get(i).getSize() == 2) {
                    //left
                    if (harmfulRivalChains.get(i).getStartPositionCol() - 2 >= 0) {
                        if (manager.getPlayerEnumAtPosition(harmfulRivalChains.get(i).getStartPositionRow(), harmfulRivalChains.get(i).getStartPositionCol() - 2) != null) {
                            if (manager.getPlayerEnumAtPosition(harmfulRivalChains.get(i).getStartPositionRow(), harmfulRivalChains.get(i).getStartPositionCol() - 2) == PlayerEnum.RIVAL) {
                                if (manager.getPlayerEnumAtPosition(harmfulRivalChains.get(i).getStartPositionRow(), harmfulRivalChains.get(i).getStartPositionCol() - 1) == PlayerEnum.EMPTY) {
                                    //check falling
                                    if (harmfulRivalChains.get(i).getStartPositionRow() == 6) {
                                        return getMoveOfColumn(harmfulRivalChains.get(i).getStartPositionCol() - 1);
                                    }
                                    if (harmfulRivalChains.get(i).getStartPositionRow() + 1 < 7 && harmfulRivalChains.get(i).getStartPositionCol() - 1 >= 0) {
                                        if (manager.getPlayerEnumAtPosition(harmfulRivalChains.get(i).getStartPositionRow() + 1, harmfulRivalChains.get(i).getStartPositionCol() - 1) != PlayerEnum.EMPTY) {
                                            return getMoveOfColumn(harmfulRivalChains.get(i).getStartPositionCol() - 1);
                                        }
                                    }
                                }
                            }
                        }

                        //right
                        if (harmfulRivalChains.get(i).getEndPositionCol() + 2 < 7) {
                            if (manager.getPlayerEnumAtPosition(harmfulRivalChains.get(i).getEndPositionRow(), harmfulRivalChains.get(i).getEndPositionCol() + 2) != null) {
                                if (manager.getPlayerEnumAtPosition(harmfulRivalChains.get(i).getEndPositionRow(), harmfulRivalChains.get(i).getEndPositionCol() + 2) == PlayerEnum.RIVAL) {
                                    if (manager.getPlayerEnumAtPosition(harmfulRivalChains.get(i).getEndPositionRow(), harmfulRivalChains.get(i).getEndPositionCol() + 1) == PlayerEnum.EMPTY) {
                                        //check falling
                                        if (harmfulRivalChains.get(i).getEndPositionRow() == 6) {
                                            return getMoveOfColumn(harmfulRivalChains.get(i).getEndPositionCol() + 1);
                                        }
                                        if ((harmfulRivalChains.get(i).getEndPositionRow() + 1 < 7 && harmfulRivalChains.get(i).getEndPositionCol() + 1 < 7)) {
                                            if (manager.getPlayerEnumAtPosition(harmfulRivalChains.get(i).getEndPositionRow() + 1, harmfulRivalChains.get(i).getEndPositionCol() + 1) != PlayerEnum.EMPTY) {
                                                return getMoveOfColumn(harmfulRivalChains.get(i).getEndPositionCol() + 1);
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }

        //DIAGONAL TOP RIGHT - 2 coins with empty fields on left & right
        for (int i = 0; i < harmfulRivalChains.size(); i++) {
            if (harmfulRivalChains.get(i).getChainType() == ChainType.DIAGONAL_TOP_RIGHT && harmfulRivalChains.get(i).getSize() == 2) {
                if (harmfulRivalChains.get(i).getEndPositionCol() + 1 < 7 && harmfulRivalChains.get(i).getEndPositionRow() - 1 >= 0) {
                    if (manager.getPlayerEnumAtPosition(harmfulRivalChains.get(i).getEndPositionRow() - 1, harmfulRivalChains.get(i).getEndPositionCol() + 1) != null) {
                        if (manager.getPlayerEnumAtPosition(harmfulRivalChains.get(i).getEndPositionRow() - 1, harmfulRivalChains.get(i).getEndPositionCol() + 1) == PlayerEnum.EMPTY) {
                            if (harmfulRivalChains.get(i).getEndPositionRow() - 2 >= 0 && harmfulRivalChains.get(i).getEndPositionCol() + 2 < 7) {
                                if (manager.getPlayerEnumAtPosition(harmfulRivalChains.get(i).getEndPositionRow() - 2, harmfulRivalChains.get(i).getEndPositionCol() + 2) != null) {
                                    if (manager.getPlayerEnumAtPosition(harmfulRivalChains.get(i).getEndPositionRow() - 2, harmfulRivalChains.get(i).getEndPositionCol() + 2) == PlayerEnum.RIVAL) {
                                        //check falling
                                        if (manager.getPlayerEnumAtPosition(harmfulRivalChains.get(i).getEndPositionRow(), harmfulRivalChains.get(i).getEndPositionCol() + 1) != null) {
                                            if (manager.getPlayerEnumAtPosition(harmfulRivalChains.get(i).getEndPositionRow(), harmfulRivalChains.get(i).getEndPositionCol() + 1) != PlayerEnum.EMPTY) {
                                                ////System.out.println(manager.getPlayerEnumAtPosition(harmfulRivalChains.get(i).getEndPositionRow(), harmfulRivalChains.get(i).getEndPositionCol() + 1) != PlayerEnum.EMPTY);
                                                return getMoveOfColumn(harmfulRivalChains.get(i).getEndPositionCol() + 1);
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }

        //DIAGONAL TOP LEFT - 2 coins with empty fields on left & right
        for (int i = 0; i < harmfulRivalChains.size(); i++) {
            if (harmfulRivalChains.get(i).getChainType() == ChainType.DIAGONAL_TOP_LEFT && harmfulRivalChains.get(i).getSize() == 2) {
                if (harmfulRivalChains.get(i).getEndPositionCol() - 1 >= 0 && harmfulRivalChains.get(i).getEndPositionRow() - 1 >= 0) {
                    if (manager.getPlayerEnumAtPosition(harmfulRivalChains.get(i).getEndPositionRow() - 1, harmfulRivalChains.get(i).getEndPositionCol() - 1) != null) {
                        if (manager.getPlayerEnumAtPosition(harmfulRivalChains.get(i).getEndPositionRow() - 1, harmfulRivalChains.get(i).getEndPositionCol() - 1) == PlayerEnum.EMPTY) {
                            if (harmfulRivalChains.get(i).getEndPositionRow() - 2 >= 0 && harmfulRivalChains.get(i).getEndPositionCol() - 2 >= 0) {
                                if (manager.getPlayerEnumAtPosition(harmfulRivalChains.get(i).getEndPositionRow() - 2, harmfulRivalChains.get(i).getEndPositionCol() - 2) != null) {
                                    if (manager.getPlayerEnumAtPosition(harmfulRivalChains.get(i).getEndPositionRow() - 2, harmfulRivalChains.get(i).getEndPositionCol() - 2) == PlayerEnum.RIVAL) {
                                        //check falling
                                        if (manager.getPlayerEnumAtPosition(harmfulRivalChains.get(i).getEndPositionRow(), harmfulRivalChains.get(i).getEndPositionCol() - 1) != null) {
                                            if (manager.getPlayerEnumAtPosition(harmfulRivalChains.get(i).getEndPositionRow(), harmfulRivalChains.get(i).getEndPositionCol() - 1) != PlayerEnum.EMPTY) {
                                                return getMoveOfColumn(harmfulRivalChains.get(i).getEndPositionCol() - 1);
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }

        //DIAGONAL BOTTOM RIGHT - 2 coins with empty fields on left & right
        for (int i = 0; i < harmfulRivalChains.size(); i++) {
            if (harmfulRivalChains.get(i).getChainType() == ChainType.DIAGONAL_BOTTOM_RIGHT && harmfulRivalChains.get(i).getSize() == 2) {
                if (harmfulRivalChains.get(i).getEndPositionCol() + 1 < 7 && harmfulRivalChains.get(i).getEndPositionRow() + 1 < 7) {
                    if (manager.getPlayerEnumAtPosition(harmfulRivalChains.get(i).getEndPositionRow() + 1, harmfulRivalChains.get(i).getEndPositionCol() + 1) != null) {
                        if (manager.getPlayerEnumAtPosition(harmfulRivalChains.get(i).getEndPositionRow() + 1, harmfulRivalChains.get(i).getEndPositionCol() + 1) == PlayerEnum.EMPTY) {
                            if (harmfulRivalChains.get(i).getEndPositionRow() + 2 < 7 && harmfulRivalChains.get(i).getEndPositionCol() + 2 < 7) {
                                if (manager.getPlayerEnumAtPosition(harmfulRivalChains.get(i).getEndPositionRow() + 2, harmfulRivalChains.get(i).getEndPositionCol() + 2) != null) {
                                    if (manager.getPlayerEnumAtPosition(harmfulRivalChains.get(i).getEndPositionRow() + 2, harmfulRivalChains.get(i).getEndPositionCol() + 2) == PlayerEnum.RIVAL) {
                                        //check falling
                                        if (manager.getPlayerEnumAtPosition(harmfulRivalChains.get(i).getEndPositionRow() + 2, harmfulRivalChains.get(i).getEndPositionCol() + 1) != PlayerEnum.EMPTY) {
                                            return getMoveOfColumn(harmfulRivalChains.get(i).getEndPositionCol() + 1);
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }

        //DIAGONAL BOTTOM LEFT - 2 coins with empty fields on left & right
        for (int i = 0; i < harmfulRivalChains.size(); i++) {
            if (harmfulRivalChains.get(i).getChainType() == ChainType.DIAGONAL_BOTTOM_LEFT && harmfulRivalChains.get(i).getSize() == 2) {
                if (harmfulRivalChains.get(i).getEndPositionCol() - 1 >= 0 && harmfulRivalChains.get(i).getEndPositionRow() + 1 < 7) {
                    if (manager.getPlayerEnumAtPosition(harmfulRivalChains.get(i).getEndPositionRow() + 1, harmfulRivalChains.get(i).getEndPositionCol() - 1) != null) {
                        if (manager.getPlayerEnumAtPosition(harmfulRivalChains.get(i).getEndPositionRow() + 1, harmfulRivalChains.get(i).getEndPositionCol() - 1) == PlayerEnum.EMPTY) {
                            if (harmfulRivalChains.get(i).getEndPositionRow() + 2 < 7 && harmfulRivalChains.get(i).getEndPositionCol() - 2 >= 0) {
                                if (manager.getPlayerEnumAtPosition(harmfulRivalChains.get(i).getEndPositionRow() + 2, harmfulRivalChains.get(i).getEndPositionCol() - 2) != null) {
                                    if (manager.getPlayerEnumAtPosition(harmfulRivalChains.get(i).getEndPositionRow() + 2, harmfulRivalChains.get(i).getEndPositionCol() - 2) == PlayerEnum.RIVAL) {
                                        //check falling
                                        if (manager.getPlayerEnumAtPosition(harmfulRivalChains.get(i).getEndPositionRow() + 2, harmfulRivalChains.get(i).getEndPositionCol() - 1) != PlayerEnum.EMPTY) {
                                            return getMoveOfColumn(harmfulRivalChains.get(i).getEndPositionCol() - 1);
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }

        //HORIZONTAL: block size: 3
        for (int i = 0; i < harmfulRivalChains.size(); i++) {
            //chainSize: 3
            if (harmfulRivalChains.get(i).getChainType() == ChainType.HORIZONTAL && harmfulRivalChains.get(i).getSize() >= 3) {
                //SET ON LEFT
                if (harmfulRivalChains.get(i).getStartPositionCol() - 1 >= 0) {
                    if (manager.getPlayerEnumAtPosition(harmfulRivalChains.get(i).getStartPositionRow(), harmfulRivalChains.get(i).getStartPositionCol() - 1) != null) {
                        if (manager.getPlayerEnumAtPosition(harmfulRivalChains.get(i).getStartPositionRow(), harmfulRivalChains.get(i).getStartPositionCol() - 1) == PlayerEnum.EMPTY) {
                            //check falling
                            if (harmfulRivalChains.get(i).getEndPositionRow() == 6) {
                                return getMoveOfColumn(harmfulRivalChains.get(i).getStartPositionCol() - 1);
                            } else {
                                if (harmfulRivalChains.get(i).getStartPositionRow() + 1 < 7 && harmfulRivalChains.get(i).getStartPositionCol() - 1 >= 0) {
                                    if (manager.getPlayerEnumAtPosition(harmfulRivalChains.get(i).getStartPositionRow() + 1, harmfulRivalChains.get(i).getStartPositionCol() - 1) != PlayerEnum.EMPTY) {
                                        return getMoveOfColumn(harmfulRivalChains.get(i).getStartPositionCol() - 1);
                                    }
                                }
                            }
                        }
                    }
                }
                //SET ON RIGHT
                if (harmfulRivalChains.get(i).getEndPositionCol() + 1 < 7) {
                    if (manager.getPlayerEnumAtPosition(harmfulRivalChains.get(i).getEndPositionRow(), harmfulRivalChains.get(i).getEndPositionCol() + 1) != null) {
                        if (manager.getPlayerEnumAtPosition(harmfulRivalChains.get(i).getEndPositionRow(), harmfulRivalChains.get(i).getEndPositionCol() + 1) == PlayerEnum.EMPTY) {
                            //check falling
                            if (harmfulRivalChains.get(i).getEndPositionRow() == 6) {
                                return getMoveOfColumn(harmfulRivalChains.get(i).getEndPositionCol() + 1);
                            } else {
                                if (manager.getPlayerEnumAtPosition(harmfulRivalChains.get(i).getEndPositionRow() + 1, harmfulRivalChains.get(i).getEndPositionCol() + 1) != PlayerEnum.EMPTY) {
                                    return getMoveOfColumn(harmfulRivalChains.get(i).getEndPositionCol() + 1);
                                }
                            }
                        }
                    }
                }
            }
        }

        //DIAGONAL_TOP_LEFT - complete 3 coins
        for (int i = 0; i < harmfulRivalChains.size(); i++) {
            if (harmfulRivalChains.get(i).getChainType() == ChainType.DIAGONAL_TOP_LEFT) {
                if (harmfulRivalChains.get(i).getSize() == 3) {
                    if (harmfulRivalChains.get(i).getEndPositionCol() - 1 >= 0 && harmfulRivalChains.get(i).getEndPositionRow() - 1 >= 0) {
                        if (manager.getPlayerEnumAtPosition(harmfulRivalChains.get(i).getEndPositionRow() - 1, harmfulRivalChains.get(i).getEndPositionCol() - 1) != null) {
                            if (manager.getPlayerEnumAtPosition(harmfulRivalChains.get(i).getEndPositionRow() - 1, harmfulRivalChains.get(i).getEndPositionCol() - 1) == PlayerEnum.EMPTY) {
                                //check falling
                                if (manager.getPlayerEnumAtPosition(harmfulRivalChains.get(i).getEndPositionRow(), harmfulRivalChains.get(i).getEndPositionCol() - 1) != PlayerEnum.EMPTY) {
                                    return getMoveOfColumn(harmfulRivalChains.get(i).getEndPositionCol() - 1);
                                }
                            }
                        }
                    }
                }
            }
        }


        //DIAGONAL_TOP_RIGHT - complete 3 coins
        for (int i = 0; i < harmfulRivalChains.size(); i++) {
            if (harmfulRivalChains.get(i).getChainType() == ChainType.DIAGONAL_TOP_RIGHT) {
                if (harmfulRivalChains.get(i).getSize() == 3) {
                    if (harmfulRivalChains.get(i).getEndPositionCol() + 1 < 7 && harmfulRivalChains.get(i).getEndPositionRow() - 1 >= 0) {
                        if (manager.getPlayerEnumAtPosition(harmfulRivalChains.get(i).getEndPositionRow() - 1, harmfulRivalChains.get(i).getEndPositionCol() + 1) != null) {
                            if (manager.getPlayerEnumAtPosition(harmfulRivalChains.get(i).getEndPositionRow() - 1, harmfulRivalChains.get(i).getEndPositionCol() + 1) == PlayerEnum.EMPTY) {
                                //check falling
                                if (manager.getPlayerEnumAtPosition(harmfulRivalChains.get(i).getEndPositionRow(), harmfulRivalChains.get(i).getEndPositionCol() + 1) != PlayerEnum.EMPTY) {
                                    return getMoveOfColumn(harmfulRivalChains.get(i).getEndPositionCol() + 1);
                                }
                            }
                        }
                    }
                }
            }
        }

        //DIAGONAL_BOTTOM_RIGHT - complete 3 coins
        for (int i = 0; i < harmfulRivalChains.size(); i++) {
            if (harmfulRivalChains.get(i).getChainType() == ChainType.DIAGONAL_BOTTOM_RIGHT) {
                if (harmfulRivalChains.get(i).getSize() == 3) {
                    if (harmfulRivalChains.get(i).getEndPositionCol() + 1 < 7 && harmfulRivalChains.get(i).getEndPositionRow() + 1 < 7) {
                        if (manager.getPlayerEnumAtPosition(harmfulRivalChains.get(i).getEndPositionRow() + 1, harmfulRivalChains.get(i).getEndPositionCol() + 1) != null) {
                            if (manager.getPlayerEnumAtPosition(harmfulRivalChains.get(i).getEndPositionRow() + 1, harmfulRivalChains.get(i).getEndPositionCol() + 1) == PlayerEnum.EMPTY) {
                                //check falling
                                if (harmfulRivalChains.get(i).getEndPositionRow() + 1 == 6) {
                                    return getMoveOfColumn(harmfulRivalChains.get(i).getEndPositionCol() + 1);
                                } else {
                                    if (harmfulRivalChains.get(i).getEndPositionRow() + 2 < 7 && harmfulRivalChains.get(i).getEndPositionCol() + 1 < 7) {
                                        if (manager.getPlayerEnumAtPosition(harmfulRivalChains.get(i).getEndPositionRow() + 2, harmfulRivalChains.get(i).getEndPositionCol() + 1) != PlayerEnum.EMPTY) {
                                            return getMoveOfColumn(harmfulRivalChains.get(i).getEndPositionCol() + 1);
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }

        //DIAGONAL_BOTTOM_LEFT - complete 3 coins
        for (int i = 0; i < harmfulRivalChains.size(); i++) {
            if (harmfulRivalChains.get(i).getChainType() == ChainType.DIAGONAL_BOTTOM_LEFT) {
                if (harmfulRivalChains.get(i).getSize() == 3) {
                    if (harmfulRivalChains.get(i).getEndPositionCol() - 1 >= 0 && harmfulRivalChains.get(i).getEndPositionRow() + 1 < 7) {
                        if (manager.getPlayerEnumAtPosition(harmfulRivalChains.get(i).getEndPositionRow() + 1, harmfulRivalChains.get(i).getEndPositionCol() - 1) != null) {
                            if (manager.getPlayerEnumAtPosition(harmfulRivalChains.get(i).getEndPositionRow() + 1, harmfulRivalChains.get(i).getEndPositionCol() - 1) == PlayerEnum.EMPTY) {
                                //check falling
                                if (harmfulRivalChains.get(i).getEndPositionRow() + 1 == 6) {
                                    return getMoveOfColumn(harmfulRivalChains.get(i).getEndPositionCol() - 1);
                                } else {
                                    if (manager.getPlayerEnumAtPosition(harmfulRivalChains.get(i).getEndPositionRow() + 2, harmfulRivalChains.get(i).getEndPositionCol() - 1) != PlayerEnum.EMPTY) {
                                        return getMoveOfColumn(harmfulRivalChains.get(i).getEndPositionCol() - 1);
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }

        //check special moves
        if (checkSpecialMoves(PlayerEnum.RIVAL, blockedColumnObjects) != null) {
            return checkSpecialMoves(PlayerEnum.RIVAL, blockedColumnObjects);
        }

        //no prevention needed
        return null;
    }


    public void blockColumns(List<DetectedChain> foundChains, PlayerEnum playerEnum) {
        //BLOCK COLUMNS
        for (int i = 0; i < foundChains.size(); i++) {
            //3 COINS
            if (foundChains.get(i).getSize() == 3) {
                //HORIZONTAL LEFT
                if (foundChains.get(i).getEndPositionCol() + 1 < 7 && foundChains.get(i).getStartPositionCol() - 1 >= 0) {
                    if (foundChains.get(i).getChainType() == ChainType.HORIZONTAL) {
                        if (manager.getPlayerEnumAtPosition(foundChains.get(i).getStartPositionRow(), foundChains.get(i).getStartPositionCol() - 1) == PlayerEnum.EMPTY) {
                            if (foundChains.get(i).getStartPositionRow() - 1 >= 0) {
                                if (manager.getPlayerEnumAtPosition(foundChains.get(i).getStartPositionRow() - 1, foundChains.get(i).getStartPositionCol() - 1) == PlayerEnum.EMPTY) {
                                    BlockedColumnObject blockedColumnObject = new BlockedColumnObject(foundChains.get(i).getStartPositionCol() - 1, playerEnum);
                                    blockedColumnObjects.add(blockedColumnObject);
                                }
                            }
                        }
                    }
                }
                //HORIZONTAL RIGHT
                if (foundChains.get(i).getEndPositionCol() + 1 < 7 && foundChains.get(i).getStartPositionCol() - 1 >= 0) {
                    if (foundChains.get(i).getChainType() == ChainType.HORIZONTAL) {
                        if (manager.getPlayerEnumAtPosition(foundChains.get(i).getEndPositionRow(), foundChains.get(i).getEndPositionCol() + 1) == PlayerEnum.EMPTY) {
                            if (foundChains.get(i).getEndPositionRow() + 1 < 7) {
                                if (manager.getPlayerEnumAtPosition(foundChains.get(i).getEndPositionRow() + 1, foundChains.get(i).getEndPositionCol() + 1) == PlayerEnum.EMPTY) {
                                    BlockedColumnObject blockedColumnObject = new BlockedColumnObject(foundChains.get(i).getEndPositionCol() + 1, playerEnum);
                                    blockedColumnObjects.add(blockedColumnObject);
                                }
                            }
                        }
                    }
                }

                //DIAGONAL TOP RIGHT
                if (foundChains.get(i).getEndPositionCol() + 1 < 7 && foundChains.get(i).getEndPositionRow() - 1 >= 0) {
                    if (foundChains.get(i).getChainType() == ChainType.DIAGONAL_TOP_RIGHT) {
                        if (manager.getPlayerEnumAtPosition(foundChains.get(i).getEndPositionRow() - 1, foundChains.get(i).getEndPositionCol() + 1) == PlayerEnum.EMPTY) {
                            if (manager.getPlayerEnumAtPosition(foundChains.get(i).getEndPositionRow(), foundChains.get(i).getEndPositionCol() + 1) == PlayerEnum.EMPTY) {
                                if (foundChains.get(i).getEndPositionCol() + 1 >= 0 && foundChains.get(i).getEndPositionRow() + 1 >= 0) {
                                    if (manager.getPlayerEnumAtPosition(foundChains.get(i).getEndPositionRow() + 1, foundChains.get(i).getEndPositionCol() + 1) != PlayerEnum.EMPTY) {
                                        BlockedColumnObject blockedColumnObject = new BlockedColumnObject(foundChains.get(i).getEndPositionCol() + 1, playerEnum);
                                        blockedColumnObjects.add(blockedColumnObject);
                                    }
                                }
                            }
                        }
                    }
                }

                //DIAGONAL TOP LEFT
                if (foundChains.get(i).getEndPositionCol() - 1 >= 0 && foundChains.get(i).getEndPositionRow() - 1 >= 0) {
                    if (foundChains.get(i).getChainType() == ChainType.DIAGONAL_TOP_LEFT) {
                        if (manager.getPlayerEnumAtPosition(foundChains.get(i).getEndPositionRow() - 1, foundChains.get(i).getEndPositionCol() - 1) == PlayerEnum.EMPTY) {
                            if (manager.getPlayerEnumAtPosition(foundChains.get(i).getEndPositionRow(), foundChains.get(i).getEndPositionCol() - 1) == PlayerEnum.EMPTY) {
                                if (foundChains.get(i).getEndPositionCol() - 1 >= 0 && foundChains.get(i).getEndPositionRow() + 1 < 7) {
                                    if (manager.getPlayerEnumAtPosition(foundChains.get(i).getEndPositionRow() + 1, foundChains.get(i).getEndPositionCol() - 1) != PlayerEnum.EMPTY) {
                                        BlockedColumnObject blockedColumnObject = new BlockedColumnObject(foundChains.get(i).getEndPositionCol() - 1, playerEnum);
                                        blockedColumnObjects.add(blockedColumnObject);
                                    }
                                }
                            }
                        }
                    }
                }

                //DIAGONAL BOTTOM RIGHT
                if (foundChains.get(i).getEndPositionCol() + 1 < 7 && foundChains.get(i).getEndPositionRow() + 1 < 7) {
                    if (foundChains.get(i).getChainType() == ChainType.DIAGONAL_BOTTOM_RIGHT) {
                        if (manager.getPlayerEnumAtPosition(foundChains.get(i).getEndPositionRow() + 1, foundChains.get(i).getEndPositionCol() + 1) == PlayerEnum.EMPTY) {
                            if (manager.getPlayerEnumAtPosition(foundChains.get(i).getEndPositionRow(), foundChains.get(i).getEndPositionCol() + 1) == PlayerEnum.EMPTY) {
                                if (foundChains.get(i).getEndPositionCol() + 1 >= 0 && foundChains.get(i).getEndPositionRow() + 1 >= 0) {
                                    if (manager.getPlayerEnumAtPosition(foundChains.get(i).getEndPositionRow() + 1, foundChains.get(i).getEndPositionCol() + 1) != PlayerEnum.EMPTY) {
                                        BlockedColumnObject blockedColumnObject = new BlockedColumnObject(foundChains.get(i).getEndPositionCol() + 1, playerEnum);
                                        blockedColumnObjects.add(blockedColumnObject);
                                    }
                                }
                            }
                        }
                    }
                }

                //DIAGONAL BOTTOM LEFT
                if (foundChains.get(i).getEndPositionCol() - 1 >= 0 && foundChains.get(i).getEndPositionRow() + 1 < 7) {
                    if (foundChains.get(i).getChainType() == ChainType.DIAGONAL_BOTTOM_LEFT) {
                        if (manager.getPlayerEnumAtPosition(foundChains.get(i).getEndPositionRow() + 1, foundChains.get(i).getEndPositionCol() - 1) == PlayerEnum.EMPTY) {
                            if (manager.getPlayerEnumAtPosition(foundChains.get(i).getEndPositionRow(), foundChains.get(i).getEndPositionCol() - 1) == PlayerEnum.EMPTY) {
                                if (foundChains.get(i).getEndPositionCol() - 1 >= 0 && foundChains.get(i).getEndPositionRow() + 1 >= 0) {
                                    if (manager.getPlayerEnumAtPosition(foundChains.get(i).getEndPositionRow() + 1, foundChains.get(i).getEndPositionCol() - 1) != PlayerEnum.EMPTY) {
                                        BlockedColumnObject blockedColumnObject = new BlockedColumnObject(foundChains.get(i).getEndPositionCol() - 1, playerEnum);
                                        blockedColumnObjects.add(blockedColumnObject);
                                    }
                                }
                            }
                        }
                    }
                }
            }

            //2 COINS HORIZONTAL
            if (foundChains.get(i).getChainType() == ChainType.HORIZONTAL) {
                if (foundChains.get(i).getSize() == 2) {
                    //left
                    if (foundChains.get(i).getStartPositionCol() - 2 >= 0) {
                        if (manager.getPlayerEnumAtPosition(foundChains.get(i).getStartPositionRow(), foundChains.get(i).getStartPositionCol() - 2) != null) {
                            if (manager.getPlayerEnumAtPosition(foundChains.get(i).getStartPositionRow(), foundChains.get(i).getStartPositionCol() - 2) == playerEnum) {
                                if (manager.getPlayerEnumAtPosition(foundChains.get(i).getStartPositionRow(), foundChains.get(i).getStartPositionCol() - 1) == PlayerEnum.EMPTY) {
                                    //check falling
                                    if (foundChains.get(i).getStartPositionRow() + 1 < 7) {
                                        if (manager.getPlayerEnumAtPosition(foundChains.get(i).getStartPositionRow() + 1, foundChains.get(i).getStartPositionCol() - 1) != PlayerEnum.EMPTY) {
                                            BlockedColumnObject blockedColumnObject = new BlockedColumnObject(foundChains.get(i).getStartPositionCol() - 1, playerEnum);
                                            blockedColumnObjects.add(blockedColumnObject);
                                        }
                                    }
                                }
                            }
                        }
                    }

                    //right
                    if (foundChains.get(i).getEndPositionCol() + 2 < 7) {
                        if (manager.getPlayerEnumAtPosition(foundChains.get(i).getEndPositionRow(), foundChains.get(i).getEndPositionCol() + 2) != null) {
                            if (manager.getPlayerEnumAtPosition(foundChains.get(i).getEndPositionRow(), foundChains.get(i).getEndPositionCol() + 2) == playerEnum) {
                                if (manager.getPlayerEnumAtPosition(foundChains.get(i).getEndPositionRow(), foundChains.get(i).getEndPositionCol() + 1) == PlayerEnum.EMPTY) {
                                    //check falling
                                    if (foundChains.get(i).getEndPositionRow() + 1 < 7) {
                                        if (manager.getPlayerEnumAtPosition(foundChains.get(i).getEndPositionRow() + 1, foundChains.get(i).getEndPositionCol() + 1) != PlayerEnum.EMPTY) {
                                            BlockedColumnObject blockedColumnObject = new BlockedColumnObject(foundChains.get(i).getEndPositionCol() + 1, playerEnum);
                                            blockedColumnObjects.add(blockedColumnObject);
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }

            //2 COINS DIAGONAL
            if (foundChains.get(i).getSize() == 2) {
                //DIAGONAL TOP RIGHT
                if (foundChains.get(i).getChainType() == ChainType.DIAGONAL_TOP_RIGHT) {
                    if (foundChains.get(i).getEndPositionRow() - 2 >= 0 && foundChains.get(i).getEndPositionCol() + 2 < 7) {
                        if (manager.getPlayerEnumAtPosition(foundChains.get(i).getEndPositionRow() - 2, foundChains.get(i).getEndPositionCol() + 2) == playerEnum) {
                            //check falling
                            if (manager.getPlayerEnumAtPosition(foundChains.get(i).getEndPositionRow(), foundChains.get(i).getEndPositionCol() + 1) == PlayerEnum.EMPTY) {
                                BlockedColumnObject blockedColumnObject = new BlockedColumnObject(foundChains.get(i).getEndPositionCol() + 1, playerEnum);
                                blockedColumnObjects.add(blockedColumnObject);
                            }
                        }
                    }
                }

                //DIAGONAL TOP LEFT
                if (foundChains.get(i).getChainType() == ChainType.DIAGONAL_TOP_LEFT) {
                    if (foundChains.get(i).getEndPositionRow() - 2 >= 0 && foundChains.get(i).getEndPositionCol() - 2 >= 0) {
                        if (manager.getPlayerEnumAtPosition(foundChains.get(i).getEndPositionRow() - 2, foundChains.get(i).getEndPositionCol() - 2) == playerEnum) {
                            if (manager.getPlayerEnumAtPosition(foundChains.get(i).getEndPositionRow() - 1, foundChains.get(i).getEndPositionCol() - 1) == PlayerEnum.EMPTY) {
                                //check falling
                                if (manager.getPlayerEnumAtPosition(foundChains.get(i).getEndPositionRow(), foundChains.get(i).getEndPositionCol() - 1) == PlayerEnum.EMPTY) {
                                    BlockedColumnObject blockedColumnObject = new BlockedColumnObject(foundChains.get(i).getEndPositionCol() - 1, playerEnum);
                                    blockedColumnObjects.add(blockedColumnObject);
                                }
                            }
                        }
                    }
                }

                //DIAGONAL BOTTOM RIGHT
                if (foundChains.get(i).getChainType() == ChainType.DIAGONAL_BOTTOM_RIGHT) {
                    if (foundChains.get(i).getEndPositionRow() + 2 < 7 && foundChains.get(i).getEndPositionCol() + 2 < 7) {
                        if (manager.getPlayerEnumAtPosition(foundChains.get(i).getEndPositionRow() + 2, foundChains.get(i).getEndPositionCol() + 2) == playerEnum) {
                            if (manager.getPlayerEnumAtPosition(foundChains.get(i).getEndPositionRow() + 1, foundChains.get(i).getEndPositionCol() + 1) == PlayerEnum.EMPTY) {
                                //check falling
                                if (manager.getPlayerEnumAtPosition(foundChains.get(i).getEndPositionRow() + 2, foundChains.get(i).getEndPositionCol() + 1) == PlayerEnum.EMPTY) {
                                    BlockedColumnObject blockedColumnObject = new BlockedColumnObject(foundChains.get(i).getEndPositionCol() + 1, playerEnum);
                                    blockedColumnObjects.add(blockedColumnObject);
                                }
                            }
                        }
                    }
                }

                //DIAGONAL BOTTOM LEFT
                if (foundChains.get(i).getChainType() == ChainType.DIAGONAL_BOTTOM_LEFT) {
                    if (foundChains.get(i).getEndPositionRow() + 2 < 7 && foundChains.get(i).getEndPositionCol() - 2 >= 0) {
                        if (manager.getPlayerEnumAtPosition(foundChains.get(i).getEndPositionRow() + 2, foundChains.get(i).getEndPositionCol() - 2) == playerEnum) {
                            if (manager.getPlayerEnumAtPosition(foundChains.get(i).getEndPositionRow() + 1, foundChains.get(i).getEndPositionCol() - 1) == PlayerEnum.EMPTY) {
                                //check falling
                                if (manager.getPlayerEnumAtPosition(foundChains.get(i).getEndPositionRow() + 2, foundChains.get(i).getEndPositionCol() - 1) == PlayerEnum.EMPTY) {
                                    BlockedColumnObject blockedColumnObject = new BlockedColumnObject(foundChains.get(i).getEndPositionCol() - 1, playerEnum);
                                    blockedColumnObjects.add(blockedColumnObject);
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    //specialWinMoves
    public Move checkSpecialMoves(PlayerEnum playerEnum, List<BlockedColumnObject> blockedColumnObjects){
        for (int i = 6; i >= 0; i--) {
            for (int j = 0; j < 7; j++) {
                if (manager.getPlayerEnumAtPosition(i, j) == playerEnum) {
                    if (j + 1 < 7) {
                        if (manager.getPlayerEnumAtPosition(i, j + 1) == PlayerEnum.EMPTY) {
                            if (j + 2 < 7) {
                                if (manager.getPlayerEnumAtPosition(i, j + 2) == playerEnum) {
                                    if (j + 3 < 7) {
                                        if (manager.getPlayerEnumAtPosition(i, j + 3) == PlayerEnum.EMPTY) {
                                            if (j - 1 >= 0) {
                                                if (manager.getPlayerEnumAtPosition(i, j - 1) == PlayerEnum.EMPTY) {
                                                    //check blocked
                                                    boolean found = false;
                                                    for(int k = 0; k < blockedColumnObjects.size(); k++){
                                                        if(blockedColumnObjects.get(k).getColumn()==(j + 1)){
                                                            found = true;
                                                        }
                                                    }
                                                    if(found == false){
                                                        return getMoveOfColumn(j + 1);
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        return null;
    }

    public Move notBlockedOwnChainImproveMove(List<Integer> notBlockedMoves) {
        Random random = new Random();
        if (notBlockedMoves.size() != 0) {
            //improve own coins
            int trys = 0;
            while (trys < 50) {
                int randomColumn = notBlockedMoves.get(random.nextInt(notBlockedMoves.size()));
                for (int i = 6; i >= 0; i--) {
                    if (manager.getPlayerEnumAtPosition(i, randomColumn) == PlayerEnum.OWN) {
                        boolean leftOrRight = random.nextBoolean();
                        //left
                        if (leftOrRight == true) {
                            if (randomColumn - 1 >= 0) {
                                if (manager.getPlayerEnumAtPosition(i, randomColumn - 1) == PlayerEnum.EMPTY) {
                                    return getMoveOfColumn(randomColumn - 1);
                                }
                            }
                        } else {
                            if (randomColumn + 1 < 7) {
                                if (manager.getPlayerEnumAtPosition(i, randomColumn + 1) == PlayerEnum.EMPTY) {
                                    return getMoveOfColumn(randomColumn + 1);
                                }
                            }
                        }
                    }
                }
                trys++;
            }
        }
        return getMoveOfColumn(random.nextInt(notBlockedMoves.size()));
    }

    public Move getNotBlockedpreventRivalChainMove(List<Integer> notBlockedMoves) {
        //HORIZONTAL
        for (int i = 0; i < winSituationDetector.getRivalDetectedChains().size(); i++) {
            if (winSituationDetector.getRivalDetectedChains().get(i).getSize() == 2 &&
                    winSituationDetector.getRivalDetectedChains().get(i).getChainType() == ChainType.HORIZONTAL) {
                if (winSituationDetector.getRivalDetectedChains().get(i).getStartPositionCol() - 1 >= 0 &&
                        winSituationDetector.getRivalDetectedChains().get(i).getEndPositionCol() + 1 < 7) {
                    if (manager.getPlayerEnumAtPosition(winSituationDetector.getRivalDetectedChains().get(i).getStartPositionRow(),
                            winSituationDetector.getRivalDetectedChains().get(i).getStartPositionCol() - 1) == PlayerEnum.EMPTY &&
                            manager.getPlayerEnumAtPosition(winSituationDetector.getRivalDetectedChains().get(i).getEndPositionRow(),
                                    winSituationDetector.getRivalDetectedChains().get(i).getEndPositionCol() + 1) == PlayerEnum.EMPTY) {
                        Random random = new Random();
                        boolean isLeft = random.nextBoolean();
                        //LEFT
                        if (isLeft) {
                            if (winSituationDetector.getRivalDetectedChains().get(i).getStartPositionCol() - 1 >= 0) {
                                if (manager.getPlayerEnumAtPosition(winSituationDetector.getRivalDetectedChains().get(i).getStartPositionRow(),
                                        winSituationDetector.getRivalDetectedChains().get(i).getStartPositionCol() - 1) == PlayerEnum.EMPTY) {
                                    //check if blocked
                                    boolean blocked = true;
                                    for (int j = 0; j < notBlockedMoves.size(); j++) {
                                        if (notBlockedMoves.get(j) == winSituationDetector.getRivalDetectedChains().get(i).getStartPositionCol() - 1) {
                                            blocked = false;
                                        }
                                    }
                                    if (!blocked) {
                                        //check falling
                                        if (winSituationDetector.getRivalDetectedChains().get(i).getStartPositionRow() == 6) {
                                            return getMoveOfColumn(winSituationDetector.getRivalDetectedChains().get(i).getStartPositionCol() - 1);
                                        }
                                        if (winSituationDetector.getRivalDetectedChains().get(i).getStartPositionRow() + 1 < 7 &&
                                                winSituationDetector.getRivalDetectedChains().get(i).getStartPositionCol() - 1 < 7) {
                                            if (manager.getPlayerEnumAtPosition(winSituationDetector.getRivalDetectedChains().get(i).getStartPositionRow() + 1,
                                                    winSituationDetector.getRivalDetectedChains().get(i).getStartPositionCol() - 1) != PlayerEnum.EMPTY) {
                                                return getMoveOfColumn(winSituationDetector.getRivalDetectedChains().get(i).getStartPositionCol() - 1);
                                            }
                                        }
                                    }
                                }
                            }
                        } else
                        //RIGHT
                        {
                            if (winSituationDetector.getRivalDetectedChains().get(i).getEndPositionCol() + 1 < 7) {
                                if (manager.getPlayerEnumAtPosition(winSituationDetector.getRivalDetectedChains().get(i).getEndPositionRow(),
                                        winSituationDetector.getRivalDetectedChains().get(i).getEndPositionCol() + 1) == PlayerEnum.EMPTY) {
                                    //check if blocked
                                    boolean blocked = true;
                                    for (int j = 0; j < notBlockedMoves.size(); j++) {
                                        if (notBlockedMoves.get(j) == winSituationDetector.getRivalDetectedChains().get(i).getEndPositionCol() + 1) {
                                            blocked = false;
                                        }
                                    }
                                    if (!blocked) {
                                        //check falling
                                        if (winSituationDetector.getRivalDetectedChains().get(i).getEndPositionRow() == 6) {
                                            return getMoveOfColumn(winSituationDetector.getRivalDetectedChains().get(i).getEndPositionCol() + 1);
                                        }
                                        if (winSituationDetector.getRivalDetectedChains().get(i).getEndPositionRow() + 1 < 7 &&
                                                winSituationDetector.getRivalDetectedChains().get(i).getEndPositionCol() + 1 < 7) {
                                            if (manager.getPlayerEnumAtPosition(winSituationDetector.getRivalDetectedChains().get(i).getEndPositionRow() + 1,
                                                    winSituationDetector.getRivalDetectedChains().get(i).getEndPositionCol() + 1) != PlayerEnum.EMPTY) {
                                                return getMoveOfColumn(winSituationDetector.getRivalDetectedChains().get(i).getEndPositionCol() + 1);
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    } else {
                        //LEFT
                        if (winSituationDetector.getRivalDetectedChains().get(i).getStartPositionCol() - 1 >= 0) {
                            if (manager.getPlayerEnumAtPosition(winSituationDetector.getRivalDetectedChains().get(i).getStartPositionRow(),
                                    winSituationDetector.getRivalDetectedChains().get(i).getStartPositionCol() - 1) == PlayerEnum.EMPTY) {
                                //check if blocked
                                boolean blocked = true;
                                for (int j = 0; j < notBlockedMoves.size(); j++) {
                                    if (notBlockedMoves.get(j) == winSituationDetector.getRivalDetectedChains().get(i).getStartPositionCol() - 1) {
                                        blocked = false;
                                    }
                                }
                                if (!blocked) {
                                    //check falling
                                    if (winSituationDetector.getRivalDetectedChains().get(i).getStartPositionRow() == 6) {
                                        return getMoveOfColumn(winSituationDetector.getRivalDetectedChains().get(i).getStartPositionCol() - 1);
                                    }
                                    if (winSituationDetector.getRivalDetectedChains().get(i).getStartPositionRow() + 1 < 7 &&
                                            winSituationDetector.getRivalDetectedChains().get(i).getStartPositionCol() - 1 < 7) {
                                        if (manager.getPlayerEnumAtPosition(winSituationDetector.getRivalDetectedChains().get(i).getStartPositionRow() + 1,
                                                winSituationDetector.getRivalDetectedChains().get(i).getStartPositionCol() - 1) != PlayerEnum.EMPTY) {
                                            return getMoveOfColumn(winSituationDetector.getRivalDetectedChains().get(i).getStartPositionCol() - 1);
                                        }
                                    }
                                }
                            }
                        }
                        //RIGHT
                        {
                            if (winSituationDetector.getRivalDetectedChains().get(i).getEndPositionCol() + 1 < 7) {
                                if (manager.getPlayerEnumAtPosition(winSituationDetector.getRivalDetectedChains().get(i).getEndPositionRow(),
                                        winSituationDetector.getRivalDetectedChains().get(i).getEndPositionCol() + 1) == PlayerEnum.EMPTY) {
                                    //check if blocked
                                    boolean blocked = true;
                                    for (int j = 0; j < notBlockedMoves.size(); j++) {
                                        if (notBlockedMoves.get(j) == winSituationDetector.getRivalDetectedChains().get(i).getEndPositionCol() + 1) {
                                            blocked = false;
                                        }
                                    }
                                    if (!blocked) {
                                        //check falling
                                        if (winSituationDetector.getRivalDetectedChains().get(i).getEndPositionRow() == 6) {
                                            return getMoveOfColumn(winSituationDetector.getRivalDetectedChains().get(i).getEndPositionCol() + 1);
                                        }
                                        if (winSituationDetector.getRivalDetectedChains().get(i).getEndPositionRow() + 1 < 7 &&
                                                winSituationDetector.getRivalDetectedChains().get(i).getEndPositionCol() + 1 < 7) {
                                            if (manager.getPlayerEnumAtPosition(winSituationDetector.getRivalDetectedChains().get(i).getEndPositionRow() + 1,
                                                    winSituationDetector.getRivalDetectedChains().get(i).getEndPositionCol() + 1) != PlayerEnum.EMPTY) {
                                                return getMoveOfColumn(winSituationDetector.getRivalDetectedChains().get(i).getEndPositionCol() + 1);
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }

        //VERTICAL
        for (int i = 0; i < winSituationDetector.getRivalDetectedChains().size(); i++) {
            if (winSituationDetector.getRivalDetectedChains().get(i).getSize() == 2 &&
                    winSituationDetector.getRivalDetectedChains().get(i).getChainType() == ChainType.VERTICAL) {
                if (winSituationDetector.getRivalDetectedChains().get(i).getStartPositionRow() - 1 >= 0 &&
                        winSituationDetector.getRivalDetectedChains().get(i).getEndPositionCol() - 1 >= 0) {
                    if (manager.getPlayerEnumAtPosition(winSituationDetector.getRivalDetectedChains().get(i).getStartPositionRow() - 1,
                            winSituationDetector.getRivalDetectedChains().get(i).getEndPositionCol()) == PlayerEnum.EMPTY) {
                        if (winSituationDetector.getRivalDetectedChains().get(i).getStartPositionRow() - 2 >= 0) {
                            //check if blocked
                            for (int j = 0; j < notBlockedMoves.size(); j++) {
                                if (notBlockedMoves.get(j) == winSituationDetector.getRivalDetectedChains().get(i).getEndPositionCol()) {
                                    return getMoveOfColumn(winSituationDetector.getRivalDetectedChains().get(i).getEndPositionCol());
                                }
                            }
                        }
                    }
                }
            }
        }

        //DIAGONAL TOP RIGHT
        for (int i = 0; i < winSituationDetector.getRivalDetectedChains().size(); i++) {
            if (winSituationDetector.getRivalDetectedChains().get(i).getSize() == 2 &&
                    winSituationDetector.getRivalDetectedChains().get(i).getChainType() == ChainType.DIAGONAL_TOP_RIGHT) {
                if (winSituationDetector.getRivalDetectedChains().get(i).getEndPositionCol() + 1 < 7 &&
                        winSituationDetector.getRivalDetectedChains().get(i).getEndPositionRow() - 1 >= 0) {
                    if (manager.getPlayerEnumAtPosition(winSituationDetector.getRivalDetectedChains().get(i).getEndPositionRow() - 1,
                            winSituationDetector.getRivalDetectedChains().get(i).getEndPositionCol() + 1) == PlayerEnum.EMPTY) {
                        //check falling
                        if (manager.getPlayerEnumAtPosition(winSituationDetector.getRivalDetectedChains().get(i).getEndPositionRow(),
                                winSituationDetector.getRivalDetectedChains().get(i).getEndPositionCol() + 1) != PlayerEnum.EMPTY) {
                            //check if blocked
                            for (int j = 0; j < notBlockedMoves.size(); j++) {
                                if (notBlockedMoves.get(j) == winSituationDetector.getRivalDetectedChains().get(i).getEndPositionCol() + 1) {
                                    return getMoveOfColumn(winSituationDetector.getRivalDetectedChains().get(i).getEndPositionCol() + 1);
                                }
                            }
                        }
                    }
                }
            }
        }

        //DIAGONAL TOP LEFT
        for (int i = 0; i < winSituationDetector.getRivalDetectedChains().size(); i++) {
            if (winSituationDetector.getRivalDetectedChains().get(i).getSize() == 2 &&
                    winSituationDetector.getRivalDetectedChains().get(i).getChainType() == ChainType.DIAGONAL_TOP_LEFT) {
                if (winSituationDetector.getRivalDetectedChains().get(i).getEndPositionCol() - 1 >= 0 &&
                        winSituationDetector.getRivalDetectedChains().get(i).getEndPositionRow() - 1 >= 0) {
                    if (manager.getPlayerEnumAtPosition(winSituationDetector.getRivalDetectedChains().get(i).getEndPositionRow() - 1,
                            winSituationDetector.getRivalDetectedChains().get(i).getEndPositionCol() - 1) == PlayerEnum.EMPTY) {
                        //check falling
                        if (manager.getPlayerEnumAtPosition(winSituationDetector.getRivalDetectedChains().get(i).getEndPositionRow(),
                                winSituationDetector.getRivalDetectedChains().get(i).getEndPositionCol() - 1) != PlayerEnum.EMPTY) {
                            //check if blocked
                            for (int j = 0; j < notBlockedMoves.size(); j++) {
                                if (notBlockedMoves.get(j) == winSituationDetector.getRivalDetectedChains().get(i).getEndPositionCol() - 1) {
                                    return getMoveOfColumn(winSituationDetector.getRivalDetectedChains().get(i).getEndPositionCol() - 1);
                                }
                            }
                        }
                    }
                }
            }
        }

        //DIAGONAL BOTTOM RIGHT
        for (int i = 0; i < winSituationDetector.getRivalDetectedChains().size(); i++) {
            if (winSituationDetector.getRivalDetectedChains().get(i).getSize() == 2 &&
                    winSituationDetector.getRivalDetectedChains().get(i).getChainType() == ChainType.DIAGONAL_TOP_RIGHT) {
                if (winSituationDetector.getRivalDetectedChains().get(i).getEndPositionCol() + 1 < 7 &&
                        winSituationDetector.getRivalDetectedChains().get(i).getEndPositionRow() + 1 < 7) {
                    if (manager.getPlayerEnumAtPosition(winSituationDetector.getRivalDetectedChains().get(i).getEndPositionRow() + 1,
                            winSituationDetector.getRivalDetectedChains().get(i).getEndPositionCol() + 1) == PlayerEnum.EMPTY) {
                        //check falling
                        if (winSituationDetector.getRivalDetectedChains().get(i).getEndPositionRow() + 2 < 7) {
                            if (manager.getPlayerEnumAtPosition(winSituationDetector.getRivalDetectedChains().get(i).getEndPositionRow() + 2,
                                    winSituationDetector.getRivalDetectedChains().get(i).getEndPositionCol() + 1) != PlayerEnum.EMPTY) {
                                //check if blocked
                                for (int j = 0; j < notBlockedMoves.size(); j++) {
                                    if (notBlockedMoves.get(j) == winSituationDetector.getRivalDetectedChains().get(i).getEndPositionCol() + 1) {
                                        return getMoveOfColumn(winSituationDetector.getRivalDetectedChains().get(i).getEndPositionCol() + 1);
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }

        //DIAGONAL BOTTOM LEFT
        for (int i = 0; i < winSituationDetector.getRivalDetectedChains().size(); i++) {
            if (winSituationDetector.getRivalDetectedChains().get(i).getSize() == 2 &&
                    winSituationDetector.getRivalDetectedChains().get(i).getChainType() == ChainType.DIAGONAL_TOP_RIGHT) {
                if (winSituationDetector.getRivalDetectedChains().get(i).getEndPositionCol() - 1 >= 0 &&
                        winSituationDetector.getRivalDetectedChains().get(i).getEndPositionRow() + 1 < 7) {
                    if (manager.getPlayerEnumAtPosition(winSituationDetector.getRivalDetectedChains().get(i).getEndPositionRow() + 1,
                            winSituationDetector.getRivalDetectedChains().get(i).getEndPositionCol() - 1) == PlayerEnum.EMPTY) {
                        //check falling
                        if (winSituationDetector.getRivalDetectedChains().get(i).getEndPositionRow() + 2 < 7) {
                            if (manager.getPlayerEnumAtPosition(winSituationDetector.getRivalDetectedChains().get(i).getEndPositionRow() + 2,
                                    winSituationDetector.getRivalDetectedChains().get(i).getEndPositionCol() - 1) != PlayerEnum.EMPTY) {
                                //check if blocked
                                for (int j = 0; j < notBlockedMoves.size(); j++) {
                                    if (notBlockedMoves.get(j) == winSituationDetector.getRivalDetectedChains().get(i).getEndPositionCol() - 1) {
                                        return getMoveOfColumn(winSituationDetector.getRivalDetectedChains().get(i).getEndPositionCol() - 1);
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        return null;
    }

    public Move getNotBlockedRandomMove(List<Integer> notBlockedMoves) {
        Random random = new Random();

        //place in middle if possible - else random
        for (int i = 0; i < manager.getRemainingColumns().size(); i++) {
            if (manager.getRemainingColumns().get(i) == 3) {
                return getMoveOfColumn(manager.getRemainingColumns().get(i));
            }
        }

        List<Integer> ownBlockedColumns = new ArrayList<>();
        for (int i = 0; i < blockedColumnObjects.size(); i++) {
            if (blockedColumnObjects.get(i).getPlayerEnum() == PlayerEnum.OWN) {
                boolean existsInArray = false;
                for (int j = 0; j < ownBlockedColumns.size(); j++) {
                    if (blockedColumnObjects.get(i).getColumn() == ownBlockedColumns.get(j)) {
                        existsInArray = true;
                    }
                }
                if (existsInArray == false) {
                    ownBlockedColumns.add(blockedColumnObjects.get(i).getColumn());
                }
            }
        }

        if (notBlockedMoves.size() != 0) {
            return getMoveOfColumn(notBlockedMoves.get(notBlockedMoves.size() / 2));
        } else if (ownBlockedColumns.size() != 0) {
            if (getMoveOfColumn(ownBlockedColumns.get(random.nextInt(ownBlockedColumns.size()))) != null) {
                return getMoveOfColumn(ownBlockedColumns.get(random.nextInt(ownBlockedColumns.size())));
            }
        }
        return moves.get(moves.size()/2);
    }

    public Move improveOwn2CoinChains(List<Integer> notBlockedMoves) {
        //vertical
        for (int i = 0; i < winSituationDetector.getOwnDetectedChains().size(); i++) {
            if (winSituationDetector.getOwnDetectedChains().get(i).getChainType() == ChainType.VERTICAL &&
                    winSituationDetector.getOwnDetectedChains().get(i).getSize() == 2) {
                if (winSituationDetector.getOwnDetectedChains().get(i).getStartPositionRow() - 1 >= 0) {
                    if (manager.getPlayerEnumAtPosition(
                            winSituationDetector.getOwnDetectedChains().get(i).getStartPositionRow() - 1,
                            winSituationDetector.getOwnDetectedChains().get(i).getStartPositionCol()) == PlayerEnum.EMPTY) {
                        return getMoveOfColumn(winSituationDetector.getOwnDetectedChains().get(i).getStartPositionCol());
                    }
                }
            }
        }
        return null;
    }

    public List<Integer> getNotBlockedMovesOfRemainingColumns() {
        List<Integer> notBlockedMoves = manager.getVirtualGameBoard().getRemainingColumns();
        for (int i = 0; i < manager.getVirtualGameBoard().getRemainingColumns().size(); i++) {
            for (int j = 0; j < blockedColumnObjects.size(); j++) {
                if (manager.getVirtualGameBoard().getRemainingColumns().get(i) == blockedColumnObjects.get(j).getColumn()) {
                    //only remove from blocked moves if there are at least 2 possible ones
                    if (notBlockedMoves.size() >= 1) {
                        notBlockedMoves.remove(manager.getVirtualGameBoard().getRemainingColumns().get(i));
                    }
                }
            }
        }
        return notBlockedMoves;
    }


    public Move getMove(boolean meFirst) {

        //save real virtualGameBoard
        VirtualGameBoard tempVirtualGameBoard = manager.getVirtualGameBoard();

        //logic sequence
        //first moves
        if (meFirst == true) {
            return plannedMove = getMoveOfColumn(3);
        } else {
            //generate first move as 2nd player
            if (manager.getVirtualGameBoard().countEnemyCoinsOnBoard() == 1
                    && manager.getPlayerEnumAtPosition(6, 3) == PlayerEnum.EMPTY) {
                return plannedMove = getMoveOfColumn(3);
            }
            if (manager.getVirtualGameBoard().countEnemyCoinsOnBoard() == 1) {
                return plannedMove = getMoveOfColumn(3);
            }
        }

        //win it!
        if (getOwnWinMove(winSituationDetector.getOwnDetectedChains(), PlayerEnum.OWN) != null) {
            plannedMove = getOwnWinMove(winSituationDetector.getOwnDetectedChains(), PlayerEnum.OWN);
            return plannedMove;
        } else {
            //prevent enemy
            if (preventEnemyWin() != null) {
                plannedMove = preventEnemyWin();
            }
        }

        //set not blocked moves
        List<Integer> notBlockedMoves = getNotBlockedMovesOfRemainingColumns();

        //get basic, not blocked move
        if (plannedMove == null) {
            //prevent rival
            plannedMove = getNotBlockedpreventRivalChainMove(notBlockedMoves);
            if (plannedMove != null) {
            }

            //if no prevention move -> random move!
            if (plannedMove == null) {
                plannedMove = getNotBlockedRandomMove(notBlockedMoves);
            }
        }

        //restore virtualGameBoard
        manager.setVirtualGameBoard(tempVirtualGameBoard);

        //return move
        return plannedMove;
    }


    public Move getPlannedMove() {
        return plannedMove;
    }

    public Move getMoveOfColumn(int column) {
        Move returnMove = null;
        for (int i = 0; i < moves.size(); i++) {
            String moveString = moves.get(i).toString();
            if (Integer.parseInt(Character.toString(moveString.charAt(moveString.length() - 2))) == column + 1) {
                returnMove = moves.get(i);
            }
        }
        return returnMove;
    }

    public String getColumnOfMoveAsString(Move move) {
        String moveString = move.toString();
        return Character.toString(moveString.charAt(moveString.length() - 2));
    }
}

