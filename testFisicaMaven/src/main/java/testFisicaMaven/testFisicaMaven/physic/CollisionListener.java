package testFisicaMaven.testFisicaMaven.physic;

import java.util.ArrayList;
import java.util.HashMap;

import org.jbox2d.callbacks.ContactImpulse;
import org.jbox2d.callbacks.ContactListener;
import org.jbox2d.collision.Manifold;
import org.jbox2d.dynamics.contacts.Contact;

import testFisicaMaven.testFisicaMaven.actors.Actor;

public class CollisionListener implements ContactListener{
	ArrayList<Contact> newContact = new ArrayList<Contact>();
	ArrayList<Contact> endContact = new ArrayList<Contact>();
	HashMap<String, Contact> continousContact = new HashMap<String, Contact>();

	public void beginContact(Contact arg0) {
		newContact.add(arg0);
		
		Actor a = (Actor)arg0.m_fixtureA.m_body.m_userData;
		Actor b = (Actor)arg0.m_fixtureB.m_body.m_userData;
		continousContact.put(a.getId()+"-"+b.getId(), arg0);
	}

	public void endContact(Contact arg0) {
		endContact.add(arg0);
		
		Actor a = (Actor)arg0.m_fixtureA.m_body.m_userData;
		Actor b = (Actor)arg0.m_fixtureB.m_body.m_userData;
		continousContact.remove(a.getId()+"-"+b.getId());
	}

	public void postSolve(Contact arg0, ContactImpulse arg1) {
		// TODO Auto-generated method stub
		
	}

	public void preSolve(Contact arg0, Manifold arg1) {
		// TODO Auto-generated method stub
		
	}

}
