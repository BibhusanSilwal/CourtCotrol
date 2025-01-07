/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.courtcontrol.controllers.algorithms;

import com.courtcontrol.models.CustomerModel;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Bibhusan Silwal LMU ID: 23048642
 */
public class Sort {

    List<CustomerModel> bookingSortList;

    public Sort() {
        bookingSortList = new ArrayList<>();
    }

    /**
     * Sorts the booking list by customer ID in either ascending or descending
     * order.
     *
     * This method uses the selection sort algorithm to arrange the list of
     * `CustomerModel` objects based on their `customerId`. It checks the value
     * of `isDesc` to determine whether to sort in descending (`true`) or
     * ascending (`false`) order.
     *
     * @param customerList The list of `CustomerModel` objects to be sorted.
     * @param isDesc A boolean value indicating whether to sort in descending
     * order (true) or ascending order (false).
     * @return A list of sorted `CustomerModel` objects based on customer ID.
     */
    public List<CustomerModel> sortBookingById(List<CustomerModel> customerList, boolean isDesc) {
        this.bookingSortList.clear();
        this.bookingSortList.addAll(customerList);

        for (int i = 0; i < bookingSortList.size() - 1; i++) {
            int minValue = i;
            for (int j = i + 1; j < bookingSortList.size(); j++) {
                if (isDesc) {

                    if (bookingSortList.get(j).getCustomerId() > bookingSortList.get(minValue).getCustomerId()) {
                        minValue = j;
                    }
                } else {

                    if (bookingSortList.get(j).getCustomerId() < bookingSortList.get(minValue).getCustomerId()) {
                        minValue = j;
                    }
                }
            }
            CustomerModel tempCustomer = bookingSortList.get(i);
            bookingSortList.set(i, bookingSortList.get(minValue));
            bookingSortList.set(minValue, tempCustomer);
        }

        return bookingSortList;
    }

    /**
     * Sorts the booking list by customer name in either ascending or descending
     * order.
     *
     * This method uses the insertion sort algorithm to arrange the list of
     * `CustomerModel` objects based on their `customerName`. It checks the
     * value of `isDesc` to determine whether to sort in descending (`true`) or
     * ascending (`false`) order. The sorting is case-insensitive due to the use
     * of `compareToIgnoreCase`.
     *
     * @param customerList The list of `CustomerModel` objects to be sorted.
     * @param isDesc A boolean value indicating whether to sort in descending
     * order (true) or ascending order (false).
     * @return A list of sorted `CustomerModel` objects based on customer name.
     */
    public List<CustomerModel> sortBookingByName(List<CustomerModel> customerList, boolean isDesc) {
        List<CustomerModel> sortedList = new ArrayList<>(customerList);

        for (int i = 1; i < sortedList.size(); i++) {
            CustomerModel current = sortedList.get(i);
            int j = i - 1;

            if (isDesc) {
                while (j >= 0 && sortedList.get(j).getCustomerName().compareToIgnoreCase(current.getCustomerName()) < 0) {
                    sortedList.set(j + 1, sortedList.get(j));
                    j--;
                }
            } else {
                while (j >= 0 && sortedList.get(j).getCustomerName().compareToIgnoreCase(current.getCustomerName()) > 0) {
                    sortedList.set(j + 1, sortedList.get(j));
                    j--;
                }
            }
            sortedList.set(j + 1, current);
        }

        return sortedList;
    }

    /**
     * Sorts the booking list by customer booking date in either ascending or
     * descending order using the merge sort algorithm.
     *
     * This method divides the `customerList` into two halves, recursively sorts
     * them, and then merges them back together using the `merge` method. The
     * sorting is based on the `customerDate` field of `CustomerModel`, and the
     * order is determined by the `isDesc` flag. If `isDesc` is true, the list
     * will be sorted in descending order; if false, it will be sorted in
     * ascending order.
     *
     * @param customerList The list of `CustomerModel` objects to be sorted.
     * @param isDesc A boolean value indicating whether to sort in descending
     * order (true) or ascending order (false).
     * @return A list of sorted `CustomerModel` objects based on customer
     * booking date.
     */
    public List<CustomerModel> sortBookingByDate(List<CustomerModel> customerList, boolean isDesc) {
        if (customerList.size() <= 1) {
            return customerList;
        }

        int mid = customerList.size() / 2;
        List<CustomerModel> left = new ArrayList<>(customerList.subList(0, mid));
        List<CustomerModel> right = new ArrayList<>(customerList.subList(mid, customerList.size()));

        left = sortBookingByDate(left, isDesc);
        right = sortBookingByDate(right, isDesc);

        return merge(left, right, isDesc);
    }

    /**
     * Merges two sorted lists of customer bookings into a single sorted list.
     *
     * This method merges two lists (`left` and `right`) of `CustomerModel`
     * objects, which are assumed to be sorted by the booking date. The merged
     * list is sorted based on the `customerDate` field in either ascending or
     * descending order, as specified by the `isDesc` parameter.
     *
     * The method uses the merge operation commonly found in the merge sort
     * algorithm. If `isDesc` is `true`, the method sorts in descending order,
     * otherwise it sorts in ascending order.
     *
     * @param left The first list of `CustomerModel` objects to merge.
     * @param right The second list of `CustomerModel` objects to merge.
     * @param isDesc A boolean indicating whether to sort in descending order
     * (`true`) or ascending order (`false`).
     * @return A new list containing all the elements from `left` and `right`,
     * merged and sorted.
     */
    private List<CustomerModel> merge(List<CustomerModel> left, List<CustomerModel> right, boolean isDesc) {
        List<CustomerModel> merged = new ArrayList<>();
        int i = 0, j = 0;

        while (i < left.size() && j < right.size()) {
            if (isDesc) {
                if (left.get(i).getCustomerDate().compareTo(right.get(j).getCustomerDate()) >= 0) {
                    merged.add(left.get(i++));
                } else {
                    merged.add(right.get(j++));
                }
            } else {
                if (left.get(i).getCustomerDate().compareTo(right.get(j).getCustomerDate()) <= 0) {
                    merged.add(left.get(i++));
                } else {
                    merged.add(right.get(j++));
                }
            }
        }

        while (i < left.size()) {
            merged.add(left.get(i++));
        }

        while (j < right.size()) {
            merged.add(right.get(j++));
        }

        return merged;
    }
}
