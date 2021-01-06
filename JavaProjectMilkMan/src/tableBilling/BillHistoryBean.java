package tableBilling;

public class BillHistoryBean {
	String name,dos,doe,amount,status,totalcqty,totalbqty;//all small
	
	public BillHistoryBean(){}

	public BillHistoryBean(String name, String dos, String doe, String amount, String status, String totalcqty,
			String totalbqty) {
		super();
		this.name = name;
		this.dos = dos;
		this.doe = doe;
		this.amount = amount;
		this.status = status;
		this.totalcqty = totalcqty;
		this.totalbqty = totalbqty;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDos() {
		return dos;
	}

	public void setDos(String dos) {
		this.dos = dos;
	}

	public String getDoe() {
		return doe;
	}

	public void setDoe(String doe) {
		this.doe = doe;
	}

	public String getAmount() {
		return amount;
	}

	public void setAmount(String amount) {
		this.amount = amount;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getTotalcqty() {
		return totalcqty;
	}

	public void setTotalcqty(String totalcqty) {
		this.totalcqty = totalcqty;
	}

	public String getTotalbqty() {
		return totalbqty;
	}

	public void setTotalbqty(String totalbqty) {
		this.totalbqty = totalbqty;
	}
	

}
