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

    private int officialRoll=0;

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
            officialRoll = gameStateInfo.getDieValue();
            int startCopy[] = gameStateInfo.getStartArray(this.playerNum);
            int boardCopy[] = gameStateInfo.getGameBoard();
            int startIdx = this.playerNum * 14;
            int toMoveFrom=-9;
            int toMoveTo=-9;
            String moveType ="Board";

            if(gameStateInfo.getTurn()!=this.playerNum) {
                sleep(500); //if it's not your turn, give the other players some time to take their turns
            }  //SLEEP CHANGED FROM 1000
            else {
                sleep(550); //CHANGED FROM 2000
                Log.i("my turn player", Integer.toString(this.playerNum));

                //getRoll returns whether or there is a roll to be made - either the start of a turn or
                //after rolling a 6 and making a valid move
                if (gameStateInfo.getRoll()) {
                    AggravationRollAction rollAct = new AggravationRollAction(this);
                    game.sendAction(rollAct);
                    sleep(500); //CHANGED FROM 1000
                    System.out.println("I rolled!");
                }
                //don't have to roll, so move a piece
                else {
                    /*This is where a start move comes from*/
                    if (officialRoll == 6 || officialRoll == 1) {
                    /*try to start whenever possible, so look through start array for a piece to move
                    and check the starting space to see if empty or an opponents piece to aggravate*/
                        for (int j = 0; j < 4; j++) {
                            if (startCopy[j] == this.playerNum && boardCopy[startIdx] != this.playerNum) {
                                AggravationMovePieceAction startPiece =
                                        new AggravationMovePieceAction(this, "Start", j, startIdx);
                                game.sendAction(startPiece);
                                return;
                            }
                        }
                    }
                    //check to see if there even is a piece on the board the CPU can look to move

                    for(int i=0;i<56; i++) {
                        int j= i+startIdx;

                        if(j>55) j-=55;

                        if (boardCopy[j] == playerNum){
                            toMoveFrom = j;
                            break;
                        }
                    }

                    Log.i("toMoveFrom is"," "+toMoveFrom);

                        //find a piece "in the way" of toMoveFrom and reset the loop around that piece
                        //moves the first "in the way" piece it can, so it can start all of its pieces as fast as possible
                        //if toMoveFrom!=-1, that means the previous loop found a space with playerNum as a officialRoll and is trying to move it
                        /*This is where a Board move comes from*/


                    if (toMoveFrom != -9) {//if toMoveFrom found a piece to move on the boardCopy[]
                        for (int i = 1; i <= officialRoll; i++) {
                            if(toMoveFrom + i > 55)
                            {
                                int stillToIterate = officialRoll - i;
                                int tempToMoveFrom = 0;
                                for(int y = 0;y <= stillToIterate;y++)
                                {
                                    if(boardCopy[y] == this.playerNum)
                                    {
                                        tempToMoveFrom+=y;
                                        toMoveFrom = tempToMoveFrom;
                                        i = 0;
                                        break;
                                    }
                                }

                            }
                            if (boardCopy[toMoveFrom + i] == this.playerNum) {
                                Log.i("There was a block","");
                                toMoveFrom+=i;
                                //move from the idx of the piece blocking you that you found at toMoveFrom+i
                                i = 0;//reset the loop at 1, so it doesn't get stuck on itself.
                                // if this doesn't fix the problem, idk why it can't find its first piece.
                            }
                        }
                        Log.i("toMoveFrom is"," "+toMoveFrom);
                        toMoveTo = toMoveFrom + officialRoll;
                        if(toMoveTo > 55)
                        {
                            toMoveTo -=56;
                        }


                        int endOfTheLine=startIdx-2;
                        if(this.playerNum==0)
                        {
                            endOfTheLine=54;
                        }


                        if (toMoveTo>endOfTheLine && toMoveFrom<=endOfTheLine) {
                            toMoveTo-=(endOfTheLine+1);
                            moveType="Home";
                        }
                        Log.i("toMoveTo is", toMoveTo+"");

                        /*AggravationMovePieceAction movePieceGetOutTheWay;
                        movePieceGetOutTheWay = new AggravationMovePieceAction(this, "Board", toMoveFrom, toMoveTo);
                        game.sendAction(movePieceGetOutTheWay);*/
                        Log.i("Action was sent?","h");
                    }

                    //no pieces to move, so send a skip turn
                    if (toMoveTo==-9) moveType="Skip";

                    AggravationMovePieceAction movePieceGetOutTheWay;
                    movePieceGetOutTheWay = new AggravationMovePieceAction(this, moveType, toMoveFrom, toMoveTo);
                    game.sendAction(movePieceGetOutTheWay);
                }//move a piece
            }
        }//receiveInfo
    }
}


