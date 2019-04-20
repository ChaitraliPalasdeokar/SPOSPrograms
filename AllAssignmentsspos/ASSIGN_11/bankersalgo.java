import java.util.Scanner; 
public class bankersalgo{
private int need[][],allocate[][],claim[][],avail[],np,nr,r[];

private void input(){
int r[] = new int[50];
Scanner sc=new Scanner(System.in);
System.out.print("Enter no. of processes : ");
np=sc.nextInt();	//no. of process
System.out.print("Enter no. of resources : ");
nr=sc.nextInt();	//no. of resources 

for(int i=0;i<nr;i++)
{
System.out.print("Enter total no. of resources R["+i+"]: ");
r[i]=sc.nextInt();

}
need=new int[np][nr];	//initializing arrays 
claim=new int[np][nr];
allocate=new int[np][nr]; 
avail=new int[nr];


//aboe int[1][nr]
System.out.println("Enter allocation matrix -->"); 
for(int i=0;i<np;i++)
for(int j=0;j<nr;j++) 
allocate[i][j]=sc.nextInt();	//allocation matrix

System.out.println("Enter claim matrix -->"); 
for(int i=0;i<np;i++)
for(int j=0;j<nr;j++) 
claim[i][j]=sc.nextInt();	//claim matrix

//System.out.println("Enter available resources -->"); 
//for(int j=0;j<nr;j++)
//avail[0][j]=sc.nextInt();	//available resources

int sum=0;
for(int i=0;i<nr;i++)
{

	for(int j=0;j<np;j++)
	{

		sum=sum+allocate[j][i];
	}
	avail[i]=r[i]-sum;
	sum=0;
}

for(int i=0;i<nr;i++)
	System.out.println("Avail calulated"+avail[i]);	



sc.close();
}

private int[][] calc_need(){ 
for(int i=0;i<np;i++)
for(int j=0;j<nr;j++)	//calculating need matrix
need[i][j]=claim[i][j]-allocate[i][j];


return need;
}

private boolean check(int i)
{
//checking if all resources for ith process can be allocated 
for(int j=0;j<nr;j++)
if(avail[j]<need[i][j]) 
 return false;

return true;
}

public void isSafe()
{
 input(); 
calc_need();
System.out.println("\nNeed matrix is :");
for(int i=0;i<np;i++)
{
for(int j=0;j<nr;j++)	
{
System.out.print("\t"+need[i][j]+"\t");
}
System.out.println();
}

boolean done[]=new boolean[np]; 
int j=0;

 while(j<np)
 {	                  //until all process allocated
   boolean allocated=false;
  for(int i=0;i<np;i++)
  {
   if(!done[i] && check(i))
   {	                  //trying to allocate 
   
   System.out.println("\nprocess : P"+i+" is executed completely"); 
   System.out.println("\nAvailable Resources:");
   for(int k=0;k<nr;k++)
   {
    avail[k]=avail[k]-need[i][k]+claim[i][k]; 
    System.out.print(avail[k]+"\t");
    }  
   allocated=done[i]=true;
   j++;
   }
  }
   
   if(!allocated)
   break;	//if no allocation
 }

if(j==np)	//if all processes are allocated 
System.out.println("\nSystem is in safe state");
else
System.out.println("\nSystem is in unsafe state");
}

public static void main(String[] args) 
{ 
new bankersalgo().isSafe();
}
}


/*OUTPUT
Enter no. of processes : 3
Enter no. of resources : 3
Enter total no. of resources R[0]: 7
Enter total no. of resources R[1]: 7
Enter total no. of resources R[2]: 10
Enter allocation matrix -->
2 2 3
2 0 3
1 2 4
Enter claim matrix -->
3 6 8
4 3 3
3 4 4
Enter available resources -->
2 3 0

Need matrix is :
	1		4		5	
	2		3		0	
	2		2		0	

process : P1 is executed completely

Available Resources:
4	3	3	
process : P2 is executed completely

Available Resources:
5	5	7	
process : P0 is executed completely

Available Resources:
7	7	10	
System is in safe state


*/



