package me.ele.bpm.talos.timer.session;

import javax.sql.DataSource;

import me.ele.bpm.talos.timer.mapper.CommonMapper;

import org.apache.ibatis.mapping.Environment;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.apache.ibatis.transaction.TransactionFactory;
import org.apache.ibatis.transaction.jdbc.JdbcTransactionFactory;

/**
 * SqlSession工厂类
 * @author jianming.zhou
 *
 */
public class SessionFactory {
	
	private static String env = "development";
	
	public static SqlSessionFactory getSqlSessionFactory(String databaseName) {
		DataSource dataSource = DataSourceFactory.getDataSource(databaseName);
		TransactionFactory transactionFactory = new JdbcTransactionFactory();
		Environment environment = new Environment(env, transactionFactory, dataSource);
		Configuration configuration = new Configuration(environment);
		configuration.addMapper(CommonMapper.class);
		SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(configuration);
		return sqlSessionFactory;
	}
	
}
