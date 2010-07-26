package ajbobo.yahtzee;

public class Player
{
	private static int NO_SCORE = -1;
	
	private int _scores[];
	private int _bonuscnt;
	
	public Player()
	{
		_scores = new int[13];
		clearScores();
	}
	
	public void clearScores()
	{
		for (int x = 0; x < 13; x++)
		{
			_scores[x] = NO_SCORE;
		}
		
		_bonuscnt = 0;
	}
	
	public int getScore(int category)
	{
		return _scores[category];
	}
	
	public void setScore(int category, int amt)
	{
		if (_scores[category] == NO_SCORE)
			_scores[category] = amt;
	}
	
	public void addBonus()
	{
		_bonuscnt++;
	}
}
