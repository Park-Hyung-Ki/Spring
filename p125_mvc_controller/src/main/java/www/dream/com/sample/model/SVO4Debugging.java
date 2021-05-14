package www.dream.com.sample.model;
// null 값이 많이 뜨는 분들이 있어서 한번 오류를찾아보기위해서 Test
public class SVO4Debugging {
	private String name;
	private int age;
	
	public SVO4Debugging() {
		
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setAge(int age) {
		this.age = age;
	}

	@Override
	public String toString() {
		return "SVO4Debugging [name=" + name + ", age=" + age + "]";
	}
	
	
}
