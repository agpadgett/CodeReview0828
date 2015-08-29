import org.sql2o.*;
import java.util.List;

public class Client{
  private int id;
  private String name;
  private int stylist_id;

  public Client (String name){
    this.name = name;
  }

  public int getId(){
    return id;
  }

  public String getName(){
    return name;
  }

  public void addStylist(int stylist_id){
    this.stylist_id = stylist_id;
    String sql = "UPDATE clients SET stylist_id = :stylist_id WHERE id = :id";
    try(Connection con = DB.sql2o.open()) {
      con.createQuery(sql)
        .addParameter("stylist_id", stylist_id)
        .addParameter("id", id)
        .executeUpdate();
    }
  }

  public int getStylistId(){
    return stylist_id;
  }

  @Override
  public boolean equals(Object otherClientInstance){
    if(!(otherClientInstance instanceof Client)){
      return false;
    } else {
      Client newClientInstance = (Client) otherClientInstance;
      return this.getId() == newClientInstance.getId() &&
             this.getStylistId() == newClientInstance.getStylistId() &&
             this.getName().equals(newClientInstance.getName());

    }
  }

  public static List<Client> all() {
    String sql = "SELECT * FROM clients";
    try(Connection con = DB.sql2o.open()) {
      return con.createQuery(sql).executeAndFetch(Client.class);
    }
  }

  public void save(){
    try(Connection con = DB.sql2o.open()){
      String sql = "INSERT INTO clients (name, stylist_id) VALUES (:name, :stylist_id)";
      this.id = (int)con.createQuery(sql, true)
          .addParameter("name", name)
          .addParameter("stylist_id", stylist_id)
          .executeUpdate()
          .getKey();
    }
  }

  public static Client find(int id) {
    try(Connection con = DB.sql2o.open()){
      String sql = "SELECT * FROM clients WHERE id = :id";
      Client client = con.createQuery(sql)
            .addParameter("id", id)
            .executeAndFetchFirst(Client.class);
          return client;
    }
  }

  public void update(int newStylistId) {
    stylist_id = newStylistId;
    try (Connection con = DB.sql2o.open()) {
      String sql = "UPDATE clients SET stylist_id = :stylist_id WHERE id = :id";
      con.createQuery(sql)
        .addParameter("stylist_id", stylist_id)
        .addParameter("id", id)
        .executeUpdate();
    }
  }

  public void delete(){
    try(Connection con = DB.sql2o.open()){
      String sql = "DELETE FROM clients WHERE id = :id";
      con.createQuery(sql)
        .addParameter("id", id)
        .executeUpdate();
    }
  }


}
