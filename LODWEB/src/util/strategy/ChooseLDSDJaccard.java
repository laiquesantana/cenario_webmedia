package util.strategy;


import java.util.ArrayList;
import java.util.List;

import database.DBFunctions;
import model.Tag;
import similarity.Jaccard;
import tagging.TaggingFactory;

public class ChooseLDSDJaccard implements Similarity {
	double similarityJaccard;
	double calculeSemanticLDSD;
	double union;
 	double intersection;
 	double ResultCalcule;

 	@Override 
	public void choiceOfSimilarity(List<Integer> userModel, List<Integer> testSet, int userId) {
		DBFunctions dbFunctions = new DBFunctions();
	
		List<Integer> tagsUserModel = dbFunctions.findTagOfDocumentsLimitTag(userModel, 5);
		ArrayList<Tag> nameOfTagsUserModel = dbFunctions.getNameOfTagsOfFilms(tagsUserModel); 
	
		for (int j = 0; j < testSet.size(); j++) {
	
			List<Integer> tagsTestSet = dbFunctions.findTagOfDocumentWithLimitTag(testSet.get(j), 5);
			ArrayList<Tag> nameOfTagsTestSet = dbFunctions.getNameOfTagsOfFilms(tagsTestSet);

			union = Jaccard.union(nameOfTagsUserModel, nameOfTagsTestSet);
			
			intersection = Jaccard.intersection(nameOfTagsUserModel, nameOfTagsTestSet);
			
			similarityJaccard = Jaccard.similarityJaccard(nameOfTagsUserModel, nameOfTagsTestSet);
			
			calculeSemanticLDSD = TaggingFactory.calculeTagSemanticLDSD(nameOfTagsUserModel, nameOfTagsTestSet, userId);
					
			ResultCalcule = TaggingFactory.calculeSimilarityAndJaccard(union, intersection, calculeSemanticLDSD);
					
	    	TaggingFactory.saveResultSimilarityOfUserModelWithTestSet("LDSD+JACCARD", similarityJaccard, calculeSemanticLDSD, ResultCalcule, testSet.get(j), userId);
		}
	}

}
