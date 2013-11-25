package testFisicaMaven.testFisicaMaven.physic;

import java.util.ArrayList;
import java.util.HashMap;

import org.jbox2d.callbacks.ContactImpulse;
import org.jbox2d.callbacks.ContactListener;
import org.jbox2d.collision.Manifold;
import org.jbox2d.dynamics.contacts.Contact;

public class CollisionListener implements ContactListener{
	ArrayList<Contact> newContact = new ArrayList<Contact>();
	ArrayList<Contact> endContact = new ArrayList<Contact>();
	HashMap<String, Contact> continousContact = new HashMap<String, Contact>();

	public void beginContact(Contact arg0) {
		newContact.add(arg0);
		
		long idA = (Long)arg0.m_fixtureA.m_userData;
		long idB = (Long)arg0.m_fixtureB.m_userData;

		continousContact.put(idA+"-"+idB, arg0 );
	}

	public void endContact(Contact arg0) {
		endContact.add(arg0);
		
		long idA = (Long)arg0.m_fixtureA.m_userData;
		long idB = (Long)arg0.m_fixtureB.m_userData;
		continousContact.remove(idA+"-"+idB);
	}

	public void postSolve(Contact arg0, ContactImpulse arg1) {
		// TODO Auto-generated method stub
		
	}

	public void preSolve(Contact arg0, Manifold arg1) {
		// TODO Auto-generated method stub
		
	}

}
