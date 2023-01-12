import javax.swing.*;
import java.awt.*;
import java.util.List;

public class InfoDisplay extends JPanel {
   
    public InfoDisplay() {
        JPanel container = new JPanel();
        BoxLayout layout = new BoxLayout(this, BoxLayout.Y_AXIS);
        this.setLayout(layout);
    }

    public void addInfo(String message) {
        JLabel label = new JLabel(message);
        if (message.contains("Red Player")) {
        	label.setForeground(Color.RED);
        }
        if (message.contains("Blue Player")) {
        	label.setForeground(Color.BLUE);
        }
        if (message.contains("Player")) {
            label.setFont(new Font("Serif", Font.BOLD, 20));
            this.add(label);
        }
        else {
            label.setFont(new Font("Serif", Font.PLAIN, 18));
            this.add(label);
        }
    }

    public void addInfo(List<String> messages) {
        this.removeAll();

        for (String message : messages) {
            addInfo(message);
        }
    }

}
