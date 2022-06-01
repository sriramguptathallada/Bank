package project;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;


public class Signin{
	static Scanner sc = new Scanner(System.in);
	public  void sign() throws SQLException {
		LoginVar log = new LoginVar ();
		
		System.out.println("Enter your account number");
		log.setAccount_number(sc.nextInt());
		
		System.out.println("Enter your pin");
		log.setPin(sc.nextInt());
		
		System.out.println("Enter your username");
		log.setName(sc.next());
		
		// checking whether user is present in the table.
		Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/project","root","Sritha@321");
		String query = "select * from new_user where account_number =? AND  pin=? and name=?";
		PreparedStatement stmt = con.prepareStatement(query);
		stmt.setInt(1, log.getAccount_number());
		stmt.setInt(2, log.getPin());
		stmt.setString(3, log.getName());
		ResultSet rs = stmt.executeQuery();
		
		if (rs.next()) {
			System.out.println("*****WELCOME "+log.getName().toUpperCase()+" *****");
			
			//getting user id.
			Connection id_con = DriverManager.getConnection("jdbc:mysql://localhost:3306/project","root","Sritha@321");
			String query1 = "select id from new_user where name = ? and account_number=?";
			PreparedStatement stmt2 = id_con.prepareStatement(query1);
			stmt2.setString(1, log.getName());
			stmt2.setInt(2, log.getAccount_number());
			ResultSet rs1 = stmt2.executeQuery();
			if (rs1.next()) {
				log.setId(Integer.parseInt(rs1.getString(1)));
			}
			id_con.close();
			// closing the id connection
			
			System.out.println("PRESS 1 TO BALANCE INQUIRY");
			System.out.println("PRESS 2 TO CASH WITHDRAWAL");
			System.out.println("PRESS 3 TO DEPOSIT CASH");
			System.out.println("PRESS 4 TO TRANSFER MONEY");
			System.out.println("PRESS 5 TO EXIT");
			System.out.println("Enter your response");
			log.setUser_response(sc.nextInt());
			
			while(log.getUser_response()>0 && log.getUser_response()<=4) {
				switch(log.getUser_response()) {
				case 1:
					// getting balance
					Connection balance_con = DriverManager.getConnection("jdbc:mysql://localhost:3306/project","root","Sritha@321");
					String query2 = "select * from new_user where account_number =? AND  pin=?";
					PreparedStatement stmt3 = balance_con.prepareStatement(query2);
					stmt3.setInt(1, log.getAccount_number());
					stmt3.setInt(2, log.getPin());
					ResultSet rs2 = stmt3.executeQuery();
					
					if (rs2.next()) {
						System.out.println("Available balance="+Integer.parseInt(rs2.getString(5)));
					}
					balance_con.close();
					//closed balance connection
					
					// inserting activity
					Connection activity_con = DriverManager.getConnection("jdbc:mysql://localhost:3306/project","root","Sritha@321");
					PreparedStatement activity_stmt = activity_con.prepareStatement("insert into logindetails(name,new_userid,action,datetime) values(?,?,?,Current_TimeStamp)");
					activity_stmt.setString(1,log.getName());
					activity_stmt.setInt(2,log.getId());
					activity_stmt.setString(3, "checked balance");
					activity_stmt.executeUpdate();
					activity_con.close();
					//closing activity
					
					System.out.println();
					System.out.println("PRESS 1 TO BALANCE INQUIRY");
					System.out.println("PRESS 2 TO CASH WITHDRAWAL");
					System.out.println("PRESS 3 TO DEPOSIT CASH");
					System.out.println("PRESS 4 TO TRANSFER MONEY");
					System.out.println("PRESS 5 TO EXIT");
					System.out.println("Enter your response");
					log.setUser_response(sc.nextInt());
					
					break;
					
				case 2:
					
					Connection bal_con1 = DriverManager.getConnection("jdbc:mysql://localhost:3306/project","root","Sritha@321");
					String query3 = "select available_balance from new_user where account_number =? AND  pin=?";
					PreparedStatement stmt4 = bal_con1.prepareStatement(query3);
					stmt4.setInt(1, log.getAccount_number());
					stmt4.setInt(2, log.getPin());
					ResultSet rs3 = stmt4.executeQuery();
					
					if(rs3.next()) {
						System.out.println();
					}
					
					
					System.out.println("ENTER WITHDRAW AMOUNT");
					log.setWithdraw_amount(sc.nextInt());
					
					if(log.getWithdraw_amount()> Integer.parseInt(rs3.getString(1))) {
						System.out.println("INSUFFICIENT FUNDS");
					}
					
					
					else {
						Connection withdraw_connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/project","root","Sritha@321");
						int Update_balance = Integer.parseInt(rs3.getString(1))-log.getWithdraw_amount();
						PreparedStatement stmt5 = withdraw_connection.prepareStatement("update new_user set available_balance=? where name=?");
						stmt5.setInt(1,Update_balance);
						stmt5.setString(2,log.getName());
						stmt5.executeUpdate();
						System.out.println("Your withdrawal request has been processed");
						System.out.println("Available balance = "+(Integer.parseInt(rs3.getString(1))-log.getWithdraw_amount()));		
						
						Connection activity_con1 = DriverManager.getConnection("jdbc:mysql://localhost:3306/project","root","Sritha@321");
						PreparedStatement activity_stmt1 = activity_con1.prepareStatement("insert into logindetails(name,new_userid,action,datetime) values(?,?,?,Current_TimeStamp)");
						activity_stmt1.setString(1,log.getName());
						activity_stmt1.setInt(2,log.getId());
						activity_stmt1.setString(3,("Withdrawn "+log.getWithdraw_amount()+" rs"));
						activity_stmt1.executeUpdate();
						activity_con1.close();
						bal_con1.close();

					    }
					
					System.out.println();
					System.out.println("PRESS 1 TO BALANCE INQUIRY");
					System.out.println("PRESS 2 TO CASH WITHDRAWAL");
					System.out.println("PRESS 3 TO DEPOSIT CASH");
					System.out.println("PRESS 4 TO TRANSFER MONEY");
					System.out.println("PRESS 5 TO EXIT");
					System.out.println("Enter your response");
					log.setUser_response(sc.nextInt());
					break;
					
					
				case 3:
					Connection bal_con2 = DriverManager.getConnection("jdbc:mysql://localhost:3306/project","root","Sritha@321");
					String query4 = "select available_balance from new_user where account_number =? and pin=?";
					PreparedStatement stmt6 = bal_con2.prepareStatement(query4);
					stmt6.setInt(1, log.getAccount_number());
					stmt6.setInt(2, log.getPin());
					ResultSet rs4 = stmt6.executeQuery();
					String updated_balance = null;
					if (rs4.next()) {
						updated_balance = rs4.getString(1);
						
					}
					bal_con2.close();
					
					System.out.println("ENTER DEPOSIT AMOUNT");
					log.setDeposit_amount(sc.nextInt());
					Connection Deposit_connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/project","root","Sritha@321");
					PreparedStatement stmt21 = Deposit_connection.prepareStatement("update new_user set available_balance=? where account_number=?");
					stmt21.setInt(1,Integer.parseInt(updated_balance)+log.getDeposit_amount());
					stmt21.setInt(2,log.getAccount_number());
					stmt21.executeUpdate();
					System.out.println("Your deposit request has been processed");
					System.out.println("Available balance = "+(Integer.parseInt(updated_balance)+log.getDeposit_amount()));
					Deposit_connection.close();
					
					Connection activity_con2 = DriverManager.getConnection("jdbc:mysql://localhost:3306/project","root","Sritha@321");
					PreparedStatement activity_stmt2 = activity_con2.prepareStatement("insert into logindetails(name,new_userid,action,datetime) values(?,?,?,Current_TimeStamp)");
					activity_stmt2.setString(1,log.getName());
					activity_stmt2.setInt(2,log.getId());
					activity_stmt2.setString(3,("deposited "+log.getDeposit_amount()+" rs"));
					activity_stmt2.executeUpdate();
					activity_con2.close();
					
					
					System.out.println();
					System.out.println("PRESS 1 TO BALANCE INQUIRY");
					System.out.println("PRESS 2 TO CASH WITHDRAWAL");
					System.out.println("PRESS 3 TO DEPOSIT CASH");
					System.out.println("PRESS 4 TO TRANSFER MONEY");
					System.out.println("PRESS 5 TO EXIT");
					System.out.println("Enter your response");
					log.setUser_response(sc.nextInt());
					break;
					
					
				case 4:
					Connection bal_con3 = DriverManager.getConnection("jdbc:mysql://localhost:3306/project","root","Sritha@321");
					String query5 = "select available_balance from new_user where account_number =? and pin=?";
					PreparedStatement stmt7 = bal_con3.prepareStatement(query5);
					stmt7.setInt(1, log.getAccount_number());
					stmt7.setInt(2, log.getPin());
					ResultSet rs5 = stmt7.executeQuery();
					if (rs5.next()) {
						System.out.println();
						
					}
					
					System.out.println("Enter receiver account number");
					log.setReceiver_Account_Number(sc.nextInt());
					
					Connection transfer_con = DriverManager.getConnection("jdbc:mysql://localhost:3306/project","root","Sritha@321");
					String query9 = "select * from new_user where account_number=?";
					PreparedStatement stmt9 = transfer_con.prepareStatement(query9);
					stmt9.setInt(1, log.getReceiver_Account_Number());
					ResultSet rs9 = stmt9.executeQuery();
					if (rs9.next()) {
						
						System.out.println("Enter tranfser amount");
						log.setTranser_amount(sc.nextInt());
						
						Connection withdraw_connection1 = DriverManager.getConnection("jdbc:mysql://localhost:3306/project","root","Sritha@321");
						int Update_balance = Integer.parseInt(rs5.getString(1))-log.getTranser_amount();
						PreparedStatement stmt12 = withdraw_connection1.prepareStatement("update new_user set available_balance=? where account_number=?");
						stmt12.setInt(1,Update_balance);
						stmt12.setInt(2,log.getAccount_number());
						System.out.println("Your tranfser request has been processed");
						System.out.println("Available balance = "+(Integer.parseInt(rs5.getString(1))-log.getTranser_amount()));
						stmt12.executeUpdate();
				
						Connection bal_con4 = DriverManager.getConnection("jdbc:mysql://localhost:3306/project","root","Sritha@321");
						String query6 = "select available_balance from new_user where account_number =?";
						PreparedStatement stmt8 = bal_con4.prepareStatement(query6);
						stmt8.setInt(1, log.getReceiver_Account_Number());
						ResultSet rs6 = stmt8.executeQuery();
						if (rs6.next()) {
							System.out.println("");;
							
						}
						
						Connection Deposit_connection1 = DriverManager.getConnection("jdbc:mysql://localhost:3306/project","root","Sritha@321");
						PreparedStatement stmt212 = Deposit_connection1.prepareStatement("update new_user set available_balance=? where account_number=?");
						stmt212.setInt(1,Integer.parseInt(rs6.getString(1))+log.getTranser_amount());
						stmt212.setInt(2,log.getReceiver_Account_Number());
						stmt212.executeUpdate();
						System.out.println("Your deposit request has been processed");
						System.out.println("Available balance = "+(Integer.parseInt(rs6.getString(1))+log.getTranser_amount()));
						Deposit_connection1.close();
						bal_con4.close();
						withdraw_connection1.close();
						bal_con3.close();
						
					}
					else {
						System.out.println("Enter receiver account number which is present in data base");
			 
					}
					transfer_con.close();
					
					
					
					System.out.println();
					System.out.println("PRESS 1 TO BALANCE INQUIRY");
					System.out.println("PRESS 2 TO CASH WITHDRAWAL");
					System.out.println("PRESS 3 TO DEPOSIT CASH");
					System.out.println("PRESS 4 TO TRANSFER MONEY");
					System.out.println("PRESS 5 TO EXIT");
					System.out.println("Enter your response");
					log.setUser_response(sc.nextInt());
					break;
					
				}
				
				}
			
		}
		else {
			System.out.println("No data found");
		}
		
	}

}
