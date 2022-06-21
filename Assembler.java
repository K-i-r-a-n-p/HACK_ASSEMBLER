package assembler;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Scanner;

public class Assembler  { 
	static ArrayList<String> list=new ArrayList<String>();
	static HashMap<String,String> Sym=new HashMap<String,String>();
	
    public static ArrayList<String> text(ArrayList<String> bin) { 

        ArrayList<String> n = new ArrayList<String>();
        int b = bin.size();
        for (int i = 0; i < b ;i++) { 
            String m = No_whitespace(bin.get(i));
            if (m.length() > 0) {
                n.add(m); 
            }
        }

        return n; 
    }

    protected static String No_whitespace(String d) { 

    	 d = d.replaceAll("\\s", "");
         if((d.length() == 0)||d.charAt(1)=='/')
         	d="";
         else { 
            String[] c = d.split("//");
            d = c[0];
        }
        
    return d;
    }
    
    static class A_ins{
		static HashMap<String,Integer> lab=new HashMap<String,Integer>();
		ArrayList<String> var=new ArrayList<String>();
		
		public A_ins() {
			Sym.put("SP",Bin(0));
			Sym.put("LCL",Bin(1));
			Sym.put("ARG",Bin(2));
			Sym.put("THIS",Bin(3));
			Sym.put("THAT",Bin(4));
			Sym.put("R0",Bin(0));
			Sym.put("R1",Bin(1));
			Sym.put("R2",Bin(2));
			Sym.put("R3",Bin(3));
			Sym.put("R4",Bin(4));
			Sym.put("R5",Bin(5));
			Sym.put("R6",Bin(6));
			Sym.put("R7",Bin(7));
			Sym.put("R8",Bin(8));
			Sym.put("R9",Bin(9));
			Sym.put("R10",Bin(10));
			Sym.put("R11",Bin(11));
			Sym.put("R12",Bin(12));
			Sym.put("R13",Bin(13));
			Sym.put("R14",Bin(14));
			Sym.put("R15",Bin(15));
			Sym.put("SCREEN",Bin(16384));
			Sym.put("KBD",Bin(24576));
		}
		
		
		public String a_ins(String d) {
			d=d.substring(1);
			String fin="";
	            fin = Bin(Integer.parseInt(d)); 
	        	
	        	
			if(Sym.containsKey(d))
				fin=Sym.get(d);
			
			else if(lab.containsKey(d))
				fin=Bin(lab.get(d));
			
			else
				fin=variables(d);

			return fin;
			
		}
		public static String Bin(int d) {
			String Binary=Integer.toBinaryString(d);
			while (Binary.length() < 16)
	            Binary = "0" + Binary;
			return Binary;
		}
		
		public static void label(String d,int index) {
			d=d.substring(1,d.length()-1);
				lab.put(d,index);
		}
	
		public String variables(String d) {
			if(!var.contains(d))  
				var.add(d);
			int value = 16 + var.indexOf(d); 
            return Bin(value);
            }
		} 
	static class c_ins{
		HashMap<String,String> dest=new HashMap<String,String>();
		HashMap<String,String> comp=new HashMap<String,String>();
		HashMap<String,String> jmp=new HashMap<String,String>();
		
	public  c_ins() {
		
        dest.put("null", "000");
        dest.put("M", "001");
        dest.put("D", "010");
        dest.put("MD", "011");
        dest.put("A", "100");
        dest.put("AM", "101");
        dest.put("AD", "110");
        dest.put("AMD", "111");
        
        comp.put("0", "0101010");
        comp.put("1", "0111111");
        comp.put("-1", "0111010");
        comp.put("D", "0001100");
        comp.put("A", "0110000");
        comp.put("M", "1110000");
        comp.put("!D", "0001101");
        comp.put("!A", "0110001");
        comp.put("!M", "1110001");
        comp.put("-D", "0001111");
        comp.put("-A", "0110011");
        comp.put("-M", "1110011");
        comp.put("D+1", "0011111");
        comp.put("A+1", "0110111");
        comp.put("M+1", "1110111");
        comp.put("D-1", "0001110");
        comp.put("A-1", "0110010");
        comp.put("M-1", "1110010");
        comp.put("D+A", "0000010");
        comp.put("A+D", "0000010");
        comp.put("D+M", "1000010");
        comp.put("M+D", "1000010");
        comp.put("D-A", "0010011");
        comp.put("D-M", "1010011");
        comp.put("A-D", "0000111");
        comp.put("M-D", "1000111");
        comp.put("D&A", "0000000");
        comp.put("A&D", "0000000");
        comp.put("D&M", "1000000");
        comp.put("M&D", "1000000");
        comp.put("D|A", "0010101");
        comp.put("A|D", "0010101");
        comp.put("D|M", "1010101");
        comp.put("M|D", "1010101");
        
        jmp.put("null", "000");
        jmp.put("JGT", "001");
        jmp.put("JEQ", "010");
        jmp.put("JGE", "011");
        jmp.put("JLT", "100");
        jmp.put("JNE", "101");
        jmp.put("JLE", "110");
        jmp.put("JMP", "111");
		
	} public String cins(String d) {
		c_ins C=new c_ins();
		String Dest,Comp,Jmp;
		
		String[] sep=d.split(";");
		
		if(sep.length==1) {
			Comp=C.comp.get(sep[0]);
			Jmp=C.jmp.get("null");}
		
		else {
			Comp=C.comp.get(sep[0]);
			Jmp=C.jmp.get(sep[1]);
		}
		String[] sep1=sep[0].split("=");
		
		if(sep1.length==1) {
			Comp=C.comp.get(sep1[0]);
			Dest=C.dest.get("null");}
		
		else {
			Dest=C.dest.get(sep1[0]);
			Comp=C.comp.get(sep1[1]);
		}
	return "111"+Comp+Dest+Jmp;
	}
	}
	public static void add(String symbol, String address) {
		Sym.put(symbol, address);
	}
    
    
    public static void main(String[] args) {

    	A_ins A=new A_ins();
		c_ins C=new c_ins();
	
	try {	
	File Obj= new File("C:/Users/kiran/eclipse-workspace/Assembler/src/assembler/RectL.asm");
	Scanner myReader = new Scanner(Obj);
	 while (myReader.hasNextLine()) {
	        String line = myReader.nextLine();
	        	list.add((line)+"\n");

	 }myReader.close();}
	       catch (FileNotFoundException e) {
	         System.out.println("File not Found.");
	      } 
	list=text(list);
	
	String Final="";
	 
	Iterator<String> itr = list.iterator();
	   while (itr.hasNext()) {
	    String loan = itr.next();
	    	  if(loan.charAt(0)=='(') {
	    		  A_ins.label(loan, list.indexOf(loan));
	    		  itr.remove();}}
	    		  
	    		  
	    	  for(String l:list) {
	    		  if(l.charAt(0)=='(') {
	    			  
	    		  }
	    		  if(l.charAt(0)=='@') {
	                  Final+= A.a_ins(l)+"\n";
	    	    	  }
	    	    	  else {
	    	    		  Final += C.cins(l)+"\n"; 
	    	    	  }
	    	  }
	    	  
	
	      try {
	            File file = new File("BinaryFile.hack");
	            FileOutputStream fileWriter = new FileOutputStream(file,true);
	            PrintWriter PW = new PrintWriter(fileWriter);
	            PW.print(Final);
	            PW.close();

	            System.out.println("File written successfully");
	        }

	        catch (FileNotFoundException e) {
	            System.out.println("File not found");
	        }
	
        }

    }


	


