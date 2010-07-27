package ajbobo.yahtzee;

public class Rules
{
	public final int CATEGORY_ONES = 0;
	public final int CATEGORY_TWOS = 1;
	public final int CATEGORY_THREES = 2;
	public final int CATEGORY_FOURS = 3;
	public final int CATEGORY_FIVES = 4;
	public final int CATEGORY_SIXES = 5;
	public final int CATEGORY_3OFAKIND = 6;
	public final int CATEGORY_4OFAKIND = 7;
	public final int CATEGORY_FULLHOUSE = 8;
	public final int CATEGORY_SMSTRAIGHT = 9;
	public final int CATEGORY_LGSTRAIGHT = 10;
	public final int CATEGORY_YAHTZEE = 11;
	public final int CATEGORY_CHANCE = 12;
	
	public void scorePlayer(Player player, Die dice[], int category)
	{
		switch(category)
		{
		case CATEGORY_ONES:
		case CATEGORY_TWOS:
		case CATEGORY_THREES:
		case CATEGORY_FOURS:
		case CATEGORY_FIVES:
		case CATEGORY_SIXES:
			scoreTopField(player, dice, category); break;
		case CATEGORY_3OFAKIND:
			score3OfAKind(player, dice); break;
		case CATEGORY_4OFAKIND:
			score4OfAKind(player, dice); break;
		case CATEGORY_FULLHOUSE:
			scoreFullHouse(player, dice); break;
		case CATEGORY_SMSTRAIGHT:
			scoreSmStraight(player, dice); break;
		case CATEGORY_LGSTRAIGHT:
			scoreLgStraight(player, dice); break;
		case CATEGORY_YAHTZEE:
			scoreYahtzee(player, dice); break;
		case CATEGORY_CHANCE:
			scoreChance(player, dice); break;
		}
	}

	private void scoreTopField(Player player, Die[] dice, int category)
	{
		int total = 0;
		for (int x = 0; x < 5; x++)
		{
			Die die = dice[x];
			if (die.getValue() == category)
				total += die.getValue() + 1; // Values are 0-5, dice scores are 1-6
		}
		
		player.setScore(category, total);
	}

	private void score3OfAKind(Player player, Die[] dice)
	{
		// TODO Auto-generated method stub
		
		player.setScore(CATEGORY_3OFAKIND, 33);
	}

	private void score4OfAKind(Player player, Die[] dice)
	{
		// TODO Auto-generated method stub
		
		player.setScore(CATEGORY_4OFAKIND, 44);
	}

	private void scoreFullHouse(Player player, Die[] dice)
	{
		// TODO Auto-generated method stub
		
		player.setScore(CATEGORY_FULLHOUSE,34);
	}

	private void scoreSmStraight(Player player, Die[] dice)
	{
		// TODO Auto-generated method stub
		
		player.setScore(CATEGORY_SMSTRAIGHT, 123);
	}

	private void scoreLgStraight(Player player, Die[] dice)
	{
		// TODO Auto-generated method stub
		
		player.setScore(CATEGORY_LGSTRAIGHT, 1234);
	}

	private void scoreYahtzee(Player player, Die[] dice)
	{
		// TODO Auto-generated method stub
		
		player.setScore(CATEGORY_YAHTZEE, 555);
	}

	private void scoreChance(Player player, Die[] dice)
	{
		int total = 0;
		for (int x = 0; x < 5; x++)
		{
			Die die = dice[x];
			total += die.getValue() + 1; // Values are 0-5, dice scores are 1-6
		}
		
		player.setScore(CATEGORY_CHANCE, total);
	}
}
