package Processes;
import java.util.*;

public class SimProcess {
	private int pid;
	private String procName;
	private int totalInstructions;
	
	public SimProcess (int pid, String procName, int totalInstructions) {
		this.pid = pid;
		this.procName = procName;
		this.totalInstructions = totalInstructions;
	}
	
	public int getPid() {
		return pid;
	}
	
	public ProcessState execute (int i) {
		System.out.println("Proc: " + procName +", PID: " + pid +  " executing instruction " + i );
		
		if(i >= totalInstructions) {
			return ProcessState.FINISHED;
		} else {
			Random rand = new Random ();
			int num = rand.nextInt(20);
			if(num <= 3) {
				return ProcessState.BLOCKED;
			}
		}
		return ProcessState.READY;
	}
}
