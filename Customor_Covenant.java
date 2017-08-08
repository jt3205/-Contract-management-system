import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Calendar;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

public class Customor_Covenant extends JFrame implements ActionListener {
	// my sources

	JLabel c_code, c_name, c_birth, c_tel, contract, amount, premium, admin, c_list;
	JTextField t_code, t_birth, t_tel, t_amount, t_premium;
	JButton select_allBtn, join, del, save, close;

	JComboBox<String> cb;
	JComboBox<String> cb2;

	JComboBox<String> customerName_combo; // 고객명 콤보박스
	JComboBox<String> contractName_combo; // 보험상품명 콤보박스
	String contractName_arr[] = { "무배당암보험", "변액연금보험", "여성건강보험", "연금보험", "의료실비보험", "종신보험" };
	JComboBox<String> adminName_combo; // 담당자명 콤보박스

	Vector<String> vec;

	// 내부 클래스
	ShowTable showTable = new ShowTable();
	DefaultTableModel model;
	JTable tb;
	int selectRow;
	String cbstr = "";

	DefaultTableCellRenderer cellAlignCenter;
	DefaultTableCellRenderer cellAlignRight;

	String customerSql = "select name from customer order by name asc";
	String adminSql = "select name from admin order by name asc";

	String codeDB;
	String birthDB;
	String telDB;

	Connection con = null;
	PreparedStatement pstmt;

	Statement stm;
	Statement customer_stm;
	Statement admin_stm;

	ResultSet rs = null;
	ResultSet customer_rs = null;
	ResultSet admin_rs = null;

	public Customor_Covenant() {
		setTitle("보험계약");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		c_code = new JLabel("고객코드:");
		t_code = new JTextField();

		c_name = new JLabel("고  객  명:");
		customerName_combo = new JComboBox<String>();


		c_birth = new JLabel("생년월일:");
		t_birth = new JTextField();

		c_tel = new JLabel("연  락  처:");
		t_tel = new JTextField();

		contract = new JLabel("보험상품:");
		contractName_combo = new JComboBox<String>(contractName_arr);

		amount = new JLabel("가입금액:");
		t_amount = new JTextField();

		premium = new JLabel("월보험료:");
		t_premium = new JTextField();

		admin = new JLabel("담당자 :");
		adminName_combo = new JComboBox<String>();


		select_allBtn = new JButton("전체보기");
		select_allBtn.setBackground(Color.YELLOW);

		join = new JButton("가입");
		join.setBackground(Color.GREEN);

		del = new JButton("삭제");
		del.setBackground(Color.ORANGE);

		close = new JButton("닫기");

		c_list = new JLabel("< 고객 보험 계약현황 >");

		setLayout(null);
		c_code.setBounds(60, 10, 100, 20);
		t_code.setBounds(140, 10, 120, 20);
		c_name.setBounds(60, 30, 100, 20);
		customerName_combo.setBounds(140, 30, 120, 20);
		c_birth.setBounds(60, 50, 100, 20);
		t_birth.setBounds(140, 50, 120, 20);
		c_tel.setBounds(60, 70, 100, 20);
		t_tel.setBounds(140, 70, 120, 20);

		contract.setBounds(320, 10, 100, 20);
		contractName_combo.setBounds(390, 10, 130, 20);
		amount.setBounds(320, 30, 100, 20);
		t_amount.setBounds(390, 30, 130, 20);
		premium.setBounds(320, 50, 100, 20);
		t_premium.setBounds(390, 50, 130, 20);

		admin.setBounds(60, 120, 100, 20);
		adminName_combo.setBounds(120, 120, 90, 20);
		select_allBtn.setBounds(230, 120, 90, 20);
		join.setBounds(330, 120, 70, 20);
		del.setBounds(420, 120, 70, 20);
		close.setBounds(500, 120, 70, 20);
		c_list.setBounds(230, 170, 200, 20);

		customerName_combo.addActionListener(this);
		select_allBtn.addActionListener(this);
		join.addActionListener(this);
		del.addActionListener(this);
		close.addActionListener(this);

		try {
			con = DBUtil.getConnection();

			customer_stm = con.createStatement();
			admin_stm = con.createStatement();

			customer_rs = customer_stm.executeQuery(customerSql);
			admin_rs = admin_stm.executeQuery(adminSql);

			while (customer_rs.next()) {
				cbstr = customer_rs.getString(1);
				customerName_combo.addItem(cbstr);
			}

			while (admin_rs.next()) {
				cbstr = admin_rs.getString(1);
				adminName_combo.addItem(cbstr);
			}

		} catch (Exception e) {
			System.out.println(e);
		} finally {
			try {
				if (!admin_rs.isClosed())
					admin_rs.close();
				if (!customer_rs.isClosed())
					customer_rs.close();
				if (!admin_stm.isClosed())
					admin_stm.close();
				if (!customer_stm.isClosed())
					customer_stm.close();
				if (!con.isClosed())
					con.close();
			} catch (Exception ee) {
				System.out.println(ee);
			}
		}

		add(admin);
		add(adminName_combo);
		add(select_allBtn);
		add(join);
		add(del);
		add(close);
		add(c_code);
		add(c_name);
		add(c_birth);
		add(c_tel);
		add(contract);
		add(amount);
		add(premium);

		add(t_code);
		add(customerName_combo);
		add(contractName_combo);
		add(t_birth);
		add(t_tel);
		add(t_amount);
		add(t_premium);
		add(c_list);

		showTable.scrollPane.setBounds(0, 210, 640, 600);
		add(showTable.scrollPane);

		setSize(650, 700);
		setLocation(700, 150);
		setVisible(true);

	}

	class ShowTable extends MouseAdapter {
		DefaultTableModel datamodel;
		JTable table;
		JScrollPane scrollPane;

		String[] colName = { "고객코드", "고객명", "보험상품", "가입일", "가입금액", "월보험료", "담당자" };

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

			// Cell 데이터 값을 가운데 오른쪽 정렬
			cellAlignCenter = new DefaultTableCellRenderer();
			cellAlignCenter.setHorizontalAlignment(SwingConstants.CENTER);

			cellAlignRight = new DefaultTableCellRenderer();
			cellAlignRight.setHorizontalAlignment(SwingConstants.RIGHT);

			table.getColumn("고객코드").setPreferredWidth(70);
			table.getColumn("고객코드").setCellRenderer(cellAlignCenter);

			table.getColumn("고객명").setPreferredWidth(70);
			table.getColumn("고객명").setCellRenderer(cellAlignCenter);

			table.getColumn("보험상품").setPreferredWidth(130);
			table.getColumn("보험상품").setCellRenderer(cellAlignCenter);

			table.getColumn("가입일").setPreferredWidth(70);
			table.getColumn("가입일").setCellRenderer(cellAlignCenter);

			table.getColumn("가입금액").setPreferredWidth(80);
			table.getColumn("가입금액").setCellRenderer(cellAlignRight);

			table.getColumn("월보험료").setPreferredWidth(70);
			table.getColumn("월보험료").setCellRenderer(cellAlignRight);

			table.getColumn("담당자").setPreferredWidth(70);
			table.getColumn("담당자").setCellRenderer(cellAlignCenter);

		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		String customerName = customerName_combo.getSelectedItem().toString();

		try {
			con = DBUtil.getConnection();
			String select_sql = "select code,birth,tel from customer where name = ?";
			pstmt = con.prepareStatement(select_sql);
			pstmt.setString(1, customerName);
			rs = pstmt.executeQuery();

			if (rs.next()) {
				codeDB = rs.getString(1);
				birthDB = rs.getString(2);
				telDB = rs.getString(3);

				t_code.setText(codeDB);
				t_birth.setText(birthDB);
				t_tel.setText(telDB);
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			DBUtil.close(rs, pstmt, con);
		}

		if (e.getActionCommand().equals("전체보기")) {
			selectAll();
		}

		if (e.getActionCommand().equals("가입")) {
			regInput();
		}

		if (e.getActionCommand().equals("삭제")) {
			del();
		}

		if (e.getActionCommand().equals("닫기")) {
			dispose();
			new Customer();
		}

	}

	public void selectAll() {
		JTable tb = showTable.table;
		DefaultTableModel model = (DefaultTableModel) tb.getModel();
		model.setNumRows(0);

		try {
			con = DBUtil.getConnection();
			String sql = "select * from contract order by regdate desc";
			stm = con.createStatement();
			rs = stm.executeQuery(sql);

			while (rs.next()) {
				vec = new Vector<String>();
				vec.add(rs.getString(1));
				vec.add(rs.getString(2));
				vec.add(rs.getString(3));
				Calendar cal = Calendar.getInstance();

				String year = rs.getString(4).substring(0, 4);
				String month = rs.getString(4).substring(5, 7);
				String day = rs.getString(4).substring(8, 10);
				String reg_date = year + "-" + month + "-" + day;

				vec.add(reg_date);
				vec.add(Util.toNumFormat(Integer.parseInt(rs.getString(5))));
				vec.add(Util.toNumFormat(Integer.parseInt(rs.getString(6))));
				vec.add(rs.getString(7));
				showTable.data.addElement(vec);
			}
			showTable.datamodel.fireTableDataChanged();
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			try {
				if (!con.isClosed())
					con.close();
				if (!stm.isClosed())
					stm.close();
				if (!rs.isClosed())
					rs.close();

			} catch (Exception ee) {
				ee.printStackTrace();
			}
		}

	}

	public void regInput() {
		String customerName = customerName_combo.getSelectedItem().toString();
		String contractName = contractName_combo.getSelectedItem().toString();
		String customerCode = t_code.getText();
		String amount = t_amount.getText();
		String premium = t_premium.getText();
		String adminName = adminName_combo.getSelectedItem().toString();

		if (t_amount.getText().equals(""))
			return;
		if (t_premium.getText().equals(""))
			return;

		JTable tb = showTable.table;
		model = (DefaultTableModel) tb.getModel();

		model.setNumRows(0);

		try {
			con = DBUtil.getConnection();
			String sql = "insert into contract values(?,?,?,sysdate,?,?,?)";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, customerCode);
			pstmt.setString(2, customerName);
			pstmt.setString(3, contractName);
			pstmt.setString(4, amount);
			pstmt.setString(5, premium);
			pstmt.setString(6, adminName);

			pstmt.executeUpdate();

			JOptionPane.showMessageDialog(null, "보험계약이 완료되었습니다.");

			t_code.setText(null);
			t_birth.setText(null);
			t_tel.setText(null);
			t_amount.setText(null);
			t_premium.setText(null);

			selectAll();

			/*
			 * vec = new Vector<String>();
			 * 
			 * vec.add(t_code.getText());
			 * vec.add(customerName_combo.getSelectedItem().toString());
			 * vec.addElement(contractName_combo.getSelectedItem().toString());
			 * 
			 * Calendar cal = Calendar.getInstance();
			 * 
			 * String year = Integer.toString(cal.get(Calendar.YEAR)); String
			 * month = Integer.toString(cal.get(Calendar.MONTH)+1); String day =
			 * Integer.toString(cal.get(Calendar.DAY_OF_MONTH)); String reg_date
			 * = year+"-"+month+"-"+day;
			 * 
			 * vec.add(reg_date);
			 * 
			 * int at = Integer.parseInt(t_amount.getText()); String amount_str
			 * = Util.toNumFormat(at); vec.add(amount_str);
			 * 
			 * int pm = Integer.parseInt(t_premium.getText()); String
			 * premium_str = Util.toNumFormat(pm); vec.add(premium_str);
			 * 
			 * vec.add(adminName_combo.getSelectedItem().toString());
			 * 
			 * showTable.data.addElement(vec);
			 * 
			 * showTable.datamodel.fireTableDataChanged();
			 */

		} catch (Exception ee) {
			ee.printStackTrace();
		} finally {
			try {
				DBUtil.close(pstmt, con);
			} catch (Exception eee) {
				eee.printStackTrace();
			}
		}
	}

	public void del() {
		int select_row = showTable.table.getSelectedRow();

		if (select_row == -1)
			return;

		String delCode = (String) showTable.table.getValueAt(select_row, 0);
		String delName = (String) showTable.table.getValueAt(select_row, 1);
		String delContract = (String) showTable.table.getValueAt(select_row, 2);

		int ok_cancel = JOptionPane.showConfirmDialog(null, delCode + "(" + delContract + ")" + "을 정말 삭제하시겠습니까?",
				"계약정보 삭제", JOptionPane.OK_CANCEL_OPTION);

		if (ok_cancel == JOptionPane.OK_OPTION) {
			DefaultTableModel model = (DefaultTableModel) showTable.table.getModel();
			model.removeRow(select_row);
			showTable.datamodel.fireTableDataChanged();

			try {
				con = DBUtil.getConnection();
				String sql = "delete from contract where customerCode = ? and customerName = ? and contractName = ?";
				pstmt = con.prepareStatement(sql);
				pstmt.setString(1, delCode);
				pstmt.setString(2, delName);
				pstmt.setString(3, delContract);
				pstmt.executeQuery();

			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				try {
					DBUtil.close(pstmt, con);
				} catch (Exception ee) {
					ee.printStackTrace();
				}
			}
			JOptionPane.showMessageDialog(null, delCode + "(" + delContract + ")" + "데이터가 삭제되었습니다.");
			t_code.setText(null);
			t_birth.setText(null);
			t_tel.setText(null);
			t_amount.setText(null);
			t_premium.setText(null);

		}
	}
}
