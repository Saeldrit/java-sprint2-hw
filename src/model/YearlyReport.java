package model;

import java.util.ArrayList;
import java.util.List;

public class YearlyReport {

    private final List<Integer> month;
    private final List<Integer> amount;
    private final List<Boolean> isExpense;

    public YearlyReport() {
        this.month = new ArrayList<>();
        this.amount = new ArrayList<>();
        this.isExpense = new ArrayList<>();
    }

    public List<Integer> getMonth() {
        return month;
    }

    public void setMonth(Integer month) {
        this.month.add(month);
    }

    public List<Integer> getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount.add(amount);
    }

    public List<Boolean> getIsExpense() {
        return isExpense;
    }

    public void setIsExpense(Boolean isExpense) {
        this.isExpense.add(isExpense);
    }

    public int length() {
        int length = Math.min(month.size(), amount.size());
        return Math.max(length, isExpense.size());
    }
}
