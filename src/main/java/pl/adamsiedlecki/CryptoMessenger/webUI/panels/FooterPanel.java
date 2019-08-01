package pl.adamsiedlecki.CryptoMessenger.webUI.panels;


import com.vaadin.ui.*;

public class FooterPanel extends Panel {

    private VerticalLayout root;

    public FooterPanel(){
        root= new VerticalLayout();
        Label author = new Label("CryptoMessenger is a Java app designed by Adam Siedlecki. I hope it will be useful for someone. ");
        Label greetings = new Label(" Have a nice day!");
        Label btcAddress = new Label("BTC donation address: "+" 1LuR1RcCuXCgfjeUKEpaNjusmEaembnUXT");

        root.addComponents(author,greetings,btcAddress);

        root.setComponentAlignment(author,Alignment.MIDDLE_CENTER);
        root.setComponentAlignment(greetings,Alignment.MIDDLE_CENTER);
        root.setComponentAlignment(btcAddress,Alignment.MIDDLE_CENTER);
        setContent(root);
    }

}
