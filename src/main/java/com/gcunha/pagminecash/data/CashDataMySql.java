package com.gcunha.pagminecash.data;

import com.gcunha.pagminecash.PagMineCash;
import com.gcunha.pagminecash.bank.Bank;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.UUID;

public class CashDataMySql implements CashData {

    private final PagMineCash plugin;
    private HikariDataSource dataSource;

    private String username;
    private String password;
    private String hostname;
    private String port;
    private String database;


    public CashDataMySql() {
        this.plugin = PagMineCash.getInstance();
        setupConfig();
        setupPool();
        setupTable();
    }

    @Override
    public Bank getBank(UUID uuid) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        Bank result = null;

        try {
            String query = "SELECT * FROM `pagminecash` WHERE Uuid = ?";

            connection = dataSource.getConnection();
            preparedStatement = connection.prepareStatement(query);

            preparedStatement.setString(1,uuid.toString());
            resultSet = preparedStatement.executeQuery();

            if(resultSet != null && resultSet.next()){
                result = new Bank(uuid,resultSet.getInt("Cash"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            close(connection,preparedStatement,resultSet);
        }

        return result;
    }


    @Override
    public ArrayList<Bank> getTopBanks() {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        ArrayList<Bank> result = new ArrayList<>();

        try {
            //TODO: TORNAR O LIMITE CONFIGURAVEL
            String query = "SELECT * FROM `pagminecash` ORDER BY Cash DESC LIMIT 10";

            connection = dataSource.getConnection();
            preparedStatement = connection.prepareStatement(query);

            
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()){
                Bank bank = new Bank(UUID.fromString(resultSet.getString("Uuid")),resultSet.getInt("Cash"));
                result.add(bank);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            close(connection,preparedStatement,resultSet);
        }

        return result;
    }

    @Override
    public void updateCash(Bank bank) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        ArrayList<Bank> result = new ArrayList<>();

        try {
            //TODO: TORNAR O LIMITE CONFIGURAVEL
            String query = "UPDATE `pagminecash` SET Cash=? WHERE Uuid=?";

            connection = dataSource.getConnection();
            preparedStatement = connection.prepareStatement(query);

            preparedStatement.setInt(1,bank.getCash());
            preparedStatement.setString(2,bank.getOwnerUuid().toString());

            preparedStatement.execute();

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            close(connection,preparedStatement,null);
        }

    }

    @Override
    public void createBank(UUID uuid) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;


        try {
            String query = "INSERT INTO `pagminecash` (Uuid) VALUES (?)";

            connection = dataSource.getConnection();
            preparedStatement = connection.prepareStatement(query);

            preparedStatement.setString(1,uuid.toString());
            preparedStatement.execute();

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            close(connection,preparedStatement,null);
        }

    }

    private HikariDataSource getDataSource(){
        return this.dataSource;
    }

    // Esse eh o metodo responsavel por encerrar a consulta (nao encerra a conexao)
    @Override
    public void close(Connection conn, PreparedStatement ps, ResultSet res) {
        if (conn != null) try { conn.close(); } catch (SQLException ignored) {}
        if (ps != null) try { ps.close(); } catch (SQLException ignored) {}
        if (res != null) try { res.close(); } catch (SQLException ignored) {}
    }


    //Esse metodo eh responsavel por encerrar a conexao
    @Override
    public void closePool() {
        if (dataSource != null && !dataSource.isClosed()) {
            dataSource.close();
        }
    }

    private void setupTable() {
        Connection conn = null;
        PreparedStatement ps = null;

        try {
            conn = getDataSource().getConnection();
            ps = conn.prepareStatement(
                    "CREATE TABLE IF NOT EXISTS `pagminecash` " +
                            "( `Uuid` VARCHAR(36) NOT NULL ," +
                            " `Cash` INTEGER NOT NULL DEFAULT '0' ," +
                            " PRIMARY KEY (`Uuid`)" +
                            ")"
            );
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();

            //Perceba que eh importante finalizar a query apos realiza-la.
        } finally {
            close(conn, ps, null);
        }

        try {
            conn = getDataSource().getConnection();
            ps = conn.prepareStatement(
                    "CREATE TABLE IF NOT EXISTS `pagminecash_keys` " +
                            "( `Key` VARCHAR(36) NOT NULL ," +
                            " `Cash` INTEGER NOT NULL DEFAULT '0' ," +
                            " PRIMARY KEY (`Key`)" +
                            ")"
            );
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();

            //Perceba que eh importante finalizar a query apos realiza-la.
        } finally {
            close(conn, ps, null);
        }
    }

    private void setupConfig(){
        this.username = "root";
        this.password = "";
        this.hostname = "localhost";
        this.port = "3306";
        this.database = "pagmine";
    }

    private void setupPool() {
        HikariConfig config = new HikariConfig();
        config.setJdbcUrl(
                "jdbc:mysql://" +
                        this.hostname +
                        ":" +
                        this.port +
                        "/" +
                        this.database
        );
        config.setDriverClassName("com.mysql.jdbc.Driver");
        config.setUsername(this.username);
        config.setPassword(this.password);
        this.dataSource = new HikariDataSource(config);
    }
}
