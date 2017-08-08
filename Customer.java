import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class Customer extends JFrame implements ActionListener {
	JButton btn_register, btn_search, btn_contract, btn_exit;
	JPanel jpanel;
	ImageIcon images = new ImageIcon("Images/main.png");
	JLabel imageLabel = new JLabel(images);

	// 생성자 구현
	public Customer() {
		setTitle("보험계약 관리화면");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		btn_register = new JButton("고객 등록");
		btn_search = new JButton("고객 조회");
		btn_contract = new JButton("계약 관리");
		btn_exit = new JButton("종  료");
		jpanel = new JPanel();

		jpanel.add(btn_register);
		jpanel.add(btn_search);
		jpanel.add(btn_contract);
		jpanel.add(btn_exit);

		setLayout(new BorderLayout());
		add(jpanel, BorderLayout.NORTH);
		add(imageLabel, BorderLayout.CENTER);
		// 이벤트 연결
		btn_register.addActionListener(this);
		btn_search.addActionListener(this);
		btn_contract.addActionListener(this);
		btn_exit.addActionListener(this);

		setSize(650, 500);
		setLocation(450, 200);
		setVisible(true);

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getActionCommand().equals("고객 등록")) {
			new Customer_Register();
		}
		if (e.getActionCommand().equals("고객 조회")) {
			new Customor_select();
		}
		if (e.getActionCommand().equals("계약 관리")) {
			new Customor_Covenant();
		}
		if (e.getActionCommand().equals("종  료")) {
			System.exit(0);
		}

	}

	public static void main(String[] args) {
		new Customer();
	}
}
