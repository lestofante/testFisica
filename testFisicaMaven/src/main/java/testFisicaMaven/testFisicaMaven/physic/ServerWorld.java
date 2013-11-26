package testFisicaMaven.testFisicaMaven.physic;

import java.util.ArrayList;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.TreeSet;

import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.Fixture;
import org.jbox2d.dynamics.World;
import org.jbox2d.dynamics.contacts.Contact;

import testFisicaMaven.testFisicaMaven.Actions.Action;
import testFisicaMaven.testFisicaMaven.actors.Actor;

public class ServerWorld {
	private static final long ACTION_BUFFER = 10;
	World world;
	float STEP = 1.0f/60.0f;
    int VI = 6;
    int POSI = 2;
	long turn = 0;
	private CollisionListener collisions = new CollisionListener();
	private TreeSet<PhysicListener> stepListener= new TreeSet<PhysicListener>(); 
	
	private SortedMap<Long, ArrayList<Action>> actionsByTurn = new TreeMap<Long, ArrayList<Action>>();
	
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
		addActor(a, new Vec2(), 0);
	}
	
	public void addActor(Actor a, Vec2 position, float angle) {
		Body tmp = world.createBody( a.getBodyDef() );
		tmp.setTransform(position, angle);
		a.setBody(tmp);
		a.setPhysic(this);
		tmp.m_userData = a;
		stepListener.add(a);
		
		a.onScanStart(a, turn); // yes, you have been created! Scan yourself :D
	}
	
	/**
	 * Call this method when you are ready to get to the next physic turn 
	 */
	public void step(){
		turn++;
		
		clearCollision();
		
		SortedMap<Long, ArrayList<Action>> toBeExecuted = new TreeMap<Long, ArrayList<Action>>( actionsByTurn.headMap(turn+1) );
		actionsByTurn = actionsByTurn.tailMap(turn+1);
		
		for (ArrayList<Action> z:toBeExecuted.values()){
			for (Action a:z){
				a.setApplyTurn(turn);
				a.execute(world, turn);
			}
		}
		
		world.step(STEP, VI, POSI);
		sendCollisionEventToPlayer();

		for (PhysicListener l: stepListener){
			l.onEndTurn(turn);
		}
	}

	private void sendCollisionEventToPlayer() {
		//send action
		for( Contact c : collisions.endContact ){
			//send destroy action
			if (c.m_fixtureA.m_isSensor && !c.m_fixtureB.m_isSensor){
				sendScanEnd(c.m_fixtureA, c.m_fixtureB); //send destroy of b to a
			}else if (!c.m_fixtureA.m_isSensor && c.m_fixtureB.m_isSensor){
				sendScanEnd(c.m_fixtureB, c.m_fixtureA); //send destroy of a to b
			}if (!c.m_fixtureA.m_isSensor && !c.m_fixtureB.m_isSensor){
				sendCollisionEnd(c.m_fixtureA, c.m_fixtureB); //send update of b to a
				sendCollisionEnd(c.m_fixtureB, c.m_fixtureA); //send update of a to b
			}
			//actually we don't care of any other case
		}
		for( Contact c : collisions.newContact ){
			//send create action
			if (c.m_fixtureA.m_isSensor && !c.m_fixtureB.m_isSensor){
				sendScanStart(c.m_fixtureA, c.m_fixtureB); //send create of b to a
			}else if (!c.m_fixtureA.m_isSensor && c.m_fixtureB.m_isSensor){
				sendScanStart(c.m_fixtureB, c.m_fixtureA); //send create of a to b
			}if (!c.m_fixtureA.m_isSensor && !c.m_fixtureB.m_isSensor){
				sendCollisionStart(c.m_fixtureA, c.m_fixtureB); //send update of b to a
				sendCollisionStart(c.m_fixtureB, c.m_fixtureA); //send update of a to b
			}
			//actually we don't care of any other case
		}
		for( Contact c : collisions.continousContact.values() ){
			//send body update sensor
			if (c.m_fixtureA.m_isSensor && !c.m_fixtureB.m_isSensor){
				sendScan(c.m_fixtureA, c.m_fixtureB); //send update of b to a
			}else if (!c.m_fixtureA.m_isSensor && c.m_fixtureB.m_isSensor){
				sendScan(c.m_fixtureB, c.m_fixtureA); //send update of a to b
			}if (!c.m_fixtureA.m_isSensor && !c.m_fixtureB.m_isSensor){
				sendCollision(c.m_fixtureA, c.m_fixtureB); //send update of b to a
				sendCollision(c.m_fixtureB, c.m_fixtureA); //send update of a to b
			}
			
			//actually we don't care of any other case
		}
	}
	
	private void sendScan(Fixture reciver_fix, Fixture scanned_fix) {
		Actor reciver = (Actor)reciver_fix.m_body.m_userData;
		Actor originator = (Actor)scanned_fix.m_body.m_userData;
		reciver.onScan( originator, turn );//we need to send many Actor info for creation
	}

	private void sendScanStart(Fixture reciver_fix, Fixture created_fix) {
		Actor reciver = (Actor)reciver_fix.m_body.m_userData;
		Actor originator = (Actor)created_fix.m_body.m_userData;
		reciver.onScanStart( originator, turn );//we need to send many Actor info for creation
	}

	private void sendScanEnd(Fixture reciver_fix, Fixture destoied_fix) {
		Actor reciver = (Actor)reciver_fix.m_body.m_userData;
		Actor originator = (Actor)destoied_fix.m_body.m_userData;
		reciver.onScanEnd( originator, turn ); //we just need the id of destoied object for destruction  
	}
	
	private void sendCollision(Fixture reciver_fix, Fixture scanned_fix) {
		Actor reciver = (Actor)reciver_fix.m_body.m_userData;
		Actor originator = (Actor)scanned_fix.m_body.m_userData;
		reciver.onCollision( originator, turn );//we need to send many Actor info for creation
	}

	private void sendCollisionStart(Fixture reciver_fix, Fixture created_fix) {
		Actor reciver = (Actor)reciver_fix.m_body.m_userData;
		Actor originator = (Actor)created_fix.m_body.m_userData;
		reciver.onCollisionStart( originator, turn );//we need to send many Actor info for creation
	}

	private void sendCollisionEnd(Fixture reciver_fix, Fixture destoied_fix) {
		Actor reciver = (Actor)reciver_fix.m_body.m_userData;
		Actor originator = (Actor)destoied_fix.m_body.m_userData;
		reciver.onCollisionEnd( originator, turn ); //we just need the id of destoied object for destruction  
	}

	private void clearCollision() {
		collisions.newContact.clear();
		collisions.endContact.clear();
		//persistent collision get auto-cleared by collision listener.
	}

	public long getTurn() {
		return turn;
	}

	public void addAction(Action a) {
		long applyTurn = a.getApplyTurn();
		if (applyTurn == -1){
			applyTurn = this.turn+ACTION_BUFFER;
		}
		ArrayList<Action> arrayList = actionsByTurn.get(applyTurn);
		if (arrayList==null){
			arrayList = new ArrayList<Action>();
			actionsByTurn.put(applyTurn, arrayList);
		}
		arrayList.add(a);
	}
	
}
