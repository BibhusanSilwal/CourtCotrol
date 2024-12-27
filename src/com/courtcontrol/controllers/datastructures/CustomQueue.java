/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.courtcontrol.controllers.datastructures;

/**
 *
 * @author Bibhusan Silwal
 * LMU ID - 23048642
 */
import com.courtcontrol.models.CustomerModel;
import java.util.ArrayList;
import java.util.List;

import java.util.List;
import java.util.LinkedList;

public class CustomQueue {
    private List<CustomerModel> requestQueue;
    private int maxSize;

    public CustomQueue(int maxSize) {
        this.requestQueue = new LinkedList<>();
        this.maxSize = maxSize;
    }

    public void enqueue(CustomerModel request) {
        if (!isFull()) {
            requestQueue.add(request);
        } else {
            throw new IllegalStateException("Queue is full");
        }
    }

    public void dequeue() {
        if (!isEmpty()) {
            requestQueue.remove(0);
        } else {
            throw new IllegalStateException("Queue is empty");
        }
    }

 

    public CustomerModel peek() {
        if (!isEmpty()) {
            return requestQueue.get(0);
        }
        return null;
    }

    public boolean isEmpty() {
        return requestQueue.isEmpty();
    }

    public boolean isFull() {
        return requestQueue.size() == maxSize;
    }

    public void display() {
        System.out.println(requestQueue.get(0).getCustomerName());
    }
}

 


