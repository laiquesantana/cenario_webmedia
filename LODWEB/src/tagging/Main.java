package tagging;

import java.util.ArrayList;
import cosinesimilarity.LuceneCosineSimilarity;
import database.DBFunctions;
import similarity.Jaccard;
import wordnet.WordNetFactory;

public class Main {

	public static void main(String[] args) {
	
		DBFunctions dbFunctions = new DBFunctions();
		
		dbFunctions.insertOrUpdateDocument("Documento 1");
				
		String textoA = "Automobile Car Bike Air Skate";
		
		String textoB = "Car Bike Air Car Bike";

		/* Variaveis utilizadas para inserir documento em um array para pode comparar */
		String[] tag1Array = TaggingFactory.inputDados(textoA);
		String[] tag2Array = TaggingFactory.inputDados(textoB);

		/* Lista utilizada para Calcular quantidade de itens iguais */
//		ArrayList<Tag> listTag1 = TaggingFactory.loadTagArray(tag1Array);
		
//		ArrayList<Tag> listTag2 = TaggingFactory.loadTagArray(tag2Array);

//		double cosineSimilarity1 = LuceneCosineSimilarity.getCosineSimilarity(TaggingFactory.concatenaTagText(listTag1), TaggingFactory.concatenaTagText(listTag2));

//		double todosItensDasListas = TaggingFactory.countAllItem(listTag1) + TaggingFactory.countAllItem(listTag1);

//		double calculeSemanticLDSD = TaggingFactory.calculeTagSemanticLDSD(listTag1, listTag2);

//  	double similarityJaccard = Jaccard.similarityJaccard(listTag1, listTag2);

//		double calculeSimilaritySemanticDSD = TaggingFactory.calculeSimilaritySemantic(calculeSemanticLDSD, Jaccard.union(listTag1, listTag2));
		
		WordNetFactory.printItemWordNet(tag1Array, tag2Array);

//		TaggingFactory.matrix(tag1Array, tag2Array);

		WordNetFactory.matrix(tag1Array, tag2Array);
		
//		System.out.println("TOTAL ITENS DA LETRA A -> " + TaggingFactory.countAllItem(listTag1));
//		System.out.println("TOTAL ITENS DA LETRA B -> " + TaggingFactory.countAllItem(listTag1));
//		System.out.println("SOMA ITENS DAS LETRA A e LETRA B -> " + todosItensDasListas);
		System.out.println("\n ========================= *** COSINE SIMILARITY *** ===================================");
//		System.out.println("                 Valor Cosine Similarity A e B: " + cosineSimilarity1);
//		System.out.println("                   totalSimilaritySemanticLDSD: " + calculeSimilaritySemanticDSD);
		System.out.println("====================================================================== \n");
//		System.out.println("               TOTAL SOMA JACCARD E  SEMANTICA: "
//				+ (similarityJaccard + calculeSimilaritySemanticDSD));

		System.out.println("\n ====================== MATRIZES ==================== \n");

	}
}