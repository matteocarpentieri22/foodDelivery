package it.food.food_delivery.servlet;

import it.food.food_delivery.resource.Product;
import it.food.food_delivery.resource.Category;
import it.food.food_delivery.resource.Order;
import it.food.food_delivery.resource.LogContext;
import it.food.food_delivery.resource.Message;
import it.food.food_delivery.rest.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.OutputStream;


public final class RestDispatcherServlet extends AbstractDatabaseServlet {

    /**
     * The JSON UTF-8 MIME media type
     */
    private static final String JSON_UTF_8_MEDIA_TYPE = "application/json; charset=utf-8";

    @Override
    protected void service(final HttpServletRequest req, final HttpServletResponse res) throws IOException {

        LogContext.setIPAddress(req.getRemoteAddr());

        final OutputStream out = res.getOutputStream();

        try {

            // if the requested resource was a Product or a Category or an Order, delegate its processing and return
            if (processProduct(req, res) || processCategories(req,res) || processOrder(req,res)) {
                return;
            }

            // if none of the above process methods succeeds, it means an unknown resource has been requested
            LOGGER.warn("Unknown resource requested: %s.", req.getRequestURI());

            final Message m = new Message("Unknown resource requested.", "E4A6",
                    String.format("Requested resource is %s.", req.getRequestURI()));
            res.setStatus(HttpServletResponse.SC_NOT_FOUND);
            res.setContentType(JSON_UTF_8_MEDIA_TYPE);
            m.toJSON(out);
        } catch (Throwable t) {
            LOGGER.error("Unexpected error while processing the REST resource.", t);

            final Message m = new Message("Unexpected error.", "E5A1", t.getMessage());
            res.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            m.toJSON(out);
        } finally {

            // flush and close the output stream
            if (out != null) {
                out.flush();
                out.close();
            }

            LogContext.removeIPAddress();
        }
    }


    /**
     * Checks whether the request if for a {@link Product} resource and, in case, processes it.
     *
     * @param req the HTTP request.
     * @param res the HTTP response.
     * @return {@code true} if the request was for a {@code Product}; {@code false} otherwise.
     * @throws Exception if any error occurs.
     */
    private boolean processProduct(final HttpServletRequest req, final HttpServletResponse res) throws Exception {

        final String method = req.getMethod();

        String path = req.getRequestURI();
        Message m = null;


        //the requested resource is not a product
        if (path.lastIndexOf("rest/product") <= 0) {
            return false;
        }

        // strip everything until after the /product
        path = path.substring(path.lastIndexOf("product") + 7);
        // the request URI is: /product
        // the URI is invalid
        if (path.length() == 0 || path.equals("/")) {

            m = new Message("Unsupported operation for URI /product.", "E4A5",
                            String.format("Requested operation %s.", method));
            res.setStatus(HttpServletResponse.SC_METHOD_NOT_ALLOWED);
            m.toJSON(res.getOutputStream());

        } else {
            // the request URI is: /product/category/{category}
            if (path.contains("category")) {
                path = path.substring(path.lastIndexOf("category") + 8);

              /*  if (path.length() == 0 || path.equals("/")) {
                    LOGGER.warn("Wrong format for URI /product/category/{category}/{restaurant}: no {category} and {restaurant} specified. Requested URI: %s.", req.getRequestURI());

                    m = new Message("Wrong format for URI /product/category/{category}/{restaurant}: no {category} and {restaurant} specified.", "E4A7",
                            String.format("Requested URI: %s.", req.getRequestURI()));
                    res.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                    m.toJSON(res.getOutputStream());
                } else { */
                    switch (method) {
                        case "GET":
                            new ListProductByCategoryRR(req, res, getConnection()).serve();

                            break;
                        default:
                            LOGGER.warn("Unsupported operation for URI /product/category/{category}/{restaurant}: %s.", method);

                            m = new Message("Unsupported operation for URI /product/category/{category}/{restaurant}.", "E4A5",
                                    String.format("Requested operation %s.", method));
                            res.setStatus(HttpServletResponse.SC_METHOD_NOT_ALLOWED);
                            m.toJSON(res.getOutputStream());
                            break;
                    }
               // }
            } else if (path.contains("all")) { // the request URI is: /product/all/{restaurant}
                path = path.substring(path.lastIndexOf("all") + 3);

                if (path.length() == 0 || path.equals("/")) {
                    LOGGER.warn("Wrong format for URI /product/all/{restaurant}: no {restaurant} specified. Requested URI: %s.", req.getRequestURI());

                    m = new Message("Wrong format for URI /product/all/{restaurant}: no {restaurant} specified.", "E4A7",
                            String.format("Requested URI: %s.", req.getRequestURI()));
                    res.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                    m.toJSON(res.getOutputStream());
                } else {
                    switch (method) {
                        case "GET":
                            new ListProductRR(req, res, getConnection()).serve();
                            break;
                        default:
                            LOGGER.warn("Unsupported operation for URI /product/all/{restaurant}: %s.", method);

                            m = new Message("Unsupported operation for URI /product/all/{restaurant}.", "E4A5",
                                    String.format("Requested operation %s.", method));
                            res.setStatus(HttpServletResponse.SC_METHOD_NOT_ALLOWED);
                            m.toJSON(res.getOutputStream());
                            break;
                    }
                }
            } else {
                // the request URI is: /product/{name}/{sagra}

                switch (method) {
                    case "GET":
                        new ReadProductRR(req, res, getConnection()).serve();
                        break;
                    default:
                        LOGGER.warn("Unsupported operation for URI /product/{name}/{restaurant}: %s.", method);

                        m = new Message("Unsupported operation for URI /product/{name}/{restaurant}.", "E4A5",
                                String.format("Requested operation %s.", method));
                        res.setStatus(HttpServletResponse.SC_METHOD_NOT_ALLOWED);
                        m.toJSON(res.getOutputStream());
                }
            }
        }

        return true;

    }


    /**
     * Checks whether the request if for a {@link Category}(ies) resource(s) and, in case, processes it.
     *
     * @param req the HTTP request.
     * @param res the HTTP response.
     * @return {@code true} if the request was for a {@code Category}; {@code false} otherwise.
     * @throws Exception if any error occurs.
     */
    private boolean processCategories(final HttpServletRequest req, final HttpServletResponse res) throws Exception {
        final String method = req.getMethod();

        String path = req.getRequestURI();
        Message m = null;

        //the requested resource are not categories
      /*  if (path.lastIndexOf("rest/categories") <= 0) {
            return false;
        }
*/
        // strip everything until after the /categories

        path = path.substring(path.lastIndexOf("categories") + 10);

        if (path.length() == 0 || path.equals("/")) {

            switch (method) {
                case "GET":
                    new ListCategoriesRR(req, res, getConnection()).serve();
                    break;
                default:
                    LOGGER.warn("Unsupported operation for URI /categories: %s.", method);

                    m = new Message("Unsupported operation for URI /categories.", "E4A5",
                            String.format("Requested operation %s.", method));
                    res.setStatus(HttpServletResponse.SC_METHOD_NOT_ALLOWED);
                    m.toJSON(res.getOutputStream());
                    break;
            }
        } else if (path.contains("available")) {

            path = path.substring(path.lastIndexOf("available") + 9);

            
                //the uri is rest/category/available/{sagra}
                switch (method) {
                    case "GET":
                        new ListCategoriesAvailableRR(req, res, getConnection()).serve();
                        break;
                    default:
                        LOGGER.warn("Unsupported operation for URI /categories/available/{restaurant}: %s.", method);

                        m = new Message("Unsupported operation for URI /categories/available/{restaurant}.", "E4A5",
                                String.format("Requested operation %s.", method));
                        res.setStatus(HttpServletResponse.SC_METHOD_NOT_ALLOWED);
                        m.toJSON(res.getOutputStream());
                        break;
                }

            
        } else {
            LOGGER.warn("Wrong format for URI /categories: too many arguments. Requested URI: %s.", req.getRequestURI());

            m = new Message("Wrong format for URI /categories: too many arguments.", "E4A7",
                    String.format("Requested URI: %s.", req.getRequestURI()));
            res.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            m.toJSON(res.getOutputStream());
        }

        return true;
    }


    /**
     * Checks whether the request if for a {@link Order} resource and, in case, processes it.
     *
     * @param req the HTTP request.
     * @param res the HTTP response.
     * @return {@code true} if the request was for a {@code Order}; {@code false} otherwise.
     * @throws Exception if any error occurs.
     */
    private boolean processOrder(final HttpServletRequest req, final HttpServletResponse res) throws Exception {
        final String method = req.getMethod();

        String path = req.getRequestURI();
        Message m = null;

        //the requested resource is not a order
        if (path.lastIndexOf("rest/order") <= 0) {
            return false;
        }

        // strip everything until after the /order

        path = path.substring(path.lastIndexOf("order") + 5);



        if (path.length() == 0 || path.equals("/")) {
            switch (method) {
                case "POST":
                    new CreateOrderRR(req, res, getConnection()).serve();
                    break;
                default:
                    LOGGER.warn("Unsupported operation for URI /order: %s.", method);

                    m = new Message("Unsupported operation for URI /order.", "E4A5",
                            String.format("Requested operation %s.", method));
                    res.setStatus(HttpServletResponse.SC_METHOD_NOT_ALLOWED);
                    m.toJSON(res.getOutputStream());
                    break;
            }
        }else{ //the uri is /rest/order/{order}/{sagra}
            switch (method) {
                case "GET":
                    new ReadOrderRR(req, res, getConnection()).serve();
                    break;
                default:
                    LOGGER.warn("Unsupported operation for URI /order/{order}: %s.", method);

                    m = new Message("Unsupported operation for URI /order/{order}.", "E4A5",
                            String.format("Requested operation %s.", method));
                    res.setStatus(HttpServletResponse.SC_METHOD_NOT_ALLOWED);
                    m.toJSON(res.getOutputStream());
                    break;
            }
        }
        return true;
    }
}
