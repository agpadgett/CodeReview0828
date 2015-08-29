import org.junit.*;
import static org.junit.Assert.*;
import java.util.Arrays;

public class StylistTest{

  @Rule
  public DatabaseRule database = new DatabaseRule();

  @Test
  public void all_emptyAtFirst(){
    assertEquals(0, Stylist.all().size());
  }

  @Test
  public void equals_returnsTrueIfNamesAreSame(){
    Stylist firstStylist = new Stylist ("Jane Doe");
    Stylist secondStylist = new Stylist ("Jane Doe");
    assertEquals(firstStylist, secondStylist);
  }

  @Test
  public void getStylistName_returnsCorrectName(){
    Stylist expStylist = new Stylist ("Jane Doe");
    assertEquals("Jane Doe", expStylist.getStylistName());
  }

  @Test
  public void save_returnsTrueIfSaved(){
    Stylist expStylist = new Stylist("Jane Doe");
    expStylist.save();
    assertTrue(Stylist.all().get(0).equals(expStylist));
  }

  @Test
  public void find_findsCorrectId(){
    Stylist expStylist = new Stylist("Jane Doe");
    expStylist.save();
    Stylist savedStylist = Stylist.find(expStylist.getId());
    assertEquals(savedStylist, expStylist);
  }

  @Test
  public void delete_deletesStylistsFromDatabase(){
    Stylist expStylist = new Stylist("Jane Doe");
    expStylist.save();
    expStylist.delete();
    assertEquals(0, Stylist.all().size());
  }


}
