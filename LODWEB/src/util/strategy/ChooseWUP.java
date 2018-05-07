package util.strategy;

import java.util.ArrayList;
import java.util.List;

import database.DBFunctions;
import model.Cenario;
import model.SemanticRaking;
import tagging.TaggingFactory;
import wordnet.WordNetFactory;

public class ChooseWUP implements Similarity {

	DBFunctions dbfunctions = new DBFunctions();
	double similarityJaccard;
	double[] calculeWup;
	double union;
	double intersection;
	double ResultCalcule;

	public void choiceOfSimilarity(List<Cenario> cenarios, Cenario cenario, int userId, int limitTag) {

		String[] arrayUserModel = cenario.getTags_user().split(",");

		List<SemanticRaking> semanticRaking = new ArrayList<SemanticRaking>();

		for (Cenario c : cenarios) {

			String[] arrayUserTestModel = c.getTags_testset().split(",");

			calculeWup = WordNetFactory.calculeWUP(TaggingFactory.loadTagArray(arrayUserModel),
					TaggingFactory.loadTagArray(arrayUserTestModel));

			if (calculeWup[1] > 0.0) {
				SemanticRaking semanticWup = new SemanticRaking(1, c.getId_filme(), "WUP", calculeWup[1], calculeWup[0],userId);
				semanticRaking.add(semanticWup);
			}
		}

		for (SemanticRaking semanticRaking2 : semanticRaking) {
			if (semanticRaking2.getScore() != 0.0 || semanticRaking2.getScore() < 1.0) {
				dbfunctions.insertOrUpdateSemanticRaking(1, semanticRaking2.getUri2(), semanticRaking2.getType(),
						semanticRaking2.getScore(), semanticRaking2.getSumsemantic(), userId);
			}
		}
	}
}
