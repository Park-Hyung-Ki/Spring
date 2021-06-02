package www.dream.com.common.dto;

import lombok.Data;

// @보다 만들어진 생성자가 우선순위가 더 높아서 나중에 @로 만들어진 생성자는 만들어지지 않는다.
@Data
public class Criteria {
	
	private static final float PAGINATION_TOTAL = 10;
	private int pageNumber; // 현재 Page 번호
	private int amount; // Page당 보여줄 Data 건 수 10개로 할 거임
	
	private int startPage, endPage; // Web 하단에 출력되는 Page Num을 표시해주는 속성들을 만들어 낼 것
	private boolean prev, next; // Page를 앞,뒤로 가기 활성화
	
	private long total; // 전체 Data건 수
	
	public Criteria() { // Default 생성자도 만들어 줄 것
		this.pageNumber = 1; // page 1쪽에 10개씩 , Total Page는 2100개로줄것, 내 Data건수가 22529개.
		this.amount = 10;
	}
	
	public void setTotal(long total) {
		this.total = total;
		calc();
	}
	
	
	
	/* 이 부분이 없으면 WebServer에서 PageNumber가 10 이후로 넘어가지 않는 상황이 발생.
	 * 그 이유로는 바로 위의 함수에서 계산이, startPage의 계산이 먼저 일어나지 않고
	 * endPage에서 계산이 먼저 일어났던것, 함수명 어순과 별개로
	 *  이 방법을 해결하기위해서 새로운 객체를 하나 더 만들어서 진행하고있고, 추후에 생기는 문제도 이 요소를 추가해줌으로써
	 *  상당히 해결이 많이 되고 있다.
	 * @param Park
	 */

	private void calc() {
		endPage = (int) (Math.ceil(pageNumber / PAGINATION_TOTAL) * PAGINATION_TOTAL);
		startPage = endPage - (int) (PAGINATION_TOTAL - 1);
		int realEnd = (int) Math.ceil((float) total / amount);
		if (endPage > realEnd) {
			endPage = realEnd;
		}
		prev = startPage > 1;
		next = endPage < realEnd;
		
		}
	}

