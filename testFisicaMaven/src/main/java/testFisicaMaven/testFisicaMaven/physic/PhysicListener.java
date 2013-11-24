package testFisicaMaven.testFisicaMaven.physic;

import testFisicaMaven.testFisicaMaven.actors.Actor;

public interface PhysicListener {

	/**
	 * @param the Actor that has been scanned
	 * @param turn number
	 */
	public void onScanStart(Actor a, long turn);

	/**
	 * @param the Actor that has been gone out of scan
	 * @param turn number
	 */
	public void onScanEnd(Actor a, long turn);

	/** 
	 * @param the Actor that has still be scanned
	 * @param turn number	 
	 */
	public void onScan(Actor a, long turn);
	
	/**
	 * @param the Actor that has been collided
	 * @param turn number
	 */
	public void onCollisionStart(Actor a, long turn);

	/**
	 * @param the Actor that has been gone out of collision
	 * @param turn number
	 */
	public void onCollisionEnd(Actor a, long turn);

	/** 
	 * @param the Actor that has still collide
	 * @param turn number	 
	 */
	public void onCollision(Actor a, long turn);

	/**
	 * this method will called after every physic step 
	 * @param turn number
	 */
	public void onEndTurn(long turn);

}
