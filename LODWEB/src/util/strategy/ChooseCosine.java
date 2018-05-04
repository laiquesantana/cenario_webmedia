package util.strategy;

import java.util.ArrayList;
import java.util.List;
import cosinesimilarity.LuceneCosineSimilarity;
import database.DBFunctions;
import model.Cenario;
import model.SemanticRaking;
import tagging.TaggingFactory;

public class ChooseCosine implements Similarity {

	@Override
	public void choiceOfSimilarity(List<Cenario> cenarios, Cenario cenario, int userId, int limitTag) {
		double cosineSimilarity;
		DBFunctions dbfunctions = new DBFunctions();
		String[] arrayUserModel = cenario.getTags_user().split(",");

		List<SemanticRaking> semanticRaking = new ArrayList<SemanticRaking>();

		for (Cenario c : cenarios) {

			String[] arrayUserTestModel = c.getTags_testset().split(",");

			cosineSimilarity = LuceneCosineSimilarity.getCosineSimilarity(TaggingFactory.retornaString(arrayUserModel),
					TaggingFactory.retornaString(arrayUserTestModel));

			System.out.println("\n UserModel : " + TaggingFactory.retornaString(arrayUserModel) + "\n TestModel: "
					+ TaggingFactory.retornaString(arrayUserTestModel) + "\n Tiveram similaridade: "
					+ cosineSimilarity);

			if (cosineSimilarity > 0.0) {
				SemanticRaking semanticRaking1 = new SemanticRaking(1, c.getId_filme(), "COSINE", cosineSimilarity, userId); 
				semanticRaking.add(semanticRaking1);
			}

		}

		for (SemanticRaking semanticRaking2 : semanticRaking) {
			if (semanticRaking2.getScore() != 0.0  || semanticRaking2.getScore() > 1.0)  {
				
				dbfunctions.insertOrUpdateSemanticRaking(1, semanticRaking2.getUri2(), semanticRaking2.getType(), semanticRaking2.getScore(), userId);
				
			}

		}
	}
}
