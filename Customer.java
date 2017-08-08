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

	// ������ ����
	public Customer() {
		setTitle("������ ����ȭ��");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		btn_register = new JButton("�� ���");
		btn_search = new JButton("�� ��ȸ");
		btn_contract = new JButton("��� ����");
		btn_exit = new JButton("��  ��");
		jpanel = new JPanel();

		jpanel.add(btn_register);
		jpanel.add(btn_search);
		jpanel.add(btn_contract);
		jpanel.add(btn_exit);

		setLayout(new BorderLayout());
		add(jpanel, BorderLayout.NORTH);
		add(imageLabel, BorderLayout.CENTER);
		// �̺�Ʈ ����
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
		if (e.getActionCommand().equals("�� ���")) {
			new Customer_Register();
		}
		if (e.getActionCommand().equals("�� ��ȸ")) {
			new Customor_select();
		}
		if (e.getActionCommand().equals("��� ����")) {
			new Customor_Covenant();
		}
		if (e.getActionCommand().equals("��  ��")) {
			System.exit(0);
		}

	}

	public static void main(String[] args) {
		new Customer();
	}
}
