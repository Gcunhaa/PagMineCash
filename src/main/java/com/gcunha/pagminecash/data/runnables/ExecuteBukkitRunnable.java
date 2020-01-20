package com.gcunha.pagminecash.data.runnables;

import org.bukkit.scheduler.BukkitRunnable;

import javax.annotation.Nullable;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class ExecuteBukkitRunnable extends BukkitRunnable {
    private final DataSource dataSource;
    private final String statement;
    private final Callback<Boolean> callback;

    public ExecuteBukkitRunnable(DataSource dataSource, String statement, @Nullable Callback<Boolean> callback) {
        if (dataSource == null) {
            //TODO: IllegalArgumentException
        }

        if (statement == null) {
            //TODO: IllegalArgumentException
        }

        this.dataSource = dataSource;
        this.statement = statement;
        this.callback = callback;
    }

    @Override
    public void run() {
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            connection = dataSource.getConnection();
            preparedStatement = connection.prepareStatement(statement);

            if (callback != null) {
                callback.call(preparedStatement.execute());
            }
        } catch (SQLException e) {
            if (callback != null) {
                e.printStackTrace();
            }
        } finally {
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
}
