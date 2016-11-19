package edu.up.cs301.AggravationGame;

import android.util.Log;

/**
 * Created by petersoe19 on 11/19/2016.
 */

//calling the method
    // dieRoll:
    //


public class scratchcode {

    public void Moves(int pieceLoc, boolean enable) {
        Log.i("clicked", "click");
        int rollVal = gameStateInfo.getDieValue();
        int myNum = playerNum;
        int[] gameBoardCopy = gameStateInfo.getGameBoard();
        int[][] homeCopy = gameStateInfo.getHomeArray();
        int[][] startCopy = gameStateInfo.getStartArray();
        String boardType = "board";
        boolean possibleMove = false;

        else //(NORMAL BUTTONS)
        {
            Log.i("roll val is ", Integer.toString(rollVal));
            Log.i("PlayerNum", Integer.toString(playerNum));
            boolean enabled = false;
            for (int m = 0; m < 4; m++) {
                if (button == playerStart[playerNum][m] && startCopy[playerNum][m] == playerNum) {
                    if (rollVal == 1 || rollVal == 6) //if a one or a 6 & there are pieces to move from start array, enable space
                    {
                        this.gameBoard[playerNum * 14].setEnabled(true);
                        Log.i("enabled", Integer.toString(playerNum * 14));
                        markedButton = m; //button the move will be send from
                        Log.i("markedButton", Integer.toString(m));
                        boardType = "start";

                    }
                }
            }
            for (int i = 0; i < 57; i++) {
                if (button == this.gameBoard[i]) //finds the button index
                    Log.i("button clicked is", Integer.toString(i));


                if (gameBoardCopy[i] == playerNum) //if the player clicked on its own button
                {

                    markedButton = i;

                    //CASE: roll val on the board
                    if (((i + rollVal) > (playerNum * 14)) && ((i + rollVal) < (55 - playerNum * 14))) //if there is a viable button for player button + roll
                        if ((i + rollVal) > 55) {
                            int correctedSpace = rollVal + i - 55; //"over the top space"
                            if (gameBoardCopy[correctedSpace] != playerNum)//"over the top"
                            {
                                if (checkPieceOrder(currentPieceLocations, playerNum, i, correctedSpace)) {
                                    this.gameBoard[correctedSpace].setEnabled(true);
                                    Log.i("enabledCS1", Integer.toString(correctedSpace));
                                }
                            }
                        } else if ((i + rollVal) > 55 || (gameBoardCopy[i + rollVal]) != playerNum) {
                            if ((i + rollVal) > 55) {
                                int correctedSpace = rollVal + i - 55; //"over the top space"
                                if (gameBoardCopy[correctedSpace] != playerNum) {
                                    if (checkPieceOrder(currentPieceLocations, playerNum, i, (correctedSpace))) {
                                        this.gameBoard[correctedSpace].setEnabled(true); //enables that button
                                        Log.i("enabledCS2", Integer.toString(correctedSpace));
                                    }
                                }
                            } else if (checkPieceOrder(currentPieceLocations, playerNum, i, (i + rollVal))) {
                                this.gameBoard[i + rollVal].setEnabled(true); //enables that button
                                Log.i("enabledcso", Integer.toString(i + rollVal));
                            }
                        }
                }


                //CASE: Potential home array move
                { //checks the Home Arrays
                    int spacesToMove = 55 - 14 * playerNum - i;
                    if (spacesToMove < 5) {
                        for (int k = 0; i < 5; i++) { //enables possible buttons in home array
                            if (homeCopy[playerNum][k + spacesToMove] != playerNum) {
                                playerHome[playerNum][k + spacesToMove].setEnabled(true);
                                Log.i("enabledstm", Integer.toString(k + spacesToMove));
                            }

                        }
                    }
                }

                if ((i + rollVal) == (5 + 1) || (i + rollVal) == (19 + 1) || (i + rollVal) == (33 + 1) || (i + rollVal) == (47 + 1)) //if the player can directly land on middle shortcut
                {
                    if (gameBoardCopy[56] != playerNum) {
                        this.gameBoard[56].setEnabled(true); //enable middle
                        Log.i("enabledrv", Integer.toString(56));
                    }


                    if (i == 5 || i == 19 || i == 33 || i == 47) //if the player is on a corner shortcut
                    {
                        int moveSpace;
                        if (rollVal == 1 && i + 14 * rollVal < playerNum * 14 + 56) //1 shortcut
                        {
                            moveSpace = i + 14 * rollVal;
                            if (moveSpace > 56) {
                                moveSpace = moveSpace - 56;
                            }

                            if (gameBoardCopy[moveSpace] != playerNum) {
                                this.gameBoard[moveSpace].setEnabled(true);
                                Log.i("enabledmavespace", Integer.toString(moveSpace));
                            }
                        }
                        if (rollVal == 2) {
                            if (i + 14 * rollVal < playerNum * 14 + 56) //take 2 shortcuts
                            {
                                moveSpace = i + 14 * rollVal;
                                if (moveSpace > 56) {
                                    moveSpace = moveSpace - 56;
                                }

                                if (gameBoardCopy[moveSpace] != playerNum) {
                                    this.gameBoard[moveSpace].setEnabled(true);
                                    Log.i("enabled", Integer.toString(moveSpace));
                                }
                            }
                            if (i + 14 * 1 + 1 < playerNum * 14 + 56) //take 1 shortcut + one step
                            {
                                moveSpace = i + 14 * 1 + 1;
                                if (moveSpace > 56) {
                                    moveSpace = moveSpace - 56;
                                }

                                if (gameBoardCopy[moveSpace] != playerNum) {
                                    this.gameBoard[moveSpace].setEnabled(true);
                                    Log.i("enabled", Integer.toString(moveSpace));
                                }
                            }
                        }
                        if (rollVal == 3) {
                            if (i + 14 * rollVal < playerNum * 14 + 56) //take 3 shortcuts
                            {
                                moveSpace = i + 14 * rollVal;
                                if (moveSpace > 56) {
                                    moveSpace = moveSpace - 56;
                                }

                                if (gameBoardCopy[moveSpace] != playerNum) {
                                    this.gameBoard[moveSpace].setEnabled(true);
                                    Log.i("enabled", Integer.toString(moveSpace));
                                }
                            }
                            if (i + 14 * 2 + 1 < playerNum * 14 + 56) //take 2 shortcuts and 1 step
                            {
                                moveSpace = i + 14 * 2 + 1;
                                if (moveSpace > 56) {
                                    moveSpace = moveSpace - 56;
                                }

                                if (gameBoardCopy[moveSpace] != playerNum) {
                                    this.gameBoard[moveSpace].setEnabled(true);
                                    Log.i("enabled", Integer.toString(moveSpace));
                                }
                            }

                            if (i + 14 * 1 + 2 < playerNum * 14 + 56) //take 1 shortcuts and 2 steps
                            {
                                moveSpace = i + 14 * 1 + 2;
                                if (moveSpace > 56) {
                                    moveSpace = moveSpace - 56;
                                }
                                if (gameBoardCopy[moveSpace] != playerNum) {
                                    this.gameBoard[moveSpace].setEnabled(true);
                                    Log.i("enabled", Integer.toString(moveSpace));
                                }
                            }


                        }
                        if (rollVal > 3) {
                            if (i + 14 * 3 + (rollVal - 3) < playerNum * 14 + 56) ////3 shortcuts + x step
                            {
                                moveSpace = i + 14 * 3 + (rollVal - 3);
                                if (moveSpace > 56) {
                                    moveSpace = moveSpace - 56;
                                }

                                if (gameBoardCopy[moveSpace] != playerNum) {
                                    this.gameBoard[moveSpace].setEnabled(true);
                                    Log.i("enabled", Integer.toString(moveSpace));
                                }
                            }

                            if (i + 14 * 2 + (rollVal - 2) < playerNum * 14 + 56) //take 2 shortcuts and 2 steps
                            {
                                moveSpace = i + 14 * 2 + (rollVal - 2);
                                if (moveSpace > 56) {
                                    moveSpace = moveSpace - 56;
                                }

                                if (gameBoardCopy[moveSpace] != playerNum) {
                                    this.gameBoard[moveSpace].setEnabled(true);
                                    Log.i("enabled", Integer.toString(moveSpace));
                                }
                            }

                            if (i + 14 * 1 + (rollVal - 3) < playerNum * 14 + 56) //take 1 shortcuts and 3 steps
                            {
                                moveSpace = i + 14 * 1 + (rollVal - 3);
                                if (moveSpace > 56) {
                                    moveSpace = moveSpace - 56;
                                }

                                if (gameBoardCopy[moveSpace] != playerNum) {
                                    this.gameBoard[moveSpace].setEnabled(true);
                                    Log.i("enabled", Integer.toString(moveSpace));
                                }
                            }

                        }
                    }

                    if (i == 56 && rollVal == 1) //if the player is in the middle space
                    {
                        if (gameBoardCopy[5] != playerNum) {
                            this.gameBoard[5].setEnabled(true);
                            Log.i("enabled", Integer.toString(5));
                        }
                        if (gameBoardCopy[19] != playerNum) {
                            this.gameBoard[19].setEnabled(true);
                            Log.i("enabled", Integer.toString(19));
                        }
                        if (gameBoardCopy[33] != playerNum) {
                            this.gameBoard[33].setEnabled(true);
                            Log.i("enabled", Integer.toString(33));
                        }
                        if (gameBoardCopy[47] != playerNum) {
                            this.gameBoard[47].setEnabled(true);
                            Log.i("enabled", Integer.toString(47));
                        }

                    }
                }
            }

            // if (enabled == false)
            {
                //   AggravationMovePieceAction move = new AggravationMovePieceAction(this, "Board",1,1);
                //   game.sendAction(move);
                //alternately try the marked btuton one below but talk to owen about how to handle
            }
            //IF NOTHING IS ENABLED SEND "BLANK" MOVE & note to user
            //else //if the player clicked on another space (space to move the button) to

            for (int k = 0; k < 56; k++) {
                if (button == this.gameBoard[k] && gameBoardCopy[k] != playerNum) {
                    Log.i("k ", Integer.toString(k));
                    Log.i("markedButton", Integer.toString(markedButton));
                    AggravationMovePieceAction move = new AggravationMovePieceAction(this, boardType, markedButton, k);
                    Log.i("sending move board", Integer.toString(markedButton));
                    game.sendAction(move);
                }
                //conditions
            }

            if (markedButton == -1) {
                AggravationMovePieceAction move = new AggravationMovePieceAction(this, "skip", -1, -1); //sends empty action
                game.sendAction(move);
                Log.i("sent", "blank move");
            }
        }

    }

}
