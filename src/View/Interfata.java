package View;

import Model.Info;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Set;

public class Interfata {
    private GraphicsConfiguration gc;
    private JFrame frame;
    private JLabel firstName, lastName, email, phoneNumber, carrier, registrationDate, select;
    private JTextField firstNameField, lastNameField, emailField, phoneNumberField, carrierField, registrationDateField;
    private JPanel buttonPanel, fieldsPanel, comboxPanel;
    private JButton submit, add, delete, modify, clear;
    private HashMap<String, Info> contactMap;
    private JComboBox<String> cb;
    public ArrayList<JTextField> textFields;
    public Client client;
    public Interfata (HashMap<String, Info> contactMap){
        this.contactMap = contactMap;
        textFields = new ArrayList<>();
    }
    public void startUI() {
        this.frame = new JFrame(gc);
        this.frame.setTitle("Client");
        this.frame.setSize(1000, 1000);
        this.frame.setLocation(200, 200);
        this.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.frame.setResizable(false);
        createButtons();
        createElements();
        setActionListeners();
        addElements();
        setSettings();
    }
    private void setSettings (){
        fieldsPanel.setLayout(new BoxLayout(fieldsPanel, BoxLayout.PAGE_AXIS));
        buttonPanel.setLayout(new FlowLayout());
        select.setAlignmentX(Component.CENTER_ALIGNMENT);
        cb.setMaximumSize(cb.getPreferredSize());
        cb.setAlignmentX(Component.CENTER_ALIGNMENT);
        cb.setVisible(true);
        comboxPanel.setLayout(new BoxLayout(comboxPanel, BoxLayout.Y_AXIS));
        clear.setAlignmentX(Component.CENTER_ALIGNMENT);
        frame.setSize(500, 300);
        frame.setLayout(new GridLayout(2, 3));
        buttonPanel.setLayout(new GridLayout(4, 1));
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
    private void createButtons (){
        submit = new JButton("Trimite");
        add = new JButton("Contact nou");
        delete = new JButton("Sterge");
        modify = new JButton("Modifica");
        clear = new JButton("Clear");
    }
    private void createElements(){
        buttonPanel = new JPanel();
        fieldsPanel = new JPanel();
        comboxPanel = new JPanel();
        frame = new JFrame("Client");

        select = new JLabel("Selecteaza ID");
        firstName = new JLabel("Nume");
        lastName = new JLabel("Prenume");
        email = new JLabel("Email");
        phoneNumber = new JLabel("Telefon");
        carrier = new JLabel("Retea");
        registrationDate = new JLabel("Data");
        firstNameField = new JTextField(" ");
        textFields.add(firstNameField);
        lastNameField = new JTextField(" ");
        textFields.add(lastNameField);
        emailField = new JTextField(" ");
        textFields.add(emailField);
        phoneNumberField = new JTextField(" ");
        textFields.add(phoneNumberField);
        carrierField = new JTextField(" ");
        textFields.add(carrierField);
        registrationDateField = new JTextField(" ");
        textFields.add(registrationDateField);
        cb = new JComboBox<String>(contactMap.keySet().toArray(new String[0]));
    }

    private void addElements(){
        fieldsPanel.add(firstName);
        fieldsPanel.add(firstNameField);
        fieldsPanel.add(lastName);
        fieldsPanel.add(lastNameField);
        fieldsPanel.add(email);
        fieldsPanel.add(emailField);
        fieldsPanel.add(phoneNumber);
        fieldsPanel.add(phoneNumberField);
        fieldsPanel.add(carrier);
        fieldsPanel.add(carrierField);
        fieldsPanel.add(registrationDate);
        fieldsPanel.add(registrationDateField);
        comboxPanel.add(select);
        comboxPanel.add(cb);
        comboxPanel.add(clear);
        buttonPanel.add(submit);
        buttonPanel.add(add);
        buttonPanel.add(delete);
        buttonPanel.add(modify);
        frame.add(fieldsPanel);
        frame.add(buttonPanel);
        frame.add(comboxPanel);
    }
    public Info getSelectedContact() {
        Info contact = contactMap.get(cb.getSelectedItem());
        return contact;
    }
    private void setActionListeners (){
        submit.addActionListener(actionEvent-> {
            Info contact = getSelectedContact();
            Message message = new Message(contact, Action.VERIFY, (String) cb.getSelectedItem());
            client.sendObject(message);
        });

        add.addActionListener(actionEvent-> addContact());

        delete.addActionListener(actionEvent-> {
            Info contact = getSelectedContact();
            Message message = new Message(contact, Action.REMOVE, (String) cb.getSelectedItem());
            client.sendObject(message);
            deleteContact();
        });

        modify.addActionListener(actionEvent-> {
            Info contact = modifyContact();
            Message message =  new Message(contact, Action.MODIFY, (String) cb.getSelectedItem());
            client.sendObject(message);
        });

        clear.addActionListener(actionEvent -> clearFields());

        cb.addActionListener( actionEvent -> {
            String id = (String) cb.getSelectedItem();
            Info selectedContact = contactMap.get(id);
            if(selectedContact!=null) {
                fillFields(selectedContact);
            }
        });
    }
    private void deleteContact() {
        String id = (String) cb.getSelectedItem();
        contactMap.remove(id);
        updateComboBox();
    }
    private void clearFields(){
        for (JTextField element : textFields) {
            element.setText("");
        }
    }
    public void updateComboBox(){
        cb.removeAllItems();
        Set<String> keySet = contactMap.keySet();
        for (String id:keySet) {
            cb.addItem(id);
        }
    }
    private void addContact(){
        Info contact = new Info(firstNameField.getText(), lastNameField.getText(), emailField.getText(), phoneNumberField.getText(),carrierField.getText(), registrationDateField.getText() );
        String newId = getNewId();
        contactMap.put(newId, contact);
        updateComboBox();
        clearFields();
    }
    private String getNewId(){
        ArrayList<String>ids = new ArrayList<>();
        contactMap.keySet().forEach(key->ids.add(key));
        Collections.sort(ids, Collections.reverseOrder());
        if(ids.size()== 0){
            return "0";
        }
        String newId = String.valueOf(Integer.parseInt(ids.get(0))  + 1);
        return newId;
    }

    private Info modifyContact(){
        String id = (String) cb.getSelectedItem();
        Info existingcontact = contactMap.get(id);
        existingcontact.setFirstName(firstNameField.getText());
        existingcontact.setLastName(lastNameField.getText());
        existingcontact.setEmail(emailField.getText());
        existingcontact.setPhoneNumber(phoneNumberField.getText());
        existingcontact.setCarrierEnum(carrierField.getText());
        existingcontact.setDate(registrationDateField.getText());
        clearFields();
        return existingcontact;
    }
    private void fillFields(Info contact){
        this.firstNameField.setText(contact.getFirstName());
        this.lastNameField.setText(contact.getLastName());
        this.emailField.setText(contact.getEmail());
        this.phoneNumberField.setText(contact.getPhoneNumber());
        this.registrationDateField.setText(String.valueOf(contact.getDate()));
        this.carrierField.setText(contact.getCarrierEnum());
    }

}
