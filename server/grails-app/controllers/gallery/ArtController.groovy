package gallery

import static org.springframework.http.HttpStatus.*
import grails.transaction.Transactional
import grails.converters.JSON

import gallery.Art
import gallery.Authentication

@Transactional(readOnly = true)
class ArtController {

    Authentication auth = new Authentication()
    static responseFormats = ['json', 'xml']
    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    // Display details for all art objects
    // Endpoint: /art
    // HTTP Method: GET
    // TODO - Tighten security to only allow admins, allow paginated results
    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        respond Art.list(params), model:[artCount: Art.count()]
    }


    // Display details for a specific artwork
    // Endpoint: /art/{_id}
    // HTTP Method: GET
    def show(Art art) { 
        art.setArtist(art.artistId) //TODO - Get rid of this and instead call the /get for Artist endpoint
        def artIncludingArtist = [:] // Create a new Map that will contain all of the art, and artist information

        art.getProperties().each { entry ->
            if(entry.key != "dbo") {
                artIncludingArtist[entry.key] = entry.value
            }
        }
        
        render (artIncludingArtist as JSON)
    }

    @Transactional
    // Saves a new art to the database, only available to Admins
    // Endpoint: /art
    // HTTTP Method: POST
    def save(Art art) {
        String token = request.getHeader('token')

        if(token == null){ //Fail if no token provided
            transactionStatus.setRollbackOnly()
            render(status: 401, text: "Requires admin to insert new art")
            return            
        }

        if (art == null) { //Fail if no art provided
            transactionStatus.setRollbackOnly()
            render status: NOT_FOUND
            return
        }

        if (art.hasErrors()) { //Fail if errors creating art object
            transactionStatus.setRollbackOnly()
            respond art.errors, view:'create'
            return
        }

        if( auth.isInRole("admin", token) ) {
            try { //Write new artwork to database
                    art.save flush:true
                } catch (Exception e) { //Fail for generic errors, TODO tighten up to include specific exceptions
                    render(status: 500, text: e)
                    return
                }
        } else {
            transactionStatus.setRollbackOnly()
            render(status: 401, text: "Requires admin to insert new art")
            return
        }
        
        render(status: 201, text: "Artwork has been added")
        return
    }

    @Transactional
    // Update details for a specific art
    // Endpoint: /art/{_id}
    // HTTP Method: PUT
    // TODO - Tighten up to only allow admins to update Arts
    def update(Art art) {
        if (art == null) {
            transactionStatus.setRollbackOnly()
            render status: NOT_FOUND
            return
        }

        if (art.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond art.errors, view:'edit'
            return
        }

        art.save flush:true

        respond art, [status: OK, view:"show"]
    }

    @Transactional
    // Delete art from the database
    // Endpoint: /art/{_id}
    // HTTP Method: DELETE
    // TODO - Tighten security so only admins can delete
    def delete(Art art) {

        if (art == null) {
            transactionStatus.setRollbackOnly()
            render status: NOT_FOUND
            return
        }

        art.delete flush:true

        render status: NO_CONTENT
    }
}
