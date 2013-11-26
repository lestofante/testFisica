package testFisicaMaven.testFisicaMaven.Actions;

import org.jbox2d.dynamics.World;

import testFisicaMaven.testFisicaMaven.actors.Actor;

public abstract class Action {
	private long turn = -1;
	private Actor actor;
	
	public long getApplyTurn() {
		return turn;
	}
	
	public void setApplyTurn(long turn) {
		this.turn = turn;
	}

	public void setActor(Actor actor) {
		this.actor = actor;
	}
	
	public Actor getActor() {
		return actor;
	}

	public abstract void execute(World world, long turn);

}
