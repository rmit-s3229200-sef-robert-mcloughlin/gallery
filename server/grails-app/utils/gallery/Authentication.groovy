package gallery

import com.auth0.jwt.*
import com.auth0.jwt.algorithms.Algorithm
import com.auth0.jwt.exceptions.*
import com.auth0.jwt.interfaces.*

class Authentication {
	Algorithm alg = Algorithm.HMAC256("secret");
	public Map<String, Claim> decodeToken(String token){
		Map<String, Claim> claims 
		try {
		    DecodedJWT jwt = JWT.decode(token);
		    claims = jwt.getClaims();    //Key is the Claim name
		} catch (JWTDecodeException exception){
		    //Invalid token
		    println("failed to decode JWT")
		}
		return claims
	}
	
	public boolean isInRole(String role, String token) {
		Claim claim = decodeToken(token).get("role")
		println(claim.asString())
		
		if(claim.asString().equals(role)){
			return true
		} else {
			return false
		}
	}
	
	public boolean isMe(long userId, String token){
		if(!verifyToken(token)){ 
			return false
		} else { 
			Claim claim = decodeToken(token).get("id")
			
			if( claim.asLong() == userId ){
				return true
			} else { 
				return false
			}
		}
		
	}

	public String login(User u) {
		String token = ""
		println(u)

		try {
			Algorithm algorithm = Algorithm.HMAC256("secret");
			token = JWT.create()
					.withClaim("id", u.id)
					.withClaim("email", u.email)
					.withClaim("role", u.role)
					.withIssuer("galleryApp")
					.sign(algorithm);
		} catch (JWTCreationException exception){
			//Invalid Signing configuration / Couldn't convert Claims.
		}

		return token
	}
	
	//TODO - implement token verification
	public Boolean verifyToken(String token) {
		return true
	}
}