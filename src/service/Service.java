package service;

import model.MonthlyReport;

public class Service {

    public int countingTheAmountForMonth(MonthlyReport monthlyReport,
                                         boolean isProfitOrExpense) {
        int resultSum = 0;

        for (int i = 0; i < monthlyReport.length(); i++) {
            if (monthlyReport.getIsExpense().get(i) == isProfitOrExpense) {
                int quantity = monthlyReport.getQuantity().get(i);
                int sumOfOne = monthlyReport.getSumOfOne().get(i);
                resultSum += (quantity * sumOfOne);
            }
        }

        return resultSum;
    }
}
