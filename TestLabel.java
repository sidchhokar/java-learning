public class TestLabel {	
	
	public static void main(String[] args) {
		
		foreach:
			for(int i=0; i<5; i++) {
				for(int j=0; j<2; j++) { 
					System.out.print(" "+i);
					if(i==0) break;
					if(j==1) break foreach;
				}
			}
			
	//output of program is " 0 1 1"
	
	}
	
}
