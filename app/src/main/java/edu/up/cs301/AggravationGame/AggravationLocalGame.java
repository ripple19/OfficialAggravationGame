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
    private int actualRoll;
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
        int playerNum=getPlayerIdx(action.getPlayer());
        int boardCopy[] = officialGameState.getGameBoard();
        int startCopy[]= officialGameState.getStartArray(playerNum);
        int homeCopy[] = officialGameState.getHomeArray(playerNum);


        if(action instanceof AggravationRollAction)
        {
            Random dieValue = new Random();//dieValue outside of the conditionals
            int value = dieValue.nextInt(6-1+1) + 1;
            actualRoll = value;
            Log.i("dieVal is ", Integer.toString(value));
            officialGameState.setDieValue(value);
            Log.i("set value LocalGame", Integer.toString(officialGameState.getDieValue()));
            officialGameState.setRoll(false);
            System.out.println("Roll = " + value);
            Log.i("set value", Integer.toString(value));
            sendAllUpdatedState();
            return true;
        }
        else if(action instanceof AggravationMovePieceAction)
        {

            int newIdx = ((AggravationMovePieceAction) action).newIdx;
            int oldIdx=((AggravationMovePieceAction) action).oldIdx;
            String type = ((AggravationMovePieceAction) action).type;
            int endOfTheLine = playerNum*14 -2;//farthest any player should get around the board
            if (playerNum==0){
                endOfTheLine = 54;
            }
            if (type.equalsIgnoreCase("Start")) {
                newIdx=playerNum*14;//safety net-if a starting move is passed with an index that isn't the start space
                if(boardCopy[newIdx]!= -1){ //the new index is occupied
                    if (boardCopy[newIdx]==playerNum) {//the player occupies their own start spot
                        return false;//not a valid move, they have to move out of the way
                    }
                    else {//aggravating move code - occupied by another player
                        int otherPlayerNum = boardCopy[newIdx];//whatever value is in the space
                        int otherStart[] = officialGameState.getStartArray(otherPlayerNum);
                        for (int i=0;i<4;i++){
                            //find first empty space in otherPlayerNum start array
                            if (otherStart[i]==-1){
                                //put their piece "back" in their start array
                                otherStart[i]=otherPlayerNum;
                                officialGameState.setStartArray(otherPlayerNum,otherStart);
                                Log.i("otherPlayerNum",""+otherPlayerNum);
                                break;
                            }
                        }
                    }
                }
                //out with the old, in with the new
                startCopy[oldIdx]=-1;
                officialGameState.setStartArray(playerNum,startCopy);
                boardCopy[newIdx]=playerNum;
                officialGameState.setGameBoard(boardCopy);
            }
            else if (type.equalsIgnoreCase("Home")){
                boolean fromOuterSpace=false;//"outer space" being the board array
                if(newIdx>3){//should only be needed for CPU players, since there aren't any buttons
                    //the move could take you out of bounds of the home array
                    return false;
                }
                if(oldIdx>3){//the move is coming from the outside
                    fromOuterSpace = true;
                    for(int i = oldIdx+1;i<=endOfTheLine;i++){//check for a leapfrog in the spaces leading up to the end
                        if (boardCopy[i]==playerNum){
                            return false;
                        }
                    }
                }
                for(int i = oldIdx+1;i<=newIdx;i++){//check for a leapfrog in the home array
                    if (homeCopy[i]==playerNum){
                        return false;
                    }
                }
                //out with the old, in with the new
                if(fromOuterSpace){
                    boardCopy[oldIdx]=-1;
                }
                else{
                    homeCopy[oldIdx]=-1;
                }
                homeCopy[newIdx]=playerNum;
                officialGameState.setGameBoard(boardCopy);
                officialGameState.setHomeArray(playerNum,homeCopy);
            }

            else if (type.equalsIgnoreCase("Board")) {
                //return false if you would be "leapfrogging" one of your own
                //Should never happen with CPU
                if (oldIdx <56) {
                    for (int i = oldIdx + 1; i <= newIdx; i++) {
                        if (boardCopy[i] == playerNum) {
                            return false;
                        }
                    }
                }
                if (oldIdx+actualRoll>endOfTheLine || newIdx>endOfTheLine){//if you're moving past the end, you should have made a "home" move
                    return false;
                }
                /*If the desired spot is not empty, it is another player*/
                if (boardCopy[newIdx]!=-1){
                    //aggravating move code
                    int otherPlayerNum = boardCopy[newIdx];//whatever value is in the space
                    int otherStart[] = officialGameState.getStartArray(otherPlayerNum);
                    for (int i=0;i<4;i++){
                        //find first empty space in otherPlayerNum start array
                        if (otherStart[i]==-1){
                            //put their piece "back" in their start array
                            otherStart[i]=otherPlayerNum;
                            officialGameState.setStartArray(otherPlayerNum,otherStart);
                            Log.i("otherPlayerNum",""+otherPlayerNum);
                            break;
                        }
                    }

                }
                //out with the old, in with the new
                boardCopy[oldIdx]=-1;
                boardCopy[newIdx]=playerNum;
                officialGameState.setGameBoard(boardCopy);
            }
            else if (type.equalsIgnoreCase("Shortcut")){
                boolean exactRoll = false;
                //if (/*new idx is the center piece*/){
                    //code to check if its an exact roll
                    //exactRoll =true;
                //}
                //if(/*newIdx is a regular shortcut*/){
                    //check if exactRoll
                    //exactRoll=true;
                //}
                if(!exactRoll){
                    return false;
                }
            }
            else if (type.equalsIgnoreCase("Skip"))
            {

            }

            if(actualRoll == 6) //if the player rolled a 6 CHANGE THIS
            {
                System.out.println("Roll was a 6.");
                officialGameState.setRoll(true);
                sendAllUpdatedState();
                return true;
            }
        }
        Log.i("changed turn to ",Integer.toString(playerNum +1));
        officialGameState.setTurn(playerNum + 1); //CHANGE THIS
        officialGameState.setRoll(true);
        sendAllUpdatedState();
        return true;
    }//makeMove

    /**
     * send the updated state to a given player
     */
    @Override
    protected void sendUpdatedStateTo(GamePlayer p) {
        Log.i("player run in sendSta", Integer.toString(officialGameState.getTurn()));
        copyGameState = new AggravationState(officialGameState);
        p.sendInfo(copyGameState);
    }//sendUpdatedState

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
