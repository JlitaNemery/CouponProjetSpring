package login;

/**
 * 
 * this enum provides the client types
 *
 */

public enum ClientType {

	ADMINISTRATOR,
	COMPANY,
	CUSTOMER;
	
	/**
	 * this function receives a string and returns the correct client type
	 * @param type
	 * @return
	 */
	public static ClientType getType(String type) {
		switch (type) {
		case "ADMINISTRATOR": return ClientType.ADMINISTRATOR;
		case "COMPANY": return ClientType.COMPANY;
		default: return ClientType.CUSTOMER;
		
		}
	}
}

