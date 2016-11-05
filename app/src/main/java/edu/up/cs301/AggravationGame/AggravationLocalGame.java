package edu.up.cs301.AggravationGame;

import edu.up.cs301.game.GamePlayer;
import edu.up.cs301.game.LocalGame;
import edu.up.cs301.game.actionMsg.GameAction;

import java.util.Random;

/**
 * class AggravationLocalGame controls the play of the game
 *
 * @author Andrew M. Nuxoll, modified by Steven R. Vegdahl
 *          modified by Emily Peterson, Andrew Ripple & Owen Price
 *
 * @version February 2016
 */
public class AggravationLocalGame extends LocalGame {

    /**
     * This ctor creates a new game state
     */
    private AggravationState officialGameState;
    private AggravationState copyGameState;
    public AggravationLocalGame() {
        super();
        officialGameState= new AggravationState();

    }

    /**
     * can the player with the given id take an action right now?
     */
    @Override
    protected boolean canMove(int playerIdx) {

        if(pig.getPlayerID() == playerIdx)
        {
            return true;
        }
        else
        {
            return false;
        }
    }

    /**
     * This method is called when a new action arrives from a player
     *
     * @return true if the action was taken or false if the action was invalid/illegal.
     */
    @Override
    protected boolean makeMove(GameAction action) {

        if(action instanceof PigRollAction)
        {
            Random dieValue = new Random();
            int value = dieValue.nextInt(6-1+1) + 1;
            if(value == 1)
            {
                pig.setRunningTotal(0);
                int x = pig.getPlayerID();
                pig.setPlayerID(1-x);
                pig.setDiceValue(value);      //ADDTHIS HERE TOO    WOEIFJEFIO
                return true;

            }
            else
            {
                pig.setRunningTotal(value+pig.getRunningTotal());
                pig.setDiceValue(value);   ///ADD THIS HERE TO MAKE DICE-PICTURE CHANGE CORRECTLY OMG
                return true;
            }
        }
        else if(action instanceof PigHoldAction)
        {
            int x = pig.getPlayerID();
            if(x == 0)
            {
                pig.setPlayerZeroScore(pig.getPlayerZeroScore() + pig.getRunningTotal());
                pig.setRunningTotal(0);
                int y = pig.getPlayerID();
                pig.setPlayerID(1-y);

            }
            else
            {
                pig.setPlayerOneScore(pig.getRunningTotal() + pig.getPlayerOneScore());
                pig.setRunningTotal(0);
                int y = pig.getPlayerID();
                pig.setPlayerID(1-y);
            }
            return true;
        }

        return false;
    }//makeMove

    /**
     * send the updated state to a given player
     */
    @Override
    protected void sendUpdatedStateTo(GamePlayer p) {
        piggy = new PigGameState(pig);
        p.sendInfo(piggy);
    }//sendUpdatedSate

    /**
     * Check if the game is over
     *
     * @return
     *        a message that tells who has won the game, or null if the
     *        game is not over
     */
    @Override
    protected String checkIfGameOver() {
        if(pig.getPlayerOneScore() >= 50)
        {
            return "Player 1 has won the game with a score of " + pig.getPlayerOneScore() + "!!";
        }
        else if(pig.getPlayerZeroScore() >= 50)
        {
            return "Player 0 has won the game with a score of " + pig.getPlayerZeroScore() + "!!";
        }
        return null;
    }

}// class PigLocalGame
