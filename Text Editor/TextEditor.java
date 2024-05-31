import javax.swing.JFrame;

public class TextEditor {
    public static void main(String[] args){
        JFrame frame = new JFrame("Text Editor");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 600);
        frame.add(new TextEditorPanel());
        frame.setVisible(true);
    }
}