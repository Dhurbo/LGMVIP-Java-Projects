import javax.swing.JFrame;

public class ScientificCalculator{
    public static void main(String[] args){
        JFrame frame = new JFrame("Scintific Calculator");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 600);
        frame.add(new CalculatorPanel());
        frame.setVisible(true);
    }
}