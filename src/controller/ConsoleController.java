package controller;

import service.ServicePrintOut;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ConsoleController {

    private static final List<String> MENU;
    private final Scanner scanner;
    private final ServicePrintOut service;

    static {
        MENU = new ArrayList<>();
        initializationMenu();
    }

    public ConsoleController() {
        this.scanner = new Scanner(System.in);
        this.service = new ServicePrintOut(scanner);
    }

    public static void setMenu(String item) {
        MENU.add(item);
    }

    private static void initializationMenu() {
        setMenu("1 - Считать все месячные отчёты");
        setMenu("2 - Считать годовой отчёт");
        setMenu("3 - Сверить отчёты");
        setMenu("4 - Вывести информацию о всех месячных отчётах");
        setMenu("5 - Вывести информацию о годовом отчёте");
        setMenu("6 - Вывести содержимое месячных отчётов");
        setMenu("7 - Вывести содержимое годового отчёта");
        setMenu("8 - Изменить год отчётов");
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

    public ServicePrintOut getService() {
        return service;
    }

    public List<String> getMenu() {
        return MENU;
    }

    private void processingMenuItems(int item) {
        switch (item) {
            case 1:
                getService().readAllMonthlyReportsByYear();
                break;
            case 2:
                getService().readAllYearlyReports();
                break;
            case 3:
                getService().comparisonOfResults();
                break;
            case 4:
                getService().compareMonthlyReport();
                break;
            case 5:
                getService().compareYearlyReport();
                break;
            case 6:
                getService().printContentOfMonthlyReports();
                break;
            case 7:
                getService().printContentYearlyReports();
                break;
            case 8:
                getService().setUserYear(
                        getService().choiceYears());
                break;
            default:
                System.err.println("Давай еще разок");
        }
    }
}
