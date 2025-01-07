/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.courtcontrol.models;

/**
 *
 * @author Bibhusan Silwal
 * LMU ID : 23048642
 */
public class CustomerModel {
    private int customerId;
    private static int idCounter = 1;
    private String customerName;
    private String customerPhone;
    private String customerTime;
    private String customerDate;
    private short customerCourtNo;

    public CustomerModel( String customerName, String customerPhone, String customerTime, String customerDate, short customerCourtNo) {
        this.customerId = idCounter++;
        this.customerName = customerName;
        this.customerPhone = customerPhone;
        this.customerTime = customerTime;
        this.customerDate = customerDate;
        this.customerCourtNo = customerCourtNo;
    }
    
    public CustomerModel( int customerId,String customerName, String customerPhone, String customerTime, String customerDate, short customerCourtNo) {
        this.customerId = customerId;
        this.customerName = customerName;
        this.customerPhone = customerPhone;
        this.customerTime = customerTime;
        this.customerDate = customerDate;
        this.customerCourtNo = customerCourtNo;
    }
    
    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getCustomerPhone() {
        return customerPhone;
    }

    public void setCustomerPhone(String customerPhone) {
        this.customerPhone = customerPhone;
    }

    public String getCustomerTime() {
        return customerTime;
    }

    public void setCustomerTime(String customerTime) {
        this.customerTime = customerTime;
    }

    public String getCustomerDate() {
        return customerDate;
    }

    public void setCustomerDate(String customerDate) {
        this.customerDate = customerDate;
    }

    public int getCustomerCourtNo() {
        return customerCourtNo;
    }

    public void setCustomerCourtNo(short customerCourtNo) {
        this.customerCourtNo = customerCourtNo;
    }
    
}
