package service;

import model.MonthlyReport;
import model.YearlyReport;

import java.util.List;

public class Builder {

    public MonthlyReport builderObjectMonthlyReport(List<String> contentOfFile) {
        MonthlyReport monthlyReport = new MonthlyReport();

        for (int i = 1; i < contentOfFile.size(); i++) {
            String[] array = contentOfFile.get(i).split(",");
            monthlyReport.setItemName(array[0]);
            monthlyReport.setIsExpense(Boolean.parseBoolean(array[1]));
            monthlyReport.setQuantity(Integer.parseInt(array[2]));
            monthlyReport.setSumOfOne(Integer.parseInt(array[3]));
        }

        return monthlyReport;
    }

    public YearlyReport builderObjectYearlyReport(List<String> contentOfFile) {
        YearlyReport yearlyReport = new YearlyReport();

        for (int i = 1; i < contentOfFile.size(); i++) {
            String[] array = contentOfFile.get(i).split(",");
            yearlyReport.setMonth(Integer.parseInt(array[0]));
            yearlyReport.setAmount(Integer.parseInt(array[1]));
            yearlyReport.setIsExpense(Boolean.parseBoolean(array[2]));
        }

        return yearlyReport;
    }
}
