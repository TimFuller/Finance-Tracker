import java.util.LinkedList;

public class Account {
	
	String userName;
	String password;
	LinkedList<Transaction> ledger;
	
	public Account(String tempUserName, String tempPassword) {
			this.userName = tempUserName;
			this.password = tempPassword;
			this.ledger = new LinkedList<Transaction>();
	}
	public Account(String tempUserName, String tempPassword, LinkedList<Transaction> tempList) {
		this.userName = tempUserName;
		this.password = tempPassword;
		this.ledger = tempList;
	}
}
 
