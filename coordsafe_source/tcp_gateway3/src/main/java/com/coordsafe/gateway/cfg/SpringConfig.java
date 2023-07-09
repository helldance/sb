package com.coordsafe.gateway.cfg;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;

import java.net.InetSocketAddress;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;




import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.command.ActiveMQQueue;
import org.apache.activemq.command.ActiveMQTopic;
import org.apache.commons.dbcp.BasicDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.data.mongodb.MongoDbFactory;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.SimpleMongoDbFactory;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jms.connection.CachingConnectionFactory;
import org.springframework.jms.core.JmsTemplate;


import com.coordsafe.gateway.dao.GpsRawDataDAOImpl;
import com.coordsafe.gateway.entity.VehicleParameter;
import com.coordsafe.gateway.handler.GpsProtocolInitalizer;
import com.mongodb.MongoClient;




@Configuration
@ComponentScan("com.coordsafe")
@PropertySource("classpath:netty-server.properties")
public class SpringConfig {

	@Value("${boss.thread.count}")
	private int bossCount;

	@Value("${worker.thread.count}")
	private int workerCount;

	@Value("${tcp.port}")
	private int tcpPort;

	@Value("${so.keepalive}")
	private boolean keepAlive;

	@Value("${so.backlog}")
	private int backlog;
	
	@Value("${db.driverClassName}")
	private String db_driverClassName;
	
	@Value("${db.url}")
	private String db_url;
	
	@Value("${db.username}")
	private String db_username;
	
	@Value("${db.password}")
	private String  db_password;
	
	@Value("${mq.url}")
	private String  mq_url;
	
	@Value("${mq.locationQ}")
	private String  mq_locationQ;
	
	@Value("${mq.alarmQ}")
	private String mq_alarmQ;
	
	@Value("${vehicle.start.internal}")
	private String  start_internal;	
	
	@Value("${vehicle.stop.internal}")
	private String  stop_internal;	
	
	@Value("${log4j.configuration}")
	private String log4jConfiguration;

	@Autowired
	@Qualifier("gpsProtocolInitializer")
	private GpsProtocolInitalizer protocolInitalizer;

	@SuppressWarnings("unchecked")
	@Bean(name = "serverBootstrap")
	public ServerBootstrap bootstrap() {
		ServerBootstrap b = new ServerBootstrap();
		b.group(bossGroup(), workerGroup())
				.channel(NioServerSocketChannel.class)
				.childHandler(protocolInitalizer);
		Map<ChannelOption<?>, Object> tcpChannelOptions = tcpChannelOptions();
		Set<ChannelOption<?>> keySet = tcpChannelOptions.keySet();
		for (@SuppressWarnings("rawtypes")
		ChannelOption option : keySet) {
			b.option(option, tcpChannelOptions.get(option));
		}
		return b;
	}

	@Bean(name = "bossGroup", destroyMethod = "shutdownGracefully")
	public NioEventLoopGroup bossGroup() {
		return new NioEventLoopGroup(bossCount);
	}

	@Bean(name = "workerGroup", destroyMethod = "shutdownGracefully")
	public NioEventLoopGroup workerGroup() {
		return new NioEventLoopGroup(workerCount);
	}

	@Bean(name = "tcpSocketAddress")
	public InetSocketAddress tcpPort() {
		return new InetSocketAddress(tcpPort);
	}

	@Bean(name = "tcpChannelOptions")
	public Map<ChannelOption<?>, Object> tcpChannelOptions() {
		Map<ChannelOption<?>, Object> options = new HashMap<ChannelOption<?>, Object>();
		options.put(ChannelOption.SO_KEEPALIVE, keepAlive);
		options.put(ChannelOption.SO_BACKLOG, backlog);
		return options;
	}

	@Bean(name = "stringEncoder")
	public StringEncoder stringEncoder() {
		return new StringEncoder();
	}

	@Bean(name = "stringDecoder")
	public StringDecoder stringDecoder() {
		return new StringDecoder();
	}

	/**
	 * For the DB 
	 * 
	 * <property name="idleConnectionTestPeriod" value="60"/>
   <property name="idleMaxAge" value="240"/>
   <property name="maxConnectionsPerPartition" value="30"/>
   <property name="minConnectionsPerPartition" value="10"/>
   <property name="partitionCount" value="3"/>
   <property name="acquireIncrement" value="5"/>
   <property name="statementsCacheSize" value="100"/>
   <property name="releaseHelperThreads" value="3"/>
	 */

	
	
	 public BasicDataSource dataSource(){
		 
		 BasicDataSource ds = new BasicDataSource();
		 ds.setDriverClassName(db_driverClassName);
		 ds.setUrl(db_url);
		 ds.setUsername(db_username);
		 ds.setPassword(db_password);
		 
		 ds.setInitialSize(10);
		 ds.setMaxActive(30);
		 
		 return ds;
		 
	 }
	 
	 
	 /*	
	    @Bean
	    @Qualifier("datasource")
  	public DriverManagerDataSource dataSource() {

        DriverManagerDataSource basicDataSource = new DriverManagerDataSource();
        basicDataSource.setDriverClassName(db_driverClassName);
        basicDataSource.setUrl(db_url);
        basicDataSource.setUsername(db_username);
        basicDataSource.setPassword(db_password);

        return basicDataSource;
    }*/

    @Bean
    @Qualifier("namedParameterJdbcTemplate")
    public NamedParameterJdbcTemplate getNamedParameterJdbcTemplate(){
    	
    	return new NamedParameterJdbcTemplate(dataSource());
    	
    }


    
    @Bean
    @Qualifier("rawDataDaoImpl")
    public GpsRawDataDAOImpl getDAOImpl(){
    	
    	GpsRawDataDAOImpl daoImpl =  new GpsRawDataDAOImpl();
    	return daoImpl;
    }
    
    //For MongoDB
    
	public @Bean
	MongoDbFactory mongoDbFactory() throws Exception {
		return new SimpleMongoDbFactory(new MongoClient("127.0.0.1"), "test");
	}
 
	public @Bean
	@Qualifier("mongoTemplate")
	MongoTemplate mongoTemplate() throws Exception {
 
		MongoTemplate mongoTemplate = new MongoTemplate(mongoDbFactory());
 
		return mongoTemplate;
 
	}
 
    
    //For ActiveMQ client

/*    public ActiveMQTopic getDestination(){
    	return new ActiveMQTopic(mq_locationQ);
    }*/
	
	public ActiveMQQueue getDestination(){
    	return new ActiveMQQueue(mq_locationQ);
	}

	public ActiveMQQueue getDestinationAlarm(){
    	return new ActiveMQQueue(mq_alarmQ);
	}
    public CachingConnectionFactory getCachingConnectionFactory(){
    	return new CachingConnectionFactory(getConnectionFactory());
    }
    @Bean
    @Qualifier("jmsTemplate")
    public JmsTemplate getJmsTemplate(){
    	JmsTemplate jmsTemplate = new JmsTemplate();
    	jmsTemplate.setConnectionFactory(getCachingConnectionFactory());
    	jmsTemplate.setDefaultDestination(getDestination());
    	return jmsTemplate;
    }
    
    @Bean
    @Qualifier("jmsTemplateForAlarm")
    public JmsTemplate getJmsTemplateAlarm(){
    	JmsTemplate jmsTemplateForAlarm = new JmsTemplate();
    	jmsTemplateForAlarm.setConnectionFactory(getCachingConnectionFactory());
    	jmsTemplateForAlarm.setDefaultDestination(getDestinationAlarm());
    	return jmsTemplateForAlarm;
    }
    
    public ActiveMQConnectionFactory getConnectionFactory(){
    	return new ActiveMQConnectionFactory(mq_url);
    }
    
    @Bean
    @Qualifier("vehicleParameter")
    public VehicleParameter getVehicleParameter(){
    	return new VehicleParameter(start_internal,stop_internal);
    }
    
    
	/**
	 * Necessary to make the Value annotations work.
	 * 
	 * @return
	 */
	@Bean
	public static PropertySourcesPlaceholderConfigurer propertyPlaceholderConfigurer() {
		return new PropertySourcesPlaceholderConfigurer();
	}
	
}
