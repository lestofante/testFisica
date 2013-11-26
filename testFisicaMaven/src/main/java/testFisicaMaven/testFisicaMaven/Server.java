package testFisicaMaven.testFisicaMaven;

import org.jbox2d.common.Vec2;

import testFisicaMaven.testFisicaMaven.Actions.ActionImpulse;
import testFisicaMaven.testFisicaMaven.actors.Actor;
import testFisicaMaven.testFisicaMaven.actors.ActorListener;
import testFisicaMaven.testFisicaMaven.actors.BluePrint.Tipo;
import testFisicaMaven.testFisicaMaven.physic.ServerWorld;

/**
 * Hello world!
 * 
 */
public class Server {

	ServerWorld world = new ServerWorld();

	Actor lastCreated;
	int direction = -1;
	/**
	 * basic example
	 * 
	 * @param NOT
	 *            USED
	 */
	public static void main(String[] args) {
		new Server();
	}

	public Server() {

		addBody();// just to test

		while (true) {
			// HERE you should execute actions on the Actors

			// remember to call this
			world.step();

			System.out.println( "lastCreated: "+ lastCreated.toString() );
			
			if (world.getTurn() % 50==0){
				lastCreated.applyAction( new ActionImpulse(new Vec2(-50, 0)) );			
			}
			
			// and the wait based on tour desired physic frame rate
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	private void addBody() {
		ActorListener oneForAll = new ActorListener() {
			public void onEndTurn(long turn, Actor b) {
				System.out.println(b.getId() + " End turn:" + turn);
			}
			
			public void onScan(Actor a, long turn, Actor b) {
				//System.out.println(b.getId() + " Scanned:" + a.toString() );
			}

			public void onScanEnd(Actor a, long turn, Actor b) {
				System.out.println(b.getId() + " Scan End:" + a.getId());
			}

			public void onScanStart(Actor a, long turn, Actor b) {
				System.out.println(b.getId() + " Scan Start:" + a.getId());
			}

			public void onCollisionStart(Actor a, long turn, Actor b) {
				System.out.println(b.getId() + " Collision Start:" + a.getId());
			}

			public void onCollisionEnd(Actor a, long turn, Actor b) {
				System.out.println(b.getId() + " Collision End:" + a.getId());
			}

			public void onCollision(Actor a, long turn, Actor b) {
				//warning: collision overwrite scan,if you get a scan you won't have a collision
				System.out.println(b.getId() + " collided:" + a.getId());
				//direction *= -1;
			}
		};
		Actor attore = null;
		for (int i = 0; i < 10; i++) {
			// we create an Actor from blueprint "Astronave"
			attore = new Actor(Tipo.Astronave);
			
			//attore.addListener(oneForAll);
			
			world.addActor(attore, new Vec2(i*1.5f, 0), 0 );
			
		}
		
		lastCreated = attore;
		
		lastCreated.addListener(oneForAll);
	}
}
