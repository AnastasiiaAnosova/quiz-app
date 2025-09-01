import java.io.File;
import java.io.IOException;
import java.util.*;

public class QuizApp {
    private static final String FILE_NAME = "questions.txt";
    private static final int TIME_LIMIT_SECONDS = 10;

    public static void main(String[] args) {
        List<Question> questions = loadQuestions(FILE_NAME);

        if (questions.isEmpty()) {
            System.out.println("No questions found!");
            return;
        }

        Scanner scanner = new Scanner(System.in);

        Set<String> categories = new HashSet<>();
        for (Question q: questions) {
            categories.add(q.getCategory());
        }

        System.out.println("Available categories: ");
        int i = 1;
        List<String> categoryList = new ArrayList<>(categories);
        for (String c : categoryList) {
            System.out.println(i + ". " + c);
            i++;
        }

//        System.out.println("Select category (by number): ");
//        if (!scanner.hasNextInt()) {
//            System.out.println("Enter the option by number!");
//            scanner.nextLine();
//            return;
//        }
//        int choice = scanner.nextInt();
//        scanner.nextLine();
//        if (choice < 1 || choice > categoryList.size()) {
//            System.out.println("Invalid choice!");
//            return;
//        }
        int choice = -1;
        while(true) {
            System.out.print("Select category (by number): ");
            if (!scanner.hasNextInt()) {
                System.out.println("\nEnter the option by number!");
                scanner.nextLine(); // clear buffer
                continue;
            }
            choice = scanner.nextInt();
            scanner.nextLine();
            if (choice < 1 || choice > categoryList.size()) {
                System.out.println("Invalid choice!");
            } else {
                break;
            }
        }

        String selectedCategory = categoryList.get(choice - 1);
        System.out.println("\nYou have selected a category: " + selectedCategory);

        List<Question> filteredQuestions = new ArrayList<>();
        for (Question q : questions) {
            if (q.getCategory().equalsIgnoreCase(selectedCategory)) {
                filteredQuestions.add(q);
            }
        }

        //Random order of questions
        Collections.shuffle(filteredQuestions);

        int score = 0;
        System.out.println("\nThe quiz is starting!\nYou have " + TIME_LIMIT_SECONDS + " seconds to answer each question." +
                "\nIf you don't have time to answer, then the answer will be considered wrong.\n");

        for (Question q : filteredQuestions) {
            q.printQuestion();
            System.out.print("Your answer A, B or C: ");

            long startTime = System.currentTimeMillis();
            String input = "";
            while (System.currentTimeMillis() - startTime < TIME_LIMIT_SECONDS * 1000 && input.isEmpty()) {
                if (scanner.hasNext()) {
//                    input = scanner.next();
                    input = scanner.next().trim().toUpperCase();
                    if(!(input.equals("A") || input.equals("B") || input.equals("C"))) {
                        System.out.println("Choose A, B or C!");
                        input = "";
                    }
                }
            }

            //char answer = scanner.next().charAt(0);
            if (input.isEmpty()) {
                System.out.println("Time is up! Wrong answer.");
            } else {
                char answer = input.charAt(0);
                if (q.checkAnswer(answer)) {
                    System.out.println("Correctly!\n");
                    score++;
                } else {
                    System.out.println("Wrong!\n");
                }
            }
        }

        System.out.println("Your score: " + score + " / " + filteredQuestions.size());
        scanner.close();
    }

    private static List<Question> loadQuestions(String filename) {
        List<Question> questions = new ArrayList<>();
        File file = new File(filename);

        if(!file.exists()) {
            System.out.println("The question file does not exist!");
            return questions;
        }

        try (Scanner fileScanner = new Scanner(file)) {
            while (fileScanner.hasNextLine()) {
                String line = fileScanner.nextLine();
                String[] parts = line.split("\\|");
                if (parts.length == 6) {
                    String category = parts[0];
                    String question = parts[1];
                    String optionA = parts[2];
                    String optionB = parts[3];
                    String optionC = parts[4];
                    char correct = parts[5].toUpperCase().charAt(0);
                    questions.add(new Question(category, question, optionA, optionB, optionC, correct));
                }
            }
        } catch (IOException e) {
            System.out.println("Error reading file: " + e.getMessage());
        }
        return questions;
    }
}
