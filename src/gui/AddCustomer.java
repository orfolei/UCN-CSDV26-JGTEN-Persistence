package gui;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.BorderLayout;
import javax.swing.JLabel;
import java.awt.GridLayout;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import javax.swing.JTextField;
import java.awt.Insets;
import javax.swing.JButton;

public class AddCustomer extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField textFieldName;
	private JTextField textFieldAddress;
	private JTextField textZipcode;
	private JTextField textFieldEmail;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					AddCustomer frame = new AddCustomer();
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
	public AddCustomer() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 0));
		
		JPanel panel = new JPanel();
		contentPane.add(panel, BorderLayout.NORTH);
		
		JLabel lblHello = new JLabel("Howdy, please fill out you contact information below");
		panel.add(lblHello);
		
		JPanel centerPanel = new JPanel();
		contentPane.add(centerPanel, BorderLayout.CENTER);
		GridBagLayout gbl_centerPanel = new GridBagLayout();
		gbl_centerPanel.columnWidths = new int[]{115, 0, 0};
		gbl_centerPanel.rowHeights = new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
		gbl_centerPanel.columnWeights = new double[]{0.0, 1.0, Double.MIN_VALUE};
		gbl_centerPanel.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		centerPanel.setLayout(gbl_centerPanel);
		
		JLabel lblName = new JLabel("Name:");
		GridBagConstraints gbc_lblName = new GridBagConstraints();
		gbc_lblName.insets = new Insets(0, 0, 5, 5);
		gbc_lblName.gridx = 0;
		gbc_lblName.gridy = 0;
		centerPanel.add(lblName, gbc_lblName);
		
		textFieldName = new JTextField();
		GridBagConstraints gbc_textFieldName = new GridBagConstraints();
		gbc_textFieldName.insets = new Insets(0, 0, 5, 0);
		gbc_textFieldName.fill = GridBagConstraints.HORIZONTAL;
		gbc_textFieldName.gridx = 1;
		gbc_textFieldName.gridy = 0;
		centerPanel.add(textFieldName, gbc_textFieldName);
		textFieldName.setColumns(10);
		
		JLabel lblAddress = new JLabel("Address:");
		GridBagConstraints gbc_lblAddress = new GridBagConstraints();
		gbc_lblAddress.insets = new Insets(0, 0, 5, 5);
		gbc_lblAddress.gridx = 0;
		gbc_lblAddress.gridy = 1;
		centerPanel.add(lblAddress, gbc_lblAddress);
		
		textFieldAddress = new JTextField();
		GridBagConstraints gbc_textFieldAddress = new GridBagConstraints();
		gbc_textFieldAddress.insets = new Insets(0, 0, 5, 0);
		gbc_textFieldAddress.fill = GridBagConstraints.HORIZONTAL;
		gbc_textFieldAddress.gridx = 1;
		gbc_textFieldAddress.gridy = 1;
		centerPanel.add(textFieldAddress, gbc_textFieldAddress);
		textFieldAddress.setColumns(10);
		
		JLabel lblZipcode = new JLabel("Zipcode");
		GridBagConstraints gbc_lblZipcode = new GridBagConstraints();
		gbc_lblZipcode.insets = new Insets(0, 0, 5, 5);
		gbc_lblZipcode.gridx = 0;
		gbc_lblZipcode.gridy = 2;
		centerPanel.add(lblZipcode, gbc_lblZipcode);
		
		textZipcode = new JTextField();
		GridBagConstraints gbc_textZipcode = new GridBagConstraints();
		gbc_textZipcode.insets = new Insets(0, 0, 5, 0);
		gbc_textZipcode.fill = GridBagConstraints.HORIZONTAL;
		gbc_textZipcode.gridx = 1;
		gbc_textZipcode.gridy = 2;
		centerPanel.add(textZipcode, gbc_textZipcode);
		textZipcode.setColumns(10);
		
		JLabel lblCity = new JLabel("City:");
		GridBagConstraints gbc_lblCity = new GridBagConstraints();
		gbc_lblCity.insets = new Insets(0, 0, 5, 5);
		gbc_lblCity.gridx = 0;
		gbc_lblCity.gridy = 3;
		centerPanel.add(lblCity, gbc_lblCity);
		
		JLabel lblCityAuto = new JLabel("");
		GridBagConstraints gbc_lblCityAuto = new GridBagConstraints();
		gbc_lblCityAuto.insets = new Insets(0, 0, 5, 0);
		gbc_lblCityAuto.gridx = 1;
		gbc_lblCityAuto.gridy = 3;
		centerPanel.add(lblCityAuto, gbc_lblCityAuto);
		
		JLabel lblEmail = new JLabel("Email:");
		GridBagConstraints gbc_lblEmail = new GridBagConstraints();
		gbc_lblEmail.insets = new Insets(0, 0, 5, 5);
		gbc_lblEmail.gridx = 0;
		gbc_lblEmail.gridy = 4;
		centerPanel.add(lblEmail, gbc_lblEmail);
		
		textFieldEmail = new JTextField();
		GridBagConstraints gbc_textFieldEmail = new GridBagConstraints();
		gbc_textFieldEmail.insets = new Insets(0, 0, 5, 0);
		gbc_textFieldEmail.fill = GridBagConstraints.HORIZONTAL;
		gbc_textFieldEmail.gridx = 1;
		gbc_textFieldEmail.gridy = 4;
		centerPanel.add(textFieldEmail, gbc_textFieldEmail);
		textFieldEmail.setColumns(10);
		
		JButton btnConfirm = new JButton("Confirm");
		GridBagConstraints gbc_btnConfirm = new GridBagConstraints();
		gbc_btnConfirm.insets = new Insets(0, 0, 0, 5);
		gbc_btnConfirm.gridx = 0;
		gbc_btnConfirm.gridy = 8;
		centerPanel.add(btnConfirm, gbc_btnConfirm);
		
		JButton btnCancel = new JButton("Cancel");
		GridBagConstraints gbc_btnCancel = new GridBagConstraints();
		gbc_btnCancel.gridx = 1;
		gbc_btnCancel.gridy = 8;
		centerPanel.add(btnCancel, gbc_btnCancel);

	}

}
