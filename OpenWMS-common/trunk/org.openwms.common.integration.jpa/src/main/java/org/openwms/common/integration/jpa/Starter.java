/**
 * 
 */
package org.openwms.common.integration.jpa;

import org.openwms.common.domain.system.usermanagement.User;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

/**
 * @author heiko
 *
 */
public class Starter implements ApplicationContextAware {
	
	public Starter() {
		System.out.println("Starting application");
		User user = new User("Heiko");
		System.out.println("User:"+user.getUsername());
	}
	
	public void set() {
		System.out.println("Starting application");
	}
	
	@Override
	public void setApplicationContext(ApplicationContext arg0) throws BeansException {
		System.out.println("Starting application");
	}

}
