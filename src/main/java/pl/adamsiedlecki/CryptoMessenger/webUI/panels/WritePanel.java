package pl.adamsiedlecki.CryptoMessenger.webUI.panels;

import com.vaadin.event.ShortcutAction;
import com.vaadin.server.FileResource;
import com.vaadin.server.Page;
import com.vaadin.ui.*;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;



public class WritePanel extends Panel {

    private TextArea messageArea;
    private TextArea publicKeyArea;
    private Button sendButton;
    private HorizontalLayout root;
    private Upload upload;
    ImageUploader imageUploader;
    final Embedded image = new Embedded("Uploaded Image");


    public WritePanel(){

        messageArea = new TextArea("Message (max 117 characters)");
        publicKeyArea = new TextArea("Public Key ");
        imageUploader = new ImageUploader();



        image.setVisible(false);
        ImageUploader receiver = new ImageUploader();
// Create the upload with a caption and set receiver later
        Upload upload = new Upload("Upload Image Here", receiver);
        upload.setButtonCaption("Start Upload");
        upload.addSucceededListener(receiver);

// Put the components in a panel
        Panel panel = new Panel("Cool Image Storage");
        Layout panelContent = new VerticalLayout();
        panelContent.addComponents(upload, image);
        panel.setContent(panelContent);
       // upload = new Upload("UPLOAD",imageUploader);
        //upload.setButtonCaption("Upload file");
        //upload.addSucceededListener(imageUploader);

        sendButton = new Button("SEND");
        root = new HorizontalLayout();

        setComponentsSizeAndProperties();
        root.addComponents(messageArea,publicKeyArea,sendButton,panel);
        root.setComponentAlignment(messageArea, Alignment.MIDDLE_CENTER);
        root.setComponentAlignment(publicKeyArea, Alignment.MIDDLE_CENTER);
        root.setComponentAlignment(panel, Alignment.MIDDLE_CENTER);
        root.setComponentAlignment(sendButton, Alignment.MIDDLE_CENTER);

        setContent(root);
    }

    private void setComponentsSizeAndProperties(){
        root.setWidth(90,Unit.PERCENTAGE);
        //messageArea.setWidth(55,Unit.PERCENTAGE);
        messageArea.setSizeFull();
        messageArea.setRows(1);
        messageArea.setMaxLength(117);

        //publicKeyArea.setWidth(55,Unit.PERCENTAGE);
        publicKeyArea.setRows(1);
        publicKeyArea.setSizeFull();

        //sendButton.setHeight(publicKeyArea.getHeight()*4,publicKeyArea.getHeightUnits());
        sendButton.setWidth(80,Unit.PIXELS);
        sendButton.setClickShortcut(ShortcutAction.KeyCode.ENTER);

        root.setHeight(100,Unit.PIXELS);


    }

    public Upload getUpload() { return upload; }

    public String getMessage() {
        return messageArea.getValue();
    }

    public String getPublicKey(){
        return publicKeyArea.getValue();
    }

    public Button getSendButton() {
        return sendButton;
    }

    ///

    // Implement both receiver that saves upload in a file and
// listener for successful upload
    class ImageUploader implements Upload.Receiver, Upload.SucceededListener {
        public File file;

        public OutputStream receiveUpload(String filename,
                                          String mimeType) {
            // Create upload stream
            FileOutputStream fos = null; // Stream to write to
            try {
                // Open the file for writing.
                file = new File("/tmp/uploads/" + filename);
                fos = new FileOutputStream(file);
            } catch (final java.io.FileNotFoundException e) {
                new Notification("Could not open file<br/>",
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
        }
    }

}





