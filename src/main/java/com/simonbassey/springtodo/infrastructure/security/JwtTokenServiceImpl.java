package com.simonbassey.springtodo.infrastructure.security;

import java.util.Date;
import java.util.Map;
import java.util.function.Function;

import org.springframework.stereotype.Service;

import com.simonbassey.springtodo.core.abstractions.services.JwtTokenService;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;


@Service
public class JwtTokenServiceImpl implements JwtTokenService {

	private final String _keySecret = "YOUR_SECRET_KEY"; // read from config and auto-wire
	@Override
	public String generateToken(String username, Map<String, Object> claims) {
		return createToken(username, claims);
	}
	
	private String createToken(String subjectOrUsername, Map<String, Object> claims) {
		return Jwts.builder().setClaims(claims)
				.setSubject(subjectOrUsername)
				.setIssuedAt(new Date(System.currentTimeMillis()))
				.setExpiration(new Date(System.currentTimeMillis()+1000*60*15))
				.signWith(SignatureAlgorithm.HS512, _keySecret).compact();
	}
	
	/*
	private Map<String, Object> getClaimsFromUserDetails(UserDetails userDetails) {
		var claimsList = new HashMap<String, Object>();
		claimsList.put("subject", userDetails.getUsername());
		List<String> roles = new ArrayList<>();
		userDetails.getAuthorities().forEach(g->roles.add(g.getAuthority()));
		claimsList.put("roles", roles);
		return claimsList;
	}
	
	*/
	private Claims extraClaims(String token) {
		return Jwts.parser().setSigningKey(_keySecret).parseClaimsJws(token).getBody();
	}
	
	private <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
		var claims = extraClaims(token);
		return claimsResolver.apply(claims);
	}

	@Override
	public String extractUserNameFromToken(String token) {
		return extractClaim(token, Claims::getSubject);
	}

	@Override
	public Date extractExpirationFromToken(String token) {
		return extractClaim(token, Claims::getExpiration);
	}

	private boolean isTokenExpired(String token) {
		return extractExpirationFromToken(token).before(new Date());
	}
	@Override
	public Boolean validateToken(String token, String username_) {
		var username = extractUserNameFromToken(token);
		return !(username.isBlank() || username_.isBlank()) && username.equals(username_) && !isTokenExpired(token);
	}

}
