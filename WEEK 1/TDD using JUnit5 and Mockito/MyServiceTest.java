import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

interface ExternalApi {
    String getData();
}

class MyService {
    ExternalApi api;

    MyService(ExternalApi api) {
        this.api = api;
    }

    String fetchData() {
        return api.getData();
    }
}

public class MyServiceTest {

    @Test
    public void testExternalApi() {
        // Create mock object
        ExternalApi mockApi = Mockito.mock(ExternalApi.class);

        // Stub method
        when(mockApi.getData()).thenReturn("Mock Data");

        // Use mock in service
        MyService service = new MyService(mockApi);
        String result = service.fetchData();

        // Verify result
        assertEquals("Mock Data", result);
    }
}