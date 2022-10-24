package Processes;

public class ProcessControlBlock {
	private SimProcess sp;
	private int currInstruction;
	private int r1;
	private int r2;
	private int r3;
	private int r4;

	
	public ProcessControlBlock (SimProcess sp) {
		this.sp = sp;
		currInstruction = 1;
	}
	
	public SimProcess getSimProcess() {
		return sp;
	}
	
	public void setCurrInstruction(int currInstruction) {
		this.currInstruction = currInstruction;
	}
	
	public int getCurrInstruction() {
		return currInstruction;
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
}
