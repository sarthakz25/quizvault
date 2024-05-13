package screens;

import constants.CommonConstants;
import database.Answer;
import database.Category;
import database.JDBC;
import database.Question;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class QuizScreenGui extends JFrame {
    private JLabel scoreLabel;
    private JTextArea questionTextArea;
    private JButton[] answerButtons;

    //    current quiz category
    private Category category;

    //    questions based on category
    private ArrayList<Question> questions;
    private Question currentQuestion;
    private int currentQuestionNumber;
    private int numOfQuestions;
    private int score;
    private boolean firstChoiceMade;

    public QuizScreenGui(Category category, int numOfQuestions) {
        super("QuizVault");
        setSize(400, 565);
        setLayout(null);
        setLocationRelativeTo(null);
        setResizable(false);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        getContentPane().setBackground(CommonConstants.LIGHT_BLUE);

        answerButtons = new JButton[4];
        this.category = category;

//        load questions based on category
        questions = JDBC.getQuestions(category);

//        adjust numOfQuestions to choose min btw user input and total in the db
        this.numOfQuestions = Math.min(numOfQuestions, questions.size());

//        load answers for each question
        for (Question question : questions) {
            ArrayList<Answer> answers = JDBC.getAnswers(question);
            question.setAnswers(answers);
        }

//        load current question
        currentQuestion = questions.get(currentQuestionNumber);

        addGuiComponents();
    }

    private void addGuiComponents() {
//        topic label
        JLabel topicLabel = new JLabel("Topic: " + category.getCategoryName());
        topicLabel.setFont(new Font("Arial", Font.BOLD, 14));
        topicLabel.setForeground(CommonConstants.YELLOW);
        topicLabel.setBounds(15, 15, 250, 20);
        add(topicLabel);

//        score label
        scoreLabel = new JLabel("Score: " + score + " / " + numOfQuestions);
        scoreLabel.setFont(new Font("Arial", Font.BOLD, 14));
        scoreLabel.setForeground(CommonConstants.DARK_BLUE);
        scoreLabel.setBounds(115, 15, 150, 20);
        add(scoreLabel);

//        question text area
        questionTextArea = new JTextArea(currentQuestion.getQuestionText());
        questionTextArea.setFont(new Font("Arial", Font.BOLD, 16));
        questionTextArea.setForeground(CommonConstants.YELLOW);
        questionTextArea.setBackground(CommonConstants.LIGHT_BLUE);
        questionTextArea.setBounds(15, 50, 358, 75);
        questionTextArea.setLineWrap(true);
        questionTextArea.setWrapStyleWord(true);
        questionTextArea.setEditable(false);
        questionTextArea.setFocusable(false);
        add(questionTextArea);

        addAnswerComponents();

//        return to title
        JButton returnToTitleButton = new JButton("Return");
        returnToTitleButton.setFont(new Font("Arial", Font.BOLD, 14));
        returnToTitleButton.setBackground(Color.lightGray);
        returnToTitleButton.setBounds(20, 473, 90, 35);
        add(returnToTitleButton);

//        next button
        JButton nextButton = new JButton("Next");
        nextButton.setFont(new Font("Arial", Font.BOLD, 14));
        nextButton.setBackground(Color.darkGray);
        nextButton.setForeground(CommonConstants.LIGHT_BLUE);
        nextButton.setBounds(295, 473, 75, 35);
        nextButton.setVisible(false);
        add(nextButton);

//        prev button
//        JButton prevButton = new JButton("Prev");
//        prevButton.setFont(new Font("Arial", Font.BOLD, 14));
//        prevButton.setBackground(Color.darkGray);
//        prevButton.setForeground(CommonConstants.LIGHT_BLUE);
//        prevButton.setBounds(210, 473, 75, 35);
//        add(prevButton);
    }

    private void addAnswerComponents() {
//        60px vertical space btw each answer button
        int verticalSpacing = 80;

        for (int i = 0; i < currentQuestion.getAnswers().size(); i++) {
            Answer answer = currentQuestion.getAnswers().get(i);

            JButton answerButton = new JButton("<html><div style='text-align: left;'>" + answer.getAnswerText() + "</div></html>");
            answerButton.setBackground(Color.white);
            answerButton.setForeground(CommonConstants.DARK_BLUE);
            answerButton.setBounds(20, 135 + (i * verticalSpacing), 350, 75);
            answerButton.setFont(new Font("Arial", Font.BOLD, 14));
            answerButtons[i] = answerButton;
            add(answerButtons[i]);
        }
    }
}
