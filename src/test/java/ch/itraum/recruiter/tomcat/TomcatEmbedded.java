package ch.itraum.recruiter.tomcat;

import java.io.File;

import org.apache.catalina.Context;
import org.apache.catalina.startup.Tomcat;
import org.apache.naming.resources.VirtualDirContext;

public class TomcatEmbedded {

	public static void main(String[] args) throws Exception {

		Tomcat tomcat = new Tomcat();
		tomcat.setPort(8080);

		Context context = tomcat.addWebapp("/",	new File("src/main/webapp/").getAbsolutePath());

		File additionWebInfClasses = new File("bin");
		VirtualDirContext resources = new VirtualDirContext();
		resources.setExtraResourcePaths("/WEB-INF/classes="	+ additionWebInfClasses);
		context.setResources(resources);

		tomcat.start();
		tomcat.getServer().await();
	}
}
