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

/**
 * Searches the order by ID for that sagra.
 * @author Riccardo Fazzi
 * @version 1.00
 * @since 1.00
 */
public class SearchOrderDAO extends AbstractDAO<List<Order>>{
    /**
     * The SQL statement to be executed
     */
    private static final String STATEMENT = "SELECT * FROM food_delivery.order JOIN food_delivery.order_content ON food_delivery.order.id = food_delivery.order_content.id_order WHERE food_delivery.order_content.id_restaurant = ? ";

    /** The id of the order */
    private final int id_order;

    /** The id of the sagra */
    private final int id_restaurant;

    /** if we want the payed order (true) or unpaid (false). */
    private final boolean paid;

    private final Timestamp order_date;

    private final Timestamp collection_date;

    private final boolean silverware;

    private final int box_type;

    private final int user_id;

    /**
     * Creates a new object for searching orders.
     *
     * @param con
     *          the connection to the database.
     * @param id_sagra
     *          the id of the sagra.
     * @param id_order
     *          the id of the order.
     * @param paid
     *          if we want the payed order (true) or unpaid (false).
     * @param from_order_time
     *          the start timestamp of the ordering times.
     * @param to_order_time
     *          the end timestamp of the ordering times.
     * @param from_payment_time
     *          the start timestamp of the payment times.
     * @param to_payment_time
     *          the end timestamp of the payment times.
     * @throws NullPointerException the ID of the sagra cannot be null.
     */
    public SearchOrderDAO(final Connection con, final int id_restaurant, final int id_order, final Timestamp order_date, final Timestamp collection_date, final boolean silverware, final int box_type, final int user_id) {
        super(con);

        if (id_restaurant < 0) {
            LOGGER.error("The ID of the Restaurant cannot be null.");
            throw new NullPointerException("The ID of the Restaurant cannot be null.");
        }
        this.id_restaurant = id_restaurant;
        this.id_order = id_order;
        this.order_date = order_date;
        this.collection_date = collection_date;
        this.silverware = silverware;
        this.box_type = box_type;
        this.user_id = user_id;
    }

    /**
     * Creates a new object for searching orders giving only the id.
     *
     * @param con
     *          the connection to the database.
     * @param id_sagra
     *          the id of the sagra.
     * @param id_order
     *          the id of the order.
     *
     
    public SearchOrderDAO(final Connection con, final int id_restaurant, final int id_order) {
        super(con);

        this.id_restaurant = id_restaurant;
        this.id_order = id_order;
        this.order_date = null;
        this.collection_date = null;
        this.silverware = null;
        this.box_type = null;
        this.user_id = null;
    }
*/
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
            if( paid )   {Clause = String.format("%s%s",Clause," AND food_delivery.order.id_user IS NOT NULL ");}
            else         {Clause = String.format("%s%s",Clause," AND food_delivery.order.id_user IS NULL ");}
            if( from_order_time != null )   {Clause = String.format("%s%s",Clause," AND sagrone.order.order_time > ? ");}
            if( to_order_time != null )     {Clause = String.format("%s%s",Clause," AND sagrone.order.order_time < ? ");}
            if( from_payment_time != null ) {Clause = String.format("%s%s",Clause," AND sagrone.order.payment_time > ? ");}
            if( to_payment_time != null )   {Clause = String.format("%s%s",Clause," AND sagrone.order.payment_time < ? ");}
            Clause = String.format("%s%s",Clause,";");
        }

        try{
            pstmt= con.prepareStatement(STATEMENT+Clause);
            pstmt.setLong(1,id_sagra);

            if(id_order >= 0){
                pstmt.setLong(2,id_order);
            }else{
                int clauseCount = 2;
                if( from_order_time != null )   {pstmt.setTimestamp(clauseCount++,from_order_time);}
                if( to_order_time != null )     {pstmt.setTimestamp(clauseCount++,to_order_time);}
                if( from_payment_time != null ) {pstmt.setTimestamp(clauseCount++,from_payment_time);}
                if( to_payment_time != null )   {pstmt.setTimestamp(clauseCount++,to_payment_time);}
            }

            rs=pstmt.executeQuery();

            while(rs.next()){
                if (!ID_dictionary.containsKey(String.valueOf(rs.getInt("id")))) {//not exist
                    //create OrderContent
                    List<OrderContent> order_content_list = new ArrayList<>();
                    order_content_list.add(new OrderContent(rs.getInt("id_sagra"),
                                                            rs.getInt("id_order"),
                                                            rs.getString("product_name"),
                                                            rs.getDouble("price"),
                                                            rs.getShort("quantity")));

                    //create Order
                    Order order = new Order(rs.getInt("id"),
                                            rs.getString("client_name"),
                                            rs.getString("email"),
                                            rs.getShort("client_num"),
                                            rs.getString("table_number"),
                                            rs.getInt("id_user"),
                                            rs.getTimestamp("order_time"),
                                            rs.getTimestamp("payment_time"),
                                            order_content_list);
                    //put into the dictionary
                    ID_dictionary.put( String.valueOf(order.getId()), order);

                }else{//already exist
                    Order order = ID_dictionary.get(String.valueOf(rs.getInt("id")));

                    List<OrderContent> order_content_list = order.getOrderContent();
                    //update list
                    order_content_list.add(new OrderContent(rs.getInt("id_sagra"),
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
