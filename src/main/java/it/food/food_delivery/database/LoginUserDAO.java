package it.food.food_delivery.database;

import it.food.food_delivery.resource.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * This DAO finds, if present in the database, the passed user and returns it.
 *
 * @author Gianluca Rossi (gianluca.rossi.4@studenti.unipd.it)
 * @version 1.00
 * @since 1.00
 */
public final class LoginUserDAO extends AbstractDAO<List<User>>{

    private static final String STATEMENT="SELECT * FROM food_delivery.user WHERE username=? AND password=?";

    /**
     * The user to be searched
     */
    private final String username,password;

    /**
     * Creates a new DAO object.
     *
     * @param con the connection to be used for accessing the database.
     * @param username the username of the user.
     * @param password the password of the user.
     * @throws NullPointerException The username cannot be null.
     * @throws NullPointerException The username cannot be empty.
     * @throws NullPointerException The password cannot be null.
     * @throws NullPointerException The password cannot be empty.
     */
    public LoginUserDAO(final Connection con, final String username, final String password) {
        super(con);
        if (username == null) {
            LOGGER.error("The username provided cannot be null.");
            throw new NullPointerException("The username provided cannot be null.");
        }
        if(username.isEmpty()){
            LOGGER.error("The username provided cannot be empty.");
            throw new NullPointerException("The username provided cannot be empty.");
        }
        if (password == null) {
            LOGGER.error("The password provided cannot be null.");
            throw new NullPointerException("The password provided cannot be null.");
        }
        if(password.isEmpty()){
            LOGGER.error("The password provided cannot be empty.");
            throw new NullPointerException("The password provided cannot be empty.");
        }

        this.username = username;
        this.password = password;
    }

    /**
     *
     * @throws SQLException if some errors occur while performing the desired SQL operation.
     */
    @Override
    protected void doAccess() throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        final List<User> users = new ArrayList<>();
        try {
            pstmt = con.prepareStatement(STATEMENT);
            pstmt.setString(1, username);
            pstmt.setString(2, (password));
        
            rs = pstmt.executeQuery();

            while(rs.next()) {
                users.add( new User(rs.getString("username"), rs.getString("password"),rs.getString("email"),rs.getString("phone_number")));
            }
            LOGGER.info("User %s is successfully searched.", username);
        } finally {
            if (rs != null)
                rs.close();
            if (pstmt != null)
                pstmt.close();
        }
        this.outputParam=users;
    }
}
