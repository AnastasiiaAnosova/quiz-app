import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class QuizApp {
    private static final String FILE_NAME = "questions.txt";

    public static void main(String[] args) {
        List<Question> questions = loadQuestions(FILE_NAME);

        if (questions.isEmpty()) {
            System.out.println("Žádné otázky nenalezeny!");
            return;
        }

        Scanner scanner = new Scanner(System.in);
        int score = 0;

        System.out.println("Vítej v kvízu! Odpovídej A, B nebo C.\n");

        for (Question q : questions) {
            q.printQuestion();
            System.out.println("Your answer: ");
            char answer = scanner.next().charAt(0);

            if (q.checkAnswer(answer)) {
                System.out.println("Correctly!");
                score++;
            } else {
                System.out.println("Wrong :(");
            }
        }

        System.out.println("Your score: " + score + " / " + questions.size());
        scanner.close();
    }

    private static List<Question> loadQuestions(String filename) {
        List<Question> questions = new ArrayList<>();
        File file = new File(filename);

        if(!file.exists()) {
            System.out.println("Soubor s otázkami neexistuje!");
            return questions;
        }

        try (Scanner fileScanner = new Scanner(file)) {
            while (fileScanner.hasNextLine()) {
                String line = fileScanner.nextLine();
                String[] parts = line.split("\\|");
                if (parts.length == 5) {
                    String question = parts[0];
                    String optionA = parts[1];
                    String optionB = parts[2];
                    String optionC = parts[3];
                    char correct = parts[4].toUpperCase().charAt(0);
                    questions.add(new Question(question, optionA, optionB, optionC, correct));
                }
            }
        } catch (IOException e) {
            System.out.println("Chyba při čtení souboru: " + e.getMessage());
        }
        return questions;
    }
}
