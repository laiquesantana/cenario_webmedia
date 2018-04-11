package util.strategy;


import java.util.ArrayList;
import java.util.List;

import cosinesimilarity.LuceneCosineSimilarity;
import database.DBFunctions;
import tagging.Tag;
import tagging.TaggingFactory;

public class ChooseLDSDCosine implements Similarity {
	double similarityJaccard;
	double calculeSemanticLDSD;
	double union;
 	double intersection;
 	double ResultCalcule;

	@Override 
	public void choiceOfSimilarity(List<Integer> filmes, List<Integer> filmesNotRating, int userId) {
		DBFunctions dbFunctions = new DBFunctions();
			
		List<Integer> tagsFilmesAvaliados = dbFunctions.findTagOfDocumentsLimitTag(filmes, userId, 5);
		ArrayList<Tag> nameOfTagsFilmsHasRating = dbFunctions.getNameOfTagsOfFilms(tagsFilmesAvaliados); 
	
		for (int j = 0; j < filmesNotRating.size(); j++) {
			
			List<Integer> tagsOfFilmsNotRating = dbFunctions.findTagOfDocumentLimitTag(filmesNotRating.get(j), 5);
			ArrayList<Tag> nameOfTagsFilmsNotRating = dbFunctions.getNameOfTagsOfFilms(tagsOfFilmsNotRating);
	
			calculeSemanticLDSD = TaggingFactory.calculeTagSemanticLDSD(nameOfTagsFilmsHasRating, nameOfTagsFilmsNotRating);
			
			if(calculeSemanticLDSD != calculeSemanticLDSD) calculeSemanticLDSD = 0;
					
			double cosineSimilarity = LuceneCosineSimilarity.getCosineSimilarity(TaggingFactory.concatenaTagText(nameOfTagsFilmsHasRating), TaggingFactory.concatenaTagText(nameOfTagsFilmsNotRating));
				 	
		 	ResultCalcule = (cosineSimilarity + calculeSemanticLDSD) / 2;
					
		 	TaggingFactory.saveResultSimilarityOfFimlUserAndFilmRecommended("LDSD+COSINE", cosineSimilarity, calculeSemanticLDSD, ResultCalcule, filmesNotRating.get(j), userId);
		}
	}
}
