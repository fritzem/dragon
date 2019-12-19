package inMain;

public class Character {
	
	/*
	 * I know this is disgusting, but the original game just kinda used
	 * random values for its stats, I'm trying to be faithful.
	 */
	public int[] levels = {0, 7, 23, 47, 110, 220, 450, 800, 1300,
			2000, 2900, 4000, 5500, 7500, 10000, 13000, 16000, 19000,
			22000, 26000, 30000, 34000, 38000, 42000, 46000, 50000, 54000,
			58000, 62000, 65535};
	public int[] strengths = {4, 5, 7, 7, 12, 16, 18, 22, 30, 35,
			40, 48, 52, 60, 68, 72, 72, 85, 87, 92, 95, 97, 99, 103, 113, 117,
			125, 130, 135, 140};
	public int[] agilities = {4, 4, 6, 8, 10, 10, 17, 20, 22, 31,
			35, 40, 48, 55, 64, 70, 78, 84, 86, 88, 90, 90, 94, 98, 100, 105,
			107, 115, 120, 130};
	public int[] healths = {15, 22, 24, 31, 35, 38, 40, 46, 50, 54,
			62, 63, 70, 78, 86, 92, 100, 115, 130, 138, 149, 158, 165, 170, 174,
			180, 189, 195, 200, 210};
	public int[] magics = {0, 0, 5, 16, 20, 24, 26, 29, 36, 40, 50,
			58, 64, 70, 72, 95, 100, 108, 115, 128, 135, 146, 153, 161, 161, 168,
			175, 180, 190, 200};
	public int[] spells = {};
	
	private String name;
	private int level;
	private int exp;
	private int bonSel;
	private int bonus;
	
	private int strength;
	private int agility;
	private int maxHP;
	private int maxMP;
	
	
	private int hp;
	private int mp;
	private int gold;
	
	public Character(String name) 
	{
		this.name = name;
		nameAnalysis();
		
		level = 0;
		exp = 0;
		levelUp();
		
		hp = healths[0];
		mp = magics[0];
		gold = 0;
	}

	public void nameAnalysis() 
	{
		bonus = 3;
		bonSel = 12;
		
		if (bonSel == 3 || bonSel == 5 || bonSel == 9)
			applyBonus(magics);
		if (bonSel == 3 || bonSel == 6 || bonSel == 10)
			applyBonus(healths);
		if (bonSel == 5 || bonSel == 6 || bonSel == 12)
			applyBonus(agilities);
		if (bonSel == 9 || bonSel == 10 || bonSel == 12)
			applyBonus(strengths);
	}
	public void applyBonus(int[] arr)
	{
		for (int i = 0; i < arr.length; i++)
		{
			arr[i] = arr[i] - arr[i] / 10 + bonus;
		}
	}
	
	
	public boolean awardEXP(int exp)
	{
		this.exp += exp;
		if (exp >= levels[level])
		{
			levelUp();
			return true;
		}
		return false;
			
	}
	public void levelUp()
	{
		if (exp < levels[level])
			exp = levels[level];
		strength = strengths[0];
		agility = agilities[0];
		maxHP = healths[0];
		maxMP = magics[0];
	}
	
	public String getName()
	{
		return name;
	}
	public int getHP()
	{
		return hp;
	}
	public int getMP()
	{
		return mp;
	}
	public int getGold()
	{
		return gold;
	}
	public int getEXP()
	{
		return exp;
	}
	public int getStr()
	{
		return strength;
	}
	public int getAgil()
	{
		return agility;
	}
	public int getMaxHP()
	{
		return maxHP;
	}
	public int getMaxMP()
	{
		return maxMP;
	}
}
