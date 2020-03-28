import com.froggengo.springlearn.SpringLearnMain;
import io.lettuce.core.RedisClient;
import io.lettuce.core.api.StatefulRedisConnection;
import io.lettuce.core.api.sync.RedisCommands;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.File;
import java.io.IOException;

/*@RunWith(SpringRunner.class)
@SpringBootTest(classes = {SpringLearnMain.class})*/
public class testRedis {



    @Test
    public void testArray() {
        // Syntax: redis://[password@]host[:port][/databaseNumber]
        //RedisClient redisClient = RedisClient.create("redis://82878871@localhost:6379/0");
        //ssl
        //// Syntax: rediss://[password@]host[:port][/databaseNumber]
/*
        RedisClient redisClient = RedisClient.create("redis://82878871@localhost:6379/0");
        StatefulRedisConnection<String, String> connection = redisClient.connect();
        RedisCommands<String, String> sync = connection.sync();
        //1、
        //sync.set("h","hello");
        //2、
        Long append = sync.append("h", "frog");
        System.out.println("Connected to Redis");
        connection.close();
        redisClient.shutdown();
*/
        int v = 8;
        System.out.println(Integer.bitCount(v));

/*        public Process exec(String[] cmdarray, String[] envp, File dir)
        throws IOException {
            return new ProcessBuilder(cmdarray)
                    .environment(envp)
                    .directory(dir)
                    .start();
        }*/
    }


}
