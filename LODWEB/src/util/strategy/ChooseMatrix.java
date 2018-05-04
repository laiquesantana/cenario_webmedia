package util.strategy;

import java.util.ArrayList;
import java.util.List;
import database.DBFunctions;
import model.Cenario;
import model.SemanticRaking;
import similarity.Jaccard;
import tagging.TaggingFactory;

public class ChooseMatrix implements Similarity {
	double similarityJaccard;
	double calculeSemanticLDSD;
	double union;
	double intersection;
	double resultCalculeLDSD;
	double resultCalculeWup;
	double calculeSemanticWup;

	@Override
	public void choiceOfSimilarity(List<Cenario> cenarios, Cenario cenario, int userId, int limitTag) {
		DBFunctions dbfunctions = new DBFunctions();
		List<SemanticRaking> semanticRaking = new ArrayList<SemanticRaking>();
		List<SemanticRaking> semanticRakingLdsdJaccard = new ArrayList<SemanticRaking>();
		List<SemanticRaking> semanticRakingWupJaccard = new ArrayList<SemanticRaking>();
		String[] arrayUserModel = cenario.getTags_user().split(",");

		for (Cenario c : cenarios) {
			
			String[] arrayUserTestModel = c.getTags_testset().split(",");

			similarityJaccard = Jaccard.similarityJaccard(TaggingFactory.loadTagArray(arrayUserModel),TaggingFactory.loadTagArray(arrayUserTestModel));

			/* Calcula Similaridade Jaccard */
			if (similarityJaccard > 0.0) {
				SemanticRaking semanticRakingJaccard = new SemanticRaking(1, c.getId_filme(), "JACCARD", similarityJaccard,
						userId);
				semanticRaking.add(semanticRakingJaccard);
			}

			union = Jaccard.union(TaggingFactory.loadTagArray(arrayUserModel), TaggingFactory.loadTagArray(arrayUserTestModel));

			intersection = Jaccard.intersection(TaggingFactory.loadTagArray(arrayUserModel), TaggingFactory.loadTagArray(arrayUserTestModel));

				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			/* Calcula Similaridade LDSD + JACCARD */
			calculeSemanticLDSD = dbfunctions.findSemantic("LDSD", userId, c.getId_filme());
			
			
			
			
			
			
			
			
			

			resultCalculeLDSD = TaggingFactory.calculeSimilarityAndJaccard(union, intersection, calculeSemanticLDSD);
	
			if (resultCalculeLDSD > 0.0) {
				SemanticRaking semanticRakingLdsdJaccard1 = new SemanticRaking(1, c.getId_filme(), "LDSD+JACCARD", resultCalculeLDSD, userId);
				semanticRakingLdsdJaccard.add(semanticRakingLdsdJaccard1);

			}
		
			/*  Calcula Similaridade WUP + JACCARD */
			calculeSemanticWup = dbfunctions.findSemantic("WUP", userId, c.getId_filme());
						
			resultCalculeWup = TaggingFactory.calculeSimilarityAndJaccard(union, intersection, calculeSemanticWup);

			if (resultCalculeWup > 0.0) {
				SemanticRaking semanticWup = new SemanticRaking(1, c.getId_filme(), "WUP+JACCARD", resultCalculeWup, userId);
				semanticRakingWupJaccard.add(semanticWup);

			}
		}

		/* Salva Similaridade JAccard */
		for (SemanticRaking semanticJaccard : semanticRaking) {

			if (semanticJaccard.getScore() != 0.0  || semanticJaccard.getScore() > 1.0)  {
				
				dbfunctions.insertOrUpdateSemanticRaking(1, semanticJaccard.getUri2(), semanticJaccard.getType(), semanticJaccard.getScore(), userId);
				
			}

		} 

		/* Salva Similaridade Jaccard + LDSD */
		for (SemanticRaking semanticLdsd : semanticRakingLdsdJaccard) {
			
			if (semanticLdsd.getScore() != 0.0  || semanticLdsd.getScore() > 1.0)  {
			
				dbfunctions.insertOrUpdateSemanticRaking(1, semanticLdsd.getUri2(), semanticLdsd.getType(), semanticLdsd.getScore(), userId);
				
			}
		}

		/* Salva Similaridade Jaccard + WUP */
		for (SemanticRaking semanticWup : semanticRakingWupJaccard) {
			if (semanticWup.getScore() != 0.0  || semanticWup.getScore() > 1.0)  {
				
				dbfunctions.insertOrUpdateSemanticRaking(1, semanticWup.getUri2(), semanticWup.getType(), semanticWup.getScore(), userId);
				
			}
		}
		System.out.println("SAlVANDO .......");
	} 
}
