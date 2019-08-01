package pl.adamsiedlecki.CryptoMessenger;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import pl.adamsiedlecki.CryptoMessenger.cryptography.AsymmetricCryptography;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

@SpringBootApplication
public class CryptoMessengerApplication {

	public static void main(String[] args) {
		SpringApplication.run(CryptoMessengerApplication.class, args);

		AsymmetricCryptography ac = new AsymmetricCryptography();
		try {
			byte[] enc = ac.encrypt("message","MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCjSquFvwOFcxIUIJm2BoKZt43BOcqzWwoRMkHf4YD8di/mH1aT3mIDDKw8MjcRRqMcTSXd5hFtLDLkILRr4TYjEV6RxCDp1/cNJEu5K6WAjSDBcQ6tz8j423JZfQRn0fUqc1hcPXpOs5UWh9A5CkPnia8H0PzNPht1/Kru26a1mwIDAQAB");

			System.out.println(ac.decrypt(enc,ac.getPrivateKey("MIICdwIBADANBgkqhkiG9w0BAQEFAASCAmEwggJdAgEAAoGBAKNKq4W/A4VzEhQgmbYGgpm3jcE5yrNbChEyQd/hgPx2L+YfVpPeYgMMrDwyNxFGoxxNJd3mEW0sMuQgtGvhNiMRXpHEIOnX9w0kS7krpYCNIMFxDq3PyPjbcll9BGfR9SpzWFw9ek6zlRaH0DkKQ+eJrwfQ/M0+G3X8qu7bprWbAgMBAAECgYAXtwp+HCW4xeOERM/OHdft1zihLXGA5uHpaXEjwK5o/Aks1LRQOWXmp3qEK47P1YBu5c1wEq/JOTgS5aUOXeDsrpf1XyOP/PfLvN1MDAnuiOOE1BCzAhj36EB06IIQj0Y64WbpvFH77XzLiriQ2vhmXnK+TNTU0e96O/W0llTmEQJBAPNrAh8iK97CC/U2cGfLawvTlfU0SbeJ2GRu/Zg2n/PYSSJWFBJMFWSLwdpe9HWDaqERv5fQubS1NkDoJGxvn+UCQQCru2cAXSwE8I+pz32NvKTXQsui3Zb2Frpw3va8wXy8oefV1DRkvxTMMWD0jbxWGnKA+sD/5/vmqwufkzkjmKd/AkAZFQCt9pZwqKwUw1xHxbUtDctY7d9esyyCBNHKum428SVwDVCaJg7aQnBiMQCdj0IQgMSuPwqzrSx++ayvI71lAkEAoudqldXMZAMuxI6nM0S3udsLALvi8B4vTdKzi+qfQPD00soxyT/ZorlUl6e7+JGt/NvhKOAO/IlUxVMK6NFuUQJBAKjBkkhqijD6jjU9ZgsenkGPxEa3oY57SNsWFb5iUrgRDNqeSc5wGxKIBX8S7KDq2oKVQqJW7h3IxmjRpPjvZIk=")));
		} catch (BadPaddingException e) {
			e.printStackTrace();
		} catch (IllegalBlockSizeException e) {
			e.printStackTrace();
		} catch (InvalidKeyException e) {
			e.printStackTrace();
		} catch (NoSuchPaddingException e) {
			e.printStackTrace();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}


	}

}
