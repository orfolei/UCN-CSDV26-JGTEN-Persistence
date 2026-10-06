package dk.ucn.jgten.persistence;

import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

public class Main {
	public static void main(String[] args) {
		SwingUtilities.invokeLater(() -> {
            try {
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            } catch (Exception ignored) {}
            
            try {                          
                new PlaceOrderView().setVisible(true);
            
            } catch (Exception e) {
				JOptionPane.showMessageDialog(null, "Failed to initialize data directory:\n" + e.getMessage(),
						"Initialization Error", JOptionPane.ERROR_MESSAGE);
				return;
			}
       

        });
	}
}
