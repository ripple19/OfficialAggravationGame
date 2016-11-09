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
        gameStateInfo = (AggravationState)info;

        if(gameStateInfo.getTurn() == this.playerNum) {
            if(gameStateInfo.getRoll()){
                AggravationRollAction rollAct = new AggravationRollAction(this);
                game.sendAction(rollAct);
                return;
            }
            else
            {//don't have to roll

                int value=gameStateInfo.getDieValue();

                if(value==6){//if the value is 6, move from start array


                    int tempGameBoard[]=gameStateInfo.getGameBoard();
                    for(int j =0;j<4;j++) {
                        if (tempGameBoard[playerNum * 14] == -1) {//if the starting space is empty

                            AggravationMovePieceAction startPieceToEmpty =
                                    new AggravationMovePieceAction(this, "Start", j, playerNum * 14);
                            game.sendAction(startPieceToEmpty);
                        }
                    }

                }

            }
        }


            //EMILY'S NOTE: Leaving the below here as an example

           /* Random x = new Random();
            int y = x.nextInt(100-0+1)+0;
            Log.i("y = sdsds ", Integer.toString(y));
            System.out.println("Hello");

            if(y<=51)
            {
                game.sendAction(holdAct);
            }
            else if(y>50)
            {
                game.sendAction(rollAct);
            }
            */

        }


    }//receiveInfo


