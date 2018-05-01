package util.strategy;


import java.util.ArrayList;
import java.util.List;

import cosinesimilarity.LuceneCosineSimilarity;
import database.DBFunctions;
import model.Tag;
import tagging.TaggingFactory;

public class ChooseLDSD implements Similarity {
	double similarityJaccard;
	double calculeLDSD;
	double union;
 	double intersection;
 	double ResultCalcule;
	
	@Override 
	public void choiceOfSimilarity(List<Integer> userModel, List<Integer> testSet, int userId, int limitTag) {
		DBFunctions dbFunctions = new DBFunctions();
			
		List<Integer> tagsFilmesAvaliados = dbFunctions.findTagOfDocumentsLimitTag(userModel, limitTag);
		ArrayList<Tag> nameOfTagsFilmsHasRating = dbFunctions.getNameOfTagsOfFilms(tagsFilmesAvaliados); 
	
		for (int j = 0; j < testSet.size(); j++) {
			
			List<Integer> tagsOfFilmsNotRating = dbFunctions.findTagOfDocumentWithLimitTag(testSet.get(j), limitTag);
			ArrayList<Tag> nameOfTagsFilmsNotRating = dbFunctions.getNameOfTagsOfFilms(tagsOfFilmsNotRating);
	
			calculeLDSD = TaggingFactory.calculeLDSD(nameOfTagsFilmsHasRating, nameOfTagsFilmsNotRating, userId);
			
			if(calculeLDSD != calculeLDSD) calculeLDSD = 0;
					
			double cosineSimilarity = LuceneCosineSimilarity.getCosineSimilarity(TaggingFactory.concatenaTagText(nameOfTagsFilmsHasRating), TaggingFactory.concatenaTagText(nameOfTagsFilmsNotRating));
			 			
		 	TaggingFactory.saveResultSimilarityOfUserModelWithTestSet("LDSD", cosineSimilarity, calculeLDSD, calculeLDSD, testSet.get(j), userId);
		}
	}
}
