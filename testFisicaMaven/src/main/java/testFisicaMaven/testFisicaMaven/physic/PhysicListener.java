package testFisicaMaven.testFisicaMaven.physic;

import testFisicaMaven.testFisicaMaven.actors.Actor;

public interface PhysicListener {

	/**
	 * this method will called when an actor get in range of at least one sensors of this player
	 * @param the Actor that has been added
	 * @param turn number
	 */
	public void onCreate(Actor a, long turn);

	/**
	 * this method will called when an actor get removed of at least one sensors of this player
	 * @param the id of the actor to be removed
	 * @param turn number
	 */
	public void onDestroy(Actor a, long turn);

	/**
	 * this method will called giving the list of the actions of the scanned player 
	 * @param the id of the actor to be removed
	 * @param turn number	 
	 */
	public void onScan(Actor a, long turn);

	/**
	 * this method will called after every physic step 
	 * @param turn number
	 */
	public void onEndTurn(long turn);

}
