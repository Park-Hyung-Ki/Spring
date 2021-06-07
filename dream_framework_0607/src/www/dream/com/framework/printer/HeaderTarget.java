package www.dream.com.framework.printer;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

@Retention(RUNTIME)
@Target({FIELD, METHOD })
public @interface HeaderTarget {
	// 비교 기능이 있으니 정렬 사용가능
		//출력 순서를 정의
		int order() default 0;
		
		//Caption
		String caption() default "";
	}

