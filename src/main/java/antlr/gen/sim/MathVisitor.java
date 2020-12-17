package antlr.gen.sim;


import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.TerminalNode;

import java.util.Queue;
import java.util.Stack;
import java.util.concurrent.ConcurrentLinkedQueue;

public class MathVisitor extends DemoSimBaseVisitor<Integer> {
    private Stack<Integer> numStack = new Stack<>();
    private Queue<String> symbols = new ConcurrentLinkedQueue<>();
    @Override
    public Integer visitTerminal(TerminalNode node) {
        String text = node.getText();
        Integer num = -1;
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
                        num = pop + Integer.valueOf(text);
                        numStack.push(num);
                        break;
                    case "jie" :
                        num = pop - Integer.valueOf(text);
                        numStack.push(num);
                        break;
                }
            }
        }
        return num;
    }

    public static void main(String[] args) {
        String s = "8 jia 2";
        CharStream input = CharStreams.fromString(s);
        DemoSimLexer lexer = new DemoSimLexer(input);
        CommonTokenStream tokenStream = new CommonTokenStream(lexer);

        DemoSimParser parser = new DemoSimParser(tokenStream);
        MathVisitor visitor = new MathVisitor();
        Integer visit = visitor.visit(parser.suanshu());
        System.out.println();
        System.out.println(visit);
    }
}
