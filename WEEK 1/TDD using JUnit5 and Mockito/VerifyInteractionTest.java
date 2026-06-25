import static org.mockito.Mockito.*;
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

    void fetchData() {
        api.getData();
    }
}

public class VerifyInteractionTest {

    @Test
    public void testVerifyInteraction() {
        // Create mock object
        ExternalApi mockApi = Mockito.mock(ExternalApi.class);

        // Use mock in service
        MyService service = new MyService(mockApi);
        service.fetchData();

        // Verify interaction
        verify(mockApi).getData();
    }
}