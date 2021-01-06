package tableVariation;

public class milkVariationBean {//no main should be there in bean class
	float cqty,bqty;
	String dov,cname;//all names should be small,its standard
	
	public milkVariationBean(){}
	
	public milkVariationBean(float cqty, float bqty, String dov, String cname) {
		super();
		this.cqty = cqty;
		this.bqty = bqty;
		this.dov = dov;
		this.cname = cname;
	}

	public float getCqty() {
		return cqty;
	}

	public void setCqty(float cqty) {
		this.cqty = cqty;
	}

	public float getBqty() {
		return bqty;
	}

	public void setBqty(float bqty) {
		this.bqty = bqty;
	}

	public String getDov() {
		return dov;
	}

	public void setDov(String dov) {
		this.dov = dov;
	}

	public String getCname() {
		return cname;
	}

	public void setCname(String cname) {
		this.cname = cname;
	}
	
}
