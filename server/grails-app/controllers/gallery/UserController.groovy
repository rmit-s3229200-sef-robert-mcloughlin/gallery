package gallery

import grails.gorm.DetachedCriteria
import com.mongodb.client.FindIterable
import static com.mongodb.client.model.Filters.*
import com.mongodb.client.model.Projections

import static org.springframework.http.HttpStatus.*
import grails.transaction.Transactional
import grails.converters.*
import gallery.Authentication

import org.apache.commons.io.IOUtils

@Transactional(readOnly = true)
class UserController {
    Authentication auth = new Authentication()

    static responseFormats = ['json', 'xml']
    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    // Display details for all users
    // Endpoint: /user
    // HTTP Method: GET
    // TODO - Tighten security to only allow admins, allow paginated results
    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        respond User.list(params), model:[userCount: User.count()]
    }

    // Display details for a specific user
    // Endpoint: /user/{_id}
    // HTTP Method: GET
    def show(User user) {
    		String token = request.getHeader('token')
    		
    		if(!user || !token) { //Require a JWT token from signed in user
    			render (status: 401, text: "Page requires authentication")
    			return
    		}
    		
    		if( auth.isMe(user.id, token) || auth.isInRole("admin", token) ) { //Only administrators and self can view a User object
			def doc = User.collection
                .find(eq("_id", user.id))
                .first()

			//TODO - Figure out how to use projections, so we don't return password field
	        respond (status: 200, doc as User)
	        return    		
    		} else { 
			respond (status: 400, text: "Cannot view this profile page")
	        return    		
    		}

    }
	
    @Transactional
    // Saves a new user to the database
    // Endpoint: /user
    // HTTTP Method: POST
    def save(User user) {
        if (user == null) { //Fail if no user provided
            transactionStatus.setRollbackOnly()
            render status: NOT_FOUND
            return
        }

        if (user.hasErrors()) { //Fail if errors creating User object
            transactionStatus.setRollbackOnly()
            respond user.errors, view:'create'
            return
        }

        def doc = User.collection
            .find(eq("email", user.email))
            .first()

        if (!!doc) {
                render(status: 500, text: "Unable to save user")
                return
        }

		try { 
        		user.save flush:true
        	} catch (Exception e) { //Fail for generic errors, TODO tighten up to include specific exceptions
        		render(status: 500, text: "Unable to save user")
        		return
        	}
		
        // Log newly created user into the system and return JWT token
		String token = login(user.email, user.password) 
        render(status: 201, text: token)
		return
    }

    @Transactional
    // Update details for a specific user
    // Endpoint: /user/{_id}
    // HTTP Method: PUT
    def update(User user) {
    //Uses parameter values to update use details
    		String token = request.getHeader('token')
    	    
        if (user == null) { // Fail if not user provided
            transactionStatus.setRollbackOnly()
            render status: NOT_FOUND
            return
        }

        if (user.hasErrors()) { // Fail if there are known issues with the User object
            transactionStatus.setRollbackOnly()
            respond user.errors, view:'edit'
            return
        }
        
        if( auth.isMe(user.id, token) || auth.isInRole("admin", token) ) { // Only User or Admins can update a User object
        		
			user.save flush:true
        		respond user, [status: OK, view:"show"]
        
	        return    		
    		} else { 
    			respond (status: 401, text: "Cannot update this user")
    	        return    		
    		}


    }

    @Transactional
    // Delete user from the database
    // Endpoint: /user/{_id}
    // HTTP Method: DELETE
    // TODO - Tighten security so only admins can delete
    def delete(User user) {

        if (user == null) {
            transactionStatus.setRollbackOnly()
            render status: NOT_FOUND
            return
        }

        user.delete flush:true

        render status: NO_CONTENT
    }

    // Login as user from the database
    // Endpoint: /login
    // HTTP Method: POST
    def login(String email, String password){

        def doc = User.collection
            .find(eq("email", email))
            .first()
        
        User u = doc as User
            
        if(u != null){
            if(u.password.equals(password)){
                String token = auth.login(u)
                                
                // render(status: 200, text: token)
                return token
                                
            } else {
                render (status: 401, text: "Authentication failed, incorrect username or password")
            }
        } else {
            render (status: 401, text: "Authentication failed, incorrect username or password")
        }

    }
}
