import java.util.Scanner;
import java.io.*;

public class CPU {
	static int pc;
	static int CLOCK_MAX_VALUE=10000;
	static int clock=0;
	//fetch method to fetch the values from memory
	public String fetch(String st) {
		String memValue=MEMORY.memoryMethod("READ", pc,"")+MEMORY.memoryMethod("READ", pc+1,"");
		String returnvalue="";
		switch (st) {
		case "d":
			returnvalue =memValue.substring(4, 7);
			break;
		case "s":
			returnvalue = memValue.substring(7, 10);
			break;
		case "t":
			returnvalue = memValue.substring(10, 13);
			break;
		case "i6":
			returnvalue = memValue.substring(10, 16);
			break;
		case "i12":
			returnvalue =memValue.substring(4, 16);
			break;
		}
		return returnvalue;
		
	}
	//method to convert binary number to integer
	public int convertBinToInt(String st1) {
		if(st1.length()==6&st1.charAt(0)=='1') {
			st1="11111111111111111111111111"+st1;
		}
		else if(st1.charAt(0)=='1'&st1.length()==16) {
			st1="1111111111111111"+st1;
		}
		else {
			st1=st1;
		}
		return (int)Long.parseLong(st1,2);
	}
	
	//method to convert integer to Binary number
	public String convertInttoBin(int i1) {
		return Integer.toBinaryString(i1);
	}
	
	//method to convert Hexa decimal number to integer
	public int convertHexToInt(String st1) {
		return Integer.parseInt(st1,16);
	}
	
	//method to convert binary number to hexa decimal number
	public String convertBinToHex(String st1){
		return Integer.toHexString(convertBinToInt(st1));
	}
	
	//method to add zeroes so that the value in register will become 16 bits
	public String appendZeroestoReg(String st1) {
		String st=st1.length()>16?st1.substring(st1.length()-16,st1.length()):st1;
		return st.length()==16?st:"0000000000000000".substring(0, 16-st.length())+st;
	}
	
	
	//CPU(X,Y) method for processing
	public static void cpuMethod(int X,int Y) throws IOException {
		//int clock=0;
		pc = X;
		int trace = Y;
		CPU c= new CPU();
		String[] r=new String[8];
		for(int i =0;i<8;i++) {
			r[i]="0000000000000000";
		}

		//trace bit = 0. We dont print the trace file
		if(trace == 0) {
			
			BufferedWriter out = new BufferedWriter(new FileWriter("trace_file.txt"));
	         out.write(String.format("%15s %15s %15s %15s %15s %15s %15s %15s ", "PC","Instruction","R","EA","R","EA","R","EA"));
	         out.write("\n");
	         out.write("\n");
	         
	         String inst="";
		
		while(true) {
			
			//checking for infinite loop
			if(clock>CLOCK_MAX_VALUE) {
				//System.out.println("Hey Im here");
				out.close();
				ERROR_HANDLER.error_handler(6);
			}
			//System.out.println("b");
			//storing opcode into firstfour variable
			String firstfour = MEMORY.memoryMethod("READ", pc, null).substring(0, 4);
			//System.out.println("first four"+firstfour);
			
			//If Opcode equals to 0000, then we have to perform addition operation i.e., rd = rs + rt
			if(firstfour.equals("0000")) {
				clock++;
				//pc = pc +2;
				String d= c.fetch("d");
				String s= c.fetch("s");
				String t= c.fetch("t");
				int db = c.convertBinToInt(r[c.convertBinToInt(d)]);
				int sb = c.convertBinToInt(r[c.convertBinToInt(s)]);
				int tb = c.convertBinToInt(r[c.convertBinToInt(t)]);
				pc=pc+2;
				r[c.convertBinToInt(d)]=c.appendZeroestoReg(c.convertInttoBin(c.convertBinToInt(r[c.convertBinToInt(s)])+c.convertBinToInt(r[c.convertBinToInt(t)])));
				
				inst = "add"+" "+"r"+c.convertBinToInt(d)+","+"r"+c.convertBinToInt(s)+","+"r"+c.convertBinToInt(t);
				
				int da = c.convertBinToInt(r[c.convertBinToInt(d)]);
				int sa = c.convertBinToInt(r[c.convertBinToInt(s)]);
				int ta = c.convertBinToInt(r[c.convertBinToInt(t)]);
				
				
			}
			
			//If Opcode equals to 0001, then we have to perform addition operation with the immediate 6 value i.e., rd = rs + immed6

			else if(firstfour.equals("0001")) {
				//System.out.println("addi");
				clock++;
				
				String d= c.fetch("d");
				String s= c.fetch("s");
				String i6= c.fetch("i6");
				int db = c.convertBinToInt(r[c.convertBinToInt(d)]);
				int sb = c.convertBinToInt(r[c.convertBinToInt(s)]);
				//System.out.println(i6);
				int bea = c.convertBinToInt(i6);
				//System.out.println(bea);
				pc = pc +2;
				r[c.convertBinToInt(d)]=c.appendZeroestoReg(c.convertInttoBin(c.convertBinToInt(r[c.convertBinToInt(s)])+(c.convertBinToInt(i6))));
			
				inst = "addi"+" "+"r"+c.convertBinToInt(d)+","+"r"+c.convertBinToInt(s)+","+c.convertBinToInt(i6);
				int da = c.convertBinToInt(r[c.convertBinToInt(d)]);
				int sa = c.convertBinToInt(r[c.convertBinToInt(s)]);
				int aea = c.convertBinToInt(i6);
				//System.out.println(aea);
				
				
				
			}


			//If Opcode equals to 0010, then we have to perform subtraction operation i.e., rd = rs + rt

			else if(firstfour.equals("0010")) {
				clock++;
				//pc = pc +2;
				String d= c.fetch("d");
				String s= c.fetch("s");
				String t= c.fetch("t");
				int db = c.convertBinToInt(r[c.convertBinToInt(d)]);
				int sb = c.convertBinToInt(r[c.convertBinToInt(s)]);
				int tb = c.convertBinToInt(r[c.convertBinToInt(t)]);
				pc=pc+2;
				r[c.convertBinToInt(d)]=c.appendZeroestoReg(c.convertInttoBin(c.convertBinToInt(r[c.convertBinToInt(s)])-c.convertBinToInt(r[c.convertBinToInt(t)])));
	
				inst = "sub"+" "+"r"+c.convertBinToInt(d)+","+"r"+c.convertBinToInt(s)+","+"r"+c.convertBinToInt(t);

				int da = c.convertBinToInt(r[c.convertBinToInt(d)]);
				int sa = c.convertBinToInt(r[c.convertBinToInt(s)]);
				int ta = c.convertBinToInt(r[c.convertBinToInt(t)]);
				
				
				
			}

			//If Opcode equals to 0011, then we have to perform subtraction operation with immediate6 value i.e., rd = rs - immed6

			else if(firstfour.equals("0011")) {
				clock++;
				//pc = pc +2;
				String d= c.fetch("d");
				String s= c.fetch("s");
				String i6= c.fetch("i6");
				int db = c.convertBinToInt(r[c.convertBinToInt(d)]);
				int sb = c.convertBinToInt(r[c.convertBinToInt(s)]);
				int bea = c.convertBinToInt(i6);
				
				pc=pc+2;
				r[c.convertBinToInt(d)]=c.appendZeroestoReg(c.convertInttoBin(c.convertBinToInt(r[c.convertBinToInt(s)])-(c.convertBinToInt(i6))));
				inst = "subi"+" "+"r"+c.convertBinToInt(d)+","+"r"+c.convertBinToInt(s)+","+c.convertBinToInt(i6);
				
				int da = c.convertBinToInt(r[c.convertBinToInt(d)]);
				int sa = c.convertBinToInt(r[c.convertBinToInt(s)]);
				int aea = c.convertBinToInt(i6);
				
				
				
			}

			//If Opcode equals to 0100, then we have to perform trap

			else if(firstfour.equals("0100")) {
				//System.out.println("Hey");
				clock++;
				//pc = pc +2;
				String i12= c.fetch("i12");
				//System.out.println(c.convertBinToInt(i12));
				pc=pc+2;
				//if immediate12 = 0, then we have to exit
				if(c.convertBinToInt(i12)==0) {
					inst = "trap" +" "+ c.convertBinToInt(i12);
					//System.out.println(inst);
					inst = "trap" +" "+ c.convertBinToInt(i12);
					
					out.write("\n");
					out.close();
					SYSTEM.endExecTime=System.currentTimeMillis();
					SYSTEM.TermNature="NORMAL";
					SYSTEM.ClockValue= Integer.toHexString(clock);
					SYSTEM.writeOutputFile();
					
				}
				
				//if immediate12 = 1, then we have to output the values in r[1] to screen
				else if(c.convertBinToInt(i12)==1) {
					clock=clock+10;
					int br = c.convertBinToInt(r[1]);
					//System.out.println("I came here");
					System.out.println(c.convertBinToInt(r[1]));
					inst = "trap" +" "+ c.convertBinToInt(i12);
					
					int ar = c.convertBinToInt(r[1]);
					//out.close();
					//System.exit(0);
					
				}
				
				//if immediate12 = 2, then we have to take input from user and store it in r[1]
				else if(c.convertBinToInt(i12)==2) {
					clock=clock+10;
					System.out.println("Enter value:");
					int br = c.convertBinToInt(r[1]);
					
					Scanner sc = new Scanner(System.in);
					int num = sc.nextInt();
					r[1]=c.appendZeroestoReg((c.convertInttoBin(num)));
					
					inst = "trap" +" "+ c.convertBinToInt(i12);
					int ar = c.convertBinToInt(r[1]);
					
					
				}
				//if trap value is not 0/1/2, we have to throw an exception
				else {
					out.close();
					ERROR_HANDLER.error_handler(1);
				}
				
			}

			//if opcode equals to 0101, then we have to lock the memory location of immediate12
			
			else if(firstfour.equals("0101")) {
				clock++;
				//pc = pc +2;
				int i12 = c.convertBinToInt(c.fetch("i12"));
				pc=pc+2;
				MEMORY.lockArr[i12] = true;
				MEMORY.lockArr[i12+1] = true;
				inst = "lock" + i12;
				
			}

			//if opcode equals to 0110, then we have to unlock the memory location that is locked

			else if(firstfour.equals("0110")) {
				clock++;
				//pc = pc +2;
				int i12 = c.convertBinToInt(c.fetch("i12"));
				pc=pc+2;
				MEMORY.lockArr[i12] = false;
				MEMORY.lockArr[i12+1] = false;
				inst = "lock" + i12;
				
			}

			/*if opcode equals 0111, we have to perform branch equals zero operation, where we will check
			 the value of the element store in rs and increment pc value accordingly*/
			
			else if(firstfour.equals("0111") ){
				//System.out.println("beqz");
				clock++;
				
				String i6= c.fetch("i6");
				
				String s= c.fetch("s");
				
				int b = c.convertBinToInt(r[c.convertBinToInt(s)]);
				pc = pc + 2;
				//System.out.println(r[c.convertBinToInt(s)]+"\n");
				//if the value in rs is equals to 0, then pc value has to be incremented by immediate6 value
				if((r[c.convertBinToInt(s)]).equals("0000000000000000")) {
					inst = "beqz"+" "+"r"+c.convertBinToInt(s);
					//System.out.println(inst);
					int a = c.convertBinToInt(r[c.convertBinToInt(s)]);
					//System.out.println(c.convertBinToInt(i6));
					pc = pc + c.convertBinToInt(i6);	
				}
				
				//if the value in rs is not equals to 0, then pc value has to be incremented by 2
				else if(!(r[c.convertBinToInt(s)]).equals("0000000000000000")) {
					inst = "beqz"+" "+"r"+c.convertBinToInt(s);
					int a = c.convertBinToInt(r[c.convertBinToInt(s)]);
					//System.out.println(c.convertBinToInt(i6));	
				}
				//System.out.println(pc);
				
				
			}

			//if opcode equals to 1000, then we have to perform load operation. i.e., rd = MEM[Reg[rs]+immed6]:2
			else if(firstfour.equals("1000")) {
				clock++;
				//pc = pc + 2;
				String d= c.fetch("d");
				String s= c.fetch("s");
				String i6= c.fetch("i6");
				int s1 = c.convertBinToInt(r[c.convertBinToInt(s)]);
				int i61= (c.convertBinToInt(i6));
				int bd = c.convertBinToInt(r[c.convertBinToInt(d)]);
				int bs = c.convertBinToInt(r[c.convertBinToInt(s)]);
				int e1=c.convertBinToInt(i6);
				pc=pc+2;
				int e2=c.convertBinToInt(MEMORY.memArray[s1+i61])+c.convertBinToInt(MEMORY.memArray[s1+i61+1]);
				if(MEMORY.lockArr[s1+i61]==false) {
				int a1 = c.convertBinToInt(MEMORY.memArray[s1+i61])+c.convertBinToInt(MEMORY.memArray[s1+i61+1]);
				
				r[c.convertBinToInt(d)] = c.appendZeroestoReg(c.convertInttoBin(a1));}
				inst = "load"+" "+"r"+c.convertBinToInt(d)+","+c.convertBinToInt(i6)+"("+"r"+c.convertBinToInt(s)+")";
				
				int ad = c.convertBinToInt(r[c.convertBinToInt(d)]);
				int as = c.convertBinToInt(r[c.convertBinToInt(s)]);
				int e3=c.convertBinToInt(MEMORY.memArray[s1+i61])+c.convertBinToInt(MEMORY.memArray[s1+i61+1]);
				
			}

			//if opcode equals to 1001, then we have to perform store operation. i.e., MEM[Reg[rs]+immed6]:2 = rs
			else if(firstfour.equals("1001")) {
				clock++;
				//pc = pc + 2;
				String d= c.fetch("d");
				String s= c.fetch("s");
				String i6= c.fetch("i6");
				int db = c.convertBinToInt(r[c.convertBinToInt(d)]);
				int i61= (c.convertBinToInt(i6));
				pc=pc+2;

				if(MEMORY.lockArr[db+i61]==false) {
					if((db+i61)<2*LOADER.instructionSize & (db+i61)>=2*LOADER.InstStart) {
						out.close();
						ERROR_HANDLER.error_handler(2);
					}
					MEMORY.memoryMethod("WRIT", db+i61, r[c.convertBinToInt(s)].substring(0,8));
					MEMORY.memoryMethod("WRIT", db+i61+1, r[c.convertBinToInt(s)].substring(8,16));
					
				//MEMORY.memArray[db+i61] = r[c.convertBinToInt(s)].substring(0,8); 
				//MEMORY.memArray[db+i61+1] = r[c.convertBinToInt(s)].substring(8,16);
				}
				inst = "store"+" "+i61+"("+"r"+c.convertBinToInt(d)+")"+","+"r"+c.convertBinToInt(s);
				
				
			}

			//if opcode equals to 1010, then we have to perform move operation.
			else if(firstfour.equals("1010")) {
				//System.out.println("move");
				clock++;
				//pc = pc + 2;
				String s= c.fetch("s");
				String d= c.fetch("d");
				String t= c.fetch("t");
				int b1=c.convertBinToInt(r[c.convertBinToInt(d)]);
				int b2=c.convertBinToInt(r[c.convertBinToInt(s)]);
				
				pc=pc+2;
				if(c.convertBinToInt(t)!=0) {
					out.close();
					ERROR_HANDLER.error_handler(1);
				}

				r[c.convertBinToInt(d)] = r[c.convertBinToInt(s)];
				inst = "move"+" "+ "r"+c.convertBinToInt(d)+","+"r"+c.convertBinToInt(s);
				int a1=c.convertBinToInt(r[c.convertBinToInt(d)]);
				int a2=c.convertBinToInt(r[c.convertBinToInt(s)]);
				
				
			}


			//if opcode equals to 1011, then we have to perform movei operation

			else if(firstfour.equals("1011")) {
				//System.out.println("movei");
				clock++;
				
				String i6=c.fetch("i6");
				String d= c.fetch("d");
				String s = c.fetch("s");
				//System.out.println(s);
				int db=c.convertBinToInt(r[c.convertBinToInt(d)]);
				int bea = c.convertBinToInt(i6);
				int sb = c.convertBinToInt(r[c.convertBinToInt(s)]);
				pc = pc + 2;
				r[c.convertBinToInt(d)]=c.appendZeroestoReg(i6);
				if(c.convertBinToInt(s)!=0) {
					//System.out.println("I am here too");
					out.close();
					ERROR_HANDLER.error_handler(7);
				}
				
				inst = "movei"+" "+"r"+c.convertBinToInt(d)+","+c.convertBinToInt(i6);
				int aea = c.convertBinToInt(i6);
				int da = c.convertBinToInt(r[c.convertBinToInt(d)]);
				int sa = c.convertBinToInt(r[c.convertBinToInt(s)]);
				
			}

			//if opcode equals to 1100, then we have to perform branch not equals to zero operation

			else if(firstfour.equals("1100")) {
				clock++;
				//pc = pc + 2;
				
				String i6= c.fetch("i6");
				String s= c.fetch("s");
				int b = c.convertBinToInt(r[c.convertBinToInt(s)]);
				pc=pc+2;
				if(c.convertBinToInt(r[c.convertBinToInt(s)]) != 0) {
					inst = "bnez"+" "+"r"+c.convertBinToInt(s);
					
					int a = c.convertBinToInt(r[c.convertBinToInt(s)]);
					
					pc = pc + c.convertBinToInt(i6);	
					
				}
				else if((c.convertBinToInt(r[c.convertBinToInt(s)]) == 0)) {
					
					inst = "bnez"+" "+"r"+c.convertBinToInt(s);
					
					int a = c.convertBinToInt(r[c.convertBinToInt(s)]);
						
					
				}
				
			}

			//if opcode equals to 11011, then we have to set the value of rd if rs equals to rt
			else if(firstfour.equals("1101")) {
				//System.out.println("seq");
				clock++;
				
				String d= c.fetch("d");
				String s= c.fetch("s");
				String t= c.fetch("t");
				int b1 = c.convertBinToInt(r[c.convertBinToInt(d)]);
				int b2 = c.convertBinToInt(r[c.convertBinToInt(s)]);
				int b3 = c.convertBinToInt(r[c.convertBinToInt(t)]);
				pc = pc + 2;
				if(c.convertBinToInt(r[c.convertBinToInt(s)]) == c.convertBinToInt(r[c.convertBinToInt(t)])) {
					//System.out.println("seq");
					r[c.convertBinToInt(d)] = "0000000000000001";
					inst = "seq"+" "+ "r"+c.convertBinToInt(d)+","+"r"+c.convertBinToInt(s)+","+"r"+c.convertBinToInt(t);
					
					int a1 = c.convertBinToInt(r[c.convertBinToInt(d)]);
					int a2 = c.convertBinToInt(r[c.convertBinToInt(s)]);
					int a3 = c.convertBinToInt(r[c.convertBinToInt(t)]);
					
				}
				else {
					//System.out.println(c.convertBinToInt(d));
					r[c.convertBinToInt(d)] = "0000000000000000";
					inst = "seq"+" "+ "r"+c.convertBinToInt(d)+","+"r"+c.convertBinToInt(s)+","+"r"+c.convertBinToInt(t);
					
					int a1 = c.convertBinToInt(r[c.convertBinToInt(d)]);
					int a2 = c.convertBinToInt(r[c.convertBinToInt(s)]);
					int a3 = c.convertBinToInt(r[c.convertBinToInt(t)]);
					
				}
				
				
				
			}

			//if opcode equals to 1110, then we have to set the value of rd if rs greater than rt

			else if(firstfour=="1110") {
				clock++;
				//pc = pc + 2;
				String d= c.fetch("d");
				String s= c.fetch("s");
				String t= c.fetch("t");
				int b1 = c.convertBinToInt(r[c.convertBinToInt(d)]);
				int b2 = c.convertBinToInt(r[c.convertBinToInt(s)]);
				int b3 = c.convertBinToInt(r[c.convertBinToInt(t)]);
				pc=pc+2;
				if(c.convertBinToInt(r[c.convertBinToInt(s)]) > c.convertBinToInt(r[c.convertBinToInt(t)])) {
					r[c.convertBinToInt(d)] = "0000000000000001";
					inst = "sgt"+" "+ "r"+c.convertBinToInt(d)+","+"r"+c.convertBinToInt(s)+","+"r"+c.convertBinToInt(t);
					//System.out.println(inst);
					int a1 = c.convertBinToInt(r[c.convertBinToInt(d)]);
					int a2 = c.convertBinToInt(r[c.convertBinToInt(s)]);
					int a3 = c.convertBinToInt(r[c.convertBinToInt(t)]);
					
				}
				
			}

			//if opcode equals to 1111, then we have to set the value on rd if rs not equals to rt

			else if(firstfour=="1111") {
				clock++;
				//pc = pc + 2;
				String d= c.fetch("d");
				String s= c.fetch("s");
				String t= c.fetch("t");
				int b1 = c.convertBinToInt(r[c.convertBinToInt(d)]);
				int b2 = c.convertBinToInt(r[c.convertBinToInt(s)]);
				int b3 = c.convertBinToInt(r[c.convertBinToInt(t)]);
				pc=pc+2;
				if(c.convertBinToInt(r[c.convertBinToInt(s)]) != c.convertBinToInt(r[c.convertBinToInt(t)])) {
					r[c.convertBinToInt(d)] = "0000000000000001";
					inst = "sne"+" "+ "r"+c.convertBinToInt(d)+","+"r"+c.convertBinToInt(s)+","+"r"+c.convertBinToInt(t);
					//System.out.println(inst);
					int a1 = c.convertBinToInt(r[c.convertBinToInt(d)]);
					int a2 = c.convertBinToInt(r[c.convertBinToInt(s)]);
					int a3 = c.convertBinToInt(r[c.convertBinToInt(t)]);
					
					
				}
				
				
			}
			
		}
	

	}
		
		//Here, the trace bit is set to 1, so we have to print the trace file
		else if(trace == 1) {
			
				 //Creating file trace_file.txt where we will be printing the tracing of the instructions
		         BufferedWriter out = new BufferedWriter(new FileWriter("trace_file.txt"));
		         out.write(String.format("%15s %15s %15s %15s %15s %15s %15s %15s ", "PC","Instruction","R","EA","R","EA","R","EA"));
		         out.write("\n");
		         out.write("\n");
		         
		         String inst="";
			
			while(true) {
				
				//checking for infinite loop
				if(clock>CLOCK_MAX_VALUE) {
					//System.out.println("Hey Im here");
					out.close();
					ERROR_HANDLER.error_handler(6);
				}
				//System.out.println("b");
				//storing opcode into firstfour variable
				String firstfour = MEMORY.memoryMethod("READ", pc, null).substring(0, 4);
				//System.out.println("first four"+firstfour);
				
				//If Opcode equals to 0000, then we have to perform addition operation i.e., rd = rs + rt
				if(firstfour.equals("0000")) {
					clock++;
					//pc = pc +2;
					String d= c.fetch("d");
					String s= c.fetch("s");
					String t= c.fetch("t");
					int db = c.convertBinToInt(r[c.convertBinToInt(d)]);
					int sb = c.convertBinToInt(r[c.convertBinToInt(s)]);
					int tb = c.convertBinToInt(r[c.convertBinToInt(t)]);
					pc=pc+2;
					r[c.convertBinToInt(d)]=c.appendZeroestoReg(c.convertInttoBin(c.convertBinToInt(r[c.convertBinToInt(s)])+c.convertBinToInt(r[c.convertBinToInt(t)])));
					
					inst = "add"+" "+"r"+c.convertBinToInt(d)+","+"r"+c.convertBinToInt(s)+","+"r"+c.convertBinToInt(t);
					
					int da = c.convertBinToInt(r[c.convertBinToInt(d)]);
					int sa = c.convertBinToInt(r[c.convertBinToInt(s)]);
					int ta = c.convertBinToInt(r[c.convertBinToInt(t)]);
					out.write(String.format("%15s %15s %15s %15s %15s %15s %15s %15s ", pc,inst,"r"+c.convertBinToInt(d),"",db,"",da,""));
					out.write("\n");
					out.write(String.format("%15s %15s %15s %15s %15s %15s %15s %15s ", "","","r"+c.convertBinToInt(s),"",sb,"",sa,""));
					out.write("\n");
					out.write(String.format("%15s %15s %15s %15s %15s %15s %15s %15s ", "","","r"+c.convertBinToInt(t),"",tb,"",ta,""));
					out.write("\n");
					
					
					
				}
				
				//If Opcode equals to 0001, then we have to perform addition operation with the immediate 6 value i.e., rd = rs + immed6

				else if(firstfour.equals("0001")) {
					//System.out.println("addi");
					clock++;
					
					String d= c.fetch("d");
					String s= c.fetch("s");
					String i6= c.fetch("i6");
					int db = c.convertBinToInt(r[c.convertBinToInt(d)]);
					int sb = c.convertBinToInt(r[c.convertBinToInt(s)]);
					//System.out.println(i6);
					int bea = c.convertBinToInt(i6);
					//System.out.println(bea);
					pc = pc +2;
					r[c.convertBinToInt(d)]=c.appendZeroestoReg(c.convertInttoBin(c.convertBinToInt(r[c.convertBinToInt(s)])+(c.convertBinToInt(i6))));
				
					inst = "addi"+" "+"r"+c.convertBinToInt(d)+","+"r"+c.convertBinToInt(s)+","+c.convertBinToInt(i6);
					int da = c.convertBinToInt(r[c.convertBinToInt(d)]);
					int sa = c.convertBinToInt(r[c.convertBinToInt(s)]);
					int aea = c.convertBinToInt(i6);
					//System.out.println(aea);
					out.write(String.format("%15s %15s %15s %15s %15s %15s %15s %15s ", pc,inst,"r"+c.convertBinToInt(d),"",db,"",da,""));
					out.write("\n");
					out.write(String.format("%15s %15s %15s %15s %15s %15s %15s %15s ", "","","r"+c.convertBinToInt(s),"",sb,"",sa,""));
					out.write("\n");
					
					
					
				}


				//If Opcode equals to 0010, then we have to perform subtraction operation i.e., rd = rs + rt

				else if(firstfour.equals("0010")) {
					clock++;
					//pc = pc +2;
					String d= c.fetch("d");
					String s= c.fetch("s");
					String t= c.fetch("t");
					int db = c.convertBinToInt(r[c.convertBinToInt(d)]);
					int sb = c.convertBinToInt(r[c.convertBinToInt(s)]);
					int tb = c.convertBinToInt(r[c.convertBinToInt(t)]);
					pc=pc+2;
					r[c.convertBinToInt(d)]=c.appendZeroestoReg(c.convertInttoBin(c.convertBinToInt(r[c.convertBinToInt(s)])-c.convertBinToInt(r[c.convertBinToInt(t)])));
		
					inst = "sub"+" "+"r"+c.convertBinToInt(d)+","+"r"+c.convertBinToInt(s)+","+"r"+c.convertBinToInt(t);

					int da = c.convertBinToInt(r[c.convertBinToInt(d)]);
					int sa = c.convertBinToInt(r[c.convertBinToInt(s)]);
					int ta = c.convertBinToInt(r[c.convertBinToInt(t)]);
					out.write(String.format("%15s %15s %15s %15s %15s %15s %15s %15s ", pc,inst,"r"+c.convertBinToInt(d),"",db,"",da,""));
					out.write("\n");
					out.write(String.format("%15s %15s %15s %15s %15s %15s %15s %15s ", "","","r"+c.convertBinToInt(s),"",sb,"",sa,""));
					out.write("\n");
					out.write(String.format("%15s %15s %15s %15s %15s %15s %15s %15s ", "","","r"+c.convertBinToInt(t),"",tb,"",ta,""));
					out.write("\n");
					
					
					
				}

				//If Opcode equals to 0011, then we have to perform subtraction operation with immediate6 value i.e., rd = rs - immed6

				else if(firstfour.equals("0011")) {
					clock++;
					//pc = pc +2;
					String d= c.fetch("d");
					String s= c.fetch("s");
					String i6= c.fetch("i6");
					int db = c.convertBinToInt(r[c.convertBinToInt(d)]);
					int sb = c.convertBinToInt(r[c.convertBinToInt(s)]);
					int bea = c.convertBinToInt(i6);
					
					pc=pc+2;
					r[c.convertBinToInt(d)]=c.appendZeroestoReg(c.convertInttoBin(c.convertBinToInt(r[c.convertBinToInt(s)])-(c.convertBinToInt(i6))));
					inst = "subi"+" "+"r"+c.convertBinToInt(d)+","+"r"+c.convertBinToInt(s)+","+c.convertBinToInt(i6);
					
					int da = c.convertBinToInt(r[c.convertBinToInt(d)]);
					int sa = c.convertBinToInt(r[c.convertBinToInt(s)]);
					int aea = c.convertBinToInt(i6);
					out.write(String.format("%15s %15s %15s %15s %15s %15s %15s %15s ", pc,inst,"r"+c.convertBinToInt(d),"",db,"",da,""));
					out.write("\n");
					out.write(String.format("%15s %15s %15s %15s %15s %15s %15s %15s ", "","","r"+c.convertBinToInt(s),"",sb,"",sa,""));
					out.write("\n");
					
					
					
				}

				//If Opcode equals to 0100, then we have to perform trap

				else if(firstfour.equals("0100")) {
					//System.out.println("Hey");
					clock++;
					//pc = pc +2;
					String i12= c.fetch("i12");
					//System.out.println(c.convertBinToInt(i12));
					pc=pc+2;
					//if immediate12 = 0, then we have to exit
					if(c.convertBinToInt(i12)==0) {
						inst = "trap" +" "+ c.convertBinToInt(i12);
						//System.out.println(inst);
						inst = "trap" +" "+ c.convertBinToInt(i12);
						out.write(String.format("%15s %15s %15s %15s %15s %15s %15s %15s ", pc,inst,"","","","","",""));
						
						out.write("\n");
						out.close();
						SYSTEM.endExecTime=System.currentTimeMillis();
						SYSTEM.TermNature="NORMAL";
						SYSTEM.ClockValue= Integer.toHexString(clock);
						//out2.write(SYSTEM.endExecTime-SYSTEM.startExecTime); 
						SYSTEM.writeOutputFile();
						
					}
					
					//if immediate12 = 1, then we have to output the values in r[1] to screen
					else if(c.convertBinToInt(i12)==1) {
						clock=clock+10;
						int br = c.convertBinToInt(r[1]);
						//System.out.println("I came here");
						System.out.println(c.convertBinToInt(r[1]));
						inst = "trap" +" "+ c.convertBinToInt(i12);
						
						int ar = c.convertBinToInt(r[1]);
						out.write(String.format("%15s %15s %15s %15s %15s %15s %15s %15s ", pc,inst,"","","","","",""));
						out.write("\n");
						//out.close();
						//System.exit(0);
						
					}
					
					//if immediate12 = 2, then we have to take input from user and store it in r[1]
					else if(c.convertBinToInt(i12)==2) {
						clock=clock+10;
						System.out.println("Enter value:");
						int br = c.convertBinToInt(r[1]);
						
						Scanner sc = new Scanner(System.in);
						int num = sc.nextInt();
						r[1]=c.appendZeroestoReg((c.convertInttoBin(num)));
						
						inst = "trap" +" "+ c.convertBinToInt(i12);
						int ar = c.convertBinToInt(r[1]);
						out.write(String.format("%15s %15s %15s %15s %15s %15s %15s %15s ", pc,inst,"","","","","",""));
						out.write("\n");
						
						
					}
					//if trap value is not 0/1/2, we have to throw an exception
					else {
						out.close();
						ERROR_HANDLER.error_handler(1);
					}
					
				}

				//if opcode equals to 0101, then we have to lock the memory location of immediate12
				
				else if(firstfour.equals("0101")) {
					clock++;
					//pc = pc +2;
					int i12 = c.convertBinToInt(c.fetch("i12"));
					pc=pc+2;
					MEMORY.lockArr[i12] = true;
					MEMORY.lockArr[i12+1] = true;
					inst = "lock" + i12;
					out.write(String.format("%15s %15s %15s %15s %15s %15s %15s %15s ", pc,inst,"","","","","",""));
					out.write("\n");
					
				}

				//if opcode equals to 0110, then we have to unlock the memory location that is locked

				else if(firstfour.equals("0110")) {
					clock++;
					//pc = pc +2;
					int i12 = c.convertBinToInt(c.fetch("i12"));
					pc=pc+2;
					MEMORY.lockArr[i12] = false;
					MEMORY.lockArr[i12+1] = false;
					inst = "lock" + i12;
					out.write(String.format("%15s %15s %15s %15s %15s %15s %15s %15s ", pc,inst,"","","","","",""));
					out.write("\n");
					
				}

				/*if opcode equals 0111, we have to perform branch equals zero operation, where we will check
				 the value of the element store in rs and increment pc value accordingly*/
				
				else if(firstfour.equals("0111") ){
					//System.out.println("beqz");
					clock++;
					
					String i6= c.fetch("i6");
					
					String s= c.fetch("s");
					
					int b = c.convertBinToInt(r[c.convertBinToInt(s)]);
					pc = pc + 2;
					//System.out.println(r[c.convertBinToInt(s)]+"\n");
					//if the value in rs is equals to 0, then pc value has to be incremented by immediate6 value
					if((r[c.convertBinToInt(s)]).equals("0000000000000000")) {
						inst = "beqz"+" "+"r"+c.convertBinToInt(s);
						//System.out.println(inst);
						int a = c.convertBinToInt(r[c.convertBinToInt(s)]);
						out.write(String.format("%15s %15s %15s %15s %15s %15s %15s %15s ", pc,inst,"r"+c.convertBinToInt(s),"",b,"",a,""));
						out.write("\n");
						//System.out.println(c.convertBinToInt(i6));
						pc = pc + c.convertBinToInt(i6);	
					}
					
					//if the value in rs is not equals to 0, then pc value has to be incremented by 2
					else if(!(r[c.convertBinToInt(s)]).equals("0000000000000000")) {
						inst = "beqz"+" "+"r"+c.convertBinToInt(s);
						int a = c.convertBinToInt(r[c.convertBinToInt(s)]);
						out.write(String.format("%15s %15s %15s %15s %15s %15s %15s %15s ", pc,inst,"r"+c.convertBinToInt(s),"",b,"",a,""));
						out.write("\n");
						//System.out.println(c.convertBinToInt(i6));	
					}
					//System.out.println(pc);
					
					
				}

				//if opcode equals to 1000, then we have to perform load operation. i.e., rd = MEM[Reg[rs]+immed6]:2
				else if(firstfour.equals("1000")) {
					clock++;
					//pc = pc + 2;
					String d= c.fetch("d");
					String s= c.fetch("s");
					String i6= c.fetch("i6");
					int s1 = c.convertBinToInt(r[c.convertBinToInt(s)]);
					int i61= (c.convertBinToInt(i6));
					int bd = c.convertBinToInt(r[c.convertBinToInt(d)]);
					int bs = c.convertBinToInt(r[c.convertBinToInt(s)]);
					int e1=c.convertBinToInt(i6);
					pc=pc+2;
					int e2=c.convertBinToInt(MEMORY.memArray[s1+i61])+c.convertBinToInt(MEMORY.memArray[s1+i61+1]);
					if(MEMORY.lockArr[s1+i61]==false) {
					int a1 = c.convertBinToInt(MEMORY.memArray[s1+i61])+c.convertBinToInt(MEMORY.memArray[s1+i61+1]);
					
					r[c.convertBinToInt(d)] = c.appendZeroestoReg(c.convertInttoBin(a1));}
					inst = "load"+" "+"r"+c.convertBinToInt(d)+","+c.convertBinToInt(i6)+"("+"r"+c.convertBinToInt(s)+")";
					
					int ad = c.convertBinToInt(r[c.convertBinToInt(d)]);
					int as = c.convertBinToInt(r[c.convertBinToInt(s)]);
					int e3=c.convertBinToInt(MEMORY.memArray[s1+i61])+c.convertBinToInt(MEMORY.memArray[s1+i61+1]);
					out.write(String.format("%15s %15s %15s %15s %15s %15s %15s %15s ", pc,inst,"r"+c.convertBinToInt(d),"",bd,"",ad,""));
					out.write("\n");
					out.write(String.format("%15s %15s %15s %15s %15s %15s %15s %15s ", "","","r"+c.convertBinToInt(s),"",bs,"",as,""));
					out.write("\n");
					out.write(String.format("%15s %15s %15s %15s %15s %15s %15s %15s ", "","","",e1,"",e2,"",e3));
					out.write("\n");
					
				}

				//if opcode equals to 1001, then we have to perform store operation. i.e., MEM[Reg[rs]+immed6]:2 = rs
				else if(firstfour.equals("1001")) {
					clock++;
					//pc = pc + 2;
					String d= c.fetch("d");
					String s= c.fetch("s");
					String i6= c.fetch("i6");
					int db = c.convertBinToInt(r[c.convertBinToInt(d)]);
					int sb = c.convertBinToInt(r[c.convertBinToInt(s)]);
					int s1 = c.convertBinToInt(r[c.convertBinToInt(s)]);
					int i61= (c.convertBinToInt(i6));
					pc=pc+2;

					int e2=c.convertBinToInt(MEMORY.memArray[db+i61]+MEMORY.memArray[db+i61+1]);
					if(MEMORY.lockArr[db+i61]==false) {
						if((db+i61)<2*LOADER.instructionSize & (db+i61)>=2*LOADER.InstStart) {
							out.close();
							ERROR_HANDLER.error_handler(2);
						}
						MEMORY.memoryMethod("WRIT", db+i61, r[c.convertBinToInt(s)].substring(0,8));
						MEMORY.memoryMethod("WRIT", db+i61+1, r[c.convertBinToInt(s)].substring(8,16));
						
					//MEMORY.memArray[db+i61] = r[c.convertBinToInt(s)].substring(0,8); 
					//MEMORY.memArray[db+i61+1] = r[c.convertBinToInt(s)].substring(8,16);
					}
					inst = "store"+" "+i61+"("+"r"+c.convertBinToInt(d)+")"+","+"r"+c.convertBinToInt(s);
					int ab = c.convertBinToInt(r[c.convertBinToInt(d)]);
					int sa = c.convertBinToInt(r[c.convertBinToInt(s)]);
					int e3=c.convertBinToInt(MEMORY.memArray[db+i61]+MEMORY.memArray[db+i61+1]);
					out.write(String.format("%15s %15s %15s %15s %15s %15s %15s %15s", pc,inst,"r"+c.convertBinToInt(d),"",db,"",ab,""));
					out.write("\n");
					out.write(String.format("%15s %15s %15s %15s %15s %15s %15s %15s", "","","r"+c.convertBinToInt(s),"",sb,"",sa,""));
					out.write("\n");
					out.write(String.format("%15s %15s %15s %15s %15s %15s %15s %15s", "","","",i61,"",e2,"",e3));
					out.write("\n");
					
					
				}

				//if opcode equals to 1010, then we have to perform move operation.
				else if(firstfour.equals("1010")) {
					//System.out.println("move");
					clock++;
					//pc = pc + 2;
					String s= c.fetch("s");
					String d= c.fetch("d");
					String t= c.fetch("t");
					int b1=c.convertBinToInt(r[c.convertBinToInt(d)]);
					int b2=c.convertBinToInt(r[c.convertBinToInt(s)]);
					
					pc=pc+2;
					if(c.convertBinToInt(t)!=0) {
						out.close();
						ERROR_HANDLER.error_handler(1);
					}

					r[c.convertBinToInt(d)] = r[c.convertBinToInt(s)];
					inst = "move"+" "+ "r"+c.convertBinToInt(d)+","+"r"+c.convertBinToInt(s);
					int a1=c.convertBinToInt(r[c.convertBinToInt(d)]);
					int a2=c.convertBinToInt(r[c.convertBinToInt(s)]);
					out.write(String.format("%15s %15s %15s %15s %15s %15s %15s %15s",pc,inst,"r"+c.convertBinToInt(d),"",b1,"",a1,""));
					out.write("\n");
					out.write(String.format("%15s %15s %15s %15s %15s %15s %15s %15s", "","","r"+c.convertBinToInt(s),"",b2,"",a2,""));
					out.write("\n");
					
					
				}


				//if opcode equals to 1011, then we have to perform movei operation

				else if(firstfour.equals("1011")) {
					//System.out.println("movei");
					clock++;
					
					String i6=c.fetch("i6");
					String d= c.fetch("d");
					String s = c.fetch("s");
					//System.out.println(s);
					int db=c.convertBinToInt(r[c.convertBinToInt(d)]);
					int bea = c.convertBinToInt(i6);
					int sb = c.convertBinToInt(r[c.convertBinToInt(s)]);
					pc = pc + 2;
					r[c.convertBinToInt(d)]=c.appendZeroestoReg(i6);
					if(c.convertBinToInt(s)!=0) {
						//System.out.println("I am here too");
						out.close();
						ERROR_HANDLER.error_handler(7);
					}
					
					inst = "movei"+" "+"r"+c.convertBinToInt(d)+","+c.convertBinToInt(i6);
					int aea = c.convertBinToInt(i6);
					int da = c.convertBinToInt(r[c.convertBinToInt(d)]);
					int sa = c.convertBinToInt(r[c.convertBinToInt(s)]);
					out.write(String.format("%15s %15s %15s %15s %15s %15s %15s %15s ", pc,inst,"r"+c.convertBinToInt(d),"",db,"",da,""));
					out.write("\n");
					out.write(String.format("%15s %15s %15s %15s %15s %15s %15s %15s ", "","","r"+c.convertBinToInt(s),"",sb,"",sa,""));
					out.write("\n");
					out.write(String.format("%15s %15s %15s %15s %15s %15s %15s %15s ", "","","",c.convertBinToInt(i6),"",bea,"",aea));
					out.write("\n");
					
				}

				//if opcode equals to 1100, then we have to perform branch not equals to zero operation

				else if(firstfour.equals("1100")) {
					clock++;
					//pc = pc + 2;
					
					String i6= c.fetch("i6");
					String s= c.fetch("s");
					int b = c.convertBinToInt(r[c.convertBinToInt(s)]);
					pc=pc+2;
					if(c.convertBinToInt(r[c.convertBinToInt(s)]) != 0) {
						inst = "bnez"+" "+"r"+c.convertBinToInt(s);
						
						int a = c.convertBinToInt(r[c.convertBinToInt(s)]);
						out.write(String.format("%15s %15s %15s %15s %15s %15s %15s %15s ", pc,inst,"r"+c.convertBinToInt(s),"",b,"",a,""));
						
						pc = pc + c.convertBinToInt(i6);	
						
					}
					else if((c.convertBinToInt(r[c.convertBinToInt(s)]) == 0)) {
						
						inst = "bnez"+" "+"r"+c.convertBinToInt(s);
						
						int a = c.convertBinToInt(r[c.convertBinToInt(s)]);
						out.write(String.format("%15s %15s %15s %15s %15s %15s %15s %15s ", pc,inst,"r"+c.convertBinToInt(s),"",b,"",a,""));
						out.write("\n");
							
						
					}
					
				}
 
				//if opcode equals to 11011, then we have to set the value of rd if rs equals to rt
				else if(firstfour.equals("1101")) {
					//System.out.println("seq");
					clock++;
					
					String d= c.fetch("d");
					String s= c.fetch("s");
					String t= c.fetch("t");
					int b1 = c.convertBinToInt(r[c.convertBinToInt(d)]);
					int b2 = c.convertBinToInt(r[c.convertBinToInt(s)]);
					int b3 = c.convertBinToInt(r[c.convertBinToInt(t)]);
					pc = pc + 2;
					if(c.convertBinToInt(r[c.convertBinToInt(s)]) == c.convertBinToInt(r[c.convertBinToInt(t)])) {
						//System.out.println("seq");
						r[c.convertBinToInt(d)] = "0000000000000001";
						inst = "seq"+" "+ "r"+c.convertBinToInt(d)+","+"r"+c.convertBinToInt(s)+","+"r"+c.convertBinToInt(t);
						
						int a1 = c.convertBinToInt(r[c.convertBinToInt(d)]);
						int a2 = c.convertBinToInt(r[c.convertBinToInt(s)]);
						int a3 = c.convertBinToInt(r[c.convertBinToInt(t)]);
						out.write(String.format("%15s %15s %15s %15s %15s %15s %15s %15s ", pc,inst,"r"+c.convertBinToInt(d),"",b1,"",a1,""));
						out.write("\n");
						out.write(String.format("%15s %15s %15s %15s %15s %15s %15s %15s ", "","","r"+c.convertBinToInt(s),"",b2,"",a2,""));
						out.write("\n");
						out.write(String.format("%15s %15s %15s %15s %15s %15s %15s %15s ", "","","r"+c.convertBinToInt(t),"",b3,"",a3,""));
						out.write("\n");
						
					}
					else {
						//System.out.println(c.convertBinToInt(d));
						r[c.convertBinToInt(d)] = "0000000000000000";
						inst = "seq"+" "+ "r"+c.convertBinToInt(d)+","+"r"+c.convertBinToInt(s)+","+"r"+c.convertBinToInt(t);
						
						int a1 = c.convertBinToInt(r[c.convertBinToInt(d)]);
						int a2 = c.convertBinToInt(r[c.convertBinToInt(s)]);
						int a3 = c.convertBinToInt(r[c.convertBinToInt(t)]);
						out.write(String.format("%15s %15s %15s %15s %15s %15s %15s %15s ", pc,inst,"r"+c.convertBinToInt(d),"",b1,"",a1,""));
						out.write("\n");
						out.write(String.format("%15s %15s %15s %15s %15s %15s %15s %15s ", "","","r"+c.convertBinToInt(s),"",b2,"",a2,""));
						out.write("\n");
						out.write(String.format("%15s %15s %15s %15s %15s %15s %15s %15s ", "","","r"+c.convertBinToInt(t),"",b3,"",a3,""));
						out.write("\n");
						
					}
					
					
					
				}

				//if opcode equals to 1110, then we have to set the value of rd if rs greater than rt

				else if(firstfour=="1110") {
					clock++;
					//pc = pc + 2;
					String d= c.fetch("d");
					String s= c.fetch("s");
					String t= c.fetch("t");
					int b1 = c.convertBinToInt(r[c.convertBinToInt(d)]);
					int b2 = c.convertBinToInt(r[c.convertBinToInt(s)]);
					int b3 = c.convertBinToInt(r[c.convertBinToInt(t)]);
					pc=pc+2;
					if(c.convertBinToInt(r[c.convertBinToInt(s)]) > c.convertBinToInt(r[c.convertBinToInt(t)])) {
						r[c.convertBinToInt(d)] = "0000000000000001";
						inst = "sgt"+" "+ "r"+c.convertBinToInt(d)+","+"r"+c.convertBinToInt(s)+","+"r"+c.convertBinToInt(t);
						//System.out.println(inst);
						int a1 = c.convertBinToInt(r[c.convertBinToInt(d)]);
						int a2 = c.convertBinToInt(r[c.convertBinToInt(s)]);
						int a3 = c.convertBinToInt(r[c.convertBinToInt(t)]);
						out.write(String.format("%15s %15s %15s %15s %15s %15s %15s %15s ", pc,inst,"r"+c.convertBinToInt(d),"",b1,"",a1,""));
						out.write(String.format("%15s %15s %15s %15s %15s %15s %15s %15s ", "","","r"+c.convertBinToInt(s),"",b2,"",a2,""));
						out.write(String.format("%15s %15s %15s %15s %15s %15s %15s %15s ", "","","r"+c.convertBinToInt(t),"",b3,"",a3,""));
						
					}
					
				}

				//if opcode equals to 1111, then we have to set the value on rd if rs not equals to rt

				else if(firstfour=="1111") {
					clock++;
					//pc = pc + 2;
					String d= c.fetch("d");
					String s= c.fetch("s");
					String t= c.fetch("t");
					int b1 = c.convertBinToInt(r[c.convertBinToInt(d)]);
					int b2 = c.convertBinToInt(r[c.convertBinToInt(s)]);
					int b3 = c.convertBinToInt(r[c.convertBinToInt(t)]);
					pc=pc+2;
					if(c.convertBinToInt(r[c.convertBinToInt(s)]) != c.convertBinToInt(r[c.convertBinToInt(t)])) {
						r[c.convertBinToInt(d)] = "0000000000000001";
						inst = "sne"+" "+ "r"+c.convertBinToInt(d)+","+"r"+c.convertBinToInt(s)+","+"r"+c.convertBinToInt(t);
						
						int a1 = c.convertBinToInt(r[c.convertBinToInt(d)]);
						int a2 = c.convertBinToInt(r[c.convertBinToInt(s)]);
						int a3 = c.convertBinToInt(r[c.convertBinToInt(t)]);
						out.write(String.format("%15s %15s %15s %15s %15s %15s %15s %15s ", pc,inst,"r"+c.convertBinToInt(d),"",b1,"",a1,""));
						out.write(String.format("%15s %15s %15s %15s %15s %15s %15s %15s ", "","","r"+c.convertBinToInt(s),"",b2,"",a2,""));
						out.write(String.format("%15s %15s %15s %15s %15s %15s %15s %15s ", "","","r"+c.convertBinToInt(t),"",b3,"",a3,""));
						
						
					}
					
					
				}
				
			}
		
		}
		else {
			
			ERROR_HANDLER.error_handler(10);
		}
			
}
	

}
