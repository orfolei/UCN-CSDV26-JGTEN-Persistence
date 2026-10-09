package gui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.SQLException;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import controller.OrderController;
import controller.ProductController;
import database.DataAccessException;
import model.OrderLineItem;
import model.Product;
import model.SaleOrder;
import model.Stock;

public class PlaceOrder extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField txtQuantity;
	private JTable tableProducts;
	private DefaultTableModel productModel;
	private JTable tableBasket;
	private DefaultTableModel basketModel;
	private JLabel lblTotal;

	private OrderController orderController;
	private SaleOrder order;

	public PlaceOrder(OrderController orderController, SaleOrder order) {

		this.orderController = orderController;
		this.order = order;

		setTitle("Place Order");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1280, 720);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(5, 5));

		// overskrift med kundens navn
		JPanel northPanel = new JPanel();
		contentPane.add(northPanel, BorderLayout.NORTH);
		JLabel lblPlaceOrder = new JLabel("Howdy " + order.getCustomer().getName() + ", please place your order");
		northPanel.add(lblPlaceOrder);

		// midten: varer til venstre, kurven til højre
		JPanel centerPanel = new JPanel(new GridLayout(1, 2, 10, 0));
		contentPane.add(centerPanel, BorderLayout.CENTER);

		// tabellen med varer er det eneste sted man vælger varer fra
		productModel = new DefaultTableModel(new String[] {
				"Varenr.", "Navn", "Pris", "På lager"}, 0) {
			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};
		tableProducts = new JTable(productModel);
		tableProducts.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		// dobbeltklik på en vare lægger den i kurven
		tableProducts.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (e.getClickCount() == 2) {
					addClicked();
				}
			}
		});
		JScrollPane productScroll = new JScrollPane(tableProducts);
		productScroll.setBorder(BorderFactory.createTitledBorder("Varer (vælg en og tryk Læg i kurv, eller dobbeltklik)"));
		centerPanel.add(productScroll);

		// kurven viser de ordrelinjer der er lagt på ordren
		basketModel = new DefaultTableModel(new String[] {
				"Varenr.", "Navn", "Antal", "Stk. pris", "Linjepris"}, 0) {
			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};
		tableBasket = new JTable(basketModel);
		tableBasket.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		JScrollPane basketScroll = new JScrollPane(tableBasket);
		basketScroll.setBorder(BorderFactory.createTitledBorder("Kurv"));
		centerPanel.add(basketScroll);

		// bunden: antal, knapper og total
		JPanel southPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 5));
		contentPane.add(southPanel, BorderLayout.SOUTH);

		southPanel.add(new JLabel("Antal:"));
		txtQuantity = new JTextField("1");
		txtQuantity.setColumns(5);
		// enter i antal-feltet lægger også i kurven
		txtQuantity.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				addClicked();
			}
		});
		southPanel.add(txtQuantity);

		JButton btnAdd = new JButton("Læg i kurv");
		btnAdd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				addClicked();
			}
		});
		southPanel.add(btnAdd);

		JButton btnRemove = new JButton("Fjern fra kurv");
		btnRemove.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				removeClicked();
			}
		});
		southPanel.add(btnRemove);

		lblTotal = new JLabel();
		southPanel.add(lblTotal);

		JButton btnProceed = new JButton("Proceed");
		btnProceed.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				proceedClicked();
			}
		});
		southPanel.add(btnProceed);

		JButton btnCancel = new JButton("Cancel");
		btnCancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				cancelClicked();
			}
		});
		southPanel.add(btnCancel);

		fillProductTable();
		updateBasket();
	}

	public void cancelClicked() {
		FrontPage frontPage = new FrontPage();
		frontPage.setVisible(true);
		super.dispose();
	}

	// henter alle varer fra databasen og lægger dem i tabellen
	private void fillProductTable() {
		try {
			ProductController productController = new ProductController();
			List<Product> products = productController.findAll();

			productModel.setRowCount(0);

			for (Product p : products) {
				productModel.addRow(new Object[] {
						p.getProductNumber(),
						p.getName(),
						formatPrice(p.getPrice().getPrice()),
						availableQuantity(p)
				});
			}
		} catch (SQLException | DataAccessException e) {
			JOptionPane.showMessageDialog(this, "Fejl ved hentning af varer: " + e.getMessage());
		}
	}

	// det man kan bestille = alt på lager minus det der allerede er reserveret
	private int availableQuantity(Product p) {
		int total = 0;
		for (Stock s : p.getStockList()) {
			total += s.getQuantity();
		}
		return total - p.getReservedQty();
	}

	// tegner kurven forfra ud fra ordrens linjer og opdaterer totalen
	private void updateBasket() {
		basketModel.setRowCount(0);

		for (OrderLineItem line : order.getOrderLineItems()) {
			basketModel.addRow(new Object[] {
					line.getProduct().getProductNumber(),
					line.getProduct().getName(),
					line.getQuantity(),
					formatPrice(line.getProductPrice()),
					formatPrice(line.getLineTotalPrice())
			});
		}

		lblTotal.setText("Subtotal: " + formatPrice(order.calcPrice()));
	}

	private String formatPrice(double price) {
		return String.format("%.2f kr.", price);
	}

	// lægger den valgte vare fra tabellen i kurven
	private void addClicked() {
		int row = tableProducts.getSelectedRow();
		if (row == -1) {
			JOptionPane.showMessageDialog(this, "Du skal vælge en vare i tabellen!");
			return;
		}

		try {
			int productNumber = (int) productModel.getValueAt(row, 0);
			int quantity = Integer.parseInt(txtQuantity.getText().trim());

			orderController.addOrderLine(order, productNumber, quantity);
			updateBasket();
			txtQuantity.setText("1");
		} catch (NumberFormatException e) {
			JOptionPane.showMessageDialog(this, "Antal skal være et tal!!");
		} catch (DataAccessException e) {
			JOptionPane.showMessageDialog(this, e.getMessage());
		}
	}

	// fjerner den valgte linje fra kurven
	private void removeClicked() {
		int row = tableBasket.getSelectedRow();
		if (row == -1) {
			JOptionPane.showMessageDialog(this, "Vælg en linje i kurven først");
			return;
		}

		// rækkerne i kurven har samme rækkefølge som ordrens linjer
		order.getOrderLineItems().remove(row);
		updateBasket();
	}

	// viser rabat, fragt og total og gemmer ordren hvis kunden siger ja
	private void proceedClicked() {
		if (order.getOrderLineItems().isEmpty()) {
			JOptionPane.showMessageDialog(this, "Kurven er tom");
			return;
		}

		double subtotal = order.calcPrice();
		double discount = orderController.calcDiscount(order);
		double freight = orderController.calcFreight(order);
		double total = orderController.calcTotal(order);

		String summary = "Subtotal: " + formatPrice(subtotal)
				+ "\nRabat: -" + formatPrice(discount)
				+ "\nFragt: " + formatPrice(freight)
				+ "\nTotal: " + formatPrice(total)
				+ "\n\nVil du gemme ordren?";

		int answer = JOptionPane.showConfirmDialog(this, summary, "Bekræft ordre", JOptionPane.YES_NO_OPTION);
		if (answer != JOptionPane.YES_OPTION) {
			return;
		}

		try {
			orderController.saveOrder(order);
			JOptionPane.showMessageDialog(this, "Ordren er gemt med ordrenr. " + order.getOrderNo());
			cancelClicked();
		} catch (DataAccessException e) {
			// fx ikke nok på lager - ordren er rullet tilbage, så man kan rette kurven og prøve igen
			String reason = e.getCause() != null ? e.getCause().getMessage() : "";
			JOptionPane.showMessageDialog(this, e.getMessage() + "\n" + reason);
			fillProductTable();
		}
	}

}
