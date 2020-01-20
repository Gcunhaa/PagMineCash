package com.gcunha.pagminecash.data.runnables;

import com.gcunha.pagminecash.bank.Bank;
import org.bukkit.scheduler.BukkitRunnable;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

public class QuerryCashBankRunnable extends BukkitRunnable {
    private final DataSource dataSource;
    private final Callback<ResultSet> callback;
    private final UUID uuid;
    private Bank result;

    public QuerryCashBankRunnable(DataSource dataSource, UUID uuid, Callback<ResultSet> callback) {
        if (dataSource == null) {
            //TODO: IllegalArgumentException
        }

        if (callback == null) {
            //TODO: IllegalArgumentException
        }

        this.uuid = uuid;
        this.dataSource = dataSource;
        this.callback = callback;
    }

    @Override
    public void run() {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            String query = "SELECT * FROM `pagminecash` WHERE Uuid = ?";

            connection = dataSource.getConnection();
            preparedStatement = connection.prepareStatement(query);

            preparedStatement.setString(1,uuid.toString());
            System.out.print("EEEEEEEE");
            resultSet = preparedStatement.executeQuery();
            //callback.call(resultSet);
            if(resultSet != null && resultSet.next()){
                this.result = new Bank(uuid,resultSet.getFloat("Cash"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (resultSet != null) {
                try {
                    resultSet.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }

            if (preparedStatement != null) {
                try {
                    preparedStatement.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }

            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public Bank getResult() {
        return result;
    }
}