package pl.adamsiedlecki.CryptoMessenger.webUI;

import com.sun.org.apache.xerces.internal.impl.dv.util.Base64;
import com.vaadin.server.VaadinRequest;
import com.vaadin.spring.annotation.SpringUI;
import com.vaadin.ui.Notification;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import pl.adamsiedlecki.CryptoMessenger.cryptography.AsymmetricCryptography;
import pl.adamsiedlecki.CryptoMessenger.cryptography.SHAUtility;
import pl.adamsiedlecki.CryptoMessenger.cryptography.SymmetricCryptography;
import pl.adamsiedlecki.CryptoMessenger.dao.MessageDAO;
import pl.adamsiedlecki.CryptoMessenger.entity.Message;
import pl.adamsiedlecki.CryptoMessenger.fileOperations.ImageConverter;
import pl.adamsiedlecki.CryptoMessenger.webUI.panels.FooterPanel;
import pl.adamsiedlecki.CryptoMessenger.webUI.panels.HeaderPanel;
import pl.adamsiedlecki.CryptoMessenger.webUI.panels.MessagesPanel;
import pl.adamsiedlecki.CryptoMessenger.webUI.panels.WritePanel;
import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.File;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.sql.Time;
import java.time.Instant;
import java.util.List;

@SpringUI
public class WebUI extends UI {

    private MessageDAO messageDAO;
    private VerticalLayout root;
    private HeaderPanel headerPanel;
    private WritePanel writePanel;
    private MessagesPanel messagesPanel;
    private FooterPanel footerPanel;

    public WebUI( MessageDAO messageDAO) {
        this.messageDAO = messageDAO;
        this.headerPanel = new HeaderPanel();
        this.messagesPanel = new MessagesPanel();
        this.writePanel = new WritePanel();
        this.footerPanel = new FooterPanel();

       setButtons();

        root = new VerticalLayout();
    }

    private void setButtons(){

        headerPanel.getButton().addClickListener(x ->messagesPanel.setMessages(getMessages(),headerPanel.getPrivateKey()));

        writePanel.getSendButton().addClickListener(x ->{
            if("".equals(writePanel.getMessageArea())||writePanel.getUpload().isUploading()){
                Notification.show("You cannot upload image without message!");
                return;
            }
            if("".equals(writePanel.getMessageArea())){
                Notification.show("Message cannot be empty. Have a good day!");
                return;
            }
            if("".equals(writePanel.getPublicKey())){
                Notification.show("Public key cannot be empty. Have a good day!");
                return;
            }
            if(writePanel.getPublicKey().length()!=216&&writePanel.getPublicKey().length()!=128&&writePanel.getPublicKey().length()!=392&&writePanel.getPublicKey().length()!=736){
                Notification.show("Public key should be 515 bit, or 1024, or 2048, or 4096 bit! Have a nice day!");
                return;
            }

            AsymmetricCryptography ac = new AsymmetricCryptography();
            byte[] encrypted = new byte[0];
            try {
                encrypted = ac.encrypt(writePanel.getMessageArea().getValue(),writePanel.getPublicKey());
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
            String encryptedString = Base64.encode(encrypted);
            //System.out.println("Encrypte String: "+encryptedString)

            String imageEncrypted = "";

            if(writePanel.getImage().isVisible()){
                imageEncrypted = encryptImage();
            }

            //System.out.println(imageEncrypted);

            messageDAO.saveAndFlush(new Message(encryptedString, Time.from(Instant.now()),headerPanel.getRoom(),imageEncrypted));
            messagesPanel.setMessages(getMessages(),headerPanel.getPrivateKey());

            cleanFields();
        }
        );

    }

    private String encryptImage(){
       // if(!writePanel.getUpload().isUploading()) {
            //if(writePanel.getUpload().getUploadSize()>0){

                String path = writePanel.getLastImagePath();
//                if(path==null){
//                    return "";
//                }
                File file = new File(path); // here i need to encrypt the image
                String symmetricKey = SHAUtility.getSHA(writePanel.getMessageArea().getValue());
                String imageBytes = null;
                try {
                    imageBytes = ImageConverter.encodeFileToBase64Binary(file);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                byte[] imageEncrypted = SymmetricCryptography.encrypt(imageBytes,symmetricKey);

                return Base64.encode(imageEncrypted);
            //}
        //}
        //return "";
    }

    private void cleanFields(){
        //writePanel.getUpload();
        writePanel.getMessageArea().clear();
        writePanel.getImage().setVisible(false);
        footerPanel.focus();  //maybe works
    }

    @Override
    protected void init(VaadinRequest request) {

        root.addComponent(headerPanel);
        root.addComponent(messagesPanel);
        root.addComponent(writePanel);
        root.addComponent(footerPanel);

        setContent(root);

    }

    private List<Message> getMessages(){
        List<Message> messages = messageDAO.getAllByRoomIs(headerPanel.getRoom());
        return messages;
    }


}
