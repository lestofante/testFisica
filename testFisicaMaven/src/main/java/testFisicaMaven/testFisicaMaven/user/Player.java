package testFisicaMaven.testFisicaMaven.user;

import testFisicaMaven.testFisicaMaven.compression.CompressorListener;
import testFisicaMaven.testFisicaMaven.compression.DefaultCollisionListener;

public class Player {

	CompressorListener listener = new DefaultCollisionListener();
	
	public CompressorListener getListener() {
		return listener;
	}

}
