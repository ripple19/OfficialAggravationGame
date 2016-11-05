package edu.up.cs301.pig;

import edu.up.cs301.game.infoMsg.GameState;

/**
 * Created by nguyenmi19 on 10/12/2016.
 */
public class PigGameState extends GameState {

    private int playerID;
    private int playerZeroScore;
    private int playerOneScore;
    private int runningTotal;
    private int diceValue;



    public PigGameState()
    {
        super();
        playerID = 0;
        playerZeroScore = 0;
        playerOneScore = 0;
        runningTotal = 0;
        diceValue = 0;
    }

    public PigGameState(PigGameState x)
    {
        super();
        this.setDiceValue(x.getDiceValue());
        this.setPlayerID(x.getPlayerID());
        this.setPlayerOneScore(x.getPlayerOneScore());
        this.setRunningTotal(x.getRunningTotal());
        this.setPlayerZeroScore(x.getPlayerZeroScore());
    }

    public void setPlayerID(int newID)
    {

        playerID = newID;

    }

    public int getPlayerID()
    {

        return playerID;
    }

    public void setPlayerZeroScore(int score)
    {

        playerZeroScore = score;

    }

    public int getPlayerZeroScore()
    {
        return playerZeroScore;
    }

    public void setPlayerOneScore(int score)
    {

        playerOneScore = score;

    }

    public int getPlayerOneScore()
    {
        return playerOneScore;

    }

    public void setRunningTotal(int total)
    {

        runningTotal = total;

    }

    public int getRunningTotal()
    {
        return runningTotal;
    }

    public void setDiceValue(int value)
    {
        diceValue = value;

    }

    public int getDiceValue()
    {
        return diceValue;
    }






}
