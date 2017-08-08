import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

public class Login extends JFrame implements ActionListener {
	JLabel manaLogin, nameLabel, pwLabel;
	JTextField name_val;
	JPasswordField pw_val;
	JButton btn_ok, btn_cancel;
	String name_str;
	String pw_str, nameDB, pwDB;

	Connection con = null;
	PreparedStatement pstmt = null;
	ResultSet rs = null;

	public Login() {

		setTitle("�α���");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		manaLogin = new JLabel("������ �α���", JLabel.CENTER);
		nameLabel = new JLabel("�̸�");
		name_val = new JTextField(20);

		pwLabel = new JLabel("��й�ȣ");
		pw_val = new JPasswordField();
		pw_val.setEchoChar('*');

		btn_ok = new JButton("Ȯ��");
		btn_cancel = new JButton("���");

		setLayout(null);
		manaLogin.setBounds(50, 10, 100, 20);

		nameLabel.setBounds(50, 50, 60, 20);
		name_val.setBounds(110, 50, 100, 20);

		pwLabel.setBounds(50, 70, 60, 20);
		pw_val.setBounds(110, 70, 100, 20);

		btn_ok.setBounds(80, 110, 60, 20);
		btn_cancel.setBounds(150, 110, 60, 20);

		add(manaLogin);
		add(nameLabel);
		add(name_val);
		add(pwLabel);
		add(pw_val);
		add(btn_ok);
		add(btn_cancel);

		btn_ok.addActionListener(this);
		btn_cancel.addActionListener(this);

		setSize(300, 200);
		setLocation(700, 150);
		setVisible(true);

		addKeyListener(new MyKey());
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getActionCommand().equals("Ȯ��")) {
			name_str = name_val.getText();
			pw_str = new String(pw_val.getPassword());
			// �ڵ�

			try {
				// DBUtil Ŭ������ getConnection() �޼ҵ�� DB�� ����
				con = DBUtil.getConnection();

				String sql = "select name,passwd from admin where name=?";

				pstmt = con.prepareStatement(sql);
				pstmt.setString(1, name_str);
				rs = pstmt.executeQuery();

				if (rs.next()) {
					nameDB = rs.getString(1);
					pwDB = rs.getString(2);
				}
				if (name_str.equals(nameDB) && pw_str.equals(pwDB)) {
					setVisible(false);
					new Customer();
				} else {
					JOptionPane.showMessageDialog(null, "�������̸� �Ǵ� ��й�ȣ�� ��ġ���� ����!", "������ �α��� ����",
							JOptionPane.ERROR_MESSAGE);
				}

			} catch (Exception ex) {
				System.out.println(ex);
			} finally {
				DBUtil.close(rs, pstmt, con);
			}

		}

		else if (e.getActionCommand().equals("����")) {
			setVisible(false);
			setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		}

	}

	class MyKey extends KeyAdapter {
		@Override
		public void keyPressed(KeyEvent e) {

			if (e.getKeyCode() == KeyEvent.VK_ENTER) {
				System.out.println("��");
				name_str = name_val.getText();
				pw_str = new String(pw_val.getPassword());
				// �ڵ�

				try {
					// DBUtil Ŭ������ getConnection() �޼ҵ�� DB�� ����
					con = DBUtil.getConnection();

					String sql = "select name,passwd from admin where name=?";

					pstmt = con.prepareStatement(sql);
					pstmt.setString(1, name_str);
					rs = pstmt.executeQuery();

					if (rs.next()) {
						nameDB = rs.getString(1);
						pwDB = rs.getString(2);
					}
					if (name_str.equals(nameDB) && pw_str.equals(pwDB)) {
						setVisible(false);
						new Customer();
					} else {
						JOptionPane.showMessageDialog(null, "�������̸� �Ǵ� ��й�ȣ�� ��ġ���� ����!", "������ �α��� ����",
								JOptionPane.ERROR_MESSAGE);
					}

				} catch (Exception ex) {
					System.out.println(ex);
				} finally {
					DBUtil.close(rs, pstmt, con);
				}
			}

		}

		@Override
		public void keyReleased(KeyEvent e) {
		}

		@Override
		public void keyTyped(KeyEvent e) {
		}
	}

	public static void main(String[] args) {
		new Login();
	}

}
