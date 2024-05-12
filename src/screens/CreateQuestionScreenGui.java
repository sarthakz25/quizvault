package screens;

import constants.CommonConstants;
import database.JDBC;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class CreateQuestionScreenGui extends JFrame {
    private JTextArea questionTextArea;
    private JTextField categoryTextField;
    private JTextField[] answerTextFields;
    private ButtonGroup buttonGroup;
    private JRadioButton[] answerRadioButtons;

    public CreateQuestionScreenGui() {
        super("Create a Question");
        setSize(851, 565);
        setLayout(null);
        setLocationRelativeTo(null);
        setResizable(false);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        getContentPane().setBackground(CommonConstants.LIGHT_BLUE);

        answerRadioButtons = new JRadioButton[4];
        answerTextFields = new JTextField[4];
        buttonGroup = new ButtonGroup();

        addGuiComponents();
    }

    private void addGuiComponents() {
//        title label
        JLabel titleLabel = new JLabel("Create your own Question");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setForeground(CommonConstants.YELLOW);
        titleLabel.setBounds(50, 35, 310, 29);
        add(titleLabel);

//        question label
        JLabel questionLabel = new JLabel("Question: ");
        questionLabel.setFont(new Font("Arial", Font.BOLD, 16));
        questionLabel.setForeground(CommonConstants.YELLOW);
        questionLabel.setBounds(50, 90, 93, 20);
        add(questionLabel);

//        question text area
        questionTextArea = new JTextArea();
        questionTextArea.setFont(new Font("Arial", Font.BOLD, 16));
        questionTextArea.setForeground(CommonConstants.DARK_BLUE);
        questionTextArea.setBounds(50, 120, 310, 110);
        questionTextArea.setLineWrap(true);
        questionTextArea.setWrapStyleWord(true);
        add(questionTextArea);

//        category label
        JLabel categoryLabel = new JLabel("Category: ");
        categoryLabel.setFont(new Font("Arial", Font.BOLD, 16));
        categoryLabel.setForeground(CommonConstants.YELLOW);
        categoryLabel.setBounds(50, 270, 93, 20);
        add(categoryLabel);

//        category text field
        categoryTextField = new JTextField();
        categoryTextField.setFont(new Font("Arial", Font.BOLD, 16));
        categoryTextField.setForeground(CommonConstants.DARK_BLUE);
        categoryTextField.setBounds(50, 300, 310, 35);
        add(categoryTextField);

        addAnswerComponents();

//        submit button
        JButton submitButton = new JButton("Submit");
        submitButton.setFont(new Font("Arial", Font.BOLD, 16));
        submitButton.setBackground(CommonConstants.LIGHT_GREEN);
        submitButton.setBounds(150, 385, 210, 45);
        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (validateInput()) {
                    String question = questionTextArea.getText();
                    String category = categoryTextField.getText();
                    String[] answers = new String[answerTextFields.length];
                    int correctIndex = 0;

                    for (int i = 0; i < answerTextFields.length; i++) {
                        answers[i] = answerTextFields[i].getText();
                        if (answerRadioButtons[i].isSelected()) {
                            correctIndex = i;
                        }
                    }

//                    update the db
                    if (JDBC.saveQuestionCategoryAndAnswersToDb(question, category, answers, correctIndex)) {
//                        update successful
                        JOptionPane.showMessageDialog(CreateQuestionScreenGui.this, "Question added successfully!");

//                        reset fields
                        resetFields();
                    } else {
//                        update failed
                        JOptionPane.showMessageDialog(CreateQuestionScreenGui.this, "Failed to add question.");
                    }
                } else {
//                    invalid input
                    JOptionPane.showMessageDialog(CreateQuestionScreenGui.this, "Error: Invalid Input");
                }
            }
        });
        add(submitButton);

//        go back label
        JLabel goBackLabel = new JLabel("Go Back");
        goBackLabel.setFont(new Font("Arial", Font.BOLD, 16));
        goBackLabel.setForeground(CommonConstants.LIGHT_RED);
        goBackLabel.setBounds(45, 385, 100, 45);
        goBackLabel.setHorizontalAlignment(SwingConstants.CENTER);
        goBackLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
//                display title screen
                TitleScreenGui titleScreenGui = new TitleScreenGui();
                titleScreenGui.setLocationRelativeTo(CreateQuestionScreenGui.this);

//                dispose this screen
                CreateQuestionScreenGui.this.dispose();

//                make title screen visible
                titleScreenGui.setVisible(true);
            }
        });
        add(goBackLabel);
    }

    private void addAnswerComponents() {
//        vertical space btw each ans
        int verticalSpacing = 100;

//        4 answer labels, radio buttons, text fields
        for (int i = 0; i < 4; i++) {
//            answer labels
            JLabel answerLabels = new JLabel("Answer #" + (i + 1));
            answerLabels.setFont(new Font("Arial", Font.BOLD, 16));
            answerLabels.setBounds(470, 60 + (i * verticalSpacing), 93, 20);
            answerLabels.setForeground(CommonConstants.YELLOW);
            add(answerLabels);

//            radio buttons
            answerRadioButtons[i] = new JRadioButton();
            answerRadioButtons[i].setBounds(440, 100 + (i * verticalSpacing), 21, 21);
            answerRadioButtons[i].setBackground(null);
            buttonGroup.add(answerRadioButtons[i]);
            add(answerRadioButtons[i]);

//            answer text field
            answerTextFields[i] = new JTextField();
            answerTextFields[i].setFont(new Font("Arial", Font.PLAIN, 16));
            answerTextFields[i].setBounds(470, 90 + (i * verticalSpacing), 310, 40);
            answerTextFields[i].setForeground(CommonConstants.DARK_BLUE);
            add(answerTextFields[i]);
        }

//        to give first radio button a default value
        answerRadioButtons[0].setSelected(true);

    }

    //    true if valid, else false
    private boolean validateInput() {
//        to make sure question field is not empty
        if (questionTextArea.getText().replaceAll(" ", "").isEmpty()) return false;

//        to make sure category field is not empty
        if (categoryTextField.getText().replaceAll(" ", "").isEmpty()) return false;

//        to make sure all answer fields are not empty
        for (int i = 0; i < answerTextFields.length; i++) {
            if (answerTextFields[i].getText().replaceAll(" ", "").isEmpty()) return false;
        }
        return true;
    }

    private void resetFields() {
        questionTextArea.setText("");
        categoryTextField.setText("");
        for (int i = 0; i < answerTextFields.length; i++) {
            answerTextFields[i].setText("");
        }
    }
}
