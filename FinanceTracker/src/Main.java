import java.util.Scanner;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map;

public class Main {

	public static void main(String[] args) throws FileNotFoundException {
        boolean verification = false;
		Map<String, String> userList = new HashMap<String, String>();
        System.out.print("Enter Username:");
		Scanner scanner = new Scanner( System.in );
	    String userName = scanner.nextLine();
	    System.out.print("Enter Password:");
		Scanner scanner1 = new Scanner( System.in );
	    String password = scanner1.nextLine();
	    scanner.close();
	    scanner1.close();
	    String usernameFilePath = "C:\\Users\\timfu\\eclipse-workspace\\FinanceTracker\\src\\username.txt";
	    String passwordFilePath = "C:\\Users\\timfu\\eclipse-workspace\\FinanceTracker\\src\\password.txt";
	    
	    Scanner s = new Scanner(new File(usernameFilePath));
	    Scanner t = new Scanner(new File(passwordFilePath));
	    while (s.hasNext()){
	        userList.put(s.next(), t.next());
	    }
	    s.close();
	    t.close();
	    
        verification = verifyLogin(userName, password, userList);
        if (verification) {
        	System.out.println("Password matches.");
        }
        else {
        	System.out.println("Password doesn't match.");
        	return;
        }
        
	    LinkedList<Transaction> ledger = createLedger(userName);   
	    Account myAccount = new Account(userName, password, ledger);
   
	    
//	    testAccount (myAccount);
//	    sortLedgerByDateDes (myAccount);
//	    testAccount (myAccount);
//	    sortLedgerByAmountDes (myAccount);
//	    testAccount (myAccount);
//	    sortLedgerByCategory (myAccount);
//	    testAccount (myAccount);
//	    testLedger(getTransactions(myAccount, 3));
//	    changePassword(myAccount, "test");
	    myAccount.userName = "Fred";
	    saveAccount(myAccount);
	    
	    
	}
	public static boolean verifyLogin (String user, String pass, Map<String,String> userlist) {
		return(pass.equals(userlist.get(user)));
	}
	public static LinkedList<Transaction> createLedger(String userName){
		LinkedList<Transaction> myList = new LinkedList<Transaction>();
		String path = "C:\\Users\\timfu\\eclipse-workspace\\FinanceTracker\\src\\" + userName + ".txt";	
		File myFile = new File(path);
		Scanner s;
		//try catch to see if file exists
		try {
			s = new Scanner(myFile);
		}
		catch (Exception e) {
			return myList;
		}
		while(s.hasNextLine()) {
			int day = Integer.parseInt(s.nextLine());
			int month = Integer.parseInt(s.nextLine());
			int year = Integer.parseInt(s.nextLine());
			String category = s.nextLine();
			double amount =  Double.parseDouble(s.nextLine());
			String desc = s.nextLine();
			Transaction transaction = new Transaction(day, month, year, category, amount, desc);
			myList.add(transaction);
		}	
		s.close();
		Iterator<Transaction> i = myList.iterator();
		while(i.hasNext()) {
			//System.out.println("Transaction");
			Transaction  tempt = i.next();
			System.out.println(tempt.category);
			System.out.println("$"+tempt.amount);
			System.out.println(tempt.description);
			System.out.println(tempt.day+"/"+tempt.month+"/"+tempt.year);
		}
		return myList;
	}
	public static double getCurrentBalance (Account myAccount) {
		double balance = 0.00;
		Iterator<Transaction> i = myAccount.ledger.iterator();
		while(i.hasNext()) {
			Transaction tempt = i.next();
			if (tempt.category.equals("Income")) {
				balance += tempt.amount;
			}
			else {balance -= tempt.amount;}
		}
		return balance;
	}
	public static void deleteTransaction (Account myAccount, Transaction myTransaction) {myAccount.ledger.remove(myTransaction);}
	public static void addTransaction (Account myAccount, Transaction myTransaction) {myAccount.ledger.add(myTransaction);}
	public static void addTransaction (Account myAccount, int day, int month, int year, String category, double amount, String description) {
		Transaction myTransaction = new Transaction(day, month, year, category, amount, description);
		addTransaction(myAccount, myTransaction);
	}
	public static void sortLedgerByDateAsc (Account myAccount) {
		 LinkedList<Transaction> newList = new LinkedList<Transaction>();
         LinkedList<Transaction> oldList = myAccount.ledger;
         System.out.println("Sort Ledger by Date Ascending");
         while (!oldList.isEmpty()) {
              //System.out.println("Sorting "+oldList.size());
             Transaction tempT = null;
             Iterator<Transaction> i = oldList.iterator();
             Transaction holdT = i.next();
             while (i.hasNext()) {
                 tempT = i.next();
                 if (isYounger(tempT,holdT))
                     holdT=tempT;                
             }
             oldList.remove(holdT);
             newList.add(holdT);
         }
         myAccount.ledger=newList;
	}
	private static boolean isGreaterThan(Transaction tranA, Transaction tranB) {
         if (tranA.amount < tranB.amount)
             return true;
         if (tranA.amount > tranB.amount)
             return false;
         return false;
     }
	private static boolean isLessThan(Transaction tranA, Transaction tranB) {
        if (tranA.amount > tranB.amount)
            return true;
        if (tranA.amount < tranB.amount)
            return false;
        return false;
    }
	private static boolean isYounger(Transaction tranA, Transaction tranB) {
         if (tranA.year < tranB.year)
             return true;
         if (tranA.year > tranB.year)
              return false;
         if (tranA.month < tranB.month)
              return true;
         if (tranA.month > tranB.month)
             return false;
          if (tranA.day < tranB.day)
              return true;
          return false;
    }
	private static boolean isOlder(Transaction tranA, Transaction tranB) {
         if (tranA.year > tranB.year)
             return true;
         if (tranA.year < tranB.year)
              return false;
         if (tranA.month > tranB.month)
              return true;
         if (tranA.month < tranB.month)
             return false;
          if (tranA.day > tranB.day)
              return true;
          return false;
     }
	public static void sortLedgerByDateDes (Account myAccount) {
	 LinkedList<Transaction> newList = new LinkedList<Transaction>();
     LinkedList<Transaction> oldList = myAccount.ledger;
     System.out.println("Sort Ledger by Date Descending");
     while (!oldList.isEmpty()) {
         //System.out.println("Sorting "+oldList.size());
         Transaction tempT = null;
          Iterator<Transaction> i = oldList.iterator();
          Transaction holdT = i.next();
         while (i.hasNext()) {
             tempT = i.next();
             if (isOlder(tempT,holdT))
                 holdT=tempT;                
         }
         oldList.remove(holdT);
         newList.add(holdT);
     }
     myAccount.ledger=newList;
	}
	public static void sortLedgerByAmountAsc (Account myAccount) {
		LinkedList<Transaction> newList = new LinkedList<Transaction>();
		LinkedList<Transaction> oldList = myAccount.ledger;
        System.out.println("Sort Ledger by Amount Ascending");
		while (!oldList.isEmpty()) {
			//System.out.println("Sorting "+oldList.size());
			Transaction tempT = null;
			Iterator<Transaction> i = oldList.iterator();
			Transaction holdT = i.next();
			while (i.hasNext()) {
				tempT = i.next();
				if (isLessThan(tempT,holdT))
					holdT=tempT;                
			}
        oldList.remove(holdT);
        newList.add(holdT);
		}
		myAccount.ledger=newList;
	}
	public static void sortLedgerByAmountDes (Account myAccount) {
		LinkedList<Transaction> newList = new LinkedList<Transaction>();
		LinkedList<Transaction> oldList = myAccount.ledger;
        System.out.println("Sort Ledger by Amount Descending");
		while (!oldList.isEmpty()) {
			//System.out.println("Sorting "+oldList.size());
			Transaction tempT = null;
			Iterator<Transaction> i = oldList.iterator();
			Transaction holdT = i.next();
        while (i.hasNext()) {
            tempT = i.next();
            if (isGreaterThan(tempT,holdT))
                holdT=tempT;                
        	}
        oldList.remove(holdT);
        newList.add(holdT);
		}
    myAccount.ledger=newList;
	}
	public static void sortLedgerByCategory (Account myAccount) {
		LinkedList<Transaction> newList = new LinkedList<Transaction>();
        LinkedList<Transaction> oldList = myAccount.ledger;
        while(!oldList.isEmpty()){
        	Transaction tempT = oldList.pop();
        	newList.add(tempT);
        	oldList.remove(tempT);
    		String category = tempT.category;
    		LinkedList<Transaction> tempList = new LinkedList<Transaction>();
    		Iterator<Transaction> i = myAccount.ledger.iterator();
    		while(i.hasNext()) {
    			tempT = i.next();
    			if(tempT.category.equals(category)) {
    	        	tempList.add(tempT);
    			}
    		}
    		i = tempList.iterator();
    		while(i.hasNext()) {
    			tempT = i.next();
            	newList.add(tempT);
            	oldList.remove(tempT);
    		}
    	}
        myAccount.ledger=newList;
	}
	public static LinkedList<Transaction> getTransactions (Account myAccount, int x){
		if(myAccount.ledger.size()<=x) {
			return myAccount.ledger;
		}
		LinkedList<Transaction> newList = new LinkedList<Transaction>();
		Iterator<Transaction> i = myAccount.ledger.iterator();
		while (x>0) {
			newList.add(i.next());
			x--;
		}
		return newList;
	}
	private static void testLedger(LinkedList<Transaction> myList) {
		Iterator<Transaction> i = myList.iterator();
	    while(i.hasNext()) {
	    	Transaction tempt = i.next();
	    	System.out.println(tempt.toString());
	    }
	}
	public static LinkedList<Transaction> getAllTransactions (Account myAccount){return getTransactions(myAccount, myAccount.ledger.size());}
	public static double totalByCategory (Account myAccount, String category) {
		double total = 0;
		Iterator<Transaction> i = myAccount.ledger.iterator();
	    while(i.hasNext()) {
	    	Transaction tempt = i.next();
	    	if(tempt.category.equals(category)) {
	    		total = total + tempt.amount;
	    	}
	    }
	    return total;
	}
	public static int percentageByCategory (Account myAccount, String category) {
		double percent = totalByCategory(myAccount, category)/getCurrentBalance(myAccount);
		percent = percent * 100;
		return (int)percent;
	}
	public static double getTotalSpent (Account myAccount) {return (getCurrentBalance(myAccount)-totalByCategory(myAccount, "Income"));}
	public static void changePassword (Account myAccount, String newPassword){
		try {
			String usernameFilePath = "C:\\Users\\timfu\\eclipse-workspace\\FinanceTracker\\src\\username.txt";
			String passwordFilePath = "C:\\Users\\timfu\\eclipse-workspace\\FinanceTracker\\src\\password.txt";
			Map<String, String> userList = new HashMap<String, String>();
			Scanner s = new Scanner(new File(usernameFilePath));
			Scanner t = new Scanner(new File(passwordFilePath));
			while (s.hasNext()){
				userList.put(s.next(), t.next());
			}
			s.close();
			t.close();
			userList.put(myAccount.userName, newPassword);
			BufferedWriter userWriter = new BufferedWriter(new FileWriter(usernameFilePath));
			BufferedWriter passwordWriter  = new BufferedWriter(new FileWriter(passwordFilePath));
			for(Map.Entry<String, String> entry : userList.entrySet()) {
				String user = entry.getKey();
				String password = entry.getValue();
				userWriter.write(user);
				userWriter.newLine();
				passwordWriter.write(password);
				passwordWriter.newLine();
			}
			userWriter.close();
			passwordWriter.close();
		}
		catch (Exception e) {
			return;
		}
	}
	public static void saveAccount (Account myAccount){
		try {
			String file = "C:\\Users\\timfu\\eclipse-workspace\\FinanceTracker\\src\\" + myAccount.userName + ".txt";
			BufferedWriter writer = new BufferedWriter(new FileWriter(file));
			Iterator<Transaction> i = myAccount.ledger.iterator();
			while (i.hasNext()) {
				Transaction tempt = i.next();
				writer.write(Integer.toString(tempt.day));
				writer.newLine();
				writer.write(Integer.toString(tempt.month));
				writer.newLine();
				writer.write(Integer.toString(tempt.year));
				writer.newLine();
				writer.write(tempt.category);
				writer.newLine();
				writer.write(Double.toString(tempt.amount));
				writer.newLine();
				writer.write(tempt.description);
				writer.newLine();
			}
			writer.close();
		}
		catch (Exception e) {
			System.out.println(e.getMessage());
			return;
		}
		
	}
//	public static void deleteAccount (Account myAccount);{}
//	public static void createAccount (String username, String password);{}//adds username and password to files
//	public static void logOut (String username) {username = null;}
	public static void testAccount (Account myAccount) {
		System.out.println("Username: "+myAccount.userName);
		System.out.println("Password: "+myAccount.password);
		testLedger(myAccount.ledger);
	    System.out.println("Current balance = "+getCurrentBalance(myAccount));
	}

}
