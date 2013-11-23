package testFisicaMaven.testFisicaMaven;

import testFisicaMaven.testFisicaMaven.actors.Actor;
import testFisicaMaven.testFisicaMaven.actors.Actor.Tipo;
import testFisicaMaven.testFisicaMaven.physic.ServerWorld;


/**
 * Hello world!
 *
 */
public class Server {
	
	ServerWorld world = new ServerWorld();
	
    public static void main( String[] args )
    {
        System.out.println( "Hello World!" );//lol
        new Server();
    }
    
    public Server(){
    	
    	addBody();//just to test
    	
    	while (true){
    		//TODO: execute actions of player
    		
    		//execute step
    		world.step();
    		
    		
    		try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
    	}
    }

	private void addBody() {
		world.addActor( new Actor(Tipo.Astronave) );
	}
}
