package stib.repository;

import javafx.scene.paint.Stop;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import stib.dto.StationDto;
import stib.dto.StopDto;
import stib.dto.SuperKey;
import stib.exception.RepositoryException;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.times;

@ExtendWith(MockitoExtension.class)
@RunWith(JUnitPlatform.class)
class StopRepositoryTest {

    @Mock
    StopDao mock;

    private final StopDto stopDto;
    private final StopDto stopDto2;

    private final StopDto stopFalse;
    private final List<StopDto> all;

    public StopRepositoryTest() {
        this.stopDto = new StopDto(new SuperKey(1, 8042), 9, "ARTS-LOI");
        this.stopDto2 = new StopDto(new SuperKey(1, 8032), 8, "PARC");
        this.stopFalse = new StopDto(new SuperKey(10, 9999), 9999, "Random Name");

        this.all = new ArrayList<>();
        all.add(stopDto);
        all.add(stopDto2);

    }

    @BeforeEach
    void init() throws RepositoryException {
        Mockito.lenient().when(mock.select(stopDto.getKey())).thenReturn(stopDto);
        Mockito.lenient().when(mock.select(stopFalse.getKey())).thenReturn(null);
        Mockito.lenient().when(mock.selectAll()).thenReturn(all);
        Mockito.lenient().when(mock.select(null)).thenThrow(RepositoryException.class);
    }

    @Test
    void getAll() throws RepositoryException {
        List<StopDto> expected = all;

        StopRepository repository = new StopRepository(mock);

        List<StopDto> result = repository.getAll();
        assertEquals(expected, result);
        Mockito.verify(mock, times(1)).selectAll();
    }

    @Test
    void getGoesRight() throws RepositoryException {
        StopDto expected = stopDto;

        //StationRepository repository = new StationRepository(mock);
        //StationDto result = repository.get(stopDto.getKey());

        //assertEquals(expected, result);
        //Mockito.verify(mock, times(1)).select(stopDto.getKey());
    }

    @Test
    void getDontFound() throws RepositoryException {

        //StationRepository repository = new StationRepository(mock);
        //StationDto result = repository.get(stationDto2.getKey());

        //assertNull(result);
        //Mockito.verify(mock, times(1)).select(stationDto2.getKey());
    }

    @Test
    void getGoesWrong() throws RepositoryException {

        assertThrows(RepositoryException.class, () -> {
            //StationRepository repository = new StationRepository(mock);
            //StationDto result = repository.get(null);
        });
    }
}