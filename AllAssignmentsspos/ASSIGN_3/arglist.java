//arglist.java
public class arglist {
	String argname;
	arglist(String argument) {
		// TODO Auto-generated constructor stub
		this.argname=argument;
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

//mnt.java

public class mnt {
	String name;
	int addr;
	int arg_cnt;
	mnt(String nm, int address)
	{
		this.name=nm;
		this.addr=address;
		this.arg_cnt=0;
	}
}
