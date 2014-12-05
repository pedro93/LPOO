package com.trapShooter.JunitTests;
import java.util.Vector;

import org.junit.Test;

import com.trapShooter.src.Target;

import static org.junit.Assert.assertEquals;

/*
 * Testar se:
 * - Prato lançado.
 * - Existem pratos no ecrâ.
 * - O jogador disparou (feito click w/e).
 * - Disparou e acertou (pode ser em multiplos alvos).
 * - Disparou e falhou.
 * - Se existem pratos sobrepostos.
 * - Se, no modo arcada, perdeu todas as vidas.
 * - Se, no modo time trial, terminou o tempo.
 */

public class Tests {
	Vector<Target> targets = null;
	
	public Tests() {
		super();
		targets = new Vector<Target>();
	}

	@Test public void testCreateTarget() {
		targets.add(new Target(960, 300, 20000));
		assertEquals(true, targets.get(0).isActive());
	}
	/*
	@Test public void testTargetOnScreen(){	
		for(int i = 0; i < GameLogic.getInstance().getTargets().size(); i++)
		{
			assertEquals(true, GameLogic.getInstance().getTargets().elementAt(i).isOnScreen());
		}
	}
	
	@Test public void testUserFired(){
		//Assumindo que iniciamos os valores a -1;
		assertNotSame(-1, Physics.getInstance().shot_x);
		assertNotSame(-1, Physics.getInstance().shot_y);
	}
	
	@Test public void testHitTarget() {
		assertTrue(GameLogic.getInstance().isTargetHit(shot_x, shot_y));
	}
	
	@Test public void testMissTarget() {
		assertFalse(targets.get(i).isHit(shot_x, shot_y));
	}
	
	//Uma vez feito o isTargetHit fica apenas necessário usar aqui parte do codigo.
	@Test public void testOverLappingTargets(){
	}
	*/
}
