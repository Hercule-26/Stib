package stib.repository;

import stib.dto.PairStationDto;
import stib.dto.StationDto;
import stib.dto.StopDto;
import stib.dto.SuperKey;
import stib.exception.RepositoryException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

public class FavoriteDao implements Dao<Integer, PairStationDto> {
    private final Connection connection;
    public FavoriteDao() {
        this.connection = DBManager.getInstance().getConnection();
    }
    @Override
    public List<PairStationDto> selectAll() throws RepositoryException {
        String query = "SELECT * FROM FAVORITE";
        try(PreparedStatement pstmt = connection.prepareStatement(query)) {
            ResultSet result = pstmt.executeQuery();
            List<PairStationDto> list = new LinkedList<>();
            while (result.next()) {
                list.add(new PairStationDto(result.getInt(1),
                                            result.getInt(2), result.getString(3),
                                            result.getInt(4), result.getString(5)));
            }
            return list;
        } catch (SQLException e) {
            throw new RepositoryException(e);
        }
    }
    @Override
    public PairStationDto select(Integer key) throws RepositoryException {
        String query = "SELECT * FROM FAVORITE WHERE id = ?";
        try(PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setInt(1, key);
            ResultSet result = pstmt.executeQuery();

            if (!result.next()) return null;

            return new PairStationDto(result.getInt(1),
                                        result.getInt(2), result.getString(3),
                                        result.getInt(4), result.getString(5));
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
    }
    public void delete(Integer key) {
        String query = "DELETE FROM FAVORITE WHERE `id` = ?";
        try(PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setInt(1, key);

            int affectedRows = pstmt.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("Creating favorite failed, no rows affected.");
            }
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
    }
    public Integer add(StationDto begin, StationDto end) {
        String query = "INSERT INTO `FAVORITE` (`id_begin`, `name_begin`, `id_end`, `name_end`) VALUES (?,?,?,?)";
        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setInt(1, begin.getKey());
            pstmt.setString(2, begin.getStationName());
            pstmt.setInt(3, end.getKey());
            pstmt.setString(4, end.getStationName());

            int affectedRows = pstmt.executeUpdate();

            if (affectedRows == 0) {
                throw new SQLException("Creating favorite failed, no rows affected.");
            }

            ResultSet generatedKeys = pstmt.getGeneratedKeys();
            if (generatedKeys.next()) {
                return generatedKeys.getInt(1);
            } else {
                throw new SQLException("Creating favorite failed, no ID obtained.");
            }
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
    }

    /**
     *  Une seul accès à la base de données
     * @return le DAO
     */
    public static FavoriteDao getInstance() {
        return FavoriteDao.StibDaoHolder.INSTANCE;
    }
    private static class StibDaoHolder {
        private static final FavoriteDao INSTANCE;
        static {
            INSTANCE = new FavoriteDao();
        }
    }
}
