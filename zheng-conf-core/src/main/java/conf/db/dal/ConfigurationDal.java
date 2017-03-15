package conf.db.dal;

import conf.db.BaseDB;
import conf.db.DbConfig;
import conf.db.model.Configuration;
import conf.db.model.query.ConfigurationQuery;
import conf.utility.DateUtility;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by alan.zheng on 2017/3/15.
 */
public class ConfigurationDal {
    /**
     * insert
     * @param config
     * @param configuration
     * @return
     */
    public Long insertConfiguration(DbConfig config,Configuration configuration){
        Connection connection=null;
        PreparedStatement preparedStatement=null;
        ResultSet resultSet=null;
        connection= BaseDB.getConnection(config);
        try {
            preparedStatement=connection.prepareStatement("INSERT INTO tb_configuration(node_id,conf_key,conf_value,conf_desc,update_time,create_time) " +
                    "VALUES (?,?,?,?,?,?)",PreparedStatement.RETURN_GENERATED_KEYS);
            preparedStatement.setLong(1,configuration.getNodeId());
            preparedStatement.setString(2,configuration.getConfKey());
            preparedStatement.setString(3,configuration.getConfValue());
            preparedStatement.setString(4,configuration.getConfDesc());
            preparedStatement.setString(5,DateUtility.getStrFromDate(new Date(),""));
            preparedStatement.setString(6,DateUtility.getStrFromDate(new Date(),""));
            preparedStatement.execute();
            resultSet=preparedStatement.getGeneratedKeys();
            if (resultSet!=null&&resultSet.next()){
                return resultSet.getLong(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            BaseDB.dispose(connection,preparedStatement,resultSet);
        }
        return null;
    }

    /**
     * 分页查询
     * @param query
     * @return
     */
    public List<Configuration> queryPageList(ConfigurationQuery query){
        List<Configuration> nodeList=new ArrayList<Configuration>();
        Connection connection=null;
        PreparedStatement preparedStatement=null;
        ResultSet resultSet=null;
        connection= BaseDB.getConnection();
        try {
            preparedStatement=connection.prepareStatement("SELECT `id`,`node_id`,`conf_key`,`conf_value`,`conf_desc`,`update_time`,`create_time` FROM tb_configuration limit ?,?");
            int i=1;
            int m=2;
            preparedStatement.setInt(i,query.getStartRow());
            preparedStatement.setInt(m,query.getPageSize());
            resultSet=preparedStatement.executeQuery();
            nodeList= resultSetToConfiguration(resultSet);
        } catch (SQLException e) {
            BaseDB.dispose(connection,preparedStatement,resultSet);
        }finally {
            BaseDB.dispose(connection,preparedStatement,resultSet);
        }
        return nodeList;
    }

    /**
     * 分页查询条数
     * @param query
     * @return
     */
    public int queryCountPage(ConfigurationQuery query){
        Integer count=null;
        List<Configuration> nodeList=new ArrayList<Configuration>();
        Connection connection=null;
        PreparedStatement preparedStatement=null;
        ResultSet resultSet=null;
        connection= BaseDB.getConnection();
        try {
            preparedStatement=connection.prepareStatement("SELECT COUNT(*) FROM tb_businessmq_node");
            resultSet=preparedStatement.executeQuery();
            while (resultSet.next()){
                count=resultSet.getInt(1);
            }
        } catch (SQLException e) {
            BaseDB.dispose(connection,preparedStatement,resultSet);
        }finally {
            BaseDB.dispose(connection,preparedStatement,resultSet);
        }
        return count;
    }

    /**
     * 转换实体
     * @param resultSet
     * @return
     */
    private List<Configuration> resultSetToConfiguration(ResultSet resultSet){
        List<Configuration> list=new ArrayList<Configuration>();
        Configuration configuration=null;
        try {
            while (resultSet.next()){
                configuration=new Configuration();
                configuration.setId(resultSet.getLong("id"));
                configuration.setNodeId(resultSet.getInt("node_id"));
                configuration.setConfKey(resultSet.getString("conf_key"));
                configuration.setConfValue(resultSet.getString("conf_value"));
                configuration.setConfDesc(resultSet.getString("conf_desc"));
                configuration.setUpdateTime(resultSet.getDate("create_time"));
                configuration.setUpdateTime(resultSet.getDate("update_time"));
                list.add(configuration);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }
}