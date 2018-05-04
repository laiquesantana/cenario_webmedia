package util.strategy;

import java.util.ArrayList;
import java.util.List;

import database.DBFunctions;
import model.Cenario;
import model.SemanticRaking;
import tagging.TaggingFactory;

public class ChooseLDSD implements Similarity {
	double similarityJaccard;
	double calculeLDSD;
	double union;
	double intersection;
	double ResultCalcule;

	@Override
	public void choiceOfSimilarity(List<Cenario> cenarios, Cenario cenario, int userId, int limitTag) {

		DBFunctions dbfunctions = new DBFunctions();
		String[] arrayUserModel = cenario.getTags_user().split(",");
		List<SemanticRaking> semanticRaking = new ArrayList<SemanticRaking>();

		for (Cenario c : cenarios) {
			String[] arrayUserTestModel = c.getTags_testset().split(",");

			calculeLDSD = TaggingFactory.calculeLDSD(TaggingFactory.loadTagArray(arrayUserModel),
					TaggingFactory.loadTagArray(arrayUserTestModel), userId);

			if (calculeLDSD > 0.0) {
				SemanticRaking semanticRaking1 = new SemanticRaking(1, c.getId_filme(), "LDSD", calculeLDSD, userId);
				semanticRaking.add(semanticRaking1);
			}

		}

	
		for (SemanticRaking semanticRaking2 : semanticRaking) {

			if (semanticRaking2.getScore() != 0.0 || semanticRaking2.getScore() > 1.0) {

				dbfunctions.insertOrUpdateSemanticRaking(1, semanticRaking2.getUri2(), semanticRaking2.getType(),
						semanticRaking2.getScore(), userId);

			}

		}
		System.out.println("SAlVANDO .......");
	}
}
