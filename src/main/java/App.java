import java.util.HashMap;
import java.util.Arrays;
import java.util.ArrayList;
import java.util.List;
import java.lang.*;
import static spark.Spark.*;
import spark.ModelAndView;
import spark.template.velocity.VelocityTemplateEngine;
import java.util.Map;


public class App{
  public static void main(String[] args) {
    staticFileLocation("/public");
    String layout = "templates/layout.vtl";

    get("/", (request, response ) -> {
      HashMap<String, Object> model = new HashMap<String, Object >();
      List<Stylist> stylists = Stylist.all();
      model.put("stylists", stylists);
      model.put("template", "templates/index.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    post("/stylists", (request, response) -> {
      HashMap<String, Object> model = new HashMap<String, Object>();
      Stylist newStylist = new Stylist(request.queryParams("newStylist"));
      newStylist.save();
      List<Stylist> stylists = Stylist.all();
      model.put("stylists", stylists);
      model.put("template", "templates/index.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    post("/stylists/delete", (request, response) -> {
      HashMap<String, Object> model = new HashMap<String, Object >();
      Stylist deadStylist = Stylist.find(Integer.parseInt(request.queryParams("deleteStylist")));
      deadStylist.delete();
      List<Stylist> stylists = Stylist.all();
      model.put("stylists", stylists);
      model.put("template", "templates/index.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    get("/stylists/:id", (request, response ) -> {
      HashMap<String, Object> model = new HashMap<String, Object>();
      Stylist stylist = Stylist.find(Integer.parseInt(request.params(":id")));
      List<Client> clients = stylist.getClients();
      int clientSize = clients.size();
      model.put("clientSize", clientSize);
      model.put("clients", clients);
      model.put("stylist", stylist);
      model.put("template", "templates/stylistclients.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    post("/stylists/:id", (request, response) -> {
      HashMap<String, Object> model = new HashMap<String, Object>();
      Stylist stylist = Stylist.find(Integer.parseInt(request.params(":id")));
      Client newClient = new Client(request.queryParams("newClient"));
      newClient.addStylist(stylist.getId());
      newClient.save();
      List<Client> clients = stylist.getClients();
      int clientSize = clients.size();
      model.put("clientSize", clientSize);
      model.put("clients", clients);
      model.put("stylist", stylist);
      model.put("template", "templates/stylistclients.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    post("/stylists/:id/delete", (request, response) -> {
      HashMap<String, Object> model = new HashMap<String, Object>();
      Stylist stylist = Stylist.find(Integer.parseInt(request.params(":id")));
      Client deadClient = Client.find(Integer.parseInt(request.queryParams("deleteClient")));
      deadClient.delete();
      List<Client> clients = stylist.getClients();
      int clientSize = clients.size();
      model.put("clientSize", clientSize);
      model.put("clients", clients);
      model.put("stylist", stylist);
      model.put("template", "templates/stylistclients.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

   get("/ChangeStylists/client/:id", (request, response) -> {
      HashMap<String, Object> model = new HashMap<String, Object>();
      Client client = Client.find(Integer.parseInt(request.params(":id")));
    //  Stylist removedStylist = Stylist.find(Integer.parseInt(request.queryParams("changedStylist")));
      List<Stylist> stylists = Stylist.all();
      model.put("stylists", stylists);
      model.put("client", client);
      model.put("template","templates/changestylist.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    post("/stylists/:id/success", (request, response) -> {
      HashMap<String, Object> model = new HashMap<String, Object>();
      Stylist stylist = Stylist.find(Integer.parseInt(request.queryParams("changedStylist")));
      Client client = Client.find(Integer.parseInt(request.params(":id")));
      client.update(stylist.getId());
      model.put("stylist", stylist);
      model.put("client", client);
      model.put("template","templates/success.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());
  }
}
