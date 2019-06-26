import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;

public class SYSTEM {
	static String filename;
	static int startAdd;
	static long startExecTime, endExecTime;
	static long startioTime, endioTime;
	static String TermNature,ErrorMessage;
	static String ClockValue;
	static int job=1;	
	static void writeOutputFile() throws IOException {
		Writer out2 = new BufferedWriter(new FileWriter(new File("output.txt")));
		out2.write("JOB IDENTIFICATION NUMBER:"+job+"\n");
		out2.write("\n");
		out2.write("The program nature of Termination: "+TermNature+"\n");
		out2.write("\n");
		out2.write("The Error Message is "+ErrorMessage+"\n");
		out2.write("\n");
		out2.write("Execution Time:"+(endExecTime-startExecTime)+"ms\n");
		out2.write("\n");
		out2.write("ioTime:"+(endioTime-startioTime)+"ms");
		out2.write("\n");
		out2.close();
		System.exit(0);
	}
	public static void main(String[] args) throws IOException {
	    //Entering arguments for program 
		
		
		
		filename = args[0];
	    startAdd = Integer.parseInt(args[1]);
		//calling the LOADER class for loading the job file 
	    startioTime=System.currentTimeMillis();
		LOADER ld = new LOADER();
		endioTime=System.currentTimeMillis();
		//out2.write
		//0 is the start index of thee memory as specified in the requirement
		ld.loader(startAdd);
		//Calling the CPU class with PC value and trace bit
		startExecTime=System.currentTimeMillis();
		//CPU cp = new CPU();
		
		
		CPU.cpuMethod(LOADER.PC, LOADER.trace);
		
		
		
		
	}

}
