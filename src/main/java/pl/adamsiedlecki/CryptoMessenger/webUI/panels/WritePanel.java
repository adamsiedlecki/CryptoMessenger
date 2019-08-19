package pl.adamsiedlecki.CryptoMessenger.webUI.panels;

import com.vaadin.event.ShortcutAction;
import com.vaadin.server.FileResource;
import com.vaadin.server.Page;
import com.vaadin.ui.*;
import pl.adamsiedlecki.CryptoMessenger.config.Config;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;

public class WritePanel extends Panel {

    private TextArea messageArea;
    private TextArea publicKeyArea;
    private Button sendButton;
    private HorizontalLayout root;
    private Upload upload;
    private final Embedded image = new Embedded("Uploaded Image");
    private Panel uploadPanel;
    private String lastImagePath;

    public WritePanel(){

        messageArea = new TextArea("Message (max 117 characters)");
        publicKeyArea = new TextArea("Public Key ");

        sendButton = new Button("SEND");
        root = new HorizontalLayout();

        setComponentsSizeAndProperties();
        root.addComponents(messageArea,publicKeyArea,uploadPanel, sendButton);
        root.setComponentAlignment(messageArea, Alignment.MIDDLE_CENTER);
        root.setComponentAlignment(publicKeyArea, Alignment.MIDDLE_CENTER);
        root.setComponentAlignment(uploadPanel, Alignment.MIDDLE_CENTER);
        root.setComponentAlignment(sendButton, Alignment.MIDDLE_CENTER);

        setContent(root);
    }

    public Embedded getImage() {
        return image;
    }

    private void setComponentsSizeAndProperties(){
        root.setWidth(90,Unit.PERCENTAGE);
        messageArea.setSizeFull();
        messageArea.setRows(1);
        messageArea.setMaxLength(117);
        publicKeyArea.setRows(1);
        publicKeyArea.setSizeFull();
        sendButton.setWidth(80,Unit.PIXELS);
        sendButton.setClickShortcut(ShortcutAction.KeyCode.ENTER);
        setFileUpload();
    }

    private void setFileUpload(){
        image.setVisible(false);
        image.setWidth(100,Unit.PIXELS);
        image.setHeight(100,Unit.PIXELS);

        ImageUploader receiver = new ImageUploader();
        // Create the upload with a caption and set receiver later
        upload = new Upload("Upload Image Here", receiver);
        upload.setButtonCaption("Start Upload");
        upload.setAcceptMimeTypes("image");
        //upload.setImmediateMode(false);
        upload.addSucceededListener(receiver);

        // Put the components in a panel
        uploadPanel = new Panel("Image Uploader");
        Layout panelContent = new VerticalLayout();
        panelContent.addComponents(upload, image);
        uploadPanel.setContent(panelContent);
    }

    public Upload getUpload() { return upload; }

    public TextArea getMessageArea() {
        return messageArea;
    }

    public String getPublicKey(){
        return publicKeyArea.getValue();
    }

    public Button getSendButton() {
        return sendButton;
    }

    // Implement both receiver that saves upload in a file and
    // listener for successful upload

    public String getLastImagePath() {
        return lastImagePath;
    }

    class ImageUploader implements Upload.Receiver, Upload.SucceededListener {
        private File file;

        public OutputStream receiveUpload(String filename,
                                          String mimeType) {
            // Create upload stream
            FileOutputStream fos = null; // Stream to write to
            try {
                // Open the file for writing.
                file = new File(Config.getImagePath() + filename);
                lastImagePath = file.getAbsolutePath();
                fos = new FileOutputStream(file);
            } catch (final java.io.FileNotFoundException e) {
                new Notification("Could not open file",
                        e.getMessage(),
                        Notification.Type.ERROR_MESSAGE)
                        .show(Page.getCurrent());
                return null;
            }
            return fos; // Return the output stream to write to
        }

        public void uploadSucceeded(Upload.SucceededEvent event) {
            // Show the uploaded file in the image viewer
            image.setVisible(true);
            image.setSource(new FileResource(file));
            System.out.println(file.getAbsolutePath());
        }
    }
}





