import org.junit.*;
import static org.junit.Assert.*;

public class ClientTest{

  @Rule
  public DatabaseRule database = new DatabaseRule();

  @Test
  public void all_emptyAtFirst(){
    assertEquals(0, Client.all().size());
  }

  @Test
  public void equals_returnsTrueIfNamesAreSame(){
    Client firstClient = new Client ("Jane Doe");
    Client secondClient = new Client ("Jane Doe");
    assertEquals(firstClient, secondClient);
  }

  @Test
  public void getName_returnsCorrectName(){
    Client expClient = new Client("Jane Doe");
    assertEquals("Jane Doe", expClient.getName());
  }

  @Test
  public void getStylistId_returnsCorrectStylistId(){
    Client expClient = new Client("Jane Doe");
    expClient.addStylist(1);
    assertEquals(1, expClient.getStylistId());
  }

  @Test
  public void save_returnsTrueIfSaved(){
    Client expClient = new Client("Jane Doe");
    expClient.save();
    assertTrue(Client.all().get(0).equals(expClient));
  }

  @Test
  public void getId_returnsIdAfterSave(){
    Client expClient = new Client("Jane Doe");
    expClient.save();
    Client savedClient = Client.find(expClient.getId());
    assertEquals(savedClient, expClient);
  }

  @Test
  public void find_findsCorrectId(){
    Client expClient = new Client("Jane Doe");
    expClient.save();
    Client savedClient = Client.find(expClient.getId());
    assertEquals(savedClient, expClient);
  }

  @Test
  public void update_uptdatesStylistIdInMemmory(){
    Client expClient = new Client("Jane Doe");
    expClient.save();
    expClient.update(1);
    assertEquals(1, expClient.getStylistId());
  }

  @Test
  public void update_updatesStylistIdInDatabase(){
    Client expClient = new Client("Jane Doe");
    expClient.save();
    expClient.update(2);
    assertEquals(2, Client.all().get(0).getStylistId());
  }

  @Test
  public void delete_deletesClientFromDatabase(){
    Client expClient = new Client("Jane Doe");
    expClient.save();
    expClient.delete();
    assertEquals(0, Client.all().size());
  }
}
