package testFisicaMaven.testFisicaMaven.Actions;

import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.World;


public class ActionImpulse extends Action {

	private Vec2 impulse;

	public ActionImpulse(Vec2 impulse) {
		super();
		this.impulse = impulse;
	}

	@Override
	public void execute(World world, long turn) {
		Body b = getActor().getBody();
		//b.applyForceToCenter(force);
		b.applyLinearImpulse( impulse, b.getPosition() );
	}

}
