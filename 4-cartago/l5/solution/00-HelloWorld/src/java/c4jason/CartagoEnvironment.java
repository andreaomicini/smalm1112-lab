package c4jason;

// Environment code for project 00-HelloWorld

import jason.asSyntax.*;
import jason.environment.*;
import java.util.logging.*;

public class CartagoEnvironment extends Environment {

    private Logger logger = Logger.getLogger("00-HelloWorld."+CartagoEnvironment.class.getName());

    /** Called before the MAS execution with the args informed in .mas2j */
    @Override
    public void init(String[] args) {
        super.init(args);
        addPercept(Literal.parseLiteral("percept(demo)"));
    }

    @Override
    public boolean executeAction(String agName, Structure action) {
        logger.info("executing: "+action+", but not implemented!");
        return true;
    }

    /** Called before the end of MAS execution */
    @Override
    public void stop() {
        super.stop();
    }
}
