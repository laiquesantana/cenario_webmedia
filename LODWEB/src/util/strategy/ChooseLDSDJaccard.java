package util.strategy;


import java.util.ArrayList;
import java.util.List;


import database.DBFunctions;
import similarity.Jaccard;
import tagging.Tag;
import tagging.TaggingFactory;

public class ChooseLDSDJaccard implements Similarity {
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
			
			try { Thread.sleep (400); } catch (InterruptedException ex) {}
			
			List<Integer> tagsOfFilmsNotRating = dbFunctions.findTagOfDocumentLimitTag(filmesNotRating.get(j), 5);
			ArrayList<Tag> nameOfTagsFilmsNotRating = dbFunctions.getNameOfTagsOfFilms(tagsOfFilmsNotRating);

			similarityJaccard = Jaccard.similarityJaccard(nameOfTagsFilmsHasRating, nameOfTagsFilmsNotRating);
			
			calculeSemanticLDSD = TaggingFactory.calculeTagSemanticLDSD(nameOfTagsFilmsHasRating, nameOfTagsFilmsNotRating);
			
			
			union = Jaccard.union(nameOfTagsFilmsHasRating, nameOfTagsFilmsNotRating);
		 	intersection = Jaccard.intersection(nameOfTagsFilmsHasRating, nameOfTagsFilmsNotRating);
		 	
		 	if(calculeSemanticLDSD != calculeSemanticLDSD) calculeSemanticLDSD = 0;
		 	
		 	ResultCalcule = TaggingFactory.calculeSumSimilarityAndJaccard(union, intersection, calculeSemanticLDSD);
					
		 	TaggingFactory.saveResultSimilarityOfFimlUserAndFilmRecommended("LDSD+JACCARD", similarityJaccard, calculeSemanticLDSD, ResultCalcule, filmesNotRating.get(j), userId);
		}
	}
}
