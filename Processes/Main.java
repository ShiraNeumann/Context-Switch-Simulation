package Processes;

import java.util.*;


public class Main {
	
	public static void main (String [] args) {
		
		
		SimProcessor processor = new SimProcessor();
		
		ArrayList <ProcessControlBlock> processReady = createProcesses();
			
		ArrayList <ProcessControlBlock> processBlocked = new ArrayList <ProcessControlBlock>();
		
		execute(processor, processReady, processBlocked);
		
		}
		
		
	public static ArrayList <ProcessControlBlock> createProcesses() {
		SimProcess p1 = new SimProcess(1,"Proc1",264);
		ProcessControlBlock pcb1 = new ProcessControlBlock(p1);
		
		SimProcess p2 = new SimProcess(2,"Proc2",321);
		ProcessControlBlock pcb2 = new ProcessControlBlock(p2);

		SimProcess p3 = new SimProcess(3,"Proc3",115);
		ProcessControlBlock pcb3 = new ProcessControlBlock(p3);

		SimProcess p4 = new SimProcess(4,"Proc4",198);
		ProcessControlBlock pcb4 = new ProcessControlBlock(p4);

		SimProcess p5 = new SimProcess(5,"Proc5",222);
		ProcessControlBlock pcb5 = new ProcessControlBlock(p5);

		SimProcess p6 = new SimProcess(6,"Proc6",374);
		ProcessControlBlock pcb6 = new ProcessControlBlock(p6);

		SimProcess p7 = new SimProcess(7,"Proc7",281);
		ProcessControlBlock pcb7 = new ProcessControlBlock(p7);

		SimProcess p8 = new SimProcess(8,"Proc8",150);
		ProcessControlBlock pcb8 = new ProcessControlBlock(p8);

		SimProcess p9 = new SimProcess(9,"Proc9",162);
		ProcessControlBlock pcb9 = new ProcessControlBlock(p9);

		SimProcess p10 = new SimProcess(10,"Proc10",256);
		ProcessControlBlock pcb10 = new ProcessControlBlock(p10);
		
		ArrayList <ProcessControlBlock> processReady = new ArrayList <ProcessControlBlock>(
				Arrays.asList(pcb1,pcb2,pcb3,pcb4,pcb5,pcb6,pcb7,pcb8,pcb9,pcb10));
		
		return processReady;
	}
	
	public static void execute(SimProcessor processor, ArrayList<ProcessControlBlock> processReady, ArrayList<ProcessControlBlock> processBlocked) {
		final int QUANTUM = 5;
		int quant = 0; //keep track of process quantum time
		int finished = 0;  // keep track of completed processes
		for(int step = 1; step<=3000; step++) {
			
			ProcessControlBlock pcb = processReady.get(0); //FIFO
			
			processor.setSimProcess(pcb.getSimProcess()); //set the simProcessor with the current ready instruction
			processor.setCurrInstruction(pcb.getCurrInstruction());//Set the simProcessor with the current instruction
			
			System.out.print("Step " + step + " ");
			
			ProcessState ps = processor.executeNextInstruction();
			pcb.setCurrInstruction(processor.getCurrInstruction());//update the instruction its up to
			quant++;//increase the quant count each time an instruction is executed
			
			if(ps.equals(ProcessState.FINISHED)) {
				processReady.remove(0);
				finished++;
				quant = 0;
				System.out.println("** Process Completed **");
				if(finished == 10) {
					System.out.println("** All Processes Completed **");
					break;
				}else {
					step = contextSwitch(pcb, processor, step, processReady, processBlocked);
				}
			} else if(ps.equals(ProcessState.BLOCKED)) {
				System.out.println("** Process Blocked **");
				processBlocked.add(pcb);//add to the blocked list
				processReady.remove(0); //remove from the ready list
				step = contextSwitch(pcb, processor, step, processReady, processBlocked);
				quant = 0; //each time a new process starts, the quantum is reset

			} else if(quant == QUANTUM) {
				System.out.println("** Quantum Expired **");
				processReady.remove(0); //remove from the front of the ready list
				processReady.add(pcb); //and add it to the back
				step = contextSwitch(pcb, processor, step, processReady, processBlocked);
				quant = 0; //each time a new process starts, the quantum is reset
		
			}	
		}
	}
	
	public static int contextSwitch(ProcessControlBlock pcb, SimProcessor processor, int step,ArrayList <ProcessControlBlock> processReady, ArrayList <ProcessControlBlock> processBlocked ) {
		//update the pcb for a context switch
		updatePcb(pcb, processor);
		step++;
		System.out.println("Step "+ step +" Context Switch: Saving process: " + pcb.getSimProcess().getPid());
		System.out.println("\tInstruction "+ pcb.getCurrInstruction() + " - R1: " + pcb.getRegisterValue1() + ", R2: " + pcb.getRegisterValue2() + ", R3: " + pcb.getRegisterValue3() + ", R4: " + pcb.getRegisterValue4());
		
		
		while( processReady.size() == 0 && processBlocked.size() != 0 ) {
			System.out.println("** System Idling **");
			unblock(processReady, processBlocked);
		}
		
		//restore a process if needed
		if(processReady.get(0).getCurrInstruction()>1) {
			System.out.println("Context Switch: Restoring process: " + processReady.get(0).getSimProcess().getPid());
			System.out.println("\tInstruction "+ processReady.get(0).getCurrInstruction() + " - R1: " + processReady.get(0).getRegisterValue1() + ", R2: " + processReady.get(0).getRegisterValue2() + ", R3: " + processReady.get(0).getRegisterValue3() + ", R4: " + processReady.get(0).getRegisterValue4());
		}
		
		return step;
	}
	public static void updatePcb(ProcessControlBlock pcb, SimProcessor processor) {
		//Set the ProcessControlBlock to the SimProcessor instruction and register values
		pcb.setCurrInstruction(processor.getCurrInstruction());
		pcb.setRegisterValue1(processor.getRegisterValue1());
		pcb.setRegisterValue2(processor.getRegisterValue2());
		pcb.setRegisterValue3(processor.getRegisterValue3());
		pcb.setRegisterValue4(processor.getRegisterValue4());
	}
	
	public static void unblock (ArrayList <ProcessControlBlock> processReady, ArrayList <ProcessControlBlock> processBlocked) {
		Random rand = new Random();
		
		for(int i = 0; i < processBlocked.size(); i++ ) {
			
			if(rand.nextInt(10) < 3) { //unblock a process with 30% probability
				processReady.add(processBlocked.get(i));//add it to the ready list 
				processBlocked.remove(i); //and remove it from the blocked list
			}
		}
	}
	
}

