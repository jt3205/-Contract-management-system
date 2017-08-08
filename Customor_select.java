import java.awt.BorderLayout;

import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Vector;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

//import Customer_Register.Input;

public class Customor_select extends JFrame implements ActionListener {

	public static final String String = null;
	// Customer_updata cus_updata;

	// 내부 클래스
	ShowTable showtable = new ShowTable();
	Customer_update customer_update;

	JLabel name;
	JTextField name_value;
	JButton selectBtn, select_firstNameBtn, se_allBtn, updateBtn, delBtn, closeBtn;
	JPanel northPanel;
	String name_str;
	String name_first;
	String delete_name;

	DefaultTableModel model;

	Connection con = null;
	PreparedStatement pstmt = null;
	ResultSet rs = null;

	Vector<String> vec;
	int select_Row;
	// int deleteRow;

	public Customor_select() { // 생성자
		setTitle("고객조회");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		northPanel = new JPanel();
		name = new JLabel("성명");
		name_value = new JTextField(10);
		selectBtn = new JButton("조회(이름)");
		select_firstNameBtn = new JButton("조회(이름 첫글자)");
		se_allBtn = new JButton("전체보기");
		updateBtn = new JButton("수정");
		delBtn = new JButton("삭제");
		closeBtn = new JButton("닫기");

		northPanel.add(name);
		northPanel.add(name_value);
		northPanel.add(selectBtn);
		northPanel.add(select_firstNameBtn);
		northPanel.add(se_allBtn);
		northPanel.add(updateBtn);
		northPanel.add(delBtn);
		northPanel.add(closeBtn);

		setLayout(new BorderLayout());
		add(northPanel, BorderLayout.NORTH);
		add(showtable.scrollPane, BorderLayout.CENTER);

		// 이벤트 연결
		selectBtn.addActionListener(this);
		select_firstNameBtn.addActionListener(this);
		se_allBtn.addActionListener(this);
		updateBtn.addActionListener(this);
		delBtn.addActionListener(this);
		closeBtn.addActionListener(this);

		setSize(700, 700);
		setLocation(700, 100);
		setVisible(true);
	} // end 생성자

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getActionCommand().equals("조회(이름)")) {
			select();
		}
		if (e.getActionCommand().equals("조회(이름 첫글자)")) {
			select_firstName();
		}
		if (e.getActionCommand().equals("전체보기")) {
			selectAll();
		}
		if (e.getActionCommand().equals("수정")) {
			upDate();
		}
		if (e.getActionCommand().equals("삭제")) {
			delData();
		}
		if (e.getActionCommand().equals("닫기")) {
			dispose();

		}

	}

	public void upDate() {
		select_Row = showtable.table.getSelectedRow();
		customer_update = new Customer_update();
		select_Row = this.select_Row;

	}

	public void delData() {
		select_Row = showtable.table.getSelectedRow();
		if (select_Row == -1) {
			return;
		}

		String del_code = (String) showtable.table.getValueAt(select_Row, 0);
		String del_name = (String) showtable.table.getValueAt(select_Row, 1);

		int ok_cancel = JOptionPane.showConfirmDialog(null, del_name + "님을 정말 삭제하시겠습니까?", "고객정보 삭제",
				JOptionPane.OK_CANCEL_OPTION);

		if (ok_cancel == JOptionPane.OK_OPTION) {
			DefaultTableModel model = (DefaultTableModel) showtable.table.getModel();
			model.removeRow(select_Row);
			showtable.datamodel.fireTableDataChanged();

			try {

				con = DBUtil.getConnection();

				String sql_del = "delete from customer " + "where code = ?";

				pstmt = con.prepareStatement(sql_del);
				// name_str = name_value.getText();
				pstmt.setString(1, del_code);

				pstmt.executeUpdate();
			} catch (Exception ee) {
				System.out.println(ee);
			} finally {
				try {
					if (pstmt != null)
						pstmt.close();
					if (con != null)
						con.close();
				} catch (Exception ex) {
					System.out.println(ex);
				}
			}
			JOptionPane.showMessageDialog(null, del_name + "님 데이터가 삭제되었습니다.");

		}
	}

	class Customer_update extends JFrame {
		private String name_tf, birth, tel, address, cmpany;

		Input input = new Input();
		Buttons buttons = new Buttons();

		public Customer_update() {
			setTitle("고객 수정");
			setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

			add(input, BorderLayout.CENTER);
			add(buttons, BorderLayout.SOUTH);

			setBounds(500, 150, 450, 200);
			setVisible(true);
		}

		class Input extends JPanel {
			String text[] = { "고객코드:", "*고객명:", "*생년월일(YYYY-MM-DD):", "*연락처:", "주	소:", "회	사:" };
			JTextField tf[] = new JTextField[6];
			String yy;

			public Input() {
				setLayout(new GridLayout(6, 2));

				for (int i = 0; i < text.length; i++) {
					JLabel la = new JLabel(text[i]);
					tf[i] = new JTextField(20);
					tf[i].setText((String) showtable.table.getValueAt(select_Row, i));

					add(la);
					add(tf[i]);
				}
				tf[0].setEditable(false);
				tf[1].setEditable(false);
			}
		} // Input class

		class Buttons extends JPanel implements ActionListener {
			JButton updateBtn, cancelBtn;

			Connection con = null;
			PreparedStatement pstmt = null;

			public Buttons() {
				updateBtn = new JButton("수정");
				cancelBtn = new JButton("취소");
				add(updateBtn);
				add(cancelBtn);

				updateBtn.addActionListener(this);
				cancelBtn.addActionListener(this);
			}

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				if (e.getActionCommand().equals("수정")) {

					String name_tf = customer_update.input.tf[1].getText();
					String birth = customer_update.input.tf[2].getText();
					String tel = customer_update.input.tf[3].getText();
					String address = customer_update.input.tf[4].getText();
					String company = customer_update.input.tf[5].getText();

					showtable.table.setValueAt(input.tf[1].getText(), select_Row, 1);

					showtable.table.setValueAt(input.tf[2].getText(), select_Row, 2);

					showtable.table.setValueAt(input.tf[3].getText(), select_Row, 3);

					showtable.table.setValueAt(input.tf[4].getText(), select_Row, 4);

					showtable.table.setValueAt(input.tf[5].getText(), select_Row, 5);

					try {
						con = DBUtil.getConnection();
						String sql = "update customer set birth=?,tel=?, address=?, company=? where code=? ";

						pstmt = con.prepareStatement(sql);
						pstmt.setString(1, input.tf[2].getText());
						pstmt.setString(2, input.tf[3].getText());
						pstmt.setString(3, input.tf[4].getText());
						pstmt.setString(4, input.tf[5].getText());
						pstmt.setString(5, input.tf[0].getText());

						pstmt.executeUpdate();

					} catch (Exception ee) {
						System.out.println(ee);
					} finally {
						try {
							if (pstmt != null)
								pstmt.close();
							if (con != null)
								con.close();
						} catch (Exception ex) {
							System.out.println(ex);
						}
					} // try-catch-finally

					JOptionPane.showMessageDialog(null, "고객 수정 완료");
				} // 수정버튼
				if (e.getActionCommand().equals("취소")) {
					dispose();
				}
			}
		}
	}

	// "조회(이름)" 메소드 구현
	public void select() {

		Vector<String> vec;
		JTable tb = showtable.table;
		model = (DefaultTableModel) tb.getModel();

		// [중요] JTable 초기화 => Row가 0으로 되서 처음부터
		model.setNumRows(0);

		try {
			con = DBUtil.getConnection();

			String sql_namesearch = "select * from customer where name=?";

			pstmt = con.prepareStatement(sql_namesearch);
			name_str = name_value.getText();
			pstmt.setString(1, name_str);

			rs = pstmt.executeQuery();

			if (rs.next()) {
				vec = new Vector<String>();
				vec.add(rs.getString(1));
				vec.add(rs.getString(2));
				vec.add(rs.getString(3));
				vec.add(rs.getString(4));
				vec.add(rs.getString(5));
				vec.add(rs.getString(6));

				showtable.data.addElement(vec);
			}
			showtable.datamodel.fireTableDataChanged();
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			DBUtil.close(rs, pstmt, con);
		}
	}

	public void select_firstName() {
		Vector<String> vec;
		JTable tb = showtable.table;
		model = (DefaultTableModel) tb.getModel();

		// [중요] JTable 초기화 => Row가 0으로 되서 처음부터
		model.setNumRows(0);

		try {
			con = DBUtil.getConnection();

			String a = name_value.getText();
			System.out.println(a);

			String sql_first_namesearch = "select * from customer where name like ?||'%' order by name";

			pstmt = con.prepareStatement(sql_first_namesearch);
			name_str = name_value.getText();
			pstmt.setString(1, name_str);

			rs = pstmt.executeQuery();

			while (rs.next()) {
				vec = new Vector<String>();
				vec.add(rs.getString(1));
				vec.add(rs.getString(2));
				vec.add(rs.getString(3));
				vec.add(rs.getString(4));
				vec.add(rs.getString(5));
				vec.add(rs.getString(6));

				showtable.data.addElement(vec);
			}
			showtable.datamodel.fireTableDataChanged();
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			DBUtil.close(rs, pstmt, con);
		}
	}

	public void selectAll() {
		Vector<String> vec;
		JTable tb = showtable.table;
		model = (DefaultTableModel) tb.getModel();

		// [중요] JTable 초기화 => Row가 0으로 되서 처음부터
		model.setNumRows(0);

		try {
			con = DBUtil.getConnection();

			String sql_all = "select * from customer order by name asc";

			pstmt = con.prepareStatement(sql_all);
			// name_str = name_value.getText();
			// pstmt.setString(1, name_str);

			rs = pstmt.executeQuery();

			while (rs.next()) {
				vec = new Vector<String>();
				vec.add(rs.getString(1));
				vec.add(rs.getString(2));
				vec.add(rs.getString(3));
				vec.add(rs.getString(4));
				vec.add(rs.getString(5));
				vec.add(rs.getString(6));

				showtable.data.addElement(vec);
			}
			showtable.datamodel.fireTableDataChanged();
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			DBUtil.close(rs, pstmt, con);
		}
	}

	// ShowTable 내부 클래스
	class ShowTable extends MouseAdapter {

		DefaultTableModel datamodel;
		JTable table;
		JScrollPane scrollPane;

		String[] colName = { "code", "name", "birth", "tel", "address", "company" };

		Vector<Vector<String>> data;
		Vector<String> column_name;

		public ShowTable() {
			data = new Vector<Vector<String>>();
			column_name = new Vector<String>();

			for (int i = 0; i < colName.length; i++) {
				column_name.add(colName[i]);
			}
			datamodel = new DefaultTableModel(data, column_name);
			table = new JTable(datamodel);

			scrollPane = new JScrollPane(table);

		}

	}

	public static void main(String[] args) {
		new Customor_select();
	}
}
