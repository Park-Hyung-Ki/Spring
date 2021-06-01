package www.dream.com.framework.test;

import java.util.List;

import org.junit.Test;

import kr.co.shineware.nlp.komoran.constant.DEFAULT_MODEL;
import kr.co.shineware.nlp.komoran.core.Komoran;
import kr.co.shineware.nlp.komoran.model.KomoranResult;
import kr.co.shineware.nlp.komoran.model.Token;

public class testKomoran {
	private enum TargetPos {NNG, NNP, SL, SH};

	@Test
	public void test() {
		Komoran komoran = new Komoran(DEFAULT_MODEL.FULL);
		String strToAnalyze = "혼자 온 세상 은인은 너야, 다시 태어난다고 해도 은인은 너야";

		KomoranResult analyzeResultList = komoran.analyze(strToAnalyze);

		System.out.println(analyzeResultList.getPlainText());

		List<Token> tokenList = analyzeResultList.getTokenList();
		for (Token token : tokenList) {
			TargetPos pos = null;
			try {
				pos = TargetPos.valueOf(token.getPos()); 
			} catch (Exception e) {
				
			}
			if (pos != null) {
			System.out.format("%s/%s\n", token.getMorph(),token.getPos());
			}
		}
	}
}
