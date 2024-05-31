import javax.swing.JFrame;

public class TicTacToe {
    public static void main(String[] args) {
        JFrame frame = new JFrame("Tic-Tac-Toe");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 400);
        frame.add(new TicTacToePanel());
        frame.setVisible(true);
    }
}
