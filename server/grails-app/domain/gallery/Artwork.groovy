package gallery

class Artwork {
    String name
    String description

    static constraints = {
    	name blank: false
    	description blank: false
    }
}
