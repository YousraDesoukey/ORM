
package maintenance1;

public class client extends DBEntity{
    public int client_id;
    public String client_name;
    public String branch_name;
    public String phone_number;
    public boolean b;

    public client(){
        
    }
    
    public client(String DB_Name, String User, String Pass, int client_id, String client_name, String branch_name, String phone_number, Boolean b)
    {
        super(DB_Name, User, Pass);
        this.client_id=client_id;
        this.client_name=client_name;
        this.branch_name=branch_name;
        this.phone_number=phone_number;
        this.b=b;

    }
    public boolean getBoolean()
    {
       
        return this.b;
    }
    public int getClient_id()
    {
       
        return this.client_id ;
    }
    public String getClient_name()
    {
       
        return this.client_name ;
    }
 
    public String getBranch_name()
    {
       
        return this.branch_name;
    }
     public String getPhone_number()
    {
       
        return this.phone_number;
    }
 
 
  
}
