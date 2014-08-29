package models;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import lib.Model;

public class User extends Model{
  public User(){
    super();
    tableName = "users";
    id = "id";
  }
  
  public String getEmail(){ return this.get("email"); }
  
  public static User create(Map<String,String> params){
    User user = new User();
    Role role = Role.getByName("User");
    String username = params.get("username");
    String email = params.get("email");
    String password = params.get("password");
    String confirmPassword = params.get("confirm_password");
    String surname = params.get("surname");
    String name = params.get("name");
    if(username == null || username.trim().equals(""))
      user.addErrors("Il campo 'Username' non può essere vuoto!");
    if(email == null || email.trim().equals(""))
      user.addErrors("Il campo 'Email' non può essere vuoto!");
    if(password == null || password.equals(""))
      user.addErrors("Il campo 'Password' non può essere vuoto!");
    if(user.getErrors().length == 0 && !password.equals(confirmPassword))
      user.addErrors("Il valore del campo 'Conferma password' non coincide con quello del campo 'Password'!");
    if(user.getErrors().length == 0){
      user.set("username", username.trim());
      user.set("email", email.trim());
      user.set("password", password);
      user.set("surname", surname.trim());
      user.set("name", name.trim());
      user.set("role_id",role.get("id"));
      if(!user.save()){
        user.addErrors("Qualcosa è andato storto nel tentativo di creare l'utente!");
      }
    }
    return user;
  }
  
  public static User authenticate(String email, String password){
    ArrayList<Model> result = null;
    Map<String,String[]> params = new HashMap<>();
    params.put("select", new String[]{"*"});
    params.put("where", new String[]{"email = ?", "password = ?"});
    params.put("params", new String[]{email, password});
    result = new User().find(params);
    return (User)(result == null || result.isEmpty() ? null : result.get(0));
  }
  
  public Role getRole(){
    return (Role)(new Role().find(this.get("role_id")));
  }
}
