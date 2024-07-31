package resource;

import java.io.IOException;
import java.io.OutputStream;

public interface Resource {
    /**
     * Returns a JSON representation of the {@code Resource} into the given {@code OutputStream}.
     *
     * @param out the stream to which the JSON representation of the {@code Resource} has to be written.
     *
     * @throws IOException if something goes wrong while serializing the {@code Resource}.
     */
    void toJSON(OutputStream out) throws IOException;
}
