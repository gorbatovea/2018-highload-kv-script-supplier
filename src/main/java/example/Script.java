package example;

import one.nio.http.HttpClient;
import one.nio.http.Response;
import one.nio.net.ConnectionString;
import ru.mail.polis.KVDao;
import script.IScript;

import java.io.InputStream;

public class Script implements IScript {

    @Override
    public String apply(KVDao context) throws Exception {
        context.remove("1".getBytes());
        return "OK";
    }

    public static void main(String[] args) throws Exception{
        HttpClient client = new HttpClient(new ConnectionString("localhost:8080"));
        String path = Script.class.getCanonicalName().replace(".", "/").concat(".class");
        InputStream inputStream = ClassLoader.getSystemResourceAsStream(path);
        byte[] bytes = new byte[inputStream.available()];
        inputStream.read(bytes);
        Response response = client.post("/v0/apply", bytes, "Classname: " + Script.class.getCanonicalName());
        System.out.println(response.getStatus());
        System.out.println(new String(response.getBody()));
    }

}
