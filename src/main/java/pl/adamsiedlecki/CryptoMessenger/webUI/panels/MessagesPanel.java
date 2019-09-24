package pl.adamsiedlecki.CryptoMessenger.webUI.panels;

import com.vaadin.server.FileResource;
import com.vaadin.shared.ui.ContentMode;
import com.vaadin.ui.*;
import pl.adamsiedlecki.CryptoMessenger.config.Config;
import pl.adamsiedlecki.CryptoMessenger.cryptography.AsymmetricCryptography;
import pl.adamsiedlecki.CryptoMessenger.cryptography.SHAUtility;
import pl.adamsiedlecki.CryptoMessenger.cryptography.SymmetricCryptography;
import pl.adamsiedlecki.CryptoMessenger.entity.Message;
import pl.adamsiedlecki.CryptoMessenger.fileOperations.ImageConverter;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.File;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

public class MessagesPanel extends Panel {

    private VerticalLayout root;
    private List<Panel> panels;
    private int count = 0;

    public MessagesPanel() {
        root = new VerticalLayout();
        root.setWidth(100, Unit.PERCENTAGE);
        panels = new ArrayList<>();

        this.setContent(root);
    }

    public void setMessages(List<Message> messages, String privateKey) {

        for (Panel p : panels) {
            root.removeComponent(p);
        }

        for (Message message : messages) {

            String decrypted = decrypt(message.getText(), privateKey);

            for (int i = 1; i < decrypted.length(); i++) {
                if (i % 140 == 0) {
                    String first = decrypted.substring(0, i);
                    String second = decrypted.substring(i);
                    decrypted = first + "\n" + second;
                }
            }

            Label label = new Label(decrypted, ContentMode.PREFORMATTED);
            label.setWidth(80, Unit.PERCENTAGE);

            HorizontalLayout horizontalLayout = new HorizontalLayout();
            horizontalLayout.addComponents(label, new Label(message.getDate().toString()));

            Panel messagePanel = new Panel(horizontalLayout);
            messagePanel.setHeight(100, Unit.PIXELS);

            if (message.getEncryptedImage() != null && !"".equals(message.getEncryptedImage())) {
                loadImage(message, privateKey, horizontalLayout, messagePanel);
            }

            panels.add(messagePanel);

            root.addComponent(messagePanel);
        }
    }

    private void loadImage(Message message, String privateKey, HorizontalLayout layout, Panel panel) {
        String temp = message.getText();

        String imageEncrypted = message.getEncryptedImage();
        byte[] byteImage = Base64.getDecoder().decode(imageEncrypted);

        temp = decrypt(temp, privateKey);
        String key = SHAUtility.getSHA(temp);
        String image = SymmetricCryptography.decrypt(byteImage, key);
        File f = ImageConverter.decodeBase64BinaryToFile(image, Config.getImagePath() + count + ".jpg");
        count++;
        Embedded embedded = new Embedded();
        embedded.setSource(new FileResource(f));
        layout.addComponent(embedded);
        float h = embedded.getHeight();
        layout.setHeight(h, embedded.getHeightUnits());
        panel.setHeight(h, embedded.getHeightUnits());
    }

    private String decrypt(String message, String privateKey) {

        try {
            if ("".equals(privateKey)) {
                Notification.show("Private key shouldn't be empty. Have a nice day!");
                return message;
            }

            AsymmetricCryptography ac = new AsymmetricCryptography();
            byte[] encrypted = Base64.getDecoder().decode(message);
            return ac.decrypt(encrypted, ac.getPrivateKey(privateKey));

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (NoSuchPaddingException e) {
            //e.printStackTrace();
        } catch (BadPaddingException e) {
            //e.printStackTrace();
        } catch (IllegalBlockSizeException e) {
            //e.printStackTrace();
        } catch (InvalidKeyException e) {
            //e.printStackTrace();
        }
        return message;
    }
}
