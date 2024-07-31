package it.food.food_delivery.resource;

/**
 * This class describes a user
 *
 *  @author Diego Spinosa
 *  @version 1.00
 *  @since 1.00
 */
public class User {
    /**
     * The identifier of the user.
     */
    private final int id;

    /**
     * The username of the user.
     */
    private final String username;

    /**
     * The password of the user.
     */
    private final String password;

    /**
     * The email of the user
     */
    private final String email;

    /**
     * The phone_number of the user
     */
    private final String phone_number;


    /**
     * Creates a new User object with null id.
     * Suitable for new user insertion, otherwise the appropriate ID must be manually set.
     *
     * @param username
     *            the User's displayed username and login name.
     * @param password
     *            the User's password.
     * @param id_sagra
     *            id of the Sagra the user belongs to.
     * @param admin
     *            TRUE if User is the Sagra admin.
     * @throws IllegalArgumentException if the user provides an invalid username.
     *
     * @throws IllegalArgumentException if th user provides an invalid password.
     *
     * @throws IllegalArgumentException if the user provides an invalid identifier of the Sagra.
     *
     */
    public User(final String username, final String password, final String email, final String phone_number) throws IllegalArgumentException
    {
        this.id = -1;         //NULL ID since it will NOT be user by the user insertion DAO (pgsql will generate one).
        if(username==null || username.isEmpty() || username.isBlank()) throw new IllegalArgumentException("Invalid username provided!");
        this.username=username;
        if(password==null || password.isEmpty() || password.isBlank()) throw new IllegalArgumentException("Invalid password provided!");
        this.password=password;
        if(email==null || email.isEmpty() || email.isBlank()) throw new IllegalArgumentException("Invalid email provided!");
        this.email=email;
        if(phone_number==null || phone_number.isEmpty() || phone_number.isBlank()) throw new IllegalArgumentException("Invalid phone number provided!");
        this.phone_number=phone_number;     
    }

    /**
     * Creates a new User object indicating an already db-existing user.
     *
     * @param username
     *            the User's displayed username and login name.
     * @param password
     *            the User's password.
     * @param id_sagra
     *            id of the sagra the user belongs to.
     * @param admin
     *            true if User is the sagra administrator.
     * @param id
     *            user identifier (int).
     * @throws IllegalArgumentException if the user provides an invalid username.
     *
     * @throws IllegalArgumentException if the user provides an invalid password.
     *
     * @throws IllegalArgumentException if the user provides an invalid identifier of the Sagra.
     *
     * @throws IllegalArgumentException if the user's identifier is invalid.
     *
     */
    public User(final String username, final String password, final String email, final String phone_number, final int id) throws IllegalArgumentException
    {
        if(username==null || username.isEmpty() || username.isBlank()) throw new IllegalArgumentException("Invalid username provided!");
        this.username=username;
        if(password==null || password.isEmpty() || password.isBlank()) throw new IllegalArgumentException("Invalid password provided!");
        this.password=password;
        if(email==null || email.isEmpty() || email.isBlank()) throw new IllegalArgumentException("Invalid email provided!");
        this.email=email;
        if(phone_number==null || phone_number.isEmpty() || phone_number.isBlank()) throw new IllegalArgumentException("Invalid phone number provided!");
        this.phone_number=phone_number;     
      
        
        //if(id==null || id.isEmpty() || id.isBlank()) throw new IllegalArgumentException("Invalid user id provided!");  // FOR Integer OBJ
        if(id < 0) throw new IllegalArgumentException("Invalid user id provided!");                                      // for int implementation.
        this.id = id;
    }

    /**
     * Creates a new User object used to update an already db-existing user, keeping old password.
     *
     * @param username
     *            the User's displayed username and login name.
     * @param id_sagra
     *            id of the sagra the user belongs to.
     * @param id
     *            user identifier (int).
     * @throws IllegalArgumentException if the user provides an invalid username.
     *
     * @throws IllegalArgumentException if the user provides an invalid identifier of the Sagra.
     *
     * @throws IllegalArgumentException if the user's identifier is invalid.
     */
    public User(final String username, final int id) throws IllegalArgumentException
    {
        if(username==null || username.isEmpty() || username.isBlank()) throw new IllegalArgumentException("Invalid username provided!");
        this.username=username;
        this.password=null;
        if(id < 0) throw new IllegalArgumentException("Invalid user id provided!");                                      // for int implementation.
        this.id = id;
    }

    /**
     * Creates a new User object used to delete an already db-existing user.
     * This object is only used to ensure id and sagra id's correct format.
     *
     * @param id_sagra
     *            id of the sagra the user belongs to.
     * @param id
     *            user identifier (int).
     * @throws IllegalArgumentException if the user provides an invalid identifier of the Sagra.
     *
     * @throws IllegalArgumentException if the user's identifier is invalid.
     */
    public User(final int id_sagra, final int id) throws IllegalArgumentException
    {
        this.username = null;
        this.password = null;
        //if(id_sagra==null || id_sagra.isEmpty() || id_sagra.isBlank()) throw new IllegalArgumentException("Invalid id_sagra provided!");  // FOR Integer OBJ
        if(id_sagra < 0) throw new IllegalArgumentException("Invalid id_sagra provided!");                                                  // for int implementation.
        this.id_sagra = id_sagra;
        this.admin=false;                   //HARDCODED TO PREVENT DELETING ADMINS!
        //if(id==null || id.isEmpty() || id.isBlank()) throw new IllegalArgumentException("Invalid user id provided!");  // FOR Integer OBJ
        if(id < 0) throw new IllegalArgumentException("Invalid user id provided!");                                      // for int implementation.
        this.id = id;
    }

    /**
     * Return the username.
     *
     * @return the username.
     */
    public final String getUsername()
    {
        return username;
    }

    /**
     * Return the user email.
     *
     * @return the email.
     */
    public int getEmail() {
        return email;
    }

   /**
     * Return the user phone_number.
     *
     * @return the phone_number.
     */
    public int getPhoneNumber() {
        return phone_number;
    }

    /**
     * Return the password of the user.
     *
     * @return the password of the user.
     */

    public final String getPassword()
    {
        return password;
    }

    /**
     * Return the identifier of the user.
     *
     * @return the identifier of the user.
     */
    public final int getId()
    {
        return id;
    }

}
