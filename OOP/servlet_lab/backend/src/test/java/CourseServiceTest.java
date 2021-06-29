import dao.CourseDao;
import org.junit.Before;
import org.junit.Test;
import service.CourseService;

import java.sql.SQLException;
import java.util.LinkedList;

import static org.mockito.Mockito.*;

public class CourseServiceTest {
    private CourseService courseService;
    private final CourseDao courseDao = mock(CourseDao.class);

    @Before
    public void init() throws SQLException {
        courseService = new CourseService();
    }

    @Test
    public void shouldGetAllRooms() throws Exception {
        when(courseDao.findAll()).thenReturn(new LinkedList<>());
        courseService.findAllCourses();
        verify(courseDao, times(1)).findAll();
    }
}
