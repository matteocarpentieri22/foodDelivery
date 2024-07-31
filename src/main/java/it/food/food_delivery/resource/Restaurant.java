package it.food.food_delivery.resource;

/** This class represents the essential features of a "Sagra"*/
public class Restaurant {

    /** The identifier of the restaurant */
    private final int id;

    /** The name of the restaurant */
    private final String name;

    /** The description of the restaurant */
    private final String description;

    /** The city where the restaurant is held */
    private final String city;

    /** The address of the restaurant */
    private final String address;


    /** Creates a new restaurant
     *  @param id
     * 	           the identifier of the restaurant
     *  @param name
     * 	             the name of the restaurant.
     * 	@param city
     * 	             the city of the restaurant.
     * 	@param address
     * 	             the address of the restaurant.
     *  @param description
     *               the description of the restaurant.

     */
    public Restaurant(final int id, final String name, final String city, final String address, final String description)
    {
        this.id = id;
        this.name = name;
        this.city = city;
        this.address = address;
        this.description = description;

    }

    /** Creates a new restaurant
     *  @param id
     * 	           the identifier of the restaurant
     *  @param name
     * 	             the name of the restaurant.

     */
    public Restaurant(final int id, final String name)
    {
        this.id = id;
        this.name = name;
        this.city = null;
        this.address = null;
        this.description = null;
    }

    /** Creates a new restaurant
     *  @param id
     * 	           the identifier of the restaurant
     *  @param name
     * 	             the name of the restaurant.
     * 	@param city
     * 	             the city of the restaurant.

     */
    public Restaurant(final int id, final String name, final String city)
    {
        this.id = id;
        this.name = name;
        this.city = city;
        this.address = null;
        this.description = null;
    }

    /** Creates a new restaurant
     *  @param id
     * 	           the identifier of the restaurant
     *  @param name
     * 	             the name of the restaurant.
     * 	@param city
     * 	             the city of the restaurant.

     *  @param description
     *               the description of the restaurant.

     */
    public Restaurant(final int id, final String name, final String city, final String description )
    {
        this.id = id;
        this.name = name;
        this.city = city;
        this.description = description;
        this.address = null;
    }

    /**
     * Returns the identifier of the restaurant.
     *
     * @return the identifier of the restaurant.
     */
    public final int getId() {return id;}

    /**
     * Return the name of the restaurant.
     *
     * @return the name of the restaurant.
     */
    public final String getName() {return name;}

    /**
     * Return the city of the restaurant.
     *
     * @return the city of the restaurant.
     */
    public final String getCity() {return city;}

    /**
     * Return the address of the restaurant.
     *
     * @return the address of the restaurant.
     */
    public final String getAddress() {return address;}

    /**
     * Return a brief description of the restaurant.
     *
     * @return a brief description of the restaurant.
     */
    public final String getDescription() {return description;}

}
