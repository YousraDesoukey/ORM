
package maintenance1;

import java.sql.*;
import java.util.ArrayList;

public class Maintenance1 {
  
   
   public static void main(String[] args) {
   Connection conn = null;
   Statement stmt = null;
  client d= new client("bank", "root", "", 2, "dsffa", "el sabesafs3", "01411111", false);
  try{
    d.connect();
    //d.createTable();
    d.save();
   
    d.connect();
 ArrayList l=d.getBy("client_name","yousra");
 for (int i=0;i<l.size();i++)
 {
     System.out.println(((client)l.get(i)).getClient_id());
     System.out.println(((client)l.get(i)).getClient_name());
     System.out.println(((client)l.get(i)).getBranch_name());
     System.out.println(((client)l.get(i)).getPhone_number());
     System.out.println(((client)l.get(i)).getBoolean());
 }
  }
  catch(Exception e)
  {
      e.printStackTrace();
  }
   }}
