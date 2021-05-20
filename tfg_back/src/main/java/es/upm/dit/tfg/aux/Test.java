package es.upm.dit.tfg.aux;

public class Test {

	public static void main(String[] args) {
		for(int i = 0; i>=3; i++) {
			for(int j = 0; j>=3; j++) {
				System.out.println("j = " + j);
				if(j == 2) break;
			}
			System.out.println("i = " + i);
		}
	}

}
