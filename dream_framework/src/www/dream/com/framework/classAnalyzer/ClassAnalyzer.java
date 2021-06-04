package www.dream.com.framework.classAnalyzer;

import java.lang.annotation.Annotation;
import java.lang.reflect.AccessibleObject;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class ClassAnalyzer {
	/**
	 * 주어진 Class에서 정의되어 있는 모든 속성과 함수(기능)에 지정된 Annotation을 붙혀놓은 것들을
	 * 상속 구조까지 분석하여 모아서 반환한다. 
	 * @param targetClass
	 * @param targetAnno
	 * @return
	 */

	public static List<AccessibleObject> findFeatureByAnnotation(Class targetClass, Class targetAnno) {
		//Return 하는 정보내용을 만들고, Catch 안에서 해결하려고 할필요 없고
		// 반환 정보 만들고, 재귀함수 만들고
		List<AccessibleObject> ret = new ArrayList<>();
		findFeatureByAnnotation(targetClass, targetAnno, ret);
		return ret;
	}

	public static void findFeatureByAnnotation(Class targetClass, Class targetAnno,  List<AccessibleObject> list) {
		// Private 함수를 만들어서 return 없이 위에서 만든 함수를 이 함수에 집어 넣어서 나오는 것들을 내가 받은곳에다가 계속 집어
		// 넣을것
		// 이렇게 해야 Recursion을 개발하기가 쉽다.

		try {
			Field[] fields = targetClass.getDeclaredFields();
			for (Field field : fields) {
				Annotation anno = field.getAnnotation(targetAnno);
				if (anno != null) {
					list.add(field);
				}
			}

			Method[] methods = targetClass.getDeclaredMethods();
			for (Method method : methods) {
				Annotation anno = method.getAnnotation(targetAnno);
				if (anno != null) {
					list.add(method);
				}
			}
		} catch (Exception e) {
		}
		
		Class targetSuper = targetClass.getSuperclass();
		if (targetSuper != Object.class) { // targetSuper.class가 Object Class가 아니면
			findFeatureByAnnotation(targetSuper, targetAnno, list);
		}

	}
}
