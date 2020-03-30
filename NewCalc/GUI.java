

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.LayoutStyle.ComponentPlacement;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Font;
import java.awt.Color;
import javax.swing.JMenuItem;

public class GUI extends JFrame {

	private JPanel contentPane;
	private JTextField Display;
	public String testCase;
	private JTextField Display2;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					GUI frame = new GUI();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 * @return 
	 */
	public GUI() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 330, 407);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		
		JButton btnNewButton = new JButton("1");
		btnNewButton.setFont(new Font("Arial", Font.BOLD, 18));
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Display.setText(Display.getText() + "1");
			}
		});
		btnNewButton.setBounds(15, 100, 50, 42);
		
		JButton btnNewButton_1 = new JButton("4");
		btnNewButton_1.setFont(new Font("Arial", Font.BOLD, 18));
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Display.setText(Display.getText() + "4");
			}
		});
		btnNewButton_1.setBounds(15, 153, 50, 42);
		contentPane.setLayout(null);
		contentPane.add(btnNewButton);
		contentPane.add(btnNewButton_1);
		
		JButton btnNewButton_1_1 = new JButton("6");
		btnNewButton_1_1.setFont(new Font("Arial", Font.BOLD, 18));
		btnNewButton_1_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Display.setText(Display.getText() + "6");
			}
		});
		btnNewButton_1_1.setBounds(131, 153, 53, 42);
		contentPane.add(btnNewButton_1_1);
		
		JButton btnNewButton_1_2 = new JButton("2");
		btnNewButton_1_2.setFont(new Font("Arial", Font.BOLD, 18));
		btnNewButton_1_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Display.setText(Display.getText() + "2");
			}
		});
		btnNewButton_1_2.setBounds(71, 100, 50, 42);
		contentPane.add(btnNewButton_1_2);
		
		JButton btnNewButton_1_3 = new JButton("3");
		btnNewButton_1_3.setFont(new Font("Arial", Font.BOLD, 18));
		btnNewButton_1_3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Display.setText(Display.getText() + "3");
			}
		});
		btnNewButton_1_3.setBounds(131, 100, 53, 42);
		contentPane.add(btnNewButton_1_3);
		
		JButton btnNewButton_1_4 = new JButton("5");
		btnNewButton_1_4.setFont(new Font("Arial", Font.BOLD, 18));
		btnNewButton_1_4.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Display.setText(Display.getText() + "5");
			}
		});
		btnNewButton_1_4.setBounds(71, 153, 50, 42);
		contentPane.add(btnNewButton_1_4);
		
		JButton btnNewButton_1_5 = new JButton("7");
		btnNewButton_1_5.setFont(new Font("Arial", Font.BOLD, 18));
		btnNewButton_1_5.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Display.setText(Display.getText() + "7");
			}
			
		});
		btnNewButton_1_5.setBounds(15, 206, 50, 42);
		contentPane.add(btnNewButton_1_5);
		
		JButton btnNewButton_1_6 = new JButton("8");
		btnNewButton_1_6.setFont(new Font("Arial", Font.BOLD, 18));
		btnNewButton_1_6.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Display.setText(Display.getText() + "8");
			}
		});
		btnNewButton_1_6.setBounds(71, 206, 50, 42);
		contentPane.add(btnNewButton_1_6);
		
		JButton btnNewButton_1_7 = new JButton("9");
		btnNewButton_1_7.setFont(new Font("Arial", Font.BOLD, 18));
		btnNewButton_1_7.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Display.setText(Display.getText() + "9");
			}
		});
		btnNewButton_1_7.setBounds(131, 206, 53, 42);
		contentPane.add(btnNewButton_1_7);
		
		JButton btnNewButton_1_8 = new JButton("0");
		btnNewButton_1_8.setFont(new Font("Arial", Font.BOLD, 18));
		btnNewButton_1_8.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Display.setText(Display.getText() + "0");
			}
		});
		btnNewButton_1_8.setBounds(74, 261, 50, 42);
		contentPane.add(btnNewButton_1_8);
		
		JButton btnNewButton_1_9 = new JButton("X");
		btnNewButton_1_9.setForeground(Color.RED);
		btnNewButton_1_9.setFont(new Font("Arial", Font.BOLD, 18));
		btnNewButton_1_9.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Display.setText(Display.getText() + " * " );
			}
		});
		btnNewButton_1_9.setBounds(194, 153, 50, 42);
		contentPane.add(btnNewButton_1_9);
		
		JButton btnNewButton_1_10 = new JButton("/");
		btnNewButton_1_10.setForeground(Color.RED);
		btnNewButton_1_10.setFont(new Font("Arial", Font.BOLD, 18));
		btnNewButton_1_10.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Display.setText(Display.getText() + " / ");
			}
		});
		btnNewButton_1_10.setBounds(254, 153, 50, 42);
		contentPane.add(btnNewButton_1_10);
		
		JButton btnNewButton_1_11 = new JButton("+");
		btnNewButton_1_11.setForeground(Color.RED);
		btnNewButton_1_11.setFont(new Font("Arial", Font.BOLD, 18));
		btnNewButton_1_11.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Display.setText(Display.getText() + " + ");
			}
		});
		btnNewButton_1_11.setBounds(194, 206, 50, 48);
		contentPane.add(btnNewButton_1_11);
		
		JButton btnNewButton_1_5_1 = new JButton(".");
		btnNewButton_1_5_1.setFont(new Font("Arial", Font.BOLD, 18));
		btnNewButton_1_5_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Display.setText(Display.getText() + ".");
			}
		});
		btnNewButton_1_5_1.setBounds(15, 261, 50, 42);
		contentPane.add(btnNewButton_1_5_1);
		
		JButton btnNewButton_1_11_1 = new JButton("-");
		btnNewButton_1_11_1.setForeground(Color.RED);
		btnNewButton_1_11_1.setFont(new Font("Arial", Font.BOLD, 18));
		btnNewButton_1_11_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Display.setText(Display.getText() + " - ");
			}
		});
		btnNewButton_1_11_1.setBounds(254, 201, 50, 53);
		contentPane.add(btnNewButton_1_11_1);
		
		JButton btnNewButton_1_11_2 = new JButton("=");
		btnNewButton_1_11_2.setForeground(Color.RED);
		btnNewButton_1_11_2.setFont(new Font("Arial", Font.BOLD, 18));
		btnNewButton_1_11_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				testCase = Display.getText();
				Display2.setText(Display.getText());
				Display.setText(Display.getText() + " = ");
				Main runEquation = new Main();
				String num;
	
					try {
						num = runEquation.run(testCase);
						Display.setText(null);
						Display.setText(num);
						Display.remove(btnNewButton_1_11_2);
						String message = Display.getText();
						if(message == "Invalid")
						{
							Display.setEnabled(false);
						}
					} catch (Exception e1) {
						e1.printStackTrace();
					}
			
			}
		});
		btnNewButton_1_11_2.setBounds(194, 259, 110, 98);
		contentPane.add(btnNewButton_1_11_2);
		
		Display = new JTextField();
		Display.setBackground(Color.WHITE);
		Display.setCaretColor(Color.WHITE);
		Display.setDisabledTextColor(Color.WHITE);
		Display.setEditable(false);
		Display.setFont(new Font("Arial", Font.BOLD, 39));
		Display.setBounds(10, 35, 294, 54);
		contentPane.add(Display);
		Display.setColumns(10);
		
		JButton btnNewButton_1_8_1 = new JButton("C");
		btnNewButton_1_8_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Brackets runEquation = new Brackets(testCase);
				runEquation.getFormulaSplitInBrackets().clear();
				Display.setEnabled(true);
				Display.setText(null);
				Display2.setText(null);

				
			}
		});
		btnNewButton_1_8_1.setFont(new Font("Yu Gothic UI Semibold", Font.BOLD, 18));
		btnNewButton_1_8_1.setBounds(134, 262, 50, 42);
		contentPane.add(btnNewButton_1_8_1);
		
		JButton btnNewButton_1_8_2 = new JButton("(");
		btnNewButton_1_8_2.setForeground(Color.RED);
		btnNewButton_1_8_2.setFont(new Font("Arial", Font.BOLD, 18));
		btnNewButton_1_8_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Display.setText(Display.getText() + " ( ");
			}
		});
		btnNewButton_1_8_2.setBounds(15, 315, 50, 42);
		contentPane.add(btnNewButton_1_8_2);
		
		JButton btnNewButton_1_8_3 = new JButton(")");
		btnNewButton_1_8_3.setForeground(Color.RED);
		btnNewButton_1_8_3.setFont(new Font("Arial", Font.BOLD, 18));
		btnNewButton_1_8_3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Display.setText(Display.getText() + " ) ");
			}
		});
		btnNewButton_1_8_3.setBounds(74, 315, 50, 42);
		contentPane.add(btnNewButton_1_8_3);
		
		JButton btnNewButton_1_8_4 = new JButton("^");
		btnNewButton_1_8_4.setForeground(Color.RED);
		btnNewButton_1_8_4.setFont(new Font("Arial", Font.BOLD, 18));
		btnNewButton_1_8_4.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Display.setText(Display.getText() + " ^ ");
			}
		});
		btnNewButton_1_8_4.setBounds(134, 315, 50, 42);
		contentPane.add(btnNewButton_1_8_4);
		
		Display2 = new JTextField();
		Display2.setBackground(Color.WHITE);
		Display2.setEditable(false);
		Display2.setForeground(Color.RED);
		Display2.setFont(new Font("Tahoma", Font.PLAIN, 11));
		Display2.setBounds(10, 0, 174, 31);
		contentPane.add(Display2);
		Display2.setColumns(10);
		
		JMenuItem menuItem = new JMenuItem("New menu item");
		menuItem.setBounds(81, 66, 129, 22);
		contentPane.add(menuItem);
		
	}
}
