package conf.db.dal;

import conf.db.BaseDB;
import conf.db.DbConfig;
import conf.db.model.Node;
import conf.db.model.PageModel;
import conf.db.model.query.NodeQuery;
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

    /**
     * 查询配置节点
     * @param config
     * @param query
     * @return
     */
    public List<Node> queryPageList(DbConfig config, NodeQuery query){
        Connection connection=null;
        PreparedStatement preparedStatement=null;
        ResultSet resultSet=null;
        List<Node> list=new ArrayList<Node>();
        connection=BaseDB.getConnection(config);
        try {
            preparedStatement=connection.prepareStatement("SELECT id,node_name,create_time,update_time FROM tb_node");
            resultSet=preparedStatement.executeQuery();
            list = resultToNode(resultSet);
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            BaseDB.dispose(connection,preparedStatement,null);
        }
        return list;
    }

    public PageModel<Node> queryPageList(NodeQuery query){
        List<Node> nodeList=new ArrayList<Node>();
        Connection connection=null;
        PreparedStatement preparedStatement=null;
        ResultSet resultSet=null;
        int count=0;
        try {
            connection= BaseDB.getConnection();
            preparedStatement=connection.prepareStatement("SELECT COUNT(*) FROM tb_node");
            resultSet=preparedStatement.executeQuery();
            while (resultSet.next()){
                count=resultSet.getInt(1);
            }
            if (count>0){
                preparedStatement=connection.prepareStatement("SELECT id,node_name,create_time,update_time FROM tb_node limit ?,?");
                int i=1;
                int m=2;
                preparedStatement.setInt(i,query.getStartRow());
                preparedStatement.setInt(m,query.getPageSize());
                resultSet=preparedStatement.executeQuery();
                nodeList= resultToNode(resultSet);
            }
        } catch (SQLException e) {
            BaseDB.dispose(connection,preparedStatement,resultSet);
        }finally {
            BaseDB.dispose(connection,preparedStatement,resultSet);
        }
        PageModel<Node> pageModel=new PageModel<Node>(nodeList,query.getCurrPage(),count,query.getPageSize());
        return pageModel;
    }

    /**
     * 查询配置节点
     * @param config
     * @param query
     * @return
     */
    public Integer queryCountPage(DbConfig config, NodeQuery query){
        Integer count=null;
        Connection connection=null;
        PreparedStatement preparedStatement=null;
        ResultSet resultSet=null;
        List<Node> list=new ArrayList<Node>();
        connection=BaseDB.getConnection(config);
        try {
            preparedStatement=connection.prepareStatement("SELECT COUNT(*) FROM tb_node");
            resultSet=preparedStatement.executeQuery();
            while (resultSet.next()){
                count=resultSet.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            BaseDB.dispose(connection,preparedStatement,null);
        }
        return count;
    }

    private List<Node> resultToNode(ResultSet resultSet){
        List<Node> list=new ArrayList<Node>();
        Node node=null;
        try {
            while (resultSet.next()){
                node=new Node();
                node.setId(resultSet.getLong("id"));
                node.setNodeName(resultSet.getString("node_name"));
                node.setCreateTime(resultSet.getDate("create_time"));
                node.setUpdateTime(resultSet.getDate("update_time"));
                list.add(node);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }
}
