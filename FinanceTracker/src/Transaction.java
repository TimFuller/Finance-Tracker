public class Transaction {
	int day;
	int month;
	int year;
	String category;
	Double amount;
	String description;

	public Transaction() {		
}
	public Transaction(int tempDay, int tempMonth, int tempYear, String tempCategory, Double tempAmount, String tempDescription) {
		this.day = tempDay;
		this.month = tempMonth;
		this.year = tempYear;
		this.category = tempCategory;
		this.amount = tempAmount;
		this.description = tempDescription;
	}
	public String toString () {
		return this.month+"/"+this.day+"/"+this.year+" "+this.category+" "+amount+" "+description;
	}
}
