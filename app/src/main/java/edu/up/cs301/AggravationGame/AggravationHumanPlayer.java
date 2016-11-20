package edu.up.cs301.AggravationGame;

import edu.up.cs301.game.GameHumanPlayer;
import edu.up.cs301.game.GameMainActivity;
import edu.up.cs301.game.R;
import edu.up.cs301.game.infoMsg.GameInfo;

import android.graphics.Color;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.view.View.OnClickListener;

/**
 * A GUI for a human to play Aggravation.
 *
 *
 * @author Emily Peterson, Andrew Ripple & Owen Price
 * @version November 2016
 */
public class AggravationHumanPlayer extends GameHumanPlayer implements OnClickListener {

   /* instance variables */

    // These variables will reference widgets that will be modified during play

    private ImageButton dieImageButton = null;
    private TextView yourTurn;
    private TextView rollView;
    private AggravationState gameStateInfo;  // holds copy of the game state

    private ImageButton[] gameBoard = new ImageButton[57];
    private ImageButton[][] playerStart = new ImageButton[4][4];
    private ImageButton[][] playerHome = new ImageButton[4][4];
    private int[] gameBoardIDS = {R.id.GameBoard0,R.id.GameBoard1,R.id.GameBoard2,R.id.GameBoard3,R.id.GameBoard4,R.id.GameBoard5,
            R.id.GameBoard6,R.id.GameBoard7,R.id.GameBoard8,R.id.GameBoard9,R.id.GameBoard10,R.id.GameBoard11,R.id.GameBoard12,R.id.GameBoard13,
            R.id.GameBoard14,R.id.GameBoard15,R.id.GameBoard16,R.id.GameBoard17,R.id.GameBoard18,R.id.GameBoard19,
            R.id.GameBoard20,R.id.GameBoard21,R.id.GameBoard22,R.id.GameBoard23,R.id.GameBoard24,R.id.GameBoard25,R.id.GameBoard26,
            R.id.GameBoard27,R.id.GameBoard28,R.id.GameBoard29,R.id.GameBoard30,R.id.GameBoard31,R.id.GameBoard32,R.id.GameBoard33,
            R.id.GameBoard34,R.id.GameBoard35,R.id.GameBoard36,R.id.GameBoard37,R.id.GameBoard38,R.id.GameBoard39,R.id.GameBoard40,
            R.id.GameBoard41,R.id.GameBoard42,R.id.GameBoard43,R.id.GameBoard44,R.id.GameBoard45,R.id.GameBoard46,R.id.GameBoard47,
            R.id.GameBoard48,R.id.GameBoard49,R.id.GameBoard50,R.id.GameBoard51,R.id.GameBoard52,R.id.GameBoard53,R.id.GameBoard54,R.id.GameBoard55,
            R.id.GameBoard56};
    private int[] playerStartIDS = {R.id.Start00,R.id.Start01,R.id.Start02,R.id.Start03,R.id.Start10,R.id.Start11,R.id.Start12,
            R.id.Start13,R.id.Start20,R.id.Start21,R.id.Start22,R.id.Start23,R.id.Start30,R.id.Start31,R.id.Start32,R.id.Start33};
    private int[] playerHomeIDS = {R.id.Home00,R.id.Home01,R.id.Home02,R.id.Home03,R.id.Home10,R.id.Home11,R.id.Home12,
            R.id.Home13,R.id.Home20,R.id.Home21,R.id.Home22,R.id.Home23,R.id.Home30,R.id.Home31,R.id.Home32,R.id.Home33};

    //didn't put in playerIdx bc playerNum is defined in Game Human Player
    // .... so i think we should follow the pig lead on this one.

    // the android activity that we are running
    private GameMainActivity myActivity;

    /**
     * constructor does nothing extra
     */
    public AggravationHumanPlayer(String name) {
        super(name);
        gameStateInfo = new AggravationState();

    }

    /**
     * Returns the GUI's top view object
     *
     * @return
     *        the top object in the GUI's view heirarchy
     */
    public View getTopView() {
        return myActivity.findViewById(R.id.top_gui_layout);
    }

    /**
     * callback method when we get a message (e.g., from the game)
     *
     * @param info
     *        the message
     */


    @Override
    public void receiveInfo(GameInfo info) {

        if(info instanceof AggravationState)
        {
            gameStateInfo = (AggravationState)info;
            Log.i("rollVal Recieve Info", Integer.toString(gameStateInfo.getDieValue()));
            int[] temp = gameStateInfo.getGameBoard();
            for(int i = 0; i < 57; i++) //setting game board to the pictures
            {

                if(temp[i] == -1)
                {
                    if(i == 5 || i == 19 || i == 33 || i == 47 || i == 56)
                    {
                        this.gameBoard[i].setBackgroundResource(R.mipmap.shortcut);
                        continue;
                    }
                    this.gameBoard[i].setBackgroundResource(R.mipmap.gamesquare);
                }

                else if(temp[i] == 0)
                {
                    this.gameBoard[i].setBackgroundResource(R.mipmap.playerzeropiece);
                }
                else if(temp[i] == 1)
                {
                    this.gameBoard[i].setBackgroundResource(R.mipmap.playeronepiece);
                }
                else if(temp[i] == 2)
                {
                    this.gameBoard[i].setBackgroundResource(R.mipmap.playertwopiece);
                }
                else if(temp[i] == 3)
                {
                    this.gameBoard[i].setBackgroundResource(R.mipmap.playerthreepiece);
                }
            }
            int tempStart[][] = gameStateInfo.getStartArray(); //temporary array that holds the start array integers of the game state
            for(int i = 0;i < 4;i++) { //this runs through and looks at the integers in the game state array and changes the image buttons to reflect them
                for (int j = 0; j < 4; j++) {
                    if (tempStart[i][j] == -1) {
                        this.playerStart[i][j].setBackgroundResource(R.mipmap.gamesquare);
                    } else if (tempStart[i][j] == 0) {
                        this.playerStart[i][j].setBackgroundResource(R.mipmap.playerzeropiece);
                    } else if (tempStart[i][j] == 1) {
                        this.playerStart[i][j].setBackgroundResource(R.mipmap.playeronepiece);
                    } else if (tempStart[i][j] == 2) {
                        this.playerStart[i][j].setBackgroundResource(R.mipmap.playertwopiece);
                    } else if (tempStart[i][j] == 3) {
                        this.playerStart[i][j].setBackgroundResource(R.mipmap.playerthreepiece);
                    }

                }
            }
                int tempHome[][] = gameStateInfo.getHomeArray();//temporary array that holds the home array integers of the game state
                for(int i = 0;i < 4;i++)//this runs through and looks at the integers in the game state's home array and changes the image buttons to reflect
            {
                    for(int j = 0;j<4;j++)
                    {
                        if(tempHome[i][j] == -1)
                        {
                            this.playerHome[i][j].setBackgroundResource(R.mipmap.homesquare);
                        }
                        else if(tempHome[i][j] == 0)
                        {
                            this.playerHome[i][j].setBackgroundResource(R.mipmap.playerzeropiece);
                        }
                        else if(tempHome[i][j] == 1)
                        {
                            this.playerHome[i][j].setBackgroundResource(R.mipmap.playeronepiece);
                        }
                        else if(tempHome[i][j] == 2)
                        {
                            this.playerHome[i][j].setBackgroundResource(R.mipmap.playertwopiece);
                        }
                        else if(tempHome[i][j] == 3)
                        {
                            this.playerHome[i][j].setBackgroundResource(R.mipmap.playerthreepiece);
                        }

                    }
            }

            int whoseTurn = gameStateInfo.getTurn();
            if(whoseTurn == playerNum)
                {
                 if(gameStateInfo.getRoll())
                 {
                     for (int i = 0; i < 57; i++) {
                         this.gameBoard[i].setEnabled(false);
                     }
                     for (int i = 0; i < 4; i++)//this runs through and looks at the integers in the game state's home array and changes the image buttons to reflect
                     {
                         for (int j = 0; j < 4; j++) {
                             this.playerStart[i][j].setEnabled(false);
                         }
                     }
                     for (int i = 0; i < 4; i++)//this runs through and looks at the integers in the game state's home array and changes the image buttons to reflect
                     {
                         for (int j = 0; j < 4; j++) {
                             this.playerHome[i][j].setEnabled(false);
                         }
                     }
                     this.dieImageButton.setEnabled(true);

                 }
                    else
                 {

                     this.dieImageButton.setEnabled(false);
                 }
             }

            if(whoseTurn != playerNum)
            {
                yourTurn.setText("PLAYER " + Integer.toString(whoseTurn) + "!");
            }
            if(whoseTurn != playerNum)
            {
                rollView.setText("Not Your Turn To Roll.");
            }
            if(gameStateInfo.getRoll() == true && whoseTurn == playerNum)
            {
                rollView.setText("Roll!");
            }
            if(whoseTurn == playerNum && gameStateInfo.getRoll() == false)
            {
                rollView.setText("You Just Rolled! Move a Piece!");
            }


        }
       // else
       // {
           // flash(Color.RED,100);
        //}
        if(gameStateInfo.getDieValue() == 1)
        {
            dieImageButton.setImageResource(R.drawable.face1);
        }
        if(gameStateInfo.getDieValue() == 2)
        {
            dieImageButton.setImageResource(R.drawable.face2);
        }
        if(gameStateInfo.getDieValue() == 3)
        {
            dieImageButton.setImageResource(R.drawable.face3);
        }
        if(gameStateInfo.getDieValue() == 4)
        {
            dieImageButton.setImageResource(R.drawable.face4);
        }
        if(gameStateInfo.getDieValue() == 5)
        {
            dieImageButton.setImageResource(R.drawable.face5);
        }
        if(gameStateInfo.getDieValue() == 6)
        {
            dieImageButton.setImageResource(R.drawable.face6);
        }

        if (checkPieces)
        {
            Log.i("does this work", Integer.toString(gameStateInfo.getDieValue()));
            possibleMoveChecker();
            checkPieces =false;
        }
    }//receiveInfo


    public int getPlayerNum()
    {
        return playerNum;
    }
/**
 * possibleMoveChecker -- holds code to call moves but has the correct die value. Called after a player rolls the die
 * and checks if there are possible moves. Code would be in onClick but the die value needs to be updated first
 */
public void possibleMoveChecker()
{
    int[] gameBoardCopy = gameStateInfo.getGameBoard();
    int[][] homeCopy = gameStateInfo.getHomeArray();
    int[][] startCopy = gameStateInfo.getStartArray();
    int rollVal = gameStateInfo.getDieValue();
    cpLi = -1;
    boolean canImove = false;
    for (int i = 0; i < 57; i++) {
        if (gameBoardCopy[i] == playerNum) {
                    cpLi++;
                    currentPieceLocations[cpLi] = i;
                    this.gameBoard[i].setEnabled(true); //enables player's buttons in game board
                    Log.i("enabled", Integer.toString(i));
                    if (!canImove) {
                        canImove = Moves("board", i, false);
                    }
                    //callNewMethod which checks the piece & make it's response = some boolean

                }
            }
            for (int i = 0; i < 4; i++) //enables player's buttons in start and home array
            {
                if (startCopy[playerNum][i] == playerNum) {
                    this.playerStart[playerNum][i].setEnabled(true);
                    if (!canImove) {
                        canImove = Moves("start", i, false);
                        Log.i("checked", "moves");
                        }
                    if (!canImove)
                    {
                        Log.i("canIMove is", "false");
                    }

                }
                if (homeCopy[playerNum][i] == playerNum) {
                    this.playerHome[playerNum][i].setEnabled(true);
                    if (!canImove) {
                        canImove = Moves("home", i, false);
                    }
                }
            }
            if (!canImove)
            {
                AggravationMovePieceAction move = new AggravationMovePieceAction(this, "skip", -1, -1); //sends empty action
                game.sendAction(move);
                Log.i("sent", "blank move");
            }
            else
            {
                Log.i("possible moves", "exist");
            }

}


/**
 * Moves checks and or enables possible move locations for the given button
 */
public boolean Moves(String board, int pieceLoc, boolean enable) {
    Log.i("clicked", "click");
    int rollVal = gameStateInfo.getDieValue();
    int myNum = playerNum;
    int[] gameBoardCopy = gameStateInfo.getGameBoard();
    int[][] homeCopy = gameStateInfo.getHomeArray();
    int[][] startCopy = gameStateInfo.getStartArray();
    String boardType = "board";
    boolean possibleMove = false;

    //if it is start value piece, checks or enables the possible move spot 0
    if (board.equals("start")) {
        Log.i("in helper1roll Val", Integer.toString(rollVal));
        if (rollVal == 1 || rollVal == 6) //if a one or a 6 & there are pieces to move from start array, enable space
        {
            Log.i("in helper, roll val", Integer.toString(rollVal));
            if (enable){
                this.gameBoard[playerNum * 14].setEnabled(true);
            }
            possibleMove = true;
            Log.i("in helper", "PM is true");
            Log.i("enabled", Integer.toString(playerNum * 14));
            markedButton = pieceLoc; //button the move will be send from
            Log.i("markedButton", Integer.toString(pieceLoc));
            boardType = "start";

        }

    }

    //if checking a value in the home area, chekcs or enables possible move spot(s)
    if (board.equals("home")) {
        //need to add move
    }

    //if checking a value in the board area, checks or enables possible move spot(s)
    if (board.equals("board"))
    {
        int i = pieceLoc;
        Log.i("button clicked is", Integer.toString(i));
        if (gameBoardCopy[i] == playerNum) //if the player clicked on its own button
        {
            markedButton = i;

            //CASE: roll val on the board
            if (((i + rollVal) > (playerNum * 14)) && ((i + rollVal) < (55 - playerNum * 14))) //if there is a viable button for player button + roll
            {
                if ((i + rollVal) > 55) {
                    int correctedSpace = rollVal + i - 55; //"over the top space"
                    if (gameBoardCopy[correctedSpace] != playerNum)//"over the top"
                    {
                        if (checkPieceOrder(currentPieceLocations, playerNum, i, correctedSpace)) {
                            if (enable) {
                                this.gameBoard[correctedSpace].setEnabled(true);
                                Log.i("enabledCS1", Integer.toString(correctedSpace));
                                    }
                                possibleMove = true;
                                }
                            }
                        }
                    else if ((i + rollVal) > 55 || (gameBoardCopy[i + rollVal]) != playerNum) {
                            if ((i + rollVal) > 55) {
                                int correctedSpace = rollVal + i - 55; //"over the top space"
                                if (gameBoardCopy[correctedSpace] != playerNum) {
                                    if (checkPieceOrder(currentPieceLocations, playerNum, i, (correctedSpace))) {
                                        if (enable) {
                                            this.gameBoard[correctedSpace].setEnabled(true); //enables that button
                                            Log.i("enabledCS2", Integer.toString(correctedSpace));
                                        }
                                        possibleMove = true;
                                    }
                                }
                            } else if (checkPieceOrder(currentPieceLocations, playerNum, i, (i + rollVal))) {
                                if (enable) {
                                    this.gameBoard[i + rollVal].setEnabled(true); //enables that button
                                    Log.i("enabledcso", Integer.toString(i + rollVal));
                                }
                                possibleMove = true;
                            }
                        }
                    }


                    //CASE: Potential home array move
                    { //checks the Home Arrays
                        int spacesToMove = 55 - 14 * playerNum - i;
                        if (spacesToMove < 5) {
                            for (int k = 0; i < 5; i++) { //enables possible buttons in home array
                                if (homeCopy[playerNum][k + spacesToMove] != playerNum) {
                                    if (enable) {

                                        playerHome[playerNum][k + spacesToMove].setEnabled(true);
                                        Log.i("enabledstm", Integer.toString(k + spacesToMove));
                                    }
                                    possibleMove = true;
                                }

                            }
                        }
                    }

                    if ((i + rollVal) == (5 + 1) || (i + rollVal) == (19 + 1) || (i + rollVal) == (33 + 1) || (i + rollVal) == (47 + 1)) //if the player can directly land on middle shortcut
                    {
                        if (gameBoardCopy[56] != playerNum) {
                            if (enable) {
                                this.gameBoard[56].setEnabled(true); //enable middle
                                Log.i("enabledrv", Integer.toString(56));
                            }
                            possibleMove = true;
                        }
                    }

                    if (i == 5 || i == 19 || i == 33 || i == 47) //if the player is on a corner shortcut
                    {
                        int moveSpace;
                        if (rollVal == 1 && i + 14 * rollVal < playerNum * 14 + 56) //1 shortcut
                        {
                            moveSpace = i + 14 * rollVal;
                            if (moveSpace > 56) {
                                moveSpace = moveSpace - 56;
                            }

                            if (gameBoardCopy[moveSpace] != playerNum) {
                                if (enable) {
                                    this.gameBoard[moveSpace].setEnabled(true);
                                    Log.i("enabledmavespace", Integer.toString(moveSpace));
                                }
                                possibleMove = true;
                            }
                        }
                        if (rollVal == 2) {
                            if (i + 14 * rollVal < playerNum * 14 + 56) //take 2 shortcuts
                            {
                                moveSpace = i + 14 * rollVal;
                                if (moveSpace > 56) {
                                    moveSpace = moveSpace - 56;
                                }

                                if (gameBoardCopy[moveSpace] != playerNum) {
                                    if (enable) {
                                        this.gameBoard[moveSpace].setEnabled(true);
                                        Log.i("enabled", Integer.toString(moveSpace));
                                    }
                                    possibleMove = true;
                                }
                            }
                            if (i + 14 * 1 + 1 < playerNum * 14 + 56) //take 1 shortcut + one step
                            {
                                moveSpace = i + 14 * 1 + 1;
                                if (moveSpace > 56) {
                                    moveSpace = moveSpace - 56;
                                }

                                if (gameBoardCopy[moveSpace] != playerNum) {
                                    if (enable) {
                                        this.gameBoard[moveSpace].setEnabled(true);
                                        Log.i("enabled", Integer.toString(moveSpace));
                                    }
                                    possibleMove = true;
                                }
                            }
                        }
                        if (rollVal == 3) {
                            if (i + 14 * rollVal < playerNum * 14 + 56) //take 3 shortcuts
                            {
                                moveSpace = i + 14 * rollVal;
                                if (moveSpace > 56) {
                                    moveSpace = moveSpace - 56;
                                }

                                if (gameBoardCopy[moveSpace] != playerNum) {
                                    if (enable) {
                                        this.gameBoard[moveSpace].setEnabled(true);
                                        Log.i("enabled", Integer.toString(moveSpace));
                                    }
                                    possibleMove = true;
                                }
                            }
                            if (i + 14 * 2 + 1 < playerNum * 14 + 56) //take 2 shortcuts and 1 step
                            {
                                moveSpace = i + 14 * 2 + 1;
                                if (moveSpace > 56) {
                                    moveSpace = moveSpace - 56;
                                }

                                if (gameBoardCopy[moveSpace] != playerNum) {
                                    if (enable) {
                                        this.gameBoard[moveSpace].setEnabled(true);
                                        Log.i("enabled", Integer.toString(moveSpace));
                                    }
                                    possibleMove = true;
                                }
                            }

                            if (i + 14 * 1 + 2 < playerNum * 14 + 56) //take 1 shortcuts and 2 steps
                            {
                                moveSpace = i + 14 * 1 + 2;
                                if (moveSpace > 56) {
                                    moveSpace = moveSpace - 56;
                                }
                                if (gameBoardCopy[moveSpace] != playerNum) {
                                    if (enable) {
                                        this.gameBoard[moveSpace].setEnabled(true);
                                        Log.i("enabled", Integer.toString(moveSpace));
                                    }
                                    possibleMove = true;
                                }
                            }


                        }
                        if (rollVal > 3) {
                            if (i + 14 * 3 + (rollVal - 3) < playerNum * 14 + 56) ////3 shortcuts + x step
                            {
                                moveSpace = i + 14 * 3 + (rollVal - 3);
                                if (moveSpace > 56) {
                                    moveSpace = moveSpace - 56;
                                }

                                if (gameBoardCopy[moveSpace] != playerNum) {
                                    if (enable) {
                                        this.gameBoard[moveSpace].setEnabled(true);
                                        Log.i("enabled", Integer.toString(moveSpace));
                                    }
                                    possibleMove = true;
                                }
                            }

                            if (i + 14 * 2 + (rollVal - 2) < playerNum * 14 + 56) //take 2 shortcuts and 2 steps
                            {
                                moveSpace = i + 14 * 2 + (rollVal - 2);
                                if (moveSpace > 56) {
                                    moveSpace = moveSpace - 56;
                                }

                                if (gameBoardCopy[moveSpace] != playerNum) {
                                    if (enable) {
                                        this.gameBoard[moveSpace].setEnabled(true);
                                        Log.i("enabled", Integer.toString(moveSpace));
                                    }
                                    possibleMove = true;
                                }
                            }

                            if (i + 14 * 1 + (rollVal - 3) < playerNum * 14 + 56) //take 1 shortcuts and 3 steps
                            {
                                moveSpace = i + 14 * 1 + (rollVal - 3);
                                if (moveSpace > 56) {
                                    moveSpace = moveSpace - 56;
                                }

                                if (gameBoardCopy[moveSpace] != playerNum) {
                                    if (enable) {
                                        this.gameBoard[moveSpace].setEnabled(true);
                                        Log.i("enabled", Integer.toString(moveSpace));
                                    }
                                    possibleMove = true;
                                }
                            }

                        }
                    }
                    if (i == 56 && rollVal == 1) //if the player is in the middle space
                    {
                        if (gameBoardCopy[5] != playerNum) {
                            if (enable) {
                                this.gameBoard[5].setEnabled(true);
                                Log.i("enabled", Integer.toString(5));
                            }
                            possibleMove = true;
                        }
                        if (gameBoardCopy[19] != playerNum) {
                            if (enable) {
                                this.gameBoard[19].setEnabled(true);
                                Log.i("enabled", Integer.toString(19));
                            }
                            possibleMove = true;
                        }
                        if (gameBoardCopy[33] != playerNum) {
                            if (enable) {
                                this.gameBoard[33].setEnabled(true);
                                Log.i("enabled", Integer.toString(33));
                            }
                            possibleMove = true;
                        }
                        if (gameBoardCopy[47] != playerNum) {
                            if (enable) {
                                this.gameBoard[47].setEnabled(true);
                                Log.i("enabled", Integer.toString(47));
                            }
                            possibleMove = true;
                        }

                    }


                }

    }
    return possibleMove;
}

    /**
     * this method checks whether a possible move would move one player's piece ahead of one of his/her
     * pieces. If it would, returns false (illegal move). Otherwise returns true.
     *
     * Helper Method!
     *
     * @param pieceLocations locations of the player's pieces
     * @param playerNum player number
     * @param startMove space on board the piece currently is
     * @param endMove space of the proposed move
     * @return true is move is legal. False otherwise
     */
    public boolean checkPieceOrder(int[] pieceLocations, int playerNum, int startMove, int endMove)
    {
        //Resets everything on a scale of 0-56 to check any player Num
        for (int i = 0; i<4;i++)
        {
            pieceLocations[i] = pieceLocations[i] + playerNum*14;
        }
        int startMove1 = startMove + playerNum*14;
        int endMove1 = endMove + playerNum*14;
        for (int j = 0; j<4; j++)
        {
            if (pieceLocations[j]!= startMove1 && pieceLocations[j] > startMove1 && pieceLocations[j] <endMove1)
            { return false;}
        }
        return true;
    }

    /**
     * this method gets called when the user clicks the die or a button space. It
     * creates a new AggravationRollAction or AggravationMovePieceAction and sends it to the game,
     * or updates the user's display if is is just showing possible moves.
     *
     * @param button
     *        the button that was clicked
     */
    boolean checkPieces = false;
    int markedButton = -1; //holds the most recently pressed player piece button
    int[] currentPieceLocations = new int[4]; //holds locations of player pieces
    int cpLi; //iterator for current Piece Location
    public void onClick(View button) {
        Log.i("clicked", "click");
        int rollVal = gameStateInfo.getDieValue();
        int myNum = playerNum;
        int[] gameBoardCopy = gameStateInfo.getGameBoard();
        int[][] homeCopy = gameStateInfo.getHomeArray();
        int[][] startCopy = gameStateInfo.getStartArray();
        String boardType = "board";

        if (button == dieImageButton) { //if the die has been rolled, enable player's buttons
            AggravationRollAction roll = new AggravationRollAction(this);
            game.sendAction(roll);
            checkPieces = true;
            return;

        }


        else //(NORMAL BUTTONS)
        {
           Log.i("clicked", "normal button");

            Log.i("roll val is ", Integer.toString(rollVal));
            Log.i("PlayerNum", Integer.toString(playerNum));
            boolean enabled = false;
            int clickedIdx = -99;
            String boardTypeCheck = "";

            //Searches board for clicked button, and disables all non player(starting precaution)
            for (int i = 0; i < 57; i++) {
                if (button == this.gameBoard[i]) //finds the button index
                {
                    clickedIdx = i;
                    boardTypeCheck = "board";

                }
                else if (gameBoardCopy[i] != playerNum) //if it's not a player button, disable
                {
                    gameBoard[i].setEnabled(false);
                }
            }

            //searches start array for clicked button and disables all non piece buttons
            for (int m = 0; m < 4; m++) {
                if (button == playerStart[playerNum][m] )
                {
                        if (startCopy[playerNum][m] == playerNum) {
                            clickedIdx = m;
                            boardTypeCheck = "start";
                        }
                }
                else if (startCopy[playerNum][m] != playerNum){ //disable if it's not the player's piece

                    playerStart[playerNum][m].setEnabled(false);
                }
            }

            //checks through home array to find button clicked and disables all non player buttons
            for (int m = 0; m < 4; m++) {
                if (button == playerHome[playerNum][m]) {
                    if (homeCopy[playerNum][m] == playerNum) {
                        clickedIdx = m;
                        boardTypeCheck = "home";
                    }
                }
                else if (homeCopy[playerNum][m] != playerNum) //diable if it's not the player's piece
                {
                    playerHome[playerNum][m].setEnabled(false);
                }
            }

            if (clickedIdx >-1) //if the button clicked has been found
            {
                Moves(boardTypeCheck, clickedIdx, true); //enable possible moves
                Log.i("enableing","stuff");
            }

                //When the player clicks on an availiable space to make a move
                for (int k = 0; k < 56; k++) {
                    if (button == this.gameBoard[k] && gameBoardCopy[k] != playerNum) {
                        Log.i("k ", Integer.toString(k));
                        Log.i("markedButton", Integer.toString(markedButton));
                        AggravationMovePieceAction move = new AggravationMovePieceAction(this, boardType, markedButton, k);
                        Log.i("sending move board", Integer.toString(markedButton));
                        game.sendAction(move);
                    }
                    //conditions
                }


            }
            Log.i("end of", "on click");
        }




// onClick


    /**
 * callback method--our game has been chosen/rechosen to be the GUI,
 * called from the GUI thread
 *
 * @param activity
 *        the activity under which we are running
 */
    public void setAsGui(GameMainActivity activity) {

        // remember the activity
        myActivity = activity;

        // Load the layout resource for our GUI
        activity.setContentView(R.layout.pig_layout);

        //Listen for button presses
        this.yourTurn = (TextView)activity.findViewById(R.id.yourTurn);
        this.rollView = (TextView)activity.findViewById(R.id.rollView);
        this.dieImageButton = (ImageButton)activity.findViewById(R.id.RollButton);
        Log.i("HERE","HERE");
        dieImageButton.setOnClickListener(this);
        Log.i("die image button", "created");
        //Initialize the widget reference member variables

        //ALL THOSE BUTTONS GO HERE
        for (int i = 0; i<57; i++) {
            this.gameBoard[i] = (ImageButton) activity.findViewById(gameBoardIDS[i]);
            this.gameBoard[i].setOnClickListener(this);
        }
        int count = 0;
        for(int i = 0; i < 4;i++)
        {
            for(int j = 0;j<4;j++)
            {
                this.playerStart[i][j] = (ImageButton) activity.findViewById(playerStartIDS[count]);
                this.playerStart[i][j].setOnClickListener(this);
                Log.i("count is", Integer.toString(count));
                count++;
            }
        }

        int count2 = 0;
        for(int i = 0; i < 4;i++)
        {
            for(int j = 0;j<4;j++)
            {
                this.playerHome[i][j] = (ImageButton) activity.findViewById(playerHomeIDS[count2]);
                this.playerHome[i][j].setOnClickListener(this);
                Log.i("count2 is ", Integer.toString(count2));
                count2++;
            }
        }

    }//setAsGui

}// class AggravationHumanPlayer
