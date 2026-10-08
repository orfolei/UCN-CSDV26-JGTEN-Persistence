package gui;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.BorderLayout;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JLabel;
import java.awt.FlowLayout;
import javax.swing.JList;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.JButton;

public class ConfirmOrder extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ConfirmOrder frame = new ConfirmOrder();
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
	public ConfirmOrder() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 0));
		
		JPanel northPanel = new JPanel();
		contentPane.add(northPanel, BorderLayout.NORTH);
		northPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		
		JLabel lblGoodbye = new JLabel("Hey Pardner, sure you got everything?");
		northPanel.add(lblGoodbye);
		
		JPanel westPanel = new JPanel();
		contentPane.add(westPanel, BorderLayout.WEST);
		
		JLabel lblOrder = new JLabel("Order:");
		
		JList listOrder = new JList();
		
		JLabel lblPrice = new JLabel("Price: ");
		
		JLabel lblFullPrice = new JLabel("");
		GroupLayout gl_westPanel = new GroupLayout(westPanel);
		gl_westPanel.setHorizontalGroup(
			gl_westPanel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_westPanel.createSequentialGroup()
					.addGap(5)
					.addGroup(gl_westPanel.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_westPanel.createSequentialGroup()
							.addComponent(lblPrice, GroupLayout.PREFERRED_SIZE, 44, GroupLayout.PREFERRED_SIZE)
							.addGap(18)
							.addComponent(lblFullPrice, GroupLayout.PREFERRED_SIZE, 44, GroupLayout.PREFERRED_SIZE))
						.addComponent(listOrder, GroupLayout.PREFERRED_SIZE, 124, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblOrder))
					.addContainerGap())
		);
		gl_westPanel.setVerticalGroup(
			gl_westPanel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_westPanel.createSequentialGroup()
					.addGap(5)
					.addComponent(lblOrder)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(listOrder, GroupLayout.PREFERRED_SIZE, 187, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addGroup(gl_westPanel.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblPrice, GroupLayout.PREFERRED_SIZE, 12, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblFullPrice, GroupLayout.PREFERRED_SIZE, 12, GroupLayout.PREFERRED_SIZE))
					.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
		);
		westPanel.setLayout(gl_westPanel);
		
		JPanel panel = new JPanel();
		contentPane.add(panel, BorderLayout.SOUTH);
		
		JButton btnConfirm = new JButton("Confirm");
		
		JButton btnCancel = new JButton("Cancel");
		GroupLayout gl_panel = new GroupLayout(panel);
		gl_panel.setHorizontalGroup(
			gl_panel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel.createSequentialGroup()
					.addGap(178)
					.addComponent(btnConfirm)
					.addPreferredGap(ComponentPlacement.RELATED, 85, Short.MAX_VALUE)
					.addComponent(btnCancel, GroupLayout.PREFERRED_SIZE, 84, GroupLayout.PREFERRED_SIZE)
					.addContainerGap())
		);
		gl_panel.setVerticalGroup(
			gl_panel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel.createSequentialGroup()
					.addGap(5)
					.addGroup(gl_panel.createParallelGroup(Alignment.BASELINE)
						.addComponent(btnConfirm)
						.addComponent(btnCancel, GroupLayout.PREFERRED_SIZE, 20, GroupLayout.PREFERRED_SIZE)))
		);
		panel.setLayout(gl_panel);
		
		JPanel centerPanel = new JPanel();
		contentPane.add(centerPanel, BorderLayout.CENTER);
		
		JLabel lblDelivery = new JLabel("Choose delivery:");
		
		JList listDeliveryOptions = new JList();
		GroupLayout gl_centerPanel = new GroupLayout(centerPanel);
		gl_centerPanel.setHorizontalGroup(
			gl_centerPanel.createParallelGroup(Alignment.LEADING)
				.addGroup(Alignment.TRAILING, gl_centerPanel.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_centerPanel.createParallelGroup(Alignment.TRAILING)
						.addComponent(lblDelivery, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 267, Short.MAX_VALUE)
						.addComponent(listDeliveryOptions, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 267, Short.MAX_VALUE))
					.addContainerGap())
		);
		gl_centerPanel.setVerticalGroup(
			gl_centerPanel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_centerPanel.createSequentialGroup()
					.addContainerGap()
					.addComponent(lblDelivery, GroupLayout.PREFERRED_SIZE, 25, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(listDeliveryOptions, GroupLayout.DEFAULT_SIZE, 192, Short.MAX_VALUE)
					.addContainerGap())
		);
		centerPanel.setLayout(gl_centerPanel);

	}
}
