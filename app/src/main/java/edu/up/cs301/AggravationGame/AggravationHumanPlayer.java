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
            int whoseTurn = gameStateInfo.getTurn();
            if(whoseTurn == playerNum)
            {
                // In pig set the score text view


                //I think this is where we update the screen with button arrays
                // can we combined with the below for the initial update

                //and then probably other stuff :)
            }
            else if(whoseTurn != playerNum)
            {

            }

        }
        else
        {
            flash(Color.RED,100);
        }



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

    /**
     * this method gets called when the user clicks the die or a button space. It
     * creates a new AggravationRollAction or AggravationMovePieceAction and sends it to the game,
     * or updates the user's display if is is just showing possible moves.
     *
     * @param button
     *        the button that was clicked
     */
    public void onClick(View button) {
        if(button == dieImageButton)
        {
            AggravationRollAction roll = new AggravationRollAction(this);
            game.sendAction(roll);

        }
        else //(NORMAL BUTTONS)
        {
            //set conditionals to know what type of move it is (???)
            AggravationMovePieceAction hold = new AggravationMovePieceAction(this);
            game.sendAction(hold);
        }
    }// onClick

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

        this.dieImageButton = (ImageButton)activity.findViewById(R.id.imageButton);
        //ALL THOSE BUTTONS GO HERE
        for (int i = 0; i<57; i++) {
            this.gameBoard[i] = (ImageButton) activity.findViewById(R.id.imageButton2);
        }

        //Listen for button presses
        dieImageButton.setOnClickListener(this);
        //ALL THE LISTENERS GO HERE

    }//setAsGui

}// class AggravationHumanPlayer
