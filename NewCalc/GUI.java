

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
import javax.swing.JOptionPane;

public class GUI2 extends JFrame {

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
					GUI2 frame = new GUI2();
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
	public GUI2() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 330, 407);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		
		Display2 = new JTextField();
		Display2.setBackground(Color.WHITE);
		Display2.setEditable(false);
		Display2.setForeground(Color.RED);
		Display2.setFont(new Font("Tahoma", Font.PLAIN, 11));
		Display2.setBounds(10, 0, 174, 31);
		contentPane.add(Display2);
		Display2.setColumns(10); 
		
		
		JButton btn_0 = new JButton("0");
		btn_0.setFont(new Font("Arial", Font.BOLD, 18));
		btn_0.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Display.setText(Display.getText() + "0");
			}
		});
		
		JButton btn_1 = new JButton("1");
		btn_1.setFont(new Font("Arial", Font.BOLD, 18));
		btn_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Display.setText(Display.getText() + "1");
			}
		});
		
		JButton btn_2 = new JButton("2");
		btn_2.setFont(new Font("Arial", Font.BOLD, 18));
		btn_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Display.setText(Display.getText() + "2");
			}
		});
		
		JButton btn_3 = new JButton("3");
		btn_3.setFont(new Font("Arial", Font.BOLD, 18));
		btn_3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Display.setText(Display.getText() + "3");
			}
		});
		
		
		
		JButton btn_4 = new JButton("4");
		btn_4.setFont(new Font("Arial", Font.BOLD, 18));
		btn_4.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Display.setText(Display.getText() + "4");
			}
		});
		
		JButton btn_5 = new JButton("5");
		btn_5.setFont(new Font("Arial", Font.BOLD, 18));
		btn_5.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Display.setText(Display.getText() + "5");
			}
		}); 
		
		
		
		JButton btn_6 = new JButton("6");
		btn_6.setFont(new Font("Arial", Font.BOLD, 18));
		btn_6.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Display.setText(Display.getText() + "6");
			}
		});
		
		JButton btn_7 = new JButton("7");
		btn_7.setFont(new Font("Arial", Font.BOLD, 18));
		btn_7.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Display.setText(Display.getText() + "7");
			}
			
		});
		
		JButton btn_8 = new JButton("8");
		btn_8.setFont(new Font("Arial", Font.BOLD, 18));
		btn_8.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Display.setText(Display.getText() + "8");
			}
		});
		
		JButton btn_9 = new JButton("9");
		btn_9.setFont(new Font("Arial", Font.BOLD, 18));
		btn_9.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Display.setText(Display.getText() + "9");
			}
		});
		
		btn_1.setBounds(15, 100, 50, 42);
		
		btn_4.setBounds(15, 153, 50, 42);
		contentPane.setLayout(null);
		contentPane.add(btn_1);
		contentPane.add(btn_4);
		
		btn_6.setBounds(131, 153, 53, 42);
		contentPane.add(btn_6);
		
		
		btn_2.setBounds(71, 100, 50, 42);
		contentPane.add(btn_2);
		
		
		btn_3.setBounds(131, 100, 53, 42);
		contentPane.add(btn_3);
		
	
		btn_5.setBounds(71, 153, 50, 42);
		contentPane.add(btn_5);
		
		
		btn_7.setBounds(15, 206, 50, 42);
		contentPane.add(btn_7);
		
		
		btn_8.setBounds(71, 206, 50, 42);
		contentPane.add(btn_8);
		
		
		btn_9.setBounds(131, 206, 53, 42);
		contentPane.add(btn_9);
		
		
		btn_0.setBounds(74, 261, 50, 42);
		contentPane.add(btn_0);
		
		JButton btn_Multiply = new JButton("X");
		btn_Multiply.setForeground(Color.RED);
		btn_Multiply.setFont(new Font("Arial", Font.BOLD, 18));
		btn_Multiply.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Display.setText(Display.getText() + " * " );
			}
		});
		btn_Multiply.setBounds(194, 153, 50, 42);
		contentPane.add(btn_Multiply);
		
		JButton btn_Division = new JButton("/");
		btn_Division.setForeground(Color.RED);
		btn_Division.setFont(new Font("Arial", Font.BOLD, 18));
		btn_Division.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Display.setText(Display.getText() + " / ");
			}
		});
		btn_Division.setBounds(254, 153, 50, 42);
		contentPane.add(btn_Division);
		
		JButton btn_Addition = new JButton("+");
		btn_Addition.setForeground(Color.RED);
		btn_Addition.setFont(new Font("Arial", Font.BOLD, 18));
		btn_Addition.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Display.setText(Display.getText() + " + ");
			}
		});
		btn_Addition.setBounds(194, 206, 50, 48);
		contentPane.add(btn_Addition);
		
		JButton btn_Dot = new JButton(".");
		btn_Dot.setFont(new Font("Arial", Font.BOLD, 18));
		btn_Dot.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Display.setText(Display.getText() + ".");
			}
		});
		btn_Dot.setBounds(15, 261, 50, 42);
		contentPane.add(btn_Dot);
		
		JButton btn_Subtraction = new JButton("-");
		btn_Subtraction.setForeground(Color.RED);
		btn_Subtraction.setFont(new Font("Arial", Font.BOLD, 18));
		btn_Subtraction.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Display.setText(Display.getText() + " - ");
			}
		});
		btn_Subtraction.setBounds(254, 201, 50, 53);
		contentPane.add(btn_Subtraction);
		
		
		
		Display = new JTextField();
		Display.setBackground(Color.WHITE);
		Display.setCaretColor(Color.WHITE);
		Display.setDisabledTextColor(Color.WHITE);
		Display.setEditable(false);
		Display.setFont(new Font("Arial", Font.BOLD, 39));
		Display.setBounds(10, 35, 294, 54);
		contentPane.add(Display);
		Display.setColumns(10);
		
	
		JButton btn_OpenBrack = new JButton("(");
		btn_OpenBrack.setForeground(Color.RED);
		btn_OpenBrack.setFont(new Font("Arial", Font.BOLD, 18));
		btn_OpenBrack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Display.setText(Display.getText() + " ( ");
			}
		});
		btn_OpenBrack.setBounds(15, 315, 50, 42);
		contentPane.add(btn_OpenBrack);
		
		JButton btn_CloseBrack = new JButton(")");
		btn_CloseBrack.setForeground(Color.RED);
		btn_CloseBrack.setFont(new Font("Arial", Font.BOLD, 18));
		btn_CloseBrack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Display.setText(Display.getText() + " ) ");
			}
		});
		btn_CloseBrack.setBounds(74, 315, 50, 42);
		contentPane.add(btn_CloseBrack);
		
		JButton btn_Power = new JButton("^");
		btn_Power.setForeground(Color.RED);
		btn_Power.setFont(new Font("Arial", Font.BOLD, 18));
		btn_Power.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Display.setText(Display.getText() + " ^ ");
			}
		});
		btn_Power.setBounds(134, 315, 50, 42);
		contentPane.add(btn_Power);
		
		JButton btn_Equal = new JButton("=");
		btn_Equal.setForeground(Color.RED);
		btn_Equal.setFont(new Font("Arial", Font.BOLD, 18));
		btn_Equal.addActionListener(new ActionListener() {
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
						Display.remove(btn_Equal);
						String message = Display.getText();
						System.out.println(message);
						if(message.equals("Invalid"))
						{
							btn_1.setEnabled(false);
							btn_2.setEnabled(false);
							btn_3.setEnabled(false);
							btn_4.setEnabled(false);
							btn_5.setEnabled(false);
							btn_6.setEnabled(false);
							btn_7.setEnabled(false);
							btn_8.setEnabled(false);
							btn_9.setEnabled(false);
							btn_0.setEnabled(false);
							btn_Multiply.setEnabled(false);
							btn_OpenBrack.setEnabled(false);
							btn_CloseBrack.setEnabled(false);
							btn_Power.setEnabled(false);
							btn_Subtraction.setEnabled(false);
							btn_Division.setEnabled(false);
							btn_Dot.setEnabled(false);
							btn_Addition.setEnabled(false);
							btn_Equal.setEnabled(false);
							
							
						}
						
					} catch (Exception e1) {
						e1.printStackTrace();
					}
			
			}
		});
		btn_Equal.setBounds(194, 259, 110, 98);
		contentPane.add(btn_Equal);
		
		JButton btn_Clear = new JButton("C");
		btn_Clear.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Brackets runEquation = new Brackets(testCase);
				runEquation.getFormulaSplitInBrackets().clear();
				Display.setEnabled(true);
				Display.setText(null);
				Display2.setText(null);
				btn_1.setEnabled(true);
				btn_2.setEnabled(true);
				btn_3.setEnabled(true);
				btn_4.setEnabled(true);
				btn_5.setEnabled(true);
				btn_6.setEnabled(true);
				btn_7.setEnabled(true);
				btn_8.setEnabled(true);
				btn_9.setEnabled(true);
				btn_0.setEnabled(true);
				btn_Multiply.setEnabled(true);
				btn_OpenBrack.setEnabled(true);
				btn_CloseBrack.setEnabled(true);
				btn_Power.setEnabled(true);
				btn_Subtraction.setEnabled(true);
				btn_Division.setEnabled(true);
				btn_Dot.setEnabled(true);
				btn_Addition.setEnabled(true);
				btn_Equal.setEnabled(true);

				
			}
		});
		btn_Clear.setFont(new Font("Yu Gothic UI Semibold", Font.BOLD, 18));
		btn_Clear.setBounds(134, 262, 50, 42);
		contentPane.add(btn_Clear);
		
		JButton btn_DrawingPad = new JButton("Drawing Pad");
		btn_DrawingPad.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				JOptionPane.showMessageDialog(btn_DrawingPad, "Please write one digit at a time");
				DrawingPad draw = new DrawingPad();
				draw.main(null);
			}
		});
		btn_DrawingPad.setFont(new Font("Arial", Font.BOLD, 12));
		btn_DrawingPad.setBounds(193, 100, 111, 42);
		contentPane.add(btn_DrawingPad);
		
	
		
		
	}
}





