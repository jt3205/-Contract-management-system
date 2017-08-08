import java.text.DecimalFormat;

/*
 * ���Աݾ�, ������� �ݾ׿� õ���� ���� �޸�(,) �ֱ� Ŭ����
 * ������ ������ õ�������� �޸��� �� ���������� �ٲ��ִ� �ڵ�
 */

public class Util {

	/*
	 * ���ڿ� õ�������� �޸�(,) �ֱ�
	 * 
	 * @param int
	 * 
	 * @return String
	 */
	public static String toNumFormat(int num) {
		DecimalFormat df = new DecimalFormat("#,###");

		return df.format(num);

	}
}
