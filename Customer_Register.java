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
	// 내부 클래스 객체 생성
	Input input = new Input();
	Buttons buttons = new Buttons();

	public Customer_Register() {
		setTitle("고객 등록");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		add(input, BorderLayout.CENTER);
		add(buttons, BorderLayout.SOUTH);

		setSize(400, 300);
		setLocation(500, 150);
		setVisible(true);

	}

	class Input extends JPanel implements KeyListener, FocusListener {
		String text[] = { "고객코드:", "*고 객 명:", "*생년월일(YYYY-MM-DD):", "*연 락 처:", "주       소:", "회       사:" };
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

			// 이벤트 연결

		}

		@Override
		public void focusGained(FocusEvent arg0) {
			// TODO Auto-generated method stub
			Calendar cal = Calendar.getInstance(Locale.KOREA);
			int year = cal.get(Calendar.YEAR); // 현재년도 를 얻어옴
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
				int year = cal.get(Calendar.YEAR); // 현재년도 를 얻어옴
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
			addBtn = new JButton("추가");
			exitBtn = new JButton("닫기");
			add(addBtn);
			add(exitBtn);

			addBtn.addActionListener(this);
			exitBtn.addActionListener(this);
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			if (e.getActionCommand().equals("추가")) {
				if (input.tf[1].getText().equals("") || input.tf[2].getText().equals("")
						|| input.tf[3].getText().equals("")) {
					JOptionPane.showConfirmDialog(null, "필수항목(*)를 모두 입력하세요", "고객등록 에러", JOptionPane.ERROR_MESSAGE);

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
					JOptionPane.showMessageDialog(null, "고객추가가 완료 되었습니다.");

				}
			}

			if (e.getActionCommand().equals("닫기")) {

				dispose();
			}
		}
	}

	public static void main(String[] args) {
		new Customer_Register();
	}

}
