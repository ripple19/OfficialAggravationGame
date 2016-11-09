package edu.up.cs301.AggravationGame;


import edu.up.cs301.game.GamePlayer;
import edu.up.cs301.game.actionMsg.GameAction;

/**
 * class to handle move piece actions
 *
 * @author Emily Peterson, Andrew Ripple, & Owen Price
 *
 * @version November 2016
 */
public class AggravationMovePieceAction extends GameAction {
    int playerNum;
    int oldIdx;
    int newIdx;
    String type;//Start, Board, or Home

    public AggravationMovePieceAction(GamePlayer player, String type, int playerNum, int oldIdx, int newIdx)
    {
        super(player);
        this.playerNum=playerNum;
        this.oldIdx=oldIdx;
        this.newIdx=newIdx;
        this.type=type;
    }



}


//class AggravationMovePieceAction