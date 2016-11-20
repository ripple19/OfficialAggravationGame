package edu.up.cs301.AggravationGame;

import android.util.Log;

import java.util.Random;

import edu.up.cs301.game.GameComputerPlayer;
import edu.up.cs301.game.infoMsg.GameInfo;

/**
 * An dumb AI for Aggravation
 *
 * @author Andrew M. Nuxoll
 *          modified by Emily Peterson & Andrew Ripple & Owen Price
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
     *
     * @param info
     *        the information (presumably containing the game's state)
     */
    @Override
    protected void receiveInfo(GameInfo info) {
        Log.i("received", "info");
       if (info instanceof AggravationState) {
           gameStateInfo = (AggravationState) info;

        /*all the code is only if it's the correct turn*/
           if (gameStateInfo.getTurn() == this.playerNum) {
               Log.i("my", "turn!");
            /*getRoll returns whether or there is a roll to be made - either the start of a turn or
            * after rolling a 6 and making a complete move*/
               sleep(3000);
               if (gameStateInfo.getRoll()) {
                   AggravationRollAction rollAct = new AggravationRollAction(this);
                   game.sendAction(rollAct);
                   return;

               } else {//don't have to roll, so move a piece
                   sleep(3000);
                   int value = gameStateInfo.getDieValue();
                   int tempStart[] = gameStateInfo.getStartArray(playerNum);
                   int tempGameBoard[] = gameStateInfo.getGameBoard();
                   if (value == 6 || value == 1) {
                    /*try to start whenever possible, so look through start array for a piece to move
                    and check the starting space to see if empty or an opponents piece to aggravate*/
                       for (int j = 0; j < 4; j++) {
                        /*playerNum *14 is where player starts based on index*/
                           if (tempStart[j] == playerNum && tempGameBoard[playerNum * 14] != playerNum) {
                            /*Send local game the move*/
                               AggravationMovePieceAction startPiece =
                                       new AggravationMovePieceAction(this, "Start", j, playerNum * 14);
                               game.sendAction(startPiece);
                               return;
                           }
                       }
                   }
                   //check to see if there is even a piece on the board the player can try to move
                   int toMoveFrom = -1;
                   for (int i = playerNum * 14; i < playerNum * 14 + 56; i++) {
                       int j = i;
                       if (j > 56) {//so j always refers to a real index on the board
                           j = j - 56;
                       }

                    /*found a piece to (try to) move, so set toMoveFrom to that spot*/
                       if (tempGameBoard[j] == playerNum) {
                           toMoveFrom = j;
                           break;
                       }
                   }
                   //find a piece "in the way" of toMoveFrom and reset the loop around that piece
                   //moves the first "in the way" piece it can, so it can start all of its pieces as fast as possible
                   //if toMoveFrom!=-1 that means the previous loop found a space with playerNum as a value and is trying to move it
                   if (toMoveFrom != -1) {
                       for (int i = 1; i <= value; i++) {
                           if (tempGameBoard[toMoveFrom + i] == playerNum) {
                               toMoveFrom = +i;
                               i = 1;
                           }
                       }
                       int toMoveTo = toMoveFrom + value;
                       AggravationMovePieceAction movePieceGetOutTheWay = new AggravationMovePieceAction(this, "Board", toMoveFrom, toMoveTo);
                       game.sendAction(movePieceGetOutTheWay);
                   } else {//no pieces to move, so send a skip turn
                       AggravationMovePieceAction ff = new AggravationMovePieceAction(this, "Skip", 0, 0);
                       game.sendAction(ff);
                   }
               }//move a piece
           }
       }
    }//receiveInfo
}


