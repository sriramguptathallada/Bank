package project;

import java.sql.*;

import java.util.Scanner;


public class LoginSignup{
	public void signup() {
		try {
			@SuppressWarnings("resource")
			Scanner sc = new Scanner(System.in); 
			Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/project","root","Sritha@321");
			System.out.println("Create your user name");
			String name = sc.nextLine();
			String query = "select * from new_user where name=?"; //or
			//String query = "select name from new_user where name ='"+ name+"'";  
			PreparedStatement stmt = con.prepareStatement(query);
			stmt.setString(1, name);
			ResultSet rs = stmt.executeQuery();
			
			
			if (rs.next()) {  
				System.out.println("user name  alreadyexisted");	
			}
			else {
				System.out.println("ENTER YOUR EMAIL");
				String email = sc.nextLine();
				System.out.println("ENTER YOUR PIN");
				int pin = sc.nextInt();
				System.out.println("minimum deposit amount 1000");
				int min_deposit = sc.nextInt();
				while (min_deposit<1000) {
					System.out.println("min_deposit should be greater than equal to 1000");
					System.out.println("enter your amount");
					min_deposit = sc.nextInt();
				}
				
				int min = 0;
				int max = 9;
				String account_Number = "";
				boolean account_number_in_table = true;
				while (account_number_in_table == true) {
					
					for(int i = 0;i<5;i++) {
						int random_Number = (int)(Math.random()*(max-min+1)+min);
						account_Number = account_Number+Integer.toString(random_Number); 
					}
					
					String sql = "select account_Number from new_user where account_Number=?"; //or
					//String query = "select name from new_user where name ='"+ name+"'";  
					PreparedStatement stmtt = con.prepareStatement(sql);
					stmtt.setString(1, account_Number);
					ResultSet rss = stmtt.executeQuery();
					
					
					if (rss.next()) {  
						account_number_in_table = true;
					}
					else {
						account_number_in_table = false;
					}
					
				}
				System.out.println(account_number_in_table); //checking while loop.
				
				String sql = "insert into new_user(name,email,pin,available_balance,account_number) values(?,?,?,?,?)";
				PreparedStatement statement = con.prepareStatement(sql,Statement.RETURN_GENERATED_KEYS);
				statement.setString(1, name);
				statement.setString(2, email);
				statement.setInt(3,pin);
				statement.setInt(4,min_deposit);
				statement.setString(5, account_Number);
				
				
				statement.executeUpdate();
			    statement.getGeneratedKeys();
			    System.out.println("USER CREATED");
			    System.out.println("save your account_number "+account_Number);
				con.close();
			}
		}catch(Exception e) {
			System.out.println("error"+e.getMessage());
		}
	}

}