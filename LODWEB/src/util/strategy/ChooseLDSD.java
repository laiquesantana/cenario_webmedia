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
		List<SemanticRaking> listSemanticRakingLDSD = new ArrayList<SemanticRaking>();

		for (Cenario c : cenarios) {
			String[] arrayUserTestModel = c.getTags_testset().split(",");

			calculeLDSD = TaggingFactory.calculeLDSD(TaggingFactory.loadTagArray(arrayUserModel), TaggingFactory.loadTagArray(arrayUserTestModel), userId, cenario.getId_filme());
		
			if (calculeLDSD > 0.0) {
				SemanticRaking semanticRakingLDSD = new SemanticRaking(1, c.getId_filme(), "LDSD", calculeLDSD, userId); 
				listSemanticRakingLDSD.add(semanticRakingLDSD);
			}
		}
	
		for (SemanticRaking semantic : listSemanticRakingLDSD) {

			if (semantic.getScore() != 0.0 || semantic.getScore() > 1.0) {

				dbfunctions.insertOrUpdateSemanticRaking(1, semantic.getUri2(), semantic.getType(),
						semantic.getScore(), userId);

			}
		}
	}
}
