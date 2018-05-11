package maintenance1;

import java.lang.reflect.*;
import java.sql.*;
import java.util.ArrayList;
public class DBEntity {
     
   static  String JDBC_DRIVER = "com.mysql.jdbc.Driver";  
   static String DB_URL;
   static  String USER;
   static  String PASS;
   String className ;
   Field[] fields;
   static Connection conn;
   static Statement stmt;
    
   public DBEntity(){
       
   }
   
   public DBEntity(String DB_Name, String User, String Pass)
   {
       this.DB_URL="jdbc:mysql://127.0.0.1:3306/"+DB_Name;
       this.USER=User;
       this.PASS=Pass;
       this.className= this.getClass().getSimpleName();
       fields= this.getClass().getFields();
   }
   
   
   public void connect()  throws SQLException {
       
       try{
        Class.forName("com.mysql.jdbc.Driver");
       }
       catch(Exception e)
       {
           e.printStackTrace();
       }

      System.out.println("Connecting to database...");
      conn = DriverManager.getConnection(DB_URL,USER,PASS);

      
      System.out.println("Creating statement...");
      stmt = conn.createStatement();
 
       
   }
   
   
    public void save()  throws SQLException{
    Class<client> cl = client.class;
    String type, name;
    String value;
    Number value2;
    Boolean value3;
    String ins;
      
      
    ins= "INSERT INTO "+ className +" VALUES (?";
    for (int j=1;j<fields.length;j++)
      {
          ins=ins+",?";
      }
      ins=ins+")";
      System.out.println(ins);        
     PreparedStatement ps = conn.prepareStatement(ins);   
     int i=0;
     for (Field field : fields) {
        type = field.getType().getSimpleName();
        name = field.getName();
             try{
                 Field f = cl.getDeclaredField(name);
                 if (type.equals("String"))
                 {
                      
                       value = (String) f.get(this);
                       ps.setString(i+1,value);
                       System.out.println(value);
                 }
                 else if (type.equals("int"))
                 {
                       value2 =  f.getInt(this);
                       ps.setInt(i+1,(int) value2);
                       System.out.println(value2);
                 }
                    else if (type.equals("float"))
                 {
                       value2 =  f.getFloat(this);
                       ps.setFloat(i+1, (float) value2);
                       System.out.println(value2);
                 }
                 else if (type.equals("double"))
                 {
                       value2 =  f.getDouble(this);
                       ps.setDouble(i+1, (double) value2);
                       System.out.println(value2);
                 }
                   else if (type.equals("boolean"))
                 {
                 
                       value3 =  f.getBoolean(this);
                       ps.setBoolean(i+1, value3);
                       System.out.println(value3);
                 }
                    i++;
                
             }
            catch(Exception e){
                 System.out.println(e);
           }
    
        }
  
     ps.addBatch();
     ps.executeBatch();
     ps.close();
       conn.close();
      if(stmt!=null)
        stmt.close();
      if(conn!=null)
        conn.close();
      
      }
    
    
    private String getType(String type){
        switch(type){
            case "class java.lang.String":
                return "String";
            case "boolean":
                return "Boolean";
            case "int":
                return "Int";
            case "float":
                return "Float";
            case "double":
                return "Double";
            case "class java.util.ArrayList":
                return "Array";
            case "interface java.util.List":
                return "ArrayList";
            default:
                return null;
        }
    }
    
    public ArrayList  getBy(String field, Object value ) throws SQLException, Exception
    {
        ArrayList  list = new ArrayList();
         String sql=null;
         if (value instanceof String)
         {
            sql="SELECT * FROM "+className+" WHERE "+ field +" = '"+value+"'";
             
         }
            
         else if (value instanceof Number || value instanceof Boolean)
         {
            sql="SELECT * FROM "+className+" WHERE "+ field +" = "+value;
            
         }
        ResultSet rs = stmt.executeQuery(sql);
        System.out.println(rs);
           while(rs.next()){
        Class <?> cls = Class.forName(this.getClass().getName());
         Object obj = cls.newInstance();
        //Loop over the fields of the given class
        for (int i=0;i<fields.length;i++){
            String type = getType(fields[i].getType().toString());
            String name = fields[i].getName();
            String method="get"+type;
            Method myMethod = ResultSet.class.getDeclaredMethod(method, String.class);
            Object result=myMethod.invoke( rs,name);
            fields[i].setAccessible(true);
            fields[i].set(obj, result); 
            //System.out.println(fields[i].getName());
    
      }
        list.add(obj);
           }
        rs.close();
        stmt.close();
        conn.close();  
        return list;
        
    }

    
private String getSqlType(String type){
    switch(type){
        case "class java.lang.String":
            return "VARCHAR(255)";
        case "boolean":
            return "BOOLEAN";
        case "int":
            return "INTEGER";
        case "float":
            return "REAL";
        case "double":
            return "REAL";
        case "class java.util.ArrayList":
            return "Array";
        case "interface java.util.List":
            return "ArrayList";
        default:
            return null;
    }

}
   
    public void createTable() throws SQLException
{
    String create= "CREATE TABLE "+ this.getClass().getSimpleName() +" (";

    for (int i=0;i<fields.length;i++){
        String temp;
        String type = getSqlType(fields[i].getType().toString());
        String name = fields[i].getName();
        if(i != fields.length - 1)
            temp = name+" "+ type +", ";
        else
            temp = name+" "+ type+", ";
        create += temp;
   
    }
    create+= " primary key(client_ID))";
    stmt.executeUpdate(create);

}  




}