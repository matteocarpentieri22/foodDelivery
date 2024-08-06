
package it.food.food_delivery.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import it.food.food_delivery.resource.Order;
import it.food.food_delivery.resource.OrderContent;

import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;
import java.sql.Timestamp;


public class SearchOrderDAO extends AbstractDAO<List<Order>>{
    /**
     * The SQL statement to be executed
     */
    private static final String STATEMENT = "SELECT * FROM food_delivery.order JOIN food_delivery.order_content ON food_delivery.order.id = food_delivery.order_content.id_order WHERE food_delivery.order_content.id_restaurant = ? ";

    /** The id of the order */
    private final int id_order;

    /** The id of the restaurant */
    private final int id_restaurant;

    /** if we want the payed order (true) or unpaid (false). */
    private final boolean paid = false;

    /** The from timestamp of order */
    private final Timestamp order_date;

    /** The collection timestamp of order */
    private final Timestamp collection_date;

    public SearchOrderDAO(final Connection con, final int id_restaurant, final int id_order, final Timestamp order_date, final Timestamp collection_date) {
        super(con);

        if (id_restaurant < 0) {
            LOGGER.error("The ID of the Restaurant cannot be null.");
            throw new NullPointerException("The ID of the restaurant cannot be null.");
        }
        this.id_restaurant = id_restaurant;
        this.id_order = id_order;
        this.order_date = order_date;
        this.collection_date = collection_date;

    }


    public SearchOrderDAO(final Connection con, final int id_restaurant, final int id_order) {
        super(con);

        this.id_restaurant = id_restaurant;
        this.id_order = id_order;
     
        this.order_date = null;
        this.collection_date = null;
    }

    /**
     *
     * @throws SQLException if some error occurred while accessing the database
     */
    @Override
    protected void doAccess() throws SQLException {
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        HashMap<String, Order> ID_dictionary = new HashMap<>();

        String Clause = "";
        if(id_order > 0){
            Clause = String.format("%s%s",Clause," AND food_delivery.order.id = ? ;");
        }else{
            if( paid )   {Clause = String.format("%s%s",Clause," AND food_delivery.order.user_id IS NOT NULL ");}
            else         {Clause = String.format("%s%s",Clause," AND food_delivery.order.user_id IS NULL ");}
            if( order_date != null )   {Clause = String.format("%s%s",Clause," AND food_delivery.order.order_date > ? ");}
            if( collection_date != null )     {Clause = String.format("%s%s",Clause," AND food_delivery.order.collection_date < ? ");}
            Clause = String.format("%s%s",Clause,";");
        }

        try{
            pstmt= con.prepareStatement(STATEMENT+Clause);
            pstmt.setLong(1,id_restaurant);

            if(id_order >= 0){
                pstmt.setLong(2,id_order);
            }else{
                int clauseCount = 2;
                if( order_date != null )   {pstmt.setTimestamp(clauseCount++,order_date);}
                if( collection_date != null )     {pstmt.setTimestamp(clauseCount++,collection_date);}
               
            }

            rs=pstmt.executeQuery();

            while(rs.next()){
                if (!ID_dictionary.containsKey(String.valueOf(rs.getInt("id")))) {//not exist
                    //create OrderContent
                    List<OrderContent> order_content_list = new ArrayList<>();
                    order_content_list.add(new OrderContent(rs.getInt("id_restaurant"),
                                                            rs.getInt("id_order"),
                                                            rs.getString("product_name"),
                                                            rs.getDouble("price"),
                                                            rs.getShort("quantity")));

                    //create Order
                    Order order = new Order(rs.getInt("id"),
                                            rs.getString("client_name"),
                                            rs.getString("client_surname"),
                                            rs.getString("phone_number"),
                                            rs.getString("email"),
                                            rs.getInt("id_admin"),
                                            rs.getTimestamp("order_date"),
                                            rs.getTimestamp("collection_date"),
                                            rs.getBoolean("silverware"),
                                            rs.getInt("box_type"),
                                            rs.getInt("user_id"),
                                            order_content_list);
                    //put into the dictionary
                    ID_dictionary.put( String.valueOf(order.getId()), order);

                }else{//already exist
                    Order order = ID_dictionary.get(String.valueOf(rs.getInt("id")));

                    List<OrderContent> order_content_list = order.getOrderContent();
                    //update list
                    order_content_list.add(new OrderContent(rs.getInt("id_restaurant"),
                                                            rs.getInt("id_order"),
                                                            rs.getString("product_name"),
                                                            rs.getDouble("price"),
                                                            rs.getShort("quantity")));
                    //order.setOrderContent(order_content_list);
                }
            }
            if(ID_dictionary.size() > 0){
                LOGGER.info("%d orders successfully listed",ID_dictionary.size());
            }else{
                LOGGER.warn("No orders found or saved");
            }

        }finally{
            if(rs!=null) rs.close();
            if(pstmt!=null) pstmt.close();
        }

        //create the list of Orders from the dictionary (convert the HashMap values to a List)
        //results of the search
        final List<Order> Orders = new ArrayList<>(ID_dictionary.values());

        this.outputParam = Orders;
    }
}
