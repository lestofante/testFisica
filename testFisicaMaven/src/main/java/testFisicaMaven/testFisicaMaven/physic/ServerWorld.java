package testFisicaMaven.testFisicaMaven.physic;

import java.util.ArrayList;

import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;
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
	
	public ServerWorld(){
		startWorld();
	}
	
	
	private void startWorld() {
		Vec2 gravity = new Vec2(0.0f, 0.0f);
		world = new World(gravity);
		world.setContactListener(collisions);
	}


	public void addActor(Actor a) {
		Body tmp = world.createBody( a.getBodyDef() );
		a.setBody(tmp);
		tmp.m_userData = a; 
		
		for (Actor s: a.getSensors()){
			tmp = world.createBody( s.getBodyDef() );
			s.setBody(tmp);
			tmp.m_userData = a;
		}
	}
	
	
	public void step(){
		world.step(STEP, VI, POSI);
	}


	public ArrayList<Contact> getEndCollision() {
		return collisions.endContact;
	}


	public ArrayList<Contact> getNewCollision() {
		return collisions.newContact;
	}


	public ArrayList<Contact> getPersistentCollision() {
		return new ArrayList<Contact>(collisions.continousContact.values());
	}


	public void clearCollision() {
		collisions.newContact.clear();
		collisions.endContact.clear();
		//persistent collision get auto-cleared
	}
	
}
