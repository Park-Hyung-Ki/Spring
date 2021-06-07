package www.dream.com.framework.printer;

import java.lang.reflect.AccessibleObject;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.List;
import java.util.TreeSet;

import www.dream.com.framework.classAnalyzer.ClassAnalyzer;
import www.dream.com.framework.util.ComparablePair;

// TableHeader를 출력할 고도의 프로그램을 만들것
public class TableHeader {
	public static String print(Class cls) {
		// HeaderTarget가 추가된 AccessibleObject를 모집
		TreeSet<ComparablePair<Integer, String>> ordered = new TreeSet<>();

		collectHeaders(cls, HeaderTarget.class, ordered);
		//collectHeaders를 통해서 다 모아본다.

		// 문자열 출력
		StringBuilder sb = new StringBuilder();
		for (ComparablePair<Integer, String> cp : ordered) {
			sb.append("<th>" + cp.getSecond() + "</th>");
		}
		return sb.toString();
	}

	private static void collectHeaders(Class cls, Class anno, TreeSet<ComparablePair<Integer, String>> ordered) {
		if (cls.getAnnotation(ClassHeaderTarget.class) == null)
			return; // 없으면 처리 안하고
		
		List<AccessibleObject> listFeature = ClassAnalyzer.findAllFeature(cls);
		for (AccessibleObject ao : listFeature) {
			HeaderTarget ht = (HeaderTarget) ao.getAnnotation(anno);
			if (ht != null) {
				ordered.add(new ComparablePair<>(ht.order(), ht.caption()));
				//@ 달려있는걸 읽어서 있으면 ordered에 넣고
			} else {
				if (ao instanceof Field) { // 없으면 Field인지, Method인지 구분하면서 Class를 뒤적뒤적
					collectHeaders(((Field) ao).getType(), anno, ordered);
				} else if (ao instanceof Method) {
					collectHeaders(((Method) ao).getReturnType(), anno, ordered);
				}
			}
		}
	}
}
