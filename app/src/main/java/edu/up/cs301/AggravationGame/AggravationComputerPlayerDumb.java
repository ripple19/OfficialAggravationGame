package edu.up.cs301.AggravationGame;

import android.util.Log;

import java.util.Random;

import edu.up.cs301.game.GameComputerPlayer;
import edu.up.cs301.game.infoMsg.GameInfo;

/**
 * An dumb AI for Aggravation
 *
 * @author Owen Price, Emily Peterson, Andrew Ripple
 * @version Nov 2016
 */
public class AggravationComputerPlayerDumb extends GameComputerPlayer {

    private AggravationState gameStateInfo; //the copy of the game state

    /* *
       * ctor does nothing extra
       */
    public AggravationComputerPlayerDumb(String name) {
        super(name);
    }

    /**
     * callback method--game's state has changed
     * dumbAI: start a piece -> move the piece closest to the start -> move the piece blocking that piece
     * does not use shortcuts at all or aggravate intentionally, but should not miss a possible move otherwise
     *
     * @param info the information (presumably containing the game's state)
     */
    @Override
    protected void receiveInfo(GameInfo info) {
        if (info instanceof AggravationState) {
            gameStateInfo = (AggravationState) info;
            if (this.playerNum == gameStateInfo.getTurn()) {
                sleep(2000);
                Log.i("my turn player", Integer.toString(this.playerNum));
                //getRoll returns whether or there is a roll to be made - either the start of a turn or
                //after rolling a 6 and making a valid move
                if (gameStateInfo.getRoll()) {
                    AggravationRollAction rollAct = new AggravationRollAction(this);
                    game.sendAction(rollAct);
                    sleep(2000);
                    System.out.println("I rolled!");
                    return;
                } else {
                    {//don't have to roll, so move a piece
                        int value = gameStateInfo.getDieValue();
                        int startCopy[] = gameStateInfo.getStartArray(this.playerNum);
                        int boardCopy[] = gameStateInfo.getGameBoard();
                        int startIdx = this.playerNum * 14;

                    /*This is where a start comes from*/
                        if (value == 6 || value == 1) {
                    /*try to start whenever possible, so look through start array for a piece to move
                    and check the starting space to see if empty or an opponents piece to aggravate*/
                            for (int j = 0; j < 4; j++) {
                        /*playerNum *14 is where player starts based on index*/
                                if (startCopy[j] == this.playerNum && boardCopy[startIdx] != this.playerNum) {
                             /*Send local game the starting move*/
                                    AggravationMovePieceAction startPiece =
                                            new AggravationMovePieceAction(this, "Start", j, startIdx);
                                    game.sendAction(startPiece);
                                    return;
                                }

                            }
                        }

                        //check to see if there even is a piece on the board the CPU can look to move
                        int toMoveFrom = -1;//default value, no -1 idx to confuse with
                        for (int i = startIdx; i < startIdx + 56; i++) {
                            int j = i;
                            while (j > 56) {
                                j = j - 56;
                            }
                    /*found a piece to (try to) move, so set toMoveFrom to that spot*/
                            if (boardCopy[j] == this.playerNum) {
                                toMoveFrom = j;
                                break;
                            }
                        }

                        //find a piece "in the way" of toMoveFrom and reset the loop around that piece
                        //moves the first "in the way" piece it can, so it can start all of its pieces as fast as possible
                        //if toMoveFrom!=-1 that means the previous loop found a space with playerNum as a value and is trying to move it
                   /*This is where a Board move comes from*/
                        if (toMoveFrom != -1) {
                            for (int i = 1; i <= value; i++) {
                                if (boardCopy[toMoveFrom + i] == this.playerNum) {
                                    toMoveFrom = +i;//move from the idx of the piece blocking you, instead
                                    i = 1;//reset the loop
                                }
                            }
                            int toMoveTo = toMoveFrom + value;
                            AggravationMovePieceAction movePieceGetOutTheWay;
                            movePieceGetOutTheWay = new AggravationMovePieceAction(this, "Board", toMoveFrom, toMoveTo);
                            game.sendAction(movePieceGetOutTheWay);
                        }
                    /*This is where a Skip move comes from*/
                        else {//no pieces to move, so send a skip turn
                            AggravationMovePieceAction ff = new AggravationMovePieceAction(this, "Skip", 0, 0);
                            game.sendAction(ff);
                        }
                    }//move a piece
                }
            }
        }//receiveInfo
    }
}


