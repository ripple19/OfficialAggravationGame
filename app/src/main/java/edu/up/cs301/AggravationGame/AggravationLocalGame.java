package edu.up.cs301.AggravationGame;

import edu.up.cs301.game.GamePlayer;
import edu.up.cs301.game.LocalGame;
import edu.up.cs301.game.actionMsg.GameAction;

import java.util.Random;

/**
 * class AggravationLocalGame controls the play of the game
 *
 * @author Emily Peterson, Andrew Ripple & Owen Price
 *
 * @version November 2016
 */
public class AggravationLocalGame extends LocalGame {

    /**
     * This ctor creates a new game state
     */
    private AggravationState officialGameState;
    private AggravationState copyGameState;
    public AggravationLocalGame()
    {
        super();
        officialGameState= new AggravationState();
    }

    /**
     * can the player with the given id take an action right now?
     */
    @Override
    protected boolean canMove(int playerNum) {

        if(officialGameState.getTurn() == playerNum)
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

        if(action instanceof AggravationRollAction)
        {
            Random dieValue = new Random();
            int value = dieValue.nextInt(6-1+1) + 1;
            if(value != 6) //if the player did not roll a 6
            {
                int playerNum = officialGameState.getTurn();
                officialGameState.setTurn(playerNum+1);
                officialGameState.setDieValue(value);
                return true;

            }
            else
            {
                officialGameState.setDieValue(value);
                return true;
            }
        }
        else if(action instanceof AggravationMovePieceAction)
        {
            int playerNum = officialGameState.getTurn();

            //CODE HERE

            return true;
        }

        return false;
    }//makeMove

    /**
     * send the updated state to a given player
     */
    @Override
    protected void sendUpdatedStateTo(GamePlayer p) {
        copyGameState = new AggravationState(officialGameState);
        p.sendInfo(copyGameState);
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
        //check stuff with official game state
        return null;
    }

}// class AggravationLocalGame
