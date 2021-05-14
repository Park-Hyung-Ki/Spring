package www.dream.com.sample.model;

import lombok.Data;
import lombok.Setter;

// sampleVO와는 1(Master) : n(sample) 관계
// 1.Meta용어로 보던 Master : Detail 구조를 학습해보려고 한다 p.133


@Data // 출력해내기 위해서 @Data를 사용해볼것 -> 출력이 된다. SampleController에서 다만, 이 @Data를 안쓰고 할수있는 방법이 있을까 
public class SampleMasterVO {
	@Setter // 6. Setter 생성
	private int id; // 3

	// 2. 관게 속성. Master-Detail relation 1:N 관계 (위에도 설명 있음)
	@Setter // 7. Setter 생성
	// private List<SampleVO> listSampleVO; // 이게 일단 server로 보내면 오류가 생기니까
	private SampleVO[] listSampleVO = new SampleVO[10]; // server에서 인식하는걸로 받아들이게 하기위해서 배열로 변수까지 바꿔줌
	
	//private List<SampleVO> listSampleVO = new ArrayList<>() 배열로도 출력을 해볼건데 이거로 위에 살아있는부분주석 처리하고 해도 된다. 
	
	// public SampleMasterVO() { //4 .이거 @NoArgsConstructor랑도 같은거.
	// listSampleVO = new ArrayList<>(); //5.변수용 객체 만들기 여기 부분은 List로 쓸대만 사용가능

	// }

	@Override // 8. 후에 함수로 받아들일 부분 생성
	public String toString() { // 내가 갖고있는 정보들로 StringBuilder를 사용하면 "성능"이 좋아진다.
		//return "SampleMasterVO [id=" + id + ", listSampleVO=" + listSampleVO + "]";
		// ↑ 위에 부분도 배열 형식으로 바꿔볼건데
		
		StringBuilder sb = new StringBuilder(); // 여기 부분부터 순서대로 작성 
		sb.append("SampleMasterVO [id=").append(id).append(", listSampleVO=");
		for (SampleVO obj : listSampleVO) {
			if (obj != null) {
				sb.append(obj.toString());
			}
		}
		sb.append("]");
		return sb.toString();
	}
}
