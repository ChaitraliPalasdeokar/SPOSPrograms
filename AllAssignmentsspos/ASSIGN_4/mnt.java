//mnt.java
public class mnt {
	String name;
	int addr;
	int arg_cnt;
	mnt(String nm, int address,int total_arg)
	{
		this.name=nm;
		this.addr=address;
		this.arg_cnt=total_arg;
	}
}

//mdt.java

public class mdt {
String stmnt;
public mdt() {
	// TODO Auto-generated constructor stub
	stmnt="";
}
}

//arglist.java

public class arglist {
	String argname,value;
	arglist(String argument) {
		// TODO Auto-generated constructor stub
		this.argname=argument;
		this.value="";
	}
}
