package controller;

import model.Month;
import model.MonthlyReport;
import model.YearlyReport;
import repository.Repository;
import service.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class FrontendController {

    private static final Scanner scanner;
    private static final List<String> menu;
    private static final Repository repository;
    private static final Service service;
    private Integer userYear;
    private List<MonthlyReport> monthlyReports;
    private YearlyReport yearlyReports;

    static {
        scanner = new Scanner(System.in);
        menu = new ArrayList<>();
        repository = new Repository();
        service = new Service();
        initializationMenu();
    }

    public FrontendController() {
        this.userYear = choiceYears();
        this.monthlyReports = repository.readAllMonthlyReports(userYear);
        this.yearlyReports = repository.readYearlyReport(userYear);
    }

    public static void setMenu(String item) {
        menu.add(item);
    }

    private static void initializationMenu() {
        setMenu("1 - Считать все месячные отчёты");
        setMenu("2 - Считать годовой отчёт");
        setMenu("3 - Сверить отчёты");
        setMenu("4 - Вывести информацию о всех месячных отчётах");
        setMenu("5 - Вывести информацию о годовом отчёте");
        setMenu("6 - Изменить год отчётов");
        setMenu("0 - Выход");
    }

    public void postConstruct() {
        getMenu().forEach(System.out::println);
        System.out.print("Ввод - ");
        int userInput = scanner.nextInt();

        while (userInput != 0) {
            processingMenuItems(userInput);
            getMenu().forEach(System.out::println);
            System.out.print("Ввод - ");
            userInput = scanner.nextInt();
        }
        System.out.println("Программа завершена");
    }

    public void setMonthlyReports(List<MonthlyReport> monthlyReports) {
        this.monthlyReports = monthlyReports;
    }

    public void setYearlyReports(YearlyReport yearlyReports) {
        this.yearlyReports = yearlyReports;
    }

    public void setUserYear(Integer userYear) {
        this.userYear = userYear;
    }

    public Integer getUserYear() {
        return userYear;
    }

    public List<MonthlyReport> getMonthlyReports() {
        return monthlyReports;
    }

    public YearlyReport getYearlyReports() {
        return yearlyReports;
    }

    public List<String> getMenu() {
        return menu;
    }

    private void processingMenuItems(int item) {
        switch (item) {
            case 1:
                readAllMonthlyReportsByYear();
                break;
            case 2:
                readAllYearlyReports();
                break;
            case 3:
                comparisonOfResults();
                break;
            case 4:
                printMonthlyReports();
                break;
            case 5:
                printYearlyReports();
                break;
            case 6:
                setUserYear(choiceYears());
                break;
            default:
                System.err.println("Давай еще разок");
        }
    }

    private void readAllMonthlyReportsByYear() {
        setMonthlyReports(repository.readAllMonthlyReports(getUserYear()));

        if (getMonthlyReports().size() != 0) {
            System.out.println("Все месяца за " + getUserYear() + "г. считаны\n");
        } else {
            System.out.println("\nФайлы не найдены\n");
        }
    }

    private void readAllYearlyReports() {
        setYearlyReports(repository.readYearlyReport(getUserYear()));

        if (getYearlyReports() != null) {
            System.out.println("Отчет за " + getUserYear() + ".г считан");
        } else {
            System.out.println("\nФайл не найден\n");
        }
    }

    private void comparisonOfResults() {
        System.out.println("Сравнение отчетов за " + getUserYear() + ".г");

        if (getMonthlyReports().size() != 0 && getYearlyReports() != null) {
            compareWith();
            System.out.println();
        } else {
            System.out.println("Считайте отчёты");
        }
    }

    private void compareWith() {
        int month, amount, resultOfMonthlyReport;
        boolean isModifier;
        MonthlyReport monthlyReport;

        for (int i = 0, count = 0; i < getYearlyReports().length(); i++) {
            month = getYearlyReports().getMonth().get(i);
            amount = getYearlyReports().getAmount().get(i);
            isModifier = getYearlyReports().getIsExpense().get(i);

            monthlyReport = repository.readMonthlyReport(getUserYear(), month);
            resultOfMonthlyReport = service.countingTheAmountForMonth(monthlyReport, isModifier);

            if (amount != resultOfMonthlyReport) {
                count++;
                System.out.println("В месяце " + Month.values()[month - 1].getTitle() +
                        " допущена ошибка в рассчёте " + (isModifier ? "доходов" : "расходов"));
            } else if (count == 0 && i == getYearlyReports().length() - 1) {
                System.out.println("Ошибок в отчётах не обнаружено");
            }
        }
    }

    private void printMonthlyReports() {
        if (getMonthlyReports().size() == 0) {
            System.out.println("\nСначала считайте файлы\n");
        }

        for (var monthlyReport : getMonthlyReports()) {

            System.out.println("\nНаименование: Доход/Расход: Количество: Стоимость единицы");

            for (int j = 0; j < monthlyReport.length(); j++) {

                String itemName = monthlyReport.getItemName().get(j);
                Boolean isExpense = monthlyReport.getIsExpense().get(j);
                Integer quantity = monthlyReport.getQuantity().get(j);
                Integer sumOfOne = monthlyReport.getSumOfOne().get(j);

                System.out.print(itemName + ": " +
                        (isExpense ? "Доход" : "Расход") + ": " +
                        quantity + ": " +
                        sumOfOne + "\n");
            }
        }
    }

    private void printYearlyReports() {
        if (getYearlyReports() == null) {
            System.out.println("\nСначала считайте файлы\n");
        }

        System.out.println("\nМесяц: Сумма: Доход/Расход");

        for (int j = 0; j < getYearlyReports().length(); j++) {

            Integer month = getYearlyReports().getMonth().get(j);
            Integer amount = getYearlyReports().getAmount().get(j);
            Boolean isExpense = getYearlyReports().getIsExpense().get(j);
            String monthOfYear = Month.values()[month - 1].getTitle();

            System.out.print(monthOfYear + ": " +
                    amount + ": " +
                    (isExpense ? "Доход" : "Расход") + "\n");
        }
    }

    private static int choiceYears() {
        System.out.print("\nВведите год - ");
        String year = scanner.next();

        while (year.length() != 4) {
            System.err.print("\nВ годах XXI века 4 цифры - ");
            year = scanner.next();
        }

        return Integer.parseInt(year);
    }
}
