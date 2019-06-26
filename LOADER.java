import java.io.*;
import java.util.*;

public class LOADER {
	static int instructionSize;
	static int PC,InstStart;
	static int trace;
	//Method to stores the instructions into memory that starts from memory[x]
	public void loader(int x) throws IOException {
		
		SYSTEM sys = new SYSTEM();
		File file = new File(sys.filename); 
		BufferedReader br = new BufferedReader(new FileReader(file));
		String st1=(br.readLine());
		//splitting the instructions size and pc value by delimiter ' '
	    for(int i =0; i<st1.length();i++) {
	    	if(st1.charAt(i) == ' ') {
	    		instructionSize= Integer.parseInt(st1.substring(0,i),16);
	    		InstStart = Integer.parseInt(st1.substring(i+1,st1.length()),16);
	    	}
	    }
	    MEMORY m= new MEMORY();
	    PC=InstStart;
	    if(2*instructionSize>1024) {
	    	ERROR_HANDLER.error_handler(3);
	    }
		List<String> l= new ArrayList<String>();
		String s;
	while((s=br.readLine()) != null) {
		l.add(s);
	}
	String st2="";
	for(int i=0;i<l.size()-1;i++) {
		st2+=l.get(i);
	}
	//reading last 3 digits as trace bit
	trace=Integer.parseInt(l.get(l.size()-1),16);
	br.close();
	//loading the instructions into memory
	
	for(int i =0; i<instructionSize*4; i=i+2) {
		int binary = Integer.parseInt(st2.substring(i,i+2),16);
		
		MEMORY.memArray[sys.startAdd] = Integer.toBinaryString(binary);
		sys.startAdd++;
		//System.out.println(MEMORY.memArray[j]);
	}
	
	//Initializing all the memery elements as 8 bit binaries
	for(int i =0;i<instructionSize*2;i++) {
		//System.out.println(mem.memArray[i]);
		if(MEMORY.memArray[i].length() < 8) {
			MEMORY.memArray[i]="00000000".substring(0,8-MEMORY.memArray[i].length() )+MEMORY.memArray[i];
			
		}
	
	}
	
    //m.Dump();

	
}
			
}
