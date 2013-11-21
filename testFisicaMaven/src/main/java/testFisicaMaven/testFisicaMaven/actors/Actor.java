package testFisicaMaven.testFisicaMaven.actors;

import java.util.ArrayList;

import org.jbox2d.collision.shapes.CircleShape;
import org.jbox2d.collision.shapes.Shape;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.BodyDef;
import org.jbox2d.dynamics.BodyType;
import org.jbox2d.dynamics.FixtureDef;

import testFisicaMaven.testFisicaMaven.Actions.ActionRotate;
import testFisicaMaven.testFisicaMaven.Actions.ActionTraslate;
import testFisicaMaven.testFisicaMaven.Actions.Actions;
import testFisicaMaven.testFisicaMaven.user.Player;

public class Actor {
	public static enum Tipo {
		Astronave, Radar 
	}
	
	private static int FREE_ID = 0;
	public static int getNextFreeId(){
		return FREE_ID++;
	}
	
	private Body body;
	private ArrayList<Actor> sensors = new ArrayList<Actor>();
	private final Tipo tipo;
	private final int id;
	
	private ArrayList<Actions> azioni = new ArrayList<Actions>();
	private Player owner;
	
	public Actor(Tipo t, Player owner){
		this.tipo = t;
		this.id = getNextFreeId();
		this.owner = owner;
		if (t == Tipo.Astronave){
			sensors.add(id, new Actor(Tipo.Radar, owner) );
		}
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
		
		//define fixture of the body.
		FixtureDef fd = new FixtureDef();
		Shape cs;
		
		switch(tipo){
		case Astronave:
			//define shape of the body.
			cs = new CircleShape();
			
			//body definition
			cs.m_radius = 0.5f;
			fd.shape = cs;
			fd.density = 0.5f;
			fd.friction = 0.3f;       
			fd.restitution = 0.5f;
			break;
		case Radar:
			//define shape of the body.
			cs = new CircleShape();
			//body definition
			cs.m_radius = 10f;
			fd.shape = cs;
			fd.isSensor = true;
		default:
			break;
		}
		
		body.createFixture(fd);
		
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
	
	public ArrayList<Actor> getSensors() {
		return sensors;
	}

	public ArrayList<Actions> getActions() {
		return azioni;
	}

	public Player getPlayer() {
		return owner;
	}

}
