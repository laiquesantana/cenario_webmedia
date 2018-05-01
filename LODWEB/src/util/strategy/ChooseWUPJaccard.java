package util.strategy;

import java.util.List;
import database.DBFunctions;
import model.Tag;
import similarity.Jaccard;
import tagging.TaggingFactory;
import wordnet.WordNetFactory;

public class ChooseWUPJaccard implements Similarity{
	double similarityJaccard;
	double calculeSemanticWup;
	double union;
 	double intersection;
 	double ResultCalcule;

	@Override
    public void choiceOfSimilarity(List<Integer> filmes, List<Integer> filmesNotRating, int userId, int limitTag) {
		DBFunctions dbFunctions = new DBFunctions();
		
		List<Integer> tagsFilmesAvaliados = dbFunctions.findTagOfDocumentsLimitTag(filmes, limitTag);
		List<Tag> nameOfTagsFilmsHasRating = dbFunctions.getNameOfTagsOfFilms(tagsFilmesAvaliados);
		
		for (int j = 0; j < filmesNotRating.size(); j++) {
			
			List<Integer> tagsOfFilmsNotRating = dbFunctions.findTagOfDocumentWithLimitTag(filmesNotRating.get(j), limitTag);
			List<Tag> nameOfTagsFilmsNotRating = dbFunctions.getNameOfTagsOfFilms(tagsOfFilmsNotRating);
	
			calculeSemanticWup = WordNetFactory.calculeTagSemanticWUP(nameOfTagsFilmsHasRating, nameOfTagsFilmsNotRating);
			
			similarityJaccard = Jaccard.similarityJaccard(nameOfTagsFilmsHasRating, nameOfTagsFilmsNotRating);
			
			union = Jaccard.union(nameOfTagsFilmsHasRating, nameOfTagsFilmsNotRating);
		
			intersection = Jaccard.intersection(nameOfTagsFilmsHasRating, nameOfTagsFilmsNotRating);
			 	
			if(calculeSemanticWup != calculeSemanticWup) calculeSemanticWup = 0;
			
			ResultCalcule = TaggingFactory.calculeSimilarityAndJaccard(union, intersection, calculeSemanticWup);
						
			TaggingFactory.saveResultSimilarityOfUserModelWithTestSet("WUP+JACCARD", similarityJaccard, calculeSemanticWup, ResultCalcule, filmesNotRating.get(j), userId);							
				
		}
   }
}

