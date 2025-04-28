import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class KalkulatorGUI extends JFrame implements ActionListener {
    private JTextField layar;
    private double angka1 = 0, angka2 = 0, hasil = 0;
    private String operator = "";

    public KalkulatorGUI() {
        setTitle("Kalkulator Sederhana");
        setSize(300, 400);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        layar = new JTextField();
        layar.setEditable(false);
        layar.setFont(new Font("Arial", Font.BOLD, 24));
        add(layar, BorderLayout.NORTH);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(4, 4, 5, 5));

        String[] tombol = {
            "7", "8", "9", "/",
            "4", "5", "6", "*",
            "1", "2", "3", "-",
            "0", ".", "=", "+"
        };

        for (String text : tombol) {
            JButton btn = new JButton(text);
            btn.setFont(new Font("Arial", Font.BOLD, 20));
            btn.addActionListener(this);
            panel.add(btn);
        }

        add(panel, BorderLayout.CENTER);
        setVisible(true);
    }

    public void actionPerformed(ActionEvent e) {
        String input = e.getActionCommand();

        if (input.matches("[0-9.]")) {
            layar.setText(layar.getText() + input);
        } else if (input.matches("[+\\-*/]")) {
            try {
                angka1 = Double.parseDouble(layar.getText());
                operator = input;
                layar.setText("");
            } catch (NumberFormatException ex) {
                layar.setText("Error");
            }
        } else if (input.equals("=")) {
            try {
                angka2 = Double.parseDouble(layar.getText());
                switch (operator) {
                    case "+":
                        hasil = angka1 + angka2;
                        break;
                    case "-":
                        hasil = angka1 - angka2;
                        break;
                    case "*":
                        hasil = angka1 * angka2;
                        break;
                    case "/":
                        if (angka2 == 0) {
                            layar.setText("Error: ÷0");
                            return;
                        }
                        hasil = angka1 / angka2;
                        break;
                }
                layar.setText(String.valueOf(hasil));
            } catch (NumberFormatException ex) {
                layar.setText("Error");
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new KalkulatorGUI());
    }
}
