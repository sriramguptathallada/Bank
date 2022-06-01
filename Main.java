package project;

import java.sql.SQLException;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Main {
	
	public static void main(String[] args) throws SQLException {
		Scanner sc = new Scanner(System.in);
		String s = "y";
		try {
			while (s.equals("y")) {
			System.out.println("*****Welcome*****");
			System.out.println();
			System.out.println("Press 1 to Create account");
			System.out.println("Press 2 to LogIn");
			int response = sc.nextInt();
			
			LoginSignup lisu = new LoginSignup();
			Signin signin = new Signin();
			switch(response) {
			case 1:
				System.out.println("Entering into the signup page.");
				lisu.signup();
				break;
			case 2:
				System.out.println("Entering into the login page.");
				signin.sign();
				break;
			default:
				System.out.println("You have entered the wrong choice.");
			}
			System.out.println("Do you want to continue y/n?");
			s = sc.next();

			}
		}catch(InputMismatchException e) {
			System.out.println("input mismatch");
		}
	}
	
}
