package com.pfg.pnet.reports.dto;

import java.io.Serializable;

public class ReportItem implements Serializable {
	private static final long serialVersionUID = 1L;
	private String itemNumber; 
	private long dateOfLastOrder; 
	private long quantityShipped; 
	private String priceBookHeadingNumber; 
	private String priceBookHeadingFamily; 
	private double extCatchWeight; 
	private String catchWeightCode; 
	private String brokenCaseCode; 
	private String vendorNumber; 
	private int itemPack; 
	private String itemSize; 
	private String UPCNumberItem; 
	private String UPCNumberUnit; 
	private String itemBrand;
	private String itemDescription;
	private String activeRecordCode;
	
	public String getItemNumber() {
		return itemNumber;
	}
	public void setItemNumber(String itemNumber) {
		this.itemNumber = itemNumber;
	}
	public long getDateOfLastOrder() {
		return dateOfLastOrder;
	}
	public void setDateOfLastOrder(long dateOfLastOrder) {
		this.dateOfLastOrder = dateOfLastOrder;
	}
	public long getQuantityShipped() {
		return quantityShipped;
	}
	public void setQuantityShipped(long quantityShipped) {
		this.quantityShipped = quantityShipped;
	}
	public String getPriceBookHeadingNumber() {
		return priceBookHeadingNumber;
	}
	public void setPriceBookHeadingNumber(String priceBookHeadingNumber) {
		this.priceBookHeadingNumber = priceBookHeadingNumber;
	}
	public String getPriceBookHeadingFamily() {
		return priceBookHeadingFamily;
	}
	public void setPriceBookHeadingFamily(String priceBookHeadingFamily) {
		this.priceBookHeadingFamily = priceBookHeadingFamily;
	}
	public double getExtCatchWeight() {
		return extCatchWeight;
	}
	public void setExtCatchWeight(double extCatchWeight) {
		this.extCatchWeight = extCatchWeight;
	}
	public String getCatchWeightCode() {
		return catchWeightCode;
	}
	public void setCatchWeightCode(String catchWeightCode) {
		this.catchWeightCode = catchWeightCode;
	}
	public String getBrokenCaseCode() {
		return brokenCaseCode;
	}
	public void setBrokenCaseCode(String brokenCaseCode) {
		this.brokenCaseCode = brokenCaseCode;
	}
	public String getVendorNumber() {
		return vendorNumber;
	}
	public void setVendorNumber(String vendorNumber) {
		this.vendorNumber = vendorNumber;
	}
	public int getItemPack() {
		return itemPack;
	}
	public void setItemPack(int itemPack) {
		this.itemPack = itemPack;
	}
	public String getItemSize() {
		return itemSize;
	}
	public void setItemSize(String itemSize) {
		this.itemSize = itemSize;
	}
	public String getUPCNumberItem() {
		return UPCNumberItem;
	}
	public void setUPCNumberItem(String uPCNumberItem) {
		UPCNumberItem = uPCNumberItem;
	}
	public String getUPCNumberUnit() {
		return UPCNumberUnit;
	}
	public void setUPCNumberUnit(String uPCNumberUnit) {
		UPCNumberUnit = uPCNumberUnit;
	}
	public String getItemBrand() {
		return itemBrand;
	}
	public void setItemBrand(String itemBrand) {
		this.itemBrand = itemBrand;
	}
	public String getItemDescription() {
		return itemDescription;
	}
	public void setItemDescription(String itemDescription) {
		this.itemDescription = itemDescription;
	}
	public String getActiveRecordCode() {
		return activeRecordCode;
	}
	public void setActiveRecordCode(String activeRecordCode) {
		this.activeRecordCode = activeRecordCode;
	}
	@Override
	public String toString() {
		return "ReportItem [itemNumber=" + itemNumber + ", dateOfLastOrder=" + dateOfLastOrder + ", quantityShipped="
				+ quantityShipped + ", priceBookHeadingNumber=" + priceBookHeadingNumber + ", priceBookHeadingFamily="
				+ priceBookHeadingFamily + ", extCatchWeight=" + extCatchWeight + ", catchWeightCode=" + catchWeightCode
				+ ", brokenCaseCode=" + brokenCaseCode + ", vendorNumber=" + vendorNumber + ", itemPack=" + itemPack
				+ ", itemSize=" + itemSize + ", UPCNumberItem=" + UPCNumberItem + ", UPCNumberUnit=" + UPCNumberUnit
				+ ", itemBrand=" + itemBrand + ", itemDescription=" + itemDescription + ", activeRecordCode="
				+ activeRecordCode + "]";
	}
}
