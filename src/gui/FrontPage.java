package gui;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.BorderLayout;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.SwingConstants;
import javax.swing.BoxLayout;
import java.awt.GridLayout;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.SystemColor;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JRadioButton;
import javax.swing.JOptionPane;

import controller.ProductController;

public class FrontPage extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					FrontPage frame = new FrontPage();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public FrontPage() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 0));
		
		JPanel topPanel = new JPanel();
		topPanel.setBackground(SystemColor.activeCaption);
		contentPane.add(topPanel, BorderLayout.NORTH);
		
		JLabel lblHowdy = new JLabel("Howdy Pardner!");
		topPanel.add(lblHowdy);
		
		JPanel bottomPanel = new JPanel();
		contentPane.add(bottomPanel, BorderLayout.SOUTH);
		GridBagLayout gbl_bottomPanel = new GridBagLayout();
		gbl_bottomPanel.columnWidths = new int[]{426, 0};
		gbl_bottomPanel.rowHeights = new int[]{21, 0};
		gbl_bottomPanel.columnWeights = new double[]{0.0, Double.MIN_VALUE};
		gbl_bottomPanel.rowWeights = new double[]{0.0, Double.MIN_VALUE};
		bottomPanel.setLayout(gbl_bottomPanel);
		
		JPanel middlePanel = new JPanel();
		contentPane.add(middlePanel, BorderLayout.CENTER);
		middlePanel.setLayout(null);
		
		JButton btnPlaceOrder = new JButton("Place Order");
		btnPlaceOrder.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				createAddCustomer();			
			}
		});
		btnPlaceOrder.setBounds(150, 120, 150, 25);
		middlePanel.add(btnPlaceOrder);

		JButton btnRestock = new JButton("Restock");
		btnRestock.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				restock();
			}
		});
		btnRestock.setBounds(150, 155, 150, 25);
		middlePanel.add(btnRestock);
	}

	public void restock() {
		try {
			new ProductController().restock();
			JOptionPane.showMessageDialog(this, "Lagrene er fyldt op igen!");
		} catch (Exception e) {
			JOptionPane.showMessageDialog(this, "Restock fejlede: " + e.getMessage());
		}
	}

		public void createAddCustomer() {
			AddCustomer createAddCustomer = new AddCustomer();
			createAddCustomer.setVisible(true);
			super.setVisible(false);
	}
}
