package pl.adamsiedlecki.CryptoMessenger;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.io.FileUtils;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import pl.adamsiedlecki.CryptoMessenger.cryptography.AsymmetricCryptography;
import pl.adamsiedlecki.CryptoMessenger.cryptography.SymmetricCryptography;
import pl.adamsiedlecki.CryptoMessenger.fileOperations.FileCleaner;
import pl.adamsiedlecki.CryptoMessenger.fileOperations.ImageConverter;

import javax.crypto.*;
import java.io.File;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.stream.Stream;

@SpringBootApplication
public class CryptoMessengerApplication {

	public static void main(String[] args) {
		SpringApplication.run(CryptoMessengerApplication.class, args);

		FileCleaner.scheduleClean();
	}

}
