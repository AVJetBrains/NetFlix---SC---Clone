package com.prepfortech.accessor;


import com.mysql.cj.xdevapi.PreparableStatement;
import com.prepfortech.exception.DependencyFailureException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.ParameterResolutionDelegate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.UUID;

@Repository
public class AuthAccessor {

    @Autowired
    DataSource dataSource;

    // store the token for the given userid if successful it will work , otherwise throws exception//
    public void storeToken(final String userId , final String token){
        String insertQuery = "INSERT INTO auth (authId, token, userId) values (?,?,?)";

        String uuid = UUID.randomUUID().toString();

        try(Connection connection = dataSource.getConnection()){

            PreparedStatement preparedStatement = connection.prepareStatement(insertQuery);
            preparedStatement.setString(1, uuid);
            preparedStatement.setString(2, token);
            preparedStatement.setString(3, userId);

            preparedStatement.executeUpdate();
        }
        catch(SQLException ex){
            ex.printStackTrace();
            throw new DependencyFailureException(ex);
        }

    }

}
