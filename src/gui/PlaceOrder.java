package gui;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.BorderLayout;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.SwingConstants;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JList;
import javax.swing.LayoutStyle.ComponentPlacement;
import java.awt.Insets;

public class PlaceOrder extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField txtSearch;
	private JTextField textField;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					PlaceOrder frame = new PlaceOrder();
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
	public PlaceOrder() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 0));
		
		JPanel northPanel = new JPanel();
		contentPane.add(northPanel, BorderLayout.NORTH);
		
		JLabel lblPlaceOrder = new JLabel("Howdy Pardner, please place your order");
		northPanel.add(lblPlaceOrder);
		
		JPanel centerPanel = new JPanel();
		contentPane.add(centerPanel, BorderLayout.CENTER);
		GridBagLayout gbl_centerPanel = new GridBagLayout();
		gbl_centerPanel.columnWidths = new int[]{0, 0, 0, 0};
		gbl_centerPanel.rowHeights = new int[]{0, 0, 0, 0, 0, 0, 31, 0};
		gbl_centerPanel.columnWeights = new double[]{1.0, 1.0, 0.0, Double.MIN_VALUE};
		gbl_centerPanel.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		centerPanel.setLayout(gbl_centerPanel);
		
		JLabel lblWare = new JLabel("Ware:");
		GridBagConstraints gbc_lblWare = new GridBagConstraints();
		gbc_lblWare.anchor = GridBagConstraints.EAST;
		gbc_lblWare.insets = new Insets(0, 0, 5, 5);
		gbc_lblWare.gridx = 0;
		gbc_lblWare.gridy = 0;
		centerPanel.add(lblWare, gbc_lblWare);
		
		JLabel lblQty = new JLabel("Qty");
		GridBagConstraints gbc_lblQty = new GridBagConstraints();
		gbc_lblQty.anchor = GridBagConstraints.EAST;
		gbc_lblQty.insets = new Insets(0, 0, 5, 5);
		gbc_lblQty.gridx = 0;
		gbc_lblQty.gridy = 5;
		centerPanel.add(lblQty, gbc_lblQty);
		
		textField = new JTextField();
		GridBagConstraints gbc_textField = new GridBagConstraints();
		gbc_textField.insets = new Insets(0, 0, 5, 5);
		gbc_textField.gridx = 1;
		gbc_textField.gridy = 5;
		centerPanel.add(textField, gbc_textField);
		textField.setColumns(10);
		
		JButton btnAdd = new JButton("Add to Cart");
		GridBagConstraints gbc_btnAdd = new GridBagConstraints();
		gbc_btnAdd.insets = new Insets(0, 0, 0, 5);
		gbc_btnAdd.gridx = 0;
		gbc_btnAdd.gridy = 6;
		centerPanel.add(btnAdd, gbc_btnAdd);
		
		JButton btnProceed = new JButton("Proceed");
		GridBagConstraints gbc_btnProceed = new GridBagConstraints();
		gbc_btnProceed.insets = new Insets(0, 0, 0, 5);
		gbc_btnProceed.gridx = 1;
		gbc_btnProceed.gridy = 6;
		centerPanel.add(btnProceed, gbc_btnProceed);
		
		JButton btnCancel = new JButton("Cancel");
		GridBagConstraints gbc_btnCancel = new GridBagConstraints();
		gbc_btnCancel.gridx = 2;
		gbc_btnCancel.gridy = 6;
		centerPanel.add(btnCancel, gbc_btnCancel);
		//	cancelClicked(); 
		
		JPanel westPanel = new JPanel();
		contentPane.add(westPanel, BorderLayout.WEST);
		
		txtSearch = new JTextField();
		txtSearch.setText("Search...");
		txtSearch.setColumns(10);
		
		JList list = new JList();
		GroupLayout gl_westPanel = new GroupLayout(westPanel);
		gl_westPanel.setHorizontalGroup(
			gl_westPanel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_westPanel.createSequentialGroup()
					.addGap(5)
					.addComponent(txtSearch, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
				.addGroup(Alignment.TRAILING, gl_westPanel.createSequentialGroup()
					.addContainerGap()
					.addComponent(list, GroupLayout.DEFAULT_SIZE, 81, Short.MAX_VALUE)
					.addGap(10))
		);
		gl_westPanel.setVerticalGroup(
			gl_westPanel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_westPanel.createSequentialGroup()
					.addGap(5)
					.addComponent(txtSearch, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(list, GroupLayout.PREFERRED_SIZE, 162, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(38, Short.MAX_VALUE))
		);
		westPanel.setLayout(gl_westPanel);

	}
}
