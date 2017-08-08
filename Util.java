import java.text.DecimalFormat;

/*
 * 가입금액, 월보험료 금액에 천단위 마다 콤마(,) 넣기 클래스
 * 숫자형 변수에 천단위마다 콤마를 찍어서 문자형으로 바꿔주는 코드
 */

public class Util {

	/*
	 * 숫자에 천단위마다 콤마(,) 넣기
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
