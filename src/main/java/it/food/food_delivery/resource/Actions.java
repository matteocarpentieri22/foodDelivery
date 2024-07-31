/*
 * Copyright (c) 2023 University of Padua, Italy
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *       https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package it.food.food_delivery.resource;

/**
 * Contains constants for the actions performed by the application.
 *
 * @author Riccardo Fazzi
 * @version 1.00
 * @since 1.00
 */
public final class Actions {

    /**
     * The loading of a product's photo
     */
    public static final String LOAD_PRODUCT_PHOTO = "LOAD_PRODUCT_PHOTO";

    /**
     * Showing list of products
     */
    public static final String SHOW_PRODUCTS = "SHOW_PRODUCTS";
    /**
     * Updating list of products
     */
    public static final String UPDATE_PRODUCT = "UPDATE_PRODUCT";


    /**
     * Deleting list of products
     */
    public static final String DELETE_PRODUCT = "DELETE_PRODUCT";
    /**
     * Creating a product
     */
    public static final String CREATE_PRODUCT = "CREATE_PRODUCT";

    /**
     * Reading a product from the database
     */
    public static final String READ_PRODUCT = "READ_PRODUCT";

    /**
     * Listing products
     */
    public static final String LIST_PRODUCT = "LIST_PRODUCT";

    /**
     * Listing products
     */
    public static final String LIST_CATEGORIES = "LIST_CATEGORIES";

    /**
     * Creating an order
     */
    public static final String CREATE_ORDER = "CREATE_ORDER";

    /**
     * Showing list of orders
     */
    public static final String SHOW_ORDERS = "SHOW_ORDERS";

    /**
     *Change an order
     */
    public static final String CHANGE_ORDER = "CHANGE_ORDER";

    /**
     * The generation of the menu to place an order.
     */
    public static final String CREATE_ORDER_MENU = "CREATE_ORDER_MENU";

    /**
     * The generation of the menu to modify an order.
     */
    public static final String CREATE_MODIFY_ORDER_MENU = "CREATE_MODIFY_ORDER_MENU";

    /**
     * The cancellation of an order.
     */
    public static final String DELETE_ORDER = "DELETE_ORDER";
    /**
     * The login operation.
     */
    public static final String LOGIN="LOGIN";
    /**
     * The logout operation.
     */
    public static final String LOGOUT="LOGOUT";
    /**
     * This class can be neither instantiated nor sub-classed.
     */
    private Actions() {
        throw new AssertionError(String.format("No instances of %s allowed.", Actions.class.getName()));
    }
}
