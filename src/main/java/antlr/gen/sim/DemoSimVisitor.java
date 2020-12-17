// Generated from D:/learn/seleniumTest/src/main/resources/antlr\DemoSim.g4 by ANTLR 4.9
package antlr.gen.sim;
import org.antlr.v4.runtime.tree.ParseTreeVisitor;

/**
 * This interface defines a complete generic visitor for a parse tree produced
 * by {@link DemoSimParser}.
 *
 * @param <T> The return type of the visit operation. Use {@link Void} for
 * operations with no return type.
 */
public interface DemoSimVisitor<T> extends ParseTreeVisitor<T> {
	/**
	 * Visit a parse tree produced by {@link DemoSimParser#suanshu}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSuanshu(DemoSimParser.SuanshuContext ctx);
}