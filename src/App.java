import database.Category;
import screens.CreateQuestionScreenGui;
import screens.QuizScreenGui;
import screens.TitleScreenGui;

import javax.swing.*;

public class App {
    public static void main(String[] args) {
        // to ensure swing gui tasks are on the event dispatch thread (EDT)
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                // display title screen
//                new TitleScreenGui().setVisible(true);

//                new CreateQuestionScreenGui().setVisible(true);

                new QuizScreenGui(new Category(1, "Java"), 10)
                        .setVisible(true);
            }
        });
    }
}
