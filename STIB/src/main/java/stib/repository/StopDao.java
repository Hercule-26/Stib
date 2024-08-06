package stib.repository;
import stib.dto.StopDto;
import stib.dto.SuperKey;
import stib.exception.RepositoryException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class StopDao implements Dao<SuperKey, StopDto> {
    private Connection connection;

    public StopDao() {
        connection = DBManager.getInstance().getConnection();
    }

    @Override
    public List<StopDto> selectAll() throws RepositoryException {
        String query = "SELECT id_line, id_station, id_order, name " +
                "From STOPS " +
                "JOIN STATIONS ON id_station = id " +
                "ORDER BY id_line, id_order";
        try(PreparedStatement pstmt = connection.prepareStatement(query)) {
            ResultSet result = pstmt.executeQuery();
            List<StopDto> list = new LinkedList<>();
            while (result.next()) {
                list.add(new StopDto(new SuperKey(result.getInt(1),
                                                    result.getInt(2)),
                                                    result.getInt(3),
                                                    result.getString(4)));
            }
            return list;
        } catch (SQLException e) {
            throw new RepositoryException(e);
        }
    }
    @Override
    public StopDto select(SuperKey key) throws RepositoryException {
        String query = "SELECT id_line, id_station, id_order, name " +
                "FROM STOPS " +
                "JOIN STATIONS ON id_station = id " +
                "WHERE id_line = ? AND id_station = ?";
        try(PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setInt(1, key.getLineKey());
            pstmt.setInt(2, key.getStationKey());

            ResultSet result = pstmt.executeQuery();

            if (!result.next()) return null;

            return new StopDto(new SuperKey(result.getInt(1),
                    result.getInt(2)),
                    result.getInt(3),
                    result.getString(4));

        } catch (SQLException e) {
            throw new RepositoryException(e);
        }
    }

    /**
     *  Une seul accès à la base de données
     * @return le DAO
     */
    public static StopDao getInstance() {
        return StopDao.StopDaoHolder.INSTANCE;
    }

    private static class StopDaoHolder {
        private static final StopDao INSTANCE;
        static {
            INSTANCE = new StopDao();
        }
    }
}
