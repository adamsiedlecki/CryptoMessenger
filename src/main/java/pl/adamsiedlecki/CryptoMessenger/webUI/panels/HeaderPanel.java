package pl.adamsiedlecki.CryptoMessenger.webUI.panels;

import com.vaadin.server.ExternalResource;
import com.vaadin.shared.ui.ContentMode;
import com.vaadin.ui.*;

public class HeaderPanel extends Panel {

    private VerticalLayout root;
    private TextArea roomIdArea;
    private TextArea privateKeyArea;
    private Button goToRoom;

    public HeaderPanel() {
        goToRoom = new Button("Go to room");
        setComponents();
        this.setContent(root);
    }

    public String getRoom() {
        return roomIdArea.getValue();
    }

    public String getPrivateKey() {
        return privateKeyArea.getValue();
    }

    private void setComponents() {
        Label main = new Label("Hi! I am CryptoMessenger! ");
        main.setStyleName("h1");
        Label description = new Label("I can give you an opportunity to be some kind of anonymous.", ContentMode.PREFORMATTED);
        Link generatorLink = new Link("Random online RSA generator -> ", new ExternalResource("https://www.devglan.com/online-tools/rsa-encryption-decryption"));
        generatorLink.setTargetName("_blank");
        roomIdArea = new TextArea();
        privateKeyArea = new TextArea();
        roomIdArea.setRows(1);
        privateKeyArea.setRows(1);
        privateKeyArea.setSizeFull();
        goToRoom.setSizeFull();

        HorizontalLayout horizontalLayout = new HorizontalLayout(roomIdArea, privateKeyArea, goToRoom);
        horizontalLayout.setCaption("Enter room id in the first box and private key in a second box :)");

        root = new VerticalLayout(main, description, generatorLink, horizontalLayout);

        root.setComponentAlignment(main, Alignment.MIDDLE_CENTER);
        root.setComponentAlignment(description, Alignment.MIDDLE_CENTER);
        root.setComponentAlignment(generatorLink, Alignment.MIDDLE_CENTER);
        root.setComponentAlignment(horizontalLayout, Alignment.MIDDLE_CENTER);
    }

    public Button getButton() {
        return goToRoom;
    }

}
