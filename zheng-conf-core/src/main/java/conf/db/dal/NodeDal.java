package conf.db.dal;

import conf.db.BaseDB;
import conf.db.DbConfig;
import conf.db.model.Node;
import conf.utility.DateUtility;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

/**
 * Created by alan.zheng on 2017/3/15.
 */
public class NodeDal {
    /**
     * insert并返回主键
     * @param config
     * @param node
     * @return
     */
    public Long insertNode(DbConfig config, Node node){
        Connection connection=null;
        PreparedStatement preparedStatement=null;
        ResultSet resultSet=null;
        connection= BaseDB.getConnection(config);
        try {
            preparedStatement=connection.prepareStatement("INSERT INTO tb_node(`node_name`,`create_time`,`update_time`) VALUES (?,?,?)",PreparedStatement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1,node.getNodeName());
            preparedStatement.setString(2,DateUtility.getStrFromDate(new Date(),""));
            preparedStatement.setString(3, DateUtility.getStrFromDate(new Date(),""));
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
     * 更新ById
     * @param config
     * @param node
     * @return
     */
    public boolean updateNode(DbConfig config, Node node){
        Connection connection=null;
        PreparedStatement preparedStatement=null;
        connection=BaseDB.getConnection(config);
        try {
            preparedStatement=connection.prepareStatement("UPDATE tb_node SET `node_name`=?,`update_time`=? WHERE id=?");
            preparedStatement.setString(1,node.getNodeName());
            preparedStatement.setString(2,DateUtility.getStrFromDate(new Date(),""));
            preparedStatement.setLong(3,node.getId());
            if (preparedStatement.executeUpdate()>0){
                return true;
            }
            return false;
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            BaseDB.dispose(connection,preparedStatement,null);
        }
        return false;
    }
}
