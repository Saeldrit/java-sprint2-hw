import controller.FrontendController;

/**
 * Консольный интерфейс по работе с программной должен позволять оператору произвести одно из пяти действий по выбору:
 * 1 - Считать все месячные отчёты
 * 2 - Считать годовой отчёт
 * 3 - Сверить отчёты
 * 4 - Вывести информацию о всех месячных отчётах
 * 5 - Вывести информацию о годовом отчёте
 *
 * @author Alexey.Pavlovskiy
 * @version 1.0 от 21.07.2022
 */

public class Main {
    public static void main(String[] args) {
        FrontendController controller = new FrontendController();
        controller.postConstruct();
    }
}
