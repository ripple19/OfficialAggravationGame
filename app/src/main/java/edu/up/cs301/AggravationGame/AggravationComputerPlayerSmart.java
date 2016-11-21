package edu.up.cs301.AggravationGame;

import android.util.Log;

import java.util.Random;

import edu.up.cs301.game.GameComputerPlayer;
import edu.up.cs301.game.infoMsg.GameInfo;

/**
 * An smart AI for Aggravation
 *
 * @author Emily Peterson & Andrew Ripple, & Owen Price
 * @version Nov 2016
 */
public class AggravationComputerPlayerSmart extends GameComputerPlayer {

    private AggravationState gameStateInfo; //the copy of the game state



    /* *
     * ctor does nothing extra
     */
    public AggravationComputerPlayerSmart(String name) {
        super(name);
    }

    /**
     * callback method--game's state has changed
     *
     * @param info
     *        the information (presumably containing the game's state)
     */
    @Override
    protected void receiveInfo(GameInfo info) {
        Log.i("received", "info");
        if (info instanceof AggravationState) {
            gameStateInfo = (AggravationState) info;

            if (gameStateInfo.getTurn() == playerNum) {
                Log.i("my", "turn!");
                sleep(3000);
                //getRoll returns whether or there is a roll to be made - either the start of a turn or
                //after rolling a 6 and making a valid move
                if (gameStateInfo.getRoll()) {
                    AggravationRollAction rollAct = new AggravationRollAction(this);
                    game.sendAction(rollAct);
                }


                else {//don't have to roll, so move a piece
                    int value = gameStateInfo.getDieValue();
                    int startCopy[] = gameStateInfo.getStartArray(playerNum);
                    int boardCopy[] = gameStateInfo.getGameBoard();
                    int startIdx=playerNum*14;
                    int[] targetAcquired = boardCopy;
                    /*idea: set certain values in the target array to 0, like shortcuts, the home array,
                    and opponent pieces, then loop through to see if any of the CPU's pieces can move to
                    any of those places - maybe priority aggravate->shortcut->home?
                    should add that if you have a piece in the center get it out as soon as
                    you roll a 1, stuff like that
                    */
                    if (value == 6 || value == 1) {
                    /*try to start whenever possible, so look through start array for a piece to move
                    and check the starting space to see if empty or an opponents piece to aggravate*/
                        for (int j = 0; j < 4; j++) {
                        /*playerNum *14 is where player starts based on index*/
                            if (startCopy[j] == playerNum && boardCopy[startIdx] != playerNum) {
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
                        if (j > 56) {//so j always refers to a real index on the board
                            j = j - 56;
                        }
                    /*found a piece to (try to) move, so set toMoveFrom to that spot*/
                        if (boardCopy[j] == playerNum) {
                            toMoveFrom = j;
                            break;
                        }
                    }
                    //find a piece "in the way" of toMoveFrom and reset the loop around that piece
                    //moves the first "in the way" piece it can, so it can start all of its pieces as fast as possible
                    //if toMoveFrom!=-1 that means the previous loop found a space with playerNum as a value and is trying to move it
                    if (toMoveFrom != -1) {
                        for (int i = 1; i <= value; i++) {
                            if (boardCopy[toMoveFrom + i] == playerNum) {
                                toMoveFrom = +i;//move from the idx of the piece blocking you, instead
                                i = 1;//reset the loop
                            }
                        }
                        int toMoveTo = toMoveFrom + value;
                        AggravationMovePieceAction movePieceGetOutTheWay;
                        movePieceGetOutTheWay = new AggravationMovePieceAction(this, "Board", toMoveFrom, toMoveTo);
                        game.sendAction(movePieceGetOutTheWay);
                    }

                    else {//no pieces to move, so send a skip turn
                        AggravationMovePieceAction ff = new AggravationMovePieceAction(this, "Skip", 0, 0);
                        game.sendAction(ff);
                    }
                }//move a piece
            }
        }
    }//receiveInfo
}

//class AggravationComputerPlayerSmart