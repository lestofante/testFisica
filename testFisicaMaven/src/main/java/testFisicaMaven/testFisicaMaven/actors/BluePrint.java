package testFisicaMaven.testFisicaMaven.actors;

import org.jbox2d.collision.shapes.CircleShape;
import org.jbox2d.collision.shapes.Shape;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.FixtureDef;

public class BluePrint {
	public static enum Tipo {
		Astronave 
	}
	
	public static void createFixiture(Tipo tipo, Body body) {
		FixtureDef fd = new FixtureDef();
		Shape cs;
		
		switch(tipo){
		case Astronave:
			//TODO: this should be loaded from local file
			
			//define the shape, size and physic of the HULL
			cs = new CircleShape();
			cs.m_radius = 0.5f;
			fd.shape = cs;
			fd.density = 0.5f;
			fd.friction = 0.3f;       
			fd.restitution = 0.5f;
			//add the HULL to the body
			body.createFixture(fd);
			
			//define shape and size of the radar.
			cs = new CircleShape();
			//body definition
			cs.m_radius = 10f;
			fd.shape = cs;
			fd.isSensor = true;
			//add the RADAR to the body
			body.createFixture(fd);
			break;
			
		default:
			break;
		}
		
		
	}

}
