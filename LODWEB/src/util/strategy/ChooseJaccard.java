package util.strategy;

import java.util.List;
import database.DBFunctions;
import model.Tag;
import similarity.Jaccard;
import tagging.TaggingFactory;

public class ChooseJaccard implements Similarity {
	double similarityJaccard;

	@Override
	public void choiceOfSimilarity(List<Integer> filmes, List<Integer> filmesNotRating, int userId) {
		DBFunctions dbFunctions = new DBFunctions();

		List<Integer> tagsFilmesAvaliados = dbFunctions.findTagOfDocuments(filmes, userId);
		List<Tag> nameOfTagsFilmsHasRating = dbFunctions.getNameOfTagsOfFilms(tagsFilmesAvaliados); // List

		for (int j = 0; j < filmesNotRating.size(); j++) {

			List<Integer> tagsOfFilmsNotRating = dbFunctions.findTagOfDocument(filmesNotRating.get(j));
			List<Tag> nameOfTagsFilmsNotRating = dbFunctions.getNameOfTagsOfFilms(tagsOfFilmsNotRating);

			similarityJaccard = Jaccard.similarityJaccard(nameOfTagsFilmsHasRating, nameOfTagsFilmsNotRating);
			
			TaggingFactory.saveResultSimilarityOfUserModelWithTestSet("JACCARD", similarityJaccard, 0, similarityJaccard, filmesNotRating.get(j), userId);	

		}
	}
}
