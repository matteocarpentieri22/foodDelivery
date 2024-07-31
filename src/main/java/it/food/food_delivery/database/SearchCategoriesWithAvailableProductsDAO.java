package it.food.food_delivery.database;

import it.food.food_delivery.resource.Category;
import org.apache.logging.log4j.message.StringFormattedMessage;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Returns the list of the categories for which there is at least one available product.
 *
 * @author Riccardo Fazzi
 * @version 1.00
 * @since 1.00
 */

public final class SearchCategoriesWithAvailableProductsDAO extends AbstractDAO<List<Category>>{
    /**
     * The SQL statement to be executed
     */
    private static final String STATEMENT = "SELECT c.name FROM food_delivery.category AS c INNER JOIN food_delivery.product as p ON c.name=p.category WHERE p.id_restaurant=? AND p.available=TRUE GROUP BY c.name HAVING count(p.id_restaurant)>0; ";

    /**
     * The id of the restaurant
     */
    private final int id_restaurant;

    /**
     * Creates a new object for searching the categories.
     *
     * @param con the connection to the database.
     * @param id_restaurant the id of the restaurant related to the products.
     */
    public SearchCategoriesWithAvailableProductsDAO(final Connection con, final int id_restaurant){
        super(con);
        this.id_restaurant=id_restaurant;
    }
    @Override
    public final void doAccess() throws SQLException{

        PreparedStatement pstmt = null;
        ResultSet rs = null;

        //the result of the search
        final List<Category> categories = new ArrayList<Category>();

        try {
            pstmt = con.prepareStatement(STATEMENT);
            pstmt.setInt(1,id_restaurant);
            rs = pstmt.executeQuery();

            while (rs.next()) {
                categories.add(new Category(rs.getString("name")));
            }
            LOGGER.info(new StringFormattedMessage("Categories with available products for restaurant %d successfully listed.",id_restaurant));
        } finally {
            if (rs != null) {
                rs.close();
            }

            if (pstmt != null) {
                pstmt.close();
            }
        }

        this.outputParam = categories;
    }


}

