package www.dream.com.framework.util;

public class StringUtil {
	/**
	 * ids의 정보를 분할하여 정수 배열로 반환
	 * @param ids 숫자가 ,로 구분되어 있습니다
	 * @return
	 */

	public static int[] convertCommnaSepString2IntArr(String ids) { // 함수를 만들었으니, 이제 Method를 선언
		String[] splited = ids.split(","); // ,를 줄테니 쪼개볼 것
		int[] ret = new int[splited.length]; // 
		for(int i = 0; i < splited.length; i++) { // int형을 사용하기때문에 foreach 형이 아닌, 정통 for문을 사용
			try {
				ret[i] = Integer.parseInt(splited[i]);
			} catch(NumberFormatException e) {
				
			}
		}
		return ret;
	} //06.02 作
	
	/*public static void main(String[] args) {
		int[] aa = convertCommnaSepString2IntArr("1,2,3");
		for (int a : aa) {
			System.out.println(a);
		}
	}*/
}
