package util.strategy;

import java.util.ArrayList;
import java.util.List;
import cosinesimilarity.LuceneCosineSimilarity;
import database.DBFunctions;
import model.Tag;
import tagging.TaggingFactory;


public class ChooseCosine implements Similarity {
	
	@Override
	public void choiceOfSimilarity(List<Integer> filmes, List<Integer> filmesNotRating, int userId) {
		DBFunctions dbFunctions = new DBFunctions();
		double cosineSimilarity;
			
		List<Integer> tagsFilmesAvaliados = dbFunctions.findTagOfDocuments(filmes, userId);
		ArrayList<Tag> nameOfTagsFilmsHasRating = dbFunctions.getNameOfTagsOfFilms(tagsFilmesAvaliados); // List

		for (int j = 0; j < filmesNotRating.size(); j++) {
			List<Integer> tagsOfFilmsNotRating = new ArrayList<Integer>();

			tagsOfFilmsNotRating = dbFunctions.findTagOfDocument(filmesNotRating.get(j));
			ArrayList<Tag> nameOfTagsFilmsNotRating = dbFunctions.getNameOfTagsOfFilms(tagsOfFilmsNotRating);

			cosineSimilarity = LuceneCosineSimilarity.getCosineSimilarity(TaggingFactory.concatenaTagText(nameOfTagsFilmsHasRating), TaggingFactory.concatenaTagText(nameOfTagsFilmsNotRating));
			
			TaggingFactory.saveResultSimilarityOfUserModelWithTestSet("COSINE", cosineSimilarity, 0, cosineSimilarity, filmesNotRating.get(j), userId);		
		}
	}
}
