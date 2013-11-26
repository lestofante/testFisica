package testFisicaMaven.testFisicaMaven.actors;

import java.util.ArrayList;

import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.BodyDef;
import org.jbox2d.dynamics.BodyType;

import testFisicaMaven.testFisicaMaven.Actions.Action;
import testFisicaMaven.testFisicaMaven.actors.BluePrint.Tipo;
import testFisicaMaven.testFisicaMaven.physic.PhysicListener;
import testFisicaMaven.testFisicaMaven.physic.ServerWorld;

public class Actor implements PhysicListener, Comparable<Actor>{

	private static int FREE_ID = 0;
	public static int getNextFreeId(){
		return FREE_ID++;
	}
	
	private final int id;
	private final Tipo tipo;
	
	private ServerWorld physic;
	private Body body;
	
	private ArrayList<Action> actionsArrivedThisTurn = new ArrayList<Action>();
	
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

	public void setPhysic(ServerWorld physic) {
		this.physic = physic;
	}
	
	public void setBody(Body body) {
		BluePrint.createFixiture(tipo, body);
		this.body = body;
	}
	
	public Body getBody() {
		return body;
	}
	
	public void applyAction(Action a){
		actionsArrivedThisTurn.add(a);
		a.setActor(this);
		physic.addAction(a);
	}
	
	
/*	
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
*/
	/**
	 * This method return all action executed on this Actor in this turn.
	 * It get cleared when onEndTurn() get called
	 * @return
	 */
	public ArrayList<Action> getActions() {
		return actionsArrivedThisTurn;
	}
	
	/**
	 * Add a listener to this Actor, who get informed on whatever happens on this Actor and its sensor
	 */
	public void addListener(ActorListener l){
		listeners .add(l);
	}
	
	public int compareTo(Actor arg0) {
		return Integer.compare(id, arg0.id);
	}
	
	@Override
	public String toString(){
		return "id: "+id+"tipo: "+tipo+" posizione: "+body.getPosition()+" angolo: "+body.getAngle();
	}

	/* FROM PHYSIC LISTENER */

	public void onEndTurn(long turn) {
		actionsArrivedThisTurn.clear();
		for(ActorListener l: listeners){
			l.onEndTurn(turn, this);
		}
	}
	
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
