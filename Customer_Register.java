import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.Calendar;
import java.util.Locale;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class Customer_Register extends JFrame {
	// ���� Ŭ���� ��ü ����
	Input input = new Input();
	Buttons buttons = new Buttons();

	public Customer_Register() {
		setTitle("�� ���");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		add(input, BorderLayout.CENTER);
		add(buttons, BorderLayout.SOUTH);

		setSize(400, 300);
		setLocation(500, 150);
		setVisible(true);

	}

	class Input extends JPanel implements KeyListener, FocusListener {
		String text[] = { "���ڵ�:", "*�� �� ��:", "*�������(YYYY-MM-DD):", "*�� �� ó:", "��       ��:", "ȸ       ��:" };
		JTextField tf[] = new JTextField[6];
		String yy;

		public Input() {
			setLayout(new GridLayout(6, 2));

			for (int i = 0; i < text.length; i++) {
				JLabel la = new JLabel(text[i]);
				tf[i] = new JTextField(20);
				add(la);
				add(tf[i]);
			}
			tf[0].setEditable(false);

			tf[2].addKeyListener(this);
			tf[3].addFocusListener(this);

			// �̺�Ʈ ����

		}

		@Override
		public void focusGained(FocusEvent arg0) {
			// TODO Auto-generated method stub
			Calendar cal = Calendar.getInstance(Locale.KOREA);
			int year = cal.get(Calendar.YEAR); // ����⵵ �� ����
			yy = Integer.toString(year).substring(2, 4);
			String yyyy, mm, dd;
			int sum = 0;

			yyyy = tf[2].getText().substring(0, 4);
			mm = tf[2].getText().substring(5, 7);
			dd = tf[2].getText().substring(8, 10);

			sum = Integer.parseInt(yyyy) + Integer.parseInt(mm) + Integer.parseInt(dd);

			tf[0].setText("S" + yy + sum);
		}

		@Override
		public void focusLost(FocusEvent arg0) {}

		@Override
		public void keyPressed(KeyEvent e) {
			if (e.getKeyCode() == KeyEvent.VK_ENTER) {
				Calendar cal = Calendar.getInstance(Locale.KOREA);
				int year = cal.get(Calendar.YEAR); // ����⵵ �� ����
				yy = Integer.toString(year).substring(2, 4);
				String yyyy, mm, dd;
				int sum = 0;

				yyyy = tf[2].getText().substring(0, 4);
				mm = tf[2].getText().substring(5, 7);
				dd = tf[2].getText().substring(8, 10);

				sum = Integer.parseInt(yyyy) + Integer.parseInt(mm) + Integer.parseInt(dd);

				tf[0].setText("S" + yy + sum);

			}
		}

		@Override
		public void keyReleased(KeyEvent e) {}

		@Override
		public void keyTyped(KeyEvent e) {}
	}

	class Buttons extends JPanel implements ActionListener {
		JButton addBtn, exitBtn;

		Connection con = null;
		PreparedStatement pstmt = null;

		public Buttons() {
			addBtn = new JButton("�߰�");
			exitBtn = new JButton("�ݱ�");
			add(addBtn);
			add(exitBtn);

			addBtn.addActionListener(this);
			exitBtn.addActionListener(this);
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			if (e.getActionCommand().equals("�߰�")) {
				if (input.tf[1].getText().equals("") || input.tf[2].getText().equals("")
						|| input.tf[3].getText().equals("")) {
					JOptionPane.showConfirmDialog(null, "�ʼ��׸�(*)�� ��� �Է��ϼ���", "����� ����", JOptionPane.ERROR_MESSAGE);

				} else {
					try {
						con = DBUtil.getConnection();
						String sql = "insert into customer values(?,?,?,?,?,?)";
						pstmt = con.prepareStatement(sql);

						pstmt.setString(1, input.tf[0].getText());
						pstmt.setString(2, input.tf[1].getText());
						pstmt.setString(3, input.tf[2].getText());
						pstmt.setString(4, input.tf[3].getText());
						pstmt.setString(5, input.tf[4].getText());
						pstmt.setString(6, input.tf[5].getText());

						pstmt.executeUpdate();

						for (int i = 0; i < input.tf.length; i++) {
							input.tf[i].setText("");
						}
						input.tf[1].requestFocus();

					} catch (Exception ex) {
						ex.printStackTrace();
					} finally {
						DBUtil.close(pstmt, con);
					}
					JOptionPane.showMessageDialog(null, "���߰��� �Ϸ� �Ǿ����ϴ�.");

				}
			}

			if (e.getActionCommand().equals("�ݱ�")) {

				dispose();
			}
		}
	}

	public static void main(String[] args) {
		new Customer_Register();
	}

}
