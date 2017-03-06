/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package webclient;



import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.UniformInterface;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;
import com.sun.jersey.core.util.MultivaluedMapImpl;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;
import javax.swing.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;

import resttest.Customer;
/**
 *
 * @author dns1
 */
public class WebClient extends JFrame{

    private WebResource baseUriWebResource;
    private WebResource webResource;
    private Client client;
    private static String BASE_URI;// = "http://localhost:8080/PhoneBookService/resources/";
    
   
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
            
        
        WebClient client = new WebClient();
        
        client.setTitle(client.getClass().getSimpleName());
        client.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        client.setSize(400,500);
        
        
        client.setLayout(new FlowLayout());
        
        JTextField  tfBaseUri  = new JTextField(30)
                   ,tfId       = new JTextField(20)
                   ,tfFullName = new JTextField(20)
                   ,tfName     = new JTextField(20)
                   ,tfSurName  = new JTextField(20);
        
        tfBaseUri.setText("http://localhost:8080/PhoneBookService/resources/");
        tfId.setText("0"); 
        tfFullName.setText("fullname");
        tfName.setText("name");
        tfSurName.setText("surname");
        
        JTextArea taPhones = new JTextArea(7,30)
                 ,taOutput = new JTextArea(7,30);
                      
        taPhones.setText("656565 \n353535 \n393939 \n");
        
        client.add(new JLabel("URI"));
        client.add(tfBaseUri);
        client.add(tfId);
        client.add(tfFullName);
        client.add(tfName);
        client.add(tfSurName);
        client.add(new JScrollPane(taPhones));
        
        
        ActionListener al = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                BASE_URI = tfBaseUri.getText();
                client.initWebResource();
                
                PhoneBookItem item = new PhoneBookItem(Integer.parseInt(tfId.getText())
                                                                        ,tfFullName.getText()
                                                                        ,tfName.getText()
                                                                        ,tfSurName.getText()
                                                                        ,taPhones.getText().split("\n"));
                
                JButton b = (JButton)ae.getSource();
                 
                      if(b.getName().equals("Insert")){
                        client.insertCustomer(item);
                }else if(b.getName().equals("Update")){
                        client.updateCustomer(item);
                }else if(b.getName().equals("Delete")){
                        client.deleteCustomer(item.getId());
                }else if(b.getName().equals("Select")){
                        client.getCustomer(item,taOutput);
                }
            }
        };
        
        JButton[] buttons = new JButton[]{
                  new JButton("Insert")
                , new JButton("Update")
                , new JButton("Delete")
                , new JButton("Select")
        };
        for(JButton b:buttons) {
            b.setName(b.getText()); 
            b.addActionListener(al);
            client.add(b);
        }
        
        client.add(new JScrollPane(taOutput));
        
        client.setVisible(true);
        
        
        //main.deleteCustomer(9);
        //main.insertCustomer(new PhoneBookItem(0,"Poval","QAlena","Sergeevna","+789456132","+456123789"));
        //main.insertCustomer(new PhoneBookItem(0,"Roval","WElena","Andreevna","+789456132","+456123789"));
        //main.insertCustomer(new PhoneBookItem(0,"Soval","OPelena","Sergeevna","+789456132","+456123789","+998788987"));
        //main.updateCustomer(new PhoneBookItem(5,"Xoval","ACalena","Sergeevna","+789456132","+456123789","88898987894564"));
        //main.deleteCustomer(5);
        //main.initWebResource();
        //main.insertCustomer();
        //main.updateCustomer();
        //main.initWebResource();
        //main.updateCustomer();
        //main.getCustomer(new PhoneBookItem(0,"","",""));
        
        
        
    }
    
    private void initWebResource(){
        ClientConfig config = new DefaultClientConfig();
        client = Client.create(config);
        baseUriWebResource = client.resource(BASE_URI);
        webResource = baseUriWebResource.path("phonebookitem");
    }
    
   
    
    public void getCustomer(PhoneBookItem item,JTextArea taOutput){
         
        MultivaluedMap map = new MultivaluedMapImpl();
        map.add("id", ""+item.getId());
        map.add("fullname", item.getFullname());
        map.add("name", item.getName());
        map.add("surname", item.getSurname());
        
        
        UniformInterface uniformInterface = webResource.type(MediaType.TEXT_XML);
        PhoneBookItem[] customer = webResource.queryParams(map).get( PhoneBookItem[].class);
        
        StringBuilder sb = new StringBuilder();
        for(PhoneBookItem _item:customer){
            sb.append(_item);
        }
        taOutput.setText(sb.toString());
        
        
        System.out.println(Arrays.toString(customer));
        
    
    }
    
    public void insertCustomer(PhoneBookItem item){
        //PhoneBookItem customer = new PhoneBookItem(28,"Tamara","Alll","Graystone","+9999","+888");
        UniformInterface uniformInterface = webResource.type(MediaType.TEXT_XML);
        uniformInterface.put(item);
    }
    
    public void updateCustomer(PhoneBookItem item){
        //PhoneBookItem customer = new PhoneBookItem(23,"Tamara","Alll","Graystone","+9999","+888");
        UniformInterface uniformInterface = webResource.type(MediaType.TEXT_XML);
        uniformInterface.post(item);
    }
    
    
    public void deleteCustomer(int id){
            //PhoneBookItem customer = new PhoneBookItem(1);
            UniformInterface uniformInterface = webResource.type(MediaType.TEXT_XML);
            webResource.queryParam("id",""+ id).delete();
    }
 
    
    
}
