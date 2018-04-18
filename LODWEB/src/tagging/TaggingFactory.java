package tagging;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import database.DBFunctions;
import metric.PrecisionAndRecall;
import model.Ratings;
import model.Tag;
import similarity.LDSD;
import util.StringUtilsNode;
import util.strategy.ChooseCosine;
import util.strategy.ChooseJaccard;
import util.strategy.ChooseLDSDCosine;
import util.strategy.ChooseLDSDJaccard;
import util.strategy.ChooseWUPJaccard;
import util.strategy.Similarity;

public class TaggingFactory {

	/*
	 * Faz a comparação para verificar se as Tags são equals
	 */
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

	
	/*
	 *  Cria uma lista de Tags de um array de string
	 */
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
	
	/*
	 * concatena Tags de filmes
	 */
	public static String loadNameTagArray(List<Integer> filmes) {
		String text = null;
		
		for(int filme : filmes) {
			text = text + DBFunctions.getNameOfTag(filme);
		}
		
		return text;
	}
	
	public static String loadNameFilmString(List<Integer> filmes) {
		String text = null;
		
		for(int filme : filmes) {
			text = text + "," + DBFunctions.findNameOfFilm(filme);
		}
		
		return text;
	}
	
	
	
	public static String saveLoadNameTagArray(List<Integer> filmes) {
		String text = null;
		
		for(int filme : filmes) {
			text = text + "," + DBFunctions.getNameOfTag(filme);
		}
		
		return text;
	}


	public static String[] inputDados(String text) {
		return text.split(" ");
	}

	/*
	 * Concatena Tags da lista de Tags
	 */
	public static String concatenaTagText(List<Tag> listTag) {
		String valueTag = "";
		for (Tag tag : listTag) {
			valueTag = valueTag + " " + tag.getName();
		}

		System.out.println(" LISTA -> " + valueTag);

		return valueTag;
	}
	
	/*
	 * Cria um array de string de uma lista de Tags
	 */
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
	
	/*
	 * Contador para retornar o tamanha da lista de tags
	 */
	public static int countAllItem(List<Tag> list) {

		return list.size();
	}

	/*
	 * Calcula a similaridade semântica entre as Tag e retorna o valor
	 */
	public static double calculeTagSemanticLDSD(List<Tag> listTag1, List<Tag> listTag2) {
		Map<String, Double> mapResultLDSDweighted = new TreeMap<String, Double>();
		double ldsdWeighted = 0;
		int cont = 0;
		double resultSumSemantic = 0;
		DBFunctions dbFunctions = new DBFunctions();
		
		for (Tag item1 : listTag1) {
			for (Tag item2 : listTag2) {
				cont = cont++;
				
				Tag tag1 = dbFunctions.findTag(item1.getName());
				Tag tag2 = dbFunctions.findTag(item2.getName());
				
				String nameTag1 = StringUtilsNode.configureNameTagWithOutCharacterWithUnderLine(tag1.getName());
				String nameTag2 = StringUtilsNode.configureNameTagWithOutCharacterWithUnderLine(tag2.getName());
				
				double isTagSimResult = dbFunctions.isTagSimResult(tag1, tag2, "LDSD");
				
				if (isTagSimResult !=0) {
					System.out.println("VALOR PARA A CONDIÇÃO DO IF -> " + isTagSimResult);
					System.out.println("VALOR INSERIDO DO BANCO " + isTagSimResult);
				} else {
					System.out.println("TAG 1 SEM CARACTERES E LETRA MAIUSCULA-> " + nameTag1);
					System.out.println("TAG 2 SEM CARACTERES E LETRA MAIUSCULA-> " + nameTag2);
				
					ldsdWeighted = LDSD.LDSDweighted("http://dbpedia.org/resource/" + nameTag1, "http://dbpedia.org/resource/" + nameTag2); }
				
					System.out.println("LDSD:  " + nameTag1 + " -  " + nameTag2 + " = " + isTagSimResult);

				if (isTagSimResult >= 1.0 && isTagSimResult != 0.0 || isTagSimResult !=0) {
					System.out.println("-------------------------------------------");
					System.out.println("VALOR DA SIMILARIDADE -> " + isTagSimResult);
					System.out.println("TAG 1 -> " + nameTag1 + " TAG 2 -> " + nameTag2 );
					System.out.println("-------------------------------------------");			
					
					mapResultLDSDweighted.put(nameTag1 + nameTag2, isTagSimResult);
					
					dbFunctions.insertOrUpdateTagSim(tag1.getId(), tag2.getId(), isTagSimResult, "LDSD");
				}
			}
	
		//	if(mapResultLDSDweighted.size() >= cont) {
		//		break;
		//	}
		
		}

		System.out.println("\n ====================== ARRAYLIST DE ELEMENTOS QUE SERÃO SOMADOS ==================== ");
		System.out.println(mapResultLDSDweighted + " \n");
		System.out.println("\n ================================== RESULTADOS ====================================== ");
		
		// Resultado da soma de todos as tags que existe similardade dividida pela quantidade de itens da lista
		resultSumSemantic = sumSemantic(mapResultLDSDweighted);
		
		if(resultSumSemantic != resultSumSemantic) resultSumSemantic = 0;
		if(resultSumSemantic >= 1.0000000) resultSumSemantic = 1.0000000;
		
		return resultSumSemantic;
	}
	
	
	/*
	 * Escolhe qual calculo de similaridade irá calcular
	 */
	public static void calculeSimilarityBetweenUserModelAndTestSet(List<Integer> userModel, List<Integer> testSet, int userId, String type) {
		Similarity similarity;
		
		for (int j = 0; j < testSet.size(); j++) {

			switch (type) {
			case "LDSD+JACCARD":
				similarity = new ChooseLDSDJaccard();
				similarity.choiceOfSimilarity(userModel, testSet, userId);
				break;
			case "LDSD+COSINE":
				similarity = new ChooseLDSDCosine();
				similarity.choiceOfSimilarity(userModel, testSet, userId);
				break;
			case "WUP+JACCARD":
				similarity = new ChooseWUPJaccard();
				similarity.choiceOfSimilarity(userModel, testSet, userId);
				break;
			case "WUP+COSINE":
				similarity = new ChooseLDSDCosine();
				similarity.choiceOfSimilarity(userModel, testSet, userId);
				break;
			case "JACCARD":
				similarity = new ChooseJaccard();
				similarity.choiceOfSimilarity(userModel, testSet, userId);
				break;
			case "COSINE":
				similarity = new ChooseCosine();
				similarity.choiceOfSimilarity(userModel, testSet, userId);
				break;
			default:
				throw new RuntimeException("Strategy not found.");
			}
		}
	}
	
    /*
     * Soma todos resultados similaridades semnaticas encontrada entre duas Tag
     */
	public static double sumSemantic(Map<String, Double> mapResultLDSDweighted) {
		double total = 0;

		for (Object key : mapResultLDSDweighted.keySet()) {
			total = total + mapResultLDSDweighted.get(key);
		}

		return total;
	}
	
	
	/*
	 * Remove tags iguais do mapa
	 */
	public static void removeEqual(Map<String, Double> map, Tag item1, Tag item2) {

		for (Object key : map.keySet()) {
			if (key.equals(item2.getName() + item1.getName())) {
				map.remove(key);
			}
		}
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static List<Ratings> orderTestSetByRating(List<Ratings> testSet) {
		
		Collections.sort(testSet, new Comparator() {
            public int compare(Object o1, Object o2) {
                Ratings p1 = (Ratings) o1;
                Ratings p2 = (Ratings) o2;
                return p1.getRating() > p2.getRating() ? -1 : (p1.getRating() > p2.getRating() ? +1 : 0);
            }
        });
		
		return testSet;
		
	}
	
	/*
	 * Faz o calculo proposto para melhorar a precição da recomendação
	 */
	public static double calculeSimilarityAndJaccard(double  union, double  intersection, double similarity) {

		double sumSimilarityAndIntersertion = 0;
		double resultCalcule = 0;
									
				resultCalcule = (similarity + intersection) / union;		
					
				System.out.println(" ************ DENTRO DO MÉTODO ************");
				System.out.println("VALOR DA UNIÃO -> " + union);
				System.out.println("VALOR DA INTERSEÇÃO -> " + intersection);
				System.out.println("VALOR DA SOMA DA SIMILARIDADE -> " + similarity);
				System.out.println("VALOR DA SOMA DA SIMILARIDADE COM INTERSEÇÃO -> " + sumSimilarityAndIntersertion);
				System.out.println("VALOR DA SOMA DA SIMILARIDADE COM INTERSEÇÃO DIVIDIDO PELA UNIÃO-> " + resultCalcule);
		
			return resultCalcule;
	}
	
	/*
	 * Salva o resultado do calculo da similaridade entre UserModel e TestSet
	 */
	public static void saveResultSimilarityOfUserModelWithTestSet(String type, double similarityLexical, double similaritySemantic, double calculeSimilaritySemantic, int testSet, int userId) {
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
			if (dbFunctions.isFilmsExistSemantic(1, testSet, userId, type) == false) {
				dbFunctions.insertOrUpdateSemantic(1, testSet, type, calculeSimilaritySemantic, userId);
			}
		}
	}
	
	/*
	 * Calcula a similaridade e salva no banco de dados e devolve uma recomendações de filmes
	 */
	public static void createRecomedationSystem(List<Integer> userModel, List<model.Ratings> testSet, int userId) {
		
		DBFunctions dbFunctions = new DBFunctions();
		
				// Cria a lista com ID dos Itens Correntos
				List<Integer> filmRatingList = new ArrayList<Integer>();
												
				for (model.Ratings rating : testSet) filmRatingList.add(rating.getIddocument());
								
			//	TaggingFactory.calculeSimilarityBetweenUserModelAndTestSet(userModel, filmRatingList, userId, "LDSD+JACCARD");
			//	TaggingFactory.calculeSimilarityBetweenUserModelAndTestSet(userModel, filmRatingList, userId, "WUP+JACCARD");
				TaggingFactory.calculeSimilarityBetweenUserModelAndTestSet(userModel, filmRatingList, userId, "COSINE");
			//	TaggingFactory.calculeSimilarityBetweenUserModelAndTestSet(userModel, filmRatingList, userId, "JACCARD");
				
				/* 
				 * Exibe e retorna a lista com as simiaridades encontrada
				 */
			//	List<Integer> jaccardAndLDSDRankedList = dbFunctions.resultRecommendation(userId, "LDSD+JACCARD");
			//	List<Integer> jaccardAndWUPRankedList = dbFunctions.resultRecommendation(userId, "WUP+JACCARD");
				List<Integer> cosineRankedList = dbFunctions.resultRecommendation(userId, "COSINE");
			//	List<Integer> jaccardRankedList = dbFunctions.resultRecommendation(userId, "JACCARD");
				
			//	System.out.println("-------------- PRECISION ----------------");
			//	System.out.println("VALOR DO PRECISION: LDSD + JACCARD -> " + PrecisionAndRecall.precision(jaccardAndLDSDRankedList,filmRatingList));
			//	System.out.println("VALOR DO PRECISION: WUP + JACCARD -> " + PrecisionAndRecall.precision(jaccardAndWUPRankedList,filmRatingList));
			//	System.out.println("VALOR DO PRECISION: COSINE -> " + PrecisionAndRecall.precision(cosineRankedList,filmRatingList));
			//	System.out.println("VALOR DO PRECISION: JACCARD -> " + PrecisionAndRecall.precision(jaccardRankedList,filmRatingList));
								
			
				System.out.println(" -------------- PRECISION COSINE---------------- \n");
				double precisionJaccardAndLDSDRankedList = PrecisionAndRecall.precision(cosineRankedList,filmRatingList);
				System.out.println("VALOR DO PRECISION: COSINE -> " + precisionJaccardAndLDSDRankedList);
				
				System.out.println(" -------------- AP: COSINE ---------------- \n");
			
				System.out.println("VALOR AP 3 -> " + calculeAP(cosineRankedList,filmRatingList, "COSINE", 3));
				System.out.println("VALOR AP 5 -> " + calculeAP(cosineRankedList,filmRatingList, "COSINE", 5));
				System.out.println("VALOR AP 10 -> " + calculeAP(cosineRankedList,filmRatingList, "COSINE", 10));
				
				
				double mapJaccardAndLDSDRankedList = calculeMAP(cosineRankedList,filmRatingList, "COSINE");
				
				/*
				 * Salva o resultado dos calculos de Precisio
				 */
				dbFunctions.saveResult(
						userId, 
						userModel, 
						filmRatingList, 
						calculeAP(cosineRankedList,filmRatingList, "COSINE", 3), 
						calculeAP(cosineRankedList,filmRatingList, "COSINE", 5), 
						calculeAP(cosineRankedList,filmRatingList, "COSINE", 10), 
						precisionJaccardAndLDSDRankedList, 
						mapJaccardAndLDSDRankedList, 
						"COSINE");

	}
	
	public static double calculeAP(List<Integer> rankedList, List<Integer> testList, String similarity, int limit) {

		List<Integer> listRank = new ArrayList<Integer>(); 
		int cont = 0;
		
		for (int test: testList) {
			cont++;
			if(cont <= limit) {
				listRank.add(test);
			}
		}

		double AP = PrecisionAndRecall.AP(listRank, testList, new ArrayList<Integer>());

		System.out.println("VALOR AP: " + similarity + ": " + AP + " Qtd -> " + listRank.size());

		return AP;
	}

	public static double calculeMAP(List<Integer> rankedList, List<Integer> testList, String similarity) {

		double apLdsdJaccard3 = calculeAP(rankedList, testList, similarity, 3);
		double apLdsdJaccard5 = calculeAP(rankedList, testList, similarity, 5);
		double apLdsdJaccard10 = calculeAP(rankedList, testList, similarity, 10);

		double map = (apLdsdJaccard3 + apLdsdJaccard5 + apLdsdJaccard10) / 3;

		System.out.println("VALOR MAP DA SIMLARIDADE " + similarity + ": " + map);
		
		return map;

	}
	
	
}

