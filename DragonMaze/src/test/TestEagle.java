package test;

import java.util.Collections;

import org.junit.Test;

import Game_Logic.Game_Loop;
import Game_Logic.myRandom;
import static org.junit.Assert.assertEquals;

// TODO: Auto-generated Javadoc
/**
 * The Class TestEagle.
 */
public class TestEagle {

	/**
	 * Test can send eagle.
	 */
	@Test public void testCanSendEagle() {
		//hero not dead and can send eagle
		Game_Loop.getInstance().InitTestGame(0); //0 dragon
		Game_Loop.getInstance().game_conditions("r");
		assertEquals(true, Game_Loop.getInstance().HERO.isEagle_sent());
	}

	/**
	 * Test cant send eagle.
	 */
	@Test public void testCantSendEagle() {
		//hero is dead due to dragon and can't send eagle
		Game_Loop.getInstance().InitTestGame(1); //1 dragon
		Game_Loop.getInstance().enemies[0].setX(1);
		Game_Loop.getInstance().enemies[0].setY(1);
		Game_Loop.getInstance().HERO.setY(1);
		Game_Loop.getInstance().HERO.setX(2);
		Game_Loop.getInstance().game_conditions("a"); //hero dies
		assertEquals(true, Game_Loop.getInstance().HERO.isDead());
		Game_Loop.getInstance().game_conditions("r");
		assertEquals(false, Game_Loop.getInstance().HERO.isEagle_sent());
	}

	/**
	 * Test eagle moves and gets sword.
	 */
	@Test public void testEagleMovesAndGetsSword() {
		Game_Loop.getInstance().InitTestGame(0); //0 dragon
		Game_Loop.getInstance().HERO.setY(5);
		Game_Loop.getInstance().HERO.setX(5);
		Game_Loop.getInstance().HERO.setEagle_x(5);
		Game_Loop.getInstance().HERO.setEagle_y(5);
		Game_Loop.getInstance().SWORD.setX(4);
		Game_Loop.getInstance().SWORD.setY(6);
		Game_Loop.getInstance().game_conditions("r"); //send eagle, goes left
		//eagle should return to (5,5)
		assertEquals(true, Game_Loop.getInstance().HERO.isEagle_sent());
		assertEquals(4, Game_Loop.getInstance().HERO.getEagle_x());
		assertEquals(5, Game_Loop.getInstance().HERO.getEagle_y());
		Game_Loop.getInstance().game_conditions("d"); //hero goes right, eagle goes down
		assertEquals(4, Game_Loop.getInstance().HERO.getEagle_x());
		assertEquals(6, Game_Loop.getInstance().HERO.getEagle_y());
		assertEquals(true, Game_Loop.getInstance().SWORD.isUsed()); //sword is picked up
		assertEquals('y', Game_Loop.getInstance().HERO.getEagle());

	}

	/**
	 * Test eagle moves but dies at sword.
	 */
	@Test public void testEagleMovesButDiesAtSword() {
		myRandom.getInstance().queue.clear();
		Integer[] moves = {2,2,3,5,5,5,5,5}; //use of an integer with no consequences in dragon movement to simulate dragon waiting for eagle
		Collections.addAll(myRandom.getInstance().queue, moves);
		Game_Loop.getInstance().InitTestGame(1); //0 dragon
		Game_Loop.getInstance().enemies[0].setX(4);
		Game_Loop.getInstance().enemies[0].setY(5);
		
		Game_Loop.getInstance().SWORD.setX(4);
		Game_Loop.getInstance().SWORD.setY(6);
		Game_Loop.getInstance().game_conditions("r"); //send eagle
		
		//eagle moves horizontally (in this case to the right -> increase in X)
		Game_Loop.getInstance().game_conditions("d"); 
		Game_Loop.getInstance().game_conditions("d"); 
		Game_Loop.getInstance().game_conditions("d"); 
		assertEquals(4, Game_Loop.getInstance().HERO.getEagle_x());
		
		//eagle moves vertically (in this case to the down -> increase in Y)
		Game_Loop.getInstance().game_conditions("d"); 
		Game_Loop.getInstance().game_conditions("d"); 
		Game_Loop.getInstance().game_conditions("d"); 
		Game_Loop.getInstance().game_conditions("d"); 
		assertEquals(6, Game_Loop.getInstance().HERO.getEagle_y());
		
		//Game consequences dragon next to sword when eagle picks it up
		assertEquals(false, Game_Loop.getInstance().SWORD.isUsed()); //sword not picked up
		assertEquals(false, Game_Loop.getInstance().HERO.isEagle_alive());
	}

	/**
	 * Test eagle moves and gives sword to hero.
	 */
	@Test public void testEagleMovesAndGivesSwordToHero() {
		Game_Loop.getInstance().InitTestGame(0); //0 dragon
		Game_Loop.getInstance().HERO.setY(5);
		Game_Loop.getInstance().HERO.setX(5);
		Game_Loop.getInstance().HERO.setEagle_x(5);
		Game_Loop.getInstance().HERO.setEagle_y(5);
		Game_Loop.getInstance().SWORD.setX(4);
		Game_Loop.getInstance().SWORD.setY(6);
		Game_Loop.getInstance().game_conditions("r"); //send eagle, goes left
		//eagle should return to (5,5)
		assertEquals(true, Game_Loop.getInstance().HERO.isEagle_sent());
		assertEquals(4, Game_Loop.getInstance().HERO.getEagle_x());
		assertEquals(5, Game_Loop.getInstance().HERO.getEagle_y());
		Game_Loop.getInstance().game_conditions("d"); //hero goes right, eagle goes down
		assertEquals(4, Game_Loop.getInstance().HERO.getEagle_x());
		assertEquals(6, Game_Loop.getInstance().HERO.getEagle_y());
		assertEquals(true, Game_Loop.getInstance().SWORD.isUsed()); //sword is picked up
		assertEquals('y', Game_Loop.getInstance().HERO.getEagle());
		Game_Loop.getInstance().game_conditions("d"); //hero goes right (useless movement), eagle goes right
		assertEquals(5, Game_Loop.getInstance().HERO.getEagle_x());
		assertEquals(6, Game_Loop.getInstance().HERO.getEagle_y());
		Game_Loop.getInstance().game_conditions("a"); //hero goes left, eagle goes up
		assertEquals(5, Game_Loop.getInstance().HERO.getEagle_x());
		assertEquals(5, Game_Loop.getInstance().HERO.getEagle_y());
		Game_Loop.getInstance().game_conditions("w"); //hero goes up(useless movement, eagle drops sword to hero
		assertEquals(5, Game_Loop.getInstance().HERO.getEagle_x());
		assertEquals(5, Game_Loop.getInstance().HERO.getEagle_y());
		assertEquals(false, Game_Loop.getInstance().HERO.isEagle_sent());
		assertEquals(true, Game_Loop.getInstance().HERO.isEagle_alive());
		assertEquals('A', Game_Loop.getInstance().HERO.getSymbol());
		}

	/**
	 * Test eagle gets sword cant deliver due to dead hero.
	 */
	@Test public void testEagleGetsSwordCantDeliverDueToDeadHero() {
		Game_Loop.getInstance().InitTestGame(1); //1 dragon
		Game_Loop.getInstance().enemies[0].setX(1);
		Game_Loop.getInstance().enemies[0].setY(5);
		myRandom.getInstance().queue.clear();
		Integer[] moves = {1,1,1,1};
		Collections.addAll(myRandom.getInstance().queue, moves);
		Game_Loop.getInstance().game_conditions("r"); //send eagle
		Game_Loop.getInstance().game_conditions("s"); //move to meet the dragon and die 
		assertEquals(true, Game_Loop.getInstance().HERO.isDead()); //hero died
		assertEquals(2, Game_Loop.getInstance().HERO.getEagle_y());
		Game_Loop.getInstance().game_conditions("s");
		assertEquals(false, Game_Loop.getInstance().HERO.isEagle_alive()); //eagle dies because hero dies
	}

	/**
	 * Test eagle dies at last hero position.
	 */
	@Test public void testEagleDiesAtLastHeroPosition() {
		Game_Loop.getInstance().InitTestGame(1); //1 dragon
		//Hero Starts at (1,1), Sword at (1,8) and Dragon at (1,5)
		Game_Loop.getInstance().enemies[0].setX(1);
		Game_Loop.getInstance().enemies[0].setY(5);
		myRandom.getInstance().queue.clear();
		Integer[] moves = {1,1,1,1,5,5,5,5,5,5,5,5,5,5};
		Collections.addAll(myRandom.getInstance().queue, moves);
		Game_Loop.getInstance().game_conditions("r"); //send eagle
		Game_Loop.getInstance().game_conditions("d"); //send hero to the right (so no iteraction with dragon)
		Game_Loop.getInstance().game_conditions("d");
		Game_Loop.getInstance().game_conditions("d");
		Game_Loop.getInstance().game_conditions("d");
		Game_Loop.getInstance().game_conditions("d");
		assertEquals('v', Game_Loop.getInstance().HERO.getEagle()); //symbol for eagle without sword
		Game_Loop.getInstance().game_conditions("d"); //eagle should have sword here
		assertEquals('y', Game_Loop.getInstance().HERO.getEagle()); //symbol for eagle with sword
		Game_Loop.getInstance().game_conditions("d");
		Game_Loop.getInstance().game_conditions("d"); 
		Game_Loop.getInstance().game_conditions("d"); 
		Game_Loop.getInstance().game_conditions("d"); 
		Game_Loop.getInstance().game_conditions("d"); 
		Game_Loop.getInstance().game_conditions("d"); 
		Game_Loop.getInstance().game_conditions("d"); //at position where hero "launched" eagle
		assertEquals(1, Game_Loop.getInstance().HERO.getEagle_x());
		assertEquals(1, Game_Loop.getInstance().HERO.getEagle_y());
		Game_Loop.getInstance().game_conditions("d"); //eagle lands (requires an extra turn) / dragon should kill here
		assertEquals(false, Game_Loop.getInstance().HERO.isEagle_alive());


		
		
	}
}