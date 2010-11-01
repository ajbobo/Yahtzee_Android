package ajbobo.yahtzee;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;
import java.util.Random;

public class MainGame extends Activity 
{
// Constants that are internal to this class
	private static final int MENU_NEWGAME = Menu.FIRST;
	
	private Random _rnd;
	private Die _dice[];
	private Player _player;
	private Rules _rules;
	private int _rollsleft;
	private int _turnsleft;
	
	private GridView grid;
	Integer scorebuttons[] = {
			R.id.btnAces, R.id.btnTwos, R.id.btnThrees, R.id.btnFours, R.id.btnFives, R.id.btnSixes,
			R.id.btn3ofakind, R.id.btn4ofakind, R.id.btnSmStraight, R.id.btnLgStraight, R.id.btnFullHouse, R.id.btnYahtzee, R.id.btnChance
	};
	
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		
		grid = (GridView) findViewById(R.id.grdDice);
		grid.setAdapter(new DiceAdapter(this));
		grid.setOnItemClickListener(new OnItemClickListener()
		{
			public void onItemClick(AdapterView<?> parent, View v, int position, long id)
			{
				HandleClickedDie(position);
				grid.invalidateViews();
			}
		});
		
		Button button = (Button) findViewById(R.id.btnRollDice);
		button.setOnClickListener(new OnClickListener()
		{
			public void onClick(View v)
			{
				RollDice();
				grid.invalidateViews();
			}
		});
		
		for (int x = 0; x < 13; x++)
		{
			Button scorebutton = (Button) findViewById(scorebuttons[x]);
			scorebutton.setOnClickListener(new OnClickListener()
			{
				public void onClick(View v)
				{
					ScoreField((Button)v);
					grid.invalidateViews();
				}
			});
		}

		// Globals that only need to be set up once
		_rnd = new Random();
		_player = new Player();
		_rules = new Rules();
		_dice = new Die[5];
		for (int x = 0; x < 5; x++)
		{
			_dice[x] = new Die();
		}
		
		// Set up things that can change between games
		SetupGame();
	}
	
	/** Called when a handled configuration changes */
	@Override
	public void onConfigurationChanged(Configuration newConfig)
	{
	    super.onConfigurationChanged(newConfig);
	    // Do nothing - I'm handling configuration changes by not changing anything. This way the Activity isn't restarted
	}
	
	/** Called when the Back button is pressed */
	@Override
	public void onBackPressed()
	{
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setMessage("Are you sure you want to exit?")
			.setCancelable(false)
			.setPositiveButton("Yes", new DialogInterface.OnClickListener()
			{
				public void onClick(DialogInterface dialog, int id)
				{
					MainGame.this.finish();
				}
			})
			.setNegativeButton("No", new DialogInterface.OnClickListener()
			{
				public void onClick(DialogInterface dialog, int id)
				{
					dialog.cancel();
				}
			});
		AlertDialog alert = builder.create();
		alert.show();
	}
	
	/** Create menu items */
	@Override
	public boolean onCreateOptionsMenu(Menu menu)
	{
		menu.add(0, MENU_NEWGAME, 0, "New Game");//.setIcon(android.R.drawable.ic_menu_info_details);

		return true;
	}

	/** Handle menu items */
	@Override
	public boolean onOptionsItemSelected(MenuItem item)
	{
		switch (item.getItemId())
		{
		case MENU_NEWGAME:	SetupGame(); grid.invalidateViews(); return true;
		}
		return false;
	}
		
	
	/** Initialize the dice and score */
	private void SetupGame()
	{
		_player.clearScores();
		for (int x = 0; x < 13; x++)
		{
			Button scorebutton = (Button) findViewById(scorebuttons[x]);
			scorebutton.setText("");
		}

		_turnsleft = 13;
		SetupTurn();
	}
	
	/** Set up a turn */
	private void SetupTurn()
	{
		_rollsleft = 3;
		for (int x = 0; x < 5; x++)
		{
			_dice[x].setChecked(false);
		}
		RollDice();
	}
	
	/** Reroll all unselected dice */
	private void RollDice()
	{
		_rollsleft--;
		
		for (int x = 0; x < 5; x++)
		{
			Die die = _dice[x];
			if (!die.isChecked())
			{
				die.setValue(_rnd.nextInt(6)); // Dice are numbered 0-5
			}
		}
		
		String buttontext = "Roll Dice (" + _rollsleft + ")";
		Button rolldicebutton = (Button)findViewById(R.id.btnRollDice);
		rolldicebutton.setText(buttontext);
		
		if (_rollsleft == 0)
			rolldicebutton.setEnabled(false);
		else
			rolldicebutton.setEnabled(true);
	}
	
	/** Add the score to the selected box */
	public void ScoreField(Button button)
	{
		// If the game's over, don't do anything
		if (_turnsleft <= 0)
			return;
		
		// Make sure the field doesn't already have a score
		String curtext = button.getText().toString();
		if (curtext.length() != 0)
		{
			showToast("That field already has a score");
			return;
		}
		
		// Calculate and show the field's new score
		int category = 0;
		int buttonid = button.getId();
		switch(buttonid)
		{
		case R.id.btnAces:			category = _rules.CATEGORY_ONES;		break;
		case R.id.btnTwos:			category = _rules.CATEGORY_TWOS;		break;
		case R.id.btnThrees:		category = _rules.CATEGORY_THREES;		break;
		case R.id.btnFours:			category = _rules.CATEGORY_FOURS;		break;
		case R.id.btnFives:			category = _rules.CATEGORY_FIVES;		break;
		case R.id.btnSixes:			category = _rules.CATEGORY_SIXES;		break;
		case R.id.btn3ofakind:		category = _rules.CATEGORY_3OFAKIND;	break;
		case R.id.btn4ofakind:		category = _rules.CATEGORY_4OFAKIND;	break;
		case R.id.btnFullHouse:		category = _rules.CATEGORY_FULLHOUSE;	break;
		case R.id.btnSmStraight:	category = _rules.CATEGORY_SMSTRAIGHT;	break;
		case R.id.btnLgStraight:	category = _rules.CATEGORY_LGSTRAIGHT;	break;
		case R.id.btnYahtzee:		category = _rules.CATEGORY_YAHTZEE;		break;
		case R.id.btnChance:		category = _rules.CATEGORY_CHANCE;		break;
		}
		
		_rules.scorePlayer(_player, _dice, category);
		
		Integer score = _player.getScore(category);
		button.setText(score.toString());
		
		// Set up the next turn or end the game
		_turnsleft--;
		if (_turnsleft == 0) // Game over
		{
			ShowScore();
		}
		else // Set up next turn
		{
			SetupTurn();
		}
	}
	
	private void ShowScore()
	{
		// Put together the score message
		String message;
		message =  "Upper Section:\t" + _player.getTopScore() + "\n";
		message += "Upper Bonus:\t\t" + _player.getTopBonus() + "\n";
		message += "Upper Total:\t\t" + _player.getTopTotal() + "\n";
		message += "\nLower Total:\t\t\t" + _player.getBottomScore() + "\n";
		message += "Yahtzee Bonus:\t" + _player.getYahtzeeBonus() + "\n";
		message += "\nTotal Score:\t\t\t" + _player.getTotalScore() + "\n";
		message += "\nDo you want to play again?";
		
		// Display the score
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setMessage(message)
			.setCancelable(false)
			.setPositiveButton("Yes", new DialogInterface.OnClickListener()
			{
				public void onClick(DialogInterface dialog, int id)
				{
					SetupGame(); grid.invalidateViews();
				}
			})
			.setNegativeButton("No", new DialogInterface.OnClickListener()
			{
				public void onClick(DialogInterface dialog, int id)
				{
					MainGame.this.finish();
				}
			})
			.setTitle("Final Score");
		AlertDialog alert = builder.create();
		alert.show();
	}
	
	/** Toggle the specified Die's selection status */
	private void HandleClickedDie(int index)
	{
		Die die = _dice[index];
		die.setChecked(!die.isChecked());
	}

	/** Returns the specified Die */
	public Die GetDie(int index)
	{
		return _dice[index];
	}
		
	/** Display a short Toast with the specified text */
	public void showToast(String msg)
	{
		Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
	}
}