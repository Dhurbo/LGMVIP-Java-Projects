import javax.swing.JPanel;
import javax.swing.JButton;
import java.awt.GridLayout;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class TicTacToePanel extends JPanel {
    private JButton[][] buttons;
    private char currentPlayer;

    public TicTacToePanel() {
        setLayout(new GridLayout(3, 3));
        buttons = new JButton[3][3];
        currentPlayer = 'X';

        initializeButtons();
    }

    private void initializeButtons() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                buttons[i][j] = new JButton("");
                buttons[i][j].setFont(buttons[i][j].getFont().deriveFont(50.0f));
                buttons[i][j].addActionListener(new ButtonClickListener(i, j));
                add(buttons[i][j]);
            }
        }
    }

    private class ButtonClickListener implements ActionListener {
        private int row;
        private int col;

        public ButtonClickListener(int row, int col) {
            this.row = row;
            this.col = col;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            if (buttons[row][col].getText().equals("")) {
                buttons[row][col].setText(String.valueOf(currentPlayer));
                if (checkForWin()) {
                    System.out.println("Player " + currentPlayer + " wins!");
                    disableButtons();
                } else if (isBoardFull()) {
                    System.out.println("The game is a tie!");
                } else {
                    currentPlayer = (currentPlayer == 'X') ? 'O' : 'X';
                }
            }
        }
    }

    private boolean checkForWin() {
        // Check rows
        for (int i = 0; i < 3; i++) {
            if (buttons[i][0].getText().equals(String.valueOf(currentPlayer)) &&
                buttons[i][1].getText().equals(String.valueOf(currentPlayer)) &&
                buttons[i][2].getText().equals(String.valueOf(currentPlayer))) {
                return true;
            }
        }
        // Check columns
        for (int j = 0; j < 3; j++) {
            if (buttons[0][j].getText().equals(String.valueOf(currentPlayer)) &&
                buttons[1][j].getText().equals(String.valueOf(currentPlayer)) &&
                buttons[2][j].getText().equals(String.valueOf(currentPlayer))) {
                return true;
            }
        }
        // Check diagonals
        if (buttons[0][0].getText().equals(String.valueOf(currentPlayer)) &&
            buttons[1][1].getText().equals(String.valueOf(currentPlayer)) &&
            buttons[2][2].getText().equals(String.valueOf(currentPlayer))) {
            return true;
        }
        if (buttons[0][2].getText().equals(String.valueOf(currentPlayer)) &&
            buttons[1][1].getText().equals(String.valueOf(currentPlayer)) &&
            buttons[2][0].getText().equals(String.valueOf(currentPlayer))) {
            return true;
        }
        return false;
    }

    private boolean isBoardFull() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (buttons[i][j].getText().equals("")) {
                    return false;
                }
            }
        }
        return true;
    }

    private void disableButtons() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                buttons[i][j].setEnabled(false);
            }
        }
    }
}
