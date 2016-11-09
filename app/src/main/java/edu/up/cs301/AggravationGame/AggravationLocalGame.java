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
        return officialGameState.getTurn() == playerNum;
    }

    /**
     * This method is called when a new action arrives from a player
     *
     * @return true if the action was taken or false if the action was invalid/illegal.
     */
    @Override
    protected boolean makeMove(GameAction action) {

        officialGameState.setRoll(false);//assume that a player cannot roll again
        Random dieValue = new Random();//dieValue outside of the conditionals
        int value = dieValue.nextInt(6-1+1) + 1;

        if(action instanceof AggravationRollAction)
        {
            officialGameState.setDieValue(value);
            return true;
        }
        else if(action instanceof AggravationMovePieceAction)
        {
            int playerNum =((AggravationMovePieceAction) action).playerNum;
            int newIdx = ((AggravationMovePieceAction) action).newIdx;
            int oldIdx=((AggravationMovePieceAction) action).oldIdx;
            String type = ((AggravationMovePieceAction) action).type;
            int [] boardCopy = officialGameState.getGameBoard();
            int startCopy[]= officialGameState.getStartArray(playerNum);


            if (type.equalsIgnoreCase("Start")) {

                if(boardCopy[newIdx]==-1) {
                    startCopy[oldIdx]=-1;
                    boardCopy[newIdx] = playerNum;
                }

                else {//aggravating move code?
                    int otherPlayerNum =boardCopy[newIdx];//whatever value is in the space
                    for (int i=0;i<4;i++){
                        if (officialGameState.getStartArray(otherPlayerNum)[i]==-1){//first empty space in otherPlayerNum start array
                            startCopy[i]=otherPlayerNum;//put their piece back in their start array
                            boardCopy[newIdx]=playerNum;
                        }
                    }
                }

                officialGameState.setStartArray(playerNum,startCopy);
                officialGameState.setGameBoard(boardCopy);
            }
            else if (type.equalsIgnoreCase("Board")) {

                boardCopy = officialGameState.getGameBoard();
                boardCopy[oldIdx]=-1;
                boardCopy[newIdx]=playerNum;//needs more code
            }
            else if (type.equalsIgnoreCase("Home")) {
                //CODE HERE
            }
            //CODE HERE

            if(value == 6) //if the player rolled a 6
            {
                officialGameState.setRoll(true);
                return true;
            }
            int turn = officialGameState.getTurn();
            officialGameState.setTurn(turn+1);
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
