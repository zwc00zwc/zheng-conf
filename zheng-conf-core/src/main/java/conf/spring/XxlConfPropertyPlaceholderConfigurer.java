package conf.spring;

import conf.AppConfigContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.BeanDefinitionVisitor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;
import org.springframework.core.Ordered;
import org.springframework.util.StringValueResolver;

import java.util.Properties;

/**
 * rewrite PropertyPlaceholderConfigurer
 * @version 1.0
 * @author xuxueli 2015-9-12 19:42:49
 *
 * <bean id="xxlConfPropertyPlaceholderConfigurer" class="com.xxl.conf.core.spring.XxlConfPropertyPlaceholderConfigurer" />
 *
 */
public class XxlConfPropertyPlaceholderConfigurer extends PropertyPlaceholderConfigurer {
	private static Logger logger = LoggerFactory.getLogger(XxlConfPropertyPlaceholderConfigurer.class);

	@Override
	protected void processProperties(ConfigurableListableBeanFactory beanFactoryToProcess, Properties props) throws BeansException {

		// init value resolver
		StringValueResolver valueResolver = new StringValueResolver() {
			String placeholderPrefix = "${";
			String placeholderSuffix = "}";

			public String resolveStringValue(String strVal) {
				StringBuffer buf = new StringBuffer(strVal);
				// loop replace by xxl-conf, if the value match '${***}'
				boolean start = strVal.startsWith(placeholderPrefix);
				boolean end = strVal.endsWith(placeholderSuffix);
				while (start && end) {
					// replace by xxl-conf
					String key = buf.substring(placeholderPrefix.length(), buf.length() - placeholderSuffix.length());
					String value = ConfigManagerHelper.get(key);
//					String zkValue = XxlConfClient.get(key, "");
					buf = new StringBuffer(value);
//					logger.info(">>>>>>>>>>> xxl-conf resolved placeholder '" + key + "' to value [" + zkValue + "]");
					start = buf.toString().startsWith(placeholderPrefix);
					end = buf.toString().endsWith(placeholderSuffix);
				}
				return buf.toString();
			}
		};

		// init bean define visitor
		BeanDefinitionVisitor visitor = new BeanDefinitionVisitor(valueResolver);

		// visit bean definition
		String[] beanNames = beanFactoryToProcess.getBeanDefinitionNames();
		if (beanNames != null && beanNames.length > 0) {
			for (String beanName : beanNames) {
				if (!(beanName.equals(this.beanName) && beanFactoryToProcess.equals(this.beanFactory))) {
					BeanDefinition bd = beanFactoryToProcess.getBeanDefinition(beanName);
					visitor.visitBeanDefinition(bd);
				}
			}
		}
		
		// TODO Auto-generated method stub
		//super.processProperties(beanFactoryToProcess, props);
	}

	@Override
	public int getOrder() {
		return Ordered.LOWEST_PRECEDENCE;
	}

	private String beanName;
	@Override
	public void setBeanName(String name) {
		this.beanName = name;
	}

	private BeanFactory beanFactory;
	@Override
	public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
		this.beanFactory = beanFactory;
	}

	@Override
	public void setIgnoreUnresolvablePlaceholders(boolean ignoreUnresolvablePlaceholders) {
		super.setIgnoreUnresolvablePlaceholders(true);
	}

}
