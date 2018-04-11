package util.strategy;

import java.util.List;

public interface Similarity {

	public void choiceOfSimilarity(List<Integer> filmes, List<Integer> filmesNotRating, int userId);
	
}
