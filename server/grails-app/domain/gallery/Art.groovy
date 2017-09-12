package gallery

import gallery.User
import com.mongodb.client.FindIterable
import static com.mongodb.client.model.Filters.*
import com.mongodb.client.model.Projections

class Art {

	String artworkName
	String artworkDescription
	String artistId
	String artworkLocation
	float artworkPrice
	String medium
	Boolean artAvailability

	User artist

    static constraints = {
    	artworkName blank:false
		artworkDescription blank:false
		artistId blank:false
		artworkLocation blank:false
		artworkPrice blank:false
		medium blank:false
		artAvailability blank:false
		artist nullable:true
    }

    //TODO - Investigate $projections, to prevent DB from sending back user password
    def setArtist(String artistId){
    	// Find an artist with corresponding artistId
    	int artistNumber = Integer.parseInt(artistId)
    	def doc = User.collection 
            .find(eq("_id", artistNumber))
            .first()

        this.artist = doc as User //Cast the return DB document as a User
    }
}
