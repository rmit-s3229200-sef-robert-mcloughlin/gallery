package gallery

import com.auth0.jwt.*
import com.auth0.jwt.algorithms.Algorithm
import com.auth0.jwt.exceptions.*
import com.auth0.jwt.interfaces.*

class Authentication {
	Algorithm alg = Algorithm.HMAC256("secret");

	public boolean isAdmin(String token){
		Claim claim;
		try {
		    DecodedJWT jwt = JWT.decode(token);
		    Map<String, Claim> claims = jwt.getClaims();    //Key is the Claim name
			claim = claims.get("role");
		} catch (JWTDecodeException exception){
		    //Invalid token
		}
		
		if(claim.asString().equals("admin")){
			return true
		} else {
			return false
		}
	}

	public String login(User u) {
		String token = ""

		try {
			Algorithm algorithm = Algorithm.HMAC256("secret");
			token = JWT.create()
					.withClaim("username", u.username)
					.withClaim("password", u.password)
					.withClaim("role", u.role)
					.withIssuer("auth0")
					.sign(algorithm);
		} catch (JWTCreationException exception){
			//Invalid Signing configuration / Couldn't convert Claims.
		}

		return token
	}

	public String verifyToken(String token){
		println(token)
		return token
	}
}