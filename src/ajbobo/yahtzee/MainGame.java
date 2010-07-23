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
	
	private GridView grid;
	
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
		
		SetupGame();
	}
	
	/** Initialize the dice and score */
	private void SetupGame()
	{
		_rnd = new Random();
		
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