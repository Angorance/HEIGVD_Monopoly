package bdfh.net;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * Define implementation details for Handlers.
 *
 * @author Daniel Gonzalez Lopez
 * @version 1.0
 */
public interface Handler {
	
	/**
	 * Handle a connection between a server and a client through the input and
	 * output streams.
	 *
	 * @param in Input stream to receive commands.
	 * @param out Output stream to send commands / info.
	 *
	 * @throws IOException
	 */
	void handle(InputStream in, OutputStream out) throws IOException;
}
