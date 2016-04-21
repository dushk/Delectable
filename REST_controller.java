
import com.google.gson.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import javax.annotation.PostConstruct;
import javax.ws.rs.*;
import javax.ws.rs.core.*;

@Path("/delectable")
public class REST_controller {
    private static DateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");

	@Path("/menu")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getMenu() {
		if(MenuManager.isCreatedMenu()) {
	        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        	String s = gson.toJson(MenuManager.viewMenu());
            return Response.status(Response.Status.OK).entity(s).build();
        } else {
    	        return Response.status(Response.Status.NOT_FOUND).entity("No Items have been added to Menu").build();
        	}
    }
	
	@Path("/menu/{mid}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getMenuByID(@PathParam("mid") int mid) {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        if(MenuManager.viewMenuItemByID(mid) != null) {
	        String s = gson.toJson(MenuManager.viewMenuItemByID(mid));
	        return Response.status(Response.Status.OK).entity(s).build();
        }
        return Response.status(Response.Status.NOT_FOUND).entity("Entity not found for ID: " + mid).build();
	}
	
	@Path("/order")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    public Response getOrderByDelDate(@DefaultValue("") @QueryParam("date") String date) {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        if(date.equals("")) {
        	if(OrderManager.isCreatedOrders()) {
        	String s = gson.toJson(OrderManager.viewOrders());
            return Response.status(Response.Status.OK).entity(s).build();
        	} else {
    	        return Response.status(Response.Status.NOT_FOUND).entity("No Orders have been created").build();
        		}
        } else {
	        if(OrderManager.viewOrdersByDelDate(date) != null) {
	        	String s = gson.toJson(OrderManager.viewOrdersByDelDate(date));
	            return Response.status(Response.Status.OK).entity(s).build();
	        }
	        return Response.status(Response.Status.NOT_FOUND).entity("Entity not found for delivery date: " + date).build();
        }
    }
	
	@Path("/order")
	@PUT
	@Produces(MediaType.APPLICATION_JSON)
	public Response createOrder(String json, @Context UriInfo uriInfo) {
		Gson gson = new Gson();		
		Order o = gson.fromJson(json, Order.class);
		int id = OrderManager.addOrderToOrders(o);
		if (id != -1) {
			JsonObject resp = new JsonObject();
			resp.addProperty("id", id);
			resp.addProperty("cancel_url", "/order/cancel/" + id);
			String s = gson.toJson(resp);
			UriBuilder builder = uriInfo.getAbsolutePathBuilder();
			builder.path(Integer.toString(id));
			return Response.created(builder.build()).entity(s).build();
		}
		else
	        return Response.status(Response.Status.NOT_FOUND).entity("ID or count is incorrect").build();
	}
	
	@Path("/order/{oid}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getORderByID(@PathParam("oid") int oid) {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        if(OrderManager.viewOrdersByID(oid) != null) {
	        String s = gson.toJson(OrderManager.viewOrdersByID(oid));
	        return Response.status(Response.Status.OK).entity(s).build();
        }
        return Response.status(Response.Status.NOT_FOUND).entity("Entity not found for ID: " + oid).build();
	}
	
	@Path("/order/cancel/{oid}")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public Response cancelOrder(@PathParam("oid") int oid) {
        if(OrderManager.viewOrdersByID(oid) != null) {
        	if(OrderManager.cancelOrder(oid) != -1) {
        		OrderManager.cancelOrder(oid);
	        	JsonObject resp = new JsonObject();
	    		resp.addProperty("id", oid);
	    		Gson gson = new Gson();
	    		String s = gson.toJson(resp);
		        return Response.status(Response.Status.NO_CONTENT).entity(s).build();
        	}
        	else 
                return Response.status(Response.Status.NOT_FOUND).entity("Entity not found for ID: " + oid).build();

        }
        return Response.status(Response.Status.NOT_FOUND).entity("Entity not found for ID: " + oid).build();
	}
	
	@Path("/customer")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    public Response getCustomerOptStr(@DefaultValue("") @QueryParam("key") String key) {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        if(key.equals("")) {
        	if(OrderManager.isCreatedCustomers()) {
	        	String s = gson.toJson(CustomerManager.viewCustomers());
	            return Response.status(Response.Status.OK).entity(s).build();
        	} else {
    	        return Response.status(Response.Status.NOT_FOUND).entity("No Customers have been created").build();
        	}
        }
        else {
	        if(CustomerManager.viewCustomersByKeyword(key) != null) {
	        	String s = gson.toJson(CustomerManager.viewCustomersByKeyword(key));
	            return Response.status(Response.Status.OK).entity(s).build();
	        }
	        return Response.status(Response.Status.NOT_FOUND).entity("Entity not found for keyword: " + key).build();
        }
    }
	
	@Path("/customer/{cid}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getCustomerByID(@PathParam("cid") int cid) {
        if(CustomerManager.viewCustomersByID(cid) != null) {
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
	        String s = gson.toJson(CustomerManager.viewCustomersByID(cid));
	        return Response.status(Response.Status.OK).entity(s).build();
        }
        return Response.status(Response.Status.NOT_FOUND).entity("Entity not found for ID: " + cid).build();
	}
	
	@Path("/report")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getReportOptions() {
        JsonArray arr = new JsonArray();
        JsonObject id = new JsonObject();
        id.addProperty("id", 1);
        id.addProperty("name", "Orders to deliver today");
        arr.add(id);
        id = new JsonObject();
        id.addProperty("id", 2);
        id.addProperty("name", "Orders to deliver tomorrow");
        arr.add(id);
        id = new JsonObject();
        id.addProperty("id", 3);
        id.addProperty("name", "Revenue report");
        arr.add(id);
        id = new JsonObject();
        id.addProperty("id", 4);
        id.addProperty("name", "Orders delivery report");
        arr.add(id);
        Gson gson = new Gson();
        String s = gson.toJson(arr);
		return Response.status(Response.Status.OK).entity(s).build();
	}
	
	
	@Path("/report/{rid}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    public Response getReportOptDates(@PathParam("rid") int rid, 
    		@DefaultValue("") @QueryParam("start_date") String start_date,
    		@DefaultValue("") @QueryParam("end_date") String end_date) {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
    	Calendar calendar = Calendar.getInstance();
        if(rid == 1) {
            if(OrderManager.isCreatedCustomers()) {
                Date today = calendar.getTime();
	        	String s = gson.toJson(OrderManager.viewOrdersByDelDate(dateFormat.format(today)));
	            return Response.status(Response.Status.OK).entity(s).build();
            }
            else 
            	return Response.status(Response.Status.NOT_FOUND).entity("No orders craeted").build();
        }
        else if(rid == 2) {
        	calendar.add(Calendar.DAY_OF_YEAR, 1);
            if(OrderManager.isCreatedCustomers()) {
	            Date tomorrow = calendar.getTime();
	        	String s = gson.toJson(OrderManager.viewOrdersByDelDate(dateFormat.format(tomorrow)));
	            return Response.status(Response.Status.OK).entity(s).build();
            }
            else 
            	return Response.status(Response.Status.NOT_FOUND).entity("No orders craeted").build();
        }
        else if(rid == 3) {
        	if(start_date.equals("")) {
        		start_date = OrderManager.viewOrders()[0].getorder_date();
        	}
        	if(end_date.equals("")) {
        		end_date = dateFormat.format(calendar.getTime());
        	}
        	String s = gson.toJson(ReportManager.viewReport(start_date, end_date));
            return Response.status(Response.Status.OK).entity(s).build();
        }
        else if(rid == 4) {
        	if(!start_date.equals("") && !end_date.equals("")) {
	        	String s = gson.toJson(OrderManager.viewOrdersBwDelDate(start_date, end_date));
	            return Response.status(Response.Status.OK).entity(s).build();
        	}
        	else 
            	return Response.status(Response.Status.NOT_FOUND).entity("Must enter dates").build();

        }
    	return Response.status(Response.Status.NOT_FOUND).entity("Entity not found for ID: " + rid).build();
    }
	
	@Path("/admin/menu")
	@PUT
	@Produces(MediaType.APPLICATION_JSON)
	public Response createMenu(String json, @Context UriInfo uriInfo) {
		Gson gson = new Gson();
		Menu m = gson.fromJson(json, Menu.class);
		int id = MenuManager.addItemToMenu(m);
		if (id > 0) {
			JsonObject resp = new JsonObject();
			resp.addProperty("id", id);
			String s = gson.toJson(resp);    
			UriBuilder builder = uriInfo.getAbsolutePathBuilder();
			builder.path(Integer.toString(id));
			return Response.created(builder.build()).entity(s).build();
		}
		else
	        return Response.status(Response.Status.NOT_FOUND).entity("ID or count is incorrect").build();
	}
	
	@Path("/admin/menu/{mid}")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public Response updatePrice(@PathParam("mid") int mid,
    		String json) {
		JsonObject obj = new Gson().fromJson(json, JsonObject.class);
        double price_per_person = obj.get("price_per_person").getAsDouble();
        if(MenuManager.viewMenuItemByID(mid) != null) {
        	MenuManager.modifyItem(mid, price_per_person);
        	JsonObject resp = new JsonObject();
    		resp.addProperty("id", mid);
    		resp.addProperty("price_per_person", price_per_person);
    		Gson gson = new Gson();
    		String s = gson.toJson(resp);
	        return Response.status(Response.Status.NO_CONTENT).entity(s).build();
        }
        return Response.status(Response.Status.BAD_REQUEST).build();
	}
	
	@Path("/admin/surcharge")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getSurcharge() {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        JsonObject resp = new JsonObject();
		resp.addProperty("surcharge", OrderManager.getSurcharge());
        String s = gson.toJson(resp);
        return Response.status(Response.Status.OK).entity(s).build();
    }	
	
	@Path("/admin/surcharge")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateSurcharge(String json) {
		if(isValid(json)) {    
			JsonObject obj = new Gson().fromJson(json, JsonObject.class);
	        double surcharge = obj.get("surcharge").getAsDouble();
        	OrderManager.setSurcharge(surcharge);
        	JsonObject resp = new JsonObject();
    		resp.addProperty("surcharge", surcharge);
    		Gson gson = new Gson();
    		String s = gson.toJson(resp);
	        return Response.status(Response.Status.NO_CONTENT).entity(s).build();
        }
        return Response.status(Response.Status.BAD_REQUEST).build();
	}
	
	public boolean isValid(String json) {
	    try {
	        new JsonParser().parse(json);
	        return true;
	    } catch (JsonSyntaxException jse) {
	        return false;
	    }
	}
}