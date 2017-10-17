package gallery
import gallery.Art

class ShoppingCart {
	 int cartID;
	 int artID;
	 int totalQuantity;
	

    static constraints = { 
    	cartID blank:false
		artID blank:false
		totalQuantity blank:false
    }	
    
}

