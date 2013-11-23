package testFisicaMaven.testFisicaMaven;

import testFisicaMaven.testFisicaMaven.actors.Actor;
import testFisicaMaven.testFisicaMaven.actors.BluePrint.Tipo;
import testFisicaMaven.testFisicaMaven.physic.ServerWorld;


/**
 * Hello world!
 *
 */
public class Server {
	
	ServerWorld world = new ServerWorld();
	
	/**
	 * basic example
	 * @param NOT USED
	 */
    public static void main( String[] args )
    {
        new Server();
    }
    
    public Server(){
    	
    	addBody();//just to test
    	
    	while (true){
    		//HERE you should execute actions on the Actors
    		
    		//remember to call this
    		world.step();
    		
    		//and the wait based on tour desired physic frame rate
    		try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
    	}
    }

	private void addBody() {
		//we create an Actor from blueprint "Astronave"
		Actor attore = new Actor(Tipo.Astronave);
		
		//we just need to add the "master" to world. Please don't add subActor to the physic or after the main actor as been added to the world.
		world.addActor( attore );
		
		//HERE you should add a listener at the master actor who send data to the Actor's owner  
	}
}
