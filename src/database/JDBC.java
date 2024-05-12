package database;

import java.sql.*;
import java.util.ArrayList;

public class JDBC {
    //    mysql config
    private static final String DB_URL = "jdbc:mysql://localhost:3306/quizvault_db";
    private static final String DB_USERNAME = "root";
    private static final String DB_PASSWORD = "sar@123";

    public static boolean saveQuestionCategoryAndAnswersToDb(String question, String category, String[] answers, int correctIndex) {
        try {
//            establish a db connection
            Connection connection = DriverManager.getConnection(
                    DB_URL, DB_USERNAME, DB_PASSWORD
            );

//            insert category if new, else retrieve it from the db
            Category categoryObj = getCategory(category);
            if (categoryObj == null) {
//                insert new category to the db
                categoryObj = insertCategory(category);
            }

//            insert question to the db
            Question questionObj = insertQuestion(categoryObj, question);

//            insert answers to the db
            return insertAnswers(questionObj, answers, correctIndex);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    //    question methods
    private static Question insertQuestion(Category category, String questionText) {
        try {
            Connection connection = DriverManager.getConnection(
                    DB_URL, DB_USERNAME, DB_PASSWORD
            );

            PreparedStatement insertQuestionQuery = connection.prepareStatement(
                    "INSERT INTO QUESTION(CATEGORY_ID, QUESTION_TEXT) " +
                            "VALUES(?, ?)",
                    Statement.RETURN_GENERATED_KEYS
            );
            insertQuestionQuery.setInt(1, category.getCategoryId());
            insertQuestionQuery.setString(2, questionText);
            insertQuestionQuery.executeUpdate();

//            check for question id
            ResultSet resultSet = insertQuestionQuery.getGeneratedKeys();
            if (resultSet.next()) {
                int questionId = resultSet.getInt(1);
                return new Question(questionId, category.getCategoryId(), questionText);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

//        return null if there was an error
        return null;
    }

    //    category methods
    private static Category getCategory(String category) {
        try {
            Connection connection = DriverManager.getConnection(
                    DB_URL, DB_USERNAME, DB_PASSWORD
            );

            PreparedStatement getCategoryQuery = connection.prepareStatement(
                    "SELECT * FROM CATEGORY WHERE CATEGORY_NAME = ?"
            );
            getCategoryQuery.setString(1, category);

//            execute query, store results
            ResultSet resultSet = getCategoryQuery.executeQuery();
            if (resultSet.next()) {
//                found the category
                int categoryId = resultSet.getInt("category_id");
                return new Category(categoryId, category);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

//        return null if category not in the db
        return null;
    }

    public static ArrayList<String> getCategories() {
        ArrayList<String> categoryList = new ArrayList<>();

        try {
            Connection connection = DriverManager.getConnection(
                    DB_URL, DB_USERNAME, DB_PASSWORD
            );

            Statement getCategoriesQuery = connection.createStatement();
            ResultSet resultSet = getCategoriesQuery.executeQuery(
                    "SELECT * FROM CATEGORY"
            );

            while (resultSet.next()) {
                String categoryName = resultSet.getString("category_name");
                categoryList.add(categoryName);
            }

            return categoryList;
        } catch (Exception e) {
            e.printStackTrace();
        }

//        null if could not find categories
        return null;
    }

    private static Category insertCategory(String category) {
        try {
            Connection connection = DriverManager.getConnection(
                    DB_URL, DB_USERNAME, DB_PASSWORD
            );

            PreparedStatement insertCategoryQuery = connection.prepareStatement(
                    "INSERT INTO CATEGORY(CATEGORY_NAME) " +
                            "VALUES(?)",
                    Statement.RETURN_GENERATED_KEYS
            );
            insertCategoryQuery.setString(1, category);
            insertCategoryQuery.executeUpdate();

//            get category id that gets auto incremented for each new insert
            ResultSet resultSet = insertCategoryQuery.getGeneratedKeys();
            if (resultSet.next()) {
                int categoryId = resultSet.getInt(1);
                return new Category(categoryId, category);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

//        return null if there was an error
        return null;
    }

    //    answer methods, if true then successfully inserted, else false
    private static boolean insertAnswers(Question question, String[] answers, int correctIndex) {
        try {
            Connection connection = DriverManager.getConnection(
                    DB_URL, DB_USERNAME, DB_PASSWORD
            );

            PreparedStatement insertAnswerQuery = connection.prepareStatement(
                    "INSERT INTO ANSWER(QUESTION_ID, ANSWER_TEXT, IS_CORRECT) " +
                            "VALUES (?, ?, ?)"
            );
            insertAnswerQuery.setInt(1, question.getQuestionId());

            for (int i = 0; i < answers.length; i++) {
                insertAnswerQuery.setString(2, answers[i]);

                if (i == correctIndex) {
                    insertAnswerQuery.setBoolean(3, true);
                } else {
                    insertAnswerQuery.setBoolean(3, false);
                }

                insertAnswerQuery.executeUpdate();
            }

            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }
}
