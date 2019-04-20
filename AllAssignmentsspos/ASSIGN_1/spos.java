//spos.java
import java.io.*;
import java.util.*;
class spos 
{
	public static void main(String args[]) throws NullPointerException, FileNotFoundException
	{
	 String REG[] = {"ax","bx","cx","dx"};
	 String IS[]={"mover","movem","add","sub","div","mul"};
	 String DL[]={"ds","dc"};
	 obj[] literal_table = new obj[10];
	 obj[] symb_table = new obj[10];
	 obj[] optab =new obj[30];
	 pooltable[] pooltab=new pooltable[5];
	 String line; 
	 try{
		 BufferedReader br=new BufferedReader(new FileReader("sample.txt"));
		 BufferedWriter bw=new BufferedWriter(new FileWriter("Output1.txt"));
		 
		 Boolean start=false;
		 Boolean end=false,fill_addr=false,ltorg=false;
	
	     int total_symb=0,total_ltr=0,optab_cnt=0,pooltab_cnt=0,loc=0,temp,pos;
	     
	     while((line=br.readLine())!=null&&!end)
	     {
	    	 String tokens[]=line.split(" ",4);
	    	 if(loc!=0 && !ltorg)
	    	 {
	    		 bw.write("\n"+String.valueOf(loc));
	    		 ltorg=false;
	    		 loc++;
	    	 }
	    	 ltorg=fill_addr=false;
	    	 for(int k=0;k<tokens.length;k++)
	    	 {
	    		 pos = -1;
	    		 if(start==true)
	    		 {
	    			 loc=Integer.parseInt(tokens[k]);
	    			 start=false;
	    		 }
	    		 
    			 switch(tokens[k])
				 {
    				 case "start" : start = true;
    				 				pos = 1;
				    				bw.write("\t(AD,"+pos+")");
		    				 		break;
    				 case "end": end=true;
    				 				pos = 2;
		    				 bw.write("\t(AD,"+pos+")");
    				 		 break;
    				 		 
    				 case "origin": pos = 3; 
    					 	 bw.write("\t(AD,"+pos+")");
    				 		 pos= search(tokens[++k],symb_table,total_symb);
    				 		 k++;
	    					 bw.write("\t(S,"+(pos+1)+")");
	    					 loc = symb_table[pos].addr;
	    					 break;
    					 
    				 case "ltorg": ltorg=true;
    				 				pos = 4;
    					 	 for(temp=0;temp<total_ltr;temp++)
	    						 if(literal_table[temp].addr==0)
	    						 {
	    							 literal_table[temp].addr=loc-1;
	    		    				 bw.write("\t(DL,1)\t(L,"+(temp+1)+")"+"\n"+loc++);
	    						 }
	    					 if(pooltab_cnt==0)
	    						 pooltab[pooltab_cnt++]=new pooltable(0,temp);
	    					 else
	    					 {
	    						 pooltab[pooltab_cnt]=new pooltable(pooltab[pooltab_cnt-1].first+pooltab[pooltab_cnt-1].total_literals,total_ltr-pooltab[pooltab_cnt-1].first-1);
	    						 pooltab_cnt++;
	    					 } 
	    					 break;
	    					 
    				 case "equ": pos = 5; 
    					 	 bw.write("\t(AD,"+pos+")");
    					 	 String prev_token=tokens[k-1];
    					 	 int pos1 = search(prev_token,symb_table,total_symb);
    				 		 pos = search(tokens[++k],symb_table,total_symb);
    				 		 symb_table[pos1].addr = symb_table[pos].addr;
	    					 bw.write("\t(S,"+(pos+1)+")");
	    					 break;
    			
				 }
    			 if(pos == -1)
	    		 {
	    			 pos=search(tokens[k], IS);
	    			 if(pos != -1)
	    			 {
        				 bw.write("\t(IS,"+(pos+1)+")");
        				 optab[optab_cnt++]=new obj(tokens[k], pos);
	    			 }
	    			 else
			    	 {
			    		 pos=search(tokens[k], DL);
		    			 if(pos != -1)
		    			 {
		    				 bw.write("\t(DL,"+(pos+1)+")");
	        				 optab[optab_cnt++]=new obj(tokens[k], pos);
		    				 fill_addr=true;
		    			 }
		    			 else if(tokens[k].matches("[a-zA-Z]+:"))
		    			 {
		    				 pos = search(tokens[k], symb_table,total_symb);
		    				 if(pos == -1)
		    				 {
		    					 symb_table[total_symb++]=new obj(tokens[k].substring(0,tokens[k].length()-1),loc-1);
		    					 bw.write("\t(S,"+total_symb+")");
		    					 pos=total_symb;
		    				 }
		    			 }
			    	 }
	    		 }
	    		 if(pos == -1) 
	    		 {
	    			 pos=search(tokens[k], REG);
	    			 if(pos!=-1)
	    				 bw.write("\t("+(pos+1)+")");
	    			 else
		    		 {
		    			 if(tokens[k].matches("='\\d+'"))
		    			 {
			    			 literal_table[total_ltr++]=new obj(tokens[k], 0);
		    				 bw.write("\t(L,"+total_ltr+")");
			    		 }
		    			 else if(tokens[k].matches("\\d+")||tokens[k].matches("\\d+H")||tokens[k].matches("\\d+h")||tokens[k].matches("\\d+D")||tokens[k].matches("\\d+d"))
	        				 bw.write("\t(C,"+tokens[k]+")");
		    			 else
		    			 {
		    				 pos = search(tokens[k], symb_table,total_symb);
		    				 if(fill_addr && pos!=-1)
							 {
								 symb_table[pos].addr=loc-1;
								 fill_addr=false;
							 }
		    				 else if(pos==-1)
		    				 {
		    					 symb_table[total_symb++]=new obj(tokens[k],0);
		    					 bw.write("\t(S," + total_symb + ")");
		    				 }
		    				 else
		    					 bw.write("\t(S," + pos + ")");
		    			 }
		    		 }
	    		 }
	    	 }
	     }
	     System.out.println("\n*************************************SYMBOL TABLE*************************************");
	     System.out.println("\nSYMBOL\tADDRESS");
	     for(int i=0;i<total_symb;i++)
	    	 System.out.println(symb_table[i].name+"\t"+symb_table[i].addr);
	     
	     pooltab[pooltab_cnt]=new pooltable(pooltab[pooltab_cnt-1].first+pooltab[pooltab_cnt-1].total_literals,total_ltr-pooltab[pooltab_cnt-1].first-2);
		 pooltab_cnt++;
		 
	     System.out.println("\n*************************************POOL TABLE*************************************");
	     System.out.println("\nPOOL\tTOTAL LITERALS");
	     
	     for(int i=0;i<pooltab_cnt;i++)
	    	 System.out.println(pooltab[i].first+"\t"+pooltab[i].total_literals);
	     
	     System.out.println("\n*************************************LITERAL TABLE*************************************");
	     System.out.println("\nIndex\tLITERAL\tADDRESS");
	     for(int i=0;i<total_ltr;i++)
	     {
	    	 if(literal_table[i].addr==0)
	    		 literal_table[i].addr=loc++;
	    	 System.out.println((i+1) +"\t"+literal_table[i].name+"\t"+literal_table[i].addr);
	     }
	     
	     System.out.println("\n*************************************OPTABLE*************************************");
	     System.out.println("\nMNEMONIC\tOPCODE");
	     for(int i=0;i<optab_cnt;i++)
	    	 System.out.println(optab[i].name+"\t\t"+optab[i].addr);
	     
	     br.close();
	     bw.close();
	 }
	 catch(Exception e)
	 {
	  System.out.println("error while reading the file");
	  e.printStackTrace();
	 }
	 BufferedReader br=new BufferedReader(new FileReader("Output1.txt"));
	 System.out.println("\n***********************Output1.txt***********************\n");
	 try {
		while((line=br.readLine())!=null)
			 System.out.println(line);
		br.close();
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}

	} 
	public static int search(String token, String[] list) {
		for(int i=0;i<list.length;i++)
			if(token.equalsIgnoreCase(list[i]))
				return i;
		return -1;
	}
	public static int search(String token, obj[] list,int cnt) {
			for(int i=0;i<cnt;i++)
				if(token.equalsIgnoreCase(list[i].name))
					return i;
		return -1;
	}
}  

//pooltable.java

public class pooltable {
int first,total_literals;
public pooltable(int f, int l) {
	// TODO Auto-generated constructor stub
	this.first=f;
	this.total_literals=l;
}
}
 
//obj.java
public class obj {
	String name;
	int addr;
	obj(String nm, int address)
	{
		this.name=nm;
		this.addr=address;
	}
}

//input.txt
start 100
movr ax 05
mover bx 10
up: add ax bx
movem a ='5'
mul ax a
origin up 
ltorg
movem b ='8'
movem c ='8'
ltorg
movem b ='7'
movem c ='8'
ds a 02
dc b 10
ds a 09
next equ up
end


/*output:
root@nikita-Inspiron-5567:/home/nikita# javac spos.java
root@nikita-Inspiron-5567:/home/nikita# java spos

*************************************SYMBOL TABLE*************************************

SYMBOL	ADDRESS
up	102
a	111
b	110
c	0
next	102

*************************************POOL TABLE*************************************

POOL	TOTAL LITERALS
0	1
1	2
3	2

*************************************LITERAL TABLE*************************************

Index	LITERAL	ADDRESS
1	='5'	102
2	='8'	105
3	='8'	106
4	='7'	114
5	='8'	115

*************************************OPTABLE*************************************

MNEMONIC	OPCODE
mover		0
mover		0
add		2
movem		1
mul		5
movem		1
movem		1
movem		1
movem		1
ds		0
dc		1
ds		0

***********************Output1.txt***********************

	(AD,1)	(C,100)
100	(IS,1)	(1)	(C,05)
101	(IS,1)	(2)	(C,10)
102	(S,1)	(IS,3)	(1)	(2)
103	(IS,2)	(S,2)	(L,1)
104	(IS,6)	(1)	(S,1)
105	(AD,3)	(S,1)
102	(DL,1)	(L,1)
103	(IS,2)	(S,3)	(L,2)
104	(IS,2)	(S,4)	(L,3)
105	(DL,1)	(L,2)
106	(DL,1)	(L,3)
107	(IS,2)	(S,2)	(L,4)
108	(IS,2)	(S,3)	(L,5)
109	(DL,1)	(C,02)
110	(DL,2)	(C,10)
111	(DL,1)	(C,09)
112	(S,5)	(AD,5)	(S,1)
113	(AD,2)

*/