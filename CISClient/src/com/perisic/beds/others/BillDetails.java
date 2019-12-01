package com.perisic.beds.others;
/**
 * Serializable billDetails class
 * 
 * @author Nuwantha Fernando
 *
 */

import java.io.Serializable;

public class BillDetails implements Serializable{
	private String billNo;
	private String branchCode;
	private double billcost;
	private String purchasedDate;
	private String purchasedTime;
	
//Getters and setters	
	public String getBillNo() {
		return billNo;
	}
	public void setBillNo(String billNo) {
		this.billNo = billNo;
	}
	public String getBranchCode() {
		return branchCode;
	}
	public void setBranchCode(String branchCode) {
		this.branchCode = branchCode;
	}
	public double getBillcost() {
		return billcost;
	}
	public void setBillcost(double billcost) {
		this.billcost = billcost;
	}
	public String getPurchasedDate() {
		return purchasedDate;
	}
	public void setPurchasedDate(String purchasedDate) {
		this.purchasedDate = purchasedDate;
	}
	public String getPurchasedTime() {
		return purchasedTime;
	}
	public void setPurchasedTime(String purchasedTime) {
		this.purchasedTime = purchasedTime;
	}
	
	
}
