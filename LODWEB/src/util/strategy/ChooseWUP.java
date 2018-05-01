package util.strategy;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import database.DBFunctions;
import model.Tag;
import tagging.TaggingFactory;
import wordnet.WordNetFactory;

public class ChooseWUP implements Similarity {
	
	double similarityJaccard;
	double calculeWup;
	double union;
 	double intersection;
 	double ResultCalcule;

	public void choiceOfSimilarity(List<Integer> filmes, List<Integer> filmesNotRating, int userId, int limitTag) {
		DBFunctions dbFunctions = new DBFunctions();
		
		
		List<Integer> tagsFilmesAvaliados = dbFunctions.findTagOfDocumentsLimitTag(filmes, limitTag);
		List<Tag> nameOfTagsFilmsHasRating = dbFunctions.getNameOfTagsOfFilms(tagsFilmesAvaliados);
		
		for (int j = 0; j < filmesNotRating.size(); j++) {
			
			List<Integer> tagsOfFilmsNotRating = dbFunctions.findTagOfDocumentWithLimitTag(filmesNotRating.get(j), limitTag);
			List<Tag> nameOfTagsFilmsNotRating = dbFunctions.getNameOfTagsOfFilms(tagsOfFilmsNotRating);
	
			calculeWup = WordNetFactory.calculeWUP(nameOfTagsFilmsHasRating, nameOfTagsFilmsNotRating);
						
			if(calculeWup != calculeWup) calculeWup = 0;
			
			TaggingFactory.saveResultSimilarityOfUserModelWithTestSet("WUP", similarityJaccard, calculeWup, calculeWup, filmesNotRating.get(j), userId);							
				
		}
   }

}
