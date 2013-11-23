package testFisicaMaven.testFisicaMaven.physic;

import java.util.TreeSet;

import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.Fixture;
import org.jbox2d.dynamics.World;
import org.jbox2d.dynamics.contacts.Contact;

import testFisicaMaven.testFisicaMaven.actors.Actor;

public class ServerWorld {
	World world;
	float STEP = 1.0f/60.0f;
    int VI = 6;
    int POSI = 2;
	long turn = 0;
	CollisionListener collisions = new CollisionListener();
	private boolean DEBUG = true;
	private TreeSet<PhysicListener> stepListener= new TreeSet<PhysicListener>(); 
	
	/**
	 * default constructor. no gravity, standard physic settings. 
	 */
	public ServerWorld(){
		startWorld();
	}
	
	private void startWorld() {
		Vec2 gravity = new Vec2(0.0f, 0.0f);
		world = new World(gravity);
		world.setContactListener(collisions);
	}

	/**
	 * Add an actor to the physic world, and also it's sensors.
	 * Please, never bypass this AND never add two time the same actor. 
	 * @param The actor that should be added
	 */
	public void addActor(Actor a) {
		Body tmp = world.createBody( a.getBodyDef() );
		a.setBody(tmp);
		tmp.m_userData = a;
		stepListener.add(a);
		
		for (Actor s: a.getSensors()){
			tmp = world.createBody( s.getBodyDef() );
			s.setBody(tmp);
			tmp.m_userData = s;
			//stepListener.add(a);
		}
	}
	
	/**
	 * Call this method when you are ready to get to the next physic turn 
	 */
	public void step(){
		turn++;
		
		clearCollision();
		world.step(STEP, VI, POSI);
		sendCollisionEventToPlayer();

		if ( DEBUG  && turn%100 == 0 ){
			sendAllMap();
		}
		
		for (PhysicListener l: stepListener){
			l.onEndTurn(turn);
		}
	}

	private void sendAllMap() {
		// TODO Auto-generated method stub
		
	}

	private void sendCollisionEventToPlayer() {
		//send action
		for( Contact c : collisions.endContact ){
			//send destroy action
			if (c.m_fixtureA.m_isSensor && !c.m_fixtureB.m_isSensor){
				sendDestory(c.m_fixtureA, c.m_fixtureB); //send destroy of b to a
			}else if (!c.m_fixtureA.m_isSensor && c.m_fixtureB.m_isSensor){
				sendDestory(c.m_fixtureB, c.m_fixtureA); //send destroy of a to b
			}
			//actually we don't care of any other case
		}
		for( Contact c : collisions.newContact ){
			//send create action
			if (c.m_fixtureA.m_isSensor && !c.m_fixtureB.m_isSensor){
				sendCreate(c.m_fixtureA, c.m_fixtureB); //send destroy of b to a
			}else if (!c.m_fixtureA.m_isSensor && c.m_fixtureB.m_isSensor){
				sendCreate(c.m_fixtureB, c.m_fixtureA); //send destroy of a to b
			}
			//actually we don't care of any other case
		}
		for( Contact c : collisions.continousContact.values() ){
			//send body action to sensor
			if (c.m_fixtureA.m_isSensor && !c.m_fixtureB.m_isSensor){
				sendAction(c.m_fixtureA, c.m_fixtureB); //send destroy of b to a
			}else if (!c.m_fixtureA.m_isSensor && c.m_fixtureB.m_isSensor){
				sendAction(c.m_fixtureB, c.m_fixtureA); //send destroy of a to b
			}
			//actually we don't care of any other case
		}
	}
	
	private void sendAction(Fixture reciver_fix, Fixture scanned_fix) {
		Actor reciver = (Actor)reciver_fix.m_userData;
		reciver.onScan( ((Actor)scanned_fix.m_userData), turn );//we need to send many Actor info for creation
	}

	private void sendCreate(Fixture reciver_fix, Fixture created_fix) {
		Actor reciver = (Actor)reciver_fix.m_userData;
		reciver.onCreate( ((Actor)created_fix.m_userData), turn );//we need to send many Actor info for creation
	}

	private void sendDestory(Fixture reciver_fix, Fixture destoied_fix) {
		Actor reciver = (Actor)reciver_fix.m_userData;
		reciver.onDestroy( ((Actor)destoied_fix.m_userData), turn ); //we just need the id of destoied object for destruction  
	}

	private void clearCollision() {
		collisions.newContact.clear();
		collisions.endContact.clear();
		//persistent collision get auto-cleared by collision listener.
	}
	
}
