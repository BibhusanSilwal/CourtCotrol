/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.courtcontrol.controllers.algorithms;

import com.courtcontrol.models.CustomerModel;
import java.util.List;

/**
 *
 * @author Bibhusan Silwal LMU Id: 23048642
 */
public class Search {

    /**
     * Searches for a customer by name in the sorted booking list using a binary
     * search algorithm.
     *
     * This method performs a binary search on the sorted `customerList` to find
     * a `CustomerModel` with a name that matches the given `searchValue`. The
     * search is case-insensitive, and the list must be sorted before calling
     * this method. If the `searchValue` is found, it returns the matching
     * `CustomerModel`; otherwise, it returns `null`. The method works by
     * recursively dividing the list into smaller sections based on the `mid`
     * value.
     *
     * @param searchValue The name of the customer to search for.
     * @param customerList The list of sorted `CustomerModel` objects to search
     * within.
     * @param left The left index of the list to start the search.
     * @param right The right index of the list to end the search.
     * @return A `CustomerModel` object if a match is found, or `null` if no
     * match exists.
     */

    public CustomerModel searchByName(String searchValue, List<CustomerModel> customerList, int left, int right) {
        if (right < left) {
            return null;
        }
        int mid = (left + right) / 2;
        String midValue = customerList.get(mid).getCustomerName().toLowerCase();
        searchValue = searchValue.toLowerCase();

        if (searchValue.equals(midValue)) {
            return customerList.get(mid);
        } else if (searchValue.compareTo(midValue) < 0) {
            return searchByName(searchValue, customerList, left, mid - 1);
        } else {
            return searchByName(searchValue, customerList, mid + 1, right);
        }
    }
}
