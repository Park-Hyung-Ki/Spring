package www.dream.com;

import org.apache.ibatis.annotations.Select;

public interface TimeMapper {
	@Select("select sysdate from dual") //2. Select라는 @를 사용할수 있습니다.
	public String getTime(); // 1.Time을 불러오는 함수를 만들어볼까요 
	
	public String getTimeByxml();
}
