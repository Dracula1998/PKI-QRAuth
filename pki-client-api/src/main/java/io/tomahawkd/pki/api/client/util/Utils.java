package io.tomahawkd.pki.api.client.util;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;
import io.tomahawkd.pki.api.client.exceptions.*;


import java.io.IOException;
import java.security.PublicKey;
import java.util.Arrays;
import java.util.Base64;
import java.util.Map;

public class Utils {

	public static String base64Encode(byte[] data) {
		return Base64.getEncoder().encodeToString(data);
	}

	public static byte[] base64Decode(String data) {
		try {
			return Base64.getDecoder().decode(data);
		} catch (Exception e) {
			throw new Base64EncodeException("Illegal Base64 Encode");
		}
	}

	public static Map<String, String> wrapMapFromJson(String json, String... params)
			throws ParamNotFoundException, MalformedJsonException {

		try {
			Map<String, String> map = new Gson().fromJson(json, new TypeToken<Map<String, String>>() {
			}.getType());

			for (String param : params) {
				if (!map.containsKey(param)) throw new ParamNotFoundException("Json key not exist: " + json);
			}

			return map;
		} catch (JsonSyntaxException e) {
			throw new MalformedJsonException("Malformed Json: " + json);
		} catch (NullPointerException e) {
			throw new MalformedJsonException("Cannot read json value: " + json);
		}
	}

	public static String responseChallenge(String t, PublicKey key) throws IOException, CipherErrorException {
		return Utils.base64Encode(
				SecurityFunctions.encryptAsymmetric(key,
						String.valueOf(
								Integer.parseInt(
										Arrays.toString(
												SecurityFunctions.decryptUsingAuthenticateServerPrivateKey(
														Utils.base64Decode(t)))) + 1).getBytes()));
	}
}
