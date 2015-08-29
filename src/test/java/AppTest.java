import org.fluentlenium.adapter.FluentTest;
import static org.junit.Assert.*;
import org.junit.*;
import org.junit.ClassRule;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import static org.fluentlenium.core.filter.FilterConstructor.*;

import static org.assertj.core.api.Assertions.assertThat;

public class AppTest extends FluentTest{

  public WebDriver webDriver= new HtmlUnitDriver();

  @Override
  public WebDriver getDefaultDriver(){
    return webDriver;
  }

  @Rule
  public DatabaseRule database = new DatabaseRule();

  @ClassRule
  public static ServerRule server = new ServerRule();

  @Test
  public void rootTest(){
    goTo("http://localhost:4567/");
    assertThat(pageSource()).contains("available stylists:");
  }

  @Test
  public void stylistIsAdded(){
    goTo("http://localhost:4567/");
    fill("#newStylist").with("sam");
    submit(".btn");
    assertThat(pageSource()).contains("sam");
  }

  @Test
  public void routeToStylistClientListWorks(){
    Stylist myStylist = new Stylist ("jon");
    myStylist.save();
    String stylistPath = String.format("http://localhost:4567/stylists/%d", myStylist.getId());
    goTo(stylistPath);
    assertThat(pageSource()).contains("jon");
  }

  @Test
  public void addClientWorks(){
    Stylist myStylist = new Stylist ("edd");
    myStylist.save();
    Client myClient = new Client ("bob");
    myClient.addStylist(myStylist.getId());
    myClient.save();
    String stylistClientPath = String.format("http://localhost:4567/stylists/%d", myStylist.getId());
    goTo(stylistClientPath);
    assertThat(pageSource()).contains("bob");

  }

  @Test
  public void update_StylistLinkWorks(){
    Stylist myStylist = new Stylist ("tim");
    myStylist.save();
    Client myClient = new Client ("bob");
    myClient.addStylist(myStylist.getId());
    myClient.save();
    String stylistClientPath = String.format("http://localhost:4567/ChangeStylists/client/%d", myClient.getId());
    goTo(stylistClientPath);
    assertThat(pageSource()).contains("pick a new stylist for bob");
  }

  @Test
  public void update_AssignesNewStylist(){
    Stylist myStylist = new Stylist ("tim");
    myStylist.save();
    Stylist myStylist2 = new Stylist ("eric");
    myStylist2.save();
    Client myClient = new Client ("bob");
    myClient.addStylist(myStylist.getId());
    myClient.save();
    myClient.update(myStylist2.getId());
    myClient.save();
    String changeStylistPath = String.format("http://localhost:4567/stylists/%d", myStylist2.getId());
    goTo(changeStylistPath);
    assertThat(pageSource()).contains("bob");
  }
}
