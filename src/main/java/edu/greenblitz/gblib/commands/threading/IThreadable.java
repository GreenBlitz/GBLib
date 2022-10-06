package edu.greenblitz.GBLib.src.main.java.edu.greenblitz.gblib.commands.threading;

public interface IThreadable {

	void run();

	boolean isFinished();

	void atEnd();

	void atInit();

	default void atInterrupt() {
		atEnd();
	}

}
