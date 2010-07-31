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
		int cnt[] = {0, 0, 0, 0, 0, 0};
		int score = 0;
		
		for (int x = 0; x < 5; x++)
		{
			Die die = dice[x];
			cnt[die.getValue()]++;
			score += die.getValue() + 1; // Values are 0-5, dice scores are 1-6
		}
		
		boolean have3 = false;
		for (int x = 0; x < 6; x++)
		{
			if (cnt[x] >= 3)
				have3 = true;
		}
		
		if (have3)
			player.setScore(CATEGORY_3OFAKIND, score);
		else
			player.setScore(CATEGORY_3OFAKIND, 0);
	}

	private void score4OfAKind(Player player, Die[] dice)
	{
		int cnt[] = {0, 0, 0, 0, 0, 0};
		int score = 0;
		
		for (int x = 0; x < 5; x++)
		{
			Die die = dice[x];
			cnt[die.getValue()]++;
			score += die.getValue() + 1; // Values are 0-5, dice scores are 1-6
		}
		
		boolean have4 = false;
		for (int x = 0; x < 6; x++)
		{
			if (cnt[x] >= 4)
				have4 = true;
		}
		
		if (have4)
			player.setScore(CATEGORY_4OFAKIND, score);
		else
			player.setScore(CATEGORY_4OFAKIND, 0);
	}

	private void scoreFullHouse(Player player, Die[] dice)
	{
		int cnt[] = {0, 0, 0, 0, 0, 0};
		
		for (int x = 0; x < 5; x++)
		{
			Die die = dice[x];
			cnt[die.getValue()]++;
		}
		
		boolean have2 = false, have3 = false;
		for (int x = 0; x < 6; x++)
		{
			if (cnt[x] == 2)
				have2 = true;
			else if (cnt[x] == 3)
				have3 = true;
		}
		
		if (have2 && have3)
			player.setScore(CATEGORY_FULLHOUSE, 25);
		else
			player.setScore(CATEGORY_FULLHOUSE, 0);
	}

	private void scoreSmStraight(Player player, Die[] dice)
	{
		boolean one = false, two = false, three = false, four = false, five = false, six = false;
		
		for (int x = 0; x < 5; x++)
		{
			Die die = dice[x];
			switch(die.getValue())
			{
			case 0: one = true; break;
			case 1: two = true; break;
			case 2: three = true; break;
			case 3: four = true; break;
			case 4: five = true; break;
			case 5: six = true; break;
			}
		}
		
		boolean straight = false;
		if ((one && two && three && four) ||
			 (two && three && four && five) ||
			 (three && four && five && six))
			straight = true;
		
		if (straight)
			player.setScore(CATEGORY_SMSTRAIGHT, 30);
		else
			player.setScore(CATEGORY_SMSTRAIGHT, 0);
	}

	private void scoreLgStraight(Player player, Die[] dice)
	{
		boolean one = false, two = false, three = false, four = false, five = false, six = false;
		
		for (int x = 0; x < 5; x++)
		{
			Die die = dice[x];
			switch(die.getValue())
			{
			case 0: one = true; break;
			case 1: two = true; break;
			case 2: three = true; break;
			case 3: four = true; break;
			case 4: five = true; break;
			case 5: six = true; break;
			}
		}
		
		boolean straight = false;
		if ((one && two && three && four && five) ||
			 (two && three && four && five && six))
			straight = true;
		
		if (straight)
			player.setScore(CATEGORY_LGSTRAIGHT, 40);
		else
			player.setScore(CATEGORY_LGSTRAIGHT, 0);
	}

	private void scoreYahtzee(Player player, Die[] dice)
	{
		boolean have5 = true;
		int value = dice[0].getValue();
		for (int x = 1; x < 5; x++)
			if (dice[x].getValue() != value)
				have5 = false;
		
		if (have5)
			player.setScore(CATEGORY_YAHTZEE, 50);
		else
			player.setScore(CATEGORY_YAHTZEE, 0);
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
