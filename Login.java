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

		setTitle("로그인");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		manaLogin = new JLabel("관리자 로그인", JLabel.CENTER);
		nameLabel = new JLabel("이름");
		name_val = new JTextField(20);

		pwLabel = new JLabel("비밀번호");
		pw_val = new JPasswordField();
		pw_val.setEchoChar('*');

		btn_ok = new JButton("확인");
		btn_cancel = new JButton("취소");

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
		if (e.getActionCommand().equals("확인")) {
			name_str = name_val.getText();
			pw_str = new String(pw_val.getPassword());
			// 코딩

			try {
				// DBUtil 클래스의 getConnection() 메소드로 DB와 연결
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
					JOptionPane.showMessageDialog(null, "관리자이름 또는 비밀번호가 일치하지 않음!", "관리자 로그인 실패",
							JOptionPane.ERROR_MESSAGE);
				}

			} catch (Exception ex) {
				System.out.println(ex);
			} finally {
				DBUtil.close(rs, pstmt, con);
			}

		}

		else if (e.getActionCommand().equals("종료")) {
			setVisible(false);
			setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		}

	}

	class MyKey extends KeyAdapter {
		@Override
		public void keyPressed(KeyEvent e) {

			if (e.getKeyCode() == KeyEvent.VK_ENTER) {
				System.out.println("야");
				name_str = name_val.getText();
				pw_str = new String(pw_val.getPassword());
				// 코딩

				try {
					// DBUtil 클래스의 getConnection() 메소드로 DB와 연결
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
						JOptionPane.showMessageDialog(null, "관리자이름 또는 비밀번호가 일치하지 않음!", "관리자 로그인 실패",
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
