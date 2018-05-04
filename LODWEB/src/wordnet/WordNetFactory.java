package wordnet;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import database.DBFunctions;
import edu.cmu.lti.lexical_db.ILexicalDatabase;
import edu.cmu.lti.lexical_db.NictWordNet;
import edu.cmu.lti.ws4j.RelatednessCalculator;
import edu.cmu.lti.ws4j.impl.WuPalmer;
import edu.cmu.lti.ws4j.util.WS4JConfiguration;
import model.Tag;
import tagging.TaggingFactory;

public class WordNetFactory {
	private static ILexicalDatabase db = new NictWordNet();

	public static double[][] getSimilarityMatrix(String[] words1, String[] words2, RelatednessCalculator rc) {
		double[][] result = new double[words1.length][words2.length];
		for (int i = 0; i < words1.length; i++) {
			for (int j = 0; j < words2.length; j++) {
				double score = rc.calcRelatednessOfWords(words1[i], words2[j]);
				result[i][j] = score;
			}
		}
		return result;
	}

	public static void matrix(String[] words1, String[] words2) {

		System.out.println("\n WuPalmer \n");
		RelatednessCalculator rc1 = new WuPalmer(db);
		{
			double[][] s1 = getSimilarityMatrix(words1, words2, rc1);
			for (int i = 0; i < words1.length; i++) {
				for (int j = 0; j < words2.length; j++) {
					System.out.print(s1[i][j] + "\t");
				}
				System.out.println();
			}
		}
	}

	private static double valueDistance(String word1, String word2) {
		WS4JConfiguration.getInstance().setMFS(true);
		double s = new WuPalmer(db).calcRelatednessOfWords(word1, word2);
		return s;
	}

	public static void printItemWordNet(String[] words1, String[] words2) {
		DBFunctions dbFunctions = new DBFunctions();
		System.out.println("\n ====================== WORDNET - WUP ==================== \n");
		for (int i = 0; i < words1.length; i++) {
			for (int j = 0; j < words2.length; j++) {
				double distance = valueDistance(words1[i], words2[j]);
				System.out.println("WUP: " + words1[i] + " -  " + words2[j] + " = " + distance);
				
				Tag tag1 = dbFunctions.findTag(words1[i]);
				Tag tag2 = dbFunctions.findTag(words2[j]);
							
				if (distance < 1 && tag1.getId() !=  tag2.getId() && dbFunctions.isTagSimResult(tag1, tag2, "WUP") !=0) {
					dbFunctions.insertOrUpdateTagSim(tag1.getId(), tag2.getId(), distance, "WUP");
				}
			}
		}
	}
				
	public static double calculeWUP(List<Tag> words1, List<Tag> words2) {
		DBFunctions dbFunctions = new DBFunctions();
		Map<String, Double> mapResultLDSDweighted = new TreeMap<String, Double>();
		double distance;
		double resultSemantic = 0;
			
		System.out.println("\n ====================== WORDNET - WUP ==================== \n");

		for (Tag word1 : words1) {
			for (Tag word2 : words2) {
		
				try { Thread.sleep (200); } catch (InterruptedException ex) {}
				
				Tag tag1 = dbFunctions.findTag(word1.getName());
				Tag tag2 = dbFunctions.findTag(word2.getName());
					
				distance = valueDistance(tag1.getName(), tag2.getName());
				
				System.out.println("WUP: " + word1.getName() + " -  " + word2.getName() + " = " + distance);

				if (distance != 1.0 && distance != 0.0 && distance < 1 && tag1.getId() != tag2.getId() || dbFunctions.isTagSimResult(tag1, tag2, "WUP") != 0) {
					mapResultLDSDweighted.put(tag1.getName() + tag2.getName(), (distance));
					System.out.println("-------------------------------------------------");
					System.out.println("VALOR DA SIMILARIDADE -> " + distance);
					System.out.println("ITEM 1 -> " + tag1.getId() + " ITEM 2 -> " + tag2.getId() );
					System.out.println("-------------------------------------------------");
					dbFunctions.insertOrUpdateTagSim(tag1.getId(), tag2.getId(), distance, "WUP");
				}
			}
		}
				
		System.out.println("\n ====================== ARRAYLIST DE ELEMENTOS QUE SERÃO SOMADOS ==================== ");
		System.out.println(mapResultLDSDweighted + " \n");
		System.out.println("\n ================================== RESULTADOS ====================================== ");
		
		// Resultado da soma de todos as tags que existe similardade dividida pela quantidade de itens da lista
		resultSemantic = TaggingFactory.sumSemantic(mapResultLDSDweighted);
			
		System.out.println(" SOMA Similarity SEMÂNTICA -> " + TaggingFactory.sumSemantic(mapResultLDSDweighted) + " | DIVISÃO PELO VALOR DA UNIÃO -> " + resultSemantic);
		System.out.println(" RESULTADO DA SOMA DA WUP -> " + TaggingFactory.sumSemantic(mapResultLDSDweighted));
		System.out.println(" QUANTIDADE DE COMPARAÇÃO -> " + mapResultLDSDweighted.size());
		System.out.println(" RESULTADO DA SOMA DIVIDIDA PELA QUANTIDADE DE COMPARAÇÃO -> " + TaggingFactory.sumSemantic(mapResultLDSDweighted));
		
			
		return resultSemantic / mapResultLDSDweighted.size();
	}

	
	
	
	
	
}
