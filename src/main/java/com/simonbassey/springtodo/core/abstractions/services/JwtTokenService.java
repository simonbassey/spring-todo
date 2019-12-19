package com.simonbassey.springtodo.core.abstractions.services;

import java.util.Date;
import java.util.Map;

public interface JwtTokenService {
	String generateToken(String username, Map<String, Object> claims);
	String extractUserNameFromToken(String token);
	Date extractExpirationFromToken(String token);
	Boolean validateToken(String token, String username);
}
