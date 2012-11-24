package in.brightpush.resque.aim.synchronization;

import org.apache.log4j.Logger;

public class Main {

	static Logger log = Logger.getLogger(Main.class.getName());
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		log.info("Starting TaskQueue <tokens-create / tokens-update> processor");
		log.info("Finishing TaskQueue <tokens-create / tokens-update> processor");
	}
}
