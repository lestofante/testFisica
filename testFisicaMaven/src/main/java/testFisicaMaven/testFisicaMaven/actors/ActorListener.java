package testFisicaMaven.testFisicaMaven.actors;

public interface ActorListener {

	void onScanStart(Actor created, long turn, Actor listener);

	void onScanEnd(Actor a, long turn, Actor listener);

	void onScan(Actor a, long turn, Actor listener);

	void onEndTurn(long turn, Actor listener);

	void onCollisionStart(Actor a, long turn, Actor actor);

	void onCollisionEnd(Actor a, long turn, Actor actor);

	void onCollision(Actor a, long turn, Actor actor);

}
