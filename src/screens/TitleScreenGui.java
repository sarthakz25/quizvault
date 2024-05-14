package screens;

import constants.CommonConstants;
import database.Category;
import database.JDBC;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class TitleScreenGui extends JFrame {
    private JComboBox categoriesMenu;
    private JTextField numOfQuestionsTextField;

    public TitleScreenGui() {
        super("Title Screen");

        setSize(400, 565);
//        to allow manual positioning of components
        setLayout(null);
//        to center frame when displayed
        setLocationRelativeTo(null);
        setResizable(false);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
//        to change bg color
        getContentPane().setBackground(CommonConstants.LIGHT_BLUE);

        addGuiComponents();
    }

    private void addGuiComponents() {
//        title label
        JLabel titleLabel = new JLabel("QuizVault");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 36));
        titleLabel.setForeground(CommonConstants.YELLOW);
        titleLabel.setBounds(0, 20, 390, 43);
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        add(titleLabel);

//        choose category label
        JLabel chooseCategoryLabel = new JLabel("Choose a category");
        chooseCategoryLabel.setFont(new Font("Arial", Font.BOLD, 16));
        chooseCategoryLabel.setForeground(CommonConstants.YELLOW);
        chooseCategoryLabel.setBounds(0, 90, 400, 20);
        chooseCategoryLabel.setHorizontalAlignment(SwingConstants.CENTER);
        add(chooseCategoryLabel);

//        category dropdown
//        String[] categories = new String[]{"Math", "Programming", "History", "Geography"}; // temp categories list
        ArrayList<String> categoryList = JDBC.getCategories();
        categoriesMenu = new JComboBox(categoryList.toArray());
        categoriesMenu.setForeground(CommonConstants.DARK_BLUE);
        categoriesMenu.setBounds(25, 120, 339, 35);
        add(categoriesMenu);

//        num of questions label
        JLabel numOfQuestionsLabel = new JLabel("Number of Questions: ");
        numOfQuestionsLabel.setFont(new Font("Arial", Font.BOLD, 16));
        numOfQuestionsLabel.setForeground(CommonConstants.YELLOW);
        numOfQuestionsLabel.setBounds(23, 190, 172, 20);
        numOfQuestionsLabel.setHorizontalAlignment(SwingConstants.CENTER);
        add(numOfQuestionsLabel);

//        num of questions text field
        numOfQuestionsTextField = new JTextField("10");
        numOfQuestionsTextField.setFont(new Font("Arial", Font.BOLD, 16));
        numOfQuestionsTextField.setForeground(CommonConstants.DARK_BLUE);
        numOfQuestionsTextField.setBounds(205, 190, 160, 25);
        numOfQuestionsTextField.setHorizontalAlignment(SwingConstants.CENTER);
        add(numOfQuestionsTextField);

//        start button
        JButton startButton = new JButton("Start");
        startButton.setFont(new Font("Arial", Font.BOLD, 16));
        startButton.setBackground(CommonConstants.LIGHT_GREEN);
        startButton.setBounds(65, 290, 262, 35);
        startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (validateInput()) {
//                    retrieve category
                    Category category = JDBC.getCategory(categoriesMenu.getSelectedItem().toString());

//                    invalid category
                    if (category == null) return;

                    int numOfQuestions = Integer.parseInt(numOfQuestionsTextField.getText());

//                    load quiz screen
                    QuizScreenGui quizScreenGui = new QuizScreenGui(category, numOfQuestions);
                    quizScreenGui.setLocationRelativeTo(TitleScreenGui.this);

//                    dispose this screen
                    TitleScreenGui.this.dispose();

//                    display quiz screen
                    quizScreenGui.setVisible(true);
                }
            }
        });
        add(startButton);

//        exit button
        JButton exitButton = new JButton("Exit");
        exitButton.setFont(new Font("Arial", Font.BOLD, 16));
        exitButton.setBackground(CommonConstants.LIGHT_RED);
        exitButton.setBounds(65, 350, 262, 35);
        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
//                dispose this screen
                TitleScreenGui.this.dispose();
            }
        });
        add(exitButton);

//        create a question button
        JButton createQuestionButton = new JButton("Create a Question");
        createQuestionButton.setFont(new Font("Arial", Font.BOLD, 16));
        createQuestionButton.setBackground(CommonConstants.LIGHT_BLUE);
        createQuestionButton.setBounds(65, 420, 262, 35);
        createQuestionButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
//                create question screen gui
                CreateQuestionScreenGui createQuestionScreenGui = new CreateQuestionScreenGui();
                createQuestionScreenGui.setLocationRelativeTo(TitleScreenGui.this);

//                dispose this screen
                TitleScreenGui.this.dispose();

//                display create question screen gui
                createQuestionScreenGui.setVisible(true);
            }
        });
        add(createQuestionButton);
    }

    //    true if input valid, else false
    private boolean validateInput() {
//        num of questions field must not be empty
        if (numOfQuestionsTextField.getText().replaceAll(" ", "").length() <= 0) return false;

//        no category chosen
        if (categoriesMenu.getSelectedItem() == null) return false;

        return true;
    }
}
