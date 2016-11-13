package edu.up.cs301.AggravationGame;

import edu.up.cs301.game.GameHumanPlayer;
import edu.up.cs301.game.GameMainActivity;
import edu.up.cs301.game.R;
import edu.up.cs301.game.infoMsg.GameInfo;

import android.graphics.Color;
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

    private AggravationState gameStateInfo = new AggravationState(); // holds copy of the game state

    private ImageButton[] gameBoard = null;
    private ImageButton[][] playerStart = null;
    private ImageButton[][] playerHome = null;
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
        gameStateInfo = (AggravationState)info;
        if(info instanceof AggravationState)
        {
            int[] temp = gameStateInfo.getGameBoard();
            for(int i = 0; i < 57; i++) //setting game board to the pictures
            {

                if(temp[i] == -1)
                {
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
                            this.playerStart[i][j].setBackgroundResource(R.mipmap.gamesquare);
                        }
                        else if(tempHome[i][j] == 0)
                        {
                            this.playerStart[i][j].setBackgroundResource(R.mipmap.playerzeropiece);
                        }
                        else if(tempHome[i][j] == 1)
                        {
                            this.playerStart[i][j].setBackgroundResource(R.mipmap.playeronepiece);
                        }
                        else if(tempHome[i][j] == 2)
                        {
                            this.playerStart[i][j].setBackgroundResource(R.mipmap.playertwopiece);
                        }
                        else if(tempHome[i][j] == 3)
                        {
                            this.playerStart[i][j].setBackgroundResource(R.mipmap.playerthreepiece);
                        }

                    }
            }
            int x;
            int whoseTurn = gameStateInfo.getTurn();
            if(whoseTurn == playerNum)
                {
                 if(gameStateInfo.getRoll() == true)
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
    }//receiveInfo


    public int getPlayerNum()
    {
        return playerNum;
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
    public void onClick(View button) {
        int rollVal = gameStateInfo.getDieValue();
        int myNum = playerNum;
        int[] gameBoardCopy = gameStateInfo.getGameBoard();
        int[][] homeCopy = gameStateInfo.getHomeArray();
        int[][] startCopy = gameStateInfo.getStartArray();

        int[] currentPieceLocations = new int[4]; //holds locations of player pieces
        int cpLi = 0; //iterator for cpL

        if(button == dieImageButton) { //if the die has been rolled, enable player's buttons
            AggravationRollAction roll = new AggravationRollAction(this);
            game.sendAction(roll);

            for (int i = 0; i < 57; i++) {
                if (gameBoardCopy[i] == playerNum) {
                    currentPieceLocations[cpLi] = i;
                    cpLi++;
                    this.gameBoard[i].setEnabled(true); //enables player's buttons in game board

                }
            }
            for (int i = 0; i <4; i++) //enables player's buttons in start and home array
            {
                if (startCopy[playerNum][i] == playerNum)
                {
                    this.playerStart[playerNum][i].setEnabled(true);
                }
                if (homeCopy[playerNum][i] == playerNum)
                {
                    this.playerHome[playerNum][i].setEnabled(true);
                }
            }
        }

        else //(NORMAL BUTTONS)
        {
            //set conditionals to know what type of move it is (???)
            //AggravationMovePieceAction hold = new AggravationMovePieceAction(this);
            //game.sendAction(hold);

            //RIGHT NOW this is set up for player 0-- need to change to allow for player 1,2,3, probably by changing all hardwired values, (i.e. 56 to multiples relative to player num)

            for (int i = 0; i<56; i++)
            {
                if (button == this.gameBoard[i]) //finds the button index
                {
                    if (gameBoardCopy[i] == playerNum) //if the player clicked on its own button
                    {
                        if (rollVal == 1 || rollVal ==6) //if a one or a 6 & there are pieces to move from start array, enable space
                        {
                            boolean notEmpty = false;
                            for (int j = 0; j<4; j++) //checks to make sure the start array is not empty
                            {
                                if (startCopy[playerNum][j] == playerNum)
                                {
                                    notEmpty = true;
                                }
                            }
                            if (notEmpty) //if the start array is not empty, enables "start" space for player
                            {
                                this.gameBoard[playerNum * 14].setEnabled(true);
                            }

                        }

                        //CASE: roll val on the board
                        if (((i+rollVal) > (playerNum*14)) && ((i +rollVal) <(55 - playerNum*14))) //if there is a viable button for player button + roll
                            if ((i+rollVal) > 55)
                            {
                                int correctedSpace = rollVal + i - 55; //"over the top space"
                                if (gameBoardCopy[correctedSpace] != playerNum)//"over the top"
                                {
                                    if (checkPieceOrder(currentPieceLocations,playerNum, i, correctedSpace)) {
                                        this.gameBoard[correctedSpace].setEnabled(true);
                                    }}
                            }
                    }
                    else if ((gameBoardCopy[i + rollVal]) != playerNum) {
                        if (checkPieceOrder(currentPieceLocations,playerNum, i, (i+rollVal))) {
                            this.gameBoard[i + rollVal].setEnabled(true); //enables that button
                        }
                    }
                }


                //CASE: Potential home array move
                { //checks the Home Arrays
                    int spacesToMove = 55 - 14*playerNum - i;
                    if (spacesToMove < 5) {
                        for (int k = 0; i < 5; i++) { //enables possible buttons in home array
                            if (homeCopy[playerNum][k+ spacesToMove] != playerNum) {
                                playerHome[playerNum][k + spacesToMove].setEnabled(true);
                            }

                        }
                    }
                }

                if ((i+rollVal) == (5+1) || (i + rollVal)== (19+1) || (i+rollVal) == (33+1) || (i+rollVal)==(47+1)) //if the player can directly land on middle shortcut
                {
                    this.gameBoard[56].setEnabled(true); //enable middle
                }


                //tighten up this code with multiples and constants
                if (i == 5 || i == 19 || i ==33 || i==47) //if the player is on a corner shortcut
                {
                    if (rollVal ==1)
                    {
                        if ((i+ rollVal) < 56) {
                            this.gameBoard[i + 14 * (rollVal)].setEnabled(true);
                        }
                    }
                    else if (rollVal ==2)
                    {
                        if (i + (14*(rollVal-1) +1) <56)
                        {
                            this.gameBoard[i + (14*(rollVal-1) +1)].setEnabled(true);
                        }
                        if (i + (14*rollVal) <56)
                        {
                            this.gameBoard[i + 14*rollVal].setEnabled(true);
                        }
                    }
                    else if (rollVal ==3)
                    {
                        if (i + (14*(rollVal-1) +1) <56)
                        {
                            this.gameBoard[i + (14*(rollVal-1) +1)].setEnabled(true);
                        }

                        if (i + (14*(rollVal-2) +2) <56)
                        {
                            this.gameBoard[i + (14*(rollVal-2) +2)].setEnabled(true);
                        }

                        if (i + (14*rollVal) <56)
                        {
                            this.gameBoard[i + 14*rollVal].setEnabled(true);
                        }
                    }
                    else if (rollVal ==4)
                    {}
                    else  if (rollVal ==5)
                    {}
                    else if (rollVal == 6)
                    {}
                }

                if (i == 56) //if the player is in the middle space
                {

                }
                //if the piece is sitting on a shortcut space
                //if the piece is sitting in the middle
            }
        }

        ///SHORTCUTS: 5, 19, 33, 47 MIDDLE = 56
        //else //if the player clicked on another space (space to move the button) to
        {
            for (int k = 0; k<56; k++)
            {
                if (button == this.gameBoard[k])
                {
                    if (k == 56) //if the shortcut space is pressed
                    {}
                }
                //conditions
            }

            //FINAL CONDITIONS BELOW
                    /*AggravationMovePieceAction hold = new AggravationMovePieceAction(this);
                    game.sendAction(hold);*/
        }
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
                count2++;
            }
        }

        //Listen for button presses
        this.dieImageButton = (ImageButton)activity.findViewById(R.id.imageButton);
        dieImageButton.setOnClickListener(this);
        //ALL THE LISTENERS GO HERE

    }//setAsGui

}// class AggravationHumanPlayer
