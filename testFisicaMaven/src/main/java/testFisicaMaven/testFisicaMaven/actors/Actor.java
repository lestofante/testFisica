package testFisicaMaven.testFisicaMaven.actors;

import java.util.ArrayList;

import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.BodyDef;
import org.jbox2d.dynamics.BodyType;

import testFisicaMaven.testFisicaMaven.Actions.ActionRotate;
import testFisicaMaven.testFisicaMaven.Actions.ActionTraslate;
import testFisicaMaven.testFisicaMaven.Actions.Actions;
import testFisicaMaven.testFisicaMaven.actors.BluePrint.Tipo;
import testFisicaMaven.testFisicaMaven.physic.PhysicListener;

public class Actor implements PhysicListener, Comparable<Actor>{

	private static int FREE_ID = 0;
	public static int getNextFreeId(){
		return FREE_ID++;
	}
	
	private Body body;
	private final Tipo tipo;
	private final int id;
	
	private ArrayList<Actions> azioni = new ArrayList<Actions>();
	private ArrayList<ActorListener> listeners = new ArrayList<ActorListener>();
	
	public Actor(Tipo t){
		this.id = getNextFreeId();
		this.tipo = t;
	}
	
	public int getId() {
		return id;
	}
	
	public BodyDef getBodyDef() {
		BodyDef bd = new BodyDef();
		bd.type = BodyType.DYNAMIC;
		return bd;
	}

	public void setBody(Body body) {
		BluePrint.createFixiture(tipo, body);
		this.body = body;
	}
	
	public void applyForce(Vec2 force){
		applyForce(force, body.getWorldCenter());
	}
	
	public void applyForce(Vec2 force, Vec2 pointOfApplication){
		body.applyForce(force, pointOfApplication);
		azioni.add(new ActionTraslate(id, force, pointOfApplication));
	}

	public void applyTorque(float torque){
		body.applyTorque(torque);
		azioni.add(new ActionRotate(id, torque));
	}

	/**
	 * This method return all action executed on this Actor in this turn.
	 * It get cleared when onEndTurn() get called
	 * @return
	 */
	public ArrayList<Actions> getActions() {
		return azioni;
	}
	
	/**
	 * Add a listener to this Actor, who get informed on whatever happens on this Actor and its sensor
	 */
	public void addListener(ActorListener l){
		listeners .add(l);
	}

	/* FROM PHYSIC LISTENER */
	public void onScanStart(Actor a, long turn) {
		for(ActorListener l: listeners){
			l.onScanStart(a, turn, this);
		}
	}

	public void onScanEnd(Actor a, long turn) {
		for(ActorListener l: listeners){
			l.onScanEnd(a, turn, this);
		}
	}

	public void onScan(Actor a, long turn) {
		for(ActorListener l: listeners){
			l.onScan(a, turn, this);
		}
	}

	public void onEndTurn(long turn) {
		azioni.clear();
		for(ActorListener l: listeners){
			l.onEndTurn(turn, this);
		}
	}

	public int compareTo(Actor arg0) {
		return Integer.compare(id, arg0.id);
	}
	
	@Override
	public String toString(){
		return "id: "+id+"tipo: "+tipo+" posizione: "+body.getPosition()+" angolo: "+body.getAngle();
	}

	public void onCollisionStart(Actor a, long turn) {
		for(ActorListener l: listeners){
			l.onCollisionStart(a, turn, this);
		}
	}

	public void onCollisionEnd(Actor a, long turn) {
		for(ActorListener l: listeners){
			l.onCollisionEnd(a, turn, this);
		}
	}

	public void onCollision(Actor a, long turn) {
		for(ActorListener l: listeners){
			l.onCollision(a, turn, this);
		}
	}

}
