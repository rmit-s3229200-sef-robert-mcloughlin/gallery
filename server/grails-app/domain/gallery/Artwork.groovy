package gallery

import gallery.User

class Artwork {

	String _id
	String artworkName
	String artworkDescription
	String artistId
	String artworkLocation
	float artworkPrice
	String medium
	Boolean artAvailability

	private User artist

    static constraints = {
    	artworkName blank:false
		artworkDescription blank:false
		artistId blank:false
		artworkLocation blank:false
		artworkPrice blank:false
		medium blank:false
		artAvailability blank:false
    }
    
}
