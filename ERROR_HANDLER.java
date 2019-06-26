import java.io.IOException;

public class ERROR_HANDLER {
		public static void error_handler(int x) throws IOException
		{
			int err = x;
			if (err == 1) {
				//error handling for illegal Instruction Exception 
				System.out.println("error 1: Illegal Instruction \n\n");
				SYSTEM.endExecTime=System.currentTimeMillis();
				SYSTEM.TermNature="ABNORMAL";
				SYSTEM.ErrorMessage="Illegal Instruction";
				SYSTEM.ClockValue= Integer.toHexString(CPU.clock);
				SYSTEM.writeOutputFile();
				
			}
			if (err == 2) {
				//error handling for Memory Range Fault Exception 
				System.out.println("error 2: Memory Range fault");
				SYSTEM.endExecTime=System.currentTimeMillis();
				SYSTEM.TermNature="ABNORMAL";
				SYSTEM.ErrorMessage="Memory Range fault";
				SYSTEM.ClockValue= Integer.toHexString(CPU.clock);
				SYSTEM.writeOutputFile();
				
			}
			if (err == 3) {
				//error handling for Program size too large exception 
				System.out.println("error 3: Program size too large");
				SYSTEM.endExecTime=System.currentTimeMillis();
				SYSTEM.TermNature="ABNORMAL";
				SYSTEM.ErrorMessage= "Program size too large";
				SYSTEM.ClockValue= Integer.toHexString(CPU.clock);
				SYSTEM.writeOutputFile();
				
			}
			if (err == 4) {
				//error handling for Alloc large data exception 
				System.out.println("error 4: Alloc large data error");
				SYSTEM.endExecTime=System.currentTimeMillis();
				SYSTEM.TermNature="ABNORMAL";
				SYSTEM.ErrorMessage="Alloc large data error";
				SYSTEM.ClockValue= Integer.toHexString(CPU.clock);
				SYSTEM.writeOutputFile();
				//System.exit(0);
			}
			if(err == 10) {
				//error handling for invalid trace bit
				System.out.println("error 10: Invalid Trace bit");
				SYSTEM.endExecTime=System.currentTimeMillis();
				SYSTEM.TermNature="ABNORMAL";
				SYSTEM.ErrorMessage="Invalid Trace bit";
				SYSTEM.ClockValue= Integer.toHexString(CPU.clock);
				SYSTEM.writeOutputFile();
				
			}
			if(err == 6) {
				//error handling for infinite loop
				System.out.println("error 6: Infinite loop");
				SYSTEM.endExecTime=System.currentTimeMillis();
				SYSTEM.TermNature="ABNORMAL";
				SYSTEM.ErrorMessage=" Infinite loop";
				SYSTEM.ClockValue= Integer.toHexString(CPU.clock);
				SYSTEM.writeOutputFile();
				
			}
			if(err == 7) {
				//error handling for Immediate value is too large exception
				System.out.println("error 7: Immediate value is too large");
				SYSTEM.endExecTime=System.currentTimeMillis();
				SYSTEM.TermNature="ABNORMAL";
				SYSTEM.ErrorMessage="Immediate value is too large";
				SYSTEM.ClockValue= Integer.toHexString(CPU.clock);
				SYSTEM.writeOutputFile();
				
			}
			
		}
}
