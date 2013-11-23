package testFisicaMaven.testFisicaMaven.actors;

public interface ActorListener {

	void onCreate(Actor a, long turn);

	void onDestroy(Actor a, long turn);

	void onScan(Actor a, long turn);

	void onEndTurn(long turn);

}
