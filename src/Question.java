class Question {
    private String question;
    private String optionA;
    private String optionB;
    private String optionC;
    private char correctAnswer;

    public Question(String question, String optionA, String optionB, String optionC, char correctAnswer) {
        this.question = question;
        this.optionA = optionA;
        this.optionB = optionB;
        this.optionC = optionC;
        this.correctAnswer = correctAnswer;
    }

    public void printQuestion() {
        System.out.println(question + "\nA. " + optionA + "\nB. " + optionB + "\nC. " + optionC);
    }

    public boolean checkAnswer(char answer) {
        return Character.toUpperCase(answer) == correctAnswer;
    }
}
