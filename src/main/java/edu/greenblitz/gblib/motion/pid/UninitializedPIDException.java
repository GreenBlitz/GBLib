package edu.greenblitz.GBLib.src.main.java.edu.greenblitz.gblib.motion.pid;

@SuppressWarnings("serial")
public class UninitializedPIDException extends IllegalStateException {
	public UninitializedPIDException() {
	}

	public UninitializedPIDException(String s) {
		super(s);
	}

	public UninitializedPIDException(String message, Throwable cause) {
		super(message, cause);
	}

	public UninitializedPIDException(Throwable cause) {
		super(cause);
	}

//	public UninitializedPIDException(edu.greenblitz.gblib.motion.pid.PIDController pid) {
//		this("PID " + pid + " isn't initialized yet");
//	}
}
