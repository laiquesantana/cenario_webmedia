package tagging;
import java.util.List;
import java.util.ArrayList;
import database.DBFunctions;
import model.Ratings;

public class CBRecommender {
	
	public static long init;
	public static long end;
	public static void main(String[] args) {
	
	   init = System.currentTimeMillis(); 
	
	    // createScenario();
	   
	         recommend(2299);
	         recommend(2931);
	         recommend(1629);
	         recommend(1678);
	}

	public static void createScenario() {
	/*
		
		DBFunctions dbFunctions = new DBFunctions();
	
		// create user
		User user1 = new User(11, "Patrick");
		User user2 = new User(12, "Pablo");
		User user3 = new User(13, "Monica");
		User user4 = new User(14, "Tatiana");
		User user5 = new User(15, "Fred");

		// create 10 Document
		Document document1 = new Document(1, "Batman");
		Document document2 = new Document(2, "Hulk");
		Document document3 = new Document(3, "Thor");
		Document document4 = new Document(4, "Snoopy");
		Document document5 = new Document(5, "Flash");
		Document document6 = new Document(6, "Mickey_Mouse");
		Document document7 = new Document(7, "Popeye");
		Document document8 = new Document(8, "Doug");
		Document document9 = new Document(9, "Chappolin");
		Document document10 = new Document(10, "Felix");
		
		// create Tags
		
		// FILM BATMAN
		Tag tag1 = new Tag(12, "Action");
		Tag tag2 = new Tag(13, "Adventure");
		Tag tag3 = new Tag(14, "Bruce");
		Tag tag4 = new Tag(15, "Fighting");
				
		// FILM HULK
		Tag tag5 = new Tag(12, "Action");
		Tag tag6 = new Tag(17, "Sci-Fi");
		Tag tag7 = new Tag(18, "Monster");
		Tag tag8 = new Tag(19, "Scientist");
		
		// FILM THOR
		Tag tag9 = new Tag(19, "Scientist");
		Tag tag10 = new Tag(21, "Fantasy");
		Tag tag11 = new Tag(17, "Sci-Fi");
		Tag tag12 = new Tag(12, "Action");
		
		// FILM SNOOPY
		Tag tag13 = new Tag(13, "Adventure");
		Tag tag14 = new Tag(25, "Comedy");
		Tag tag15 = new Tag(26, "Travel");
		Tag tag16 = new Tag(27, "Child");
		
		// FILM FLASH
		Tag tag17 = new Tag(12, "Action");
		Tag tag18 = new Tag(13, "Adventure");
		Tag tag19 = new Tag(30, "Superhero");
		Tag tag20 = new Tag(31, "Forensics");
		
		// FILME MICKEY
		Tag tag21 = new Tag(32, "Animation");
		Tag tag22 = new Tag(25, "Comedy");
		Tag tag23 = new Tag(34, "Disney");
		Tag tag24 = new Tag(35, "Pluto");
		
		// FILME POPAYE
		Tag tag25 = new Tag(25, "Comedy");
		Tag tag26 = new Tag(13, "Adventure");
		Tag tag27 = new Tag(34, "Disney");
		Tag tag28 = new Tag(39, "Hero");
		
		// FILME DOUG
		Tag tag29 = new Tag(25, "Comedy");
		Tag tag30 = new Tag(32, "Animation");
		Tag tag31 = new Tag(42, "Journal");
		Tag tag32 = new Tag(43, "Daydream");
		
		// FILME CHAPPOLIN
		Tag tag33 = new Tag(25, "Comedy");
		Tag tag34 = new Tag(13, "Adventure");
		Tag tag35 = new Tag(34, "Disney");
		Tag tag36 = new Tag(39, "Hero");
		
		// FILME FELIX
		Tag tag37 = new Tag(25, "Comedy");
		Tag tag38 = new Tag(13, "Adventure");
		Tag tag39 = new Tag(30, "Superhero");
		Tag tag40 = new Tag(39, "Hero");
				
		dbFunctions.insertOrUpdateDocument(document1.getName());
		dbFunctions.insertOrUpdateDocument(document2.getName());
		dbFunctions.insertOrUpdateDocument(document3.getName());
		dbFunctions.insertOrUpdateDocument(document4.getName());
		dbFunctions.insertOrUpdateDocument(document5.getName());
		dbFunctions.insertOrUpdateDocument(document6.getName());
		dbFunctions.insertOrUpdateDocument(document7.getName());
		dbFunctions.insertOrUpdateDocument(document8.getName());
		dbFunctions.insertOrUpdateDocument(document9.getName());
		dbFunctions.insertOrUpdateDocument(document10.getName());

		dbFunctions.insertOrUpdateTag(tag1.getName());
		dbFunctions.insertOrUpdateTag(tag2.getName());
		dbFunctions.insertOrUpdateTag(tag3.getName());
		dbFunctions.insertOrUpdateTag(tag4.getName());
		dbFunctions.insertOrUpdateTag(tag5.getName());
		dbFunctions.insertOrUpdateTag(tag6.getName());
		dbFunctions.insertOrUpdateTag(tag7.getName());
		dbFunctions.insertOrUpdateTag(tag8.getName());
		dbFunctions.insertOrUpdateTag(tag9.getName());
		dbFunctions.insertOrUpdateTag(tag10.getName());
		dbFunctions.insertOrUpdateTag(tag11.getName());
		dbFunctions.insertOrUpdateTag(tag12.getName());
		dbFunctions.insertOrUpdateTag(tag13.getName());
		dbFunctions.insertOrUpdateTag(tag14.getName());
		dbFunctions.insertOrUpdateTag(tag15.getName());
		dbFunctions.insertOrUpdateTag(tag16.getName());
		dbFunctions.insertOrUpdateTag(tag17.getName());
		dbFunctions.insertOrUpdateTag(tag18.getName());
		dbFunctions.insertOrUpdateTag(tag19.getName());
		dbFunctions.insertOrUpdateTag(tag20.getName());
		dbFunctions.insertOrUpdateTag(tag21.getName());
		dbFunctions.insertOrUpdateTag(tag22.getName());
		dbFunctions.insertOrUpdateTag(tag23.getName());
		dbFunctions.insertOrUpdateTag(tag24.getName());
		dbFunctions.insertOrUpdateTag(tag25.getName());
		dbFunctions.insertOrUpdateTag(tag26.getName());
		dbFunctions.insertOrUpdateTag(tag27.getName());
		dbFunctions.insertOrUpdateTag(tag28.getName());
		dbFunctions.insertOrUpdateTag(tag29.getName());
		dbFunctions.insertOrUpdateTag(tag30.getName());
		dbFunctions.insertOrUpdateTag(tag31.getName());
		dbFunctions.insertOrUpdateTag(tag32.getName());
		dbFunctions.insertOrUpdateTag(tag33.getName());
		dbFunctions.insertOrUpdateTag(tag34.getName());
		dbFunctions.insertOrUpdateTag(tag35.getName());
		dbFunctions.insertOrUpdateTag(tag36.getName());
		dbFunctions.insertOrUpdateTag(tag37.getName());
		dbFunctions.insertOrUpdateTag(tag38.getName());
		dbFunctions.insertOrUpdateTag(tag39.getName());
		dbFunctions.insertOrUpdateTag(tag40.getName());

		// Documentos com 3 tags cada

		Tagging tagging1 = new Tagging(document1, user1, tag1);
		Tagging tagging2 = new Tagging(document1, user1, tag2);
		Tagging tagging3 = new Tagging(document1, user1, tag3);
		Tagging tagging4 = new Tagging(document1, user1, tag4);

		Tagging tagging5 = new Tagging(document2, user1, tag5);
		Tagging tagging6 = new Tagging(document2, user1, tag6);
		Tagging tagging7 = new Tagging(document2, user1, tag7);
		Tagging tagging8 = new Tagging(document2, user1, tag8);

		Tagging tagging9 = new Tagging(document4, user1, tag9);
		Tagging tagging10 = new Tagging(document4, user1, tag10);
		Tagging tagging11 = new Tagging(document4, user1, tag11);
		Tagging tagging12 = new Tagging(document4, user1, tag12);

		Tagging tagging13 = new Tagging(document5, user1, tag13);
		Tagging tagging14 = new Tagging(document5, user1, tag14);
		Tagging tagging15 = new Tagging(document5, user1, tag15);
		Tagging tagging16 = new Tagging(document5, user1, tag16);
		
		Tagging tagging17 = new Tagging(document6, user1, tag17);
		Tagging tagging18 = new Tagging(document6, user1, tag18);
		Tagging tagging19 = new Tagging(document6, user1, tag19);
		Tagging tagging20 = new Tagging(document6, user1, tag20);
		
		Tagging tagging21 = new Tagging(document7, user1, tag21);
		Tagging tagging22 = new Tagging(document7, user1, tag22);
		Tagging tagging23 = new Tagging(document7, user1, tag23);
		Tagging tagging24 = new Tagging(document7, user1, tag24);
		
		Tagging tagging25 = new Tagging(document8, user1, tag25);
		Tagging tagging26 = new Tagging(document8, user1, tag26);
		Tagging tagging27 = new Tagging(document8, user1, tag27);
		Tagging tagging28 = new Tagging(document8, user1, tag28);
		
		Tagging tagging29 = new Tagging(document8, user1, tag29);
		Tagging tagging30 = new Tagging(document8, user1, tag30);
		Tagging tagging31 = new Tagging(document8, user1, tag31);
		Tagging tagging32 = new Tagging(document8, user1, tag32);
		
		Tagging tagging33 = new Tagging(document8, user1, tag33);
		Tagging tagging34 = new Tagging(document8, user1, tag34);
		Tagging tagging35 = new Tagging(document8, user1, tag35);
		Tagging tagging36 = new Tagging(document8, user1, tag36);
		
		Tagging tagging37 = new Tagging(document8, user1, tag37);
		Tagging tagging38 = new Tagging(document8, user1, tag38);
		Tagging tagging39 = new Tagging(document8, user1, tag39);
		Tagging tagging40 = new Tagging(document8, user1, tag40);
				
		dbFunctions.insertOrUpdateTagging(tagging1.getDocument().getId(), tagging1.getUser().getId(), tagging1.getTag().getId());
		dbFunctions.insertOrUpdateTagging(tagging2.getDocument().getId(), tagging2.getUser().getId(),tagging2.getTag().getId());
		dbFunctions.insertOrUpdateTagging(tagging3.getDocument().getId(), tagging3.getUser().getId(),tagging3.getTag().getId());
		dbFunctions.insertOrUpdateTagging(tagging4.getDocument().getId(), tagging4.getUser().getId(),tagging4.getTag().getId());
		
		dbFunctions.insertOrUpdateTagging(tagging5.getDocument().getId(), tagging5.getUser().getId(),tagging5.getTag().getId());
		dbFunctions.insertOrUpdateTagging(tagging6.getDocument().getId(), tagging6.getUser().getId(),tagging6.getTag().getId());
		dbFunctions.insertOrUpdateTagging(tagging7.getDocument().getId(), tagging7.getUser().getId(),tagging7.getTag().getId());
		dbFunctions.insertOrUpdateTagging(tagging8.getDocument().getId(), tagging8.getUser().getId(),tagging8.getTag().getId());
		
		dbFunctions.insertOrUpdateTagging(tagging9.getDocument().getId(), tagging9.getUser().getId(),tagging9.getTag().getId());
		dbFunctions.insertOrUpdateTagging(tagging10.getDocument().getId(), tagging10.getUser().getId(),tagging10.getTag().getId());
		dbFunctions.insertOrUpdateTagging(tagging11.getDocument().getId(), tagging11.getUser().getId(),tagging11.getTag().getId());
		dbFunctions.insertOrUpdateTagging(tagging12.getDocument().getId(), tagging12.getUser().getId(),tagging12.getTag().getId());
		
		dbFunctions.insertOrUpdateTagging(tagging13.getDocument().getId(), tagging13.getUser().getId(),tagging13.getTag().getId());
		dbFunctions.insertOrUpdateTagging(tagging14.getDocument().getId(), tagging14.getUser().getId(),tagging14.getTag().getId());
		dbFunctions.insertOrUpdateTagging(tagging15.getDocument().getId(), tagging15.getUser().getId(),tagging15.getTag().getId());
		dbFunctions.insertOrUpdateTagging(tagging16.getDocument().getId(), tagging16.getUser().getId(),tagging16.getTag().getId());
		
		dbFunctions.insertOrUpdateTagging(tagging17.getDocument().getId(), tagging17.getUser().getId(),tagging17.getTag().getId());
		dbFunctions.insertOrUpdateTagging(tagging18.getDocument().getId(), tagging18.getUser().getId(),tagging18.getTag().getId());
		dbFunctions.insertOrUpdateTagging(tagging19.getDocument().getId(), tagging19.getUser().getId(),tagging19.getTag().getId());
		dbFunctions.insertOrUpdateTagging(tagging20.getDocument().getId(), tagging20.getUser().getId(),tagging20.getTag().getId());
		
		dbFunctions.insertOrUpdateTagging(tagging21.getDocument().getId(), tagging21.getUser().getId(),tagging21.getTag().getId());
		dbFunctions.insertOrUpdateTagging(tagging22.getDocument().getId(), tagging22.getUser().getId(),tagging22.getTag().getId());
		dbFunctions.insertOrUpdateTagging(tagging23.getDocument().getId(), tagging23.getUser().getId(),tagging23.getTag().getId());
		dbFunctions.insertOrUpdateTagging(tagging24.getDocument().getId(), tagging24.getUser().getId(),tagging24.getTag().getId());
		
		dbFunctions.insertOrUpdateTagging(tagging25.getDocument().getId(), tagging25.getUser().getId(),tagging25.getTag().getId());
		dbFunctions.insertOrUpdateTagging(tagging26.getDocument().getId(), tagging26.getUser().getId(),tagging26.getTag().getId());
		dbFunctions.insertOrUpdateTagging(tagging27.getDocument().getId(), tagging27.getUser().getId(),tagging27.getTag().getId());
		dbFunctions.insertOrUpdateTagging(tagging28.getDocument().getId(), tagging28.getUser().getId(),tagging28.getTag().getId());
		
		dbFunctions.insertOrUpdateTagging(tagging29.getDocument().getId(), tagging29.getUser().getId(),tagging29.getTag().getId());
		dbFunctions.insertOrUpdateTagging(tagging30.getDocument().getId(), tagging30.getUser().getId(),tagging30.getTag().getId());
		dbFunctions.insertOrUpdateTagging(tagging31.getDocument().getId(), tagging31.getUser().getId(),tagging31.getTag().getId());
		dbFunctions.insertOrUpdateTagging(tagging32.getDocument().getId(), tagging32.getUser().getId(),tagging32.getTag().getId());
		
		dbFunctions.insertOrUpdateTagging(tagging33.getDocument().getId(), tagging33.getUser().getId(),tagging33.getTag().getId());
		dbFunctions.insertOrUpdateTagging(tagging34.getDocument().getId(), tagging34.getUser().getId(),tagging34.getTag().getId());
		dbFunctions.insertOrUpdateTagging(tagging35.getDocument().getId(), tagging35.getUser().getId(),tagging35.getTag().getId());
		dbFunctions.insertOrUpdateTagging(tagging36.getDocument().getId(), tagging36.getUser().getId(),tagging36.getTag().getId());
		
		dbFunctions.insertOrUpdateTagging(tagging37.getDocument().getId(), tagging37.getUser().getId(),tagging37.getTag().getId());
		dbFunctions.insertOrUpdateTagging(tagging38.getDocument().getId(), tagging38.getUser().getId(),tagging38.getTag().getId());
		dbFunctions.insertOrUpdateTagging(tagging39.getDocument().getId(), tagging39.getUser().getId(),tagging39.getTag().getId());
		dbFunctions.insertOrUpdateTagging(tagging40.getDocument().getId(), tagging40.getUser().getId(),tagging40.getTag().getId());

		// criar avaliacao 5 usuario avaliou e 5 usuario nao avaliou

		Rating rating1 = new Rating(document1, user1, 5);
		Rating rating2 = new Rating(document2, user2, 5);
		Rating rating3 = new Rating(document3, user3, 5);
		Rating rating4 = new Rating(document4, user4, 5);
		Rating rating5 = new Rating(document5, user5, 5);
		Rating rating6 = new Rating(document6, user1);
		Rating rating7 = new Rating(document7, user2);
		Rating rating8 = new Rating(document8, user3);
		Rating rating9 = new Rating(document9, user4);
		Rating rating10 = new Rating(document10, user5);

		dbFunctions.insertOrUpdateRating(rating1.getDocument().getId(), rating1.getUser().getId(), rating1.getRating());
		dbFunctions.insertOrUpdateRating(rating2.getDocument().getId(), rating2.getUser().getId(), rating2.getRating());
		dbFunctions.insertOrUpdateRating(rating3.getDocument().getId(), rating3.getUser().getId(), rating3.getRating());
		dbFunctions.insertOrUpdateRating(rating4.getDocument().getId(), rating4.getUser().getId(), rating4.getRating());
		dbFunctions.insertOrUpdateRating(rating5.getDocument().getId(), rating5.getUser().getId(), rating5.getRating());
		dbFunctions.insertOrUpdateRating(rating6.getDocument().getId(), rating6.getUser().getId(), rating6.getRating());
		dbFunctions.insertOrUpdateRating(rating7.getDocument().getId(), rating7.getUser().getId(), rating7.getRating());
		dbFunctions.insertOrUpdateRating(rating8.getDocument().getId(), rating8.getUser().getId(), rating8.getRating());
		dbFunctions.insertOrUpdateRating(rating9.getDocument().getId(), rating9.getUser().getId(), rating9.getRating());
		dbFunctions.insertOrUpdateRating(rating10.getDocument().getId(), rating10.getUser().getId(), rating10.getRating());
		
		*/
	}


	public static void recommend(int userId) {
		int limitUserModel = 5;
		int limitMax = 5;
		int limitMin = 5;
		
		List<Ratings> testSet = new ArrayList<Ratings>();
		DBFunctions dbFunctions = new DBFunctions();

		System.out.println(" \n ************* List UserModel ************* \n");
		List<Integer> userModel = dbFunctions.findByUserRating(userId, limitUserModel);
		System.out.println(" \n ******************************************* \n");

		System.out.println(" \n ************* Filmes TestSet ************* \n");
		List<Ratings> testSetRelevant = DBFunctions.createTestSetByMin(2199,limitMin,4);
		List<Ratings> testSetIrrelevant = DBFunctions.createTestSetByMax(2199,limitMax,3);
	
		
		testSet.addAll(TaggingFactory.orderTestSetByRating(testSetRelevant));
		testSet.addAll(TaggingFactory.orderTestSetByRating(testSetIrrelevant));

		System.out.println(" \n *********************************************** \n");
			
		for(Ratings r: testSet) {
			System.out.println(
					" ID -> " + r.getIddocument() + 
					" NOME DO FILME -> " + DBFunctions.findNameOfFilm(r.getIddocument()) + 
					" RATING -> " + r.getRating()
					);
		}
		
		System.out.println(" \n *********************************************** \n");

		TaggingFactory.createRecomedationSystem(userModel, testSet, userId);
		
		}
}
