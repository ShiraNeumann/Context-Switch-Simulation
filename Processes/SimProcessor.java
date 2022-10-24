package Processes;
import java.util.*;

public class SimProcessor {
	private SimProcess currProcess;
	private int r1;
	private int r2;
	private int r3;
	private int r4;
	private int currInstruction;
	
	public SimProcessor() {
		
	}
	
	public void setSimProcess(SimProcess currProcess) {
		this.currProcess = currProcess;
	}
	
	public SimProcess getSimProcess() {
		return currProcess;
	}
	
	public void setRegisterValue1(int r1) {
		this.r1 = r1;
	}
	
	public void setRegisterValue2(int r2) {
		this.r2 = r2;
	}
	
	public void setRegisterValue3(int r3) {
		this.r3 = r3;
	}
	
	public void setRegisterValue4(int r4) {
		this.r4 = r4;
	}
	
	public int getRegisterValue1() {
		return this.r1;
	}
	
	public int getRegisterValue2() {
		return this.r2;
	}
	
	public int getRegisterValue3() {
		return this.r3;
	}
	
	public int getRegisterValue4() {
		return this.r4;
	}
	
	public void setCurrInstruction(int currInstruction) {
		this.currInstruction = currInstruction;
	}
	
	public int getCurrInstruction() {
		return currInstruction;
	}
	
	public ProcessState executeNextInstruction() {
		ProcessState state = currProcess.execute(currInstruction);
		currInstruction++;
		Random rand = new Random();
		r1 = rand.nextInt();
		r2 = rand.nextInt();
		r3 = rand.nextInt();
		r4 = rand.nextInt();
		return state;
	}
}
