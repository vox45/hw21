import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

public class FlyingButtonsGame extends JFrame implements ActionListener {
    private final int BUTTON_SIZE = 50;
    private final int FRAME_WIDTH = 800;
    private final int FRAME_HEIGHT = 600;
    private final int TIMER_DELAY = 100; // Затримка таймера в мілісекундах

    private Timer timer;
    private JButton[] buttons;

    public FlyingButtonsGame() {
        setTitle("Flying Buttons Game");
        setSize(FRAME_WIDTH, FRAME_HEIGHT);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(null); // Вимикаємо автоматичне розташування компонентів

        initializeButtons();
        initializeTimer();

        setVisible(true);
    }

    private void initializeButtons() {
        buttons = new JButton[5]; // Задаємо кількість кнопок

        for (int i = 0; i < buttons.length; i++) {
            buttons[i] = new JButton();
            buttons[i].setSize(BUTTON_SIZE, BUTTON_SIZE);
            buttons[i].addActionListener(this); // Додаємо обробник подій
            add(buttons[i]);
        }

        // Розміщаємо кнопки на випадкових позиціях
        Random random = new Random();
        for (JButton button : buttons) {
            int x = random.nextInt(FRAME_WIDTH - BUTTON_SIZE);
            int y = random.nextInt(FRAME_HEIGHT - BUTTON_SIZE);
            button.setLocation(x, y);
            changeButtonColor(button); // Встановлюємо випадковий колір
        }
    }

    private void initializeTimer() {
        timer = new Timer(TIMER_DELAY, e -> moveButtons()); // Лямбда-вираз для обробки таймера
        timer.start();
    }

    private void moveButtons() {
        Random random = new Random();
        for (JButton button : buttons) {
            int dx = random.nextInt(10) - 5; // Випадкове зміщення по горизонталі в межах [-5, 5]
            int dy = random.nextInt(10) - 5; // Випадкове зміщення по вертикалі в межах [-5, 5]

            // Перевірка на зіткнення з межами вікна
            int newX = button.getX() + dx;
            int newY = button.getY() + dy;
            if (newX < 0) newX = 0;
            if (newX > FRAME_WIDTH - BUTTON_SIZE) newX = FRAME_WIDTH - BUTTON_SIZE;
            if (newY < 0) newY = 0;
            if (newY > FRAME_HEIGHT - BUTTON_SIZE) newY = FRAME_HEIGHT - BUTTON_SIZE;

            button.setLocation(newX, newY);
        }
    }

    private void changeButtonColor(JButton button) {
        Random random = new Random();
        int r = random.nextInt(256); // Випадкова складова червоного кольору
        int g = random.nextInt(256); // Випадкова складова зеленого кольору
        int b = random.nextInt(256); // Випадкова складова синього кольору

        button.setBackground(new Color(r, g, b));
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        JButton clickedButton = (JButton) e.getSource();
        remove(clickedButton); // Видаляємо кнопку при кліку
        revalidate();
        repaint();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(FlyingButtonsGame::new); // Запускаємо гру в окремому потоці
    }
}
