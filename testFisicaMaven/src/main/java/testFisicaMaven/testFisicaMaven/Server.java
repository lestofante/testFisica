package testFisicaMaven.testFisicaMaven;

import org.jbox2d.dynamics.Fixture;
import org.jbox2d.dynamics.contacts.Contact;

import testFisicaMaven.testFisicaMaven.actors.Actor;
import testFisicaMaven.testFisicaMaven.actors.Actor.Tipo;
import testFisicaMaven.testFisicaMaven.physic.ServerWorld;
import testFisicaMaven.testFisicaMaven.user.Player;


/**
 * Hello world!
 *
 */
public class Server {
	
	ServerWorld world = new ServerWorld();
	Player NPC = new Player();
	
    public static void main( String[] args )
    {
        System.out.println( "Hello World!" );//lol
        new Server();
    }
    
    public Server(){
    	
    	addBody();//just to test
    	
    	while (true){
    		//TODO: execute actions on actors
    		
    		//execute step
    		world.step();
    		
    		//send action
    		for( Contact c:world.getEndCollision() ){
    			//send destroy action
    			if (c.m_fixtureA.m_isSensor && !c.m_fixtureB.m_isSensor){
    				sendDestory(c.m_fixtureA, c.m_fixtureB); //send destroy of b to a
    			}else if (!c.m_fixtureA.m_isSensor && c.m_fixtureB.m_isSensor){
    				sendDestory(c.m_fixtureB, c.m_fixtureA); //send destroy of a to b
    			}
    			//actually we don't care of any other case
    		}
    		for( Contact c:world.getNewCollision() ){
    			//send create action
    			if (c.m_fixtureA.m_isSensor && !c.m_fixtureB.m_isSensor){
    				sendCreate(c.m_fixtureA, c.m_fixtureB); //send destroy of b to a
    			}else if (!c.m_fixtureA.m_isSensor && c.m_fixtureB.m_isSensor){
    				sendCreate(c.m_fixtureB, c.m_fixtureA); //send destroy of a to b
    			}
    			//actually we don't care of any other case
    		}
    		for( Contact c:world.getPersistentCollision() ){
    			//send body action to sensor
    			if (c.m_fixtureA.m_isSensor && !c.m_fixtureB.m_isSensor){
    				sendAction(c.m_fixtureA, c.m_fixtureB); //send destroy of b to a
    			}else if (!c.m_fixtureA.m_isSensor && c.m_fixtureB.m_isSensor){
    				sendAction(c.m_fixtureB, c.m_fixtureA); //send destroy of a to b
    			}
    			//actually we don't care of any other case
    		}
    		
    		//clear collision
    		world.clearCollision();
    		
    		//TODO: clear Actors's actions!!
    		
    		
    		try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
    	}
    }

	private void sendAction(Fixture reciver_fix, Fixture scanned_fix) {
		Actor reciver = (Actor)reciver_fix.m_userData;
		reciver.getPlayer().getListener().onScan( ((Actor)scanned_fix.m_userData).getActions() );//we need to send many Actor info for creation
	}

	private void sendCreate(Fixture reciver_fix, Fixture created_fix) {
		Actor reciver = (Actor)reciver_fix.m_userData;
		reciver.getPlayer().getListener().onCreate( ((Actor)created_fix.m_userData) );//we need to send many Actor info for creation
	}

	private void sendDestory(Fixture reciver_fix, Fixture destoied_fix) {
		Actor reciver = (Actor)reciver_fix.m_userData;
		reciver.getPlayer().getListener().onDestroy( ((Actor)destoied_fix.m_userData).getId() ); //we just need the id of destoied object for destruction  
	}

	private void addBody() {
		world.addActor( new Actor(Tipo.Astronave, NPC) );
	}
}
