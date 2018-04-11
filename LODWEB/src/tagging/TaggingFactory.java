package tagging;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import database.DBFunctions;
import metric.PrecisionAndRecall;
import similarity.LDSD;
import util.StringUtilsNode;
import util.strategy.ChooseCosine;
import util.strategy.ChooseJaccard;
import util.strategy.ChooseLDSDCosine;
import util.strategy.ChooseLDSDJaccard;
import util.strategy.ChooseWUPJaccard;
import util.strategy.Similarity;

public class TaggingFactory {
	
	public static List<Tag> compareTag(List<Tag> listTag1, List<Tag> listTag2) {
		ArrayList<Tag> listResult = new ArrayList<Tag>();

		for (Tag item1 : listTag1) {
			item1.getName();
			for (Tag item2 : listTag2) {
				item2.getName();
				
				if (item1.getName().equals(item2.getName())) {
					listResult.add(item1);
				}
			}
		}

		return listResult;
	}

	public static List<Tag> loadTagArray(String[] tagArray) {
		List<Tag> listTag = new ArrayList<Tag>();
		DBFunctions dbFunctions = new DBFunctions();
		
		for (int i = 0; i < tagArray.length; i++) {
			// System.out.println("TextB " + (i+1) + " = " + tagArray[i] );
			listTag.add(new Tag(tagArray[i]));
			dbFunctions.insertOrUpdateTag(tagArray[i]);
		}
		
		return listTag;
	}
	
	public static String loadNameTagArray(List<Integer> filmes) {
		String text = null;
		
		for(int filme : filmes) {
			text = text + DBFunctions.getNameOfTag(filme);
		}
		
		return text;
	}


	public static String[] inputDados(String text) {
		return text.split(" ");
	}

	public static String concatenaTagText(List<Tag> listTag) {
		String valueTag = "";
		for (Tag tag : listTag) {
			valueTag = valueTag + " " + tag.getName();
		}

		System.out.println(" LISTA -> " + valueTag);

		return valueTag;
	}
	
	public static String[] convertTypeTagByArray(List<Tag> nameOfTagsFilms) {
		String [] tags = new String[nameOfTagsFilms.size()];
		int cont = 0;
		
		for (Tag tag : nameOfTagsFilms) {
			tags[cont] = tag.getName();
			cont += 1;
			System.out.println("Posição -> " + cont + " Tag -> " + tag.getName());
		}

		return tags;
	}
	
	public static int countAllItem(List<Tag> list) {

		int cont = 0;
		for (Tag tag : list) {
				cont++;
		}

		return cont;
	}

	public static double calculeTagSemanticLDSD(List<Tag> listTag1, List<Tag> listTag2) {
		Map<String, Double> mapResultLDSDweighted = new TreeMap<String, Double>();
		double ldsdWeighted = 0;
		double resultLDSDweighted = 0;
		double resultSumSemantic = 0;
		DBFunctions dbFunctions = new DBFunctions();

		for (Tag item1 : listTag1) {
			for (Tag item2 : listTag2) {
				
				try { Thread.sleep (300); } catch (InterruptedException ex) {}
				
				
				Tag tag1 = dbFunctions.findTag(item1.getName());
				Tag tag2 = dbFunctions.findTag(item2.getName());
				
				String nameTag1 = StringUtilsNode.configureNameTagWithOutCharacterWithUnderLine(tag1.getName());
				String nameTag2 = StringUtilsNode.configureNameTagWithOutCharacterWithUnderLine(tag2.getName());
				
				if (dbFunctions.isTagSimResult(tag1, tag2, "LDSD") !=0) {
					System.out.println("VALOR PARA A CONDIÇÃO DO IF -> " + dbFunctions.isTagSimResult(tag1, tag2, "LDSD"));
					System.out.println("VALOR INSERIDO DO BANCO " + resultLDSDweighted);
					resultLDSDweighted = dbFunctions.isTagSimResult(tag1, tag2, "LDSD");
				} else {
											
					System.out.println("TAG 1 SEM CARACTERES E LETRA MAIUSCULA-> " + nameTag1);
					System.out.println("TAG 2 SEM CARACTERES E LETRA MAIUSCULA-> " + nameTag2);
								
					
					try {
						ldsdWeighted = LDSD.LDSDweighted("http://dbpedia.org/resource/" + nameTag1, "http://dbpedia.org/resource/" + nameTag2); }
						catch (Exception ex) {
							System.out.println(" Errro!!!");
						}
					}
	
				System.out.println("LDSD:  " + nameTag1 + " -  " + nameTag2 + " = " + resultLDSDweighted);

				if (resultLDSDweighted != 1.0 && resultLDSDweighted != 0.0 || dbFunctions.isTagSimResult(tag1, tag2, "LDSD") !=0) {
					System.out.println("-------------------------------------------");
					System.out.println("VALOR DA SIMILARIDADE -> " + resultLDSDweighted);
					System.out.println("TAG 1 -> " + nameTag1 + " TAG 2 -> " + nameTag2 );
					System.out.println("-------------------------------------------");			
					
					mapResultLDSDweighted.put(nameTag1 + nameTag2, resultLDSDweighted);
					dbFunctions.insertOrUpdateTagSim(tag1.getId(), tag2.getId(), resultLDSDweighted, "LDSD");
				}
			}
		}

		System.out.println("\n ====================== ARRAYLIST DE ELEMENTOS QUE SERÃO SOMADOS ==================== ");
		System.out.println(mapResultLDSDweighted + " \n");
		System.out.println("\n ================================== RESULTADOS ====================================== ");
		
		// Resultado da soma de todos as tags que existe similardade dividida pela quantidade de itens da lista
		resultSumSemantic = calculeSemantic(mapResultLDSDweighted);
		
		if(resultSumSemantic != resultSumSemantic) resultSumSemantic = 0;
		
		return resultSumSemantic / mapResultLDSDweighted.size();
	}
	
	public static void calculeSimilarityBetweenFilmUserAndFilmNotRating(List<Integer> filmes, List<Integer> filmesNotRating, int userId, String type) {
		Similarity similarity;
		
		for (int j = 0; j < filmesNotRating.size(); j++) {

			switch (type) {
			case "LDSD+JACCARD":
				similarity = new ChooseLDSDJaccard();
				similarity.choiceOfSimilarity(filmes, filmesNotRating, userId);
				break;
			case "LDSD+COSINE":
				similarity = new ChooseLDSDCosine();
				similarity.choiceOfSimilarity(filmes, filmesNotRating, userId);
				break;
			case "WUP+JACCARD":
				similarity = new ChooseWUPJaccard();
				similarity.choiceOfSimilarity(filmes, filmesNotRating, userId);
				break;
			case "WUP+COSINE":
				similarity = new ChooseLDSDCosine();
				similarity.choiceOfSimilarity(filmes, filmesNotRating, userId);
				break;
			case "JACCARD":
				similarity = new ChooseJaccard();
				similarity.choiceOfSimilarity(filmes, filmesNotRating, userId);
				break;
			case "COSINE":
				similarity = new ChooseCosine();
				similarity.choiceOfSimilarity(filmes, filmesNotRating, userId);
				break;
			default:
				throw new RuntimeException("Strategy not found.");
			}
		}
	}

	public static double calculeSemantic(Map<String, Double> mapResultLDSDweighted) {
		double total = 0;

		for (Object key : mapResultLDSDweighted.keySet()) {
			total = total + mapResultLDSDweighted.get(key);
		}

		return total;
	}
	
	public static void removeEqual(Map<String, Double> map, Tag item1, Tag item2) {

		for (Object key : map.keySet()) {
			if (key.equals(item2.getName() + item1.getName())) {
				map.remove(key);
			}
		}
	}
	
	public static double calculeSumSimilarityAndJaccard(double  union, double  intersection, Double similarity) {
		double aDouble = 0;
		double sumSimilarityAndIntersertion = 0;
		double divSumSimilarityAndIntersertionByUnion = 0;
		String convertido = String.valueOf(similarity);
		
		if(convertido.equals("0.0")) {
			aDouble = 0.0000000;

		} else {
			
			System.out.println("VARIAVEL -> " + aDouble);
			
			aDouble = Double.parseDouble(convertido.substring(0, 8));
			sumSimilarityAndIntersertion = aDouble + intersection;
			divSumSimilarityAndIntersertionByUnion = sumSimilarityAndIntersertion / union;
				
			System.out.println(" ************ DENTRO DO MÉTODO ************");
			System.out.println("VALOR DA UNIÃO -> " + union);
			System.out.println("VALOR DA INTERSEÇÃO -> " + intersection);
			System.out.println("VALOR DA SOMA DA SIMILARIDADE -> " + similarity);
			System.out.println("VALOR DA SOMA DA SIMILARIDADE COM INTERSEÇÃO -> " + sumSimilarityAndIntersertion);
			System.out.println("VALOR DA SOMA DA SIMILARIDADE COM INTERSEÇÃO DIVIDIDO PELA UNIÃO-> " + divSumSimilarityAndIntersertionByUnion);
					
		}
		
		return divSumSimilarityAndIntersertionByUnion;
	}
	
	public static void saveResultSimilarityOfFimlUserAndFilmRecommended(String type, double similarityLexical, double similaritySemantic, double calculeSimilaritySemantic, int filmesNotRating, int userId) {
		DBFunctions dbFunctions = new DBFunctions();

		System.out.println(" \n ***************************** CALCULO DA SIMILARIDADE " + type + " ************************* \n");
		System.out.println("\n ========================= *** SIMILARITY " + type + " *** ====================================");
		System.out.println("                      SimilarityJaccard: " + similarityLexical);
		System.out.println("                      Soma Similarity Semântica: " + similaritySemantic);
		System.out.println("                      Resultado do Calculo: " + calculeSimilaritySemantic);
		System.out.println("=================================================================================== ");
		System.out.println("               TOTAL SOMA JACCARD +  SEMÂNTICA / UNIÃO " + type + ": " + (calculeSimilaritySemantic));
		System.out.println("======================================================================================== \n");

		if ((calculeSimilaritySemantic) != 0 || (calculeSimilaritySemantic) <= 1) {
			if (dbFunctions.isFilmsExistSemantic(1, filmesNotRating, userId, type) == false) {
				dbFunctions.insertOrUpdateSemanticFilm(1, filmesNotRating, type, calculeSimilaritySemantic, userId);
			}
		}
	}
	
	
		
	
	
	public static void createRecomedationSystemWithTopK(List<model.Rating> filmesNotRating, int userId, int itens, int topK) {
		
		DBFunctions dbFunctions = new DBFunctions();
		
		// Cria a lista com ID dos Itens Correntos
				List<Integer> idFilmRating = new ArrayList<Integer>();
								
				System.out.println(" \n ************* Fimes Avaliados ************* \n"); 
				List<Integer> filmes = dbFunctions.findByUserRatingHigher(userId, itens);
				System.out.println(" \n ******************************************* \n"); 
				
				int cont  = 0;
				
				System.out.println(" ********************** " + topK + " FILMES SERÃO OS ITENS CORRETOS. ************************");
				
				for (model.Rating rating : filmesNotRating) {
					cont = cont + 1;
					
				    if(cont <= topK ) {
				    	System.out.println("VALOR -> " + DBFunctions.findNameOfFilm(rating.getIddocument()) + " | RATING -> " + rating.getRating());
				    	idFilmRating.add(rating.getIddocument());
				    }
				}
				
				System.out.println(" -----------------------------------------------------------------------------------");
						
				TaggingFactory.calculeSimilarityBetweenFilmUserAndFilmNotRating(filmes, idFilmRating, userId, "LDSD+JACCARD");
				TaggingFactory.calculeSimilarityBetweenFilmUserAndFilmNotRating(filmes, idFilmRating, userId, "WUP+JACCARD");
//				TaggingFactory.calculeSimilarityBetweenFilmUserAndFilmNotRating(filmes, idFilmRating, userId, "LDSD+COSINE");
//				TaggingFactory.calculeSimilarityBetweenFilmUserAndFilmNotRating(filmes, idFilmRating, userId, "WUP+COSINE");
				TaggingFactory.calculeSimilarityBetweenFilmUserAndFilmNotRating(filmes, idFilmRating, userId, "COSINE");
				TaggingFactory.calculeSimilarityBetweenFilmUserAndFilmNotRating(filmes, idFilmRating, userId, "JACCARD");
			
				// LISTA DOS FILME INDICADO PELO (SR)
				List<Integer> rankedItemsLDSDJaccard = dbFunctions.createRecommendation(userId, "LDSD+JACCARD");
				List<Integer> rankedItemsWupJaccard = dbFunctions.createRecommendation(userId, "WUP+JACCARD");
//				List<Integer> rankedItemsLDSDCosine = dbFunctions.createRecommendation(userId, "LDSD+COSINE");
//				List<Integer> rankedItemsWupCosine = dbFunctions.createRecommendation(userId, "WUP+COSINE");
			
				dbFunctions.createRecommendation(userId, "COSINE");
				dbFunctions.createRecommendation(userId, "JACCARD");
				
				System.out.println("-------------- PRECISION ----------------");
				System.out.println("VALOR DO PRECISION: WUP + JACCARD -> " + PrecisionAndRecall.precision(rankedItemsWupJaccard,idFilmRating));
				System.out.println("VALOR DO PRECISION: LDSD + JACCARD -> " + PrecisionAndRecall.precision(rankedItemsLDSDJaccard,idFilmRating));
//				System.out.println("VALOR DO PRECISION: WUP + COSINE -> " + PrecisionAndRecall.precision(rankedItemsWupCosine,idFilmRating));
//				System.out.println("VALOR DO PRECISION: LDSD + COSINE -> " + PrecisionAndRecall.precision(rankedItemsLDSDCosine,idFilmRating));
				
				System.out.println("----------------- AP -----------------");
				System.out.println("VALOR AP WUP + JACCARD-> " + PrecisionAndRecall.AP(rankedItemsWupJaccard, idFilmRating, new ArrayList<Integer>()));
				System.out.println("VALOR AP LDSD + JACCARD-> " + PrecisionAndRecall.AP(rankedItemsLDSDJaccard, idFilmRating, new ArrayList<Integer>()));
//				System.out.println("VALOR AP WUP + COSINE-> " + PrecisionAndRecall.AP(rankedItemsWupCosine, idFilmRating, new ArrayList<Integer>()));
//				System.out.println("VALOR AP LDSD + COSINE-> " + PrecisionAndRecall.AP(rankedItemsLDSDCosine, idFilmRating, new ArrayList<Integer>()));
				
				// createRecommendation top 5, top 10, top 15 (calcular o precision de todos e o Map( AP de todos/ 3))
			
				for (model.Rating rating : filmesNotRating) {
					cont = cont + 1;
					
				    if(cont <= topK ) {
				    	System.out.println("FILMES SELECIONADOS TOP-K -> " + DBFunctions.findNameOfFilm(rating.getIddocument()) + " | RATING -> " + rating.getRating());
				    }
				}	
				
	}
	
	
	
	
	
	
}

