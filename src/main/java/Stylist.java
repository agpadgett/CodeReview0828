import org.sql2o.*;
import java.util.List;

public class Stylist{

  private int id;
  private String stylist_name;

  public Stylist (String stylist_name){
    this.stylist_name = stylist_name;
  }

  public int getId(){
    return id;
  }

  public String getStylistName(){
    return stylist_name;
  }

  public List<Client> getClients(){
    try(Connection con = DB.sql2o.open()){
      String sql = "SELECT * FROM clients WHERE stylist_id = :id";
      return con.createQuery(sql)
          .addParameter("id", id)
          .executeAndFetch(Client.class);
    }
  }

  @Override
  public boolean equals(Object otherStylistInstance){
    if(!(otherStylistInstance instanceof Stylist)){
      return false;
    } else {
      Stylist newStylistInstance = (Stylist) otherStylistInstance;
      return this.getStylistName().equals(newStylistInstance.getStylistName()) &&
             this.getId() == newStylistInstance.getId();
    }
  }

  public static List<Stylist> all() {
    String sql = "SELECT * FROM stylists";
    try(Connection con = DB.sql2o.open()){
      return con.createQuery(sql).executeAndFetch(Stylist.class);
    }
  }

  public void save() {
    try(Connection con = DB.sql2o.open()){
      String sql = "INSERT INTO stylists (stylist_name) VALUES (:stylist_name)";
      this.id = (int)con.createQuery(sql, true)
          .addParameter("stylist_name", stylist_name)
          .executeUpdate()
          .getKey();
    }
  }

  public static Stylist find(int id){
    try(Connection con = DB.sql2o.open()){
      String sql = "SELECT * FROM stylists where id = :id";
      Stylist stylist = con.createQuery(sql)
          .addParameter("id", id)
          .executeAndFetchFirst(Stylist.class);
          return stylist;
    }
  }

  public void delete(){
    try(Connection con = DB.sql2o.open()){
      String sql = "DELETE FROM stylists WHERE id = :id";
      con.createQuery(sql)
          .addParameter("id", id)
          .executeUpdate();
    }
  }

}
