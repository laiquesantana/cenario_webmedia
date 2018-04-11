package util.strategy;

import java.util.List;

import cosinesimilarity.LuceneCosineSimilarity;
import database.DBFunctions;
import similarity.Jaccard;
import tagging.Tag;
import tagging.TaggingFactory;
import wordnet.WordNetFactory;

public class ChooseWUPCosine implements Similarity{
	double similarityJaccard;
	double calculeSemanticWup;
	double union;
 	double intersection;
 	double ResultCalcule;
	
 	
	@Override
    public void choiceOfSimilarity(List<Integer> filmes, List<Integer> filmesNotRating, int userId) {
		DBFunctions dbFunctions = new DBFunctions();
		
		List<Integer> tagsFilmesAvaliados = dbFunctions.findTagOfDocumentsLimitTag(filmes, userId, 5);
		List<Tag> nameOfTagsFilmsHasRating = dbFunctions.getNameOfTagsOfFilms(tagsFilmesAvaliados);
	
		for (int j = 0; j < filmesNotRating.size(); j++) {
			
			List<Integer> tagsOfFilmsNotRating = dbFunctions.findTagOfDocumentLimitTag(filmesNotRating.get(j), 5);
			List<Tag> nameOfTagsFilmsNotRating = dbFunctions.getNameOfTagsOfFilms(tagsOfFilmsNotRating);
	
			calculeSemanticWup = WordNetFactory.calculeTagSemanticWUP(nameOfTagsFilmsHasRating, nameOfTagsFilmsNotRating);
			
			if(calculeSemanticWup != calculeSemanticWup) calculeSemanticWup = 0;
			
			double cosineSimilarity = LuceneCosineSimilarity.getCosineSimilarity(TaggingFactory.concatenaTagText(nameOfTagsFilmsHasRating), TaggingFactory.concatenaTagText(nameOfTagsFilmsNotRating));
				 	
		 	ResultCalcule = (cosineSimilarity + calculeSemanticWup) / 2;
					
						
			TaggingFactory.saveResultSimilarityOfFimlUserAndFilmRecommended("WUP+COSINE", cosineSimilarity, calculeSemanticWup, ResultCalcule, filmesNotRating.get(j), userId);							
				
		}
   }
}

