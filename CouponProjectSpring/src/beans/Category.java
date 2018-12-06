package beans;
/**
 * 
 * this enum is for coupon category
 *
 */



public enum Category {

	FOOD,
	ELECTRICITY,
	RESTAURANT,
	VACATION,
	FASHION,
	HOME_DEPOT,
	TOYS,
	GARDEN,
	MUSICAL_INSTRUMENTS;
	
	/**
	 * 
	 * @param type - receives String 
	 * @return the proper enum
	 */
	
	public static Category getType(String type) {
		switch (type) {
		case "FOOD": return Category.FOOD;
		case "ELECTRICITY": return Category.ELECTRICITY;
		case "RESTAURANT": return Category.RESTAURANT;
		case "VACATION": return Category.VACATION;
		case "FASHION": return Category.FASHION;
		case "HOME_DEPOT": return Category.HOME_DEPOT;
		case "TOYS": return Category.TOYS;
		case "GARDEN": return Category.GARDEN;
		default: return Category.MUSICAL_INSTRUMENTS;
		
		}
	}
	public static String getString(Category type) {
		switch (type) {
		case FOOD: return "FOOD";
		case ELECTRICITY: return "ELECTRICITY";
		case RESTAURANT: return "RESTAURANT";
		case VACATION: return "VACATION";
		case FASHION: return "FASHION";
		case HOME_DEPOT: return "HOME_DEPOT";
		case TOYS: return "TOYS";
		case GARDEN: return "GARDEN";
		default: return "MUSICAL_INSTRUMENTS";
		
		}
	}
}

