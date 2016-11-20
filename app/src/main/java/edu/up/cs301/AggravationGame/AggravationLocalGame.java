package edu.up.cs301.AggravationGame;

import android.util.Log;

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
    protected boolean canMove(int playerNum)
    {
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
        Log.i("dieVal is ", Integer.toString(value));

        int playerNum=getPlayerIdx(action.getPlayer());
        int [] boardCopy = officialGameState.getGameBoard();
        int startCopy[]= officialGameState.getStartArray(playerNum);


        if(action instanceof AggravationRollAction)
        {
            officialGameState.setDieValue(value);
            Log.i("set value LocalGame", Integer.toString(officialGameState.getDieValue()));
            officialGameState.setRoll(false);
            System.out.println("Roll = " + value);
            Log.i("set value", Integer.toString(value));
            sendUpdatedStateTo(action.getPlayer());
            return true;
        }
        else if(action instanceof AggravationMovePieceAction)
        {

            int newIdx = ((AggravationMovePieceAction) action).newIdx;
            int oldIdx=((AggravationMovePieceAction) action).oldIdx;
            String type = ((AggravationMovePieceAction) action).type;


            if (type.equalsIgnoreCase("Start")) {

                /*A marble must be "started" before it can be advanced around the board.
                To do this, it is moved from the base row to the start hole.
                A player only moves a marble to the start hole when he or she rolls a
                six or a one—it cannot be advanced until the player's next turn.
                (Remember: If you roll a six, you get an extra turn!) Each player can only
                have one marble occupying the start hole at a time.*/

                //if the desired space is empty, empty the old space and set the new space to playerNum
                if(boardCopy[newIdx]==-1) {
                    startCopy[oldIdx]=-1;
                    boardCopy[newIdx]=playerNum;
                    Log.i("newIdx",""+newIdx);
                }
                else if (boardCopy[newIdx]==playerNum){//started a marble before this and haven't moved it yet
                    return false;//not a valid move, so they need to make another
                }
                else {//aggravating move code - space is not empty and not this player
                    int otherPlayerNum = boardCopy[newIdx];//whatever value is in the space
                    int otherStart[] = officialGameState.getStartArray(otherPlayerNum);
                    for (int i=0;i<4;i++){
                        //find first empty space in otherPlayerNum start array
                        if (otherStart[i]==-1){
                            //put their piece back in their start array
                            otherStart[i]=otherPlayerNum;
                            Log.i("otherPlayerNum",""+otherPlayerNum);
                            //move piece out of this player's start array
                            startCopy[oldIdx]=-1;
                            boardCopy[newIdx]=playerNum;
                            officialGameState.setStartArray(otherPlayerNum,otherStart);
                        }
                    }
                }
                officialGameState.setStartArray(playerNum,startCopy);
                officialGameState.setGameBoard(boardCopy);
                sendUpdatedStateTo(action.getPlayer());
            }
            else if (type.equalsIgnoreCase("Board")) {
                /*Jumping over or landing on an opponent's marbles is permitted.
                For more on landing on an opponent's marble, see the "Getting Aggravated"
                section. However, jumping over or landing on your own is not.
                If one of your own marbles prevents you from moving another marble the
                full count on the dice, then you are prevented from moving the "blocked"
                marble, and your turn is forfeited.*/


                /*Trying to handle home bases
                * endOfTheLine is the last space before the piece should be moving to the home base*/
                int endOfTheLine = playerNum*14 -2;
                if(endOfTheLine==-2){
                    endOfTheLine=54;
                }
                if (newIdx>=endOfTheLine){
                    int homeCopy[] = officialGameState.getHomeArray(playerNum);
                    int homeIdx=newIdx-endOfTheLine;
                    if (homeIdx>3) {
                        return false;
                    }

                    /*leapfrog prevention code*/
                    for(int i = oldIdx+1;i<=homeIdx;i++){
                        if (homeCopy[i]==playerNum){
                            return false;
                        }
                    }
                    if (homeCopy[homeIdx]==-1){
                        boardCopy[oldIdx]=-1;
                        homeCopy[homeIdx]=playerNum;
                        sendUpdatedStateTo(action.getPlayer());

                    }
                }

                //return false if you would be "leapfrogging" one of your own
                //Should never happen with CPU
                for(int i = oldIdx+1;i<=newIdx;i++){
                    if (boardCopy[i]==playerNum){
                        return false;
                    }
                }

                if (boardCopy[newIdx]==-1){
                    boardCopy[oldIdx]=-1;
                    boardCopy[newIdx]=playerNum;
                    sendUpdatedStateTo(action.getPlayer());

                }
                else {
                    int otherPlayerNum=boardCopy[newIdx];
                    for (int i=0;i<4;i++){
                        //find first empty space in otherPlayerNum start array
                        if (officialGameState.getStartArray(otherPlayerNum)[i]==-1){
                            //put their piece back in their start array
                            startCopy[i]=otherPlayerNum;
                            boardCopy[newIdx]=playerNum;
                            sendUpdatedStateTo(action.getPlayer());

                        }
                    }
                }
            }
            else if (type.equalsIgnoreCase("Skip")) {

            }

            if(value == 6) //if the player rolled a 6
            {
                System.out.println("Roll was a 6.");
                officialGameState.setRoll(true);
                sendUpdatedStateTo(action.getPlayer());
                return true;
            }
            else
            {
                System.out.println("Roll was not a 6.");
                int turn = officialGameState.getTurn();
                officialGameState.setTurn(turn + 1);
                officialGameState.setRoll(true);
                sendUpdatedStateTo(action.getPlayer());
                Log.i("new Player turn", Integer.toString(turn+1));
                return true;
            }
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
        int countZeroPiece = 0;
        int countOnePiece = 0;
        int countTwoPiece = 0;
        int countThreePiece = 0;
        for(int i = 0;i < 4; i++)
        {
            int[][] tempGameOver = officialGameState.getHomeArray();
            if(tempGameOver[0][i] == 0)
            {
                countZeroPiece++;
            }
        }
        for(int i = 0;i < 4; i++)
        {
            int[][] tempGameOver = officialGameState.getHomeArray();
            if(tempGameOver[1][i] == 0)
            {
                countOnePiece++;
            }
        }
        for(int i = 0;i < 4; i++)
        {
            int[][] tempGameOver = officialGameState.getHomeArray();
            if(tempGameOver[2][i] == 0)
            {
                countTwoPiece++;
            }
        }
        for(int i = 0;i < 4; i++)
        {
            int[][] tempGameOver = officialGameState.getHomeArray();
            if(tempGameOver[3][i] == 0)
            {
                countThreePiece++;
            }
        }
        if(countZeroPiece==4){
            return "Player 0 Wins!";
        }
        if(countOnePiece==4){
            return "Player 1 Wins!";
        }
        if(countTwoPiece==4){
            return "Player 2 Wins!";
        }
        if(countThreePiece==4){
            return "Player 3 Wins!";
        }
        return null;
    }

}// class AggravationLocalGame
