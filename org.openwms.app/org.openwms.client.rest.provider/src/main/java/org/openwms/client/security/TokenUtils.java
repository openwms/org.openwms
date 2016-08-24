/*
 * openwms.org, the Open Warehouse Management System.
 * Copyright (C) 2014 Heiko Scherrer
 *
 * This file is part of openwms.org.
 *
 * openwms.org is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as 
 * published by the Free Software Foundation; either version 2 of the
 * License, or (at your option) any later version.
 *
 * openwms.org is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this software. If not, write to the Free Software
 * Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA
 * 02110-1301 USA, or see the FSF site: http://www.fsf.org.
 */
package org.openwms.client.security;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.springframework.security.core.token.Sha512DigestUtils;
import org.springframework.security.core.userdetails.UserDetails;

/**
 * A TokenUtils is an utility class that creates and validates authentication
 * tokens from UserDetails.
 * 
 * @author <a href="mailto:scherrer@openwms.org">Heiko Scherrer</a>
 * @version $Revision: $
 * @since 0.1
 */
public class TokenUtils {

    private static final String MAGIC_KEY = "spicy";

    /**
     * Create a new authentication token from the given username with an
     * expiration period of one hour.
     * 
     * @param userDetails
     *            From where the username is taken
     * @return Token as colon concatenated String
     */
    public static String createToken(UserDetails userDetails) {
        long expires = System.currentTimeMillis() + 1000L * 60 * 60;
        StringBuilder tokenBuilder = new StringBuilder();
        tokenBuilder.append(userDetails.getUsername()).append(":");
        tokenBuilder.append(expires).append(":");
        tokenBuilder.append(TokenUtils.computeSignature(userDetails, expires));
        return tokenBuilder.toString();
    }

    /**
     * Concatenate credentials with <code>expires</code>, add a salt and hash
     * this String.
     * 
     * @param userDetails
     *            Where to take the credentials from
     * @param expires
     *            Expiration lease
     * @return The hashed String
     */
    public static String computeSignature(UserDetails userDetails, long expires) {
        StringBuilder signatureBuilder = new StringBuilder();
        signatureBuilder.append(userDetails.getUsername());
        signatureBuilder.append(":");
        signatureBuilder.append(expires);
        signatureBuilder.append(":");
        signatureBuilder.append(userDetails.getPassword());
        signatureBuilder.append(":");
        signatureBuilder.append(TokenUtils.MAGIC_KEY);
        MessageDigest digest;
        try {
            digest = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            throw new IllegalStateException("No MD5 algorithm found on platform!");
        }
        return new String(Sha512DigestUtils.shaHex(digest.digest(signatureBuilder.toString().getBytes())));
    }

    /**
     * Split token at colons and return the first part as username.
     * 
     * @param authenticationToken
     *            The token
     * @return The username or <code>null</code>
     */
    public static String getUserNameFromToken(String authenticationToken) {
        if (null == authenticationToken) {
            return null;
        }
        String[] parts = authenticationToken.split(":");
        return parts[0];
    }

    /**
     * Validate <code>authenticationToken</code> if expired and its content.
     * 
     * @param authenticationToken
     *            The token
     * @param userDetails
     *            To be used for content comparison
     * @return <code>true</code> is valid, otherwise <code>false</code>
     */
    public static boolean validateToken(String authenticationToken, UserDetails userDetails) {
        String[] parts = authenticationToken.split(":");
        long expires = Long.parseLong(parts[1]);
        String signature = parts[2];
        if (expires < System.currentTimeMillis()) {
            return false;
        }
        return signature.equals(TokenUtils.computeSignature(userDetails, expires));
    }
}
