/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.courtcontrol.controllers.datastructures;

/**
 *
 * @author Bibhusan Silwal LMU ID - 23048642
 */
import com.courtcontrol.models.CustomerModel;

import java.util.List;
import java.util.LinkedList;

public class CustomQueue {

    private final List<CustomerModel> requestQueue;
    private final int maxSize;

    public CustomQueue(int maxSize) {
        this.requestQueue = new LinkedList<>();
        this.maxSize = maxSize;
    }

    /**
     * Adds a `CustomerModel` request to the queue if there is space.
     *
     * This method checks if the queue is full before adding a new
     * `CustomerModel` request. If the queue is not full, the request is added
     * to the `requestQueue`. If the queue is full, an `IllegalStateException`
     * is thrown to indicate that no more elements can be added.
     *
     * @param request The `CustomerModel` object to be added to the queue.
     * @throws IllegalStateException If the queue is full and the request cannot
     * be added.
     */
    public void enqueue(CustomerModel request) {
        if (!isFull()) {
            requestQueue.add(request);
        } else {
            throw new IllegalStateException("Queue is full");
        }
    }

    /**
     * Removes the first `CustomerModel` request from the queue.
     *
     * This method checks if the queue is empty before attempting to remove the
     * first element. If the queue is not empty, the first request is removed.
     * If the queue is empty, an `IllegalStateException` is thrown to indicate
     * that there are no elements to remove.
     *
     * @throws IllegalStateException If the queue is empty and no element can be
     * removed.
     */
    public void dequeue() {
        if (!isEmpty()) {
            requestQueue.remove(0);
        } else {
            throw new IllegalStateException("Queue is empty");
        }
    }

    /**
     * Returns the first `CustomerModel` request from the queue without removing
     * it.
     *
     * This method checks if the queue is empty before attempting to return the
     * first element. If the queue is not empty, the first request is returned.
     * If the queue is empty, `null` is returned to indicate that there are no
     * elements in the queue.
     *
     * @return The first `CustomerModel` request in the queue, or `null` if the
     * queue is empty.
     */
    public CustomerModel peek() {
        if (!isEmpty()) {
            return requestQueue.get(0);
        }
        return null;
    }

    /**
     * Checks if the queue is empty.
     *
     * This method checks whether the `requestQueue` contains any elements. It
     * returns `true` if the queue is empty, and `false` if the queue contains
     * one or more elements.
     *
     * @return `true` if the queue is empty, `false` otherwise.
     */
    public boolean isEmpty() {
        return requestQueue.isEmpty();
    }

    /**
     * Checks if the queue is full.
     *
     * This method checks whether the `requestQueue` has reached its maximum
     * allowed size. It returns `true` if the queue is full, meaning its size is
     * equal to `maxSize`, and `false` otherwise.
     *
     * @return `true` if the queue is full, `false` otherwise.
     */
    public boolean isFull() {
        return requestQueue.size() == maxSize;
    }

    public void display() {
        System.out.println(requestQueue.get(0).getCustomerName());
    }
}
