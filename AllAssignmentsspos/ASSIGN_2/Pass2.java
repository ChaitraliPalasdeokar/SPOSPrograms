import java.io.*;
import java.nio.channels.SeekableByteChannel;
import java.nio.file.Files;
import java.util.*;


import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

class data{
public 	String seq;
public 	String value;
public 	String addr;
	
}

public class Pass2  {
	
	
	static String lc;
	static int reg;
			
	public static void main(String[] args)throws Exception
	{
		
	File ic = new File("/home/ccoew/3908/Pass2/ic.txt");
	
	BufferedReader br1 = new BufferedReader(new FileReader(ic));
	
	File sym = new File("/home/ccoew/3908/Pass2/sym.txt");
	
	BufferedReader br2 = new BufferedReader(new FileReader(sym));
	
	File lit = new File("/home/ccoew/3908/Pass2/lit.txt");
	
	BufferedReader br3 = new BufferedReader(new FileReader(lit));
	
	File pool = new File("/home/ccoew/3908/Pass2/pool.txt");
	
	BufferedReader br4 = new BufferedReader(new FileReader(pool));
	String str1;
	

	File tc1=new File("/home/ccoew/3908/Pass2/tc.txt");
	
	if(tc1.exists()){
		tc1.delete();
	}
	File tc=new File("/home/ccoew/3908/Pass2/tc.txt");
	
	
	FileWriter fw=new FileWriter(tc);
	int cnt=0;

	//--------------------------------DATA STRUCTURES-------------------------------
	String str=new String();
	
	//--------------------------------literals----------------------
	ArrayList<data>l=new ArrayList<data>();
	while((str=br3.readLine())!=null)
	{
		StringTokenizer st=new StringTokenizer(str," ");
		data a=new data();
		a.seq=st.nextToken();
		a.value=st.nextToken();
		a.addr=st.nextToken();
		l.add(a);
	}
	br3.close();
	//--------------------------------------symbols---------------------------
	
	ArrayList<data>s=new ArrayList<data>();
	while((str=br2.readLine())!=null)
	{
		StringTokenizer st=new StringTokenizer(str," ");
		data a=new data();
		a.seq=st.nextToken();
		a.value=st.nextToken();
		a.addr=st.nextToken();
		s.add(a);
	}
	br2.close();
	//---------------------------LOOP------------------------------------
	
	str1=br1.readLine();
	while((str1=br1.readLine())!=null)
	{
		StringTokenizer st=new StringTokenizer(str1," ,()");
		//System.out.println(st.nextToken());
		String arr[]=new String[st.countTokens()];
		for(int i=0;i<arr.length;i++)
		{
			arr[i]=st.nextToken();
			
		}
		
		
		if(arr.length==6)
		{
		
			String ad=new String();
			
			lc=arr[0];
			
			for(int i=0;i<l.size();i++)
			{
				if(l.get(i).seq.equals(arr[5]))
				{
					ad=l.get(i).addr;
					break;
				}
			}
			String r=arr[3];
			switch(r)
			{
			case "AREG":reg=1;
				break;
			case "BREG":reg=2;
				break;
			case "CREG":reg=3;
				break;
			case "DREG":reg=4;
				break;
			}
			fw.write(lc+" "+arr[2]+" "+reg+" "+ad+"\n");
			
		}
		else if(arr.length==5)
		{
			
			String ad=new String();
			
			lc=arr[0];
			for(int i=0;i<s.size();i++)
			{
				if(s.get(i).value.equals(arr[4]))
				{
					ad=s.get(i).addr;
					break;
				}
			}
			String r=arr[3];
			switch(r)
			{
			case "AREG":reg=1;
				break;
			case "BREG":reg=2;
				break;
			case "CREG":reg=3;
				break;
			case "DREG":reg=4;
				break;
			}
			fw.write(lc+" "+arr[2]+" "+reg+" "+ad+"\n");
		}
		else if(arr.length==4)
		{
			lc=arr[0];
			fw.write(lc+"\n");
		}
		else if(arr.length==3)
		{
			if(arr[2].equals("00"))
			{
				fw.write(arr[0]+" "+arr[2]+"\n");
			}
			else
			{
				fw.write("\n");
			}
		}
		else if(arr.length==2)
		{
			if(arr[1].equals("05")||(arr[1].equals("02"))||(arr[1].equals("04")))
			fw.write("\n");
			else
			{
				fw.write(arr[0]+"    "+arr[1]+"\n");
			}
		}
		
				
	}
	
	fw.close();
	}	
}
