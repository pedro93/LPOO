package test;

import java.util.Collections;
import org.junit.Test;
import Game_Logic.Game_Loop;
import Game_Logic.myRandom;
import static org.junit.Assert.assertEquals;

// TODO: Auto-generated Javadoc
/**
 * The Class TestDragons.
 */
public class TestDragons {
	/*
	 * Dragons move according to a number between 1-4; 
	 * 1-> Up
	 * 2-> Down
	 * 3-> Left
	 * 4-> Right
	 */

	/*
	 * 		Game_Loop.getInstance().game_conditions("d");
	 * 		used so that hero does not meet dragons without explicit command or "teleport" (use of setX/setY)
	 */

	
	/**
	 * Dragon movement.
	 */
	@Test public void  DragonMovement() {
		myRandom.getInstance().queue.clear();
		Integer[] moves = {2,1,3,4};
		Collections.addAll(myRandom.getInstance().queue, moves);
		Game_Loop.getInstance().InitTestGame(1); //0 dragon
		Game_Loop.getInstance().enemies[0].setX(4);
		Game_Loop.getInstance().enemies[0].setY(5);
		Game_Loop.getInstance().game_conditions("d");
		//Down Movement
		assertEquals(4, Game_Loop.getInstance().enemies[0].getX());
		assertEquals(6, Game_Loop.getInstance().enemies[0].getY());
		Game_Loop.getInstance().game_conditions("d");
		//Up Movement
		assertEquals(4, Game_Loop.getInstance().enemies[0].getX());
		assertEquals(5, Game_Loop.getInstance().enemies[0].getY());
		Game_Loop.getInstance().game_conditions("d");
		//Left Movement
		assertEquals(3, Game_Loop.getInstance().enemies[0].getX());
		assertEquals(5, Game_Loop.getInstance().enemies[0].getY());
		Game_Loop.getInstance().game_conditions("d");
		//Right Movement
		assertEquals(4, Game_Loop.getInstance().enemies[0].getX());
		assertEquals(5, Game_Loop.getInstance().enemies[0].getY());
		myRandom.getInstance().queue.clear();
	}
	
	/**
	 * Multi dragon movement.
	 */
	@Test public void  MultiDragonMovement() {
		myRandom.getInstance().queue.clear();
		Integer[] moves = {2,2,1,1,3,3,4,4};
		Collections.addAll(myRandom.getInstance().queue, moves);
		Game_Loop.getInstance().InitTestGame(2); //0 dragon
		Game_Loop.getInstance().enemies[0].setX(4);
		Game_Loop.getInstance().enemies[0].setY(5);
		Game_Loop.getInstance().enemies[1].setX(6);
		Game_Loop.getInstance().enemies[1].setY(5);
		Game_Loop.getInstance().game_conditions("d");
		//Down Movement
		assertEquals(4, Game_Loop.getInstance().enemies[0].getX());
		assertEquals(6, Game_Loop.getInstance().enemies[0].getY());
		assertEquals(6, Game_Loop.getInstance().enemies[1].getX());
		assertEquals(6, Game_Loop.getInstance().enemies[1].getY());
		Game_Loop.getInstance().game_conditions("d");
		//Up Movement
		assertEquals(4, Game_Loop.getInstance().enemies[0].getX());
		assertEquals(5, Game_Loop.getInstance().enemies[0].getY());
		assertEquals(6, Game_Loop.getInstance().enemies[1].getX());
		assertEquals(5, Game_Loop.getInstance().enemies[1].getY());
		Game_Loop.getInstance().game_conditions("d");
		//Left Movement
		assertEquals(3, Game_Loop.getInstance().enemies[0].getX());
		assertEquals(5, Game_Loop.getInstance().enemies[0].getY());
		assertEquals(5, Game_Loop.getInstance().enemies[1].getX());
		assertEquals(5, Game_Loop.getInstance().enemies[1].getY());
		Game_Loop.getInstance().game_conditions("d");
		//Right Movement
		assertEquals(4, Game_Loop.getInstance().enemies[0].getX());
		assertEquals(5, Game_Loop.getInstance().enemies[0].getY());
		assertEquals(6, Game_Loop.getInstance().enemies[1].getX());
		assertEquals(5, Game_Loop.getInstance().enemies[1].getY());
		myRandom.getInstance().queue.clear();
	}
	
	/**
	 * Dragons colision.
	 */
	@Test public void DragonsColision() {
		myRandom.getInstance().queue.clear();
		Integer[] moves = {3,3,4,3};
		Collections.addAll(myRandom.getInstance().queue, moves);
		Game_Loop.getInstance().InitTestGame(2); //0 dragon
		Game_Loop.getInstance().enemies[0].setX(4);
		Game_Loop.getInstance().enemies[0].setY(5);
		Game_Loop.getInstance().enemies[1].setX(6);
		Game_Loop.getInstance().enemies[1].setY(5);
		Game_Loop.getInstance().game_conditions("d");
		assertEquals(3, Game_Loop.getInstance().enemies[0].getX());
		assertEquals(5, Game_Loop.getInstance().enemies[0].getY());
		assertEquals(5, Game_Loop.getInstance().enemies[1].getX());
		assertEquals(5, Game_Loop.getInstance().enemies[1].getY());
		Game_Loop.getInstance().game_conditions("d");
		//second dragon cant move to cell (dragon 1 already there)
		assertEquals(4, Game_Loop.getInstance().enemies[0].getX());
		assertEquals(5, Game_Loop.getInstance().enemies[0].getY());
		assertEquals(5, Game_Loop.getInstance().enemies[1].getX());
		assertEquals(5, Game_Loop.getInstance().enemies[1].getY());
		myRandom.getInstance().queue.clear();

	}

	/**
	 * Dragons move impossible.
	 */
	@Test public void DragonsMoveImpossible()
	{
		myRandom.getInstance().queue.clear();
		Integer[] moves = {2,2,3,3,4,4,1,1,3,3,1,1,2,2};
		Collections.addAll(myRandom.getInstance().queue, moves);
		Game_Loop.getInstance().InitTestGame(2); //0 dragon
		Game_Loop.getInstance().enemies[0].setX(4);
		Game_Loop.getInstance().enemies[0].setY(5);
		Game_Loop.getInstance().enemies[1].setX(6);
		Game_Loop.getInstance().enemies[1].setY(5);
		Game_Loop.getInstance().game_conditions("d"); //put in a plce where he cant go left or right
		assertEquals(4, Game_Loop.getInstance().enemies[0].getX());
		assertEquals(6, Game_Loop.getInstance().enemies[0].getY());
		assertEquals(6, Game_Loop.getInstance().enemies[1].getX());
		assertEquals(6, Game_Loop.getInstance().enemies[1].getY());
		Game_Loop.getInstance().game_conditions("d");
		assertEquals(4, Game_Loop.getInstance().enemies[0].getX());
		assertEquals(6, Game_Loop.getInstance().enemies[0].getY());
		assertEquals(6, Game_Loop.getInstance().enemies[1].getX());
		assertEquals(6, Game_Loop.getInstance().enemies[1].getY());
		Game_Loop.getInstance().game_conditions("d");
		assertEquals(4, Game_Loop.getInstance().enemies[0].getX());
		assertEquals(6, Game_Loop.getInstance().enemies[0].getY());
		assertEquals(6, Game_Loop.getInstance().enemies[1].getX());
		assertEquals(6, Game_Loop.getInstance().enemies[1].getY());
		Game_Loop.getInstance().game_conditions("d");
		Game_Loop.getInstance().game_conditions("d");//put in a place where he cant go up or down
		assertEquals(3, Game_Loop.getInstance().enemies[0].getX());
		assertEquals(5, Game_Loop.getInstance().enemies[0].getY());
		assertEquals(5, Game_Loop.getInstance().enemies[1].getX());
		assertEquals(5, Game_Loop.getInstance().enemies[1].getY());
		Game_Loop.getInstance().game_conditions("d");
		assertEquals(3, Game_Loop.getInstance().enemies[0].getX());
		assertEquals(5, Game_Loop.getInstance().enemies[0].getY());
		assertEquals(5, Game_Loop.getInstance().enemies[1].getX());
		assertEquals(5, Game_Loop.getInstance().enemies[1].getY());
		Game_Loop.getInstance().game_conditions("d");
		assertEquals(3, Game_Loop.getInstance().enemies[0].getX());
		assertEquals(5, Game_Loop.getInstance().enemies[0].getY());
		assertEquals(5, Game_Loop.getInstance().enemies[1].getX());
		assertEquals(5, Game_Loop.getInstance().enemies[1].getY());
		myRandom.getInstance().queue.clear();
	}
	
	//test against door
	/**
	 * Dragon cant pass door.
	 */
	@Test public void DragonCantPassDoor()
	{
		myRandom.getInstance().queue.clear();
		Integer[] moves = {2,2,2,4,4,1,1,1,4};
		Collections.addAll(myRandom.getInstance().queue, moves);
		Game_Loop.getInstance().InitTestGame(1); //0 dragon
		Game_Loop.getInstance().enemies[0].setX(6);
		Game_Loop.getInstance().enemies[0].setY(5);
		Game_Loop.getInstance().game_conditions("d");
		assertEquals(6, Game_Loop.getInstance().enemies[0].getX());
		assertEquals(6, Game_Loop.getInstance().enemies[0].getY());
		Game_Loop.getInstance().game_conditions("d");
		assertEquals(6, Game_Loop.getInstance().enemies[0].getX());
		assertEquals(7, Game_Loop.getInstance().enemies[0].getY());
		Game_Loop.getInstance().game_conditions("d");
		assertEquals(6, Game_Loop.getInstance().enemies[0].getX());
		assertEquals(8, Game_Loop.getInstance().enemies[0].getY());
		Game_Loop.getInstance().game_conditions("d");
		assertEquals(7, Game_Loop.getInstance().enemies[0].getX());
		assertEquals(8, Game_Loop.getInstance().enemies[0].getY());
		Game_Loop.getInstance().game_conditions("d");
		assertEquals(8, Game_Loop.getInstance().enemies[0].getX());
		assertEquals(8, Game_Loop.getInstance().enemies[0].getY());
		Game_Loop.getInstance().game_conditions("d");
		assertEquals(8, Game_Loop.getInstance().enemies[0].getX());
		assertEquals(7, Game_Loop.getInstance().enemies[0].getY());
		Game_Loop.getInstance().game_conditions("d");
		assertEquals(8, Game_Loop.getInstance().enemies[0].getX());
		assertEquals(6, Game_Loop.getInstance().enemies[0].getY());
		Game_Loop.getInstance().game_conditions("d"); //in exit position dragon cannot fill it
		assertEquals(8, Game_Loop.getInstance().enemies[0].getX());
		assertEquals(5, Game_Loop.getInstance().enemies[0].getY());
	}
}
