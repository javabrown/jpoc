
public class ThreadTest {
	public static void main(String args[]) throws InterruptedException{
		Emp e = new Emp(); 
		e.country = "US";
		
		T1 t1 = new T1(e);
		T2 t2 = new T2(e);
		
		t1.start();
		t2.start();
		
		new Thread().sleep(1000);
		
		
 
		System.out.println("T1 => " +  t1.t1E.country);
		System.out.println("T2 => " +  t2.t2E.country);
	}
}


class Bean{
	private String a;
	private int b;
	private Emp emp;
	
//	public Bean(String a, int b){
//		this.a = a;
//		this.b = b;
//	}
	
	public void setA(String a){
		this.a = a;
	}
	
	public void setB(int b){
		this.b = b;
	}
	
	public void setEmp(Emp emp){
		this.emp = emp;
	}
}

class Emp {
	public static String country;
	public String name;
	
	public String remoteData;
	
	public static String getCountry() {
		return country;
	}
	
	
	public static void setCOuntry(String country) {
		Emp.country = country;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public void makeWsCall(){
		//-----
		//.....
		remoteData = "My Remote Data";
	}
}


class T1 extends Thread {
	public Emp t1E;
	
	public T1(Emp x){
		this.t1E = x;
	}
	
	public void run(){
		 t1E.country = "INDIA";
		 System.out.println("T1-run called");
	}
}

class T2 extends Thread {
	public Emp t2E;
	
	public T2(Emp y){
		this.t2E = y;
	}

	public void run(){
		t2E.country = "SriLanka";
		System.out.println("T2-run called");
	}
}
