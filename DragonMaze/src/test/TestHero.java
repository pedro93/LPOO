package test;
import Game_Logic.Game_Loop;
import maze.Labirynth;
import org.junit.Test;
import static org.junit.Assert.assertEquals;

// TODO: Auto-generated Javadoc
/**
 * The Class TestHero.
 */
public class TestHero {

	/**
	 * Test hero mov available.
	 */
	@Test public void testHeroMovAvailable() {
		Game_Loop.getInstance().InitTestGame(0); //1 dragon
		Game_Loop.getInstance().HERO.setX(4);
		Game_Loop.getInstance().HERO.setY(5);
		//Down Movement
		Game_Loop.getInstance().game_conditions("s");
		assertEquals(6, Game_Loop.getInstance().HERO.getY());
		//Up Movement
		Game_Loop.getInstance().game_conditions("w");
		assertEquals(5, Game_Loop.getInstance().HERO.getY());
		//Left Movement
		Game_Loop.getInstance().game_conditions("a");
		assertEquals(3, Game_Loop.getInstance().HERO.getX());
		//Right Movement
		Game_Loop.getInstance().game_conditions("d");
		assertEquals(4, Game_Loop.getInstance().HERO.getX());
	}
	
	/**
	 * Test hero mov not available.
	 */
	@Test public void testHeroMovNotAvailable() {
		Game_Loop.getInstance().InitTestGame(0); //1 dragon
		Game_Loop.getInstance().HERO.setX(1);
		Game_Loop.getInstance().HERO.setY(1);
		//Up Movement
		Game_Loop.getInstance().game_conditions("w");
		assertEquals(1, Game_Loop.getInstance().HERO.getY());
		assertEquals(1, Game_Loop.getInstance().HERO.getX());
		//Left Movement
		Game_Loop.getInstance().game_conditions("a");
		assertEquals(1, Game_Loop.getInstance().HERO.getX());
		assertEquals(1, Game_Loop.getInstance().HERO.getY());
		//Down Movement
		Game_Loop.getInstance().game_conditions("d");
		Game_Loop.getInstance().game_conditions("s");
		assertEquals(1, Game_Loop.getInstance().HERO.getY());
		assertEquals(2, Game_Loop.getInstance().HERO.getX());
		//Right Movement
		Game_Loop.getInstance().game_conditions("a");
		Game_Loop.getInstance().game_conditions("s");
		Game_Loop.getInstance().game_conditions("d");
		assertEquals(1, Game_Loop.getInstance().HERO.getX());
		assertEquals(2, Game_Loop.getInstance().HERO.getY());
	}
	
	/**
	 * Test get sword.
	 */
	@Test public void testGetSword() {
		Game_Loop.getInstance().InitTestGame(0); //1 dragon
		Game_Loop.getInstance().HERO.setX(1);
		Game_Loop.getInstance().HERO.setY(7);
		Game_Loop.getInstance().SWORD.setX(1);
		Game_Loop.getInstance().SWORD.setY(8);
		Game_Loop.getInstance().game_conditions("s");
		assertEquals('A', Game_Loop.getInstance().HERO.getSymbol());
	}
	
	/**
	 * Dragon kills.
	 */
	@Test public void DragonKills() {
		Game_Loop.getInstance().InitTestGame(1); //1 dragon
		Game_Loop.getInstance().HERO.setX(1);
		Game_Loop.getInstance().HERO.setY(2);
		Game_Loop.getInstance().enemies[0].setX(1);
		Game_Loop.getInstance().enemies[0].setY(3);
		Game_Loop.getInstance().game_conditions("s");
		assertEquals(true, Game_Loop.getInstance().HERO.isDead());
	}
	
	/**
	 * Dragon gets killed.
	 */
	@Test public void DragonGetsKilled() {
		Game_Loop.getInstance().InitTestGame(1); //1 dragon
		Game_Loop.getInstance().HERO.setX(1);
		Game_Loop.getInstance().HERO.setY(7);
		Game_Loop.getInstance().HERO.setSymbol('A'); //armed hero
		Game_Loop.getInstance().game_conditions("s");
		Game_Loop.getInstance().enemies[0].setX(1);
		Game_Loop.getInstance().enemies[0].setY(7);
		Game_Loop.getInstance().game_conditions("w");
		assertEquals(true,Game_Loop.getInstance().enemies[0].isDead());
	}
	
	/**
	 * Gets the sword kill dragon exit.
	 *
	 * @return the sword kill dragon exit
	 */
	@Test public void getSwordKillDragonExit() {
		Game_Loop.getInstance().InitTestGame(1); //1 dragon
		Game_Loop.getInstance().enemies[0].setX(1);
		Game_Loop.getInstance().enemies[0].setY(1);
		Game_Loop.getInstance().HERO.setX(1);
		Game_Loop.getInstance().HERO.setY(7);
		Game_Loop.getInstance().SWORD.setX(1);
		Game_Loop.getInstance().SWORD.setY(8);
		Game_Loop.getInstance().game_conditions("s");
		assertEquals(true,Game_Loop.getInstance().SWORD.isUsed());
		assertEquals('A',Game_Loop.getInstance().HERO.getSymbol());
		Game_Loop.getInstance().enemies[0].setX(1);
		Game_Loop.getInstance().enemies[0].setY(7);
		Game_Loop.getInstance().game_conditions("w");
		assertEquals(true,Game_Loop.getInstance().enemies[0].isDead());
		Game_Loop.getInstance().HERO.setX(8);
		Game_Loop.getInstance().HERO.setY(5);
		Game_Loop.getInstance().game_conditions("d");
		assertEquals(true,Game_Loop.getInstance().HERO.getY() == Labirynth.getInstance().EXIT_Y && Game_Loop.getInstance().HERO.getX() == Labirynth.getInstance().EXIT_X);
	}
	
	/**
	 * Exit without sword.
	 */
	@Test public void ExitWithoutSword() {
		Game_Loop.getInstance().InitTestGame(1); //1 dragons doesnt matter for this test

		Game_Loop.getInstance().HERO.setX(8);
		Game_Loop.getInstance().HERO.setY(5);

		//well away to not affect hero
		Game_Loop.getInstance().enemies[0].setX(1); 
		Game_Loop.getInstance().enemies[0].setY(1);
		Game_Loop.getInstance().SWORD.setX(1); 
		Game_Loop.getInstance().SWORD.setY(3);

		Game_Loop.getInstance().game_conditions("d");
		assertEquals(false,Game_Loop.getInstance().HERO.getY() == Labirynth.getInstance().EXIT_Y && Game_Loop.getInstance().HERO.getX() == Labirynth.getInstance().EXIT_X);

	}
}
