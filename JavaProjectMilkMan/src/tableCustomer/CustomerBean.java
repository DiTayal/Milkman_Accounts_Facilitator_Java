package tableCustomer;

public class CustomerBean {
	String customerName,mobile,address,dos;
	float cowQty,buffaloQty,cowPrice,buffaloPrice;
	
	public CustomerBean(){//default constructor
	}

	public CustomerBean(String customerName, String mobile, String address, String dos, float cowQty, float buffaloQty,
			float cowPrice, float buffaloPrice) {
		super();
		this.customerName = customerName;
		this.mobile = mobile;
		this.address = address;
		this.dos = dos;
		this.cowQty = cowQty;
		this.buffaloQty = buffaloQty;
		this.cowPrice = cowPrice;
		this.buffaloPrice = buffaloPrice;
	}

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getDos() {
		return dos;
	}

	public void setDos(String dos) {
		this.dos = dos;
	}

	public float getCowQty() {
		return cowQty;
	}

	public void setCowQty(float cowQty) {
		this.cowQty = cowQty;
	}

	public float getBuffaloQty() {
		return buffaloQty;
	}

	public void setBuffaloQty(float buffaloQty) {
		this.buffaloQty = buffaloQty;
	}

	public float getCowPrice() {
		return cowPrice;
	}

	public void setCowPrice(float cowPrice) {
		this.cowPrice = cowPrice;
	}

	public float getBuffaloPrice() {
		return buffaloPrice;
	}

	public void setBuffaloPrice(float buffaloPrice) {
		this.buffaloPrice = buffaloPrice;
	}
	
}
	