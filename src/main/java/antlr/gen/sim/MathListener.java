package antlr.gen.sim;


import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.tree.ErrorNode;
import org.antlr.v4.runtime.tree.ParseTreeWalker;
import org.antlr.v4.runtime.tree.TerminalNode;

import java.io.IOException;
import java.util.Queue;
import java.util.Stack;
import java.util.concurrent.ConcurrentLinkedQueue;

public class MathListener extends DemoSimBaseListener {
    private Stack<Integer> numStack = new Stack<>();
    private Queue<String> symbols = new ConcurrentLinkedQueue<>();

    public static void main(String[] args) throws IOException {
        String s = "8 jia 2";
        CharStream input = CharStreams.fromString(s);
        DemoSimLexer lexer = new DemoSimLexer(input);
        CommonTokenStream tokenStream = new CommonTokenStream(lexer);

        DemoSimParser parser = new DemoSimParser(tokenStream);
        ParseTreeWalker walker = new ParseTreeWalker();
        MathListener mathListener = new MathListener();
        walker.walk(mathListener, parser.suanshu());
    }

    @Override
    public void enterSuanshu(DemoSimParser.SuanshuContext ctx) {
        super.enterSuanshu(ctx);
    }

    @Override
    public void enterEveryRule(ParserRuleContext ctx) {
        super.enterEveryRule(ctx);
    }

    @Override
    public void visitTerminal(TerminalNode node) {
        super.visitTerminal(node);
        String text = node.getText();
        if (text.equals("jia") || text.equals("jie")) {
            symbols.add(text);
        } else {
            if (symbols.isEmpty()) {
                numStack.push(Integer.valueOf(text));
            } else  {
                Integer pop = numStack.pop();
                String poll = symbols.poll();
                switch (poll) {
                    case "jia" :
                        numStack.push(pop + Integer.valueOf(text));
                        break;
                    case "jie" :
                        numStack.push(pop - Integer.valueOf(text));
                        break;
                }
            }
        }
    }

    @Override
    public void exitSuanshu(DemoSimParser.SuanshuContext ctx) {
        super.exitSuanshu(ctx);
        System.out.println();
        System.out.println(numStack.pop());
    }

    @Override
    public void visitErrorNode(ErrorNode node) {
        super.visitErrorNode(node);
    }
}
