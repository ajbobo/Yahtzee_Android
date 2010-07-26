package ajbobo.yahtzee;

import android.app.Activity;
import android.os.Bundle;
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
	private Random _rnd;
	private Die _dice[];
	private Player _player;
	private Rules _rules;
	
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
				}
			});
		}
		
		SetupGame();
	}
	
	/** Initialize the dice and score */
	private void SetupGame()
	{
		_rnd = new Random();
		
		_player = new Player();
		_player.clearScores();
		_rules = new Rules();
		_dice = new Die[5];
		for (int x = 0; x < 5; x++)
		{
			_dice[x] = new Die();
		}
		
		RollDice();
	}
	
	/** Toggle the specified Die's selection status */
	private void HandleClickedDie(int index)
	{
		Die die = _dice[index];
		die.setChecked(!die.isChecked());
	}
	
	/** Reroll all unselected dice */
	private void RollDice()
	{
		for (int x = 0; x < 5; x++)
		{
			Die die = _dice[x];
			if (!die.isChecked())
			{
				die.setValue(_rnd.nextInt(6)); // Dice are numbered 0-5
			}
		}
	}
	
	/** Add the score to the selected box */
	public void ScoreField(Button button)
	{
		// Make sure the field doesn't already have a score
		String curtext = button.getText().toString();
		if (curtext.length() != 0)
		{
			showToast("That field already has a score");
			return;
		}
		
		button.setText("123");
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