package View;

import Model.Info;

import java.util.HashMap;

public class MainIN {
    public static void main(String[] args) {

        HashMap<String, Info> contactMap = new HashMap<>();
        Interfata ui  = new Interfata(contactMap);
        ui.startUI();

        Client client = new Client(5000, contactMap, ui);

        ui.client = client;

    }
}
