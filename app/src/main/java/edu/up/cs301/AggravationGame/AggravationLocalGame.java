package edu.up.cs301.AggravationGame;

import android.util.Log;

import edu.up.cs301.game.GamePlayer;
import edu.up.cs301.game.LocalGame;
import edu.up.cs301.game.actionMsg.GameAction;

import java.util.Random;

/**
 * class AggravationLocalGame controls the play of the game
 *
 * @author Owen Price, Emily Peterson, Andrew Ripple
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
     * if it ever works add a bunch of shit here to explain
     * @return true if the action was taken or false if the action was invalid/illegal.
     */
    @Override
    protected synchronized boolean makeMove(GameAction action) {
            //^^^^^^^^^^^^//ask Vegdahl about this in particular - Owen

        int playerNum=getPlayerIdx(action.getPlayer());
        int boardCopy[] = officialGameState.getGameBoard();
        int startCopy[]= officialGameState.getStartArray(playerNum);
        int homeCopy[] = officialGameState.getHomeArray(playerNum);


        if(action instanceof AggravationRollAction) {
            Random dieValue = new Random();//dieValue outside of the conditionals
            int value = dieValue.nextInt(6) + 1; //"As a mathematician," I took out the +1-1 b/c why would it be there? - Owen
            actualRoll = value;
            Log.i("dieVal is ", Integer.toString(value));
            officialGameState.setDieValue(value);
            Log.i("set value LocalGame", Integer.toString(officialGameState.getDieValue()));
            officialGameState.setRoll(false);
            System.out.println("Roll = " + value);
            Log.i("set value", Integer.toString(value));
            //took these two lines out because I think they were causing the thread problems - Owen
            //sendAllUpdatedState();
            //return true;
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
                newIdx=playerNum*14;//safety net-if a starting move is somehow passed with an index that isn't the start space
                if(boardCopy[newIdx]!= -1){//the new index is occupied
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
            /* Call your move a "Home" move if you are moving TO the home array
            * from the board or within the home array itself - Owen
            */
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

            /* Call your move a "Board" move if you are moving TO somewhere on the board, unless it's a start
            * the shortcut spaces count as "Board" spaces, so unless you're trying to land on the actual center
            * and are actually TAKING a shortcut / cutting something short, call it a "Board" move
            - Owen */
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

                /* the 4 shortcut spaces are 5, 19, 33, and 47
                * if oldIdx = any of these, then enable shortcut moves?
                * a valid shortcut move is based exclusively on the space
                * you can loop all the way around if you want, which means it doesn't have to be
                * based on the player at all valid newIdx's:
                * i from 0 to the die value - there are as many valid new indexes as the die value
                * newIdx == oldIdx + (14*i) + value - i
                * 14*i is b/c you move a full quarter of the 56 piece board jumping shortcut spot to shortcut spot
                * value -i is b/c each jump from cut to cut is 1 less you can move after
                * newIdx == oldIdx+value + 13*i
                * - Owen
                */

            /* Call your move a "shortcut" move if you are moving IN "shortcut space"
             * "Am I taking a shortcut?" is the key question here, so any moves
             * TO the center, or FROM a regular shortcut space
             * are moves you should label "Shortcut."
             * Any moves to just a regular shortcut space should be labeled Board
             * if this sounds unintuitive and backwards I get it but I have my reasons - Owen
             * */
            else if (type.equalsIgnoreCase("Shortcut")) {

                //canCutThere -"Can you take a shortcut there?" for validity of move,
                // last shortcut is an idx for leapfrogging purposes
                boolean canCutThere = false;
                int lastShortcut=0;

                /*the middle space stuff has more code than I'd like,
                * the 3 line if-statement really hurts my aesthetic.
                * basically, if you're trying to move to the middle spot, it makes sure that
                * you land on a shortcut immediately before hitting the middle spot, so you see that it's
                * landing on the center by an exact count, then makes sure neither that spot nor the center spot
                * is the player's piece (leapfrog check), then it aggravates if someone's there - Owen */
                if (newIdx==57) {
                    //oneOff is the shortcut spot right before the middle reached by exact count
                    int oneOff = oldIdx+actualRoll-1;

                    if ((oneOff==5||oneOff==19||oneOff==33||oneOff==47)
                            && (boardCopy[oneOff]!= playerNum)
                            && (boardCopy[57]!=playerNum)){

                        if(boardCopy[57]!=-1){//aggravation copypaste
                            int otherPlayerNum = boardCopy[newIdx];
                            int otherStart[] = officialGameState.getStartArray(otherPlayerNum);
                            for (int i=0;i<4;i++){
                                if (otherStart[i]==-1){
                                    otherStart[i]=otherPlayerNum;
                                    officialGameState.setStartArray(otherPlayerNum,otherStart);
                                    break;
                                }
                            }
                        }
                    }
                }

                /*moving from any of the 4 possible shortcut spaces,
                * check the given newIdx with every possible valid point you can move to
                * you're allowed to loop around as many times as you want on the shortcut spaces, apparently,
                * and if this works like it does in my head that should be totally fine - Owen*/
                if (oldIdx==5||oldIdx==19||oldIdx==33||oldIdx==47){
                    for(int i = 0; i<=actualRoll;i++){

                        //valid is a valid new index
                        //yes I did mean 13 not 14 check my work the i distributes - Owen
                        int valid = oldIdx+actualRoll+(13*i);

                        //keep it on the board
                        //while loop since we want it to stay relative to the 56 square board
                        while (valid>56) {
                            valid=valid-56;
                            //I feel like my algebra is questionable, but I don't see any reason this wouldn't work
                            //valid = oldIdx+actualRoll+(13*i)
                            //oldIdx+14*i = the index of the furthest shortcut reached before
                            //leaving shortcut space for the main board
                            //so lastShortcut = oldIdx + 14*i = valid - actualRoll + i
                            //since valid is decremented I put in terms of valid - Owen
                            lastShortcut = valid-actualRoll+i;
                        }

                        //special leapfrog check, breaks the loop before validating
                        //a newIdx if you have another piece on that shortcut space
                        //don't need to keep it relative to the 56, since it would break before
                        //i got greater than 3, if it was going to break
                        if(i!=0 && boardCopy[oldIdx+14*i]==playerNum)
                            break;


                        //if the newIdx matches with a valid spot, you can take the
                        // short cut you asked for and break the loop early
                        if(valid == newIdx) {
                            canCutThere = true;
                            break;
                        }
                    }
                }
                else return false;//don't make a shortcut move if you aren't moving from a shortcut space

                if(!canCutThere) return false;//if you don't match with any of the possible valid moves
                else {//if you can, check for leapfrog and aggravations from the shortcut you left from
                    //ideally, since every move has such similar leapfrog/aggravation code, I would be able to
                    //move this bit outside of the "type" cases. Not important for functionality yet though - Owen

                    for (int i=lastShortcut;i<=newIdx;i++){//leapfrog check
                        if (boardCopy[i] == playerNum)
                            return false;
                    }

                    if (boardCopy[newIdx]!=-1){//aggravation copypasta
                        int otherPlayerNum = boardCopy[newIdx];
                        int otherStart[] = officialGameState.getStartArray(otherPlayerNum);
                        for (int i=0;i<4;i++){
                            if (otherStart[i]==-1){
                                otherStart[i]=otherPlayerNum;
                                officialGameState.setStartArray(otherPlayerNum,otherStart);
                                break;
                            }
                        }
                    }
                    //out with the old, in with the new
                    boardCopy[oldIdx]=-1;
                    boardCopy[newIdx]=playerNum;
                    officialGameState.setGameBoard(boardCopy);
                }
            }

            /*Label a move "skip" if you want to make a move,
            * but are afraid of commitment */
            else if (type.equalsIgnoreCase("Skip"))
            {
                //99% sure we can leave this blank since we don't want it to do anything - Owen
            }
            //after any actual move is made, someone has to roll
            officialGameState.setRoll(true);
        }

        //if the player *didn't* roll a 6 - I moved this outside
        // to the same scope as the other "return true" statement and cleaned it up a bit
        // there was also an extra 2 lines at the end of "RollAction" - I think
        // part of our problem was that we called "sendAllUpdatedState" at least twice
        // for any given move. I've also made sure that every other return statement is a return false,
        // so makeMove can't return true prematurely. Between this and the synchronization thing I think I probably fixed
        // the roll problem. Bear that in mind when the rest of the code I added inevitably fucks everything up - Owen
        if(actualRoll != 6) {
            officialGameState.setTurn(playerNum + 1);
            Log.i("changed turn to ",Integer.toString(officialGameState.getTurn()));
        }
        else System.out.println("Roll was a 6.");

        //last 2 lines aren't anywhere else anymore
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
        int[] counts = new int[4];
        int[][] homeCopy = officialGameState.getHomeArray();

        for(int i=0;i<4;i++){
            for(int j=0;j<4;j++){
                if (homeCopy[i][j]==i){
                    counts[i]++;
                }
            }
            if (counts[i]==4){
                return ("Player "+(i+1)+" Wins!");
            }
            else {
                counts[i] = 0;
            }
        }
        return null;

        /* I left the other method here in case you wanted it back, I wasn't trying to be
        passive aggressive or anything it was just an easy fix - Owen


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
        return null;*/
    }
}// class AggravationLocalGame
