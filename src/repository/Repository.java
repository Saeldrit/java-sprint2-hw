package repository;

import model.Modifier;
import model.MonthlyReport;
import model.YearlyReport;
import service.Builder;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Repository {

    private final Builder builder;
    private final String directory;
    private final List<File> files;

    public Repository() {
        this.builder = new Builder();
        this.directory = "resources";
        this.files = readFilesName(directory);
    }

    public Builder getBuilder() {
        return builder;
    }

    public List<File> getFiles() {
        return files;
    }

    public String getDirectory() {
        return directory;
    }

    public List<String> readFileContents(String path) {
        List<String> contentOfFile = new ArrayList<>();

        try (BufferedReader csvReader = new BufferedReader(new FileReader(path))) {
            String line;
            while ((line = csvReader.readLine()) != null) {
                contentOfFile.add(line);
            }
        } catch (IOException e) {
            throw new NullPointerException(e.getMessage() + " where is your f*** file?");
        }

        return contentOfFile;
    }

    public List<File> readFilesName(String directory) {
        List<File> files;

        try (Stream<Path> paths = Files.walk(Paths.get(directory + "/"))) {
            files = paths.filter(Files::isRegularFile)
                    .map(Path::toFile)
                    .collect(Collectors.toList());
        } catch (IOException e) {
            throw new NullPointerException(e.getMessage() + " check path");
        }

        return files;
    }

    public List<MonthlyReport> readAllMonthlyReports(int userYear) {
        List<MonthlyReport> monthlyReports = new ArrayList<>();
        List<File> filesModMonths = getFilesByModifier(getFiles(), Modifier.MONTH.getTitle());

        for (var file : filesModMonths) {
            int fileYear;
            String path = file.getName();
            StringBuilder stringBuilder = new StringBuilder();

            for (int i = 2; i < 6; i++) {
                stringBuilder.append(path.charAt(i));
            }

            fileYear = Integer.parseInt(stringBuilder.toString());

            if (userYear == fileYear) {
                monthlyReports.add(getBuilder()
                        .builderObjectMonthlyReport(
                                readFileContents(getDirectory() + "/" +
                                        Modifier.MONTH.getTitle() + "." +
                                        fileYear + "" +
                                        file.getName().charAt(6) +
                                        "" + file.getName().charAt(7) + ".csv")));
            }
        }

        return monthlyReports;
    }

    public MonthlyReport readMonthlyReport(int userYear, int month) {
        MonthlyReport monthlyReport = null;
        List<File> months = getFilesByModifier(getFiles(), Modifier.MONTH.getTitle());

        for (var file : months) {
            int fileYear, fileMonth;
            String path = file.getName();
            StringBuilder stringYear = new StringBuilder();
            StringBuilder stringMonth = new StringBuilder();

            for (int i = 2; i < 6; i++) {
                stringYear.append(path.charAt(i));
            }

            for (int i = 6; i < 8; i++) {
                stringMonth.append(path.charAt(i));
            }

            fileYear = Integer.parseInt(stringYear.toString());
            fileMonth = Integer.parseInt(stringMonth.toString());

            if (userYear == fileYear && fileMonth == month) {
                monthlyReport = getBuilder()
                        .builderObjectMonthlyReport(
                                readFileContents(getDirectory() + "/" +
                                        Modifier.MONTH.getTitle() + "." +
                                        fileYear + "" +
                                        file.getName().charAt(6) +
                                        "" + file.getName().charAt(7) + ".csv"));
                break;
            }
        }

        return monthlyReport;
    }

    public YearlyReport readYearlyReport(int userYear) {
        YearlyReport yearlyReports = null;
        List<File> years = getFilesByModifier(getFiles(), Modifier.YEAR.getTitle());

        for (var year : years) {
            int fileYear;
            String path = year.getName();
            StringBuilder stringBuilder = new StringBuilder();

            for (int i = 2; i < 6; i++) {
                stringBuilder.append(path.charAt(i));
            }

            fileYear = Integer.parseInt(stringBuilder.toString());

            if (userYear == fileYear) {
                yearlyReports = getBuilder()
                        .builderObjectYearlyReport(
                                readFileContents(getDirectory() + "/" +
                                        Modifier.YEAR.getTitle() + "." +
                                        fileYear + "" +
                                        ".csv"));
                break;
            }
        }

        return yearlyReports;
    }

    public List<File> getFilesByModifier(List<File> files, char modifier) {
        List<File> lists = new ArrayList<>();

        for (var file : files) {
            String name = file.getName();

            if (name.charAt(0) == modifier) {
                lists.add(file);
            }
        }

        return lists;
    }
}
