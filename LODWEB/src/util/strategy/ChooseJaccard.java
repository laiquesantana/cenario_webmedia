package util.strategy;

import java.util.List;
import database.DBFunctions;
import model.Tag;
import similarity.Jaccard;
import tagging.TaggingFactory;

public class ChooseJaccard implements Similarity {
	double similarityJaccard;

	@Override
	public void choiceOfSimilarity(List<Integer> userModel, List<Integer> testSet, int userId) {
		DBFunctions dbFunctions = new DBFunctions();

		List<Integer> listTagsUserModel = DBFunctions.findTagOfDocuments(userModel);
		List<Tag> listNameOfTagsUserModel = dbFunctions.getNameOfTagsOfFilms(listTagsUserModel); // List

		for (int j = 0; j < testSet.size(); j++) {

			List<Integer> listTagsTestSet = dbFunctions.findTagOfDocument(testSet.get(j));
			List<Tag> listNameOfTagsTestSet = dbFunctions.getNameOfTagsOfFilms(listTagsTestSet);

			similarityJaccard = Jaccard.similarityJaccard(listNameOfTagsUserModel, listNameOfTagsTestSet);
			
			TaggingFactory.saveResultSimilarityOfUserModelWithTestSet("JACCARD", similarityJaccard, 0, similarityJaccard, testSet.get(j), userId);	

		}
	}
}
