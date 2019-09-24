package pl.adamsiedlecki.CryptoMessenger;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import pl.adamsiedlecki.CryptoMessenger.fileOperations.FileCleaner;

@SpringBootApplication
public class CryptoMessengerApplication {

    // Designed to run on windows. Using Linux, problems with image paths may occur.
    public static void main(String[] args) {
        SpringApplication.run(CryptoMessengerApplication.class, args);

        FileCleaner.scheduleClean();
    }

}
