package model;

import java.util.ArrayList;
import java.util.List;

public class MonthlyReport {

    private final List<String> itemName;
    private final List<Boolean> isExpense;
    private final List<Integer> quantity;
    private final List<Integer> sumOfOne;

    public MonthlyReport() {
        this.itemName = new ArrayList<>();
        this.isExpense = new ArrayList<>();
        this.quantity = new ArrayList<>();
        this.sumOfOne = new ArrayList<>();
    }

    public List<String> getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName.add(itemName);
    }

    public List<Boolean> getIsExpense() {
        return isExpense;
    }

    public void setIsExpense(Boolean isExpense) {
        this.isExpense.add(isExpense);
    }

    public List<Integer> getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity.add(quantity);
    }

    public List<Integer> getSumOfOne() {
        return sumOfOne;
    }

    public void setSumOfOne(Integer sumOfOne) {
        this.sumOfOne.add(sumOfOne);
    }

    public int length() {
        int firstLength = Math.min(quantity.size(), sumOfOne.size());
        int secondLength = Math.min(itemName.size(), isExpense.size());
        return Math.min(firstLength, secondLength);
    }
}
