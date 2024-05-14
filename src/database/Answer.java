package database;

// represents a row from answer table in the db
public class Answer {
    private int answerId;
    private int questionId;
    private String answerText;
    private boolean isCorrect;

    public int getAnswerId() {
        return answerId;
    }

    public int getQuestionId() {
        return questionId;
    }

    public String getAnswerText() {
        return answerText;
    }

    public boolean isCorrect() {
        return isCorrect;
    }

    public Answer(int answerId, int questionId, String answerText, Boolean isCorrect) {
        this.answerId = answerId;
        this.questionId = questionId;
        this.answerText = answerText;
        this.isCorrect = isCorrect;
    }
}
