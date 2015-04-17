import org.apache.log4j.Logger;


public class LoggerDemo {
	final static Logger logger = Logger.getLogger(LoggerDemo.class);
	
	public static void main(String[] args){
		if(logger.isDebugEnabled()){
		    logger.debug("This is debug");
		}
		
		logger.error("This is error : ");
	}
}
