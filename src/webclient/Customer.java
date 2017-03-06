/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package resttest;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author dns1
 */
@XmlRootElement
public class Customer implements Serializable{
    private Long id;
    private String firstName;
    private String middleName;
    private String lastName;
    private List<String> phones = new ArrayList<String>();
    
    public Customer(){
    }
    
    public Customer(Long id
            , String firstName
            , String middleInitial
            , String lastName
            , String...phones){
        this.id = id;
        this.firstName = firstName;
        this.middleName = middleInitial;
        this.lastName = lastName;
        this.phones.addAll(Arrays.asList(phones));
        
        
        
    }
    
    public String getFirstName(){
        return firstName;
    }
    
    public void setFirstName(String firstName){
        this.firstName = firstName;
    }
    
    public Long getId(){
        return id;
    }
    
    public void setId(Long id){
        this.id = id;
    }
    
    public String getLastName(){
        return lastName;
    }
    
    public void setLastName(String lastName){
        this.lastName = lastName;
    }
    
    public String getMiddleName(){
        return middleName;
    }
    
    public void setMiddleName(String middleName){
        this.middleName = middleName;
    }
    
    
    
    public List<String> getPhones(){
        return phones;
    }
    
    public void setPhones(List<String> phones){
        this.phones = phones;
    }
    
    public String toString(){
        StringBuilder sb = new StringBuilder();
        sb.append("id = "+getId());
        sb.append("\nfirstName = "+getFirstName());
        sb.append("\nmiddleName = "+getMiddleName());
        sb.append("\nlastName = "+getLastName());
        
        
        for(String ph:phones){
            sb.append("\n    - "+ph);
        }
        
        return sb.toString();
    
    }
}
