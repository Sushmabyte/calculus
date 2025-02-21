import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

public final class calculator {
    String op;
    double num1,num2, res;
    JFrame frame;
    JTextField t;
    public calculator() {
        GUI();
    }

    public void GUI() {
        frame = new JFrame("Calculator");
        frame.setSize(250, 400);
        frame.setLayout(new BorderLayout());
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        t = new JTextField();
        t.setFont(new Font("Arial", Font.PLAIN, 24));
        t.setHorizontalAlignment(SwingConstants.RIGHT);
        t.setEditable(false);
        frame.add(t, BorderLayout.NORTH);

        JPanel p = new JPanel();
        p.setLayout(new GridLayout(4, 4, 2, 2));
        String[] buttons = {
            "1", "2", "3", "+",
            "4", "5", "6", "-",
            "7", "8", "9", "*",
            "0", "=", "/", "C"
        };

        for (String i : buttons) {
            JButton b = new JButton(i);
            b.setFont(new Font("Arial", Font.PLAIN, 25));
            b.addActionListener(new ButtonClickListener());
            p.add(b);
        }

        frame.add(p, BorderLayout.CENTER);
        frame.setVisible(true);
    }

    private class ButtonClickListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String command = e.getActionCommand();
            if (Character.isDigit(command.charAt(0))) {
                t.setText(t.getText() + command);
            } else {
                switch (command) {
                    case "C" -> {
                        t.setText("");
                        num1 = num2 = res = 0;
                        op = "";
                    }
                    case "=" -> {
                        try {
                            String expression = t.getText();
                            res = evaluateExpression(expression);
                            t.setText(String.valueOf(res));
                        } catch (Exception ex) {
                            t.setText("Error");
                        }
                    }
                    default -> t.setText(t.getText() + " " + command + " ");
                }
            }
        }

        private double evaluateExpression(String exp) {
            String[] tokens = exp.split(" ");
            double res = Double.parseDouble(tokens[0]);
            for (int i = 1; i < tokens.length; i += 2) {
                String op = tokens[i];
                double num = Double.parseDouble(tokens[i + 1]);
                switch (op) {
                    case "+" -> res += num;
                    case "-" -> res -= num;
                    case "*" -> res *= num;
                    case "/" -> res /= num;
                    default -> throw new AssertionError();
                }
            }
            return res;
        }
    }

    public static void main(String[] args) {
        new calculator();
    }
}