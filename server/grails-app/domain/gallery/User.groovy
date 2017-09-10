package gallery

import java.util.*

class User {
	final int MINIMUM_AGE_IN_MS = 1000 * 60 * 60 * 24 * 365 * 13 //13 Years old

	String _id
	String firstName
	String lastName
	String email
	String password
	String role = "user"
//	Date dateOfBirth;
	
	// Forces unique values for 'email' by using a unique index in MongoDB
	static mapping = { 
		email index:true, indexAttributes: [unique: true, background: true]
	}
	
    static constraints = {
	    	email email: true, blank: false
	    	password blank: false
	    	firstName blank: false
	    	lastName blank: false
	    	role inList: ["user", "admin", "artist"]
//	    	dateOfBirth(validator: { 
//	    		return ( it > new Date().setTime(new Date().getTime() - MINIMUM_AGE_IN_MS) ) //TODO - Rethink this, seems inelegant. See page 43 or Grails GS book 
//	    	}); //TODO Think of a better way of handling Date for API 
    }
}
