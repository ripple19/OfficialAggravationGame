package edu.up.cs301.AggravationGame;

import android.util.Log;

import java.util.Random;

import edu.up.cs301.game.GameComputerPlayer;
import edu.up.cs301.game.infoMsg.GameInfo;

/**
 * An AI for Pig
 *
 * @author Andrew M. Nuxoll
 *          modified by Emily Peterson & Andrew Ripple & Owen Price
 * @version Nov 2016
 */
public class AggravationComputerPlayer extends GameComputerPlayer {

    private AggravationState gameStateInfo; //the copy of the game state



    /* *
     * ctor does nothing extra
     */
    public AggravationComputerPlayer(String name) {
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

        if(gameStateInfo.getPlayerID() == this.playerNum)
        {
            PigHoldAction holdAct = new PigHoldAction(this);
            PigRollAction rollAct = new PigRollAction(this);
            Random x = new Random();
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

        }
        else
        {
            return;
        }

    }//receiveInfo

}
