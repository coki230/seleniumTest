// Generated from D:/learn/seleniumTest/src/main/resources/antlr\DemoSim.g4 by ANTLR 4.9
package antlr.gen.sim;

import org.antlr.v4.runtime.tree.AbstractParseTreeVisitor;

/**
 * This class provides an empty implementation of {@link DemoSimVisitor},
 * which can be extended to create a visitor which only needs to handle a subset
 * of the available methods.
 *
 * @param <T> The return type of the visit operation. Use {@link Void} for
 * operations with no return type.
 */
public class DemoSimBaseVisitor<T> extends AbstractParseTreeVisitor<T> implements DemoSimVisitor<T> {
	/**
	 * {@inheritDoc}
	 *
	 * <p>The default implementation returns the result of calling
	 * {@link #visitChildren} on {@code ctx}.</p>
	 */
	@Override public T visitSuanshu(DemoSimParser.SuanshuContext ctx) { return visitChildren(ctx); }
}