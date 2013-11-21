package testFisicaMaven.testFisicaMaven.compression;

import java.util.ArrayList;

import testFisicaMaven.testFisicaMaven.Actions.Actions;
import testFisicaMaven.testFisicaMaven.actors.Actor;

public interface CompressorListener {

	public void onCreate(Actor m_userData);

	public void onDestroy(int id);

	public void onScan(ArrayList<Actions> actions);

}
