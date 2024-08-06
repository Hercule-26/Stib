package stib.repository;

import stib.dto.StationDto;
import stib.exception.RepositoryException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

public class StationDao implements Dao<Integer, StationDto> {
    private final Connection connection;
    private StationDao() throws RepositoryException {
        connection = DBManager.getInstance().getConnection();
    }

    @Override
    public List<StationDto> selectAll() throws RepositoryException {
        String query = "SELECT * FROM STATIONS";
        try(PreparedStatement pstmt = connection.prepareStatement(query)) {
            ResultSet result = pstmt.executeQuery();
            List<StationDto> list = new LinkedList<>();
            while (result.next()) {
                list.add(new StationDto(result.getInt(1), result.getString(2)));
            }
            return list;
        } catch (SQLException e) {
            throw new RepositoryException(e);
        }
    }

    @Override
    public StationDto select(Integer key) throws RepositoryException {
        String query = "SELECT * FROM STATIONS WHERE id = ?";
        try(PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setInt(1, key);

            ResultSet result = pstmt.executeQuery();

            if (!result.next()) return null;

            return new StationDto(result.getInt(1), result.getString(2));
        } catch (SQLException e) {
            throw new RepositoryException(e);
        }
    }

    /**
     *  Une seul accès à la base de données
     * @return le DAO
     */
    public static StationDao getInstance() {
        return StationDao.StibDaoHolder.INSTANCE;
    }

    private static class StibDaoHolder {
        private static final StationDao INSTANCE;
        static {
            try {
                INSTANCE = new StationDao();
            } catch (RepositoryException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
