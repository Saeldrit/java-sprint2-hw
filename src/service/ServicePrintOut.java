package service;

import model.Month;
import model.MonthlyReport;
import model.YearlyReport;
import repository.Repository;

import java.util.Map;
import java.util.Scanner;

public class ServicePrintOut {

    private final Scanner scanner;
    private final Repository repository;
    private Integer userYear;
    private Map<Integer, MonthlyReport> monthlyReports;
    private YearlyReport yearlyReport;

    public ServicePrintOut(Scanner scanner) {
        this.repository = new Repository();
        this.scanner = scanner;
        this.userYear = choiceYears();
    }

    public void setMonthlyReports(Map<Integer, MonthlyReport> monthlyReports) {
        this.monthlyReports = monthlyReports;
    }

    public void setYearlyReport(YearlyReport yearlyReport) {
        this.yearlyReport = yearlyReport;
    }

    public void setUserYear(Integer userYear) {
        this.userYear = userYear;
    }

    public Integer getUserYear() {
        return userYear;
    }

    public Map<Integer, MonthlyReport> getMonthlyReports() {
        return monthlyReports;
    }

    public YearlyReport getYearlyReport() {
        return yearlyReport;
    }

    public Scanner getScanner() {
        return scanner;
    }

    public Repository getRepository() {
        return repository;
    }

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

    public void readAllMonthlyReportsByYear() {
        setMonthlyReports(getRepository().readAllMonthlyReports(getUserYear()));

        if (getMonthlyReports().size() != 0) {
            System.out.println("\nВсе отчёты за " + getUserYear() + "г. считаны\n");
        } else {
            System.out.println("\nФайлы не найдены\n");
        }
    }

    public void readAllYearlyReports() {
        setYearlyReport(repository.readYearlyReport(getUserYear()));

        if (getYearlyReport() != null) {
            System.out.println("Отчет за " + getUserYear() + ".г считан");
        } else {
            System.out.println("\nФайл не найден\n");
        }
    }

    public void comparisonOfResults() {
        System.out.println("Сравнение отчетов за " + getUserYear() + ".г");

        if (getMonthlyReports() != null && getYearlyReport() != null) {
            compareWith();
            System.out.println();
        } else {
            System.out.println("Считайте отчёты");
        }
    }

    public void compareWith() {
        int month, amount, resultOfMonthlyReport;
        boolean isModifier;
        MonthlyReport monthlyReport;

        for (int i = 0, count = 0; i < getYearlyReport().length(); i++) {
            month = getYearlyReport().getMonth().get(i);
            amount = getYearlyReport().getAmount().get(i);
            isModifier = getYearlyReport().getIsExpense().get(i);

            monthlyReport = getRepository().lookForMonthlyReport(getMonthlyReports(), month);
            resultOfMonthlyReport = countingTheAmountForMonth(monthlyReport, isModifier);

            if (amount != resultOfMonthlyReport) {
                count++;
                System.out.println("В месяце " + Month.values()[month - 1].getTitle() +
                        " допущена ошибка в рассчёте " + (isModifier ? "доходов" : "расходов"));
            } else if (count == 0 && i == getYearlyReport().length() - 1) {
                System.out.println("Ошибок в отчётах не обнаружено");
            }
        }
    }

    public void compareMonthlyReport() {
        if (getMonthlyReports() == null) {
            System.out.println("\nСначала считайте файлы\n");
            return;
        }

        MonthlyReport monthlyReport;

        for (var key : getMonthlyReports().keySet()) {

            System.out.println("Анализ месяца " + Month.values()[key - 1].getTitle());
            monthlyReport = getMonthlyReports().get(key);

            String[] resultsProfit = lookForExpenseOrProfitGoods(monthlyReport, false);
            String[] resultWaste = lookForExpenseOrProfitGoods(monthlyReport, true);

            System.out.println("Самый прибыльный товар - " + resultsProfit[0] +
                    "\nСумма товара - " + resultsProfit[1] + "\n");

            System.out.println("Самая большая трата - " + resultWaste[0] +
                    "\nСумма товара - " + resultWaste[1] + "\n");
        }
    }

    public void compareYearlyReport() {
        if (getYearlyReport() == null) {
            System.out.println("\nСначала считайте файл\n");
            return;
        }

        int difference;

        System.out.println("\nИнформация за " + getUserYear() + ".г\n");

        for (int i = 0; i < getYearlyReport().length() - 1; i += 2) {
            int month = getYearlyReport().getMonth().get(i);
            int profit = getYearlyReport().getAmount().get(i);
            int waste = getYearlyReport().getAmount().get(i + 1);
            boolean isExpense = getYearlyReport().getIsExpense().get(i);

            if (isExpense) {
                profit = getYearlyReport().getAmount().get(i + 1);
                waste = getYearlyReport().getAmount().get(i);
            }
            difference = profit - waste;
            printProfit(month, difference);
        }
        printAverageExpenseOrProfit(getYearlyReport(), false);
        printAverageExpenseOrProfit(getYearlyReport(), true);
    }

    /**
     *Я пока оставил массив String, не стал переписывать на класс, но взял на заметку.
     * И на счет регулярных выражений тоже, да и модели классов. В этой работе не стал менять,
     * хотя начал делать этот проект в другом окне с твоими советами.
     */
    public String[] lookForExpenseOrProfitGoods(MonthlyReport monthlyReport,
                                                boolean isExpenseOrProfit) {
        int product, maxProduct = 0;
        String[] array = new String[2];

        for (int i = 0; i < monthlyReport.length(); i++) {
            String itemName = monthlyReport.getItemName().get(i);
            boolean isExpense = monthlyReport.getIsExpense().get(i);
            int quantity = monthlyReport.getQuantity().get(i);
            int sumOfOne = monthlyReport.getSumOfOne().get(i);

            if (isExpense == isExpenseOrProfit) {
                product = quantity * sumOfOne;
                if (maxProduct < product) {
                    maxProduct = product;
                    array[0] = itemName;
                    array[1] = Integer.toString(maxProduct);
                }
            }
        }

        return array;
    }

    private void printProfit(int month, int difference) {
        System.out.println("Прибыль за месяц " + Month.values()[month - 1].getTitle() +
                " сотавила - " + difference);
    }

    public void printAverageExpenseOrProfit(YearlyReport yearlyReport,
                                            boolean isExpenseOrProfit) {
        int result = lookForAverageExpense(yearlyReport, isExpenseOrProfit);

        System.out.println("Средний " + (isExpenseOrProfit ? "расход" : "доход")
                + " за " + getUserYear() + "г." +
                " составил - " + result);
    }

    public int lookForAverageExpense(YearlyReport yearlyReport,
                                     boolean isExpenseOrProfit) {
        int sumWaste = 0;

        for (int i = 0; i < yearlyReport.length(); i++) {
            if (isExpenseOrProfit == yearlyReport.getIsExpense().get(i)) {
                sumWaste += yearlyReport.getAmount().get(i);
            }
        }

        return (sumWaste / (yearlyReport.length() / 2));
    }

    public void printContentOfMonthlyReports() {
        if (getMonthlyReports() == null) {
            System.out.println("\nСначала считайте файлы\n");
            return;
        }

        for (var monthlyReport : getMonthlyReports().values()) {

            System.out.println("\nНаименование: Доход/Расход: Количество: Стоимость единицы");

            for (int j = 0; j < monthlyReport.length(); j++) {

                String itemName = monthlyReport.getItemName().get(j);
                Boolean isExpense = monthlyReport.getIsExpense().get(j);
                Integer quantity = monthlyReport.getQuantity().get(j);
                Integer sumOfOne = monthlyReport.getSumOfOne().get(j);

                System.out.print(itemName + ": " +
                        (isExpense ? "Расход" : "Доход") + ": " +
                        quantity + ": " +
                        sumOfOne + "\n");
            }
        }
    }

    public void printContentYearlyReports() {
        if (getYearlyReport() == null) {
            System.out.println("\nСначала считайте файл\n");
            return;
        }

        System.out.println("\nМесяц: Сумма: Доход/Расход");

        for (int j = 0; j < getYearlyReport().length(); j++) {

            Integer month = getYearlyReport().getMonth().get(j);
            Integer amount = getYearlyReport().getAmount().get(j);
            Boolean isExpense = getYearlyReport().getIsExpense().get(j);
            String monthOfYear = Month.values()[month - 1].getTitle();

            System.out.print(monthOfYear + ": " +
                    amount + ": " +
                    (isExpense ? "Расход" : "Доход") + "\n");
        }
    }

    public int choiceYears() {
        setYearlyReport(null);

        if (getMonthlyReports() != null) {
            getMonthlyReports().clear();
        }

        System.out.print("\nВведите год - ");
        String year = getScanner().next();

        while (year.length() != 4) {
            System.err.print("\nВ годах XXI века 4 цифры - ");
            year = getScanner().next();
        }

        return Integer.parseInt(year);
    }
}
