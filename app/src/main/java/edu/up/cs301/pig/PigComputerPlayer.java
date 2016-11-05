package edu.up.cs301.pig;

import android.util.Log;

import java.util.Random;

import edu.up.cs301.game.GameComputerPlayer;
import edu.up.cs301.game.GamePlayer;
import edu.up.cs301.game.LocalGame;
import edu.up.cs301.game.actionMsg.GameAction;
import edu.up.cs301.game.infoMsg.GameInfo;
import edu.up.cs301.game.util.Tickable;

/**
 * An AI for Pig
 *
 * @author Andrew M. Nuxoll
 * @version August 2015
 */
public class PigComputerPlayer extends GameComputerPlayer {

    private PigGameState pig;



    /* *
     * ctor does nothing extra
     */
    public PigComputerPlayer(String name) {
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
        pig = (PigGameState)info;

        if(pig.getPlayerID() == this.playerNum)
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
