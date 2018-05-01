package tagging;
import java.util.List;
import java.util.ArrayList;
import database.DBFunctions;
import model.Ratings;

public class CBRecommender {

	public static long init;
	public static long end;

	public static void main(String[] args) {

		/*
		 * Quantidade de usuarios
		 */
		int users = 100;
		
		
		init = System.currentTimeMillis();
		//CreateScenario.createScenario();
				
		List<Integer> listUsers = DBFunctions.findUsers();
		
		for(int i=1; i < listUsers.size(); i++) {
			recommend(listUsers.get(i));
			if(i == users) {
				break;
			}
		}
	}

	public static void recommend(int idUser) {
		int limitItemUserModel = 5;
		int limitMax = 5;
		int limitMin = 5;
		
		DBFunctions dbFunctions = new DBFunctions();
		List<Ratings> testSet = new ArrayList<Ratings>();
		
		System.out.println("ID USUARIO -> " + idUser);

		System.out.println(" \n ************* List UserModel ************* \n");
		List<Integer> userModel = dbFunctions.createUserModel(idUser, limitItemUserModel);
		System.out.println(" \n ******************************************* \n");

		System.out.println(" \n ************* Filmes TestSet ************* \n");
		List<Ratings> testSetRelevant = DBFunctions.createTestSetByMin(idUser, limitMin, 4);
		List<Ratings> testSetIrrelevant = DBFunctions.createTestSetByMax(idUser, limitMax, 3);

		testSet.addAll(testSetRelevant);
		testSet.addAll(testSetIrrelevant);

		TaggingFactory.createRecomedationSystem(userModel, testSet, idUser);
	}

}
