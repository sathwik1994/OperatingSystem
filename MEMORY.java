
public class MEMORY {
	static String memArray[]=new String[1024];
	static boolean lockArr[]=new boolean[1024];
	public static String memoryMethod(String X,int Y,String Z) {
		if(X=="READ") {
			Z = ""+memArray[Y];
			return Z;
		}
		if(X =="WRIT") {
			memArray[Y]= Z;
			return null;
		}
		if(X=="DUMP") {
			Dump();
		}
		
		return "ILLEGAL X VALUE";
		
	}

	static void Dump(){
		for(int i = 0; i < 64; i=i+8) {
			System.out.printf("%-20s %-20s %-20s %-20s %-20s %-20s %-20s %-20s %-20s \r\n",("0000".substring(0, 4-(Integer.toString(i).length()))+i),memArray[i],memArray[i+1]
					,memArray[i+2],memArray[i+3],memArray[i+4],memArray[i+5],memArray[i+6],memArray[i+7]);
			
			
		}
		
	}
}
