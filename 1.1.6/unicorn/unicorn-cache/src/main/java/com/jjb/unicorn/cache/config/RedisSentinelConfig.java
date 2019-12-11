//package com.jjb.unicorn.cache.config;
//
//import static org.springframework.util.Assert.hasText;
//import static org.springframework.util.Assert.isTrue;
//import static org.springframework.util.Assert.notNull;
//import static org.springframework.util.StringUtils.commaDelimitedListToSet;
//import static org.springframework.util.StringUtils.split;
//
//import java.util.Collections;
//import java.util.HashMap;
//import java.util.LinkedHashSet;
//import java.util.Map;
//import java.util.Set;
//
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.core.env.MapPropertySource;
//import org.springframework.core.env.PropertySource;
//import org.springframework.data.redis.connection.NamedNode;
//import org.springframework.data.redis.connection.RedisConfiguration;
//import org.springframework.data.redis.connection.RedisConfiguration.SentinelConfiguration;
//import org.springframework.data.redis.connection.RedisNode;
//import org.springframework.data.redis.connection.RedisPassword;
//import org.springframework.util.Assert;
//
//public class RedisSentinelConfig implements RedisConfiguration, SentinelConfiguration {
//
//	private static final String REDIS_SENTINEL_MASTER_CONFIG_PROPERTY = "spring.redis.sentinel.master";
//	private static final String REDIS_SENTINEL_NODES_CONFIG_PROPERTY = "spring.redis.sentinel.nodes";
//
//	private NamedNode master;
//	private Set<RedisNode> sentinels;
//	private int database;
//	private RedisPassword password = RedisPassword.none();
//
//	@Value("#{env['redisMaster']?:'20080'}")
//	public String redisMaster;
//	@Value("#{env['redisSentinels']?:'20080'}")
//	public String redisSentinels;
//	
//	/**
//	 * Creates new {@link RedisSentinelConfig}.
//	 */
//	public RedisSentinelConfig() {
//		
//		this(new MapPropertySource("RedisSentinelConfig", Collections.emptyMap()));
//	}
//
//	/**
//	 * Creates {@link RedisSentinelConfig} for given hostPort combinations.
//	 *
//	 * <pre>
//	 * sentinelHostAndPorts[0] = 127.0.0.1:23679 sentinelHostAndPorts[1] = 127.0.0.1:23680 ...
//	 *
//	 * <pre>
//	 *
//	 * @param sentinelHostAndPorts must not be {@literal null}.
//	 * @since 1.5
//	 */
//	public RedisSentinelConfig(String redisMaster,  String redisSentinels) {
//		this(new MapPropertySource("RedisSentinelConfiguration", asMap(redisMaster, redisSentinels)));
//	}
//
//	/**
//	 * Creates {@link RedisSentinelConfig} looking up values in given {@link PropertySource}.
//	 *
//	 * <pre>
//	 * <code>
//	 * spring.redis.sentinel.master=myMaster
//	 * spring.redis.sentinel.nodes=127.0.0.1:23679,127.0.0.1:23680,127.0.0.1:23681
//	 * </code>
//	 * </pre>
//	 *
//	 * @param propertySource must not be {@literal null}.
//	 * @since 1.5
//	 */
//	public RedisSentinelConfig(PropertySource<?> propertySource) {
//
//		notNull(propertySource, "PropertySource must not be null!");
//
//		this.sentinels = new LinkedHashSet<>();
//
//		if (propertySource.containsProperty(REDIS_SENTINEL_MASTER_CONFIG_PROPERTY)) {
//			this.setMaster(propertySource.getProperty(REDIS_SENTINEL_MASTER_CONFIG_PROPERTY).toString());
//		}
//
//		if (propertySource.containsProperty(REDIS_SENTINEL_NODES_CONFIG_PROPERTY)) {
//			appendSentinels(
//					commaDelimitedListToSet(propertySource.getProperty(REDIS_SENTINEL_NODES_CONFIG_PROPERTY).toString()));
//		}
//	}
//
//	/**
//	 * Set {@literal Sentinels} to connect to.
//	 *
//	 * @param sentinels must not be {@literal null}.
//	 */
//	public void setSentinels(Iterable<RedisNode> sentinels) {
//
//		notNull(sentinels, "Cannot set sentinels to 'null'.");
//
//		this.sentinels.clear();
//
//		for (RedisNode sentinel : sentinels) {
//			addSentinel(sentinel);
//		}
//	}
//
//	/*
//	 * (non-Javadoc)
//	 * @see org.springframework.data.redis.connection.RedisConfiguration.SentinelConfiguration#getSentinels()
//	 */
//	public Set<RedisNode> getSentinels() {
//		return Collections.unmodifiableSet(sentinels);
//	}
//
//	/**
//	 * Add sentinel.
//	 *
//	 * @param sentinel must not be {@literal null}.
//	 */
//	public void addSentinel(RedisNode sentinel) {
//
//		notNull(sentinel, "Sentinel must not be 'null'.");
//		this.sentinels.add(sentinel);
//	}
//
//	/*
//	 * (non-Javadoc)
//	 * @see org.springframework.data.redis.connection.RedisConfiguration.SentinelConfiguration#setMaster(org.springframework.data.redis.connection.NamedNode)
//	 */
//	public void setMaster(NamedNode master) {
//
//		notNull(master, "Sentinel master node must not be 'null'.");
//		this.master = master;
//	}
//
//	/*
//	 * (non-Javadoc)
//	 * @see org.springframework.data.redis.connection.RedisConfiguration.SentinelConfiguration#getMaster()
//	 */
//	public NamedNode getMaster() {
//		return master;
//	}
//
//	/**
//	 * @see #setMaster(String)
//	 * @param master The master node name.
//	 * @return this.
//	 */
//	public RedisSentinelConfig master(String master) {
//		this.setMaster(master);
//		return this;
//	}
//
//	/**
//	 * @see #setMaster(NamedNode)
//	 * @param master the master node
//	 * @return this.
//	 */
//	public RedisSentinelConfig master(NamedNode master) {
//		this.setMaster(master);
//		return this;
//	}
//
//	/**
//	 * @see #addSentinel(RedisNode)
//	 * @param sentinel the node to add as sentinel.
//	 * @return this.
//	 */
//	public RedisSentinelConfig sentinel(RedisNode sentinel) {
//		this.addSentinel(sentinel);
//		return this;
//	}
//
//	/**
//	 * @see #sentinel(RedisNode)
//	 * @param host redis sentinel node host name or ip.
//	 * @param port redis sentinel port.
//	 * @return this.
//	 */
//	public RedisSentinelConfig sentinel(String host, Integer port) {
//		return sentinel(new RedisNode(host, port));
//	}
//
//	private void appendSentinels(Set<String> hostAndPorts) {
//
//		for (String hostAndPort : hostAndPorts) {
//			addSentinel(readHostAndPortFromString(hostAndPort));
//		}
//	}
//
//	/*
//	 * (non-Javadoc)
//	 * @see org.springframework.data.redis.connection.RedisConfiguration.WithDatabaseIndex#getDatabase()
//	 */
//	@Override
//	public int getDatabase() {
//		return database;
//	}
//
//	/*
//	 * (non-Javadoc)
//	 * @see org.springframework.data.redis.connection.RedisConfiguration.WithDatabaseIndex#setDatabase(int)
//	 */
//	@Override
//	public void setDatabase(int index) {
//
////		Assert.isTrue(index >= 0, () -> String.format("Invalid DB index '%s' (a positive index required)", index));
//
//		this.database = index;
//	}
//
//	/*
//	 * (non-Javadoc)
//	 * @see org.springframework.data.redis.connection.RedisConfiguration.WithPassword#getPassword()
//	 */
//	@Override
//	public RedisPassword getPassword() {
//		return password;
//	}
//
//	/*
//	 * (non-Javadoc)
//	 * @see org.springframework.data.redis.connection.RedisConfiguration.WithPassword#setPassword(org.springframework.data.redis.connection.RedisPassword)
//	 */
//	@Override
//	public void setPassword(RedisPassword password) {
//
//		Assert.notNull(password, "RedisPassword must not be null!");
//
//		this.password = password;
//	}
//
//	private RedisNode readHostAndPortFromString(String hostAndPort) {
//
//		String[] args = split(hostAndPort, ":");
//
//		notNull(args, "HostAndPort need to be seperated by  ':'.");
//		isTrue(args.length == 2, "Host and Port String needs to specified as host:port");
//		return new RedisNode(args[0], Integer.valueOf(args[1]).intValue());
//	}
//
//	/**
//	 * @param master must not be {@literal null} or empty.
//	 * @param sentinelHostAndPorts must not be {@literal null}.
//	 * @return configuration map.
//	 */
//	private static Map<String, Object> asMap(String master, String sentinelHostAndPorts) {
//
//		hasText(master, "Master address must not be null or empty!");
//		notNull(sentinelHostAndPorts, "SentinelHostAndPorts must not be null!");
//
//		Map<String, Object> map = new HashMap<>();
//		map.put(REDIS_SENTINEL_MASTER_CONFIG_PROPERTY, master);
//		map.put(REDIS_SENTINEL_NODES_CONFIG_PROPERTY, sentinelHostAndPorts);
//
//		return map;
//	}
//}