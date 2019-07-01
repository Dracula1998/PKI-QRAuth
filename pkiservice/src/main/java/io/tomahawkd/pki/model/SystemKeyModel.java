package io.tomahawkd.pki.model;

import java.security.KeyPair;
import java.sql.Timestamp;
import java.util.Base64;

public class SystemKeyModel {

	private int systemId;
	private int systemUserId;
	private String systemApi;
	private Timestamp registerDate;
	private String publicKey;
	private String privateKey;

	public SystemKeyModel(int systemUserId, String systemApi, KeyPair kp) {
		this.systemApi = systemApi;
		this.systemUserId = systemUserId;
		this.publicKey = Base64.getEncoder().encodeToString(kp.getPublic().getEncoded());
		this.privateKey = Base64.getEncoder().encodeToString(kp.getPrivate().getEncoded());
	}

	public int getSystemId() {
		return systemId;
	}

	public int getSystemUserId() {
		return systemUserId;
	}

	public String getSystemApi() {
		return systemApi;
	}

	public Timestamp getRegisterDate() {
		return registerDate;
	}

	public String getPublicKey() {
		return publicKey;
	}

	public String getPrivateKey() {
		return privateKey;
	}

	@Override
	public String toString() {
		return "SystemKeyModel{" +
				"systemApi='" + systemApi + '\'' +
				"registerDate=" + registerDate +
				'}';
	}
}
