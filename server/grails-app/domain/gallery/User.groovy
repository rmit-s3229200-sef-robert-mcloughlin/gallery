package gallery

class User {
	String username
	String password
	String role
	
    static constraints = {
    	username blank: false
    	password blank: false
    	role blank: false
    }

    public String getUsername() {
    	return username
    }
}
